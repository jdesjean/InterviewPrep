package org.ip.matrix;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.ToIntFunction;

import org.ip.Test;
import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/sliding-puzzle/">LC: 773</a>
 */
public class SlidingPuzzle {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {1, new int[][] {{1,2,3},{4,0,5}}};
		Object[] tc2 = new Object[] {-1, new int[][] {{1,2,3},{5,4,0}}};
		Object[] tc3 = new Object[] {5, new int[][] {{4,1,2},{5,0,3}}};
		Object[] tc4 = new Object[] {14, new int[][] {{3,2,4},{1,5,0}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static String SENTINEL = "";
		private final static int[][] DIRS = new int[][] {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};
		@Override
		public int applyAsInt(int[][] value) {
			String target = "123450";
			String start = toString(value);
			Deque<String> deque = new LinkedList<>();
			Set<String> visited = new HashSet<>();
			deque.add(start);
			visited.add(start);
			deque.add(SENTINEL);
			int level = 0;
			while (!deque.isEmpty()) {
				String current = deque.remove();
				if (current == SENTINEL) {
					level++;
					if (!deque.isEmpty()) {
						deque.add(SENTINEL);
					}
					continue;
				}
				if (current.equals(target)) {
					return level;
				}
				int pos = current.indexOf('0');
				for (int dir : DIRS[pos]) {
					String next = swap(current, pos, dir);
					if (visited.add(next)) {
						deque.add(next);
					}
				}
			}
			return -1;
		}
		String swap(String current, int i, int j) {
			char[] a = current.toCharArray();
			swap(a, i, j);
			return new String(a);
		}
		public static void swap(char[] array, int i, int j) {
			char tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
		String toString(int[][] value) {
			StringBuilder sb = new StringBuilder();
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < 3; c++) {
					sb.append(value[r][c]);
				}
			}
			return sb.toString();
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
