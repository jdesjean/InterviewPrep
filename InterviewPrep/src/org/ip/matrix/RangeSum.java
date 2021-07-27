package org.ip.matrix;

/**
 * <a href="https://leetcode.com/problems/range-sum-query-2d-immutable/">LC: 304</a>
 */
public class RangeSum {
	public static void main(String[] s) {
		NumMatrix1 numMatrix1 = new NumMatrix1(new int[][] {
			{3, 0, 1, 4, 2},
			{5, 6, 3, 2, 1},
			{1, 2, 0, 1, 5},
			{4, 1, 0, 1, 7},
			{1, 0, 3, 0, 5}
		});
		System.out.println(numMatrix1.sumRegion(2, 1, 4, 3));
		System.out.println(numMatrix1.sumRegion(1,1,2,2));
		System.out.println(numMatrix1.sumRegion(1,2,2,4));
		
		NumMatrix2 numMatrix2 = new NumMatrix2(new int[][] {
			{3, 0, 1, 4, 2},
			{5, 6, 3, 2, 1},
			{1, 2, 0, 1, 5},
			{4, 1, 0, 1, 7},
			{1, 0, 3, 0, 5}
		});
		System.out.println(numMatrix2.sumRegion(2, 1, 4, 3));
		System.out.println(numMatrix2.sumRegion(1,1,2,2));
		System.out.println(numMatrix2.sumRegion(1,2,2,4));
	}
	
	static class NumMatrix2 {

	    private int[][] matrix;

		public NumMatrix2(int[][] matrix) {
	        for (int l = 0; l < matrix.length; l++) {
	        	for (int c = 0; c < matrix[l].length; c++) {
	        		int left = c > 0 ? matrix[l][c - 1] : 0;
	        		int top = l > 0 ? matrix[l - 1][c] : 0;
	        		int tl = c > 0 && l > 0 ? matrix[l - 1][c - 1] : 0;
	        		matrix[l][c] += left + top - tl;
	        	}
	        }
	        this.matrix = matrix;
	    }
	    
	    
	    
	    public int sumRegion(int row1, int col1, int row2, int col2) {
	        int bl = col1 > 0 ? matrix[row2][col1 - 1] : 0;
	        int bt = row1 > 0 ? matrix[row1 - 1][col2] : 0;
	        int bd = col1 > 0 && row1 > 0 ? matrix[row1 - 1][col1 - 1] : 0;
	        return matrix[row2][col2] - bl - bt + bd;
	    }
	}
	
	static class NumMatrix1 {

	    private int[][] matrix;

		public NumMatrix1(int[][] matrix) {
	        for (int l = 0; l < matrix.length; l++) {
	        	matrix[l] = cumSum(matrix[l]);
	        }
	        this.matrix = matrix;
	    }
	    
	    int[] cumSum(int[] line) {
	    	int sum = 0;
	    	for(int c = 0; c < line.length; c++) {
	    		sum += line[c];
	    		line[c] = sum;
	    	}
	    	return line;
	    }
	    
	    public int sumRegion(int row1, int col1, int row2, int col2) {
	    	int sum = 0;
	        for (int l = row1; l <= row2; l++) {
	        	int prev = col1 > 0 ? matrix[l][col1 - 1] : 0;
	        	sum += matrix[l][col2] - prev;
	        }
	        return sum;
	    }
	}
}
