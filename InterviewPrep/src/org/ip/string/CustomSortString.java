package org.ip.string;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/custom-sort-string/">LC: 791</a>
 */
public class CustomSortString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"cbad", "cba", "abcd"};
		Object[] tc2 = new Object[] {"cbad", "cbafg", "abcd"};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public String apply(String order, String s) {
			int[] freq = freq(s);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < order.length(); i++) {
				for (int j = 0; j < freq[order.charAt(i) - 'a']; j++) {
					sb.append(order.charAt(i));
				}
				freq[order.charAt(i) - 'a'] = 0;
			}
			for (int i = 0; i < 26; i++) {
				for (int j = 0; j < freq[i]; j++) {
					sb.append((char)('a' + i));
				}
				freq[i] = 0;
			}
			return sb.toString();
		}
		int[] freq(String s) {
			int[] res = new int[26];
			for (int i = 0; i < s.length(); i++) {
				res[s.charAt(i) - 'a']++;
			}
			return res;
		}
		int[] order(String order) {
			int[] res = new int[26];
			Arrays.fill(res, Integer.MAX_VALUE);
			for (int i = 0; i < order.length(); i++) {
				res[order.charAt(i) - 'a'] = i;
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public String apply(String order, String s) {
			int[] pos = order(order);
			Character[] chars = toCharacter(s);
			Arrays.sort(chars, (a, b) -> Integer.compare(pos[a.charValue() - 'a'], pos[b.charValue() - 'a']));
			return new String(toChar(chars));
		}
		char[] toChar(Character[] s) {
			char[] res = new char[s.length];
			for (int i = 0; i < s.length; i++) {
				res[i] = s[i];
			}
			return res;
		}
		Character[] toCharacter(String s) {
			Character[] res = new Character[s.length()];
			for (int i = 0; i < s.length(); i++) {
				res[i] = s.charAt(i);
			}
			return res;
		}
		int[] order(String order) {
			int[] res = new int[26];
			Arrays.fill(res, Integer.MAX_VALUE);
			for (int i = 0; i < order.length(); i++) {
				res[order.charAt(i) - 'a'] = i;
			}
			return res;
		}
		
	}
	interface Problem extends BiFunction<String, String, String> {
		
	}
}
