package org.ip.matrix;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/sparse-matrix-multiplication/">LC: 311</a>
 */
public class SparseMatrixMultiplication {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 
			new int[][] {
				{7,0,0},
				{-7,0,3}
			}, 
			new int[][] {
				{1,0,0},
				{-1,0,3}
				},
			new int[][]{
				{7,0,0},
				{0,0,0},
				{0,0,1}
			}};
		Object[] tc2 = new Object[] {
			new int[][] {
				{17}
			},
			new int[][] {
				{1,-5}
			},
			new int[][] {
				{12},
				{-1}
			}
		};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver1(), new Solver2(), new Solver3(), new Solver4() };
		Test.apply(solvers, tcs);
	}
	static class Solver4 implements Problem {

		@Override
		public int[][] apply(int[][] a, int[][] b) {
			int m = a.length, n = a[0].length, nB = b[0].length;
	        int[][] res = new int[m][nB];
	        int[][] sparsea = sparseHorizontal(a);
	        int[][] sparseb = sparseHorizontal(b);

	        for(int l = 0; l < m; l++) {
	            for(int i = 0; i < sparsea[l].length; i++) {
	            	int j = sparsea[l][i];
	            	for (int k = 0; k < sparseb[j].length; k++) {
	            		int c = sparseb[j][k];
	            		res[l][c] += a[l][j] * b[j][c];
	            	}
	            }
	        }
	        return res;
		}
		
		int[][] sparseHorizontal(int[][] t) {
			int[][] res = new int[t.length][];
			for (int l = 0; l < t.length; l++) {
				int count = 0;
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) count++;
				}
				res[l] = new int[count];
				for (int c = 0, k = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) res[l][k++] = c;
				}
			}
			return res;
		}
		
	}
	static class Solver3 implements Problem {

		@Override
		public int[][] apply(int[][] a, int[][] b) {
			int m = a.length, n = a[0].length, nB = b[0].length;
	        int[][] res = new int[m][nB];
	        int[][] sparsea = sparseHorizontal(a);
	        int[][] sparseb = sparseVertical(b);
	        for(int l = 0; l < m; l++) {
	        	for (int c = 0; c < nB; c++) {
	        		res[l][c] += mult(a, b, sparsea, sparseb, l, c);
	        	}
	        }
	        return res;
		}
		int mult(int[][] a, int b[][], int[][] sparsea, int[][] sparseb, int l, int c) {
			int[] va = sparsea[l];
			int[] vb = sparseb[c];
			if (vb.length < va.length) {
				int[] tmp = va;
				va = vb;
				vb = tmp;
			}
			int sum = 0;
			for (int i = 0; i < va.length; i++) {
				int j = va[i];
				sum += a[l][j] * b[j][c];
			}
			return sum;
		}
		int[][] sparseHorizontal(int[][] t) {
			int[][] res = new int[t.length][];
			for (int l = 0; l < t.length; l++) {
				int count = 0;
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) count++;
				}
				res[l] = new int[count];
				for (int c = 0, k = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) res[l][k++] = c;
				}
			}
			return res;
		}
		int[][] sparseVertical(int[][] t) {
			int[][] res = new int[t[0].length][];
			for (int c = 0; c < t[0].length; c++) {
				int count = 0;
				for (int l = 0; l < t.length; l++) {
					if (t[l][c] != 0) count++;
				}
				res[c] = new int[count];
				for (int l = 0, ll = 0; l < t.length; l++) {
					if (t[l][c] != 0) res[c][ll++] = l;
				}
			}
			return res;
		}
		
		
	}
	static class Solver2 implements Problem {

		@Override
		public int[][] apply(int[][] a, int[][] b) {
			int m = a.length, n = a[0].length, nB = b[0].length;
	        int[][] res = new int[m][nB];
	        int[][] sparsea = sparseHorizontal(a);

	        for(int l = 0; l < m; l++) {
	            for(int i = 0; i < sparsea[l].length; i++) {
	            	int j = sparsea[l][i];
	            	for (int c = 0; c < nB; c++) {
                        res[l][c] += a[l][j] * b[j][c];
                    }
	            }
	        }
	        return res;
		}
		
		int[][] sparseHorizontal(int[][] t) {
			int[][] res = new int[t.length][];
			for (int l = 0; l < t.length; l++) {
				int count = 0;
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) count++;
				}
				res[l] = new int[count];
				for (int c = 0, k = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) res[l][k++] = c;
				}
			}
			return res;
		}
		
	}
	static class Solver1 implements Problem {

		@Override
		public int[][] apply(int[][] a, int[][] b) {
			int m = a.length, n = a[0].length, nB = b[0].length;
	        int[][] res = new int[m][nB];

	        for(int l = 0; l < m; l++) {
	            for(int i = 0; i < n; i++) {
	                if (a[l][i] == 0) continue;
	                for (int c = 0; c < nB; c++) {
                    	res[l][c] += a[l][i] * b[i][c];
                    }
	            }
	        }
	        return res;
		}
		
	}
	interface Problem extends BiFunction<int[][], int[][], int[][]> {
		
	}
}
