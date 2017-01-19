package com.kuyun.test.retry;

/**
 * Created by xuwuqiang on 2017/1/19.
 */
public abstract class AsyncBussinessCallBack {
    public abstract void executeBussiness();
    public abstract void onException();
}
