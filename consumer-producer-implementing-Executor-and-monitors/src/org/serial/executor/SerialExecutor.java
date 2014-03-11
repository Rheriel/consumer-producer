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
	 * The constructor.
	 */
	public SerialExecutor() {
		startConsumerThread();
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
					
					final Thread executeRunnable = new Thread(nextRunnable);
					
					executeRunnable.start();
					
					try {
						executeRunnable.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
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
