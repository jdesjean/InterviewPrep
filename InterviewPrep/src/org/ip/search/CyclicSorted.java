package org.ip.search;

// EPI 2018: 11.3
public class CyclicSorted {
	public static void main(String[] s) {
		int[] array = new int[] {378,478,550,631,103,203,220,234,279,368};
		System.out.println(new CyclicSorted().find(array));
	}
	public int find(int[] array) {
		int left = 0, right = array.length - 1;
		for (; left < right; ) {
			int mid = left + (right - left)/2;
			if (array[mid] > array[right]) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return left;
	}
}
