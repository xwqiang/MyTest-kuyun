package com.kuyun.test.retry;

import java.util.Random;

/**
 * Created by xuwuqiang on 2017/1/19.
 */
public class Test {
    public static void main(String[] args) {
        boolean ok = AsyncLoopTaskContext.create(new int[]{1000,2000,3000},new AsyncBussinessCallBack(){
            @Override
            public void executeBussiness() {
                if(new Random().nextInt() % 3 == 0) {
                    System.out.println("aaa");
                }else{
                    Integer.parseInt("343j");
                }
            }
            @Override
            public void onException() {
                System.out.println("error");

            }
        }).schedule();
        System.out.println(ok);
    }
}
