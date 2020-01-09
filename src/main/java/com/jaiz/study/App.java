package com.jaiz.study;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<String> strs = Arrays.asList("hello", "functional", "interface");
        strs.forEach(System.out::println);
    }
}
