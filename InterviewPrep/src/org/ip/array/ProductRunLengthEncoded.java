package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/product-of-two-run-length-encoded-arrays/">LC: 1868</a>
 */
public class ProductRunLengthEncoded {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{6,6}}, new int[][] {{1,3},{2,3}}, new int[][] {{6,3},{3,3}}};
		Object[] tc2 = new Object[] {new int[][] {{2,3},{6,1},{9,2}}, new int[][] {{1,3},{2,1},{3,2}}, new int[][] {{2,3},{3,3}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t, int[][] u) {
			List<List<Integer>> res = new ArrayList<>();
			for (PairIterator i = new PairIterator(t), j = new PairIterator(u); i.hasNext(); ) {
				int min = Math.min(i.size(), j.size());
				int p = i.next(min) * j.next(min);
				if (res.isEmpty() || res.get(res.size() - 1).get(0) != p) {
					res.add(Arrays.asList(new Integer[]{p, min}));
				} else {
					res.get(res.size() - 1).set(1, res.get(res.size() - 1).get(1) + min);
				}
			}
			return res;
		}
		static class PairIterator {
			int i;
			int j;
			private int[][] t;
			public PairIterator(int[][] t) {
				this.t = t;
			}
			boolean hasNext() {
				return i < t.length;
			}
			int size() {
				return t[i][1] - j;
			}
			int next(int next) {
				int v = t[i][0];
				j += next;
				if (j == t[i][1]) {
					i++;
					j = 0;
				}
				return v;
			}
		}
	}
	static class Solver implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t, int[][] u) {
			List<List<Integer>> res = new ArrayList<>();
			for (RunLengthIterator i1 = new RunLengthIterator(t), i2 = new RunLengthIterator(u); i1.hasNext(); ) {
				int p = i1.next() * i2.next();
				if (res.isEmpty() || res.get(res.size() - 1).get(0) != p) {
					res.add(Arrays.asList(new Integer[]{p, 1}));
				} else {
					res.get(res.size() - 1).set(1, res.get(res.size() - 1).get(1) + 1);
				}
			}
			return res;
		}
		static class RunLengthIterator {

			private int[][] v;
			private int i;
			private int j;

			public RunLengthIterator(int[][] v) {
				this.v = v;
				this.i = -1;
				this.j = 0;
			}
			
			public boolean hasNext() {
				return i < v.length - 1 || j < v[i][1] - 1;
			}

			public int next() {
				if (i >= v.length) throw new RuntimeException();
				if (i != -1 && j < v[i][1] - 1) j++;
				else {
					i++;
					j = 0;
				}
				return v[i][0];
			}
			
		}
	}
	interface Problem extends BiFunction<int[][], int[][], List<List<Integer>>> {
		
	}
}
