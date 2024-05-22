package com.dragonco.multithreading;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class CollectionProcessing {
    public final List<Integer> list;
    public CollectionProcessing(@Value("${size}") Long size) {
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i + i * 2);
        }
    }

    public void processingWithCreatingNewThreads() {
        Thread sumWorker = new Thread(() -> {
            long sum = 0;
            for (Integer num : list) {
                sum += num;
            }
            System.out.println("Sum: " + sum);
        });

        Thread avgWorker = new Thread(() -> {
            long avg = 0;
            long sum = 0;
            for (Integer num : list) {
                sum += num;
            }
            avg = sum / list.size();
            System.out.println("Average: " + avg);
        });

        sumWorker.start();
        avgWorker.start();
    }

    public void processingWithFixedThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();

        futures.add(executor.submit(() -> {
            long sum = 0;
            for (Integer num : list) {
                sum += num;
            }
            return "Sum: " + sum;
        }));

        futures.add(executor.submit(() -> {
            long sum = 0;
            long avg;

            for (Integer num : list) {
                sum += num;
            }
            avg = sum / list.size();
            return "Average: " + avg;
        }));

        futures.add(executor.submit(() -> {
            int countEvenNumbers = 0;
            for (Integer num : list) {
                if (num % 2 == 0) {
                    countEvenNumbers++;
                }
            }
            return "Count even numbers: " + countEvenNumbers;
        }));
        executor.shutdown();

        try {
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void withForkJoinPool(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + i * 2;
        }
        SumCounter counter = new SumCounter(arr);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("Sum: " + forkJoinPool.invoke(counter));
    }

    @Async
    public void makeAsync() {
        System.out.println("Save some info from thread - " + Thread.currentThread().getName());
    }
}
