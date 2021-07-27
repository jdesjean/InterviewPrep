package org.ip;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/352458/">OA 2019</a>
 */
public class CompareStrings {
	public static void main(String[] s) {
		String s1 = "abcd,aabc,bd";
		String s2 = "aaa,aa";
		Utils.println(new Solver1().solve(s1, s2));
	}
	public interface Solver {
		public int[] solve(String s1, String s2);
	}
	private static class Solver1 implements Solver {
		public int[] solve(String s1, String s2) {
			String[] a1 = s1.split(",");
			String[] a2 = s2.split(",");
			int[] freqsa = new int[11];
			for (int i = 0; i < a1.length; i++) {
				freqsa[freqs(a1[i])]++;
			}
			for (int i = 1; i < 11; i++)
				freqsa[i] = freqsa[i] + freqsa[i - 1];
			int[] res = new int[a2.length];
			for (int i = 0; i < a2.length; i++) {
				int freqsb = freqs(a2[i]);
				res[i] = freqsb > 0 ? freqsa[freqsb - 1] : 0;
			}
			return res;
		}
		int freqs(String s) {
			int smallest = smallest(s);
			return freqs(s, smallest);
		}
		int freqs(String s, int smallest) {
			int freq = 0;
			for (int i = 0; i < s.length(); i++) {
				int index = s.charAt(i) - 'a';
				if (index == smallest) freq++;
			}
			return freq;
		}
		int smallest(String s) {
			int smallest = Integer.MAX_VALUE;
			for (int i = 0; i < s.length(); i++) {
				int index = s.charAt(i) - 'a';
				smallest = Math.min(index, smallest);
			}
			return smallest;
		}
	}
}
