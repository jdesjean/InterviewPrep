package org.ip.honor;

import java.util.Deque;
import java.util.LinkedList;

// EPI 2018: 24.11
public class LSMP {
	public static void main(String[] s) {
		String p = "((())()(()(";
		System.out.println(new LSMP().solve(p));
	}
	public int solve(String p) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int max = 0;
		int end = 0;
		for (int i = 0; i < p.length(); i++) {
			if (p.charAt(i) == '(') {
				stack.push(i);
			} else if (stack.isEmpty()) {
				end = i;
			} else {
				stack.pop();
				int start = stack.isEmpty() ? end : stack.peek();
				max = Math.max(max, i - start);
			}
		}
		return max;
	}
}
