package org.consumer.producer.semaphores.test;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.consumer.producer.semphores.SerialExecutor;
import org.junit.Assert;
import org.junit.Test;

public class SerialExecutorTest {

	@Test
	public void testExecutorCapacity() {
		
		final int executorThreadPool = 4;

		final SerialExecutor serialExecutor = new SerialExecutor(executorThreadPool);

		final AtomicInteger runnableExecutedCound = new AtomicInteger();

		final Runnable fakeRunnable = new Runnable() {

			public void run() {

				final int runnableCount = runnableExecutedCound.incrementAndGet();

				System.out.println("Executing runnable " + runnableCount);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		Assert.assertEquals(4, serialExecutor.retrieveThreadsInPool());
		
		try {
			Thread.sleep(13000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(0, serialExecutor.retrieveThreadsInPool());
		
	}
	
	@Test
	public void testExecutor() {
		
		final int executorThreadPool = 4;
		
		final Executor serialExecutor = new SerialExecutor(executorThreadPool);
		
		final AtomicInteger runnableExecutedCound = new AtomicInteger();
	
		final Runnable fakeRunnable = new Runnable() {
			
			public void run() {
				
				final int runnableCount = runnableExecutedCound.incrementAndGet();
				
				System.out.println("Executing runnable " + runnableCount);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		// Simulate other processing
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		// Wait for the executor
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(9, runnableExecutedCound.get());
		
	}
	
}
