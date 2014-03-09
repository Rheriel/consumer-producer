package org.consumer.producer.example;

import java.util.Random;

/**
 * Producer example class. Produces 15 random messages trying to put them into 
 * the given Queue.
 * 
 * @author Leo Gutierrez.
 *
 */
public class Producer implements Runnable {

	/**
	 * Queue where producer is putting the messages and where the consumer 
	 * will be pulling from.
	 */
	private Queue queue;
	
	/**
	 * Producer name.
	 */
	private String producerName;
	
	/**
	 * The constructor.
	 * 
	 * @param queue Queue where producer is putting the messages and where 
	 *              the consumer will be pulling from. Must not be null.
	 * @param producerName Producer name.
	 * @throws IllegalArgumentException If the queue is null.
	 */
	public Producer(final Queue queue, final String producerName) 
			throws IllegalArgumentException {
		
		if (queue == null) {
			throw new IllegalArgumentException("Queue must not be null");
		}
	
		this.queue = queue;
		this.producerName = producerName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		putMessagesInQueue();
		
	}

	/**
	 * Produces 15 random message and tries to put them into the Queue. Once 
	 * the producer finishes to put all messages in the queue, this thread 
	 * terminates. It's all consumer's duty to consume all of these messages.
	 */
	private void putMessagesInQueue() {
		
		final int randomNumbertoGerenate = 15;
		
		for (int messageIndex = 0; 
				messageIndex <= randomNumbertoGerenate; messageIndex++) {
		
			final StringBuilder message = new StringBuilder();
			message.append("Message ");
			message.append(new Random().nextInt());
			
			final Runnable messageToConsume = new Message(message.toString());

			System.out.println(producerName + " says: putting new message into the Queue -> " + message.toString());
			
			queue.put(messageToConsume);

		}
		
	}

}
