package org.ip.primitives;

import java.util.Random;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/random-pick-with-weight/">LC: 528</a>
 */
public class RandomPickWeight {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {0}, new TestCase1()};
		Object[] tc2 = new Object[] {new int[] {1,1,1,1,0}, new TestCase2()};
		Object[] tc3 = new Object[] {new int[] {}, new TestCase3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solution()};
		Test.apply(solvers, tcs);
	}
	static class Solution implements Problem {

	    private int[] w;
	    Random random = new Random(0);
		public Solution() {
			
		}
		public Solution(int[] w) {
	       accept(w);
	    }
	    
	    public int pickIndex() {
	    	int v = random.nextInt(w[w.length - 1]) + 1;
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

		@Override
		public int[] apply(Function<Problem, int[]> t) {
			return t.apply(this);
		}

		@Override
		public void accept(int[] w) {
	        for (int i = 1; i < w.length; i++) {
	        	w[i] += w[i - 1];
	        }
	        this.w = w;
		}
	}
	interface Problem extends Function<Function<Problem, int[]>, int[]> {
		public int pickIndex();
		public void accept(int[] a);
	}
	static class TestCase1 implements Function<Problem, int[]> {

		@Override
		public int[] apply(Problem t) {
			t.accept(new int[] {1});
			return new int[] {
					t.pickIndex()
			};
		}
		
	}
	static class TestCase2 implements Function<Problem, int[]> {

		@Override
		public int[] apply(Problem t) {
			t.accept(new int[] {1,3});
			return new int[] {
					t.pickIndex(),
					t.pickIndex(),
					t.pickIndex(),
					t.pickIndex(),
					t.pickIndex(),
			};
		}
		
	}
	static class TestCase3 implements Function<Problem, int[]> {

		@Override
		public int[] apply(Problem t) {
			t.accept(new int[] {});
			return new int[] {
					
			};
		}
		
	}
}
