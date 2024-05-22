package com.dragonco.iteration5;

import com.dragonco.multithreading.CollectionProcessing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultithreadingTest {

	private final CollectionProcessing t;

	@Autowired
	public MultithreadingTest(CollectionProcessing t) {
		this.t = t;
	}

	@Test
	public void newThreadsTest()
	{
		t.processingWithCreatingNewThreads();
	}

	@Test
	public void fixedThreadPoolTest() {
		t.processingWithFixedThreadPool();
	}

	@Test
	public void forkJoinPoolTest() {
		CollectionProcessing.withForkJoinPool(10000);
	}

	@Test
	public void asyncTest() {
		for (int i = 0; i < 100; i++) {
			t.makeAsync();
		}
	}

}
