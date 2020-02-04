package com.example.swaggertest.dynamic.cglib;


import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 13:38
 */
public class MyProxyTest {
    public static void main(String[] args) {
        //生成代理类源码 生成到当前目录
        System.setProperty( DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "." );
        HelloFacade helloFacade = new HelloFacade();
        //通过生成子类的方式创建代理类
        HelloService helloService = (HelloService) helloFacade.getInstance(new HelloService());
        System.out.println(helloService.getClass());
        helloService.sayHello();
    }
}