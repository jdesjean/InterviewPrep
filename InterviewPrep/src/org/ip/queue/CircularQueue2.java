package org.ip.queue;

// EPI 2018: 8.7
public class CircularQueue2 {
	public static void main(String[] s) {
		CircularQueue2 queue = new CircularQueue2(2);
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
	int[] data;
	int capacity;
	int head = -1;
	int tail = -1;
	public CircularQueue2(int capacity) {
		this.capacity = capacity;
		data = new int[capacity];
	}
	private void grow() {
		int newCapacity = capacity * 2;
		int[] tmp = new int[newCapacity];
		for (int i = 0; i < size; i++) {
			tmp[size - i - 1] = data[(head + i) % capacity];
		}
		head = size - 1;
		tail = -1;
		data = tmp;
		capacity = newCapacity;
	}
	public void enqueue(int value) {
		if (size == capacity) {
			grow();
		}
		data[++head % capacity] = value;
		size++;
	}
	public int dequeue() {
		if (size > 0) {
			size --;
			return data[++tail % capacity];
		} else {
			throw new RuntimeException("Empty");
		}
	}
	public boolean isEmpty() {
		return size == 0;
	}
}
