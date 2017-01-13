package com.kuyun.test.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 高并发下java的Date会处问题 ，非现成安全。
 * Created by xuwuqiang on 2017/1/13.
 */
public class DateTest {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static Logger LOG = LoggerFactory.getLogger("cardsinfo");
    static ExecutorService executorService = Executors.newFixedThreadPool(500);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000000; i++) {
                        try {
                            LOG.info(sdf.parse("2014-01-01 00:00:00").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000000);
    }

}

