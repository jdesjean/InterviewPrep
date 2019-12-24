package org.ip.recursion;

// EPI 17.5
public class Matrix {
	public static void main(String[] s) {
		int[][] matrix = new int[][] {{1,2,3},{3,4,5},{5,6,7}};
		System.out.println(new RecursiveSolve().solve(matrix, new int[] {1,3,4,6}));
		System.out.println(new Recursive2Solve().solve(matrix, new int[] {1,3,4,6}));
		System.out.println(new DPSolve().solve(matrix, new int[] {1,3,4,6}));
		System.out.println(new RecursiveSolve().solve(matrix, new int[] {1,2,3,4}));
		System.out.println(new Recursive2Solve().solve(matrix, new int[] {1,2,3,4}));
		System.out.println(new DPSolve().solve(matrix, new int[] {1,2,3,4}));
	}
	public interface Solve {
		public boolean solve(int[][] matrix, int[] pattern);
	}
	public static class RecursiveSolve implements Solve {

		@Override
		public boolean solve(int[][] matrix, int[] pattern) {
			return solve(matrix, pattern, 0, 0) >= 0;
		}
		public int solve(int[][] matrix, int[] pattern, int i, int p) {
			while ((p = find(matrix, pattern[i], p)) != -1) {
				if (i == pattern.length - 1) return p;
				int pos = -1;
				while ((pos = solve(matrix, pattern, i + 1, pos + 1)) != -1) {
					if (pos == p - matrix[0].length || pos == p + matrix[0].length || pos == p + 1 || pos == p-1) {
						return p;
					}
				}
				p++;
			}
			return -1;
		}
		public int find(int[][] matrix, int k, int kk) {
			int l = kk / matrix.length;
			int c = kk % matrix.length;
			for (; l < matrix.length; l++) {
				for (; c < matrix[0].length; c++) {
					if (matrix[l][c] == k) return l * matrix[0].length + c;
				}
				c = 0;
			}
			return -1;
		}
		
	}
	public static class Recursive2Solve implements Solve {

		@Override
		public boolean solve(int[][] matrix, int[] pattern) {
			return solve(matrix, pattern, -1, -1, 0);
		}
		public boolean solve(int[][] matrix, int[] pattern, int i, int j, int k) {
			if (k == pattern.length) {
				return true;
			}
			boolean found = false;
			for (int l = 0; l < matrix.length; l++) {
				for (int c = 0; c < matrix[0].length; c++) {
					if (matrix[l][c] != pattern[k]) continue;
					if (i == -1 && j == -1) {
						found = solve(matrix, pattern, l, c, k + 1);
					} else if (i + 1 == l && j == c) {
						found = solve(matrix, pattern, l, c, k + 1);
					} else if (i - 1 ==l && j == c) {
						found = solve(matrix, pattern, l, c, k + 1);
					} else if (i == l && j - 1 == c) {
						found = solve(matrix, pattern, l, c, k + 1);
					} else if (i == l && j + 1 == c) {
						found = solve(matrix, pattern, l, c, k + 1);
					}
					if (found) {
						return found;
					}
				}
			}
			return false;
		}
	}
	public static class DPSolve implements Solve {

		@Override
		public boolean solve(int[][] matrix, int[] pattern) {
			int[][] cache = new int[matrix.length][matrix[0].length];
			boolean found = false;
			for (int i = 0; i < pattern.length; i++) {
				found = false;
				for (int l = 0; l < matrix.length; l++) {
					for (int c = 0; c < matrix[0].length; c++) {
						if (matrix[l][c] == pattern[i]) {
							//cache[l][c-1] || cache[l][c+1] || cache[l+1][c] || cache[l-1][c]
							if (i == 0) {
								cache[l][c] = 1;
								found = true;
							} else if (c > 0 && cache[l][c-1] == i){
								cache[l][c] = i + 1;
								found = true;
							} else if (c < matrix[0].length - 1 && cache[l][c+1] == i) {
								cache[l][c] = i + 1;
								found = true;
							} else if (l < cache.length - 1 && cache[l+1][c] == i) {
								cache[l][c] = i + 1;
								found = true;
							} else if (l > 0 && cache[l-1][c] == i) {
								cache[l][c] = i + 1;
								found = true;
							}
						}
					}
				}
			}
			return found;
		}
		
	}
}
