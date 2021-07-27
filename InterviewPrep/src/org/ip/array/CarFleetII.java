package org.ip.array;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/car-fleet/">LC: 853</a>
 */
public class CarFleetII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new double[] {1,-1,3,-1}, new int[][] {{1,2},{2,1},{4,3},{7,2}}};
		Object[] tc2 = new Object[] { new double[] {2,1,1.5,-1}, new int[][] {{3,4},{5,4},{6,3},{9,1}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		double collision(int[][] a, int i, int j) {
			return (double)(a[j][0] - a[i][0]) / (double)(a[i][1] - a[j][1]);
		}
		
		@Override
		public double[] apply(int[][] a) {
			double[] res = new double[a.length];
			Deque<Integer> deque = new LinkedList<>();
			for (int i = a.length - 1; i >= 0; i--) {
				//If slower than car in front or collision happen later than that cars collision
				//we can remove them from the stack as they're no longer important to other cars collision
				while (!deque.isEmpty() 
						&& (a[i][1] <= a[deque.peek()][1] // slower than car in front
						|| (deque.size() > 1 && collision(a, i, deque.peek()) > res[deque.peek()])) //collision later than collision from car in front
						) {
					deque.pop();
				}
				res[i] = deque.isEmpty() ? -1 : collision(a, i, deque.peek());
				deque.push(i);
			}
			return res;
		}
	}
	interface Problem extends Function<int[][], double[]> {
		
	}
}
