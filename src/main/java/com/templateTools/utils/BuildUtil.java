package com.templateTools.utils;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BuildUtil {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        int e = 2;
//        Car car = newAndSet0(Car::new, new String[]{a, b}, Car::setAaa, Car::setBbb);
//        Car car1 = newAndSet(Car::new, buildValAndFun(a, Car::setAaa), buildValAndFun(e, Car::setEee));
    }

    public static <O> O newAndSet0(Supplier<O> supplier, String[] valueArr , SSetter<O> ...setter){
        O o = supplier.get();
        for(int i = 0; i < setter.length; i++){
            setter[i].apply(o, valueArr[i]);
        }
        return o;
    }

    public static <O> O newAndSet(Supplier<O> supplier, FunAndVal ...funAndVals){
        O o = supplier.get();
        for (FunAndVal funAndVal: funAndVals) {
            funAndVal.apply(o);
        }
        return o;
    }

    public static <O, T> FunAndVal<O, T> buildValAndFun(T value, Fun<O, T> fun){
        BiFunction<Fun<O, T>, T, FunAndVal<O, T>> biFunction = FunAndVal::new;
        return biFunction.apply(fun, value);
    }

}

class FunAndVal<O, T>{

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

@FunctionalInterface
interface Fun<O, T> {
    void apply(O o, T value);
}

@FunctionalInterface
interface SSetter<O> extends Fun<O, String> {
}

