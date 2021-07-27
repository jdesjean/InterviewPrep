package org.ip;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/397156/">OA 2019</a>
 * <a href="">LC: 801</a>
 */
public class RelativeSort {
	public static void main(String[] s) {
		Recursive recursive = new Recursive();
		Recursive2 recursive2 = new Recursive2();
		DP dp = new DP();
		List<Consumer<Solver>> consumers = Arrays.asList(RelativeSort::tc1, RelativeSort::tc2, RelativeSort::tc3, RelativeSort::tc4, RelativeSort::tc5, RelativeSort::tc6, RelativeSort::tc7);
		Solver[] solvers = new Solver[] {new Recursive(), new Recursive2(), new DP()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
		/*tc1(recursive);
		tc1(dp);
		tc2(recursive);
		tc2(dp);
		tc3(recursive);
		tc3(dp);
		tc4(recursive);
		tc4(dp);
		tc5(recursive);
		tc5(dp);
		tc6(recursive);
		tc6(dp);
		tc7(recursive);
		tc7(recursive2);
		tc7(dp);*/
	}
	public static void tc1(Solver solver) {
		//A = [1,3,5,4], B = [1,2,3,7]
		//a[3]
		System.out.println(solver.solve(new int[] {1,3,5,4}, new int[] {1,2,3,7}));
	}
	public static void tc2(Solver solver) {
		//1 4 4 9
		//2 3 5 10
		//a[1]
		System.out.println(solver.solve(new int[] {1,4,4,9}, new int[] {2,3,5,10}));
	}
	public static void tc3(Solver solver) {
		//1 4 4 5
		//2 3 5 10
		//a[1]
		System.out.println(solver.solve(new int[] {1,4,4,5}, new int[] {2,3,5,10}));
	}
	public static void tc4(Solver solver) {
		//1 4 4 4
		//2 3 5 10
		//0
		System.out.println(solver.solve(new int[] {1,4,4,4}, new int[] {2,3,5,10}));
	}
	public static void tc5(Solver solver) {
		//1 2 4 7
		//2 3 5 10
		//0
		System.out.println(solver.solve(new int[] {1,2,4,7}, new int[] {2,3,5,10}));
	}
	public static void tc6(Solver solver) {
		//3 3 5 5
		//2 4 5 10
		//1 1 2 
		//0 2 1
		//2
		System.out.println(solver.solve(new int[] {3,3,6,6}, new int[] {2,4,5,10}));
	}
	public static void tc7(Solver solver) {
		//[3,3,8,9,10]
		//[1,7,4,6,8]
		//1
		System.out.println(solver.solve(new int[] {3,3,8,9,10}, new int[] {1,7,4,6,8}));
	}
	public interface Solver {
		public int solve(int[] a, int[] b);
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] a, int[] b) {
			int[][] cache = new int[2][a.length];
			Arrays.fill(cache[0], Integer.MAX_VALUE);
			Arrays.fill(cache[1], Integer.MAX_VALUE);
			cache[0][0] = 0;
			cache[1][0] = 1;
			for (int i = 1; i < a.length; i++) {
				if (a[i] > a[i - 1] && b[i] > b[i - 1]) {
					cache[0][i] = Math.min(cache[0][i], cache[0][i - 1]);
					cache[1][i] = Math.min(cache[1][i], cache[1][i - 1] + 1);
				}
				if (a[i] > b[i - 1] && b[i] > a[i - 1]) {
					cache[0][i] = Math.min(cache[0][i], cache[1][i - 1]);
					cache[1][i] = Math.min(cache[1][i], cache[0][i - 1] + 1);
				}
			}
			int min = Math.min(cache[0][a.length - 1], cache[1][a.length - 1]);
			return min != Integer.MAX_VALUE ? min : -1;
		}
		
	}
	public static class Recursive2 implements Solver {
		public int solve(int[] a, int[] b) {
	 		int swaps = solve(a, b, 0, 0, false);
			return swaps != Integer.MAX_VALUE ? swaps : -1;
		}
		public int solve(int[] a, int[] b, int index, int swaps, boolean flag) {
			if (index == a.length) {
				return swaps;
			}
			int min = Integer.MAX_VALUE;
			boolean natural = index == 0 || (a[index] > a[index - 1] && b[index] > b[index - 1]);
			boolean swapped = index == 0 || (a[index] > b[index - 1] && b[index] > a[index - 1]);
			if ((!flag && natural) || (flag && swapped)) {
				min = solve(a, b, index + 1, swaps, false);
			}
			if ((!flag && swapped) || (flag && natural)) {
				min = Math.min(min, solve(a, b, index + 1, swaps + 1, true));
			}
			return min;
		}
	}
	public static class Recursive implements Solver{
		public int solve(int[] a, int[] b) {
	 		int swaps = solve(a, b, 0, 0);
			return swaps != Integer.MAX_VALUE ? swaps : -1;
		}
		public int solve(int[] a, int[] b, int index, int swaps) {
			if (index == a.length) {
				return swaps;
			}
			int min = Integer.MAX_VALUE;
			if (index == 0 || (a[index] > a[index - 1] && b[index] > b[index - 1])) {
				min = solve(a, b, index + 1, swaps);
			}
			if (index == 0 || (b[index] > a[index - 1] && a[index] > b[index - 1])) {
				swap(a, b, index);
				min = Math.min(min, solve(a, b, index + 1, swaps + 1));
				swap(a, b, index);
			}
			return min;
		}
		public void swap(int[] a, int[] b, int i) {
			int tmp = a[i];
			a[i] = b[i];
			b[i] = tmp;
		}
	}
}
