package com.chii.javatry.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Describe:
 * Author:  xujialin.
 * Date:    2016/9/14
 */
public class TestBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> testQueue = new ArrayBlockingQueue<Integer>(4);

        new Thread(new GetRunnable(testQueue)).start();
        Thread.sleep(5000);

        new Thread(new PutRunnable(testQueue)).start();

    }

}
class PutRunnable implements Runnable {
    ArrayBlockingQueue<Integer> testQueue;

    public PutRunnable(ArrayBlockingQueue<Integer> testQueue) {
        this.testQueue = testQueue;
    }

    public void run() {
        while (true) {
            try {
                testQueue.put(1);
                System.out.println("put a num");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("put method was interrupted");
            }
        }
    }
}
class GetRunnable implements Runnable {
    ArrayBlockingQueue<Integer> testQueue;

    public GetRunnable(ArrayBlockingQueue<Integer> testQueue) {
        this.testQueue = testQueue;
    }

    public void run() {
        while (true) {
            try {
                testQueue.poll(10, TimeUnit.SECONDS);
                System.out.println("take a num");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("poll method was interrupted");
            }
        }
    }
}

class BlockQueueTest {
    ArrayBlockingQueue<Integer> testQueue = new ArrayBlockingQueue<Integer>(4);

    public int poll() throws InterruptedException {
        return testQueue.poll(10, TimeUnit.SECONDS);
    }

    public void put() throws InterruptedException {
        testQueue.put(1);
    }
}