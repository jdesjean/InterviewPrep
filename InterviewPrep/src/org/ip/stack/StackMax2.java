package org.ip.stack;

import java.util.Deque;
import java.util.LinkedList;
//EPI 8.1
//Time: O(1), Space: O(n) 
public class StackMax2 {
	public static void main(String[] s) {
		StackMax2 stack = new StackMax2();
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
	Deque<Integer> stackValue = new LinkedList<Integer>();
	Deque<Integer> stackMax = new LinkedList<Integer>();
	public void push(int value) {
		stackValue.push(value);
		if (stackMax.isEmpty() || stackMax.peek() <= value) {
			stackMax.push(value);
		}
	}
	public int max() {
		return stackMax.peek();
	}
	public int pop() {
		int value = stackValue.pop();
		if (stackMax.peek() == value) {
			stackMax.pop();
		}
		return value;
	}
	public boolean isEmpty() {
		return stackValue.isEmpty();
	}
}
