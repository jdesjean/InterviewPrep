package org.ip.sort;

import org.ip.primitives.ArrayUtils;

public class Merge2 {
	public static void main(String[] s) {
		int[] a = new int[]{0,1,2,2,3,6,7,7,8,0,0,0,0,0,0};
		int[] b = new int[]{1,4,4,7,7,9};
		merge(a,b,9);
		ArrayUtils.println(a, 0, a.length-1);
	}
	public static void merge(int[] a, int[] b, int n) {
		for (int k = a.length-1, i = b.length-1, j = n-1; i >= 0; k--) {
			a[k] = j < 0 || b[i] >= a[j] ? b[i--] : a[j--];
		}
	}
}
