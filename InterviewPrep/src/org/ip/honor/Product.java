package org.ip.honor;

// EPI 2018: 24.4
public class Product {
	public static void main(String[] s) {
		int[] a = new int[] {3,2,5,4};
		System.out.println(new Product().solve(a));
	}
	public int solve(int[] a) {
		int neg = 0;
		int minNeg = 0;
		int minPos = 0;
		int greatestNeg = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < 0) {
				neg++;
				if (Math.abs(a[i]) < a[minNeg]) {
					minNeg = i;
				}
				if (a[i] < a[greatestNeg]) {
					greatestNeg = i;
				}
			}
			else {
				if (a[i] < a[minPos]) {
					minPos = i;
				}
			}
		}
		int index = -1;
		if (neg % 2 == 1) {
			index = minNeg;
		} else {
			if (neg == a.length) {
				index = greatestNeg;
			} else {
				index = minPos;
			}
		}
		int p = 1;
		for (int i = 0; i < a.length; i++) {
			if (i == index) continue;
			p*=a[i];
		}
		return p;
	}
	public int _solve(int[] a) {
		int[] cache = new int[a.length];
		int p = 1;
		for (int i = a.length - 1; i >= 0; i--) {
			p*=a[i];
			cache[i] = p; 
		}
		p = 1;
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			int pp = i < cache.length - 1 ? cache[i + 1] : 0;
			max = Math.max(max, p * pp);
			p*= a[i];
		}
		return max;
	}
}
