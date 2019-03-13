package org.ip.array;

// EPI: 6.4
public class Advance {
	public static void main(String[] s) {
		System.out.println(isWinable(new int[]{3,3,1,0,2,0,1}));
		System.out.println(isWinable(new int[]{3,2,0,0,2,0,1}));
	}
	public static boolean isWinable(int[] board) {
		for (int i = 0, max = 0; i < board.length; i++, max--) {
			max = Math.max(board[i], max);
			if (max == 0) return false;
		}
		return true;
	}
}
