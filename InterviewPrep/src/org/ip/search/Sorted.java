package org.ip.search;

// EPI 2018: 11.1
public class Sorted {
	public static void main(String[] s) {
		int[] array = new int[] {-14,-10,2,108,108,243,285,285,285,401};
		System.out.println(new Sorted().find(array, 108));
		System.out.println(new Sorted().find(array, 285));
		System.out.println(new Sorted().find(new int[] {1,2}, 2));
	}
	public int find(int[] array, int k) {
		int left = 0;
		int right = array.length - 1;
		int result = -1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (array[mid] < k) {
				left = mid + 1;
			} else if (array[mid] > k) {
				right = mid - 1;
			} else {
				result = mid;
				right = mid - 1;
			}
		}
		return result;
	}
}
