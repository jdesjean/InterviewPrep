package org.ip.array;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/random-pick-index/">LC: 398</a>
 */
public class RandomPickIndex {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"[2, 4]", new int[] {1,2,3,3,3}, 3};
		Object[] tc2 = new Object[] {"[2, 4]", new int[] {1,2,3,3,3}, 3};
		Object[] tc3 = new Object[] {"[2, 4]", new int[] {1,2,3,3,3}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {
		Random random = new Random(0);
		
		@Override
		public Integer apply(int[] a, Integer target) {
			int idx = -1;
			int bound = 0;
			for (int i = 0; i < a.length; i++) {
				if (target.compareTo(a[i]) == 0) {
					bound++;
					if (random.nextInt(bound) == 0) {
						idx = i;
					}
				}
			}
			return idx;
		}
		
	}
	public interface Problem extends BiFunction<int[], Integer, Integer> {
		
	}
}
