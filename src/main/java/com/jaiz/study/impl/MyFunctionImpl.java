package com.jaiz.study.impl;

import com.jaiz.study.MyFunction;

public class MyFunctionImpl implements MyFunction {

    @Override
    public void printAString(String content) {
        System.out.println("content is: "+content);
    }
}
