package com.template.pub.commModel;

public class KeyAndVal<T, E>{
    public T v1;

    public E v2;

    public KeyAndVal(T v1, E v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public T getV1() {
        return v1;
    }

    public void setV1(T v1) {
        this.v1 = v1;
    }

    public E getV2() {
        return v2;
    }

    public void setV2(E v2) {
        this.v2 = v2;
    }

}
