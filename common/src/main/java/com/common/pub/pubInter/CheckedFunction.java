package com.common.pub.pubInter;

@FunctionalInterface
public interface CheckedFunction<T, R>{
    R apply(T t) throws Exception;
}