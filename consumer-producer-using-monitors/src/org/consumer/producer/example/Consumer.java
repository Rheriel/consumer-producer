package org.consumer.producer.example;

/**
 * Consumer example class. Will consume all the available in the given Queue 
 * one bye one element in a sequence way, thus, once one message is completed 
 * consumer, the consumer will proceed to consume the next message.
 * 
 * @author Leo Gutierrez
 *
 */
public class Consumer implements Runnable {

	/**
	 * Queue where producer is putting the messages and where the consumer 
	 * will be pulling from.
	 */
	private Queue queue;
	
	/**
	 * Consumer name.
	 */
	private String consumerName;
	
	/**
	 * The constructor.
	 * 
	 * @param queue ueue where producer is putting the messages and where 
	 *              the consumer will be pulling from. Must not be null.
	 * @param consumerName Consumer name.
	 * @throws IllegalArgumentException If the given queue is null.
	 */
	public Consumer(final Queue queue, final String consumerName) 
			throws IllegalArgumentException {
		
		if (queue == null) {
			throw new IllegalArgumentException("Queue must not be null");
		}
		
		this.queue = queue;
		
		this.consumerName = consumerName;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		consumeMessages();
		
	}

	/**
	 * Consumes all messages from the Queue in a sequence way. Once one message 
	 * is completed consumer, the consumer will proceed to consume the next message.
	 */
	private void consumeMessages() {
		
		while (true) {
			
			final Runnable message = queue.pull();

			System.out.println(consumerName + " says: New message, wait for it ... ");
			
			final Thread consumeMessage = new Thread(message);
			consumeMessage.start();

			try {

				consumeMessage.join();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

}
