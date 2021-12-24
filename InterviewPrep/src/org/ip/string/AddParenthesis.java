package org.ip.string;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/">LC: 921</a>
 */
public class AddParenthesis {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 1, "())"};
		Object[] tc2 = new Object[] { 3, "((("};
		Object[] tc3 = new Object[] { 0, "()"};
		Object[] tc4 = new Object[] { 4, "()))(("};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String value) {
			int open = 0;
			int res = 0;
			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) == '(') {
					open++;
				} else if (open != 0){
					open--;
				} else {
					res++;;
				}
			}
			
			return res + open;
		}
		
	}
	interface Problem extends ToIntFunction<String> {
		
	}
}
