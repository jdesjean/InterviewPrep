package org.ip;

import java.util.ArrayList;
import java.util.List;

public class Palindrome {
	public static void main(String[] s) {
		/*
		 * a|b|r|a|c|a|d|a|b|r|a|
			a|b|r|a|c|ada|b|r|a|
			a|b|r|aca|d|a|b|r|a|
		 */
		System.out.println(decompose("abracadabra"));
	}
	public static List<String> decompose(String palindrome) {
		return decompose(new ArrayList<String>(), palindrome, new char[palindrome.length()*2],0,0,0);
	}
	private static boolean isPalindrome(char[] palindrome, int left, int right) {
		for (int i = left, j = right; i < j; i++, j--) {
			if (palindrome[i] != palindrome[j]) return false;
		}
		return true;
	}
	private static List<String> decompose(List<String> result, String palindrome, char[] buffer, int read, int write, int lastDecomposition) {
		if (read == palindrome.length() && buffer[write-1] == '|') {
			result.add(String.copyValueOf(buffer, 0, write));
			return result;
		}
		
		if (write > 0 && buffer[write-1] != '|' && isPalindrome(buffer,lastDecomposition,write-1)) {
			buffer[write] = '|';
			decompose(result,palindrome,buffer,read,write+1,write+1);
		}
		
		if (read < palindrome.length()) {
			buffer[write] = palindrome.charAt(read);
			decompose(result,palindrome,buffer,read+1,write+1,lastDecomposition);
		}
		return result;
	}
}
