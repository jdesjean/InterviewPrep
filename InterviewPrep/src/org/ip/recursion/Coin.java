package org.ip.recursion;

//EPI: 17.9
public class Coin {
	public static void main(String[] s) {
		CoinPlayer[] players = new CoinPlayer[]{new CoinPlayerRecursive(), new CoinPlayerDP()};
		int[] array = new int[]{8,15,3,7};
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].play(array));
		}
	}
	public interface CoinPlayer {
		public int play(int[] array);
	}
	public static class CoinPlayerDP implements CoinPlayer {

		@Override
		public int play(int[] array) {
			int[][] cache = new int[array.length][array.length];
			int max = 0;
			for (int k = 0; k < array.length; k++) {
				for (int j = k, i = 0; j < array.length; j++, i++) {
					if (j - i < 2) cache[i][j] = Math.max(array[i], array[j]);
					else {
						int l1r1 = cache[i+1][j-1];
						int l2 =  cache[i+2][j];
						int r2 = cache[i][j-2];
						int ml = Math.min(l2, l1r1);
						int mr = Math.min(l1r1, r2);
						cache[i][j] = Math.max(array[i] + ml, array[j] + mr);
					}
					
					max = Math.max(max, cache[i][j]);
				}
			}
			
			return max;
		}
		
	}
	public static class CoinPlayerRecursive implements CoinPlayer {

		@Override
		public int play(int[] array) {
			return solve(array,0,array.length-1);
		}
		private static int solve(int[] array, int left, int right) {
			if (left == right) return array[left];
			else if (left > right) return 0;
			int l1r1 = solve(array, left+1, right-1);
			int l2 = solve(array,left+2,right);
			int r2 = solve(array,left,right-2);
			int ml = Math.min(l2, l1r1);
			int mr = Math.min(l1r1, r2);
			return Math.max(array[left] + ml, array[right] + mr);
		}
		
	}
	
}
