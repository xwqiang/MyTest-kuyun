package com.kuyun.test.retry;

/**
 * Created by xuwuqiang on 2017/1/19.
 */
public class AsyncLoopTaskContext {
    private AsyncBussinessCallBack callBack;
    private int[] delay;
    private boolean ok ;
    private AsyncLoopTaskContext(int[] delay,AsyncBussinessCallBack callBack){
        this.delay = delay;
        this.callBack = callBack;
    }
    public static AsyncLoopTaskContext create(int[] delay, AsyncBussinessCallBack callback){
        return new AsyncLoopTaskContext(delay,callback);
    }
    public boolean schedule(){
        for(int i : delay){
            try {
                callBack.executeBussiness();
                return true;
            }catch (Exception e){
                callBack.onException();
                try { Thread.sleep(i);} catch (InterruptedException e1) {e1.printStackTrace();}
            }
        }
        return false;
    }

}
