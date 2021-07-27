package org.ip.matrix;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/shortest-bridge/">LC: 934</a>
 */
public class ShortestBridge {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {1, new int[][] {{0,1},{1,0}}};
		Object[] tc2 = new Object[] {2, new int[][] {{0,1,0},{0,0,0},{0,0,1}}};
		Object[] tc3 = new Object[] {1, new int[][] {{1,1,1,1,1},{1,0,0,0,1},{1,0,1,0,1},{1,0,0,0,1},{1,1,1,1,1}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private static final int[][] DIRS = new int[][] {{0,1},{0,-1},{-1,0},{1,0}};
		@Override
		public int applyAsInt(int[][] value) {
			Deque<int[]> deque = swapOne(value);
			return bfsZeros(value, deque);
		}
		Deque<int[]> swapOne(int[][] value) {
			Deque<int[]> deque = new LinkedList<>();
			for (int r = 0; r < value.length; r++) {
				for (int c = 0; c < value.length; c++) {
					if (value[r][c] != 1) continue;
					dfsOnes((pos) -> {
						value[pos[0]][pos[1]] = -1;
						deque.add(pos);
					}, value, new int[] {r, c});
					return deque;
				}
			}
			return deque;
		}
		int bfsZeros(int[][] value, Deque<int[]> deque) {
			int step = 0;
			while (!deque.isEmpty()) {
				int size = deque.size();
				while (size-- > 0) {
					int[] current = deque.remove();
					for (int i = 0; i < DIRS.length; i++) {
						int r = current[0] + DIRS[i][0];
						int c = current[1] + DIRS[i][1];
						if (r < 0 || c < 0 || r >= value.length || c >= value[r].length) continue;
						if (value[r][c] == -1) continue;
						if (value[r][c] == 1) return step;
						value[r][c] = -1;
						deque.add(new int[] {r,c});
					}
				}
				step++;
			}
			return -1;
		}
		void dfsOnes(Consumer<int[]> consumer, int[][] value, int[] pos) {
			if (value[pos[0]][pos[1]] != 1) return;
			consumer.accept(pos);
			for (int i = 0; i < DIRS.length; i++) {
				int r = pos[0] + DIRS[i][0];
				int c = pos[1] + DIRS[i][1];
				if (r < 0 || c < 0 || r >= value.length || c >= value[r].length) continue; 
				int[] nextPos = new int[] {r, c};
				dfsOnes(consumer, value, nextPos);
			}
		}
	}
	static class Solver implements Problem {
		private static final int[][] DIRS = new int[][] {{0,1},{0,-1},{-1,0},{1,0}};
		@Override
		public int applyAsInt(int[][] value) {
			boolean foundFirst = false;
			for (int r = 0; r < value.length; r++) {
				for (int c = 0; c < value.length; c++) {
					if (value[r][c] == 0) {
						value[r][c] = Integer.MAX_VALUE;
					} else if (value[r][c] == 1 && !foundFirst) {
						foundFirst = true;
						dfsOnes((pos) -> value[pos[0]][pos[1]] = -1, value, new int[] {r, c});
					}
				}
			}
			for (int r = 0; r < value.length; r++) {
				for (int c = 0; c < value.length; c++) {
					if (value[r][c] != 1) continue;
					return bfsZeros(value, new int[] {r, c});
				}
			}
			return 0;
		}
		int bfsZeros(int[][] value, int[] pos) {
			Deque<int[]> deque = new LinkedList<>();
			deque.add(pos);
			int min = Integer.MAX_VALUE;
			while (!deque.isEmpty()) {
				int[] current = deque.remove();
				int v = value[current[0]][current[1]];
				if (v == -1) {
					min = Math.min(min, value[current[2]][current[3]] - 2);
					continue;
				} else if (v == 1) {
					value[current[0]][current[1]] = 2;
				} else {
					if (value[current[2]][current[3]] + 1 < value[current[0]][current[1]]) {
						value[current[0]][current[1]] = value[current[2]][current[3]] + 1; 
					} else {
						continue;
					}
				}
				for (int i = 0; i < DIRS.length; i++) {
					int r = current[0] + DIRS[i][0];
					int c = current[1] + DIRS[i][1];
					if (r < 0 || c < 0 || r >= value.length || c >= value[r].length) continue;
					if (value[r][c] == 2) continue;
					deque.add(new int[] {r,c,current[0],current[1]});
				}
			}
			return min;
		}
		void dfsOnes(Consumer<int[]> consumer, int[][] value, int[] pos) {
			if (value[pos[0]][pos[1]] != 1) return;
			consumer.accept(pos);
			for (int i = 0; i < DIRS.length; i++) {
				int r = pos[0] + DIRS[i][0];
				int c = pos[1] + DIRS[i][1];
				if (r < 0 || c < 0 || r >= value.length || c >= value[r].length) continue; 
				int[] nextPos = new int[] {r, c};
				dfsOnes(consumer, value, nextPos);
			}
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
