package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-invalid-parentheses/">LC: 301</a>
 * 
 */
public class RemoveInvalidParenthesis {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(new String[] { "()()()", "(())()" }), "()())()" };
		Object[] tc2 = new Object[] { Arrays.asList(new String[] { "(a)()()", "(a())()" }), "(a)())()" };
		Object[] tc3 = new Object[] { Arrays.asList(new String[] { "" }), ")(" };
		Object[] tc4 = new Object[] { Arrays.asList(new String[] { "()" }), "(()" };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	
	static class Solver2 implements Problem {

		@Override
		public List<String> apply(String s) {
			List<String> output = new ArrayList<>();
	        removeHelper(s, output, 0, 0, '(', ')');
	        return output;
		}

	    public void removeHelper(String s, List<String> output, int iStart, int jStart, char openParen, char closedParen) {
	    	int open = 0;
	        for (int i = iStart; i < s.length(); i++) {
	            if (s.charAt(i) == openParen) {
	            	open++;
	        	} else if (s.charAt(i) == closedParen && --open < 0) {
	        		for (int j = jStart; j <= i; j++) { // Try removing one at each position, skipping duplicates
	                	if (s.charAt(j) == closedParen && (j == jStart || s.charAt(j - 1) != closedParen)) {
	                		// Recursion: iStart = i since we now have valid # closed parenthesis thru i. jStart = j prevents duplicates
	                        removeHelper(s.substring(0, j) + s.substring(j + 1, s.length()), output, i, j, openParen, closedParen);
	                	}
	                }
	                return; // Stop here. The recursive calls handle the rest of the string.
	            }
	        }
	        // No invalid closed parenthesis detected. Now check opposite direction, or reverse back to original direction.
	        if (open > 0) {
	        	String reversed = new StringBuilder(s).reverse().toString();
	        	removeHelper(reversed, output, 0, 0, ')','(');
	        } else {
	        	output.add( openParen == ')' ? new StringBuilder(s).reverse().toString() : s);
	        }
	    }
	}

	static class Solver implements Problem {

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
	interface Problem extends Function<String, List<String>> {
		
	}
}
