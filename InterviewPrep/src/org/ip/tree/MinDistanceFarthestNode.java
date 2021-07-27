package org.ip.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/356378/">OA 2019</a>
 * <a href="https://leetcode.com/problems/tree-diameter/">LC: 1245</a>
 */
public class MinDistanceFarthestNode {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MinDistanceFarthestNode::tc1,
				MinDistanceFarthestNode::tc2,
				MinDistanceFarthestNode::tc3,
				MinDistanceFarthestNode::tc4,
				MinDistanceFarthestNode::tc5);
		Solver[] solvers = new Solver[] {new DiameterMinDistance()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(toGraph(new int[][] {{1, 4}, {2, 3}, {3, 4}, {4, 5}, {5, 6}})));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(toGraph(new int[][] {{1, 3}, {4, 5}, {5, 6}, {3, 2}, {3, 4}})));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(toGraph(new int[][] {{1, 2}})));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(toGraph(new int[][] {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9}, {9, 10}})));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(toGraph(new int[][] {{7, 8}, {7, 9}, {4, 5}, {1, 3}, {3, 4}, {6, 7}, {4, 6}, {2, 3}, {9, 10}})));
	}
	public static class DiameterMinDistance implements Solver {

		@Override
		public int solve(Graph g) {
			int diameter = new Diameter().solve(g);
			return (diameter & 1) == 0 ? diameter / 2 : (diameter + 1) / 2;
		}
		
	}
	public static class Diameter implements Solver {
		@Override
		public int solve(Graph g) {
			Integer first = g.map.keySet().iterator().next();
			Map<Integer, Integer> visited = new HashMap<>();
			Integer pmax = solve(g, new HashMap<>(), first, 0);
			Integer max = solve(g, visited, pmax, 0);
			return visited.get(max);
		}
		
		public Integer solve(Graph g, Map<Integer, Integer> visited, Integer current, int distance) {
			if (visited.putIfAbsent(current, distance) != null) {
				return current;
			}
			Integer max = current;
			for (Integer edge : g.map.get(current)) {
				Integer maxEdge = solve(g, visited, edge, distance + 1);
				if (visited.get(maxEdge).compareTo(visited.get(max)) > 0) {
					max = maxEdge;
				}
			}
			return max;
		}
	}
	
	private static Graph toGraph(int[][] edges) {
		Graph g = new Graph();
		for (int i = 0; i < edges.length; i++) {
			g.addEdge(edges[i][0], (edges[i][1]));
			g.addEdge(edges[i][1], (edges[i][0]));
		}
		return g;
	}
	public interface Solver {
		public int solve(Graph g);
	}
	private static class Graph {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		public void addEdge(int a, int b) {
			Set<Integer> set = map.computeIfAbsent(a, ignored -> new HashSet<>());
			set.add(b);
		}
	}
}
