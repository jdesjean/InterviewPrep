package org.ip.array;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/397339/">OA 2020</a>
 */
public class MinEmployeeCost {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MinEmployeeCost::tc1, 
				MinEmployeeCost::tc2,
				MinEmployeeCost::tc3);
		Solver[] solvers = new Solver[] {new DP2(), new DP(), new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(1, 1, 1, new int[] {1,2,3,4}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(1, 1, 1, new int[] {4,3,2,1}));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(1, 1, 1, new int[] {4,0,0,4}));
	}
	public static class DP2 implements Solver {

		@Override
		public int solve(int h, int m, int s, int[] nums) {
			int min = 0, e = 0;
			Deque<Integer> fired = new LinkedList<Integer>();
			for (int i = 0; i < nums.length; i++) {
				if (e == nums[i]) {
					min = min + nums[i] * m;
				} else if (e < nums[i]) { // hire
					min = min + h * (nums[i] - e) + nums[i] * m;
					min += unfire(h, m, s, i, nums[i] - e, fired);
				} else if (e > nums[i]) { // fire
					min = min + s * (e - nums[i]) + nums[i] * m;
					for (int j = e; j > nums[i]; j--) {
						fired.push(i);
					}
				}
				e = nums[i];
			}
			min += s * e;
			return min;
		}
		private int unfire(int h, int m, int s, int i, int e, Deque<Integer> fired) {
			int neg = 0;
			for (; e > 0 && !fired.isEmpty(); e--) {
				int p = fired.remove();
				int dist = i - p;
				int minUnfired = dist * m - s - h;
				if (minUnfired < 0) {
					neg += minUnfired;
				} else {
					break;
				}
			}
			return neg;
		}
	}
	public static class DP implements Solver {

		@Override
		public int solve(int h, int m, int s, int[] nums) {
 			int max = Utils.max(nums);
			int[] prev = new int[max + 1];
			Arrays.fill(prev, Integer.MAX_VALUE);
			prev[0] = 0;
			for (int i = 0; i < nums.length; i++) {
				int[] next = new int[max + 1];
				Arrays.fill(next, Integer.MAX_VALUE);
				for (int j = max; j >= 0; j--) {
					if (prev[j] == Integer.MAX_VALUE) continue;
					if (j >= nums[i]) { // firing
						for (int k = j; k >= nums[i]; k--) {
							next[k] = Math.min(next[k], prev[j] + s * (j - k) + m * k);
						}
					} else { //hiring
						next[nums[i]] = Math.min(next[nums[i]], prev[j] + h * (nums[i] - j) + m * nums[i]);
					}
				}
				prev = next;
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < prev.length; i++) {
				min = Math.min(min, add(prev[i], i * s));
			}
			return min;
		}
	}
	public static int add(int a, int b) {
		int c = a + b;
		if (((a & b & ~c) | (~a & ~b & c)) < 0) {
			return Integer.MAX_VALUE;
		}
		return c;
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(int h, int m, int s, int[] nums) {
			return solve(h, m, s, nums, 0, 0, 0);
		}
		public int solve(int h, int m, int s, int[] nums, int i, int e, int c) {
			if (i == nums.length) {
				return c + e * s;
			}
			int min = Integer.MAX_VALUE;
			if (e < nums[i]) {
				int hired = nums[i] - e;
				min = solve(h, m, s, nums, i + 1, nums[i], c + hired * h + nums[i] * m);
			} else if (e > nums[i]) {
				for (int j = nums[i]; j <= e; j++) {
					int fired = e - j;
					min = Math.min(min, solve(h, m, s, nums, i + 1, j, c + fired * h + j * m));
				}
			}
			return min;
		}
	}
	public interface Solver {
		public int solve(int h, int m, int s, int[] nums);
	}
}
