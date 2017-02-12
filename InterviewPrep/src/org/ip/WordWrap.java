package org.ip;

import java.util.HashSet;
import java.util.Set;

public class WordWrap {
	public static void main(String[] s) {
		Set<String> set = new HashSet<String>();
		set.add("apple");
		set.add("pie");
		System.out.println(solve("applepie",set));
	}
	public static String solve(String word, Set<String> dictionary) {
		int[] cache = new int[word.length()+1];
		cache[0] = 1;
		for (int i = 1; i <= word.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (cache[j] == 0 || !dictionary.contains(word.substring(j, i))) continue;
				cache[i] = i-j;
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		if (cache[word.length()] == 0) return "";
		for (int i = word.length(), j = word.length()-1; i >= 1; i-=cache[i]) {
			for (int k = 0; k < cache[i]; k++, j--) {
				sb.append(word.charAt(j));
			}
			sb.append(' ');
		}
		return sb.reverse().toString();
	}
}
