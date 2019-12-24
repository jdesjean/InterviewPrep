package org.ip;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

// Facebook 10/03/2019
public class BalanceParenthesis2 {
	public static void main(String[] s) {
		BalanceParenthesis2 balance = new BalanceParenthesis2();
		System.out.println(balance.balance("(a)".toCharArray()));
		System.out.println(balance.balance("(a()".toCharArray()));
		System.out.println(balance.balance("(a))".toCharArray()));
		System.out.println(balance.balance("(a))(".toCharArray()));
		System.out.println(balance.balance("a".toCharArray()));
	}
	private final static char SENTINEL = ' ';
	public String balance(char[] a) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int j = -1;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == ')' && stack.isEmpty()) {
				a[j] = a[i];
			} else {
				a[++j] = a[i];
				if (a[i] == '(') {
					stack.push(j);
				} else if (a[i] == ')' && !stack.isEmpty()) {
					stack.pop();
				}
			}
		}
		int k = j;
		int size = stack.size();
		for (int i = j; i >= 0; i--) {
			if (!stack.isEmpty() && i == stack.peek()) {
				a[k] = a[i];
				stack.pop();
			} else {
				a[k--] = a[i];
			}
		}
		int length = j + 1 - size + 1;
		return length > 0 ? new String(Arrays.copyOfRange(a, size, j + 1)) : "";
	}
}
