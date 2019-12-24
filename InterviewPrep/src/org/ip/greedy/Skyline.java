package org.ip.greedy;

import java.util.Deque;
import java.util.LinkedList;

// EPI: 18.8
public class Skyline {
	public static void main(String[] s) {
		int[] heights = new int[] {1,4,2,5,6,3,2,6,6,5,2,1,3};
		System.out.println(solve(heights));
	}
	public static int solve(int[] heights) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int max = heights[0];
		stack.push(0);
		for (int i = 1; i < heights.length; i++) {
			while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
				int j = stack.pop();
				int k;
				if (stack.isEmpty()) {
					k = i;
				} else {
					k = stack.peek();
				}
				max = Math.max(max, heights[j] * (i - k - 1));
			}
			if (!stack.isEmpty() && heights[stack.peek()] == heights[i]) {
				stack.pop();
			}
			stack.push(i);
		}
		int min = Integer.MAX_VALUE;
		int i = !stack.isEmpty() ? stack.peek() : 0;
		while (!stack.isEmpty()) {
			int j = stack.pop();
			int k;
			if (stack.isEmpty()) {
				k = i;
			} else {
				k = stack.peek();
			}
			max = Math.max(max, heights[j] * (i - k - 1));
		}
		max = Math.max(max, i * min);
		return max;
	}
	public static class Pair {
		int index;
		int height;
		public Pair(int height, int index) {this.height=height;this.index=index;}
	}
}
