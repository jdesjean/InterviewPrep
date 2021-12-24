package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/continuous-subarray-sum/">LC: 523</a>
 */
public class ContinuousPositiveSubarraySum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[] {1,2,3}, 3};
		Object[] tc2 = new Object[] {false, new int[] {1,3,2}, 3};
		Object[] tc3 = new Object[] {true, new int[] {2,1,3}, 3};
		Object[] tc4 = new Object[] {false, new int[] {2,3,1}, 3};
		Object[] tc5 = new Object[] {true, new int[] {3,1,2}, 3};
		Object[] tc6 = new Object[] {true, new int[] {3,2,1}, 3};
		Object[] tc7 = new Object[] {true, new int[] {0,1,2}, 3};
		Object[] tc8 = new Object[] {true, new int[] {0,2,1}, 3};
		Object[] tc9 = new Object[] {true, new int[] {1,0,2}, 3};
		Object[] tc10 = new Object[] {true, new int[] {1,2,0}, 3};
		Object[] tc11 = new Object[] {true, new int[] {2,0,1}, 3};
		Object[] tc12 = new Object[] {true, new int[] {2,1,0}, 3};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10, tc11, tc12);
		BiFunction<int[], Integer, Boolean>[] solvers = new BiFunction[] {new Solver()};
		for (Object[] tc : tcs) { 
			System.out.print(String.valueOf(tc[0]));
			for (BiFunction<int[], Integer, Boolean> solver : solvers) {
				System.out.print("," + solver.apply((int[]) tc[1], (Integer)tc[2]));
			}
			System.out.println();
		}
	}
	public static class Solver implements BiFunction<int[], Integer, Boolean> {

		@Override
		public Boolean apply(int[] t, Integer u) {
			if (t == null || t.length < 2) return false;
			int sum = 0;
			for (int l = 0, r = 0; r < t.length; ) {
				if (sum == u && l + 1 < r) {
					break;
				} else if (sum < u) {
					sum += t[r++];
				} else if (l < r) {
					sum -= t[l++];
				}
			}
			return sum == u;
		}
		
	}
}
