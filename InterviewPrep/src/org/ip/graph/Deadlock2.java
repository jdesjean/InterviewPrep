package org.ip.graph;

import java.util.HashMap;
import java.util.Map;

import org.ip.graph.Deadlock.Graph;

// EPI 2018: 18.4
public class Deadlock2 {
	public static void main(String[] s) {
		Deadlock2 deadlock = new Deadlock2();
		System.out.println(deadlock.hasCycle(cycleFree()));
		System.out.println(deadlock.hasCycle(cycle()));
	}
	public boolean hasCycle(Graph<Integer> g) {
		Map<Integer, State> states = new HashMap<Integer, State>();
		for (Integer v : g.map.keySet()) {
			boolean hasCycle = visit(g, v, states);
			if (hasCycle) return true;
		}
		return false;
	}
	public boolean visit(Graph<Integer> g, Integer v, Map<Integer, State> states) {
		State state = states.get(v);
		if (state == State.VISITED) {
			return false;
		} else if (state == State.VISITING) {
			return true;
		}
		states.put(v, State.VISITING);
		for (Integer e: g.getEdges(v)) {
			boolean hasCycle = visit(g, e, states);
			if (hasCycle) return true;
		}
		states.put(v, State.VISITED);
		return false;
	}
	public static enum State {
		VISITING, VISITED, NONE;
	}
	public static Graph<Integer> cycle() {
		Integer[] vertex = new Integer[] { 0, 1, 2, 3, 4 };
		Graph<Integer> graph = new Graph<Integer>();
		graph.addEdge(vertex[0],vertex[1]);
		graph.addEdge(vertex[0],vertex[2]);
		graph.addEdge(vertex[1],vertex[3]);
		graph.addEdge(vertex[2],vertex[4]);
		graph.addEdge(vertex[4],vertex[0]);
		return graph;
	}
	public static Graph<Integer> cycleFree() {
		Integer[] vertex = new Integer[] { 0, 1, 2, 3, 4 };
		Graph<Integer> graph = new Graph<Integer>();
		graph.addEdge(vertex[0], vertex[1]);
		graph.addEdge(vertex[0], vertex[2]);
		graph.addEdge(vertex[1], vertex[2]);
		return graph;
	}
}
