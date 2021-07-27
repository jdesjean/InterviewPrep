package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/number-of-closed-islands/">LC: 1254</a>
 */
public class CountClosedIsland {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 2, new int[][] {
			{1,1,1,1,1,1,1,0},
			{1,0,0,0,0,1,1,0},
			{1,0,1,0,1,1,1,0},
			{1,0,0,0,0,1,0,1},
			{1,1,1,1,1,1,1,0}
		}};
		Object[] tc2 = new Object[] { 1, new int[][] {
			{0,0,1,0,0},
			{0,1,0,1,0},
			{0,1,1,1,0}
		}};
		Object[] tc3 = new Object[] { 5, new int[][] {
			{0,0,1,1,0,1,0,0,1,0},
			{1,1,0,1,1,0,1,1,1,0},
			{1,0,1,1,1,0,0,1,1,0},
			{0,1,1,0,0,0,0,1,0,1},
			{0,0,0,0,0,0,1,1,1,0},
			{0,1,0,1,0,1,0,1,1,1},
			{1,0,1,0,1,1,0,0,0,1},
			{1,1,1,1,1,1,0,0,0,0},
			{1,1,1,0,0,1,0,1,0,1},
			{1,1,1,0,1,1,0,1,1,0}
		}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private static final int[][] directions = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
		enum State {
			VISITING(2), CLOSED(3), OPEN(4);
			
			public final int value;
			
			State(int value) {
				this.value = value;
			}
		}
		@Override
		public int applyAsInt(int[][] value) {
			int count = 0;
			for (int row = 0; row < value.length; row++) {
				for (int col = 0; col < value[row].length; col++) {
					if (value[row][col] != 0) continue;
					if (!_apply(value, row, col)) continue;
					count++;
				}
			}
			return count;
		}
		boolean _apply(int[][] value, int row, int col) {
			if (value[row][col] != 0) {
				return value[row][col] != State.CLOSED.value;
			}
			value[row][col] = State.VISITING.value;
			boolean result = true;
			for (int i = 0; i < directions.length; i++) {
				int nextRow = row + directions[i][0];
				int nextCol = col + directions[i][1];
				// not returning right away on false to fill all zeros
				result &= nextRow >= 0 && nextCol >= 0 && nextRow < value.length && nextCol < value[nextRow].length;
				result = result && _apply(value, nextRow, nextCol); // using && for short circuit
			}
			value[row][col] = result ? State.OPEN.value : State.CLOSED.value;
			return result;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
