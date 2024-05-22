package com.dragonco.iteration5;

import com.dragonco.concurrency.IncrementAtomicNumber;
import com.dragonco.concurrency.IncrementNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.ReentrantLock;

@SpringBootTest
public class ConcurrencyTest {

    private final IncrementNumber number;
    private final IncrementAtomicNumber atomic;

    @Autowired
    public ConcurrencyTest(IncrementNumber number, IncrementAtomicNumber atomic) {
        this.number = number;
        this.atomic = atomic;
    }

    @Test
    public void concurrentIncrementTest() throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                number.increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                number.increment();
            }
        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("First Thread state - " + one.getState());
        System.out.println("Second Thread state - " + two.getState());
        System.out.println(number.getNum());
    }

    @Test
    public void synchronizedIncrementTest() throws InterruptedException {
        Object lock = new Object();

        Thread one = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10000; i++) {
                    number.increment();
                }
            }
        });

        Thread two = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10000; i++) {
                    number.increment();
                }
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("First Thread state - " + one.getState());
        System.out.println("Second Thread state - " + two.getState());
        System.out.println(number.getNum());
    }

    @Test
    public void atomicIncrementTest() throws InterruptedException {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                atomic.increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                atomic.increment();
            }
        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("First Thread state - " + one.getState());
        System.out.println("Second Thread state - " + two.getState());
        System.out.println(atomic.getNum());
    }

    @Test
    public void lockIncrementTest() {
        ReentrantLock l = new ReentrantLock();
        Thread one = new Thread(() -> {
            try {
                l.lock();
                for (int i = 0; i < 100000; i++) {
                    number.increment();
                }
            } catch (Exception ignored) {}
            finally {
                l.unlock();
            }
        });

        Thread two = new Thread(() -> {
            try {
                l.lock();
                for (int i = 0; i < 100000; i++) {
                    number.increment();
                }
            } catch (Exception ignored) {}
            finally {
                l.unlock();
            }
        });

        one.start();
        two.start();
        while (one.isAlive() && two.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
        System.out.println("First Thread state - " + one.getState());
        System.out.println("Second Thread state - " + two.getState());
        System.out.println(number.getNum());
    }
}
