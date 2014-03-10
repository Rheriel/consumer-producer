package org.simple.executor.test;

import java.lang.Thread.State;

import org.consumer.producer.example.Message;
import org.consumer.producer.example.Queue;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test harness for Queue class.
 * 
 * @author Leo Gutierrez
 *
 */
public class QueueTest {

	/**
	 * Tests Queue should not accept more to consume than its capacity.
	 * 
	 * @throws InterruptedException Not expected exception and must cause to 
	 *                              fail the test.
	 */
	@Test
	public void testQueueFullCapacity() throws InterruptedException {
		
		int queueCapacity = 4;
		 
		final Queue queue = new Queue(queueCapacity);
		
		final Thread fakeProducer = generateFakeProducer(queue, 3);
		
		fakeProducer.start();
		
		fakeProducer.join();

		// Queue size should be 2 at this point.
		Assert.assertEquals(3, queue.size());
		
		final Thread fakeSecondProducer = generateFakeProducer(queue, 2);
		
		fakeSecondProducer.start();
		
		// Didn't join() second producer because we expect a full queue,  
		// thus, this test will be waiting for the second producer thread that 
		// is waiting for space in by the queue monitor.
		// We expect second producer to become in WAITING state due to a full queue.
		final int currentTry = 1;
		final int numberOfTries = 4;
		verifyIfThreadIsWaiting(fakeSecondProducer, currentTry, numberOfTries);
		
		// Queue size should be full (4) at this point.
		Assert.assertEquals(queueCapacity, queue.size());
		
		// Free second producer and finish.
		queue.pull();
		
	} 
	
	/**
	 * Generates a fake producer that will send a number of messages specified 
	 * as parameter.
	 *  
	 * @param queue
	 * @param numberOfMessageToSend
	 * @return
	 */
	private Thread generateFakeProducer(final Queue queue, 
			final int numberOfMessageToSend) {

		final Thread fakeProducer = new Thread(new Runnable() {

			@Override
			public void run() {

				final Runnable fakeMessage = new Message("foo");

				for (int messageIndex = 0; 
						messageIndex < numberOfMessageToSend; messageIndex++) {
					queue.put(fakeMessage);
				}
				
			}
		});
		
		return fakeProducer;
		
	}
	
	/**
	 * Verifies if the given thread is in WAITING state. Fails if the state is 
	 * in TERMINATED state of the number of tries have been reached. 
	 * 
	 * @param thread
	 * @param currentTry
	 * @param numberOfTries
	 * @throws InterruptedException
	 */
	private void verifyIfThreadIsWaiting(final Thread thread, 
			int currentTry, final int numberOfTries) throws InterruptedException {
		
		if ((currentTry >= numberOfTries) 
				|| (thread.getState() == State.TERMINATED)) {
			
			Assert.assertTrue(false);
			
		} else {
			
			if (thread.getState() == State.WAITING) {
				
				Assert.assertTrue(true);
				
			} else {
				
				Thread.sleep(3000);
				
				verifyIfThreadIsWaiting(thread, ++currentTry, numberOfTries);
				
			}
			
		}
		
	}
	
	/**
	 * Test that once the consumer processes all the messages, the queue must 
	 * be empty.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testConsumeAllMessage() throws InterruptedException {
		
		final int queueCapacity = 5;
		
		final Queue queue = new Queue(queueCapacity);
		
		final Thread fakeProducer = generateFakeProducer(queue, 6);
		
		fakeProducer.start();
		
		final Thread fakeConsumer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (queue.size() > 0) {
					
					final Thread consumeMessage = new Thread(queue.pull());
					
					consumeMessage.start();
					
					try {
						
						consumeMessage.join();
						
					} catch (InterruptedException e) {
						
						Assert.fail(e.getMessage());
						
					}
					
				}
				
			}
		});
		
		fakeConsumer.start();
		
		final Thread fakeSecondProducer = generateFakeProducer(queue, 3);

		fakeSecondProducer.start();
		
		// Wait until consumer is done, the queue sould be empty then
		fakeConsumer.join();
		
		Assert.assertEquals(0, queue.size());
		
	}
	
}
