package org.ip.recursion;

// EPI: 17.3
public class Paths {
	public static void main(String[] s) {
		System.out.println(count(new int[][]{{1,1,1,1},{1,1,1,1},{1,1,1,1}}));
		System.out.println(count(new int[][]{{1,1},{0,1}}));
	}
	public static int count(int[][] matrix) {
		if (matrix.length == 0) return 0;
		int[] cache = new int[matrix[0].length];
		for (int i = 0; i < cache.length; i++) {
			cache[i] = matrix[0][i];
		}
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				cache[j] = j == 0 ? cache[j] : cache[j-1]+cache[j];
				cache[j]*=matrix[i][j];
			}
		}
		return cache[cache.length-1];
	}
}
