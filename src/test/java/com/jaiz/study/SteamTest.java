package com.jaiz.study;

import com.jaiz.study.bean.User;
import com.jaiz.study.utils.RandomString;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamTest {

    /**
     * 无限流测试
     */
    @Test
    public void unlimitedSteamTest() {
        System.out.println("使用generate生成无限流");
        Stream<Double> generateStream = Stream.generate(Math::random).limit(10);
        generateStream.forEach(System.out::println);

        System.out.println("使用iterate创建无限流");
        Stream<Integer> iterateStream = Stream.iterate(1, a -> a * 2).limit(10);
        iterateStream.forEach(System.out::println);
    }

    /**
     * 生成随机测试List
     *
     * @param limit
     * @return
     */
    private List<User> genTestList(int limit) {
        Random r = new Random();
        RandomString rs = new RandomString();
        Supplier<User> userGenerator = () -> User.build(r.nextInt(50), rs.next(8));
        return Stream.generate(userGenerator).limit(limit).collect(Collectors.toList());
    }

    /**
     * user列表打印函数
     */
    private Consumer<Object> printer = System.out::println;

    private void compareUserList(List<User> list1, List<User> list2) {
        System.out.println("原始列表中共有" + list1.size() + "个user：");
        list1.forEach(printer);
        System.out.println("处理后共有" + list2.size() + "个user");
        list2.forEach(printer);
    }

    /**
     * 测试去重
     */
    @Test
    public void distinctTest() {
        var userList = genTestList(20);
        var distinctUserList = userList.stream().distinct().collect(Collectors.toList());
        compareUserList(userList, distinctUserList);
    }

    /**
     * 测试过滤
     */
    @Test
    public void filterTest() {
        var userList = genTestList(20);
        var filterUserList = userList.stream().filter(user -> user.getId() <= 25).collect(Collectors.toList());
        compareUserList(userList, filterUserList);
    }

    @Test
    public void sortedTest() {
        var userList = genTestList(20);
        //这里用到了实例方法的静态引用
        //User::getId实际上相当于创建了一个含有User类型参数和Integer类型返回值的Function
        var sortedUserList = userList.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
    }

    @Test
    public void sortLimitTest() {
        //先sort与先limit的结果有区别吗？
        var userList = genTestList(20);
        var sortedUserList = userList.stream().sorted(Comparator.comparing(User::getId)).limit(10).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
        var sortedUserList2 = userList.stream().limit(10).sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        compareUserList(userList, sortedUserList2);
        //答有区别，两操作的执行顺序是被保留的
    }

    @Test
    public void skipLimitTest(){
        //skip配合limit实现分页
        var userList = genTestList(20);
        userList.sort(Comparator.comparing(User::getId));
        var sortedUserList=userList.stream().skip(5).limit(5).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
    }

    @Test
    public void mapTest(){
        //使用map将user列表转换成String列表，记录name
        var userList = genTestList(20);
        var nameList=userList.stream().map(User::getName).collect(Collectors.toList());
        userList.forEach(printer);
        nameList.forEach(printer);
    }

}
