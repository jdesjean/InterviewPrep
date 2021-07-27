package org.ip.array;

// Facebook 10/03/2019
public class Monotonic {
	public static void main(String[] s) {
		Monotonic monotonic = new Monotonic();
		System.out.println(monotonic.isMonotonic(new int[] {1,1}));
		System.out.println(monotonic.isMonotonic(new int[] {}));
		System.out.println(monotonic.isMonotonic(new int[] {1,1,2,3}));
		System.out.println(monotonic.isMonotonic(new int[] {3,3,2,1}));
		System.out.println(monotonic.isMonotonic(new int[] {1,1,2,3,3,2,1}));
	}
	public boolean isMonotonic(int[] a) {
		int state = 0;
		for (int i = 1; i < a.length; i++) {
			int diff = a[i] - a[i - 1];
			if (state == 0) {
				state = diff;
			} else if (state < 0 && diff > 0) {
				return false;
			} else if (state > 0 && diff < 0) {
				return false;
			}
		}
		return true;
	}
}
