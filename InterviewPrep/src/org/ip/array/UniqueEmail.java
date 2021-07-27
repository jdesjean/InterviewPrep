package org.ip.array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/unique-email-addresses/">LC: 929</a>
 */
public class UniqueEmail {
	public static void main(String[] s) {
		System.out.println(new UniqueEmail().solve(new String[] {"test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"})); 
	}
	public int solve(String[] emails) {
		Map<String, Set<String>> map = new HashMap<>();
		int count = 0;
		for (int i = 0; i < emails.length; i++) {
			String[] split = emails[i].split("@");
			if (split.length != 2) continue; // invalid
			String name = unique(split[0]);
			Set<String> names = map.computeIfAbsent(split[1], domain -> new HashSet<>());
			if (names.add(name)) {
				count++;
			}
		}
		return count;
	}
	public String unique(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.') continue;
			else if (s.charAt(i) == '+') break;
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
}
