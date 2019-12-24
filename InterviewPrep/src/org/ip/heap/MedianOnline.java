package org.ip.heap;

import java.util.Collections;
import java.util.PriorityQueue;

// EPI 2018: 10.5
public class MedianOnline {
	public static void main(String[] s) {
		float[] array = new float[] {1,0,3,5,2,0,1};
		new MedianOnline().median(new PrintVisitor(), array);
		System.out.println();
		array = new float[] {1,0,3,5,2,0};
		new MedianOnline().median(new PrintVisitor(), array);
	}
	public void median(PrintVisitor visitor, float[] array) {
		PriorityQueue<Float> minHeap = new PriorityQueue<Float>(4);
		PriorityQueue<Float> maxHeap = new PriorityQueue<Float>(4, Collections.reverseOrder());
		for (int i = 0; i < array.length; i++) {
			boolean isMinTarget = minHeap.isEmpty() || array[i] >= minHeap.peek();
			PriorityQueue<Float> target = isMinTarget ? minHeap : maxHeap;
			PriorityQueue<Float> other = isMinTarget ? maxHeap : minHeap;
			target.add(array[i]);
			if (target.size() > other.size()) {
				other.add(target.remove());
			}
			visitor.visit((i + 1) % 2 == 0 ? (minHeap.peek() + maxHeap.peek()) / 2 : maxHeap.peek());
		}
	}
	public static class PrintVisitor {
		public void visit(float value) { 
			System.out.print(String.valueOf(value) + ", ");
		}
	}
}
