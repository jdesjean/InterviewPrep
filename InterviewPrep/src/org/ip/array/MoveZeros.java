package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.ip.array.ThreeSum.MapSolver;
import org.ip.array.ThreeSum.Solver;
import org.ip.array.ThreeSum.SortSolver;

/**
 * <a href="https://leetcode.com/problems/move-zeroes/">LC: 283</a>
 */
public class MoveZeros {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MoveZeros::tc1,
				MoveZeros::tc2,
				MoveZeros::tc3);
		Solver[] solvers = new Solver[] {new Iterative()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		int[] a = new int[] {0,1,0,3,12};
		solver.solve(a);
		Utils.println(a);
	}
	public static void tc2(Solver solver) {
		int[] a = new int[] {0,0,0};
		solver.solve(a);
		Utils.println(a);
	}
	public static void tc3(Solver solver) {
		int[] a = new int[] {0, 0, 1};
		solver.solve(a);
		Utils.println(a);
	}
	public static class Iterative implements Solver {

		@Override
		public void solve(int[] nums) {
			for (int i = 0, j = 1; j < nums.length; j++) {
				if (nums[j] != 0 && nums[i] == 0) {
					Utils.swap(nums, i++, j);
				} else if (nums[i] != 0) {
					i++;
				}
			}
		}
		
	}
	public interface Solver {
		public void solve(int[] nums);
	}
}
