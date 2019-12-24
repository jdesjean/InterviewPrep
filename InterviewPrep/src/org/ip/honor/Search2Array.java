package org.ip.honor;

import java.util.Arrays;
import java.util.function.IntConsumer;

import org.ip.array.Utils;

// EPI 2018: 24.16
public class Search2Array {
	public static void main(String[] s) {
		//new Search2Array().find((x) -> System.out.println(x), new int[] {0,1,3,4,6}, new int [] {1,1,3,4,5}, 3);
		System.out.println(new Search2Array().find(new int[] {0,1,3,4,6}, new int [] {1,1,3,4,5}, 3));
	}
	public int find(int[] a, int[] b, int k) {
		int i = Math.min(a.length, k);
		int j = Math.max(0, k - a.length);
		for (; j < i; ) {
			int m = j + (i - j) / 2;
			int am1 = m >=0 ? a[m - 1] : Integer.MIN_VALUE;
			int am = m < a.length ? a[m] : Integer.MAX_VALUE;
			int bm = k - m < b.length ? b[k - m] : Integer.MAX_VALUE;
			int bm1 = k - m - 1 >= 0 ? b[k - m - 1] : Integer.MIN_VALUE;
			if (am < bm1) {
				j = m + 1;
			} else if (am1 > bm) {
				i = m - 1;
			} else {
				return Math.max(am1, bm1);
			}
		}
		int a1 = j <= 0 ? Integer.MIN_VALUE : a[i - 1];
		int b1 = k - j  <= 0 ? Integer.MIN_VALUE : b[k - j - 1];
		return Math.max(a1, b1);
	}
	
	// a + b
	public void _find(IntConsumer consumer, int[] a, int[] b, int k) {
		int i = 0, j = 0;
		for (; i < a.length && j < b.length && i + j < k; ) {
			if (a[i] <= a[j]) {
				consumer.accept(a[i++]);
			} else {
				consumer.accept(b[j++]);
			}
		}
		if (i + j < k) {
			if (i < a.length) {
				for (; j < a.length; i++) {
					consumer.accept(a[i]);
				}
			} else {
				for (; j < b.length; j++) {
					consumer.accept(b[j]);
				}
			}
		}
	}
}
