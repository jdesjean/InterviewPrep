package org.ip.array;

public class Matrix3 {
	public static void main(String[] s) {
		/*
		 * A: 40x20
		 * B: 20x30
		 * C: 30x10
		 * D: 10x30
		 */
		System.out.println(new MinimizeRecursive().solve(new int[]{40,20,30,10,30}) + "==26000");
		System.out.println(new MinimizeDP().solve(new int[]{40,20,30,10,30}) + "==26000");
	}
	public static class MinimizeRecursive {
		public int solve(int[] array) {
			return min(array, 1, array.length - 1);
		}
		public int min(int[] array, int l, int r) {
			if (l == r) return 0;
			int min = Integer.MAX_VALUE;
			for (int i = l; i < r; i++) {
				int m = min(array, l, i) + min(array, i + 1, r) + array[l - 1] * array[i] * array[r];
				min = Math.min(min, m);
			}
			return min;
		}
	}
	public static class MinimizeDP {
		public int solve(int[] array) {
			int[][] cache = new int[array.length][array.length];
			for (int m = 2; m < array.length; m++) {
				for (int l = 1, r = l+m-1; r < array.length; r++, l++) {
					cache[l][r] = Integer.MAX_VALUE;
					for (int i = l; i < r; i++) {
						int min = cache[l][i] + cache[i + 1][r] + array[l - 1] * array[i] * array[r];
						cache[l][r] = Math.min(cache[l][r], min);
					}
				}
			}
			return cache[1][array.length-1];
		}
	}
}
