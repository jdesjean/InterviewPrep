package org.ip.stack;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

//EPI: 11.7
//Time: O(log(n)), Space: O(n)
public class StackHeap {
	public static void main(String[] s) {
		StackHeap stack = new StackHeap();
		int[] array = new int[]{5,4,1,2,3,6,1};
		
		//TC1
		for (int i = 0; i < array.length; i++) {
			stack.push(array[i]);
		}
		for (int i = 0; i < array.length; i++) {
			if (i > 0) System.out.print(",");
			System.out.print(stack.pop());
		}
		System.out.println();
		
		Random random = new Random();
		
		for (int i = 0; i < array.length || !stack.isEmpty(); i++) {
			if (i < array.length) {
				stack.push(array[i]);
				System.out.println("push: " + array[i]);
			}
			if (random.nextInt(2) == 0) {
				System.out.println("pop: " + stack.pop());
			}
		}
	}
	final PriorityQueue<Pair> heap = new PriorityQueue<Pair>(Collections.reverseOrder());
	public static class Pair implements Comparable<Pair>{
		final int index;
		final int value;
		public Pair(int value, int index){this.value=value;this.index=index;}
		@Override
		public int compareTo(Pair o) {
			return index - o.index;
		}
	}
	int index = 0;
	public StackHeap() {
	}
	public void push(int value) {
		heap.add(new Pair(value,index++));
	}
	public int pop() {
		return heap.remove().value;
	}
	public int peek() {
		return heap.peek().value;
	}
	public boolean isEmpty() {
		return heap.isEmpty();
	}
}
