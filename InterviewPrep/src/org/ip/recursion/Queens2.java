package org.ip.recursion;

public class Queens2 {
	public static void main(String[] s) {
		solve(new QVisitor() {
			int index = 0;
			@Override
			public void visit(int[] board) {
				System.out.println(index++);
				printChess(board);
			}
		}, 4);
	}
	public static void solve(QVisitor visitor, int size) {
		int[] board = new int[size];
		solve(visitor, board, 0);
	}
	public static void solve(QVisitor visitor, int[] board, int l) {
		if (l == board.length) {
			visitor.visit(board);
			return;
		}
		for (int c = 0; c < board.length; c++) {
			boolean canPlace = true;
			for (int ll = 0; ll < l; ll++) {
				if (isDiagonal(l, c, ll, board[ll]) || isVertical(l, c, ll, board[ll])) {
					canPlace = false;
					break;
				}
			}
			if (!canPlace) continue;
			board[l] = c;
			solve(visitor, board, l + 1);
		}
	}
	private static boolean isVertical(int l, int c, int ll, int cc) {
		return c == cc;
	}
	private static boolean isDiagonal(int l, int c, int ll, int cc) {
		return Math.abs(cc - c) == Math.abs(ll - l);
	}
	public interface QVisitor {
		public void visit(int[] board);
	}
	private static void printChessLine(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print("+");
			System.out.print("-");
		}
		System.out.println("+");
	}
	public static void printChess(int[] array) {
		printChessLine(array);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print("|");
				if (array[i] == j) System.out.print("*");
				else System.out.print(" ");
			}
			System.out.println("|");
			//if (i < array.length - 1) printChessLine(array);
		}
		printChessLine(array);
	}
}
