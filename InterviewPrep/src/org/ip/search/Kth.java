package org.ip.search;

import org.ip.array.Utils;

// EPI 2018: 11.8
public class Kth {
	public static void main(String[] s) {
		int[] array = new int[] {3,2,1,5,4};
		for (int i = 0; i < array.length; i++) {
			System.out.println(new Kth().find(array.clone(), i));
		}
	}
	public int find(int[] array, int k) {
		int left = 0, right = array.length - 1;
		int pivot = -1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			pivot = partition(array, left, right, mid);
			if (array.length - pivot == k) {
				return array[pivot];
			} else if (array.length - pivot > k) {
				left = pivot + 1;
			} else {
				right = pivot - 1;
			}
		}
		return -1;
	}
	private int partition(int[] array, int left, int right, int pivot) {
		int value = array[pivot];
		Utils.swap(array, pivot, left);
		int mid = left;
		for (int i = left + 1; i <= right; i++) {
			if (array[i] < value) {
				Utils.swap(array, mid++, i);
			}
		}
		return mid;
	}
}
