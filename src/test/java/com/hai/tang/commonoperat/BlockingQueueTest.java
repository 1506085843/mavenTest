package com.hai.tang.commonoperat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@DisplayName("阻塞队列测试类")
public class BlockingQueueTest {

    @Test
    @DisplayName("ArrayBlockingQueue 阻塞队列测试")
    public void arrayBlockingQueue() {
        //创建一个容量大小是3的阻塞队列
        BlockingQueue<Integer> p = new ArrayBlockingQueue<>(3);
        //开一个线程作为生产者
        new Thread(() -> {
            try {
                p.put(0);
                p.put(2);
                p.put(5);
                p.put(6);
                p.put(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //开一个线程作为消费者
        new Thread(() -> {
            try {
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    @DisplayName("LinkedBlockingQueue 阻塞队列测试")
    public void linkedBlockingQueue() {
        //创建LinkedBlockingQueue队列时即可以指定队列初始大小，也可以不指定，如果不指定，容量等于Integer.MAX_VALUE就是无界限队列
        BlockingQueue<Integer> p = new LinkedBlockingQueue<>();
        //开一个线程作为生产者
        new Thread(() -> {
            try {
                p.put(0);
                p.put(2);
                p.put(5);
                p.put(6);
                p.put(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //开一个线程作为消费者
        new Thread(() -> {
            try {
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
                System.out.println(p.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
