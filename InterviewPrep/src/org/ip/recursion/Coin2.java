package org.ip.recursion;

// EPI: 17.9
public class Coin2 {
	public static void main(String[] s) {
		CoinPlayer[] players = new CoinPlayer[]{new CoinPlayerRecursive(), new CoinPlayerDP()};
		int[] array = new int[]{8,15,3,7};
		//int[] array = new int[]{15,7,8,3};
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].play(array));
		}
	}
	public interface CoinPlayer {
		public int play(int[] array);
	}
	public static class CoinPlayerRecursive implements CoinPlayer {

		@Override
		public int play(int[] array) {
			return max(array, 0, array.length-1);
		}
		public int max(int[] array, int l, int r) {
			if (l > r) return 0;
			int ml2 = max(array, l + 2, r);
			int mlr = max(array, l + 1, r - 1);
			int mlr2 = max(array, l, r - 2);
			return Math.max(array[l] + Math.min(ml2,  mlr), array[r] + Math.min(mlr, mlr2));
		}
	}
	public static class CoinPlayerDP implements CoinPlayer {

		@Override
		public int play(int[] array) {
			int[][] cache = new int[array.length][array.length];
			int max = 0;
			for (int i = 0; i < cache.length; i++) {
				for (int r = i, l = 0; r < cache.length; l++, r++) {
					if (r - l < 2) cache[l][r] = Math.max(array[r], array[l]);
					else {
						int ml2 = cache[l+2][r];
						int mlr = cache[l+1][r-1];
						int mlr2 = cache[l][r-2];
						cache[l][r] = Math.max(array[l] + Math.min(ml2,  mlr), array[r] + Math.min(mlr, mlr2));
					}
					max = Math.max(max, cache[l][r]);
				}
			}
			return max;
		}
		
	}
}
