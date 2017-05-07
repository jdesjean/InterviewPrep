package org.ip.queue;

import java.util.Deque;
import java.util.LinkedList;

//EPI 9.10
public class QueueWithMax {
	public static void main(String[] s) {
		QueueWithMax queue = new QueueWithMax();
		for (int i = 1; i < 4; i++) {
			queue.enqueue(i);
			System.out.println("max"+queue.max());
			System.out.println(queue.dequeue());
		}
		for (int i = 8; i >= 4; i--) {
			queue.enqueue(i);
			System.out.println("max"+queue.max());
		}
		for (int i = 6; i < 10; i++) {
			queue.enqueue(i);
			System.out.println("max"+queue.max());
		}
		while (!queue.isEmpty()) {
			System.out.println("max"+queue.max());
			System.out.println(queue.dequeue());
		}
	}
	Deque<Integer> queue = new LinkedList<Integer>();
	Deque<Integer> queueMax = new LinkedList<Integer>();
	//Time: O(1), Space: O(n)
	public void enqueue(int value) {
		queue.addLast(value);
		if (queueMax.isEmpty()) {
			queueMax.addLast(value);
		} else {
			while (!queueMax.isEmpty() && queueMax.getFirst() < value) {
				queueMax.removeFirst();
			}
			queueMax.addLast(value);
		}
	}
	//Time: O(1), Space: O(1)
	public int dequeue() {
		int value = queue.removeFirst();
		if (value == queueMax.getFirst()) {
			queueMax.removeFirst();
		}
		return value;
	}
	public int max() {
		return queueMax.getFirst();
	}
	public boolean isEmpty(){return queue.isEmpty();}
}
