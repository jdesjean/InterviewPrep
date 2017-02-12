package org.ip;

public class Keypad {
	public static int[][] moves = new int[][]{{4,6},{6,8},{7,9},{4,8},{0,3,9},{},{0,1,7},{2,6},{1,3},{2,4}};
	public static void main(String[] s) {
		Counter[] movers = new Counter[]{new Knight1Counter(), new Knight2Counter()};
		for (Counter mover : movers) {
			System.out.println(mover.count(10, 1));
		}
	}
	public interface Counter {
		public int count(int phoneNumberLength, int startDigit);
	}
	public static class Knight1Counter implements Counter{

		@Override
		public int count(int phoneNumberLength, int startDigit) {
			int[][] cache = new int[phoneNumberLength+1][10];
			for (int j = 0; j <= 9; j++) {
				cache[1][j] = 1;
			}
			for (int i = 2; i <= phoneNumberLength; i++) {
				for (int j = 0; j <= 9; j++) {
					for (int k = 0; k < moves[j].length; k++) {
						cache[i][j] += cache[i-1][moves[j][k]];
					}
				}
			}
			return cache[phoneNumberLength][startDigit];
		}
		
	}
	
	public static class Knight2Counter implements Counter{

		@Override
		public int count(int phoneNumberLength, int startDigit) {
			int[][] cache = new int[2][10];
			for (int j = 0; j <= 9; j++) {
				cache[1][j] = 1;
			}
			for (int i = 2; i <= phoneNumberLength; i++) {
				for (int j = 0; j <= 9; j++) {
					cache[i % 2][j] = 0;
					for (int k = 0; k < moves[j].length; k++) {
						cache[i % 2][j] += cache[(i-1) % 2][moves[j][k]];
					}
				}
			}
			return cache[phoneNumberLength % 2][startDigit];
		}
		
	}
}
