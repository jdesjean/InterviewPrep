package org.ip.recursion;

import java.util.function.BiConsumer;

import org.ip.array.Utils;

// EPI 2018: 15.7
public class Palindrome2 {
	public static void main(String[] s) {
		char[] string = "0204451881".toCharArray();
		new Palindrome2().decomposition((x,l) -> Utils.println(x,l, false), string);
	}
	public void decomposition(BiConsumer<char[], Integer> consumer, char[] s) {
		decomposition(consumer, s, new char[s.length * 2], 0, 0);
	}
	public void decomposition(BiConsumer<char[], Integer> consumer, char[] s, char[] buffer, int ri, int wi) {
		if (ri == s.length) {
			consumer.accept(buffer, wi);
			return;
		}
		for (int i = ri, j = wi; i < s.length; i++, j++) {
			buffer[j] = s[i];
			if (isPalindrome(buffer, wi, j)) {
				buffer[j + 1] = ',';
				decomposition(consumer, s, buffer, i + 1, j + 2);
			}
		}
	}
	public boolean isPalindrome(char[] buffer, int left, int right) {
		for (int l = left, r = right; l < right; l++, r--) {
			if (buffer[l] != buffer[r]) return false;
		}
		return true;
	}
}
