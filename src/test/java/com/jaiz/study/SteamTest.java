package com.jaiz.study;

import com.jaiz.study.bean.User;
import com.jaiz.study.utils.RandomString;
import org.junit.Test;

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
     * 测试去重
     */
    @Test
    public void distinctTest(){
        Consumer<User> printer=System.out::println;
        Random r=new Random();
        RandomString rs=new RandomString();
        Supplier<User> userGenerator=()-> User.build(r.nextInt(50),rs.next(8));
        var userList=Stream.generate(userGenerator).limit(20).collect(Collectors.toList());
        System.out.println("原始列表中共有"+userList.size()+"个user：");
        userList.forEach(printer);
        var distinctUserList=userList.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后共有"+distinctUserList.size()+"个user");
        distinctUserList.forEach(printer);
    }

}
