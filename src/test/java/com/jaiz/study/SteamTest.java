package com.jaiz.study;

import com.jaiz.study.bean.User;
import com.jaiz.study.utils.RandomString;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
        List<User> userList = genTestList(20);
        List<User> distinctUserList = userList.stream().distinct().collect(Collectors.toList());
        compareUserList(userList, distinctUserList);
    }

    /**
     * 测试过滤
     */
    @Test
    public void filterTest() {
        List<User> userList = genTestList(20);
        List<User> filterUserList = userList.stream().filter(user -> user.getId() <= 25).collect(Collectors.toList());
        compareUserList(userList, filterUserList);
    }

    @Test
    public void sortedTest() {
        List<User> userList = genTestList(20);
        //这里用到了实例方法的静态引用
        //User::getId实际上相当于创建了一个含有User类型参数和Integer类型返回值的Function
        List<User> sortedUserList = userList.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
    }

    @Test
    public void sortLimitTest() {
        //先sort与先limit的结果有区别吗？
        List<User> userList = genTestList(20);
        List<User> sortedUserList = userList.stream().sorted(Comparator.comparing(User::getId)).limit(10).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
        List<User> sortedUserList2 = userList.stream().limit(10).sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        compareUserList(userList, sortedUserList2);
        //答有区别，两操作的执行顺序是被保留的
    }

    @Test
    public void skipLimitTest(){
        //skip配合limit实现分页
        List<User> userList = genTestList(20);
        userList.sort(Comparator.comparing(User::getId));
        List<User> sortedUserList=userList.stream().skip(5).limit(5).collect(Collectors.toList());
        compareUserList(userList, sortedUserList);
    }

    @Test
    public void mapTest(){
        //使用map将user列表转换成String列表，记录name
        List<User> userList = genTestList(20);
        List<String> nameList=userList.stream().map(User::getName).collect(Collectors.toList());
        userList.forEach(printer);
        nameList.forEach(printer);
    }

    @Test
    public void flatMapTest(){
        //flatMap用于嵌套列表转换为一个流
        List<User> userList = genTestList(20);
        List<String> allEmailList=userList.stream().flatMap(u->u.getEmailList().stream()).collect(Collectors.toList());
        allEmailList.forEach(printer);
    }

    @Test
    public void matchTest(){
        //测试allMatch,noneMatch,anyMatch
        List<User> userList = genTestList(10);
        boolean allMatch=userList.stream().allMatch(u->u.getId()>30);
        System.out.println(allMatch);
        boolean anyMatch=userList.stream().anyMatch(user->user.getId()==3);
        System.out.println(anyMatch);
        boolean noneMatch=userList.stream().noneMatch(user->user.getId()>100);
        System.out.println(noneMatch);
        userList.forEach(printer);
    }

    @Test
    public void findFirstTest(){
        List<User> userList = genTestList(10);
        Optional<User> user=userList.stream().findFirst();
        user.ifPresent(printer);
        System.out.println("===");
        userList.forEach(printer);
    }

    @Test
    public void findAnyTest(){
        List<User> userList = genTestList(5);
        Stream<User> stream=userList.stream();
        Optional<User> user=stream.findAny();
        user.ifPresent(printer);
        System.out.println("===");
        userList.forEach(printer);
    }

    @Test
    public void minMaxTest(){
        List<User> userList = genTestList(5);
        Comparator<User> comparator=Comparator.comparing(User::getId);
        Optional<User> minUser=userList.stream().min(comparator);
        minUser.ifPresent(printer);
        Optional<User> maxUser=userList.stream().max(comparator);
        maxUser.ifPresent(printer);
        System.out.println("====");
        userList.forEach(printer);
    }

    @Test
    public void reduceTest(){
        //对所有id求和
        List<User> userList = genTestList(5);
        //第一种api,使用流中第一个元素作为首项，返回一个optional结果
        userList.stream().map(User::getId).reduce(Integer::sum).ifPresent(printer);
        //第二种api，使用给定的值作为首项，返回给定值类型的结果
        Integer idSum=userList.stream().map(User::getId).reduce(100,Integer::sum);
        System.out.println(idSum);
        //第三种api，使用给定的值作为首项，使用一个BiFunction将各项进行归并，返回归并结果，这个结果将作为参与下次运算的项
        //将每次归并结果使用BinaryOperator进行操作，并返回给定值类型的结果
        Integer idSum2=userList.stream().reduce(100,(i,user)->{
            System.out.println("i="+i+";userId="+user.getId());
            return user.getId()+i;
            },Integer::sum);
        System.out.println(idSum2);

    }
}
