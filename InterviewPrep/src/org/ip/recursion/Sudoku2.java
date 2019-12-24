package org.ip.recursion;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Consumer;

import org.ip.array.Utils;
import org.ip.recursion.Sudoku.PrintVisitor;

// EPI 2018: 15.9
public class Sudoku2 {
	public static void main(String[] s) {
		int[][] board = new int[][] {
			{5,3,4,6,7,8,9,1,2},
			{6,7,2,1,9,5,3,4,8},
			{1,9,8,3,4,2,5,6,7},
			{8,5,9,7,6,1,4,2,3},
			{4,2,6,8,5,3,7,9,1},
			{7,1,3,9,2,4,8,5,6},
			{9,6,1,5,3,7,2,8,4},
			{2,8,7,4,1,9,6,3,5},
			{3,4,5,2,8,6,1,7,9}};
		Random random = new Random(0);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (random.nextInt(4) == 0) {
					board[i][j] = 0;
				}
			}
		}
		Utils.println(board);
		System.out.println("*******************");
		new Sudoku2().solve((x) -> Utils.println(board), board);
	}
	public void solve(Consumer<int[][]> consumer, int[][] board) {
		_solveNext(consumer, board, new BitSet(), 0, 0);
	}
	private void _solve(Consumer<int[][]> consumer, int[][] board, BitSet set, int l, int c) {
		if (l == -1 || c == -1) {
			consumer.accept(board);
			return;
		}
		getSet(board, set, l, c);
		if (set.isEmpty()) return;
		for (int i = 1; i <= 9; i++) {
			if (set.get(i)) continue;
			board[l][c] = i;
			_solveNext(consumer, board, set, l, c);
			board[l][c] = 0;
		}
	}
	private void _solveNext(Consumer<int[][]> consumer, int[][] board, BitSet set, int l, int c) {
		int nl = -1;
		int nc = -1;
		for (int ll = l; ll < board.length; ll++) {
			for (int cc = ll == l ? c : 0; cc < board.length; cc++) {
				if (board[ll][cc] != 0) continue;
				nl = ll;
				nc = cc;
				break;
			}
			if (nl != -1) break;
		}
		_solve(consumer, board, set, nl, nc);
	}
	public void getSet(int[][] board, BitSet set, int l, int c) {
		set.clear();
		getSet(board, set, l, 0, l, board.length - 1); // line
		getSet(board, set, 0, c, board.length - 1, c); // column
		int tl = l / 3 * 3;
		int tc = c / 3 * 3;
		getSet(board, set, tl, tc, tl + 2, tc + 2); // square
	}
	public void getSet(int[][] board, BitSet set, int fl, int fc, int el, int ec) {
		for (int l = fl; l <= el; l++) {
			for (int c = fc; c <= ec; c++) {
				if (board[l][c] == 0) continue;
				set.set(board[l][c]);
			}
		}
	}
}
