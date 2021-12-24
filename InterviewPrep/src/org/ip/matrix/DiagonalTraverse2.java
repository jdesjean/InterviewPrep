package org.ip.matrix;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/diagonal-traverse/">LC: 498</a>
 */
public class DiagonalTraverse2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,2,4,7,5,3,6,8,9}, new int[][] {
			{1,2,3},
			{4,5,6},
			{7,8,9}
		}};
		Object[] tc2 = new Object[] { new int[] {1,2,4,5,3,6}, new int[][] {
			{1,2,3},
			{4,5,6}
		}};
		Object[] tc3 = new Object[] { new int[] {1,2,3,5,4,6}, new int[][] {
			{1,2},
			{3,4},
			{5,6}
		}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver2(), new Solver() };
		Test.apply(solvers, tcs);
	}
	
	static class Solver implements Problem {
		
		@Override
		public int[] apply(int[][] t) {
			
		}
		
	}
	interface Problem extends Function<int[][], int[]> {
		
	}
}
