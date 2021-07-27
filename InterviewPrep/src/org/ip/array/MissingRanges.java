package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/missing-ranges/">LC: 163</a>
 */
public class MissingRanges {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList("2","4->49","51->74","76->99"), new int[] {0,1,3,50,75}, 0, 99};
		Object[] tc2 = new Object[] { Arrays.asList("0->99"), new int[] {}, 0, 99};
		Object[] tc3 = new Object[] { Arrays.asList("0","2->99"), new int[] {1}, 0, 99};
		Object[] tc4 = new Object[] { Arrays.asList(), new int[] {-1}, -1, -1};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(int[] a, Integer b, Integer c) {
			int lower = b;
			int upper = c;
			if (a.length == 0) return Arrays.asList(format(lower, upper));
			List<String> res = new ArrayList<>();
			if (a[0] != lower) {
				res.add(format(lower, a[0] - 1));
			}
			for (int i = 1; i < a.length; i++) {
				if (a[i] != a[i - 1] + 1) res.add(format(a[i - 1] + 1, a[i] - 1));
			}
			if (a[a.length - 1] != upper) {
				res.add(format(a[a.length - 1] + 1, upper));
			}
			return res;
		}
		String format(int a, int b) {
			if (a == b) return String.valueOf(a);
			return String.format("%d->%d", a, b);
		}
		
	}
	interface Problem extends TriFunction<int[], Integer, Integer, List<String>> {
		
	}
}
