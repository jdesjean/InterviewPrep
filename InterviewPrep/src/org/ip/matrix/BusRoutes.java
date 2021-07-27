package org.ip.matrix;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/bus-routes/">LC: 815</a>
 */
public class BusRoutes {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[][] {{1,2,7},{3,6,7}}, 1, 6};
		Object[] tc2 = new Object[] {-1, new int[][] {{7,12},{4,5,15},{6},{15,19},{9,12,13}}, 15, 12};
		Object[] tc3 = new Object[] {0, new int[][] {{1,7},{3,5}}, 5, 5};
		Object[] tc4 = new Object[] {-1, new int[][] {{1,7},{3,5}}, 5, 6};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static Integer SENTINEL = Integer.MAX_VALUE;
		@Override
		public Integer apply(int[][] a, Integer source, Integer target) {
			if (source == target) return 0;
			Map<Integer, Set<Integer>> stopToRoute = stopToRoute(a);
			Set<Integer> sourceRoute = stopToRoute.get(source);
			Set<Integer> targetRoute = stopToRoute.get(target);
			if (sourceRoute == null
		               || targetRoute == null) return -1;
			Set<Integer> visited = new HashSet<>();
			Deque<Integer> deque = new LinkedList<>();
			deque.addAll(sourceRoute);
			deque.add(SENTINEL);
			int count = 1;
			while(!deque.isEmpty()) {
				Integer currentRoute = deque.remove();
				if (currentRoute == SENTINEL) {
					if (deque.isEmpty()) {
						return -1;
					}
					count++;
					deque.add(SENTINEL);
					continue;
				}
				if (!visited.add(currentRoute)) {
					continue;
				}
				if (targetRoute.contains(currentRoute)) {
					break;
				}
				for (int i = 0; i < a[currentRoute].length; i++) {
					Set<Integer> nextRoute = stopToRoute.get(a[currentRoute][i]);
					if (nextRoute == null) continue;
					deque.addAll(nextRoute);
				}
			}
			return count;
		}
		Map<Integer, Set<Integer>> stopToRoute(int[][] a) {
			Map<Integer, Set<Integer>> stopToRoute = new HashMap<>();
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[i].length; j++) {
					Set<Integer> set = stopToRoute.computeIfAbsent(a[i][j], (k) -> new HashSet<>());
					set.add(i);
				}
			}
			return stopToRoute;
		}
	}
	interface Problem extends TriFunction<int[][], Integer, Integer, Integer> {
		
	}
}
