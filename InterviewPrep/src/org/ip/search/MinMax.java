package org.ip.search;

import org.ip.array.Utils;

// EPI 2018: 11.7
public class MinMax {
	public static void main(String[] s) {
		int[] array = new int[] {3,2,5,1,2,4};
		Utils.println(new MinMax().find(array));
	}
	public int[] find(int[] array) {
		int[] pair = new int[] {0, 0};
		// 3/2 n comparisons
		for (int i = 1; i < array.length; i+=2) {
			int min = i - 1;
			int max = i;
			if (array[i] < array[i - 1]) { // 1 comparison
				min = i;
				max = i - 1;
			}
			if (array[min] < array[pair[0]]) { // 1 comparison
				pair[0] = min;
			}
			if (array[max] > array[pair[1]]) { // 1 comparison
				pair[1] = max;
			}
		}
		if (array.length % 2 == 1) {
			if (array[array.length - 1] < array[pair[0]]) {
				pair[0] = array.length - 1; 
			} else if (array[array.length - 1] > array[pair[1]]) {
				pair[1] = array.length - 1;
			}
		}
		return pair;
	}
}
