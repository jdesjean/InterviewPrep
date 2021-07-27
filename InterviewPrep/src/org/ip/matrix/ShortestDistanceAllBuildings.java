package org.ip.matrix;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/shortest-distance-from-all-buildings/">LC: 317</a>
 */
public class ShortestDistanceAllBuildings {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {7, new int[][] {
			{1,0,2,0,1},
			{0,0,0,0,0},
			{0,0,1,0,0}
		}};
		Object[] tc2 = new Object[] {-1, new int[][] {
			{1},
		}};
		Object[] tc3 = new Object[] {88, new int[][] {
			{1,1,1,1,1,0},
			{0,0,0,0,0,1},
			{0,1,1,0,0,1},
			{1,0,0,1,0,1},
			{1,0,1,0,0,1},
			{1,0,0,0,0,1},
			{0,1,1,1,1,0}
		}};
		Object[] tc4 = new Object[] {21, new int[][] {
			{1,1,1,1,1,0},
			{0,0,0,0,0,1},
			{0,1,1,0,0,1}
		}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem {

		final static int[][] directions = new int[][] {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
		
		@Override
		public Integer apply(int[][] t) {
			if (t == null || t.length == 0) return -1;
			int[][] distances = new int[t.length][t[0].length];
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 1) continue;
					boolean[][] visited = _apply(t, distances, l, c);
					convertUnvisited(t, visited);
				}
			}
			int min = Integer.MAX_VALUE;
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) continue;
					min = Math.min(min, distances[l][c]);
				}
			}
			return min != Integer.MAX_VALUE ? min : -1;
		}
		private final static Point SENTINEL = new Point(0, 0);
		boolean[][] _apply(int[][] t, int[][] distances, int l, int c) {
			Deque<Point> queue = new LinkedList<>();
			boolean[][] visited = new boolean[t.length][t[0].length];
			queue.add(new Point(l, c));
			queue.add(SENTINEL);
			int distance = 0;
			while (!queue.isEmpty()) {
				Point point = queue.removeFirst();
				if (point == SENTINEL) {
					distance++;
					if (!queue.isEmpty())  {
						queue.addLast(SENTINEL);
					}
					continue;
				}
				if (visited[point.l][point.c]) continue;
				visited[point.l][point.c] = true;
				distances[point.l][point.c] += distance;
				for (int i = 0; i < directions.length; i++) {
					int ll = point.l + directions[i][0];
					int cc = point.c + directions[i][1];
					if (ll < 0 || cc < 0 || ll >= t.length || cc >= t[0].length) continue;
					if (t[ll][cc] != 0) continue; //cannot pass through
					queue.addLast(new Point(ll, cc));
				}
			}
			return visited;
		}
		void convertUnvisited(int[][] t, boolean[][] visited) {
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (visited[l][c] || t[l][c] != 0) continue;
					t[l][c] = 3;
				}
			}
		}
	}
	public static class Solver implements Problem {

		final static int[][] directions = new int[][] {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
		
		@Override
		public Integer apply(int[][] t) {
			if (t == null || t.length == 0) return -1;
			int[][] distances = new int[t.length][t[0].length];
			Set<Point>[][] visited = new HashSet[t.length][t[0].length];
			Deque<PointDistance> queue = new LinkedList<PointDistance>();
			List<PointDistance> buildings = buildings(t);
			queue.addAll(buildings);
			while (!queue.isEmpty()) {
				PointDistance pd = queue.removeFirst();
				if (visited[pd.current.l][pd.current.c] == null) visited[pd.current.l][pd.current.c] = new HashSet<>();
				if (!visited[pd.current.l][pd.current.c].add(pd.original)) continue; // already visited
				distances[pd.current.l][pd.current.c] += pd.distance;
				for (int i = 0; i < directions.length; i++) {
					int ll = pd.current.l + directions[i][0];
					int cc = pd.current.c + directions[i][1];
					if (ll < 0 || cc < 0 || ll >= t.length || cc >= t[0].length) continue;
					if (t[ll][cc] != 0) continue; //cannot pass through
					Point next = new Point(ll, cc);
					queue.addLast(new PointDistance(pd.original, next, pd.distance + 1));
				}
			}
			int min = Integer.MAX_VALUE;
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0 || visited[l][c] == null || visited[l][c].size() != buildings.size()) continue;
					min = Math.min(min, distances[l][c]);
				}
			}
			return min != Integer.MAX_VALUE ? min : -1;
		}
		List<PointDistance> buildings(int[][] t) {
			List<PointDistance> buildings = new ArrayList<>();
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] == 1) {
						Point point = new Point(l, c);
						buildings.add(new PointDistance(point, point, 0));
					}
				}
			}
			return buildings;
		}
		public static class PointDistance {
			Point original;
			Point current;
			int distance;
			public PointDistance(Point original, Point current, int distance) {
				super();
				this.original = original;
				this.current = current;
				this.distance = distance;
			}
			@Override
			public String toString() {
				return "PointDistance [original=" + original + ", current=" + current + ", distance=" + distance + "]";
			}
			
		}
	}
	public static class Point {
		int l;
		int c;
		public Point(int l, int c) {
			this.l = l;
			this.c = c;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + c;
			result = prime * result + l;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (c != other.c)
				return false;
			if (l != other.l)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Point [l=" + l + ", c=" + c + "]";
		}
		
	}
	public interface Problem extends Function<int[][], Integer> {
		
	}
}
