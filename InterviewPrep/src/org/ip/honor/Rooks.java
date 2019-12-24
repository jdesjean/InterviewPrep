package org.ip.honor;

import org.ip.array.Utils;

// EPI 2018: 24.7
public class Rooks {
	public static void main(String[] s) {
		int[][] b = new int[][] {
			{0,1,1,1},
			{0,1,1,1},
			{1,1,0,0},
			{1,1,1,1}
		};
		new Rooks().solve(b);
		Utils.println(b);
	}
	public void solve(int[][] board) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == 0) {
					board[0][x] = 0;
					board[y][0] = 0;
				}
			}
		}
		boolean y0 = false;
		for (int y = 1; y < board.length; y++) {
			if (board[y][0] != 0) continue;
			setRow(board, y, 0);
			y0 = true;
		}
		boolean x0 = false;
		for (int x = 1; x < board[0].length; x++) {
			if (board[0][x] != 0) continue;
			setColumn(board, x, 0);
			x0 = true;
		}
		if (y0) {
			setRow(board, 0, 0);
		}
		if (x0) {
			setColumn(board, 0, 0);
		}
	}
	public void setColumn(int[][] b, int x, int v) {
		for (int y = 1; y < b.length; y++) {
			b[y][x] = v;
		}
	}
	public void setRow(int[][] b, int y, int v) {
		for (int x = 1; x < b[y].length; x++) {
			b[y][x] = v;
		}
	}
}
