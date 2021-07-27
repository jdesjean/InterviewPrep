package org.ip.stream;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-median-from-data-stream/">LC: 295</a>
 */
public class Median {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new double[] {1,1.5,2}, new int[] {1,2,3}};
		Object[] tc2 = new Object[] { new double[] {-1,-1.5,-2}, new int[] {-1,-2,-3}};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class MedianFinder {
		PriorityQueue<Integer> min = new PriorityQueue<>();
		PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
		public MedianFinder() {
	        
	    }
	    
	    public void addNum(int num) {
	        min.add(num);
	        max.add(min.remove());
	        if (min.size() < max.size()) {
	        	min.add(max.remove());
	        }
	    }
	    
	    public double findMedian() {
	    	if (min.size() == 0) return 0;
	        if (min.size() == max.size()) {
	        	return (min.peek() + max.peek()) / 2d; 
	        } else {
	        	return min.peek();
	        }
	    }
	}
	interface Problem extends Function<int[], double[]> {
		
	}
	static class Solver implements Problem {

		@Override
		public double[] apply(int[] t) {
			double[] res = new double[t.length];
			MedianFinder finder = new MedianFinder();
			for (int i = 0; i < t.length; i++)  {
				finder.addNum(t[i]);
				res[i] = finder.findMedian();
			}
			return res;
		}
		
	}
}
