package org.ip.dp;

// EPI 2018: 16.8
public class Triangle {
	public static void main(String[] s) {
		System.out.println(new Triangle().solve(new int[][] {
			{2,0,0,0,0},
			{4,4,0,0,0},
			{8,5,6,0,0},
			{4,2,6,2,0},
			{1,5,2,3,4}
		}));
	}
	public int solve(int[][] triangle) {
		int[] cache = new int[triangle[0].length];
		cache[0] = triangle[0][0];
		for (int i = 1; i < triangle.length; i++) {
			int prev = 0;
			for (int j = 0; j <= i; j++) {
				int tmp = cache[j];
				if (j == 0) {
					cache[j] = triangle[i][j] + cache[j];
				} else if (j == i) {
					cache[j] = triangle[i][j] + prev;
				} else {
					cache[j] = triangle[i][j] + Math.min(prev, cache[j]);
				}
				prev = tmp;
			}
		}
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < cache.length; i++) {
			min = Math.min(min, cache[i]);
		}
		return min;
	}
}
