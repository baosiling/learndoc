package com.example.swaggertest.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 10:22
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("----------插入前置代码---------");
        Object ret = method.invoke(target, args);
        System.out.println("----------插入后置代码---------");
        return ret;
    }
}