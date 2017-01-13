package com.kuyun.test.pipe;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * java 中 通过queue来实现fifo 通过文件系统中的fifo也能快速实现 并解耦
 * <p>
 * mkfifo /Users/xuwuqiang/Downloads/pipe来创建这个文件
 * ls > /Users/xuwuqiang/Downloads/pipe
 * <p>
 * <p>
 * Created by xuwuqiang on 2017/1/11.
 */
public class TestPipe {
    public static void main(String[] args) throws Exception {
        pipeTest();
    }

    public static void pipeFile() throws IOException {
        File fifoPipeFile = new File("/Users/xuwuqiang/Downloads/pipe");//mkfifo /Users/xuwuqiang/Downloads/pipe来创建这个文件
        BufferedReader br = new BufferedReader(new FileReader(fifoPipeFile.getAbsolutePath()));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void pipeTest() throws Exception {
        Pipe pipe = Pipe.open();
        final Pipe.SinkChannel psic = pipe.sink();
        final Pipe.SourceChannel psoc = pipe.source();

        Thread tPwriter = new Thread() {
            public void run() {
                try {
                    // 创建一个线程，利用管道的写入口Pipe.SinkChannel类型的psic往管道里写入指定ByteBuffer的内容
                    psic.write(ByteBuffer.wrap("Hello,Pipe!"
                            .getBytes("utf-16BE")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread tPreader = new Thread() {
            public void run() {
                int bbufferSize = 1024 * 2;
                ByteBuffer bbuffer = ByteBuffer.allocate(bbufferSize);
                try {
                    // 创建一个线程，利用管道的读入口Pipe.SourceChannel类型的psoc将管道里内容读到指定的ByteBuffer中
                    psoc.read(bbuffer);
                    System.out.println("Content:" + ByteBufferToString(bbuffer));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        tPwriter.start();
        tPreader.start();


    }


    public static String ByteBufferToString(ByteBuffer content) {
        if (content == null || content.limit() <= 0
                || (content.limit() == content.remaining())) {
            System.out.println("ByteBufferToString()传入的ByteBuffer不存在或内容为空！");
            return null;
        }
        int contentSize = content.limit() - content.remaining();
        StringBuffer resultStr = new StringBuffer();
        for (int i = 0; i < contentSize; i += 2) {
            resultStr.append(content.getChar(i));
        }
        return resultStr.toString();
    }

}
