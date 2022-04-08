package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/trapping-rain-water/">LC: 42</a>
 */
public class TrappingWater {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6, new int[] {0,1,0,2,1,0,1,3,2,1,2,1}};
		Object[] tc2 = new Object[] {2, new int[] {2,1,1,2}};
		Object[] tc3 = new Object[] {1, new int[] {1,1,0,1}};
		Object[] tc4 = new Object[] {9, new int[] {4,2,0,3,2,5}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new StackSolver(), new DP(), new Pointers() };
		Test.apply(solvers, tcs);
	}
	static class Pointers implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int res = 0;
			for (int l = 0, lmax = 0, r = value.length - 1, rmax = 0; l < r;) {
				if (value[l] <= value[r]) {
					if (value[l] > lmax) {
						lmax = value[l];
					} else {
						res += lmax - value[l];
					}
					l++;
				} else {
					if (value[r] > rmax) {
						rmax = value[r];
					} else {
						res += rmax - value[r];
					}
					r--;
				}
			}
			return res;
		}
		
	}
	public static class DP implements Problem {

		@Override
		public int applyAsInt(int[] height) {
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
	public static class StackSolver implements Problem {

		@Override
		public int applyAsInt(int[] height) {
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
	public interface Problem extends ToIntFunction<int[]> {
		
	}
}
