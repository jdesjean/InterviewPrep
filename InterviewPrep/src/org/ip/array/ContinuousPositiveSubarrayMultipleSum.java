package org.ip.array;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/continuous-subarray-sum/">LC: 523</a>
 */
public class ContinuousPositiveSubarrayMultipleSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {false, new int[] {1,2,3,4}, 4};
		Object[] tc2 = new Object[] {false, new int[] {1,2,4,3}, 4};
		Object[] tc3 = new Object[] {true, new int[] {1,3,2,4}, 4};
		Object[] tc4 = new Object[] {true, new int[] {1,3,4,2}, 4};
		Object[] tc5 = new Object[] {false, new int[] {1,4,2,3}, 4};
		Object[] tc6 = new Object[] {true, new int[] {1,4,3,2}, 4};
		Object[] tc7 = new Object[] {false, new int[] {4,1,2,3}, 4};
		Object[] tc8 = new Object[] {true, new int[] {4,1,3,2}, 4};
		Object[] tc9 = new Object[] {true, new int[] {4,2,1,3}, 4};
		Object[] tc10 = new Object[] {true, new int[] {4,2,3,1}, 4};
		Object[] tc11 = new Object[] {true, new int[] {4,3,1,2}, 4};
		Object[] tc12 = new Object[] {false, new int[] {4,3,2,1}, 4};
		Object[] tc13 = new Object[] {true, new int[] {23, 2, 4, 6, 7}, 6};
		Object[] tc14 = new Object[] {true, new int[] {23, 2, 6, 4, 7}, 6};
		Object[] tc15 = new Object[] {true, new int[] {0, 0}, 0};
		Object[] tc16 = new Object[] {false, new int[] {1, 0}, 0};
		Object[] tc17 = new Object[] {true, new int[] {1, 0, 0}, 0};
		Object[] tc18 = new Object[] {true, new int[] {0, 0, 0}, 1};
		Object[] tc19 = new Object[] {true, new int[] {1, 2, 3}, -3};
		List<Object[]> tcs = Arrays.asList(tc19, tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10, tc11, tc12, tc13, tc14, tc15, tc16, tc17, tc18);
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
			if (u == 0) return checkZero(t);
			int mod = Math.abs(u);
			BitSet sums = new BitSet();
			int sum = 0;
			int prevSum = 0;
			for (int i = 0; i < t.length; i++) {
				sum = (sum + t[i]) % mod;
				if (sums.get(sum)) {
					return true;
				}
				sums.set(prevSum);
				prevSum = sum;
			}
			return false;
		}
		
		boolean checkZero(int[] t) {
			for (int i = 1; i < t.length; i++) {
				if (t[i] == 0 && t[i - 1] == 0) {
					return true;
				}
			}
			return false;
		}
	}
}
