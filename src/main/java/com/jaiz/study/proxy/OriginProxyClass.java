package com.jaiz.study.proxy;

import com.sun.management.VMOption;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OriginProxyClass implements InvocationHandler {

    private OriginClass target;

    public OriginProxyClass(OriginClass target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        boolean needProxy=method.getName().startsWith("withProxy");
        if(needProxy){
            System.out.println("代理要做的事情-前");
        }

        Object result=method.invoke(target,args);

        if (needProxy){
            System.out.println("代理要做的事情-后");
        }
        return result;
    }
}
