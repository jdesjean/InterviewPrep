package org.ip.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/number-of-matching-subsequences/">LC: 792</a>
 */
public class NumberMatchingSubsequences {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, "abcde", new String[] {"a","bb","acd","ace"} };
		Object[] tc2 = new Object[] { 2, "dsahjpjauf", new String[] {"ahjpjau","ja","ahbwzgqnuk","tnmlanowax"} };
		
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	// next letter pointers
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String t, String[] u) {
			Map<Character, Set<StringBuilder>> map = new HashMap<>();
			for (int i = 0; i < u.length; i++) {
				StringBuilder sb = new StringBuilder(u[i]);
				sb.reverse();
				Set<StringBuilder> list = map.computeIfAbsent(u[i].charAt(0), (k) -> new HashSet<>());
				list.add(sb);
			}
			int count = 0;
			for (int i = 0; i < t.length(); i++) {
				Set<StringBuilder> list = map.get(t.charAt(i));
				if (list == null) continue;
				for (Iterator<StringBuilder> it = list.iterator(); it.hasNext(); ) {
					StringBuilder sb = it.next();
					sb.deleteCharAt(sb.length() - 1);
					if (sb.length() == 0) {
						it.remove();
						count++;
					} else {
						Set<StringBuilder> list2 = map.computeIfAbsent(sb.charAt(sb.length() - 1), (k) -> new HashSet<>());
						if (list != list2) {
							it.remove();
							list2.add(sb);
						}
					}
				}
			}
			return count;
		}
	}
	interface Problem extends ToIntBiFunction<String, String[]> {
		
	}
}
