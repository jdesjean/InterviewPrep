package org.ip.queue;

import java.util.Deque;
import java.util.LinkedList;

//EPI 9.9
//leetcode 232
public class CircularQueueStack {
	public static void main(String[] s) {
		CircularQueueStack queue = new CircularQueueStack();
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
	Deque<Integer> stack = new LinkedList<Integer>();
	Deque<Integer> stackFIFO = new LinkedList<Integer>();
	//Time: O(1), Space: O(1) 
	public void enqueue(int value) {
		stack.push(value);
	}
	//Time: O(1) amortized, Space: O(1)
	public int dequeue() {
		if (!stackFIFO.isEmpty()) return stackFIFO.pop();
		while (!stack.isEmpty()) {
			stackFIFO.push(stack.pop());
		}
		return stackFIFO.pop();
	}
	public boolean isEmpty(){return stack.isEmpty() && stackFIFO.isEmpty();}
}
