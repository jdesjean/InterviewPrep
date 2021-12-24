package org.ip;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/k-closest-points-to-origin/">LC: 973</a>
 */
public class KClosestOrigin {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{-2,2}}, new int[][] {{1,3},{-2,2}}, 1};
		Object[] tc2 = new Object[] {new int[][] {{3,3}, {-2,4}}, new int[][] {{3,3},{5,-1},{-2,4}}, 2};
		Object[] tc3 = new Object[] {new int[][] {{0,1},{1,0}}, new int[][] {{0,1},{1,0}}, 2};
		Object[] tc4 = new Object[] {new int[][] {{-2,2},{2,-2}}, new int[][] {{1,3},{-2,2},{2,-2}}, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new SolverPQ1(), new SolverPQ2(), new SolverPartition(), new SolverPartition2()};
		Test.apply(solvers, tcs);
	}
	private static class SolverPartition2 implements Problem {

		@Override
		public int[][] apply(int[][] t, Integer u) {
			int k = u - 1;
			double[] d = distance(t);
			Points points = new Points(t, d);
			for (int min = 0, max = t.length - 1; min < max;) {
				int mid = min + (max - min) / 2;
				int p = partition(points, min, max, mid);
				if (p < k) {
					min = p + 1;
				} else if (p > k) {
					max = p - 1; 
				} else {
					break;
				}
			}
			return Arrays.copyOf(t, u);
		}
		int partition(Points points, int l, int r, int p) {
			double pd = points.getDistance(p);
			for (int m = l; m <= r; ) {
				double md = points.getDistance(m);
				if (md < pd) {
					points.swap(l++, m++);
				} else if (md > pd) {
					points.swap(m, r--);
				} else {
					m++;
				}
			}
			return l;
		}
		static class Points {
			private final int[][] t;
			private final double[] d;
			public Points(int[][] t, double[] d) {
				this.t = t;
				this.d = d;
			}
			double getDistance(int i) {
				return d[i];
			}
			void swap(int a, int b) {
				swap(t, a, b);
				swap(d, a, b);
			}
			static void swap(double[] array, int i, int j) {
				double tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
			}
			static void swap(Object[] array, int i, int j) {
				Object tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
			}
		}
		static double[] distance(int[][] t) {
			double[] res = new double[t.length];
			for (int i = 0; i < t.length; i++) {
				res[i] = Math.sqrt(t[i][0] * t[i][0] + t[i][1] * t[i][1]);
			}
			return res;
		}
		
	}
	private static class SolverPartition implements Problem {

		@Override
		public int[][] apply(int[][] t, Integer u) {
			Point[] point = new Point[t.length];
			for (int i = 0; i < t.length; i++) {
				point[i] = new Point(t[i]);
			}
			int m = quickSelect(point, u - 1);
			int[][] res = new int[m + 1][2];
			for (int i = 0; i <= m; i++) {
				res[i] = point[i].point;
			}
			return res;
		}
		int quickSelect(Point[] points, int k) {
			int l = 0;
			for (int m = 0, r = points.length - 1; l < r; ) {
				m = l + (r - l) / 2;
				m = partition(points, l, r, m);
				if (m == k) {
					return m;
				} else if (m < k) {
					l = m + 1;
				} else {
					r = m;
				}
			}
			return l;
		}
		int partition(Point[] points, int l, int r, int p) {
			double pivot = points[p].distance;
			for (int m = l; m <= r; ) {
				if (points[m].distance < pivot) {
					swap(points, l++, m++);
				} else if (points[m].distance > pivot) {
					swap(points, m, r--);
				} else {
					m++;
				}
			}
			return l;
		}
	}
	static void swap(Object[] obj, int l, int r) {
		Object tmp = obj[l];
		obj[l] = obj[r];
		obj[r] = tmp;
	}
	static class Point {
		private double distance;
		private int[] point;

		public Point(int[] point) {
			this.point = point;
			this.distance = Math.sqrt(point[0] * point[0] + point[1] * point[1]);
		}
		@Override
		public String toString() {
			return "{x: " + point[0] + ", y: " + point[1] + "}";
		}
	}
	private static class SolverPQ1 implements Problem {
		public int[][] apply(int[][] points, Integer k) {
			PriorityQueue<Point> pq = new PriorityQueue<>(Collections.reverseOrder());
			for (int i = 0; i < points.length; i++) {
				Point p = new Point(points[i][0], points[i][1]);
				while (pq.size() >= k && p.distance < pq.peek().distance) {
					pq.remove();
				}
				if (pq.size() < k) {
					pq.add(p);
				}
			}
			int[][] res = new int[pq.size()][2];
			for (int i = 0; i < res.length; i++) {
				Point p = pq.remove();
				res[i][0] = p.x;
				res[i][1] = p.y;
			}
			return res;
		}
		private static class Point implements Comparable<Point> {
			private final double distance;
			private int x;
			private int y;
			public Point(int x, int y) {
				this.x = x;
				this.y = y;
				distance = Math.sqrt(x * x + y * y);
			}
			@Override
			public int compareTo(Point o) {
				return Double.compare(distance, o.distance);
			}
		}
	}
	private static class SolverPQ2 implements Problem {
		@Override
		public int[][] apply(int[][] points, Integer k) {
			PriorityQueue<int[]> pq = new PriorityQueue<>(new PointComparator());
			for (int i = 0; i < points.length; i++) {
				double distance = distance(points[i]);
				while (pq.size() >= k && distance < distance(pq.peek())) {
					pq.remove();
				}
				if (pq.size() < k) {
					pq.add(points[i]);
				}
			}
			int[][] res = new int[pq.size()][2];
			for (int i = 0; i < res.length; i++) {
				res[i] = pq.remove();
			}
			return res;
		}
		private static class PointComparator implements Comparator<int[]> {

			@Override
			public int compare(int[] o1, int[] o2) {
				return Double.compare(distance(o2), distance(o1)); // reverse order for max heap
			}	
		}
		public static double distance(int[] a) {
			return Math.sqrt(a[0] * a[0] + a[1] * a[1]);
		}
	}
	interface Problem extends BiFunction<int[][], Integer, int[][]> {
		
	}
}
