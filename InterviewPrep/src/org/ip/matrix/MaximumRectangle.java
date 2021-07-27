package org.ip.matrix;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximal-rectangle/">LC: 85</a>
 */
public class MaximumRectangle {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6, new char[][] {
			{'1','0','1','0','0'},
			{'1','0','1','1','1'},
			{'1','1','1','1','1'},
			{'1','0','0','1','0'}}};
		Object[] tc2 = new Object[] {0, new char[][] {}};
		Object[] tc3 = new Object[] {0, new char[][] {{'0'}}};
		Object[] tc4 = new Object[] {1, new char[][] {{'1'}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public int applyAsInt(char[][] value) {
			if (value.length == 0) return 0;
			int width = value[0].length;
			int[] lefts = new int[width];
			int[] rights = new int[width];
			int[] heights = new int[width];
			Arrays.fill(rights, width);
			int max = 0;
			for (int r = 0; r < value.length; r++) {
				int curRight = width, curLeft = 0;
				for (int c = 0; c < width; c++) {
					if (value[r][c] != '0') {
						heights[c] = heights[c] + 1;
						lefts[c] = Math.max(lefts[c], curLeft);
					} else {
						heights[c] = 0;
						lefts[c] = 0;
						curLeft = c + 1;
					}
				}
				for (int c = width - 1; c >= 0; c--) {
					if (value[r][c] != '0') {
						rights[c] = Math.min(rights[c], curRight);
					} else {
						rights[c] = width;
						curRight = c;
					}
					max = Math.max(max, (rights[c] - lefts[c]) * heights[c]);
				}
			}
			return max;
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(char[][] value) {
			if (value.length == 0) return 0;
			int[] cache = new int[value[0].length];
			int max = 0;
			for (int r = 0; r < value.length; r++) {
				for (int c = 0; c < value[r].length; c++) {
					cache[c] = value[r][c] != '0' ? cache[c] + 1 : 0;
				}
				max = Math.max(max, maxHistogram(cache));
			}
			return max;
		}
		int maxHistogram(int[] heights) {
			Deque<Integer> stack = new ArrayDeque<>(heights.length);
			int max = 0;
			for (int c = 0; c < heights.length; c++) {
				while (!stack.isEmpty() && heights[stack.peek()] >= heights[c]) {
					int height = heights[stack.pop()];
					int width = c - (stack.isEmpty() ? -1 : stack.peek()) - 1;
					max = Math.max(max, width * height);
				}
				stack.push(c);
			}
			while (!stack.isEmpty()) {
				int height = heights[stack.pop()];
				int width = heights.length - (stack.isEmpty() ? -1 : stack.peek()) - 1;
				max = Math.max(max, width * height);
			}
			return max;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(char[][] value) {
			if (value.length == 0) return 0;
			int[][] cache = new int[value.length][value[0].length];
			for (int r = 0; r < value.length; r++) {
				int count = 0;
				for (int c = 0; c < value[r].length; c++) {
					cache[r][c] = value[r][c] != '0' ? ++count : (count = 0);
				}
			}
			Deque<Integer> stack = new ArrayDeque<>(value.length);
			int max = 0;
			for (int c = 0; c < cache[0].length; c++) {
				for (int r = 0; r < cache.length; r++) {
					while (!stack.isEmpty() && cache[stack.peek()][c] >= cache[r][c]) {
						int height = cache[stack.pop()][c];
						int width = r - (stack.isEmpty() ? -1 : stack.peek()) - 1;
						max = Math.max(max, width * height);
					}
					stack.push(r);
				}
				while (!stack.isEmpty()) {
					int height = cache[stack.pop()][c];
					int width = cache.length - (stack.isEmpty() ? -1 : stack.peek()) - 1;
					max = Math.max(max, width * height);
				}
			}
			return max;
		}
		
	}
	interface Problem extends ToIntFunction<char[][]> {
		
	}
}
