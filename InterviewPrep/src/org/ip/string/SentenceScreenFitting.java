package org.ip.string;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/sentence-screen-fitting/">LC: 418</a>
 */
public class SentenceScreenFitting {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {1, new String[] {"hello", "world"}, 2, 8};
		Object[] tc2 = new Object[] {2, new String[] {"a", "bcd", "e"}, 3, 6};
		Object[] tc3 = new Object[] {1, new String[] {"i","had","apple","pie"}, 4, 5};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	/**
	 * O(ROWS)
	 */
	static class Solver2 implements Problem {

		@Override
		public Integer apply(String[] a, Integer bb, Integer cc) {
			String combined = String.join(" ", a);
			combined = combined + " ";
			int rows = bb;
			int cols = cc;
			int i = 0;
			for (int r = 0; r < rows; r++) {
				i += cols;
				int j = i % combined.length();
				if (combined.charAt(j) == ' ') {
					i++; // set to first letter of a word
					continue;
				}
				for (; j > 0 && combined.charAt(j - 1) != ' '; j--, i--) {}
			}
			return i / combined.length();
		}
	}
	/**
	 * TLE
	 * O(ROWS*COLS/length(a[i]))
	 */
	static class Solver implements Problem {

		@Override
		public Integer apply(String[] a, Integer bb, Integer cc) {
			int rows = bb;
			int cols = cc;
			int i = 0;
			for (int r = 0; r < rows; r++) {
				int remaining = cols;
				while (remaining > 0) {
					int j = i % a.length;
					int length = remaining == cols ? a[j].length() : a[j].length() + 1;
					remaining -= length;
					if (remaining >= 0) {
						i++;
					}
				}
			}
			return i / a.length;
		}
	}
	interface Problem extends TriFunction<String[], Integer, Integer, Integer> {
		
	}
}
