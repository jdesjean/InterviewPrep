package org.ip.hashtable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// EPI 2018: 12.1
public class PalindromicPermutation {
	public static void main(String[] s) {
		System.out.println(new PalindromicPermutation().isPermutation("edified"));
		System.out.println(new PalindromicPermutation().isPermutation("palindrome"));
	}
	public boolean isPermutation(String s) {
		int[] c = new int[26];
		for (int i = 0; i < s.length(); i++) {
			c[s.charAt(i) - 'a']++;
		}
		int oddCount = 0;
		for (int i = 0; i < s.length(); i++) {
			if (c[s.charAt(i) - 'a'] % 2 == 1) {
				oddCount++;
			}
		}
		return oddCount <= 1;
	}
}
