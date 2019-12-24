package org.ip.search;

// EPI 2018: 11.2
public class SortedIndex {
	public static void main(String[] s) {
		int[] array = new int[] {-2,0,2,3,6,7,9};
		System.out.println(new SortedIndex().find(array));
	}
	public int find(int[] array) {
		int left = 0, right = array.length - 1;
		for (; left <= right; ) {
			int mid = left + (right - left) / 2;
			if (array[mid] == mid) {
				return mid;
			} else if (array[mid] > mid) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return -1;
	}
}
