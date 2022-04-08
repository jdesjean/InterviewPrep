package org.ip.string;

import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.PriorityQueue;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/reorganize-string/">LC: 767</a>
 */
public class ReorganizeString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"aba", "aab"};
		Object[] tc2 = new Object[] {"", "aaab"};
		Object[] tc3 = new Object[] {"ababababab", "abbabbaaab"};
		Object[] tc4 = new Object[] {"vlvov", "vvvlo"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public String apply(String t) {
			int[] freqs = new int[26];
			IntHolder max = new IntHolder();
			IntSummaryStatistics stats = freq(t, freqs, max);
			if (stats.getMax() * 2 - stats.getSum()  > 1) return "";
			char[] res = new char[t.length()];
			int k = 0;
			int i = 0;
			for (; i < t.length(); i+=2) {
				if (freqs[max.value]-- <= 0) break;
				res[i] = (char)(max.value + 'a');
			}
			for (; i < t.length(); i+=2) {
				for (; k < 26; k++) {
					if (freqs[k]-- > 0) break;
				}
				res[i] = (char)(k + 'a');
			}
			for (i = 1; i < t.length(); i+=2) {
				for (; k < 26; k++) {
					if (freqs[k]-- > 0) break;
				}
				res[i] = (char)(k + 'a');
			}
			return new String(res);
		}
		IntSummaryStatistics freq(String t, int[] freqs, IntHolder max) {
			IntSummaryStatistics stats = new IntSummaryStatistics();
			for (int i = 0; i < t.length(); i++) {
				int idx = t.charAt(i) - 'a';
				++freqs[idx];
			}
			for (int i = 0; i < 26; i++) {
				stats.accept(freqs[i]);
				if (freqs[i] == stats.getMax()) {
					max.value = i;
				}
			}
			return stats;
		}
		static class IntHolder {
			int value;
		}
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t) {
			if (t == null || t.length() == 0) return t;
			int[] freqs = new int[26];
			IntSummaryStatistics stats = freq(t, freqs);
			if (stats.getMax() * 2 - stats.getSum()  > 1) return "";
			PriorityQueue<Character> pq = new PriorityQueue<>(Collections.reverseOrder(new FrequencyComparator(freqs)));
			for (int i = 0; i < 26; i++) {
				if (freqs[i] > 0) {
					pq.add((char)(i + 'a'));
				}
			}
			StringBuilder res = new StringBuilder();
			while (!pq.isEmpty()) { // process characters 2 at a time to handle case where max + 1 = next
				Character current = pq.remove();
				res.append(current);
				if (!pq.isEmpty() && freqs[pq.peek() - 'a'] < freqs[current - 'a']) {
					Character next = pq.remove();
					res.append(next);
					if (--freqs[next - 'a'] > 0) {
						pq.add(next);
					}
				}
				if (--freqs[current - 'a'] > 0)  {
					pq.add(current);
				}
			}
			return res.toString();
		}
		IntSummaryStatistics freq(String t, int[] freqs) {
			IntSummaryStatistics stats = new IntSummaryStatistics();
			for (int i = 0; i < t.length(); i++) {
				int idx = t.charAt(i) - 'a';
				++freqs[idx];
			}
			for (int i = 0; i < 26; i++) {
				stats.accept(freqs[i]);
			}
			return stats;
		}
		static class FrequencyComparator implements Comparator<Character> {
			private int[] freqs;

			public FrequencyComparator(int[] freqs) {
				this.freqs = freqs;
			}

			@Override
			public int compare(Character o1, Character o2) {
				return Integer.compare(freqs[o1.charValue() - 'a'], freqs[o2.charValue() - 'a']);
			}
			
		}
	}
	interface Problem extends Function<String, String> {
		
	}
}
