package org.ip.queue;

import java.util.Deque;
import java.util.LinkedList;

//EPI 2018: 8.9
public class QueueWithMax2<T extends Comparable<T>> {
	public static void main(String[] s) {
		QueueWithMax2 queue = new QueueWithMax2();
		for (int i = 1; i < 4; i++) {
			queue.enqueue(i);
			System.out.println("max"+queue.max());
		}
		for (int i = 6; i >= 4; i--) {
			queue.enqueue(i);
			System.out.println("max"+queue.max());
		}
		queue.enqueue(7);
		System.out.println("max"+queue.max());
		while (!queue.isEmpty()) {
			System.out.println("max"+queue.max());
			System.out.println(queue.dequeue());
		}
	}
	Deque<T> queue = new LinkedList<T>();
	Deque<T> queueMax = new LinkedList<T>();
	public void enqueue(T value) {
		while (!queueMax.isEmpty() && queueMax.peekFirst().compareTo(value) < 0) {
			queueMax.removeFirst();
		}
		queueMax.addLast(value);
		queue.addLast(value);
	}
	public T dequeue() {
		while (queue.peekFirst() == queueMax.peekFirst()) {
			queueMax.removeFirst();
		}
		return queue.removeFirst();
	}
	public T peek() {
		if (queue.isEmpty()) return null;
		return queue.peek();
	}
	public T max() {
		return queueMax.getFirst();
	}
	public boolean isEmpty(){return queue.isEmpty();}
}
