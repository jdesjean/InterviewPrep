package org.ip;

import java.util.Deque;
import java.util.LinkedList;

// Facebook 10/03/2019
public class BalanceParenthesis {
	public static void main(String[] s) {
		BalanceParenthesis balance = new BalanceParenthesis();
		System.out.println(balance.balance("(a)".toCharArray()));
		System.out.println(balance.balance("(a()".toCharArray()));
		System.out.println(balance.balance("(a))".toCharArray()));
		System.out.println(balance.balance("(a))(".toCharArray()));
	}
	private final static char SENTINEL = ' ';
	public String balance(char[] a) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int length = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == ')' && stack.isEmpty()) {
				a[i] = SENTINEL;
			} else {
				length++;
				if (a[i] == '(') {
					stack.push(i);
				} else if (a[i] == ')' && !stack.isEmpty()) {
					stack.pop();
				}
			}
		}
		for (int i = a.length - 1; i >= 0 && !stack.isEmpty(); i--) {
			if (i == stack.peek()) {
				a[i] = SENTINEL;
				stack.pop();
				length--;
			}
		}
		char[] buffer = new char[length];
		for (int i = 0, j = 0; i < a.length; i++) {
			if (a[i] != SENTINEL) {
				buffer[j++] = a[i];
			}
		}
		return new String(buffer);
	}
	
}
