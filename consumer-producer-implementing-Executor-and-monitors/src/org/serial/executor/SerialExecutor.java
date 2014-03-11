package org.serial.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Example of a serial executor. Runnable's are executed in a sequential way, 
 * not blocking the Executor.  
 * 
 * @author Leo Gutierrez (leoggut@mx1.ibm.com)
 *
 */
public class SerialExecutor implements Executor {

	/**
	 * Reference to the queue containing the Runnable's.
	 */
	private final List<Runnable> queue = new ArrayList<Runnable>();
	
	/**
	 * The constructor. Executes only one Thread to consume the Runnable's.
	 */
	public SerialExecutor() {
		startConsumerThread();
	}
	
	/**
	 * The constructor. Specifies the number of threads consuming the 
	 * Runnable's.
	 * 
	 * @param poolSize Thread pool size.
	 * @throws IllegalArgumentException If the given pool size is 0 or less.
	 */
	public SerialExecutor(final int poolSize) throws IllegalArgumentException {
		
		if (poolSize <= 0) {
			throw new IllegalArgumentException("Pool size must be 1 or above.");
		}
		
		for (int poolThreadIndex = 0; poolThreadIndex < poolSize; 
				poolThreadIndex++) {
			
			startConsumerThread();
			
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.concurrent.Executor#execute(java.lang.Runnable)
	 */
	@Override
	public void execute(final Runnable command) {
		
		synchronized (queue) {
		
			if (command != null) {
				
				System.out.println("Executor says: new command added");
				
				queue.add(command);
				
				queue.notifyAll();
				
			}
			
		}
		
	}

	/**
	 * Starts the consumer thread that will be consuming all the Runnable's 
	 * sent to this Executor.
	 */
	private void startConsumerThread() {
		
		final Thread consumeRunnables = new Thread(new Runnable() {
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				
				while (true) {
					
					final Runnable nextRunnable = consume();
					
					nextRunnable.run();
					
				}
				
			}
		});
		
		consumeRunnables.start();
		
	}
	
	/**
	 * Retrieves the next Runnable to be consumed. If there is nothing to consume 
	 * it will wait until there is something.
	 */
	private Runnable consume() {
		
		synchronized (queue) {
		
			while (queue.isEmpty()) {
				
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			return queue.remove(0);
			
		}
		
	}

}
