package com.example.swaggertest.dynamic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: wangzhx
 * @create: 2020-02-04 10:25
 */
public class MyProxyTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //===============第一种================
        //设置生成$Proxy0的class文件的标志
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        //1.获取动态代理类，因为设置了上面的标志，这一步会生成$Proxy0.class文件
        Class proxyClazz = Proxy.getProxyClass(IHello.class.getClassLoader(), IHello.class);
        //2.获取动态代理类的构造函数，这里传入InvocationHandler.class是为了获取以InvocationHandler类型为参数的构造函数
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        //3.通过构造函数创建动态代理对象（类实例）,并将自定义的invocationHandler实例作为构造参数传入
        IHello hello1 = (IHello) constructor.newInstance(new MyInvocationHandler(new HelloImpl()));
        System.out.println(hello1.getClass());
        //4.通过代理对象调用目标方法
        hello1.sayHello();

        //=================第二种===================
        /**
         * Proxy类中还有个将2~4步骤封装好的简便方法来创建动态代理对象，
         *其方法签名为：newProxyInstance(ClassLoader loader,Class<?>[] instance, InvocationHandler h)
         */
        IHello hello2 = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new MyInvocationHandler(new HelloImpl()));
        System.out.println(hello1.getClass());
        hello2.sayHello();

        IWorld world = (IWorld) Proxy.newProxyInstance(IWorld.class.getClassLoader(), new Class[]{IWorld.class}, new MyInvocationHandler(new WorldImpl()));
        System.out.println(world.getClass());
        world.sayWorld();

    }
}