package com.jaiz.study;

import com.jaiz.study.bean.User;
import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void xxTest(){
        Optional<User> opt=Optional.of(User.build(1,"test"));
        String s="test";
        System.out.println(s.intern());
    }
}
