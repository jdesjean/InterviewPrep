package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/palindrome-permutation/">LC: 267</a>
 */
public class PalindromePermutationII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(new String[] {"abba", "baab"}), "aabb"};
		Object[] tc2 = new Object[] {Arrays.asList(new String[] {}), "abc"};
		Object[] tc3 = new Object[] {Arrays.asList(new String[] {"civic","icvci"}), "civic"};
		Object[] tc4 = new Object[] {Arrays.asList(new String[] {"aaa"}), "aaa"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem {

		@Override
		public List<String> apply(String t) {
			Map<Character, Integer> odd = freq(t);
			Set<String> res = new HashSet<>();
			char[] buf = new char[t.length() / 2];
			char c = ' ';
			int i = 0;
			for (Map.Entry<Character, Integer> entry : odd.entrySet()) {
				if (entry.getValue() % 2 == 1) {
					if (c != ' ') return new ArrayList<>();
					c = entry.getKey();
				}
				if (entry.getValue() > 1) {
					for (int j = 0; j < entry.getValue() / 2; j++, i++) {
						buf[i] = entry.getKey();
					}
				}
			}
			perms(res, buf, 0, c);
			return res
					.stream()
					.collect(Collectors.toList());
		}
		
		void perms(Set<String> res, char[] buf, int l, char c) {
			if (l == buf.length) {
				res.add(new String(buf) + (c != ' ' ? c : "") + new String(reverse(buf)));
				return;
			}
			for (int i = l; i < buf.length; i++) {
				if (buf[l] != buf[i] || l == i) {
					swap(buf, l, i);
					perms(res, buf, l + 1, c);
					swap(buf, l, i);
				}
			}
		}
		char[] reverse(char[] buf) {
			char[] copy = Arrays.copyOf(buf, buf.length);
			for (int i = 0, j = buf.length - 1; i < j; i++, j--) {
				swap(copy, i, j);
			}
			return copy;
		}
		void swap(char[] buf, int i, int j) {
			char c = buf[i];
			buf[i] = buf[j];
			buf[j] = c;
		}
		
		Map<Character, Integer> freq(String t) {
			Map<Character, Integer> map = new HashMap<>();
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				map.compute(c, (k, v) -> v == null ? 1 : v + 1);
			}
			return map;
		}
	}
	public static class Solver implements Problem {

		@Override
		public List<String> apply(String t) {
			Map<Character, Integer> freq = new HashMap<>();
			Set<Character> odd = freq(t, freq);
			List<String> res = new ArrayList<>();
			if (odd.size() > 1) return res;
			char[] buf = new char[t.length()];
			if (buf.length % 2 == 1) {
				buf[buf.length / 2] = odd.iterator().next();
			}
			perms(res, freq, buf, 0);
			return res;
		}
		void perms(List<String> res, Map<Character, Integer> map, char[] buf, int i) {
			if (i >= buf.length / 2) {
				res.add(new String(buf));
				return;
			}
			for (Map.Entry<Character, Integer> entry : map.entrySet()) {
				if (entry.getValue() <= 1) continue;
				char c = entry.getKey();
				buf[i] = c;
				buf[buf.length - i - 1] = c;
				map.put(c, entry.getValue() - 2);
				perms(res, map, buf, i + 1);
				map.put(c, entry.getValue() + 2);
			}
		}
		
		Set<Character> freq(String t, Map<Character, Integer> map) {
			Set<Character> odd = new HashSet<>();
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				map.compute(c, (k, v) -> v == null ? 1 : v + 1);
				if (!odd.add(c)) {
					odd.remove(c);
				}
			}
			return odd;
		}
		
	}
	public interface Problem extends Function<String, List<String>> {
		
	}
}
