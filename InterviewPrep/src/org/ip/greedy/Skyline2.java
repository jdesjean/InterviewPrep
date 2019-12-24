package org.ip.greedy;

import java.util.Deque;
import java.util.LinkedList;

// EPI 2018: 17.8
public class Skyline2 {
	public static void main(String[] s) {
		int[] heights = new int[] {1,4,2,5,6,3,2,6,6,5,2,1,3};
		System.out.println(new Skyline2().solve(heights));
	}
	public int solve(int[] heights) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int max = 0;
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
		return max;
	}
}
