package org.ip.matrix;

import java.util.Random;

// EPI 2018: 12.12 - Zobrist_hashing
public class Chess {
	public static void main(String[] s) {
		int[] board = new int[64];
		System.out.println(new Chess().hash(board));
	}
	static int[][] table;
	static {
		table = init();
	}
	public int hash(int[] board) {
		int h = 0;
		for (int i = 0; i < 64; i++) {
			if (board[i] != 0) { //empty
				h ^= table[i][board[i]];
			}
		}
		return h;
	}
	private static int[][] init() {
		Random random = new Random();
		int[][] table = new int[64][13];
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 12; j++) { // 6 pieces x 2 colors
				table[i][j] = random.nextInt(); 
			}
		}
		return table;
	}
}
