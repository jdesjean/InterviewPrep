package org.ip.recursion;

import org.ip.array.Utils;

// EPI: 16.7
public class Palindrome {
	public static void main(String[] s) {
		solve(new PrintVisitor(), "abracadabra".toCharArray());
		solve(new PrintVisitor(), "0204451881".toCharArray());
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(char[] buffer, int l) {
			Utils.println(buffer, l, false);
		}
	}
	public interface Visitor {
		public void visit(char[] buffer, int l);
	}
	public static void solve(Visitor visitor, char[] word) {
		char[] buffer = new char[word.length * 2];
		solve(visitor, word, buffer, 0, 0);
	}
	public static void solve(Visitor visitor, char[] word, char[] buffer, int l, int w) {
		if (l >= word.length) {
			visitor.visit(buffer, w);
			return;
		}
		for (int k = l; k < word.length; k++) {
			buffer[w + k - l] = word[k];
			if (isPalindrome(buffer, w, w + k - l)) {
				buffer[w + k - l + 1] = '/';
				solve(visitor, word, buffer, k + 1, w + k - l + 2);
			}
		}
	}
	public static boolean isPalindrome(char[] buffer, int l, int r) {
		for (int i = l, j = r; i < j; i++, j--) {
			if (buffer[i] != buffer[j]) return false;
		}
		return true;
	}
	
}
