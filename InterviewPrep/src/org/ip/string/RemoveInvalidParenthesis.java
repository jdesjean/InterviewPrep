package org.ip.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <a href="https://leetcode.com/problems/remove-invalid-parentheses/">LC: 301</a>
 * 
 */
public class RemoveInvalidParenthesis {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(new String[] { "()()()", "(())()" }), "()())()" };
		Object[] tc2 = new Object[] { Arrays.asList(new String[] { "(a)()()", "(a())()" }), "(a)())()" };
		Object[] tc3 = new Object[] { Arrays.asList(new String[] { "" }), ")(" };
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		Function<String, List<String>>[] solvers = new Function[] { new Solver() };
		for (Object[] tc : tcs) {
			System.out.print(String.valueOf(tc[0]));
			for (Function<String, List<String>> solver : solvers) {
				System.out.print("," + String.valueOf(solver.apply((String) tc[1])));
			}
			System.out.println();
		}
	}

	public static class Solver implements Function<String, List<String>> {

		@Override
		public List<String> apply(String t) {
			Set<String> res = new HashSet<>();
			int[] toRemove = toRemove(t);
			solve(x -> res.add(x), 0, 0, new char[t.length()], 0, t, 0, toRemove[0], toRemove[1]);
			return res.stream().collect(Collectors.toList());
		}
		
		int[] toRemove(String t) {
			int open = 0, close = 0;
			int diffs = 0;
			for (int i = 0; i < t.length(); i++) {
				if (t.charAt(i) == '(') {
					open++;
				} else if (t.charAt(i) == ')') {
					if (close < open) {
						close++;
					} else {
						diffs++;
					}
				}
			}
			return new int[] {open - close, diffs};
		}

		void solve(Consumer<String> consumer, int open, int close, char[] buffer, int bufferIndex, String t,
				int index, int openToRemove, int closeToRemove) {
			if (t.length() == index) {
				if (open == close && openToRemove == 0 && closeToRemove == 0) {
					consumer.accept(new String(buffer, 0, bufferIndex));
				}
				return;
			}
			if (t.charAt(index) == '(') {
				if (openToRemove > 0) {
					solve(consumer, open, close, buffer, bufferIndex, t, index + 1, openToRemove - 1, closeToRemove); // remove
				}
				if (close - open + openToRemove < t.length() - index) {
					buffer[bufferIndex++] = '(';
					solve(consumer, open + 1, close, buffer, bufferIndex, t, index + 1, openToRemove, closeToRemove);
				}
			} else if (t.charAt(index) == ')') {
				if (closeToRemove > 0) {
					solve(consumer, open, close, buffer, bufferIndex, t, index + 1, openToRemove, closeToRemove - 1); // remove
				}
				if (close < open && close - open + closeToRemove < t.length() - index) {
					buffer[bufferIndex++] = ')';
					solve(consumer, open, close + 1, buffer, bufferIndex, t, index + 1, openToRemove, closeToRemove);
				}
			} else {
				buffer[bufferIndex++] = t.charAt(index);
				solve(consumer, open, close, buffer, bufferIndex, t, index + 1, openToRemove, closeToRemove);
			}
		}
	}
}
