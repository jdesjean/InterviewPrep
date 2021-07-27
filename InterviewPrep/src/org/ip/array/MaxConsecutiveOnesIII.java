package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/max-consecutive-ones-iii/">LC: 1004</a>
 */
public class MaxConsecutiveOnesIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6, new int[] {1,1,1,0,0,0,1,1,1,1,0}, 2};
		Object[] tc2 = new Object[] {10, new int[] {0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1}, 3};
		Object[] tc3 = new Object[] {4, new int[] {1,0,0,1}, 2};
		Object[] tc4 = new Object[] {4, new int[] {1,0,1,0}, 2};
		Object[] tc5 = new Object[] {4, new int[] {0,1,0,1}, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new SolverIterative(), new SolverRecursive()};
		Test.apply(solvers, tcs);
	}
	public static class SolverIterative implements Problem {

		@Override
		public Integer apply(int[] t, Integer u) {
			int max = 0;
			int count = 0;
			for (int l = 0, r = 0; r < t.length; ) {
				if (t[r] == 0) {
					if (count < u) {
						r++;
						count++;
					} else {
						if (t[l++] == 0) {
							count--;
						}
					}
				} else if(t[r] == 1) {
					r++;
				}
				max = Math.max(max, r - l);
			}
			return max;
		}
	}
	public static class SolverRecursive implements Problem {

		@Override
		public Integer apply(int[] t, Integer k) {
			AtomicInteger id = new AtomicInteger();
			Map<Integer, Integer> counts = counts(t, id);
			int max = counts.values().stream().mapToInt(Integer::intValue).reduce(0, Integer::max);
			return _apply(t, k, max, counts, id);
		}
		
		int _apply(int[] t, int k, int sum, Map<Integer, Integer> counts, AtomicInteger id) {
			if (k <= 0) {
				return sum;
			}
			int max = sum; // up to k
			for (int i = 0; i < t.length; i++) {
				if (t[i] != 0) continue;
				int idRight = i < t.length - 1 ? t[i + 1] : 0;
				int idLeft = i > 0 ? t[i - 1] : 0;
				int countRight = counts.getOrDefault(idRight, 0);
				int countLeft = counts.getOrDefault(idLeft, 0);
				int nextSum;
				if (idRight != 0 && idLeft != 0) {
					t[i] = idLeft;
					nextSum = countRight + countLeft + 1;
					counts.put(idRight, nextSum);
					counts.put(idLeft, nextSum);
				} else if (idRight != 0) {
					t[i] = idRight;
					nextSum = countRight + 1;
					counts.put(idRight, nextSum);
				} else if (idLeft != 0) {
					t[i] = idLeft;
					nextSum = countLeft + 1;
					counts.put(idLeft, nextSum);
				} else {
					t[i] = id.incrementAndGet();
					counts.put(t[i], 1);
					nextSum = 1;
				}
				max = Math.max(max, _apply(t, k - 1, Math.max(sum, nextSum), counts, id));
				if (idRight == 0 && idLeft == 0) {
					id.decrementAndGet();
					counts.remove(t[i]);
				} else {
					if (idRight != 0) {
						counts.put(idRight, countRight);
					}
					if (idLeft != 0) {
						counts.put(idLeft, countLeft);
					}
				}
				t[i] = 0;
			}
			return max;
		}

		Map<Integer, Integer> counts(int[] u, AtomicInteger id) {
			Map<Integer, Integer> counts = new HashMap<>();
			for (int i = 0; i < u.length; i++) {
				if (u[i] == 0) continue;
				if (i == 0 || u[i - 1] == 0) id.incrementAndGet();
				u[i] = id.get();
				counts.compute(id.get(), (k, v) -> v == null ? 1 : v + 1);
			}
			return counts;
		}
	}
	public interface Problem extends BiFunction<int[], Integer, Integer> {
		
	}
}
