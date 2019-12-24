package org.ip.recursion;

import org.ip.array.Utils;

public class Knight {
	public final static int[][] moves = new int[][]{{1,2}, {2,1}, {1,-2}, {2,-1}, {-1,2}, {-2,1}, {-1,-2}, {-2,-1}};
	public final static int[][] board = new int[][]{
		{1,2,3},
		{4,5,6},
		{7,8,9},
		{-1,0,-1}};
	public static void main(String[] s) {
		System.out.println(new KnightRecursive().solve(0, 0, 10));
		System.out.println(new KnightRecursive2().solve(0, 0, 10));
		System.out.println(new KnightDP().solve(0, 0, 10));
		System.out.println(new KnightDP2().solve(0, 0, 10));
	}
	public interface KnightI {
		public int solve(int r, int l, int count);
	}
	public static class KnightRecursive implements KnightI {
		public int solve(int r, int l, int count) {
			if (count <= 0) return 0;
			if (r < 0 || r >= board.length || l < 0 || l >= board[0].length) return 0;
			if (board[r][l] == -1) return 0;
			if (count == 1) return 1;
			int sum = 0;
			for (int i = 0; i < moves.length; i++) {
				sum += solve(r + moves[i][0], l + moves[i][1], count - 1);
			}
			return sum;
		}
	}
	public static class KnightRecursive2 implements KnightI {
		int[][] map = new int[][] {
			{4,6}, 
			{6,8}, 
			{7,9}, 
			{4,8}, 
			{0,3,9}, 
			{}, 
			{0,1,7}, 
			{2,6}, 
			{1,3}, 
			{2,4}};
		int[][] map2 = new int[][] {
			{3,1}, 
			{0,0}, 
			{0,1}, 
			{0,2},
			{1,0}, 
			{1,1}, 
			{1,2}, 
			{2,0}, 
			{2,1}, 
			{2,2}};
		@Override
		public int solve(int r, int l, int count) {
			if (count == 0) return 0;
			if (count == 1) return 1;
			int n = board[r][l];
			int sum = 0;
			for (int j = 0; j < map[n].length; j++) {
				int nn = map[n][j];
				int rr = map2[nn][0];
				int ll = map2[nn][1];
				sum += solve(rr, ll, count - 1);
			}
			return sum;
		}
	}
	public static class KnightDP implements KnightI {
		@Override
		public int solve(int r, int l, int count) {
			int[][] cache = new int[board.length][board[0].length];
			int[][] prev = new int[board.length][board[0].length];
			for (int rr = 0; rr < board.length; rr++) {
				for (int ll = 0; ll < board[0].length; ll++) {
					cache[rr][ll] = board[rr][ll] != -1 ? 1 : 0;  
				}
			}
			for (int k = 2; k <= count; k++) {
				Utils.arraycopy(cache, prev);
				for (int rr = 0; rr < board.length; rr++) {
					for (int ll = 0; ll < board[0].length; ll++) {
						if (board[rr][ll] == -1) continue;
						int sum = 0;
						for (int m = 0; m < moves.length; m++) {
							int rrr = rr + moves[m][0];
							int lll = ll + moves[m][1];
							if (rrr < 0 || rrr >= board.length || lll < 0 || lll >= board[0].length) continue;
							sum += prev[rrr][lll]; 
						}
						cache[rr][ll] = sum;
					}
				}
			}
			return cache[r][l];
		}
	}
	public static class KnightDP2 implements KnightI {
		@Override
		public int solve(int r, int l, int count) {
			int n = board[r][l];
			int[][] map = new int[][] {{4,6}, {6,8}, {7,9}, {4,8}, {0,3,9}, {}, {0,1,7}, {2,6}, {1,3}, {2,4}};
			int[][] cache = new int[2][10];
			int rr = 0;
			for (int i = 0; i <= 9; i++) {
				cache[rr][i] = 1;
			}
			for (int k = 2; k <= count; k++) {
				int rprev = rr;
				rr = 1 - rr;
				for (int i = 0; i <= 9; i++) {
					int sum = 0;
					for (int j = 0; j < map[i].length; j++) {
						sum += cache[rprev][map[i][j]];
					}
					cache[rr][i] = sum;
				}
			}
			return cache[rr][n];
		}
	}
}
