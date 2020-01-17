package com.jaiz.study;

import com.jaiz.study.proxy.OriginClass;
import com.jaiz.study.proxy.OriginProxyClass;
import com.jaiz.study.proxy.ProxyClass;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    public void proxyTest(){
        OriginClass originClass=new OriginClass();
        InvocationHandler handler=new OriginProxyClass(originClass);
        Object proxy=Proxy.newProxyInstance(OriginClass.class.getClassLoader(),OriginClass.class.getInterfaces(),handler);
        ProxyClass origin=(ProxyClass)proxy;


        origin.withProxyA();
        System.out.println("+++");
        origin.withProxyB();
        System.out.println("+++");
        origin.withoutProxyC();
        System.out.println("+++");
        origin.withoutProxyD();

    }


}
