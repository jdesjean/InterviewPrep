package org.ip.graph;

import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/is-graph-bipartite/">LC: 785</a>
 * EPI 2018: 18.6
 * EPI: 19.6
 */
public class Bipartite {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { false, new int[][] { {1,2,3},{0,2},{0,1,3},{0,2} } };
		Object[] tc2 = new Object[] { true, new int[][] { {1,3},{0,2},{1,3},{0,2} } };
		Object[] tc3 = new Object[] { false, new int[][] { {}, {2,4,6},{1,4,8,9},{7,8},{1,2,8,9},{6,9},{1,5,7,8,9},{3,6,9},{2,3,4,6,9},{2,4,5,6,7,8} } };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		enum Color {
			BLACK, WHITE;
			Color flip() {
				if (this == BLACK) return WHITE;
				else return BLACK;
			}
		}
		
		@Override
		public boolean test(int[][] graph) {
			//TODO: check invariants
			Color[] colors = new Color[graph.length];
			for (int v = 0; v < graph.length; v++) {
				if (colors[v] != null) continue;
				if (!dfs(colors, graph, v, Color.BLACK)) {
					return false;
				}
			}
			return true;
		}
		boolean dfs(Color[] colors, int[][] graph, int v, Color color) {
			if (colors[v] != null) {
				return colors[v] == color;
			}
			colors[v] = color;
			for (int i = 0; i < graph[v].length; i++) {
				int e = graph[v][i];
				if (!dfs(colors, graph, e, color.flip())) {
					return false;
				}
			}
			return true;
		}
		
	}
	interface Problem extends Predicate<int[][]> {
		
	}
}
