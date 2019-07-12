package com.common.util;

import com.common.pub.pubInter.CheckedBiConsumer;
import com.common.pub.pubInter.CheckedConsumer;
import com.common.pub.pubInter.CheckedFunction;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CheckedUtil {

    public <T> Consumer<T> acceptOrThrow(CheckedConsumer<T> checkedConsumer){
        return t -> {
            try {
                checkedConsumer.accept(t);
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        };
    }

    public <T, U> BiConsumer<T, U> biAcceptOrThrow(CheckedBiConsumer<T, U> checkedBiConsumer){
        return (t, u) -> {
            try {
                checkedBiConsumer.accept(t, u);
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        };
    }

    public <T, R> Function<T, R> applyOrThrow(CheckedFunction<T, R> checkedFuntion){
        return t -> {
            try {
                return checkedFuntion.apply(t);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        };
    }

}
