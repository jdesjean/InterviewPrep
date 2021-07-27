package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/optimal-account-balancing/">LC: 465</a>
 */
public class OptimalAccountBalancing {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[][] {{0,1,10},{2,0,5}}};
		Object[] tc2 = new Object[] {1, new int[][] {{0,1,10},{1,0,1},{1,2,5},{2,0,5}}};
		Object[] tc3 = new Object[] {2, new int[][] {{0,1,2},{1,2,1},{1,3,1}}};
		Object[] tc4 = new Object[] {5, new int[][] {{0,1,1},{0,2,2},{0,3,3},{0,4,3},{0,5,3}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	/**
	 * compute balance per person O(N) where N is transactions
	 * balance series on minus need to match series of pluses
	 * (transaction is minimum when abs(balance[i]) = balance[j])
	 */
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			int[] balances = balances(value);
			Arrays.sort(balances);
			int positiveIndex = 0;
			for (; positiveIndex < balances.length && balances[positiveIndex] <= 0; positiveIndex++) {}
			return dfs(balances, 0, positiveIndex);
		}
		int dfs(int[] balances, int negativeIndex, int positiveIndex) {
			if (negativeIndex == positiveIndex) {
				return 0;
			}
			if (balances[negativeIndex] == 0) {
				return dfs(balances, negativeIndex + 1, positiveIndex);
			}
			for (int i = positiveIndex; i < balances.length; i++) { //greedy match
				if (-balances[negativeIndex] == balances[i]) {
					int transfer = balances[i];
					transfer(balances, negativeIndex, i, -transfer);
					int min = dfs(balances, negativeIndex + 1, positiveIndex) + 1;
					transfer(balances, negativeIndex, i, transfer);
					return min;
				}
			}
			int min = Integer.MAX_VALUE;
			for (int i = positiveIndex; i < balances.length; i++) {
				if (balances[i] == 0) continue;
				int transfer = Math.min(Math.abs(balances[negativeIndex]), balances[i]);
				transfer(balances, negativeIndex, i, -transfer);
				min = Math.min(min, dfs(balances, negativeIndex, positiveIndex) + 1);
				transfer(balances, negativeIndex, i, transfer);
			}
			return min;
		}
		void transfer(int[] balances, int i, int j, int transfer) {
			balances[i] -= transfer;
			balances[j] += transfer;
		}
		int[] balances(int[][] value) {
			int accounts = 0;
			for (int i = 0; i < value.length; i++) {
				accounts = Math.max(accounts, value[i][0]);
				accounts = Math.max(accounts, value[i][1]);
			}
			int[] balances = new int[accounts + 1];
			for (int i = 0; i < value.length; i++) {
				transfer(balances, value[i][0], value[i][1], value[i][2]);
			}
			return balances;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
