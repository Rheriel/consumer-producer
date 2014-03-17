package org.consumer.producer.semaphores.test;

import org.consumer.producer.semphores.Semaphore;
import org.junit.Assert;
import org.junit.Test;

public class SemaphoreTest {

	@Test
	public void testSemaphoreBound() throws InterruptedException {
		
		final int semaphoreBound = 1;
		
		final Semaphore semaphore = new Semaphore(semaphoreBound);
		
		final Thread fakeProducer = new Thread(new Runnable() {
			
			public void run() {
				
				System.out.println("Producer 1: taking the lock");
				
				semaphore.take();
				
				System.out.println("Producer 1: adquired the lock");
				
				try {
					
					Thread.sleep(5000);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
					
				} finally {
					
					System.out.println("Producer 1: releasing the lock");
					
					semaphore.release();
					
				}
				
			}
		});
		
		fakeProducer.start();
		
		final Thread secondFakeProducer = new Thread(new Runnable() {

			public void run() {

				System.out.println("Producer 2: taking the lock");
				
				semaphore.take();
				
				try {
					
					System.out.println("Producer 2: adquired the lock");
					
					Thread.sleep(5000);

				} catch (InterruptedException e) {

					e.printStackTrace();

				} finally {

					System.out.println("Producer 2: releasing the lock");
					
					semaphore.release();

				}

			}
		});

		secondFakeProducer.start();
		
		Thread.sleep(2000);
		
		Assert.assertEquals(1, semaphore.getSignalCount());
		
		Thread.sleep(10000);
		
		Assert.assertEquals(0, semaphore.getSignalCount());
		
	}
	
}
