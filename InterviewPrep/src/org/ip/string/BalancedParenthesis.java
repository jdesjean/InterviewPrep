package org.ip.string;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/">lC: 1249</a>
 * Facebook 10/03/2019
 */
public class BalancedParenthesis {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"lee(t(c)o)de", "lee(t(c)o)de)"};
		Object[] tc2 = new Object[] {"ab(c)d", "a)b(c)d"};
		Object[] tc3 = new Object[] {"", "))(("};
		Object[] tc4 = new Object[] {"a(b(c)d)", "(a(b(c)d)"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Function[] solvers = new Function[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	public static class Solver3 implements Function<String, String> {
		@Override
		public String apply(String t) {
			char[] a = t.toCharArray();
			Deque<Integer> stack = new LinkedList<Integer>();
			int j = -1;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == ')' && stack.isEmpty()) {
					continue;
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
	public static class Solver2 implements Function<String, String> {
		private final static char SENTINEL = '\0';
		@Override
		public String apply(String t) {
			char[] a = t.toCharArray();
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
	public static class Solver implements Function<String, String> {

		@Override
		public String apply(String s) {
			Deque<Integer> stack = new LinkedList<Integer>();
			StringBuilder copy = new StringBuilder();
			int j = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '(') {
					stack.addLast(i);
				} else if (s.charAt(i) == ')') {
					if (!stack.isEmpty()) {
						stack.removeLast();
					} else {
						copy(copy, s, j, i);
						j = i + 1;
					}
				}
			}
			while (!stack.isEmpty()) {
				int index = stack.removeFirst();
				copy(copy, s, j, index);
				j = index + 1;
			}
			for (; j < s.length(); j++) {
				copy(copy, s, j, j + 1);
			}
			return copy.toString();
		} 
		void copy(StringBuilder copy, String s, int i, int j) {
			for (; i < j; i++) {
				copy.append(s.charAt(i));
			}
		}
	}
}
