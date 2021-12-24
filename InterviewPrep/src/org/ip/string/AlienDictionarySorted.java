package org.ip.string;

import java.util.Comparator;
import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/verifying-an-alien-dictionary/">LC: 953</a>
 */
public class AlienDictionarySorted {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new String[] {"hello","leetcode"}, "hlabcdefgijkmnopqrstuvwxyz"};
		Object[] tc2 = new Object[] {false, new String[] {"word","world","row"}, "worldabcefghijkmnpqstuvxyz"};
		Object[] tc3 = new Object[] {false, new String[] {"apple","app"}, "abcdefghijklmnopqrstuvwxyz"};
		Object[] tc4 = new Object[] {true, new String[] {"kuvp","q"}, "ngxlkthsjuoqcpavbfdermiywz"};
		Object[][] consumers = new Object[][] {tc1, tc2, tc3, tc4};
		BiPredicate<String[], String>[] solvers = new BiPredicate[] {new Solver()};
		Test.apply(solvers, consumers);
	}
	static class Solver implements BiPredicate<String[], String> {

		@Override
		public boolean test(String[] t, String u) {
			int[] alphabet = new int[26];
			for (int i = 0; i < u.length(); i++) {
				int j = u.charAt(i) - 'a';
				alphabet[j] = i;
			}
			AlienComparator comparator = new AlienComparator(alphabet);
			for (int i = 1; i < t.length; i++) {
				if (comparator.compare(t[i - 1], t[i]) > 0) return false;
			}
			return true;
		}
		static class AlienComparator implements Comparator<String> {
			private int[] alphabet;

			public AlienComparator(int[] alphabet) {
				this.alphabet = alphabet;
			}

			@Override
			public int compare(String o1, String o2) {
				for (int i = 0; i < o1.length() && i < o2.length(); i++) {
					int c1 = o1.charAt(i) - 'a';
					int c2 = o2.charAt(i) - 'a';
					int j1 = alphabet[c1];
					int j2 = alphabet[c2];
					int res = Integer.compare(j1, j2);
					if (res != 0) return res;
				}
				return Integer.compare(o1.length(), o2.length());
			}
			
		}
	}
}
