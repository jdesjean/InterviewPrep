package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/jump-game-vi/">LC: 1696</a>
 */
public class JumpGameIV {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {7, new int[] {1,-1,-2,4,-7,3}, 2};
		Object[] tc2 = new Object[] {17, new int[] {10,-5,-2,4,0,3}, 3};
		Object[] tc3 = new Object[] {0, new int[] {1,-5,-20,4,-1,3,-6,-3}, 2};
		Object[] tc4 = new Object[] {198, new int[] {100,-1,-100,-1,100}, 2};
		Object[] tc5 = new Object[] {1, new int[] {1,-5,-3,-7,3}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			if (t == null || t.length == 0) return 0;
			MaxQueue minQueue = new MaxQueue(u);
			
			for (int i = 0; i < t.length; i++) {
				if (minQueue.isEmpty()) {
					minQueue.add(t[i]);
				} else {
					minQueue.add(minQueue.max() + t[i]);
				}
			}
			return minQueue.last();
		}
		static class MaxQueue {
			Deque<Pair> deque = new LinkedList<>();
			private int maxSize;
			private int size;
			public MaxQueue(int maxSize) {
				this.maxSize = maxSize;
			}
			public void add(Integer v) {
				if (size == maxSize) {
					if (--deque.peekFirst().count < 1) {
						deque.removeFirst();
					}
				} else {
					size++;
				}
				int count = 0;
				while (!deque.isEmpty() && deque.peekLast().value < v) {
					count += deque.removeLast().count;
				}
				deque.add(new Pair(v, count + 1));
			}
			public boolean isEmpty() {
				return deque.isEmpty();
			}
			public int max() {
				return deque.peekFirst().value;
			}
			public int last() {
				return deque.peekLast().value;
			}
		}
		static class Pair {
			int value;
			int count;
			public Pair(int value, int count) {
				super();
				this.value = value;
				this.count = count;
			}
			@Override
			public String toString() {
				return "Pair [value=" + value + ", count=" + count + "]";
			}
			
		}
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			if (t == null || t.length == 0) return 0;
			int k = u;
			int[] buffer = new int[k];
			for (int i = 0; i < t.length; i++) {
				int max = Integer.MIN_VALUE;
				for (int j = 0; j < Math.min(i, k); j++) {
					max = Math.max(max, buffer[j]);
				}
				if (max == Integer.MIN_VALUE) max = 0;
				buffer[i % k] = max + t[i];
			}
			return buffer[(t.length - 1) % k];
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
