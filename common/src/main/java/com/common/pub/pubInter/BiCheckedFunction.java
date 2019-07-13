package com.common.pub.pubInter;

@FunctionalInterface
public interface BiCheckedFunction<T, U, R> {
    R biApply(T t, U u) throws Exception;
}
