package com.yuntongxun.mwork.test;


import com.yuntongxun.mwork.flow.InfoFlow;
import org.reflections.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: mwork
 * @description:
 * @author: liugang
 * @create: 2020-06-02 09:53
 **/
public class ClassNameTest {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Class clazz = InfoFlow.class;

        Set<Method> methods = ReflectionUtils.getMethods(clazz, ReflectionUtils.withName("process"));
        methods =  methods.stream().filter( method -> !method.getParameterTypes()[0].isInterface()).collect(Collectors.toSet());
        for( Method method : methods ){
            System.out.println( method.getName() );
            System.out.println( method.getParameterTypes()[0].getName());
        }
    }
}
