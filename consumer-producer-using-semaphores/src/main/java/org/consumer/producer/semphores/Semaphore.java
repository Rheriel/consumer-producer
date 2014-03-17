package org.consumer.producer.semphores;

public class Semaphore {

	private int bound;
	
	private int signalCount;
	
	public Semaphore(final int bound) {
		
		if (bound <= 0) {
			throw new IllegalArgumentException("Bound should be 1 or above");
		}
		
		this.bound = bound;
		
	}
	
	public Semaphore() {
		bound = 1;
	}
	
	public synchronized int getSignalCount() {
		return signalCount;
	}
	
	public synchronized void take() {
		
		while(signalCount >= bound) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		signalCount++;
		
		notifyAll();
		
	}
	
	public synchronized void release() {
		
		while(signalCount <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		signalCount--;
		
		notifyAll();
		
	}
	
}
