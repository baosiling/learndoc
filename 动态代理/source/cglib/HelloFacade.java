package com.example.swaggertest.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 13:39
 */
public class HelloFacade implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(this.target.getClass());
        //通过字节码技术动态创建子类实例
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("-------插入前置代码-------");
        Object ret = methodProxy.invokeSuper(o, objects);
        System.out.println("-------插入后置代码-------");
        return ret;
    }
}