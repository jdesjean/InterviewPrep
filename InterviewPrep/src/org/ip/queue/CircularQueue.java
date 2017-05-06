package org.ip.queue;

import java.util.NoSuchElementException;

public class CircularQueue {
	public static void main(String[] s) {
		CircularQueue queue = new CircularQueue(2);
		for (int i = 1; i < 4; i++) {
			queue.enqueue(i);
			System.out.println(queue.dequeue());
		}
		for (int i = 4; i < 8; i++) {
			queue.enqueue(i);
		}
		while (!queue.isEmpty()) {
			System.out.println(queue.dequeue());
		}
		
	}
	int size = 0;
	int capacity = 0;
	int[] queue;
	int head = -1, tail = -1;
	public CircularQueue(int capacity){
		this.capacity = capacity;
		queue = new int[capacity];
	}
	private void grow() {
		if (capacity >= 1 << 30) throw new IllegalStateException();
		int newCapacity = capacity << 1;
		int[] newQueue = new int[newCapacity];
		for (int i = 0; i < capacity; i++) {
			newQueue[i] = dequeue();
		}
		tail = capacity-1;
		head = -1;
		size = capacity;
		queue = newQueue;
		capacity = newCapacity;
		
	}
	public boolean isFull(){return size == capacity;}
	public boolean isEmpty(){return size == 0;}
	public int size(){return size;}
	public void enqueue(int value) {
		if (isFull()) grow();
		size++;
		tail = (tail + 1) % capacity;
		queue[tail] = value;
	}
	public int dequeue(){
		if (size == 0) throw new NoSuchElementException();
		size--;
		head = (head + 1) % capacity;
		int value = queue[head];
		return value;
	}
}
