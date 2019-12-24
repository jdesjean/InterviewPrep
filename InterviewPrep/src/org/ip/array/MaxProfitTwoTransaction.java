package org.ip.array;

//EPI 2018: 5.7
public class MaxProfitTwoTransaction {
	public static void main(String[] s) {
		IMaxProfitTwoTransaction[] algos = new IMaxProfitTwoTransaction[] {new MaxProfitTwoTransaction1(), new MaxProfitTwoTransaction2()};
		for (IMaxProfitTwoTransaction algo : algos) {
			int[] array = new int[] {310,315,275,295,260,270,290,230,255,250};
			System.out.println(algo.solve(array));
			array = new int[] {1,2,3,4};
			System.out.println(algo.solve(array));
		}
	}
	public static class MaxProfitTwoTransaction2 implements IMaxProfitTwoTransaction {

		@Override
		public int solve(int[] array) {
			int min = Integer.MAX_VALUE;
			int maxProfit = 0;
			int maxTotalProfit = 0;
			int minProfitPrev = Integer.MAX_VALUE;
			for (int i = 0; i < array.length; i++) {
				maxTotalProfit = Math.max(maxTotalProfit, array[i] - minProfitPrev);
				minProfitPrev = Math.min(array[i] - maxProfit, minProfitPrev);
				maxProfit = Math.max(maxProfit, array[i] - min);
				min = Math.min(min, array[i]);
			}
			return maxTotalProfit;
		}
	}
	public static class MaxProfitTwoTransaction1 implements IMaxProfitTwoTransaction {
		public int solve(int[] array) {
			int[] cache = fillCache(array);
			// max i<j<N Aj - Ai
			int max = 0;
			int maxProfit = 0;
			int maxTotalProfit = 0;
			for (int i = array.length - 1; i >= 0; i--) {
				maxProfit = Math.max(maxProfit, max - array[i]);
				max = Math.max(max, array[i]);
				int previousCache = i > 0 ? cache[i - 1] : 0;
				maxTotalProfit = Math.max(maxTotalProfit, maxProfit + previousCache);
			}
			return maxTotalProfit;
		}
		// Ai - min 1<j<i Aj
		public int[] fillCache(int[] array) {
			int[] cache = new int[array.length];
			int min = Integer.MAX_VALUE;
			int max = 0;
			for (int i = 0; i < array.length; i++) {
				cache[i] = max = Math.max(array[i] - min, max);
				min = Math.min(min, array[i]); 
			}
			return cache;
		}
	}
	public interface IMaxProfitTwoTransaction {
		public int solve(int[] array);
	}
}
