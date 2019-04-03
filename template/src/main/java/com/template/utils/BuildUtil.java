package com.template.utils;

import com.template.pub.commModel.*;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuildUtil {
//    public static void main(String[] args) {
//        String a = "a";
//        String b = "b";
//        int e = 2;
//        Car car = newAndSet0(Car::new, new String[]{a, b}, Car::setAaa, Car::setBbb);
//        Car car1 = newAndSet(Car::new, getVAndF(a, Car::setAaa), getVAndF(e, Car::setEee));
//    }

    public static <T, O> O oneConstr(Function<T, O> function, T t){
        return function.apply(t);
    }

    public static <O> O zeroConstr(Supplier supplier){
        return (O) supplier.get();
    }

    public static <O> O newAndSet0(Supplier<O> supplier, String[] valueArr , SSetter<O>...setter){
        O o = supplier.get();
        for(int i = 0; i < setter.length; i++){
            setter[i].apply(o, valueArr[i]);
        }
        return o;
    }

    public static <O> O newAndSet(Supplier<O> supplier, FunAndVal...funAndVals){
        O o = supplier.get();
        for (FunAndVal funAndVal: funAndVals) {
            funAndVal.apply(o);
        }
        return o;
    }

    public <O> O newAndSetNonStatic(Supplier<O> supplier, FunAndVal...funAndVals){
        O o = supplier.get();
        for (FunAndVal funAndVal: funAndVals) {
            funAndVal.apply(o);
        }
        return o;
    }

    public static <O> O setVals(O o, FunAndVal ...funAndVals){
        for (FunAndVal funAndVal: funAndVals) {
            funAndVal.apply(o);
        }
        return o;
    }

    public static <T, E, O> O newAndPuts0(Supplier<O> supplier, Puts<T, E, O> puts, KeyAndVal<T, E>...keyAndVals){
        O o = supplier.get();
        for (KeyAndVal<T, E> keyAndVal : keyAndVals) {
            puts.apply(o, keyAndVal.getV1(), keyAndVal.getV2());
        }
        return o;
    }

    public static <T, E, O> O newAndPuts1(Supplier<O> supplier, Puts<T, E, O> puts, LinkedList linkedList){
        O o = supplier.get();
        return putsValsLoop(o, puts, linkedList);
    }

    public static <T, E, O> O newAndPuts(Supplier<O> supplier, Puts<T, E, O> puts, Object ...objs){
        O o = supplier.get();
        LinkedList linkedList = Stream.of(objs).collect(Collectors.toCollection(LinkedList::new));

        return putsValsLoop(o, puts, linkedList);
    }

    public static <T, E, O> O putsVals(O o, Puts<T, E, O> puts, Object ...objs){

        LinkedList linkedList = Stream.of(objs).collect(Collectors.toCollection(LinkedList::new));

        return putsValsLoop(o, puts, linkedList);
    }

    public static <T, E, O> O putsValsLoop(O o, Puts<T, E, O> puts, LinkedList linkedList) {
        while (linkedList.size() > 0){
            T v1 = (T)linkedList.poll();
            E v2 = (E)linkedList.poll();
            puts.apply(o, v1, v2);
        }
        return o;
    }

    public static <O, T> FunAndVal<O, T> getVAndF(T value, Fun<O, T> fun){
        BiFunction<Fun<O, T>, T, FunAndVal<O, T>> biFunction = FunAndVal::new;
        return biFunction.apply(fun, value);
    }

}



