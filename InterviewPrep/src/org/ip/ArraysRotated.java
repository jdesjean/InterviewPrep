package org.ip;

public class ArraysRotated {
	public static void main(String[] s) {
		ArraysRotated arraysRotated = new ArraysRotated();
		System.out.println(arraysRotated.min(new int[]{5,6,7,1,2,3,4}));
		System.out.println(arraysRotated.min(new int[]{5,6,7,8,2,3,4}));
		System.out.println(arraysRotated.min(new int[]{5,6,7,8,9,10,4}));
		System.out.println(arraysRotated.min(new int[]{5,6,7,8,9,10,11}));
	}
	public int min(int[] a) {
		int left = 0;
		for (int right = a.length - 1; left < right; ) {
			int mid = left+(right-left)/2;
			if (a[mid] < a[right]) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return a[left];
	}
}
