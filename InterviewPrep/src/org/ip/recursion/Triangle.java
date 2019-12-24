package org.ip.recursion;

// EPI: 17.8
public class Triangle {
	public static void main(String[] s) {
		int[][] triangle = new int[][] {{2},{4,4}, {8,5,6}, {4,2,6,2}, {1,5,2,3,4}};
		System.out.println(solve(triangle));
	}
	public static int solve(int[][] triangle) {
		int[] cache = new int[triangle[triangle.length - 1].length];
		for (int i = 0; i < triangle[0].length; i++) {
			cache[i] = triangle[0][i];
		}
		int min = cache[0];
		for (int l = 1; l < triangle.length; l++) {
			min = Integer.MAX_VALUE;
			int prev = cache[0];
			for (int c = 0; c < triangle[l].length; c++) {
				int minprev = c == 0 ? cache[0] : c == l ? prev : Math.min(prev, cache[c]);
				prev = cache[c];
				cache[c] = minprev + triangle[l][c];
				min = Math.min(min, cache[c]);
			}
		}
		return min;
	}
}
