package com.jaiz.study;

import com.jaiz.study.bean.Person;
import com.jaiz.study.bean.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ObjectsTest {


    @Test
    public void equalTest(){
        String s1="hello";
        String s2=new String("hello");
        String s3="hello";
        System.out.println(s1==s3);//true 常量池
        System.out.println(s1==s2);//false new String不会服用常量池对象
        System.out.println(Objects.equals(s1, s2));//true 内置非空判断与equals方法

        System.out.println(Objects.deepEquals(s1, s3));//true
        boolean de=Objects.deepEquals(new String[]{"hello"," ","world","!"},new String[]{"hello"," ","world","!"});//true
        System.out.println(de);
    }

    @Test
    public void hashCodeTest(){
        Person p=new Person();
        System.out.println(p);
        System.out.println(Integer.toHexString(p.hashCode()));

        Person p2=new Person();
        System.out.println(p2);
        System.out.println(Integer.toHexString(p2.hashCode()));

        Object o=new Object();
        System.out.println(o);
        System.out.println(o.hashCode());
        System.out.println(Objects.hashCode(o));//与上一行得到相同结果
        System.out.println(Objects.hash(o));//与上一行得到的结果不同
    }

    @Test
    public void compareTest(){
        User u1=User.build(1,"test1");
        User u2=User.build(2,"test2");

        Comparator<User> c=Comparator.comparing(User::getId);

        int result=Objects.compare(u1,u2, c);//-1
        System.out.println(result);
        System.out.println(Objects.compare(u1,User.build(0,"test0"),c));//1
        System.out.println(Objects.compare(u1,User.build(1,"test1_"),c));//0
    }


}
