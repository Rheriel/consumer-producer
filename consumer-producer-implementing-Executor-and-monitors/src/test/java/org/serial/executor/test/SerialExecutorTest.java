package org.serial.executor.test;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.serial.executor.SerialExecutor;

/**
 * Test harness for SerialExecutor class.
 * 
 * @author Leo Gutierrez.
 *
 */
public class SerialExecutorTest {

	@Test
	public void testSerialExecutorWithThreadPoolSize() throws InterruptedException {
		
		final AtomicInteger threadCount = new AtomicInteger();

		final int poolSize = 4;
		final Executor serialExecutor = new SerialExecutor(poolSize);

		final Runnable fakeRunnable = new Runnable() {

			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {

				final int noOfThred = threadCount.incrementAndGet();

				System.out.println("Thread number: " + noOfThred);

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

		// simulate other processing
		Thread.sleep(3000);

		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		// simulate other processing
		Thread.sleep(3000);

		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);

		// wait for executor.
		Thread.sleep(3000);

		Assert.assertEquals(12, threadCount.get());
	}

	@Test
	public void testSerialExecutor() throws InterruptedException {
		
		final AtomicInteger threadCount = new AtomicInteger();
		
		final Executor serialExecutor = new SerialExecutor();
		
		final Runnable fakeRunnable = new Runnable() {
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				
				final int noOfThred = threadCount.incrementAndGet();
				
				System.out.println("Thread number: " + noOfThred);
				
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
		
		// simulate other processing
		Thread.sleep(3000);
		
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		// simulate other processing
		Thread.sleep(14000);
		
		serialExecutor.execute(fakeRunnable);
		serialExecutor.execute(fakeRunnable);
		
		// wait for executor.
		Thread.sleep(5000);
		
		Assert.assertEquals(10, threadCount.get());
		
	}
	
}
