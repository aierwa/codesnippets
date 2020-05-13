package com.xx.xxtest.guava;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @author xuxiang
 * 2020/5/13
 */
public class TestStopwatch {
    public static void main(String[] args) throws InterruptedException {
        // start immediately
        Stopwatch stopwatch = Stopwatch.createStarted();

        TimeUnit.SECONDS.sleep(1);
        // stopwatch.toString() return suitable compression.
        System.out.println("point 1 elapsed: " + stopwatch);

        TimeUnit.SECONDS.sleep(1);
        System.out.println("point 2 elapsed: " + stopwatch);

        TimeUnit.SECONDS.sleep(1);
        stopwatch.stop();
        System.out.println("total elapsed: " + stopwatch);

        // Also can call start() to continue
        // or reset() to reset elapsedNanos to 0
    }
}
