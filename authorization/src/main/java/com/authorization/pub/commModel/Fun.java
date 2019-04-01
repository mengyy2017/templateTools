package com.authorization.pub.commModel;

@FunctionalInterface
public interface Fun<O, T> {
    void apply(O o, T value);
}
