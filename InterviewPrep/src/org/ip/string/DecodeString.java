package org.ip.string;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/decode-string/">LC: 394</a>
 */
public class DecodeString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"aaabcbc", "3[a]2[bc]"};
		Object[] tc2 = new Object[] {"accaccacc", "3[a2[c]]"};
		Object[] tc3 = new Object[] {"abcabccdcdcdef", "2[abc]3[cd]ef"};
		Object[] tc4 = new Object[] {"abccdcdcdxyz", "abc3[cd]xyz"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public String apply(String t) {
			Deque<Character> stack = new LinkedList<>();
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				if (c == ']') {
					StringBuilder sb = new StringBuilder();
					while(stack.peek() != '[') {
						sb.append(stack.pop());
					}
					stack.pop();
					int count = 0;
					int pow = 1;
					while(!stack.isEmpty() && Character.isDigit(stack.peek())) {
						count += pow * (stack.pop() - '0');
						pow *= 10;
					}
					append(stack, sb, count);
				} else {
					stack.push(c);
				}
			}
			StringBuilder sb = new StringBuilder();
			while(!stack.isEmpty()) {
				sb.append(stack.pop());
			}
			return sb.reverse().toString();
		}
		void append(Deque<Character> stack, StringBuilder sb, int count) {
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < sb.length(); j++) {
					stack.push(sb.charAt(j));
				}
			}
		}
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t) {
			return _apply(t, new AtomicInteger(-1));
		}
		String _apply(String t, AtomicInteger index) {
			StringBuilder res = new StringBuilder();
			int count = 0;
			for (int current = index.incrementAndGet(); index.get() < t.length(); current = index.incrementAndGet()) {
				char c = t.charAt(current);
				if (c == '[') {
					String next = _apply(t, index); 
					append(res, next, count);
					count = 0;
				} else if (c == ']') {
					break;
				} else if (Character.isDigit(c)){
					count = count * 10 + (c - '0');
				} else {
					res.append(c);
				}
			}
			return res.toString();
		}
		void append(StringBuilder sb, String t, int count) {
			for (int i = 0; i < count; i++) {
				sb.append(t);
			}
		}
	}
	interface Problem extends Function<String, String> {
		
	}
}
