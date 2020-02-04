package com.example.swaggertest.dynamic;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 10:22
 */
public class HelloImpl implements IHello {
    @Override
    public void sayHello() {
        System.out.println("hello,world!");
    }
}