package com.example.webflux.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.LongStream.range;

/**
 * @author Yuriy Tumakha.
 */
public class ConcurrentRun {

  private Runnable runnable;

  public ConcurrentRun(Runnable runnable) {
    this.runnable = runnable;
  }

  public void run(int times) {
    ExecutorService executor = Executors.newFixedThreadPool(50);
    range(0, times).forEach(t ->
        executor.submit(runnable)
    );
    executor.shutdown();
    try {
      executor.awaitTermination(15, SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
