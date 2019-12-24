package org.ip.heap;

import java.util.PriorityQueue;

// EPI 2018: 10.3
public class MergeK {
	public static void main(String[] s) {
		int[] array = new int[] {3,-1,2,6,4,5,8};
		int k = 2;
		new MergeK().merge(new PrintVisitor(), array, k);
	}
	public void merge(PrintVisitor visitor, int[] array, int k) {
		PriorityQueue<Integer> heap =  new PriorityQueue<Integer>();
		for (int i = 0; i < array.length; i++) {
			if (heap.size() == k) {
				visitor.visit(heap.remove());
			}
			heap.add(array[i]);
		}
		while (!heap.isEmpty()) {
			visitor.visit(heap.remove());
		}
	}
	public static class PrintVisitor {
		public void visit(int value) {
			System.out.print(String.valueOf(value) + ",");
		}
	}
}
