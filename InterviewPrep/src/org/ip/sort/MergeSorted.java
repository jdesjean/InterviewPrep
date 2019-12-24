package org.ip.sort;

import org.ip.array.Utils;

// EPI 2018: 13.2
public class MergeSorted {
	public static void main(String[] s) {
		int[] a1 = new int[] {3,13,17,0,0,0,0};
		int[] a2 = new int[] {3,7,11,19};
		new MergeSorted().merge(a1, 3, a2);
		Utils.println(a1);
	}
	public void merge(int[] a1, int l, int[] a2) {
		for (int i = l - 1, j = a2.length - 1, k = a1.length - 1; k >= 0 && j >= 0; k--) {
			if (i >= 0 && a1[i] >= a2[j]) {
				a1[k] = a1[i--];
			} else {
				a1[k] = a2[j--];
			}
		}
	}
}
