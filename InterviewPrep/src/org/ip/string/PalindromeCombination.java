package org.ip.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.ip.ArrayUtils;

public class PalindromeCombination {
	public static void main(String[] s) {
		System.out.println(Arrays.toString(solve(new String[]{"bab","tab","cat"})));
		System.out.println(Arrays.toString(solve(new String[]{"bat","tab","cat"})));
		System.out.println(Arrays.toString(solve(new String[]{"ab","deedba"})));
		System.out.println(Arrays.toString(solve(new String[]{"ant","cat","dog"})));
	}
	public static String[] solve(String[] strings) {
		Set<String> set = new HashSet<String>();
		set.add("");
		for (String string : strings) {
			set.add(string);
		}
		for (String string: strings) {
			for (int i = 0; i < string.length(); i++) {
				String s1 = i > 0 ? string.substring(0, i) : "";
				String s2 = i < string.length() ? string.substring(i) : "";
				String s3 = ArrayUtils.reverse(s1);
				String s4 = ArrayUtils.reverse(s2);
				if (Palindrome.isPalindrome(s1) && set.contains(s4)) return new String[]{string,s4};
				else if (set.contains(s3) && Palindrome.isPalindrome(s2)) return new String[]{s3,string};
			}
		}
		return null;
	}
	
}
