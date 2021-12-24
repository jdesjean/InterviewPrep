package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/string-compression-ii/">LC: 1531</a>
 */
public class StringCompressionII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, "aabbaa", 2};
		Object[] tc2 = new Object[] {3, "aaaaaaaaaaa", 0};
		Object[] tc3 = new Object[] {4, "aaabcccd", 2};
		Object[] tc4 = new Object[] {0, "a", 1};
		Object[] tc5 = new Object[] {1, "abc", 2};
		Object[] tc6 = new Object[] {4, "llllllllllttttttttt", 1};
		//Object[] tc6 = new Object[] {10, "abcdefghijklmnopqrstuvwxyz", 16};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] { new Solver2(), new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(String t, Integer u) {
			char[] s = t.toCharArray();
			int k = u;
	        
	        int[][] dp = new int[s.length][k + 1];
	        for(int[] row : dp) Arrays.fill(row, -1);
	        
	        return dfs(s, 0, k, dp);
		}
		
		private int dfs(char[] s, int curIdx, int rest, int[][] dp) {
	        // reach end or we can simply delete all
	        if (curIdx == s.length || s.length - curIdx <= rest) return 0;
	        
	        if(dp[curIdx][rest] != -1) return dp[curIdx][rest];
	        
	        int[] fre = new int[26];
	        int res = Integer.MAX_VALUE;
	        
	        // i - curIdx + 1 - most. count of chars we need to delete
	        // but we cannot break. Because when most change, rest could also change.
	        // So we must iterate to end
	        for(int i = curIdx, most = 0; i < s.length; i++) {
	            int idx = s[i] - 'a';
	            fre[idx]++;
	            
	            most = Math.max(most, fre[idx]);
	            if(rest >= i - curIdx + 1 - most) // only recurse when we can delete all other chars
	                res = Math.min(res, getLen(most) + 1 + dfs(s, i + 1, rest - (i - curIdx + 1 - most), dp));
	        }
	        dp[curIdx][rest] = res;
	        return res;
	    }
	    
	    private int getLen(int most) {
	        return most <= 1 ? 0 : most < 10 ? 1 : most < 100 ? 2 : 3;
	    }
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String t, Integer u) {
			if (t.length() == 0) return 0;
			List<Pair> pairs = pairs(t);
			return dfs(pairs, u);
		}
		public int dfs(List<Pair> pairs, int k) {
			if (k == 0) {
				return length(pairs);
			}
			int min = length(pairs);
			for (Pair pair : pairs) {
				if (pair.length == 0) continue;
				int dk = Math.min(pair.length, k);
				int pLength = length(pair.length);
				pair.length -= dk;
				if (pair.length == 0 || pLength != length(pair.length)) { // rle does not change
					min = Math.min(min, dfs(pairs, k - dk));
				}
				pair.length += dk;
			}
			return min;
		}
		int length(List<Pair> pairs) {
			if (pairs.isEmpty()) return 0;
			int length = 0;
			int pLength = 0;
			Pair prev = null;
			for (Pair pair : pairs) {
				if (pair.length == 0) continue;
				else if (prev != null && prev.character == pair.character) {
					pLength += pair.length;
				} else if (prev != null) {
					length += pLength == 0 ? 0 : length(pLength) + 1;
					pLength = pair.length;
				}
				if (prev == null) {
					pLength = pair.length;
				}
				prev = pair;
			}
			length += pLength == 0 ? 0 : length(pLength) + 1;
			return length;
		}
		int length(int length) {
			return length <= 1 ? 0 : length < 10 ? 1 : length < 100 ? 2 : 3;
		}
		List<Pair> pairs(String t) {
			List<Pair> pairs = new ArrayList<>();
			int l = 1;
			for (int i = 1; i < t.length(); i++) {
				if (t.charAt(i) == t.charAt(i - 1)) {
					l++;
				} else {
					pairs.add(new Pair(t.charAt(i - 1), l));
					l = 1;
				}
			}
			pairs.add(new Pair (t.charAt(t.length() - 1), l));
			return pairs;
		}
		
	}
	static class Pair {
		Character character;
		int length;
		public Pair(Character character, int length) {
			super();
			this.character = character;
			this.length = length;
		}
		@Override
		public String toString() {
			return "Pair [character=" + character + ", length=" + length + "]";
		}
		
	}
	interface Problem extends ToIntBiFunction<String, Integer> {
		
	}
}
