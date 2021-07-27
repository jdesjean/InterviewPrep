package org.ip.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/odd-even-jump/">LC: 975</a>
 */
public abstract class OddEvenJump {
	public static void main(String[] s) {
		OddEvenJump[] jump = new OddEvenJump[] {new OddEvenJumpNaive(), new OddEvenJumpDP2(), new OddEvenJumpDP3()};
		List<Consumer<OddEvenJump>> consumers = Arrays.asList(OddEvenJump::tc1, OddEvenJump::tc2, OddEvenJump::tc3);
		for (Consumer tc : consumers) {
			for (OddEvenJump j : jump) {
				tc.accept(j);
			}
		}
		
	}
	private static void tc1(OddEvenJump jump) {
		//2
		System.out.println(jump.solve(new int[] {10,13,12,14,15}));
	}
	private static void tc2(OddEvenJump jump) {
		//3
		System.out.println(jump.solve(new int[] {2,3,1,1,4}));
	}
	private static void tc3(OddEvenJump jump) {
		//3
		System.out.println(jump.solve(new int[] {5,1,3,4,2}));
	}
	
	public abstract int solve(int[] array);
	public static class OddEvenJumpDP3 extends OddEvenJump {

		@Override
		public int solve(int[] array) {
			if (array == null || array.length == 0) return 0;
			int[] odds = odds(array);
			int[] evens = even(array);
			State[] cache = new State[array.length];
			cache[array.length - 1] = State.BOTH;
			int count = 1;
			for (int i = array.length - 2; i >= 0; i--) {
				int odd = odds[i];
				int even = evens[i];
				boolean hasOdd = odd != 0 && (cache[odd] == State.BOTH || cache[odd] == State.EVEN);
				boolean hasEven = even != 0 && (cache[even] == State.BOTH || cache[even] == State.ODD);
				if (hasOdd && hasEven) {
					cache[i] = State.BOTH;
					count++;
				} else if (hasOdd) {
					cache[i] = State.ODD;
					count++;
				} else if (hasEven) {
					cache[i] = State.EVEN;
				} else {
					cache[i] = State.NONE;
				}
			}
			return count;
		}
		int[] odds(int[] array) {
			Pair[] tmp = new Pair[array.length];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = new Pair(i, array[i]);
			}
			return make(tmp);
		}
		int[] even(int[] array) {
			Pair[] tmp = new Pair[array.length];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = new Pair(i, -array[i]);
			}
			return make(tmp);
		}
		int[] make(Pair[] tmp) {
			Arrays.sort(tmp);
			int[] ans = new int[tmp.length];
			Deque<Pair> dq = new LinkedList<Pair>();
			for (int i = 0; i < tmp.length; i++) {
				while (!dq.isEmpty() && tmp[i].one > dq.peekFirst().one) {
					ans[dq.pop().one] = tmp[i].one;
				}
				dq.push(tmp[i]);
			}
			return ans;
		}
		private static class Pair implements Comparable<Pair>{
			int one;
			int two;
			public Pair(int one, int two) {
				this.one=one;
				this.two=two;
			}
			@Override
			public int compareTo(Pair o) {
				return this.two-o.two;
			}
		}
	}
	enum State {
		BOTH, ODD, EVEN, NONE
	}
	public static class OddEvenJumpDP2 extends OddEvenJump {

		@Override
		public int solve(int[] array) {
			if (array == null || array.length == 0) return 0;
			State[] cache = new State[array.length];
			cache[array.length - 1] = State.BOTH;
			int count = 1;
			for (int i = array.length - 2; i >= 0; i--) {
				int odd = odd(array, i);
				int even = even(array, i);
				boolean hasOdd = odd != -1 && (cache[odd] == State.BOTH || cache[odd] == State.EVEN);
				boolean hasEven = even != -1 && (cache[even] == State.BOTH || cache[even] == State.ODD);
				if (hasOdd && hasEven) {
					cache[i] = State.BOTH;
					count++;
				} else if (hasOdd) {
					cache[i] = State.ODD;
					count++;
				} else if (hasEven) {
					cache[i] = State.EVEN;
				} else {
					cache[i] = State.NONE;
				}
			}
			return count;
		}
	}
	
	public static class OddEvenJumpDP extends OddEvenJump {

		@Override
		public int solve(int[] array) {
			Deque<Integer> min = new ArrayDeque<Integer>();
			Deque<Integer> max = new ArrayDeque<Integer>();
			fillMin(array, min);
			fillMax(array, max);
			int count = 0;
			for (int j = 0; j < array.length; j++) {
				int index = j;
				Iterator<Integer> itMax = max.iterator();
				Iterator<Integer> itMin = min.iterator();
				for (int i = 1; i < array.length; i++) {
					int prevIndex = index;
					for (Iterator<Integer> it = i % 2 == 1 ? itMin : itMax; it.hasNext();) {
						int t = it.next();
						if (t <= index) continue;
						else if (i % 2 == 1) {
							if (array[index] > array[t]) break;
						} else {
							if (array[index] < array[t]) break;
						}
						index = t;
						break;
					}
					if (prevIndex == index) break;
				}
				if (index == array.length - 1) count++;
			}
			return count;
		}
		void fillMin(int[] array, Deque<Integer> max) {
			for (int i = 0; i < array.length; i++) {
				while (!max.isEmpty() && array[i] < array[max.peekLast()] && array[i] >= array[max.peekFirst()]) {
					max.removeLast();
				}
				if (max.isEmpty() || array[i] >= array[max.peekLast()]) {
					max.addLast(i);
				}
			}
		}
		void fillMax(int[] array, Deque<Integer> min) {
			for (int i = 0; i < array.length; i++) {
				while (!min.isEmpty() && array[i] > array[min.peekLast()] && array[i] <= array[min.peekFirst()]) {
					min.removeLast();
				}
				if (min.isEmpty() || array[i] <= array[min.peekLast()]) {
					min.addLast(i);
				}
			}
		}
		
	}
	public static class OddEvenJumpNaive extends OddEvenJump {

		@Override
		public int solve(int[] array) {
			int count = 0;
			for (int i = 0; i < array.length; i++) {
				if (ok(array, i, 1)) count++;
			}
			return count++;
		}
		private boolean ok(int[] array, int i, int j) {
			if (i >= array.length) {
				return false;
			} else if (i == array.length - 1) {
				return true;
			}
			if (j % 2 == 1) {
				int min = odd(array, i); // next up
				return min != -1 && ok(array, min, j + 1);
			} else {
				int max = even(array, i); // next down
				return max != -1 && ok(array, max, j + 1);
			}
		}
	}
	private static int even(int[] array, int i) {
		int maxv = Integer.MIN_VALUE;
		int max = -1;
		for (int j = i + 1; j < array.length; j++) {
			if (array[j] <= array[i] && array[j] > maxv) {
				maxv = array[j];
				max = j;
			}
		}
		return max;
	}
	private static int odd(int[] array, int i) {
		int minv = Integer.MAX_VALUE;
		int min = -1; 
		for (int j = i + 1; j < array.length; j++) {
			if (array[j] >= array[i] && array[j] < minv) {
				minv = array[j];
				min = j;
			}
		}
		return min;
	}
}
