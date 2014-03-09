package org.consumer.producer.example;

/**
 * Consumer producer example.
 * 
 * @author Leo Gutierrez.
 *
 */
public class ConsumerProducer {

	/**
	 * Main method.
	 * 
	 * @param args No args expected.
	 */
	public static void main(String[] args) {
		
		final Queue queue = new Queue(10);
		
		final Thread producer = new Thread(new Producer(queue, "Producer1"));
		producer.start();
		
		final Thread consumer = new Thread(new Consumer(queue, "Consumer1"));
		consumer.start();
		
		final Thread secondProducer = new Thread(new Producer(queue, "Producer2"));
		secondProducer.start();
		
	}

}
