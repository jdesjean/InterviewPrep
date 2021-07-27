package org.ip.array;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/open-the-lock/">LC: 752</a>
 */
public class OpenLock {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 6, new String[] {"0201","0101","0102","1212","2002"}, "0202" };
		Object[] tc2 = new Object[] { 1, new String[] {"8888"}, "0009" };
		Object[] tc3 = new Object[] { -1, new String[] {"8887","8889","8878","8898","8788","8988","7888","9888"}, "8888" };
		Object[] tc4 = new Object[] { -1, new String[] {"0000"}, "8888" };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static int[] SENTINEL = new int[0];
		@Override
		public int applyAsInt(String[] t, String u) {
			int[] start = new int[] {0,0,0,0};
			Deque<int[]> queue = new LinkedList<>();
			Set<String> visited = new HashSet<>();
			Set<String> deadends = new HashSet<>(Arrays.asList(t));
			queue.add(start);
			queue.add(SENTINEL);
			int length = 0;
			while (!queue.isEmpty()) {
				int[] current = queue.remove();
				if (current == SENTINEL) {
					length++;
					if (!queue.isEmpty()) {
						queue.add(SENTINEL);
					}
					continue;
				}
				String sCurrent = toString(current);
				if (deadends.contains(sCurrent)) continue;
				if (!visited.add(sCurrent)) { //visited
					continue; 
				}
				if (sCurrent.equals(u)) {
					return length;
				}
				for (int i = 0; i < 4; i++) {
					int v = current[i];
                    for (int d = -1; d <= 1; d += 2) {
                    	current[i] = v + d;
                    	if (current[i] < 0) {
                    		current[i] = 9;
                    	} else if (current[i] > 9) {
                    		current[i] = 0;
                    	}
                    	queue.add(Arrays.copyOf(current, 4));
                    }
                    current[i] = v;
				}
			}
			return -1;
		}
		String toString(int[] a) {
			char[] buf = new char[a.length];
			for (int i = 0; i < a.length; i++) {
				buf[i] = String.valueOf(a[i]).charAt(0);
			}
			return new String(buf);
		}
	}
	interface Problem extends ToIntBiFunction<String[], String> {
		
	}
}
