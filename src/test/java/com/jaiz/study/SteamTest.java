package com.jaiz.study;

import com.jaiz.study.bean.User;
import com.jaiz.study.utils.RandomString;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamTest {

    /**
     * 无限流测试
     */
    @Test
    public void unlimitedSteamTest(){
        System.out.println("使用generate生成无限流");
        Stream<Double> generateStream=Stream.generate(Math::random).limit(10);
        generateStream.forEach(System.out::println);

        System.out.println("使用iterate创建无限流");
        Stream<Integer>  iterateStream=Stream.iterate(1,a->a*2).limit(10);
        iterateStream.forEach(System.out::println);
    }

    /**
     * 生成随机测试List
     * @param limit
     * @return
     */
    private List<User> genTestList(int limit){
        Random r=new Random();
        RandomString rs=new RandomString();
        Supplier<User> userGenerator=()-> User.build(r.nextInt(50),rs.next(8));
        return Stream.generate(userGenerator).limit(limit).collect(Collectors.toList());
    }

    /**
     * user列表打印函数
     */
    private Consumer<User> printer=System.out::println;

    private void compareUserList(List<User> list1,List<User> list2){
        System.out.println("原始列表中共有"+list1.size()+"个user：");
        list1.forEach(printer);
        System.out.println("去重后共有"+list2.size()+"个user");
        list2.forEach(printer);
    }

    /**
     * 测试去重
     */
    @Test
    public void distinctTest(){
        var userList=genTestList(20);
        var distinctUserList=userList.stream().distinct().collect(Collectors.toList());
        compareUserList(userList,distinctUserList);
    }

    /**
     * 测试过滤
     */
    @Test
    public void filterTest(){
        var userList=genTestList(20);
        var filterUserList=userList.stream().filter(user->user.getId()<=25).collect(Collectors.toList());
        compareUserList(userList,filterUserList);
    }

    @Test
    public void sortedTest(){
        var userList=genTestList(20);
        // TODO comparing中传入的Comparator函数类型按理说应该不匹配才对
        var sortedUserList=userList.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        compareUserList(userList,sortedUserList);
    }

}
