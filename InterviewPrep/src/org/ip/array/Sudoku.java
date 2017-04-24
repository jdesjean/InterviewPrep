package org.ip.array;

import org.ip.primitives.Bit;

public class Sudoku {
	public static void main(String[] s) {
		int[][] board = new int[][]{
			{5,3,0,0,7,0,0,0,0},
			{6,0,0,1,9,5,0,0,0},
			{0,9,8,0,0,0,0,0,0},
			{8,0,0,0,6,0,0,0,3},
			{4,0,0,8,0,3,0,0,1},
			{7,0,0,0,2,0,0,0,6},
			{0,6,0,0,0,0,2,8,0},
			{0,0,0,4,1,9,0,0,5},
			{0,0,0,0,8,0,0,7,9}};
		System.out.println(isValid(board));
		board = new int[][]{
			{5,3,0,0,7,0,0,0,0},
			{6,0,0,1,9,5,0,0,0},
			{0,9,8,0,0,0,0,0,0},
			{8,0,0,0,6,0,0,0,3},
			{4,0,0,8,0,3,0,0,1},
			{7,0,0,0,2,0,0,0,6},
			{0,6,0,0,0,0,2,8,0},
			{0,0,0,4,1,9,4,0,5},
			{0,0,0,0,8,0,0,7,9}};
		System.out.println(isValid(board));
		board = new int[][]{
			{5,3,0,0,7,0,0,0,0},
			{6,0,0,1,9,5,0,0,0},
			{0,9,8,0,0,0,0,0,0},
			{8,0,0,0,6,0,0,0,3},
			{4,0,0,8,0,3,0,0,1},
			{7,0,0,0,2,0,0,0,6},
			{0,6,0,0,0,0,2,8,3},
			{0,0,0,4,1,9,0,0,5},
			{0,0,0,0,8,0,0,7,9}};
		System.out.println(isValid(board));
		board = new int[][]{
			{5,3,0,0,7,0,0,0,0},
			{6,0,0,1,9,5,0,0,0},
			{0,9,8,0,0,0,0,0,0},
			{8,0,0,0,6,0,0,0,3},
			{4,0,0,8,0,3,0,0,1},
			{7,0,0,0,2,0,0,0,6},
			{0,6,0,0,0,0,2,8,0},
			{0,0,0,4,1,9,7,0,5},
			{0,0,0,0,8,0,0,7,9}};
		System.out.println(isValid(board));
	}
	public static boolean isValid(int[][] board) {
		for (int row = 0; row < board.length; row++) if (!isValidRow(board,row)) return false;
		for (int column = 0; column < board[0].length; column++) if (!isValidColumn(board,column)) return false;
		for (int row = 0; row < board.length; row+=3) {
			for (int column = 0; column < board[0].length; column+=3) {
				if (!isValidGrid(board,row,column)) return false;
			}
		}
		return true;
	}
	public static boolean isValidRow(int[][] board, int row) {
		return isValidRect(board,row,row,0,board[0].length-1);
	}
	public static boolean isValidColumn(int[][] board, int column) {
		return isValidRect(board,0,board.length-1,column,column);
	}
	public static boolean isValidGrid(int[][] board, int row, int column) {
		return isValidRect(board,row,row+2,column,column+2);
	}
	
	public static boolean isValidRect(int[][] board, int rowStart, int rowEnd, int columnStart, int columnEnd) {
		int set = 0;
		for (int i = rowStart; i <= rowEnd; i++) {
			for (int j = columnStart; j <= columnEnd; j++) {
				int square = board[i][j];
				if (square == 0) continue;
				if (Bit.getBit(set, square)) return false;
				set = Bit.setBit(set, square);
			}
		}
		return true;
	}
}
