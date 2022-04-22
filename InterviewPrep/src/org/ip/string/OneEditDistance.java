package org.ip.string;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/one-edit-distance/">LC: 161</a>
 */
public class OneEditDistance {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "ab", "acb"};
		Object[] tc2 = new Object[] {false, "", ""};
		Object[] tc3 = new Object[] {false, "cab", "ad"};
		Object[] tc4 = new Object[] {true, "a", ""};
		Object[] tc5 = new Object[] {false, "ab", "acd"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public boolean test(String s, String t) {
			if (s.length() < t.length()) {
		    	return test(t, s);
		    }
			return (s.length() == t.length() && isOneModify(s,t))
					|| (s.length() - t.length() == 1 && isOneDel(s,t));
		}
		public boolean isOneDel(String s, String t) {
		    for(int i = 0, j = 0; i < s.length() && j < t.length();){
		        if(s.charAt(i) == t.charAt(j)) {
		        	i++;
		        	j++;
		        } else if (++i - j > 1) {
		        	return false;
		        }
		    }
		    return true;
		}
		public boolean isOneModify(String s, String t) {
		    int diff = 0;
		    for(int i = 0; i < s.length(); i++){
		        if(s.charAt(i) == t.charAt(i)) continue;
		        if (++diff > 1) return false;
		    }
		    return diff == 1;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public boolean test(String s, String t) {
			if (s.length() < t.length()) {
		    	return test(t, s);
		    }
			return s.length() - t.length() <= 1 && test(s, t, 0, 0, 1);
		}
		boolean test(String s, String t, int i, int j, int k) {
			if (k < 0) return false;
			for (; i < s.length() && j < t.length(); i++, j++) {
				if (s.charAt(i) == t.charAt(j)) continue;
				return test(s, t, i + 1, j, k - 1) 
						|| test(s, t, i + 1, j + 1, k - 1);
			}
			int diff = s.length() - i  + t.length() - j;
			return diff == k;
		}
	}
	interface Problem extends BiPredicate<String, String> {
		
	}
}
