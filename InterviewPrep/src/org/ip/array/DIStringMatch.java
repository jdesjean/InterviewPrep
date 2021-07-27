package org.ip.array;

import java.util.function.Function;

import org.ip.Test;
import org.ip.array.JumpGameIV.Problem;
import org.ip.array.JumpGameIV.Solver;
import org.ip.array.JumpGameIV.Solver2;

/**
 * <a href="https://leetcode.com/problems/di-string-match/">LC: 942</a>
 */
public class DIStringMatch {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {0,4,1,3,2}, "IDID"};
		Object[] tc2 = new Object[] {new int[] {0,1,2,3}, "III"};
		Object[] tc3 = new Object[] {new int[] {3,2,0,1}, "DDI"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(String t) {
			int n = t.length() + 1;
			int[] res = new int[n];
			int min = 0;
			int max = t.length();
			for (int i = 0; i < t.length(); i++) {
				res[i] = t.charAt(i) == 'I' ? min++ : max--;
			}
			res[t.length()] = min;
			return res;
		}
		
	}
	interface Problem extends Function<String, int[]> {
		
	}
}
