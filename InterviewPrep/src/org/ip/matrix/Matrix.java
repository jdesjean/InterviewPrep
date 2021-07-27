package org.ip.matrix;

import org.ip.array.Utils;

public class Matrix {
	public static void main(String[] s) {
		testRotate();
	}
	public static void testRotate() {
		int[][] array = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		rotate(array);
		Utils.println(array);
		array = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
		rotate(array);
		Utils.println(array);
		array = new int[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
		rotate(array);
		Utils.println(array);
	}
	public static void testFind() {
		System.out.println(find(new int[][]{{0,3,6,9},{1,4,7,10},{2,5,8,11}},0));
		System.out.println(find(new int[][]{{0,3,6,9},{1,4,7,10},{2,5,8,11}},2));
		System.out.println(find(new int[][]{{0,3,6,9},{1,4,7,10},{2,5,8,11}},12));
	}
	public static void testMaximizer() {
		SubMatrixMaximizer[] maximizer = new SubMatrixMaximizer[]{new SubMatrixMaximizerDP1(),new SubMatrixMaximizerDP2()};
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
		MatrixMultiplicationMinimizer[] minimizers = new MatrixMultiplicationMinimizer[]{new MatrixMultiplicationMinimizerRecursive(), new MatrixMultiplicationMinimizerDP()};
		int[] matrices = new int[]{40,20,30,10,30};
		for (int i = 0; i < minimizers.length; i++) {
			t1 = System.currentTimeMillis();
			System.out.println(minimizers[i].minimum(matrices));
			t2 = System.currentTimeMillis();
			System.out.println("t"+(t2-t1));
		}
	}
	public interface SubMatrixMaximizer {
		public int maximum(boolean[][] array);
	}
	public static class SubMatrixMaximizerDP1 implements SubMatrixMaximizer{

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
	public static class SubMatrixMaximizerDP2 implements SubMatrixMaximizer{

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
	public interface MatrixMultiplicationMinimizer {
		public int minimum(int[] matrices);
	}
	public static class MatrixMultiplicationMinimizerRecursive implements MatrixMultiplicationMinimizer {

		@Override
		public int minimum(int[] matrices) {
			return minimize(matrices,1,matrices.length-1);
		}
		private static int minimize(int[] matrices, int i, int j) {
			if(i == j) return 0;
		    int min = Integer.MAX_VALUE;
		 
		    for (int k = i; k < j; k++)
		    {
		        int count = minimize(matrices, i, k) +
		        		minimize(matrices, k+1, j) +
		        		matrices[i-1]*matrices[k]*matrices[j];
		 
		        min = Math.min(min, count);
		    }
		 
		    return min;
		}
		
	}
	public static class MatrixMultiplicationMinimizerDP implements MatrixMultiplicationMinimizer {

		@Override
		public int minimum(int[] matrices) {
			int[][] cache = new int[matrices.length][matrices.length];
			for (int length = 2; length < matrices.length; length++) {
				for (int i = 1, j=i+length-1; i <= matrices.length-length; i++,j++) {
					cache[i][j] = Integer.MAX_VALUE;
					for (int k = i; k < j; k++) {
						int count = cache[i][k] + cache[k+1][j] + matrices[i-1]*matrices[k]*matrices[j];
						cache[i][j] = Math.min(count, cache[i][j]);
					}
				}
			}
			return cache[1][matrices.length-1];
		}
		
	}
	public static boolean find(int[][] sorted, int value) {
		for (int l = 0, c = sorted[0].length-1; l < sorted.length && c >= 0;) {
			if (sorted[l][c] == value) return true;
			else if (value < sorted[l][c]) c--;
			else l++;
		}
		return false;
	}
	public static void rotate(int[][] matrix) {
		for (int start = 0, end = matrix.length-1; start < Math.ceil(0.5*matrix.length); start++, end--) {
			int length = end-start;
			for (int i = 0; i < length; i++) {
				int startCurrent = start + i;
				int endCurrent = end - i;
				int tmp = matrix[start][startCurrent];
				matrix[start][startCurrent] = matrix[endCurrent][start];
				matrix[endCurrent][start] = matrix[end][endCurrent];
				matrix[end][endCurrent] = matrix[startCurrent][end];
				matrix[startCurrent][end] = tmp;
			}
		}
	}
	
}
