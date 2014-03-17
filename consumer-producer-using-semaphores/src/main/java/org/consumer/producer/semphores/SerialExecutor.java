package org.consumer.producer.semphores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {

	private final Semaphore semaphore;
	
	private final List<Runnable> runnablesToExecute;
	
	public SerialExecutor(final int capacity) {
		
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity must be 1 or above");
		}
		
		semaphore = new Semaphore(capacity);
		
		runnablesToExecute = new ArrayList<Runnable>(capacity);
		
		startConsumer();
		
	}
	
	public void execute(final Runnable command) {
		
		synchronized (semaphore) {
		
			semaphore.take();
			
			System.out.println("Runnable added");
			
			runnablesToExecute.add(command);
			
		}
		
	}

	private Runnable nextRunnable() {
		
		synchronized (semaphore) {

			semaphore.release();

			System.out.println("Consumming a runnable");

			final Runnable nextRunnable = runnablesToExecute.remove(0);
			
			return nextRunnable;

		}

		
	}
	
	private void startConsumer() {
		
		final Thread consumer = new Thread(new Runnable() {
			
			public void run() {
				
				Runnable nextRunnableToConsume = null;
				
				while ((nextRunnableToConsume = nextRunnable()) != null) {
					
					nextRunnableToConsume.run();
					
				}
				
			}
		});
		
		consumer.start();
		
	}
	
	public synchronized int retrieveThreadsInPool() {
		
		return runnablesToExecute.size();
		
	}
}
