package org.ip.array;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/largest-rectangle-in-histogram/">LC: 84</a>
 */
public class LargestRectangle {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {10, new int[] {2,1,5,6,2,3}};
		Object[] tc2 = new Object[] {4, new int[] {2,4}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] heights) {
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
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
