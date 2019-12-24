package org.ip.dp;

// EPI 2018: 16.9
public class Coins {
	public static void main(String[] s) {
		int[] coins = new int[] {25,5,10,5,10,5,10,25,1,25,1,25,1,25,5,10};
		System.out.println(new Coins().solve(coins));
		coins = new int[] {5,1,10,5};
		System.out.println(new Coins().solve(coins));
		coins = new int[] {10,25,5,1,10,5};
		System.out.println(new Coins().solve(coins));
	}
	public int solve(int[] coins) {
		int[][] cache = new int[coins.length-1][coins.length];
		for (int i = coins.length - 1; i >= 0; i--) {
			for (int j = i + 1; j < coins.length; j++) {
				int left = i;
				int right = j;
				int ll = right - left > 2 ? cache[left + 2][right] : 0;
				int lr = right - left > 1 ? cache[left + 1][right - 1] : 0;
				int rr = right - left > 2 ? cache[left][right - 2] : 0;
				cache[left][right] = Math.max(coins[left] + Math.min(ll, lr), coins[right] + Math.min(lr, rr));
			}
		}
		return Math.max(cache[0][0], cache[0][coins.length-1]);
	}
	public int _solve(int[] coins) {
		return _solve(coins, 0, coins.length-1);
	}
	public int _solve(int[] coins, int left, int right) {
		if (left > right) return 0; 
		int l2 = _solve(coins, left+2, right);
		int lr = _solve(coins, left+1, right-1);
		int rr = _solve(coins, left, right-2);
		return Math.max(coins[left] + Math.min(l2, lr), coins[right] + Math.min(lr, rr));
	}
}
