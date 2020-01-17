package com.jaiz.study.proxy;


public class OriginClass implements ProxyClass {

    public void withProxyA(){
        System.out.println("A");
    }

    public void withProxyB(){
        System.out.println("B");
    }

    public void withoutProxyC(){
        System.out.println("C");
    }

    public void withoutProxyD(){
        System.out.println("D");
        withProxyA();
    }

}
