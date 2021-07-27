package org.ip.string;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/">LC: 340</a>
 */
public class LongestSubstringWithAtMostKCharacters {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {3, "eceba", 2};
		Object[] tc2 = new Object[] {2, "aa", 1};
		Object[] tc3 = new Object[] {14, "a@b$5!a8alskj234jasdf*()@$&%&#FJAvjjdaurNNMa8ASDF-0321jf?>{}L:fh", 10};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(String.class).newInstance((String) tc[1]);
				System.out.print(function.apply((Integer) tc[2]));
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private String s;

		public Solver(String s) {
			this.s = s;
		}

		@Override
		public Integer apply(Integer t) {
			if (t <= 0) return 0;
			Distribution distribution = new Distribution();
			int max = 0;
			for (int i = 0, j = 0; i < s.length(); i++) {
				if (Integer.compare(distribution.add(s.charAt(i)), t) <= 0) {
					max = Math.max(max, distribution.count());
				} else {
					while (Integer.compare(distribution.remove(s.charAt(j++)), t) > 0) {
						//do nothing
					}
				}
			}
			return max;
		}
	}
	public static class Distribution {
		Map<Character, Integer> counts = new HashMap<>();
		int unique = 0;
		int count = 0;
		public int add(char c) {
			if (counts.compute(c, (k, v) -> v == null ? 1 : v + 1) == 1) { 
				unique++;
			}
			count++;
			return unique;
		}
		public int remove(char c) {
			if (counts.compute(c, (k, v) -> v - 1) == 0) {
				unique--;
			}
			count--;
			return unique;
		}
		public int count() {
			return count;
		}
	}
	public interface Problem extends Function<Integer, Integer> {
		
	}
}
