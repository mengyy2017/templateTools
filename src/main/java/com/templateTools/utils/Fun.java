package com.templateTools.utils;

@FunctionalInterface
public interface Fun<O, T> {
    void apply(O o, T value);
}
