package org.ip.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.ip.array.Utils;

// EPI 2018: 10.6
public class LargestKInHeap {
	public static void main(String[] s) {
		int[] array = new int[] {561,314,401,28,156,359,271,11,3};
		int[] result = new int[4];
		new LargestKInHeap().largest(array, result);
		Utils.println(result);
	}
	//0 -> 1, 2
	//1 -> 3,4
	//2 -> 5,6
	//3 -> 7,8
	//4 -> 9,10
	public void largest(int[] array, int[] result) {
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(4, new ArrayComparator(array));
		maxHeap.add(0);
		for (int i = 0; i < result.length; i++) {
			int index = maxHeap.remove();
			result[i] = array[index];
			int next1 = index * 2 + 1;
			int next2 = next1 + 1;
			if (next1 < array.length) {
				maxHeap.add(index * 2 + 1);
				if (next2 < array.length) {
					maxHeap.add(index * 2 + 2);
				}
			}
		}
	}
	public static class ArrayComparator implements Comparator<Integer> {
		private int[] array;

		public ArrayComparator(int[] array) {
			this.array=array;
		}

		@Override
		public int compare(Integer o1, Integer o2) {
			return array[o2] - array[o1]; //reverse order
		}
	}
}
