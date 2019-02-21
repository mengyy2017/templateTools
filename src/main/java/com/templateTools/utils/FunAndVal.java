package com.templateTools.utils;

public class FunAndVal<O, T>{

    private T value;

    private Fun<O, T> fun;

    public FunAndVal(Fun<O, T> fun, T value) {
        this.value = value;
        this.fun = fun;
    }

    public void apply(O o){
        this.fun.apply(o, this.value);
    }

}
