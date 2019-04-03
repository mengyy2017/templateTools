package com.common.pub.pubVo;

@FunctionalInterface
public interface Fun<O, T> {
    void apply(O o, T value);
}
