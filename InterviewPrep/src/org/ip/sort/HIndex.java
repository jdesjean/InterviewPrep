package org.ip.sort;

import java.util.Arrays;

import org.ip.array.Utils;

// EPI 2018
public class HIndex {
	public static void main(String[] s) {
		int[] array = new int [] {1,4,1,4,2,1,3,5,6};
		System.out.println(new HIndex().index(array));
	}
	public int _index(int[] array) {
		Arrays.sort(array);
		for (int i = array.length - 1; i >= 0; i--) {
			if (array.length - i >= array[i]) {
				return array[i];
			}
		}
		return -1;
	}
	public int index(int[] array) {
		int left = 0, right = array.length - 1, pivot = -1;
		do {
			pivot = left + (right - left) / 2;
			int next = partition(array, left, right, pivot);
			if (array.length - next >= array[next]) {
				left = next;
			} else {
				right = next;
			}
		} while (left < right);
		return pivot;
	}
	private int partition(int[] array, int left, int right, int pivot) {
		int value = array[pivot];
		int l = left;
		for (int r = right, m = l; m < r;) {
			if (array[m] < value) {
				Utils.swap(array, l++, m++);
			} else if (array[m] > value) {
				Utils.swap(array, m, r--);
			} else {
				m++;
			}
		}
		return l;
	}
}
