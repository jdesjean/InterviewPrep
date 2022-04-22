package org.ip.matrix;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/knight-dialer/">LC: 935</a>
 */
public class KnightDialer {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {10, 1};
		Object[] tc2 = new Object[] {20, 2};
		Object[] tc3 = new Object[] {46, 3};
		Object[] tc4 = new Object[] {104, 4};
		Object[] tc5 = new Object[] {136006598, 3131};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {
		private final static int MOD = (int)Math.pow(10, 9) + 7;
		private final static long MOD_LONG = (int)Math.pow(10, 9) + 7;
		private static int[][] IDENTITY = {
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
	    };

		@Override
		public int applyAsInt(int n) {
			if (n < 1) return 0;
			if (n == 1) return 10;
			int[][] m = new int[][] {
		       {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
               {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
               {0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
               {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
               {1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
               {1, 1, 0, 0, 0, 0, 0, 1, 0, 0},
               {0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
               {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
               {0, 0, 1, 0, 1, 0, 0, 0, 0, 0}
			};
			int[][] res = matrixPower(m, n - 1);
			int sum = 0;
			for(int i=0;i<res.length;i++){
	            for(int j=0; j<res[0].length;j++)
	                sum = (sum+res[i][j])%MOD;
	        }
			return sum;
		}
		
		private int[][] matrixPower(int[][] base, int power) {
	        int[][] res = IDENTITY;
	        
	        for (int i = 1; i <= power; i <<= 1) {
	            if ((i & power) != 0) {
	                res = multiply(res, base);
	            }
	            base = multiply(base, base);
	        }
	        
	        return res;
	    }
	    
	    private int[][] multiply(int[][] multiplicand, int[][] multiplier) {
	        int[][] res = new int[multiplicand.length][multiplier[0].length];
	        
	        for (int i = 0; i < multiplicand.length; i++) {
	            for (int k = 0; k < multiplicand[0].length; k++) {
	                long cache = multiplicand[i][k];
	                for (int j = 0; j < multiplier[0].length; j++) {
	                	long mult = (cache * (long) multiplier[k][j]) % MOD_LONG;
	                    res[i][j] = (res[i][j] + (int) mult) % MOD;
	                }
	            }
	        }
	        
	        return res;
	    }
	}
	static class Solver2 implements Problem {

		
		private final static int MOD = (int)Math.pow(10, 9) + 7;
		@Override
		public int applyAsInt(int n) {
			if (n < 1) return 0;
			if (n == 1) return 10;
			/*
			 * 0,1,2,3
			 * 0,1,2,4 
			 */
			int[][] cache = new int[2][4];
			cache[0] = new int[] {1,4,2,2};
			int i = 1;
			for (; n > 1; n--, i = 1 - i) {
				int j = 1 - i;
				cache[i][0] = cache[j][3];
				cache[i][1] = ((cache[j][2] * 2) % MOD + (cache[j][3] * 2) % MOD) % MOD;
				cache[i][2] = cache[j][1];
				cache[i][3] = ((2 * cache[j][0]) % MOD + cache[j][1]) % MOD;
			}
			int sum = 0;
			for (int j = 0; j < 4; j++) {
				sum = (sum + cache[1 - i][j]) % MOD;
			}
			return sum;
		}
		
	}
	static class Solver implements Problem {

		//0,1,2,3,4,5,6,7,8,9
		//1,2,3,4,5,6,7,8,9,0
		private final static int[][] DIRS = new int[][]{{5,7},{6,8},{3,7},{2,8,9},{},{0,6,9},{1,5},{0,2},{1,3},{3,5}};
		private final static int MOD = (int)Math.pow(10, 9) + 7;
		@Override
		public int applyAsInt(int n) {
			if (n < 1) return 0;
			int[][] cache = new int[2][10];
			Arrays.fill(cache[0], 1);
			int i = 1;
			for (; n > 1; i = 1 - i, n--) {
				int j = 1 - i;
				for (int k = 0; k < DIRS.length; k++) {
					cache[i][k] = 0;
					for (int dir : DIRS[k]) {
						cache[i][k] = (cache[i][k] + cache[j][dir]) % MOD;
					}
				}
			}
			int sum = 0;
			for (int j = 0; j < 10; j++) {
				sum = (sum + cache[1 - i][j]) % MOD;
			}
			return sum;
		}
		
	}
	interface Problem extends IntUnaryOperator {
		
	}
}
