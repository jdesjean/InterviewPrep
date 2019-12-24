package org.ip.recursion;

import java.util.BitSet;
import java.util.Random;

import org.ip.array.Utils;

public class Sudoku {
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
		solve(new PrintVisitor(), board);
	}
	public static void solve(Visitor visitor, int[][] board) {
		int[] buffer = new int[9];
		solve(visitor, board, 0, 0);
	}
	public static void solve(Visitor visitor, int[][] board, int l, int c) {
		if (l == board.length || c == board[0].length) {
			visitor.visit(board);
			return;
		}
		if (board[l][c] != 0) {
			solveNext(visitor, board, l, c);
		} else {
			for (int i = 1; i <= 9; i++) {
				board[l][c] = i;
				if (isValid(board, l, c)) {
					solveNext(visitor, board, l, c);
				}
			}
			board[l][c] = 0;
		}
	}
	public static void solveNext(Visitor visitor, int[][] board, int i, int j) {
		if (j < board[0].length - 1) {
			solve(visitor, board, i, j + 1);
		} else {
			solve(visitor, board, i + 1, 0);
		}
	}
	public static boolean isValid(int[][] board,  int l, int c) {
		//0,1,2 -> 0
		//3,4,5 -> 3
		//6,7,8 -> 6
		int ll = l / 3 * 3;
		int cc = c / 3 * 3;
		return isValid(board, l, l, 0, 8) && 
				isValid(board, 0, 8, c, c) && 
				isValid(board, ll, ll+2, cc, cc+2);
	}
	public static boolean isValid(int[][] board, int l, int ll, int c, int cc) {
		BitSet set = new BitSet();
		for (int lll = l; lll <= ll; lll++) {
			for (int ccc = c; ccc <= cc; ccc++) {
				if (board[lll][ccc] == 0) continue;
				if (set.get(board[lll][ccc])) return false;
				set.set(board[lll][ccc]);
			}
		}
		return true;
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(int[][] board) {
			Utils.println(board);
			System.out.println("********");
		}
	}
	public interface Visitor {
		public void visit(int[][] board);
	}
}
