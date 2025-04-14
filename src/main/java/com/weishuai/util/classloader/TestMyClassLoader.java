package com.weishuai.util.classloader;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMyClassLoader {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //自定义类加载器的加载路径
        HeroClassLoader heroClassLoader = new HeroClassLoader("/Users/ws001/Desktop");
        Class<?> aClass = heroClassLoader.loadClass("com.weishuai.util.classloader.Test");
        if (ObjectUtils.isNotEmpty(aClass)) {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("say", null);
            method.invoke(obj, null);
            System.out.println(aClass.getClassLoader().toString());
        }

    }
}
