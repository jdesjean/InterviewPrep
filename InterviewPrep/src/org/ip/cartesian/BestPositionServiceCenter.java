package org.ip.cartesian;

import java.util.function.ToDoubleFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/best-position-for-a-service-centre/">LC: 1515</a>
 */
public class BestPositionServiceCenter {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4d, new int[][] {{0,1},{1,0},{1,2},{2,1}}};
		Object[] tc2 = new Object[] {2.82843, new int[][] {{1,1},{3,3}}};
		Object[] tc3 = new Object[] {0d, new int[][] {{1,1}}};
		Object[] tc4 = new Object[] {2.73205, new int[][] {{1,1},{0,0},{2,0}}};
		Object[] tc5 = new Object[] {32.94036, new int[][] {{0,1},{3,2},{4,5},{7,6},{8,9},{11,1},{2,12}}};
		
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		final static int[][] DIRS = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
		final static double EPS = 1e-6;
		final static int MAX = 100;
		@Override
		public double applyAsDouble(int[][] value) {
			double[] pos = centroid(value);
			double res = distance(value, pos);
			double chg = MAX;
			boolean zoom = false;
			while (chg > EPS) {
				zoom = true;
	            for (int i = 0; i < DIRS.length; i++) {
	            	double[] next = new double[] {pos[0] + chg * DIRS[i][0], pos[1] + chg * DIRS[i][1]};
	                double dist = distance(value, next);
	                if (dist < res) {
	                	res = dist; 
	                    pos = next;
	                    zoom = false; 
	                    break;
	                }
	            }
	            if (zoom) {
	            	chg /= 2;
	            }
			}
			return res;
		}
		double[] centroid(int[][] value) {
			double[] pos = new double[2]; 
			for (int i = 0; i < value.length; i++) {
				pos[0] += value[i][0];
				pos[1] += value[i][1];
			}
			pos[0] /= value.length;
			pos[1] /= value.length;
			return pos;
		}
		double distance(int[][] value, double[] a) {
			double sum = 0;
			for (int i = 0; i < value.length; i++) {
				sum += distance(a, value[i]);
			}
			return sum;
		}
		double distance(double[] a, int[] b) {
			double dx = b[0] - a[0];
			double dy = b[1] - a[1];
			return Math.sqrt(dx * dx + dy * dy);
		}
	}
	interface Problem extends ToDoubleFunction<int[][]> {
		
	}
}
