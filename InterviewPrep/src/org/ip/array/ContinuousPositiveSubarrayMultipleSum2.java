package org.ip.array;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.ip.Test;
import org.ip.array.CarFleet.Problem;
import org.ip.array.CarFleet.Solver;

/**
 * <a href="https://leetcode.com/problems/continuous-subarray-sum/">LC: 523</a>
 */
public class ContinuousPositiveSubarrayMultipleSum2 {
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
		Object[] tc19 = new Object[] {true, new int[] {23,2,4,6,6}, 7};
		Object[] tc20 = new Object[] {true, new int[] {2,4,3}, 6};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10, tc11, tc12, tc13, tc14, tc15, tc16, tc17, tc18, tc19, tc20};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public boolean test(int[] t, Integer u) {
			int k = u;
			if (k == 0) return checkZero(t);
			Set<Integer> set = new HashSet<>();
			set.add(0);
			for (int i = 1, sum = t[0] % k, prev = sum; i < t.length; i++) {
				sum = (sum + t[i]) % k;
				if (set.contains(sum)) return true;
				set.add(prev);
				prev = sum;
			}
			return false;
		}
		boolean checkZero(int[] t) {
			for (int i = 1; i < t.length; i++) {
				if (t[i] == t[i - 1] && t[i] == 0) return true;
			}
			return false;
		}
		
	}
	interface Problem extends BiPredicate<int[], Integer> {
		
	}
}
