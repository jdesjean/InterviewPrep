package org.ip.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// EPI: 19.6
public class Bipartite {
	public static void main(String[] s) {
		testOdd();
		testEven();
	}
	public static boolean color(Graph<Integer> g, Map<Integer, Pair> color) {
		for (Integer v : g.map.keySet()) {
			if (color.containsKey(v)) continue;
			if (!dfs(g, v, null, color, 0)) return false; 
		}
		return true;
	}
	public static boolean dfs(Graph<Integer> g, Integer vertex, Integer parent, Map<Integer, Pair> visited, int depth) {
		Pair state = visited.get(vertex);
		if (state == null) {
			state = new Pair(Boolean.FALSE, depth % 2 == 0);
			visited.put(vertex, state);
		} else if (state.visit == VISITING) {
			return depth % 2 == 0;
		}
		state.visit = VISITING;
		for (Integer e : g.getEdges(vertex)) {
			Pair statee = visited.get(e); 
			if (statee != null && statee.visit == VISITED || e == parent) continue;
			if (!dfs(g, e, vertex, visited, depth + 1)) return false;
		}
		state.visit = VISITED;
		return true;
	}
	public static final Boolean BLACK = Boolean.FALSE;
	public static final Boolean WHITE = Boolean.TRUE;
	public static final Boolean VISITED = Boolean.TRUE;
	public static final Boolean VISITING = Boolean.FALSE;
	public static class Pair {
		Boolean visit;
		Boolean color;
		public Pair(Boolean visit, Boolean color) {this.visit=visit;this.color=color;}
		@Override
		public String toString() {
			return visit + ":" + color;
		}
		
	}
	public static void testOdd() {
		Map<Integer,Pair> map = new HashMap<Integer,Pair>();
		boolean canColor = color(cycleOdd(), map);
		if (canColor) {
			System.out.println(map);
		} else {
			System.out.println(canColor);
		}
	}
	public static void testEven() {
		Map<Integer,Pair> map = new HashMap<Integer,Pair>();
		boolean canColor = color(cycleEven(), map);
		if (canColor) {
			System.out.println(map);
		} else {
			System.out.println(canColor);
		}
	}
	public static Graph<Integer> cycleOdd() {
		Integer[] vertex = new Integer[] { 0, 1, 2 };
		Graph<Integer> graph = new Graph<Integer>();
		graph.addEdge(vertex[0],vertex[1]);
		graph.addEdge(vertex[0],vertex[2]);
		graph.addEdge(vertex[1],vertex[2]);
		return graph;
	}
	public static Graph<Integer> cycleEven() {
		Integer[] vertex = new Integer[] { 0, 1, 2, 3};
		Graph<Integer> graph = new Graph<Integer>();
		graph.addEdge(vertex[0],vertex[1]);
		graph.addEdge(vertex[0],vertex[2]);
		graph.addEdge(vertex[2],vertex[3]);
		graph.addEdge(vertex[1],vertex[3]);
		return graph;
	}
	
}
