package org.ip;

public class Matrix {
	public static void main(String[] s) {
		SubMatrixMaximizer[] maximizer = new SubMatrixMaximizer[]{new SubMatrixMaximizer1(),new SubMatrixMaximizer2()};
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
		long t1,t2;
		for (int i = 0; i < maximizer.length; i++) {
			t1 = System.currentTimeMillis();
			System.out.println(maximizer[i].maximum(matrix));
			t2 = System.currentTimeMillis();
			System.out.println("t"+(t2-t1));
		}
	}
	public interface SubMatrixMaximizer {
		public int maximum(boolean[][] array);
	}
	public static class SubMatrixMaximizer1 implements SubMatrixMaximizer{

		@Override
		public int maximum(boolean[][] array) {
			if (array.length == 0) return 0;
			int[][] cache = new int[array.length][array[0].length];
			int max = 0;
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					if (!array[i][j]) continue;
					int min = i == 0 || j == 0 ? 0 : min(cache[i-1][j-1], cache[i-1][j], cache[i][j-1]);
					cache[i][j] = 1 + min;
					max = Math.max(max, cache[i][j]);
				}
			}
			return max;
		}
	}
	public static class SubMatrixMaximizer2 implements SubMatrixMaximizer{

		@Override
		public int maximum(boolean[][] array) {
			if (array.length == 0) return 0;
			int[] cache = new int[array.length];
			int max = 0;
			for (int i = 0, prev = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					if (!array[i][j]) continue;
					/*
					 * prev = cache[i-1][j-1]
					 * cache[j] = cache[i-1][j]
					 * cache[j-1] = cache[i][j-1]
					 */
					int min = i == 0 || j == 0 ? 0 : min(prev, cache[j], cache[j-1]);
					prev = cache[j];
					cache[j] = 1 + min;
					max = Math.max(max, cache[j]);
				}
			}
			return max;
		}
	}
	public static int min(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}
}
