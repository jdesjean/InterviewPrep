package org.ip.honor;

// EPI 2018: 24.15
public class SearchArrayLength {
	public static void main(String[] s) {
		System.out.println(new SearchArrayLength().find(new int[] {1,3,5,6,7,9}, 6));
	}
	public int find(int[] a, int k) {
		int l = 1;
		while (l <= a.length) {
			l = l * 2 + 1;
		}
		for (int left = 1, right = l; left < right; ) {
			int mid = left + (right - left) / 2;
			if (mid == a.length) {
				l = mid;
				break;
			}
			else if (mid > a.length) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		for (int left = 1, right = l - 1; left < right; ) {
			int mid = left + (right - left) / 2;
			if (a[mid] == k) return mid;
			else if (a[mid] > k) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return -1;
	}
}
