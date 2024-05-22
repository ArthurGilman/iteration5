package com.dragonco.concurrency;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IncrementNumber num = new IncrementNumber();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                num.increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                num.increment();
            }
        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("First Thread state - " + one.getState());
        System.out.println("Second Thread state - " + two.getState());
        System.out.println(num.getNum());
    }
}
