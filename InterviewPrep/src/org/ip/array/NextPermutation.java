package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/next-permutation/">LC: 31</a>
 */
public class NextPermutation {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				NextPermutation::tc1,
				NextPermutation::tc2,
				NextPermutation::tc3);
		Solver[] solvers = new Solver[] {new Iterative()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		//1,3,2
		int[] a = new int[] {1,2,3};
		solver.solve(a);
		Utils.println(a);
	}
	public static void tc2(Solver solver) {
		//1,2,3
		int[] a = new int[] {3,2,1};
		solver.solve(a);
		Utils.println(a);
	}
	public static void tc3(Solver solver) {
		//1,5,1
		int[] a = new int[] {1,1,5};
		solver.solve(a);
		Utils.println(a);
	}
	public static void tc4(Solver solver) {
		//1
		int[] a = new int[] {1};
		solver.solve(a);
		Utils.println(a);
	}
	public static class Iterative implements Solver {

		@Override
		public void solve(int[] nums) {
			int l = nums.length - 2;
			// first number decreasing (right to left)
			for (; l >= 0; l--) {
				if (nums[l] < nums[l + 1]) break;
			}
			// l < 0 all decreasing (left to right)
			if (l >= 0) {
				int r = nums.length - 1;
				for (; r >= l; r--) {
					if (nums[l] < nums[r]) break;
				}
				Utils.swap(nums, l, r);
			}
			Utils.reverse(nums, l + 1, nums.length - 1);
		}
		
	}
	public interface Solver {
		public void solve(int[] nums);
	}
}
