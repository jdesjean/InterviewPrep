package org.ip.matrix;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/making-a-large-island/">LC: 827</a>
 */
public class MakeLargeIsland {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new int[][] {{1,0},{0,1}}};
		Object[] tc2 = new Object[] {4, new int[][] {{1,1},{0,1}}};
		Object[] tc3 = new Object[] {4, new int[][] {{1,1},{1,1}}};
		Object[] tc4 = new Object[] {1, new int[][] {{0,0},{0,0}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		private final static int[][] DIRS = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
		@Override
		public Integer apply(int[][] t) {
			Map<Integer, Integer> sizes = sizes(t);
			return largest(t, sizes);
		}
		int largest(int[][] a, Map<Integer, Integer> sizes) {
			int max = sizes.isEmpty() ? a.length > 0 && a[0].length > 0 ? 1 : 0 : sizes.values().iterator().next();
			for (int l = 0; l < a.length; l++) {
				for (int c = 0; c < a[l].length; c++) {
					if (a[l][c] != 0) continue;
					max = Math.max(max, sum(a, l, c, sizes));
				}
			}
			return max;
		}
		int sum(int[][] a, int l, int c, Map<Integer, Integer> count) {
			int sum = 1;
			BitSet ids = new BitSet();
			for (int i = 0; i < DIRS.length; i++) {
				int ll = l + DIRS[i][0];
				int cc = c + DIRS[i][1];
				if (ll < 0 || cc < 0 || ll >= a.length || cc >= a[ll].length || a[ll][cc] < 2) continue;
				int id = a[ll][cc];
				if (!ids.get(id)) {
					ids.set(id);
					sum += count.get(id);
				}
			}
			return sum;
		}
		Map<Integer, Integer> sizes(int[][] a) {
			int id = 2;
			Map<Integer, Integer> size = new HashMap<>();
			for (int l = 0; l < a.length; l++) {
				for (int c = 0; c < a[l].length; c++) {
					if (a[l][c] != 1) continue;
					size.put(id, dfs(a, l, c, id++));
				}
			}
			return size;
		}
		int dfs(int[][] a, int l, int c, int id) {
			if (a[l][c] != 1) return 0;
			a[l][c] = id;
			int sum = 1;
			for (int i = 0; i < DIRS.length; i++) {
				int ll = l + DIRS[i][0];
				int cc = c + DIRS[i][1];
				if (ll < 0 || cc < 0 || ll >= a.length || cc >= a[l].length) continue;
				sum += dfs(a, ll, cc, id);
			}
			return sum;
		}
	}
	public static class Solver implements Problem {
		
		
		@Override
		public Integer apply(int[][] t) {
			Map<Integer, Integer> counts = counts(t);
			return max(t, counts);
		}
		
		Consumer<int[]> countConsumer(int[][] t, Map<Integer, Integer> counts, int l, int c, BitSet ids, AtomicInteger sum) {
			return direction -> {
				int ll = l + direction[0];
				int cc = c + direction[1];
				int id = t[ll][cc];
				if (!ids.get(id)) {
					ids.set(id);
					sum.accumulateAndGet(counts.getOrDefault(id, 0), Integer::sum);
				}
			};
		}
		
		int max(int[][] t, Map<Integer, Integer> counts) {
			int max = counts.values().stream().reduce(0, (x, y) -> Math.max(x, y));
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) continue;
					BitSet ids = new BitSet();
					int sum = 1;
					for (Iterator<int[]> it = new DirectionIterator(t, l, c); it.hasNext(); ) {
						int[] direction = it.next();
						int ll = l + direction[0];
						int cc = c + direction[1];
						int id = t[ll][cc];
						if (!ids.get(id)) {
							ids.set(id);
							sum += counts.getOrDefault(id, 0);
						}
					}
					max = Math.max(max, sum);
				}
			}
			return max;
		}
		
		Map<Integer, Integer> counts(int[][] t) {
			int id = 2;
			Map<Integer,Integer> counts = new HashMap<>();
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					int count = _counts(t, l, c, id);
					if (count > 0) {
						counts.put(id++, count);
					}
				}
			}
			return counts;
		}
		int _counts(int[][] t, int l, int c, int id) {
			if (t[l][c] != 1) return 0;
			t[l][c] = id;
			int sum = 1;
			for (Iterator<int[]> it = new DirectionIterator(t, l, c); it.hasNext(); ) {
				int[] direction = it.next();
				sum += _counts(t, l + direction[0], c + direction[1], id);
			}
			return sum;
		}
	}
	public static class SolverSpliterator implements Problem {
		
		
		@Override
		public Integer apply(int[][] t) {
			Map<Integer, Integer> counts = counts(t);
			return max(t, counts);
		}
		
		Consumer<int[]> countConsumer(int[][] t, Map<Integer, Integer> counts, int l, int c, BitSet ids, AtomicInteger sum) {
			return direction -> {
				int ll = l + direction[0];
				int cc = c + direction[1];
				int id = t[ll][cc];
				if (!ids.get(id)) {
					ids.set(id);
					sum.accumulateAndGet(counts.getOrDefault(id, 0), Integer::sum);
				}
			};
		}
		
		int max(int[][] t, Map<Integer, Integer> counts) {
			int max = counts.values().stream().reduce(0, (x, y) -> Math.max(x, y));
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) continue;
					BitSet ids = new BitSet();
					AtomicInteger sum = new AtomicInteger(1);
					Consumer<int[]> consumer = countConsumer(t, counts, l, c, ids, sum);
					for (Spliterator<int[]> it = new DirectionSpliterator(t, l, c); true; ) {
						if (!it.tryAdvance(consumer)) {
							break;
						}
					}
					max = Math.max(max, sum.get());
				}
			}
			return max;
		}
		
		Map<Integer, Integer> counts(int[][] t) {
			int id = 2;
			Map<Integer,Integer> counts = new HashMap<>();
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					int count = _counts(t, l, c, id);
					if (count > 0) {
						counts.put(id++, count);
					}
				}
			}
			return counts;
		}
		int _counts(int[][] t, int l, int c, int id) {
			if (t[l][c] != 1) return 0;
			t[l][c] = id;
			
			AtomicInteger sum = new AtomicInteger(1);
			Consumer<int[]> consumer = direction -> sum.accumulateAndGet(_counts(t, l + direction[0], c + direction[1], id), Integer::sum); 
			for (Spliterator<int[]> it = new DirectionSpliterator(t, l, c); true; ) {
				if (!it.tryAdvance(consumer)) {
					break;
				}
			}
			return sum.get();
		}
	}
	public static class DirectionSpliterator implements Spliterator<int[]> {
		private final static int[][] directions = new int[][] {{0, -1}, {0, 1},{-1, 0},{1,0}};
		int index = -1;
		private int l;
		private int c;
		private int[][] m;
		public DirectionSpliterator(int[][] m, int l, int c) {
			this.l = l;
			this.c = c;
			this.m = m;
		}

		@Override
		public boolean tryAdvance(Consumer<? super int[]> action) {
			index++;
			for (; index < directions.length; index++) {
				int ll = l + directions[index][0];
				if (ll < 0 || ll >= m.length) continue;
				int cc = c + directions[index][1];
				if (cc < 0 || cc >= m[ll].length) continue;
				action.accept(directions[index]);
				return true;
			}
			return false;
		}

		@Override
		public Spliterator<int[]> trySplit() {
			return null;
		}

		@Override
		public long estimateSize() {
			return directions.length;
		}

		@Override
		public int characteristics() {
			return ORDERED & DISTINCT & NONNULL & IMMUTABLE;
		}
		
	}
	public static class DirectionIterator implements Iterator<int[]> {
		private final static int[][] directions = new int[][] {{0, -1}, {0, 1},{-1, 0},{1,0}};
		int index = -1;
		private int l;
		private int c;
		private int[][] m;
		public DirectionIterator(int[][] m, int l, int c) {
			this.l = l;
			this.c = c;
			this.m = m;
			_next();
		}
		
		void _next() {
			index++;
			for (; index < directions.length; index++) {
				int ll = l + directions[index][0];
				if (ll < 0 || ll >= m.length) continue;
				int cc = c + directions[index][1];
				if (cc < 0 || cc >= m[ll].length) continue;
				break;
			}
		}
		
		@Override
		public boolean hasNext() {
			return index < directions.length;
		}

		@Override
		public int[] next() {
			int[] res = directions[index];
			_next();
			return res;
		}
		
	}
	public interface Problem extends Function<int[][], Integer> {
		
	}
}
