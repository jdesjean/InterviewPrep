package org.ip.array;

// EPI 2018: 5.6
public class MaxProfitOneTransaction {
	public static void main(String[] s) {
		int[] array = new int[] {310,315,275,295,260,270,290,230,255,250};
		System.out.println(new MaxProfitOneTransaction().solve(array));
	}
	public int solve(int[] array) {
		int maxProfit = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			maxProfit = Math.max(array[i] - min, maxProfit);
			min = Math.min(min, array[i]);
		}
		
		return maxProfit;
	}
}
