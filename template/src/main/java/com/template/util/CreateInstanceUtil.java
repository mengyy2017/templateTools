package com.template.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CreateInstanceUtil {

    <T, R, U, V> void createInstance(T constructor, R method, U value, V v, Function<T, R> function, Function<V, T> before){

        function.compose(before);

        function.apply(constructor);
    }

    <T, U, R> void create1(Supplier<U> supplier, List<String> value, Function<T, R> function){
        U u = supplier.get();

//        function.compose(value, u);

//        value.forEach(consumer);

    }

}
