package org.ip.sort;

import org.ip.array.ArrayUtils;

public class Sorted {
	public static void main(String[] s) {
		int[] array = new int[]{2,3,5,5,7,11,11,11,13};
		System.out.println(dedup(array));
		ArrayUtils.println(array, 0, array.length-1);
	}
	public static int dedup(int[] a) {
		int l = a.length  > 0 ? 1 : 0;
		for (int i = 1, j = 1; i < a.length;) {
			while (a[i] == a[i-1]) i++;
			a[j++] = a[i++];
			l++;
		}
		return l;
	}
}
