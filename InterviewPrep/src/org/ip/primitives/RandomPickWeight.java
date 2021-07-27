package org.ip.primitives;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <a href="https://leetcode.com/problems/random-pick-with-weight/">LC: 528</a>
 */
public class RandomPickWeight {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {}, new int[] {1}};
		Object[] tc2 = new Object[] {new int[] {}, new int[] {1,3}};
		Object[] tc3 = new Object[] {new int[] {}, new int[] {}};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		for (Object[] tc : tcs) { 
			System.out.print(String.valueOf(tc[0]));
			Solution solution = new Solution((int[]) tc[1]);
			System.out.print("," + solution.pickIndex());
			System.out.println();
		}
	}
	static class Solution {

	    private int[] w;
	    Random random = new Random(0);
		private int sum;

		public Solution(int[] w) {
	        int sum = 0;
	        for (int i = 0; i < w.length; i++) {
	        	sum += w[i];
	        	w[i] = sum;
	        }
	        this.w = w;
	        this.sum = sum;
	    }
	    
	    public int pickIndex() {
	    	int v = random.nextInt(sum) + 1;
	    	int l = 0;
	    	for (int h = w.length; l < h; ) {
	    		int m = l + (h - l) / 2;
	    		if (v > w[m]) {
	    			l = m + 1;
	    		} else {
	    			h = m;
	    		}
	    	}
	       return l;
	    }
	}
}
