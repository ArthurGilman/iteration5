package com.dragonco.concurrency;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class IncrementNumber {
    private int num = 0;

    public void increment() {
        num++;
    }
}
