package org.ip.string;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-repeating-substring/">LC: 1668</a>
 */
public class MaxRepeating {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, "ababc", "ab"};
		Object[] tc2 = new Object[] {1, "ababc", "ba"};
		Object[] tc3 = new Object[] {0, "ababc", "ac"};
		Object[] tc4 = new Object[] {2, "abcababc", "ab"};
		Object[] tc5 = new Object[] {2, "ababaaba", "aba"};
		Object[] tc6 = new Object[] {0, "a", "aa"};
		Object[] tc7 = new Object[] {0, "baba", "b"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3(), new Solver4() };
		Test.apply(solvers, tcs);
	}
	static class Solver4 implements Problem {
		private final static int P = 31;
		private final static int M = 256;
		
		@Override
		public int applyAsInt(String sequence, String word) {
			if (sequence.length() < word.length()) return 0;
			int wHash = hash(word, word.length());
			int sHash = hash(sequence, word.length());
			int power = power(word.length());
			int[] cache = new int[word.length()];
			int max = 0;
			if (sHash == wHash && equal(sequence, 0, word)) {
				cache[word.length() - 1] = 1;
				max = 1;
			}
			for (int j = word.length(); j < sequence.length(); j++) {
				sHash -= sequence.charAt(j - word.length()) * power;
				sHash = Math.floorMod(sHash * P + sequence.charAt(j), M);
				int mj = j % word.length();
				if (sHash == wHash && equal(sequence, j - word.length() + 1, word)) {
					int mjp = (j - word.length()) % word.length();
					cache[mj] = cache[mjp] + 1;
					max = Math.max(max, cache[mj]);
				} else {
					cache[mj] = 0;
				}
			}
			return max;
		}
		private boolean equal(String sequence, int sStart, String word) {
			for (int i = 0; i < word.length(); i++) {
				if (sequence.charAt(i + sStart) != word.charAt(i)) return false;
			}
			return true;
		}
		private int power(int length) {
			int power = 1;
			for (int i = 1; i < length; i++) {
				power = (power * P) % M;
			}
			return power;
		}
		private int hash(String s, int length) {
			int hash = 0;
			for (int j = 0; j < length; j++) {
				hash = Math.floorMod(P * hash + s.charAt(j), M);
			}
			return hash;
		}
	}
	static class Solver3 implements Problem {

		@Override
		public int applyAsInt(String t, String u) {
			if (!t.contains(u)) return 0;
			int max = t.length() / u.length();
			String buf = u.repeat(max);
			int l = 1, h = max;
			RabinKarpe rk = new RabinKarpe();
			for (; l < h; ) {
				int m = l + (h - l + 1) / 2;
				boolean find = rk.find(t, buf, m * u.length()) >= 0;
				if (find) {
					l = m;
				} else {
					h = m - 1;
				}
			}
			return l;
		}
		void append(char[] buf, String u, int x) {
			char[] tmp = u.toCharArray();
			for (int i = 0, l = 0; i < x; i++, l += u.length()) {
				System.arraycopy(tmp, 0, buf, l, u.length());
			}
		}
	}
	static boolean contains(String t, String u, int l) {
		for (int i = 0; i < t.length(); i++) {
			for (int j = 0; j < l; j++) {
				if (t.charAt(i + j) != u.charAt(j)) break;
				if (j == l - 1) return true;
			}
		}
		return false;
	}
	static class RabinKarpe {
		private final static int d = 256;
		public int find(String t, String p, int l) {
			int q = 101;
			int shash = hash(p, l, q);
			int thash = hash(t, l, q);
			int power = power(l, q);
			if (thash == shash && equal(t, 0, p, l)) return 0;
			for (int j = l; j < t.length(); j++) {
				thash -= t.charAt(j - l) * power;
				thash = (thash * d + t.charAt(j)) % q;
				if (thash < 0) thash = thash + q;
				if (thash == shash && equal(t, j - l + 1, p, l)) return j - l + 1;
			}
			return -1;
		}
		public int[] findAll(String t, String p, int l) {
			int q = 101;
			int shash = hash(p, l, q);
			int thash = hash(t, l, q);
			int power = power(l, q);
			int[] res = new int[t.length()];
			if (thash == shash && equal(t, 0, p, l)) {
				res[l - 1] = 1;
			}
			for (int j = l; j < t.length(); j++) {
				thash -= t.charAt(j - l) * power;
				thash = (thash * d + t.charAt(j)) % q;
				if (thash < 0) thash = thash + q;
				if (thash == shash && equal(t, j - l + 1, p, l)) {
					res[j] = 1;
				}
			}
			return res;
		}
		private boolean equal(String one, int i, String two, int l) {
			for (int j = 0; j < l; j++) {
				if (one.charAt(j + i) != two.charAt(j)) return false;
			}
			return true;
		}
		private int power(int length, int q) {
			int power = 1;
			for (int i = 1; i < length; i++) {
				power = (power * d) % q;
			}
			return power;
		}
		private int hash(String s, int length, int q) {
			int hash = 0;
			for (int j = 0; j < length; j++) {
				hash = (d * hash + s.charAt(j)) % q;
			}
			return hash;
		}
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(String t, String u) {
			if (!t.contains(u)) return 0;
			StringBuilder sb = new StringBuilder();
			int max = t.length() / u.length();
			append(sb, u, max);
			for (; max > 1;) {
				if (t.contains(sb)) return max;
				sb.setLength(u.length() * --max);
			}
			return 1;
		}
		void append(StringBuilder sb, String u, int x) {
			for (int i = 0; i < x; i++) {
				sb.append(u);
			}
		}
	}
	// doesn't work on test case 5
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String t, String u) {
			int max = 0;
			for (int i = 0, j = 0, count = 0; i < t.length(); i++) {
				if (t.charAt(i) == u.charAt(j++)) {
					if (j == u.length()) {
						max = Math.max(max, ++count);
						j = 0;
					}
				} else {
					count = 0;
					j = 0;
				}
			}
			return max;
		}
		
	}
	interface Problem extends ToIntBiFunction<String, String> {
		
	}
}
