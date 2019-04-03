package com.template.pub.commModel;

@FunctionalInterface
public interface Puts<T, E, O> {
    void apply(O o, T v1, E v2);
}
