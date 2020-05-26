package com.yuntongxun.mwork;

import org.reflections.Reflections;
import java.lang.annotation.Annotation;

/**
 * @program: mwork
 * @description: 扫描org.springframework 包下所有 注解
 * @author: liugang
 * @create: 2020-05-22 10:10
 **/
public class ScanAnnotationRunner {

   public static void main(String[] args) {
        new Reflections("org.springframework")
                .getSubTypesOf(Annotation.class)
                .stream()
                .map(clazz->clazz.getName())
                .sorted()
                .forEach(System.out::println);
    }
}
