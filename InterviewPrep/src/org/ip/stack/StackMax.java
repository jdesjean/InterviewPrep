package org.ip.stack;

import java.util.Deque;
import java.util.LinkedList;

public class StackMax {
	public static void main(String[] s) {
		StackMax stack = new StackMax();
		stack.push(2);
		System.out.println(stack.max());
		stack.push(2);
		System.out.println(stack.max());
		stack.push(1);
		System.out.println(stack.max());
		stack.push(4);
		System.out.println(stack.max());
		stack.push(5);
		System.out.println(stack.max());
		stack.push(5);
		System.out.println(stack.max());
		stack.push(3);
		System.out.println(stack.max());
		System.out.println("**");
		while (!stack.isEmpty()) {
			System.out.println(stack.max());
			stack.pop();
		}
	}
	private static class MaxCountPair {
		public final int max;
		public int count;
		public MaxCountPair(int max, int count){this.max=max;this.count=count;}
	}
	Deque<Integer> stackValue = new LinkedList<Integer>();
	Deque<MaxCountPair> stackMax = new LinkedList<MaxCountPair>();
	public void push(int value) {
		if (isEmpty()) stackMax.push(new MaxCountPair(value,1));
		else if (stackMax.peek().max == value) stackMax.peek().count++;
		else if (stackMax.peek().max < value) stackMax.push(new MaxCountPair(value,1));
		stackValue.push(value);
	}
	public int pop() {
		int value = stackValue.pop();
		if (stackMax.peek().max == value && stackMax.peek().count == 1) stackMax.pop();
		else if (stackMax.peek().max == value) stackMax.peek().count--;
		return value;
	}
	public int max() {
		return stackMax.peek().max;
	}
	public boolean isEmpty() {
		return stackValue.isEmpty();
	}
}
