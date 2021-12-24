package org.ip.array;

import java.util.Collections;
import java.util.PriorityQueue;

import org.ip.Test;
import org.ip.Test.QuadFunction;

/**
 * <a href="https://leetcode.com/problems/ipo/">LC: 502</a>
 */
public class IPO {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, 2, 0, new int[] {1,2,3}, new int[] {0,1,1}};
		Object[] tc2 = new Object[] {6, 3, 0, new int[] {1,2,3}, new int[] {0,1,2}};
		Object[] tc3 = new Object[] {0, 1, 0, new int[] {1,2,3}, new int[] {1,1,2}};
		Object[] tc4 = new Object[] {1, 2, 0, new int[] {1,2,3}, new int[] {0,9,10}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public Integer apply(Integer a, Integer b, int[] profits, int[] capital) {
			int k = a;
			int w = b;
			int sum = w;
			int j = 0;
			PriorityQueue<int[]> min = new PriorityQueue<>((x, y) -> x[1] - y[1]);
			PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
			for (int i = 0; i < profits.length; i++) {
				min.add(new int[] {profits[i], capital[i]});
			}
			for (int i = 0; i < k && i < profits.length; i++) {
				while (!min.isEmpty() && min.peek()[1] <= sum) {
					max.add(min.remove()[0]);
				}
				if (max.isEmpty()) break;
				sum += max.remove();
			}
			return sum;
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public Integer apply(Integer a, Integer b, int[] profits, int[] capital) {
			int k = a;
			int w = b;
			int sum = w;
			int j = 0;
			PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
			for (int i = 0; i < k && i < profits.length; i++) {
				for (int l = j; l < profits.length; l++) {
					if (capital[l] <= sum) {
						pq.add(profits[l]);
						Utils.swap(profits, j, l);
						Utils.swap(capital, j++, l);
						// klogn vs (klogk + k^2)
						if (pq.size() > k) {
							pq.remove(Collections.min(pq));
						}
					}
				}
				if (pq.isEmpty()) break;
				sum += pq.remove();
			}
			return sum;
		}
	}
	/**
	 * Works when capital is presorted.
	 */
	static class Solver implements Problem {

		@Override
		public Integer apply(Integer a, Integer b, int[] profits, int[] capital) {
			int k = a;
			int w = b;
			int sum = w;
			int j = 0;
			PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
			for (int i = 0; i < k && i < profits.length; i++) {
				for (; j < profits.length && capital[j] <= sum; j++) {
					pq.add(profits[j]);
					// klogn vs (klogk + k^2)
					if (pq.size() > k) {
						pq.remove(Collections.min(pq));
					}
				}
				if (pq.isEmpty()) break;
				sum += pq.remove();
			}
			return sum;
		}
	}
	interface Problem extends QuadFunction<Integer, Integer, int[], int[], Integer> {
		
	}
}
