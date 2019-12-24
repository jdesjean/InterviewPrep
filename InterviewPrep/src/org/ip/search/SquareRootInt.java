package org.ip.search;

// EPI 2018: 11.4
public class SquareRootInt {
	public static void main(String[] s) {
		System.out.println(new SquareRootInt().find(300));
	}
	public int find(int value) {
		int result = -1;
		for (int left = 0, right = value / 2; left <= right; ) {
			int mid = left + (right - left) / 2;
			int square = mid * mid;
			if (square == value) {
				return mid;
			} else if (square < value) {
				result = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return result;
	}
}
