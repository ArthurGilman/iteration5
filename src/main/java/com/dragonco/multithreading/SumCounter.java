package com.dragonco.multithreading;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class SumCounter extends RecursiveTask<Integer> {

    private int[] arr;

    public SumCounter(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length <= 2) {
            return Arrays.stream(arr).sum();
        }
        SumCounter firstHalf = new SumCounter(Arrays.copyOfRange(arr, 0, arr.length / 2));
        SumCounter secondHalf = new SumCounter(Arrays.copyOfRange(arr,  arr.length / 2, arr.length));
        firstHalf.fork();
        secondHalf.fork();
        return firstHalf.join() + secondHalf.join();
    }
}
