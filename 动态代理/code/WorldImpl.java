package com.example.swaggertest.dynamic;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 12:28
 */
public class WorldImpl implements IWorld {
    @Override
    public void sayWorld() {
        System.out.println("world,hello!");
    }
}