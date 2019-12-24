package org.ip.honor;

import java.util.Arrays;

// EPI 2018: 24.3
public class Stock {
	public static void main(String[] s) {
		int[] a = new int[] {310,315,275,295,260,270,290,230,255,250};
		System.out.println(new Stock().solve(a, 2));
	}
	public int solve(int[] a, int k) {
		int[] max = new int[k];
		int[] min = new int[k];
		Arrays.fill(min, Integer.MAX_VALUE);
		for (int i = 0; i < a.length; i++) {
			for (int j = k - 1; j >= 0; j--) {
				max[j] = Math.max(max[j], a[i] - min[j]);
				min[j] = Math.min(min[j], a[i] - (j > 0 ? max[j - 1] : 0));
			}
		}
		return max[k - 1];
	}
}
