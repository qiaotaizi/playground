package com.jaiz.study;

import java.util.List;

/**
 * 一个函数式接口，java8特性
 */
@FunctionalInterface //这个标注可加可不加，加上之后会检查编译错误
public interface MyFunction {

    /**
     * 函数式接口有且仅有一个抽象方法
     */
    void printAString(String content);

    /**
     * 函数式接口中可以编写非抽象静态方法
     * java8特性
     *
     * @return
     */
    static String getClassName() {
        return _getClassName();
    }

    /**
     * 函数式接口中可以编写多个default实例方法
     * default方法可以被子类重写
     * java8特性
     *
     * @return
     */
    default String getFunctionName() {
        return "my";
    }

    default String getFunctionName2() {
        return _getFunctionName2();
    }

    /**
     * 函数式接口中可以声明继承自Object类中的任何方法的重写
     * 但是没有实际意义
     *
     * @return
     */
    @Override
    String toString();

    /**
     * 接口中可以定义私有方法和私有静态方法，java9新特性
     * 私有方法允许在接口内部将一些逻辑进行抽象
     * 这些方法可以在本接口的static或default方法中被调用
     *
     */
    private String _getFunctionName2() {
        return "my2";
    }

    private static String _getClassName() {
        return "MyFunction";
    }
}
