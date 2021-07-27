package org.ip.tree;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/validate-binary-tree-nodes/">LC: 1361</a>
 */
public class ValidateBTNodes {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, new int[] {1,-1,3,-1}, new int[] {2,-1,-1,-1}};
		Object[] tc2 = new Object[] { false, new int[] {1,-1,3,-1}, new int[] {2,3,-1,-1}};	
		Object[] tc3 = new Object[] { false, new int[] {1,0}, new int[] {-1,-1}};
		Object[] tc4 = new Object[] { false, new int[] {1,-1,-1,4,-1,-1}, new int[] {2,-1,-1,5,-1,-1}};
		Object[] tc5 = new Object[] { false, new int[] {1,-1,-1}, new int[] {-1,-1,1}};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[] t, int[] u) {
			UnionFind unionFind = new UnionFind(t.length);
			int[] counts = new int[t.length];
			for (int i = 0; i < t.length; i++) {
				if (t[i] != -1) {
					if (unionFind.find(t[i]) == unionFind.find(i)) return false;
					unionFind.union(t[i], i);
					if (++counts[t[i]] > 1) return false;
				}
				if (u[i] != -1) {
					if (unionFind.find(u[i]) == unionFind.find(i)) return false;
					unionFind.union(u[i], i);
					if (++counts[u[i]] > 1) return false;
				}
			}
			int root = unionFind.find(0);
			for (int i = 1; i < t.length; i++) {
				if (unionFind.find(i) != root) return false;
			}
			return true;
		}
	}
	static class UnionFind {
		int[] parents;
		public UnionFind(int size) {
			parents = new int[size];
			for (int i = 0; i < size; i++) {
				parents[i] = i;
			}
		}
		int find(int x) {
			if (parents[x] != x) parents[x] = find(parents[x]);
			return parents[x];
		}
		void union(int c, int p) {
			int fc = find(c);
			int fp = find(p);
			parents[fc] = fp;
		}
	}
	interface Problem extends BiPredicate<int[], int[]> {
		
	}
}
