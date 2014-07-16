package com.rorkien.ircbot.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Queue-based implementation wrapping the OutputStream class. 
 * 
 * @author Rorkien
 */
public class QueueBuffer<T> {
	private Queue<T> queue;
	private PrintWriter writer;
		
	public QueueBuffer(PrintWriter writer) {
		this.writer = writer;
		this.queue = new PriorityQueue<T>();
	}
	
	public QueueBuffer(OutputStream stream) {
		this(new PrintWriter(stream));
	}
	
	/**
	 * Queues up an object.
	 * 
	 * @param message the object to be queued
	 */
	public void buffer(T message) {
		queue.add(message);
	}
	
	/**
	 * Sends a determinate amount of objects, notifies the designed handler, then flushes the output stream.
	 * 
	 * @param length the amount of objects to be sent
	 */
	public void sendBuffers(int length) {
		while (length-- > 0) {
			T message = queue.poll();
			writer.println(message);
			writer.flush();
		}
	}
	
	/**
	 * Polls and sends an object to the queue. The entire queue is then flushed.
	 * 
	 * @param message the object to be polled
	 */
	public void sendBuffers(T message) {
		buffer(message);
		sendBuffers();
	}
	
	/**
	 * Polls an array of objects to the queue. The entire queue is then flushed.
	 * 
	 * @param messages the array of objects to be polled 
	 */
	public void sendBuffers(T[] messages) {
		for (T message : messages) buffer(message);
		sendBuffers();
	}
	
	/**
	 * Flushes the entire queue to the output stream.
	 */
	public void sendBuffers() {
		sendBuffers(queue.size());
	}
	
	/**
	 * Closes the stream
	 */
	public void close() {
		writer.close();
	}
}
