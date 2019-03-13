package org.ip.array;

import java.util.BitSet;


// EPI: 6.16
public class Sudoku {
	public static void main(String[] s) {
		Sudoku sudoku = new Sudoku(new int[][] {
			{5,3,4,6,7,8,9,1,2},
			{6,7,2,1,9,5,3,4,8},
			{1,9,8,3,4,2,5,6,7},
			{8,5,9,7,6,1,4,2,3},
			{4,2,6,8,5,3,7,9,1},
			{7,1,3,9,2,4,8,5,6},
			{9,6,1,5,3,7,2,8,4},
			{2,8,7,4,1,9,6,3,5},
			{3,4,5,2,8,6,1,7,9}});
		System.out.println(sudoku.check());
	}
	private int[][] board;
	private BitSet set = new BitSet(8);
	public Sudoku(int[][] board) {
		this.board=board;
	}
	public boolean check() {
		for (int l = 0; l < 9; l++) {
			if (!checkRegion(l, 0, l, 8)) return false;
		}
		for (int c = 0; c < 9; c++) {
			if (!checkRegion(0, c, 8, c)) return false;
		}
		for (int i = 0; i < 9; i++) {
			int l = i / 3 * 3; //0, 3, 6
			int c = i % 3 * 3; // 0, 3, 6
			if (!checkRegion(l, c, l+2, c+2)) return false;
		}
		return true;
	}
	public boolean checkRegion(int l, int c, int ll, int cc) {
		set.clear();
		for (int i = l; i <= ll; i++) {
			for (int j = c; j <= cc; j++) {
				if (board[i][j] < 0) continue;
				if (set.get(board[i][j])) return false;
				set.set(board[i][j]);
			}
		}
		return true;
	}
}
