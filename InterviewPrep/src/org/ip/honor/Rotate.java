package org.ip.honor;

import org.ip.array.Utils;

// EPI 2018: 24.6
public class Rotate {
	public static void main(String[] s) {
		int[] a = new int[] {0,1,2,3,4,5};
		new Rotate().solve(a, 2);
		Utils.println(a);
	}
	public void solve(int[] a, int k) {
		if (k % a.length == 0) return;
		int kk = k % a.length;
		Utils.reverse(a, 0, a.length - kk - 1);
		Utils.reverse(a, a.length - kk, a.length - 1);
		Utils.reverse(a, 0, a.length - 1);
	}
}
