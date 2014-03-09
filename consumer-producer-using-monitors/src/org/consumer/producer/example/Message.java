package org.consumer.producer.example;

/**
 * Message to produce and consume.
 * 
 * @author Leo Gutierrez.
 *
 */
public class Message implements Runnable {

	/**
	 * Reference to the message.
	 */
	private String message;
	
	/**
	 * The constructor.
	 * 
	 * @param message Message.
	 */
	public Message(final String message) {
		
		this.message = message;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		System.out.println("--> " + message + " <--- ");
		
		// Simulate other processing.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
