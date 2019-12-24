package org.ip.parallel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// LinkedIn
public class BlockingQueue<T> {
	public static void main(String[] s) {
		BlockingQueue queue = new BlockingQueue();
		Thread t1 = new Thread(new Task(queue));
		t1.start();
		queue.enqueue(1);
	}
	Node head;
	Node tail;
	int size;
	ReentrantLock lock = new ReentrantLock();
	Condition notEmpty = lock.newCondition();
	public void enqueue(T value) {
		try {
			lock.lock();
			if (head == null) {
				head = tail = new Node(value);
			} else {
				tail.next = new Node(value);
				tail = tail.next;
			}
			size++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
		
	}
	public T dequeue() throws InterruptedException {
		try {
			lock.lock();
			while (head == null) {
				notEmpty.await();
			}
			T value = head.value;
			head = head.next;
			size--;
			return value;
		} finally {
			lock.unlock();
		}
		
	}
	public boolean isEmpty() {
		try {
			lock.lock();
			return size == 0;
		} finally {
			lock.unlock();
		}
	}
	public int size() {
		try {
			lock.lock();
			return size;
		} finally {
			lock.unlock();
		}
	}
	public class Node {
		T value;
		Node next;
		public Node(T value) {
			this.value=value;
		}
	}
	public static class Task implements Runnable {
		private BlockingQueue queue;

		public Task(BlockingQueue queue) {
			this.queue=queue;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queue.dequeue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
