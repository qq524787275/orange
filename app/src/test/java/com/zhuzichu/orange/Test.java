package com.zhuzichu.orange;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class Test {
    @org.junit.Test
    void test(){
        Flowable<Integer> just1 = Flowable.just(1);
        Flowable<Integer> just2 = Flowable.just(2);
        Flowable.zip(just1, just2, new BiFunction<Integer, Integer, Object>() {
            @Override
            public Object apply(Integer integer, Integer integer2) throws Exception {
                return null;
            }
        })
        .subscribe();
    }

    private int update(int a, int b) {
        return a+b;
    }
}
