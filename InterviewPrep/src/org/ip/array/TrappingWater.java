package org.ip.array;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/trapping-rain-water/">LC: 42</a>
 */
public class TrappingWater {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				TrappingWater::tc1,
				TrappingWater::tc2,
				TrappingWater::tc3,
				TrappingWater::tc4);
		Solver[] solvers = new Solver[] {new StackSolver(), new DP(), new Pointers()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {0,1,0,2,1,0,1,3,2,1,2,1}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {2,1,1,2}));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new int[] {1,1,0,1}));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new int[] {4,2,0,3,2,5}));
	}
	public static class Pointers implements Solver {

		@Override
		public int solve(int[] height) {
			int res = 0;
			int left = 0;
			int right = 0;
			// TODO: Fix start length 3
			for (int i = 0, j = height.length - 1; i <= j;) {
				if (left <= right) {
					if (height[i] > left) {
						left = height[i];
					} else {
						res += left - height[i];
					}
					i++;
				} else {
					if (height[j] > right) {
						right = height[j];
					} else {
						res += right - height[j];
					}
					j--;
				}
			}
			return res;
		}
		
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] height) {
			int[] left = new int[height.length];
			int[] right = new int[height.length];
			left[0] = height[0];
			right[height.length - 1] = height[height.length - 1];
			for (int i = 1; i < height.length; i++) {
				left[i] = Math.max(left[i - 1], height[i]);
			}
			for (int i = height.length - 2; i >= 0; i--) {
				right[i] = Math.max(right[i + 1], height[i]);
			}
			int res = 0;
			for (int i = 0; i < height.length; i++) {
				int min = Math.min(left[i], right[i]);
				if (height[i] < min) {
					res += min - height[i];
				}
			}
			return res;
		}
		
	}
	public static class StackSolver implements Solver {

		@Override
		public int solve(int[] height) {
			Deque<Integer> stack = new LinkedList<Integer>();
			int res = 0;
			for (int i = 0; i < height.length; i++) {
				if (stack.isEmpty()) {
					stack.push(i);
				} else if (height[stack.peek()] > height[i]) {
					stack.push(i);
				} else if (height[stack.peek()] == height[i]) {
					stack.pop();
					stack.push(i);
				} else {
					int prev = i > 0 ? height[i - 1] : 0;
					while (!stack.isEmpty() && height[stack.peek()] <= height[i]) {
						int pIndex = stack.pop();
						int iDiff = i - pIndex - 1; 
						if (iDiff > 0) {
							int water = (height[pIndex] - prev) * iDiff; 
							res += water;
						}
						prev = height[pIndex];
					}
					if (!stack.isEmpty()) {
						int water = (height[i] - prev) * (i - stack.peek() - 1);
						res += water;
					}
					stack.push(i);
				}
			}
			return res;
		}
		
	}
	public interface Solver {
		public int solve(int[] height);
	}
}
