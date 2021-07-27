package org.ip;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * <a href="https://leetcode.com/problems/can-i-win/">LC: 464</a>
 */
public class CanIWin {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {false, 10, 11};
		Object[] tc2 = new Object[] {true, 10, 0};
		Object[] tc3 = new Object[] {true, 10, 1};
		Object[] tc4 = new Object[] {false, 5, 11};
		Object[] tc5 = new Object[] {false, 5, 50};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(Integer t, Integer u) {
			int maxChoosableInteger = t;
			int desiredTotal = u;
			if (desiredTotal < maxChoosableInteger) return true;
			int sum = 0;
			for (int i = 1; i <= maxChoosableInteger; i++) {
				sum += i;
			}
			if (sum < desiredTotal) return false;
			BitSet taken = new BitSet();
			Map<String, Boolean> memo = new HashMap<>();
			return _apply(maxChoosableInteger, desiredTotal, taken, memo);
		}
		boolean _apply(int maxChoosableInteger, int desiredTotal, BitSet taken, Map<String, Boolean> memo) {
			if (desiredTotal <= 0) {
				return false;
			}
			String key = taken.toString() + ":" + desiredTotal;
			Boolean resMemo = memo.get(key);
			if (resMemo != null) {
				//return resMemo;
			}
			for (int value = maxChoosableInteger; value > 0; value--) {
				if (taken.get(value)) continue;
				int total = desiredTotal - value;
				taken.set(value);
				boolean res = _apply(maxChoosableInteger, total, taken, memo);
				taken.clear(value);
				if (!res) {
					memo.put(key, true);
					return true;
				}
			}
			memo.put(key, false);
			return false;
		}
		
	}
	interface Problem extends BiPredicate<Integer, Integer> {
		
	}
}
