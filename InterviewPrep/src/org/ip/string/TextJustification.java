package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;
import org.ip.string.ConstructKPalindrome.Problem;
import org.ip.string.ConstructKPalindrome.Solver;
import org.ip.string.ConstructKPalindrome.Solver2;

/**
 * <a href="https://leetcode.com/problems/text-justification/">LC: 68</a>
 */
public class TextJustification {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new String[] {
				"This    is    an",
				"example  of text",
				"justification.  "
				}, 
				new String[] {"This", "is", "an", "example", "of", "text", "justification."}, 16};
		Object[] tc2 = new Object[] {new String[] {
				"What   must   be",
				"acknowledgment  ",
				"shall be        "
		}, new String[] {"What","must","be","acknowledgment","shall","be"}, 16};
		Object[] tc3 = new Object[] {new String[] {
				"Science  is  what we",
				"understand      well",
				"enough to explain to",
				"a  computer.  Art is",
				"everything  else  we",
				"do                  "
		}, new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"}, 20};
		Object[] tc4 = new Object[] {new String[] {
				"Listen",
				"to    ",
				"many, ",
				"speak ",
				"to   a",
				"few.  "
		}, new String[] {"Listen","to","many,","speak","to","a","few."}, 6};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(String[] t, Integer u) {
			int k = u;
			List<String> list = new ArrayList<>();
			int length = 0;
			int i = 0, j = 0;
			for (; i < t.length; i++) {
				if (length != 0 && length + t[i].length() + 1 > k) {
					String current = justify(t, j, i - 1, k - length);
					list.add(current);
					j = i;
					length = 0;
				} else if (i != j){
					length += 1;
				}
				length += t[i].length();
			}
			if (length > 0) {
				String current = justify(t, j, i - 1, 0);
				current = current + spaces(k - length);
				list.add(current);
			}
			return list;
		}
		String spaces(int k) {
			StringBuilder sb = new StringBuilder(k);
			for (int i = 0; i < k; i++) {
				sb.append(" ");
			}
			return sb.toString();
		}
		String justify(String[] a, int i0, int j, int spaceRemaining) {
			StringBuilder sb = new StringBuilder();
			int words = j - i0;
			int spaceLength = words != 0 ? spaceRemaining / words : spaceRemaining;
			spaceRemaining -= words * spaceLength;
			for (int i = i0; i <= j; i++) {
				if (sb.length() != 0) {
					sb.append(" ");
					sb.append(spaces(spaceLength));
					if (spaceRemaining-- > 0) sb.append(" ");
				}
				sb.append(a[i]);
			}
			if (spaceRemaining > 0) {
				sb.append(spaces(spaceRemaining));
			}
			return sb.toString();
		}
	}
	interface Problem extends BiFunction<String[], Integer, List<String>> {
		
	}
}
