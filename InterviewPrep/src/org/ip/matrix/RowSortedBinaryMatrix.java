package org.ip.matrix;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/leftmost-column-with-at-least-a-one/">LC:1428</a>
 */
public class RowSortedBinaryMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new BinaryMatrix(new boolean[][] {
				{false, false},
				{true, true}}), 0};
		Object[] tc2 = new Object[] {new BinaryMatrix(new boolean[][] {
			{false, false},
			{false, true}}), 1};
		Object[] tc3 = new Object[] {new BinaryMatrix(new boolean[][] {
			{false, false},
			{false, false}}), -1};
		Object[] tc4 = new Object[] {new BinaryMatrix(new boolean[][] {
			{false, false, false, false},
			{false, false, false, true},
			{false, false, true, true},
			{false, true, true, true}}), 1};
		Object[] tc5 = new Object[] {new BinaryMatrix(new boolean[][] {
			{true, true, true, true, true},
			{false, false, false, true, true},
			{false, false, true, true, true},
			{false, false, false, false, true},
			{false, false, false, false, false}}), 0};
		List<Object[]> tcs = Arrays.asList(tc5, tc3, tc1, tc2, tc4);
		Function<BinaryMatrix, String>[] solvers = new Function[] {new Solver()};
		for (Object[] tc : tcs) {
			for (Function<BinaryMatrix, String> solver : solvers) {
				System.out.print(String.valueOf(solver.apply((BinaryMatrix) tc[0])) + "," + String.valueOf(tc[1]));
			}
			System.out.println();
		}
	}
	public static class Solver implements Function<BinaryMatrix, Integer> {

		@Override
		public Integer apply(BinaryMatrix t) {
			int cols = t.dimensions().get(1);
			int rows = t.dimensions().get(0);
			int min = Integer.MAX_VALUE;
			for (int row = 0; row < rows; row++) {
				int index = search(t, row, cols);
				if (index == -1) continue;
				min = Math.min(index, min);
			}
			return min == Integer.MAX_VALUE ? -1 : min;
		}
		int search(BinaryMatrix t, int row, int cols) {
			int res = -1;
			for (int left = 0, right = cols - 1; left <= right;) {
				int mid = left + (right - left) / 2;
				int value = t.get(row, mid);
				if (value == 1) {
					res = mid;
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			return res;
		}
	}
	public static class BinaryMatrix {
		private final boolean[][] m;
		private final List<Integer> d;
		public BinaryMatrix(boolean[][] m) {
			this.m = m;
			if (m == null) {
				d = Arrays.asList(0, 0);
			} else if (m.length == 0) {
				d = Arrays.asList(0, 0);
			} else {
				d = Arrays.asList(m.length, m[0].length);
			}
		}
		public int get(int row, int col) {
			return m[row][col] ? 1 : 0;
		}
		public List<Integer> dimensions() {
			return d;
		}
	}
}
