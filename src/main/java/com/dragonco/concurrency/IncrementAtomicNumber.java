package com.dragonco.concurrency;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Component
public class IncrementAtomicNumber {
    private AtomicInteger num;

    public IncrementAtomicNumber() {
        this.num = new AtomicInteger(0);
    }

    public void increment() {
        num.incrementAndGet();
    }
}
