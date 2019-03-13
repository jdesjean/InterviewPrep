package org.ip.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.ip.array.Utils;

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
		PalindromeChecker checker = new PalindromeChecker();
		for (String string: strings) {
			for (int i = 0; i < string.length(); i++) {
				String s1 = i > 0 ? string.substring(0, i) : "";
				String s2 = i < string.length() ? string.substring(i) : "";
				String s3 = Utils.reverse(s1);
				String s4 = Utils.reverse(s2);
				if (checker.check(s1, 0, s1.length() - 1) && set.contains(s4)) return new String[]{string,s4};
				else if (set.contains(s3) && checker.check(s2, 0, s2.length() - 1)) return new String[]{s3,string};
			}
		}
		return null;
	}
	
}
