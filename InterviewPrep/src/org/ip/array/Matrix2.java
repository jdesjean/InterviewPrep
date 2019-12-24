package org.ip.array;

public class Matrix2 {
	public static void main(String[] s) {
		testMaximizer();
	}
	public static void testMaximizer() {
		SubMatrixMaximizer[] maximizer = new SubMatrixMaximizer[]{new SubMatrixMaximizerDP()};
		/*
		 * 0 1 1 0 1
		 * 1 1 0 1 0
		 * 0 1 1 1 0
		 * 1 1 1 1 0
		 * 1 1 1 1 1
		 * 0 0 0 0 0
		 */
		boolean[][] matrix = new boolean[][] {
			{false,true,true,false,true},
			{true,true,false,true,false},
			{false,true,true,true,false},
			{true,true,true,true,false},
			{true,true,true,true,true},
			{false,false,false,false,false}};
		for (int i = 0; i < maximizer.length; i++) {
			System.out.println(maximizer[i].maximum(matrix));
		}
	}
	public interface SubMatrixMaximizer {
		public int maximum(boolean[][] array);
	}
	public static class SubMatrixMaximizerDP implements SubMatrixMaximizer {

		@Override
		public int maximum(boolean[][] array) {
			int[] cache = new int[array.length];
			int max = 0;
			for (int r = 0; r < array.length; r++) {
				int prev = 0;
				for (int c = 0; c < array[c].length; c++) {
					if (!array[r][c]) continue;
					/*
					 * prev = cache[r-1][l-1]
					 * cache[r-1] = cache[r-1][l]
					 * cache[r] = cache[r][l-1]
					 */
					int min = r == 0 || c == 0 ? 0 : min(cache[r-1], prev, cache[r]);
					prev = cache[r];
					cache[r] = 1 + min;
					max = Math.max(max, cache[r]);
				}
			}
			return max;
		}
		private int min(int a, int b, int c) {
			return Math.min(Math.min(a, b), c);
		}
	}
}
