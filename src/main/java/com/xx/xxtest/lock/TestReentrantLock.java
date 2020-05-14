package com.xx.xxtest.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuxiang
 * 2020/5/14
 */
public class TestReentrantLock {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        TestReentrantLock test = new TestReentrantLock();
        new Thread(test::addOneThenAddTwo).start();
        new Thread(test::addOneThenAddTwo).start();
    }

    public void addOneThenAddTwo() {
        lock.lock();
        count++;
        // 这里体现了可重入锁的意思，addTwo 里面也可以用 lock 进行加锁
        addTwo();
        System.out.println(count);
        lock.unlock();
    }

    public void addTwo() {
        lock.lock();
        count = count + 2;
        lock.unlock();
    }
}
