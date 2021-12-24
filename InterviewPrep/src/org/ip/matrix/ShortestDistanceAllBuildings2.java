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
public class ShortestDistanceAllBuildings2 {
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
		Object[] tc5 = new Object[] {8, new int[][] {
			{0,2,1,0},
			{2,0,2,0},
			{1,2,0,0},
			{0,0,0,0},
		}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	
	public static class Solver implements Problem {
		private final static int[][] DIRECTIONS = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
		private final static int[] SENTINEL = new int[0];
		
		@Override
		public Integer apply(int[][] t) {
			int[][] d = new int[t.length][t[0].length];
			int v = 0;
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 1) continue;
					bfs(t, d, l, c, v--);
				}
			}
			return min(t, d, v);
		}
		int min(int[][] t, int[][] d, int v) {
			int min = Integer.MAX_VALUE;
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != v) continue;
					min = Math.min(min, d[l][c]);
				}
			}
			return min == Integer.MAX_VALUE ? -1 : min;
		}
		void bfs(int[][] t, int[][] d, int l, int c, int v) {
			Deque<int[]> deque = new LinkedList<>();
			addValid(deque, t, l, c, v);
			deque.add(SENTINEL);
			int distance = 1;
			while (!deque.isEmpty()) {
				int[] pos = deque.removeFirst();
				if (pos == SENTINEL) {
					if (!deque.isEmpty()) {
						deque.addLast(SENTINEL);
					}
					distance++;
					continue;
				}
				int vv = t[pos[0]][pos[1]];
				if (vv != v) continue;
				t[pos[0]][pos[1]]--;
				d[pos[0]][pos[1]] += distance;
				addValid(deque, t, pos[0], pos[1], v);
			}
		}
		void addValid(Deque<int[]> deque, int[][] t, int l, int c, int v) {
			for (int i = 0; i < DIRECTIONS.length; i++) {
				int ll = l + DIRECTIONS[i][0];
				int cc = c + DIRECTIONS[i][1];
				if (ll < 0 || cc < 0 || ll >= t.length || cc >= t[ll].length || t[ll][cc] > v) continue;
				deque.addLast(new int[] {ll,cc});
			}
		}
	}
	public interface Problem extends Function<int[][], Integer> {
		
	}
}
