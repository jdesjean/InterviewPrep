package org.ip.heap;

import java.util.Collections;
import java.util.PriorityQueue;

// EPI 2018: 10.4
public class ClosestKOnline {
	public static void main(String[] s) {
		int[] array = new int[] {3,-1,2,6,4,5,8}; // array does not fit in RAM
		int[] result = new int[2];
		new ClosestKOnline().closest(array, result);
		org.ip.array.Utils.println(result);
	}
	public void closest(int[] array, int[] result) {
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>(result.length + 1, Collections.reverseOrder());
		for (int i = 0; i < array.length; i++) {
			heap.add(array[i]);
			if (heap.size() > result.length) {
				heap.remove();
			}
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = heap.remove();
		}
	}
}
