package org.ip.graph;

import java.util.HashMap;
import java.util.Map;

import org.ip.graph.Deadlock.Graph;

// EPI: 19.5
public class Clone {
	public static void main(String[] s) {
		Graph<Integer> g = Deadlock.cycle();
		println(clone(g, 0));
	}
	public static void println(Graph<Integer> g) {
		for (Integer e : g.map.keySet()) {
			System.out.println(e + ":" + g.map.get(e));
		}
	}
	public static Graph<Integer> clone(Graph<Integer> g, Integer v) {
		Map<Integer, Boolean> state = new HashMap<Integer, Boolean>();
		Graph<Integer> gg = new Graph<Integer>();
		dfs(g, gg, v, state);
		return gg;
	}
	public static boolean dfs(Graph<Integer> g, Graph<Integer> gg, Integer vertex, Map<Integer, Boolean> visited) {
		Boolean state = visited.get(vertex);
		if (state == VISITING) {
			return true;
		}
		visited.put(vertex, VISITING);
		for (Integer e : g.getEdges(vertex)) {
			gg.addEdge(vertex, e);
			if (visited.get(e) == VISITED) continue;
			if (dfs(g, gg, e, visited)) return true;
		}
		visited.put(vertex, VISITED);
		return false;
	}
	public static final Boolean VISITED = Boolean.TRUE;
	public static final Boolean VISITING = Boolean.FALSE;
}
