package org.ip.graph;

import java.util.HashMap;
import java.util.Map;

// EPI 2018: 18.6
public class Bipartite2 {
	public static void main(String[] s) {
		testOdd();
		testEven();
	}
	public boolean color(Graph<Integer> g, Map<Integer, State> map) {
		return dfs(g, map, g.map.keySet().iterator().next(), State.WHITE);
	}
	public boolean dfs(Graph<Integer> g, Map<Integer, State> map, Integer v, State state) {
		if (v == null) return true;
		State vstate = map.getOrDefault(v, State.NONE);
		if (vstate != State.NONE) {
			return vstate == state;
		}
		map.put(v, state);
		State next = state == State.WHITE ? State.BLACK : State.WHITE;
		for (Integer e : g.getEdges(v)) {
			boolean canColor = dfs(g, map, e, next);
			if (canColor == false) return canColor;
		}
		return true;
	}
	public enum State {
		NONE, WHITE, BLACK
	}
	public static void testOdd() {
		Map<Integer,State> map = new HashMap<Integer,State>();
		boolean canColor = new Bipartite2().color(cycleOdd(), map);
		if (canColor) {
			System.out.println(map);
		} else {
			System.out.println(canColor);
		}
	}
	public static void testEven() {
		Map<Integer,State> map = new HashMap<Integer,State>();
		boolean canColor = new Bipartite2().color(cycleEven(), map);
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
