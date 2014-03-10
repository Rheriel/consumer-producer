package org.consumer.producer.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Queue representation for consumer producer example.
 * 
 * @author Leo Gutierrez.
 *
 */
public class Queue {

	/**
	 * Reference to the queue capacity.
	 */
	private int queueCapacity;
	
	/**
	 * Reference to the queue containing the messages.
	 */
	private List<Runnable> queueList;
	
	/**
	 * The constructor.
	 * 
	 * @param queueCapacity Queue capacity.
	 * @throws IllegalArgumentException If the queue capacity is equals or less 
	 *                                  than 0.
	 */
	public Queue(final int queueCapacity) throws IllegalArgumentException {
		
		if (queueCapacity <= 0) {
			throw new IllegalArgumentException("Queue capacity must be 1 to n");
		}
		
		this.queueCapacity = queueCapacity;
		
		queueList = new ArrayList<Runnable>(queueCapacity);
		
	}
	
	/**
	 * Puts the given element into the Queue. If the queue is full, it will 
	 * wait until there is a spot available.
	 * 
	 * @param element Element to put in the queue.
	 */
	public void put(final Runnable element) {
		
		synchronized (queueList) {
			
			while (queueList.size() >= queueCapacity) {
				
				try {
					
					System.out.println("Queue says: i'm full -> " + queueList.size());
					
					queueList.wait();
					
					System.out.println("Queue says: ok, not full anymore");
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			queueList.add(element);
			
			queueList.notifyAll();
			
		}
		
	}
	
	/**
	 * Pulls from the Queue the first element inserted. If the Queue is empty, 
	 * it will wait until there is something to pull.
	 * 
	 * @return First element in the Queue.
	 */
	public Runnable pull() {
		
		synchronized (queueList) {
			
			while (queueList.isEmpty()) {
				
				try {
					
					System.out.println("Queue says: i'm empty");
					
					queueList.wait();
					
					System.out.println("Queue says: Yeah!, someone put something to consume");
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			try {
				
				return queueList.remove(0);
			
			} finally {
				queueList.notifyAll();
			}
			
		}
		
	}
	
	/**
	 * Answers the number of elements in this Queue.
	 * 
	 * @return the number of elements in this Queue.
	 */
	public int size() {
		
		synchronized (queueList) {
		
			return queueList.size();
			
		}
		
	}
	
	
}
