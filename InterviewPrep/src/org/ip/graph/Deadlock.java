package org.ip.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ip.graph.Deadlock.Graph;

// EPI: 19.4
public class Deadlock {
	public static void main(String[] s) {
		System.out.println(hasCycle(cycleFree()));
		System.out.println(hasCycle(cycle()));
	}
	public static boolean hasCycle(Graph<Integer> g) {
		Map<Integer, Boolean> state = new HashMap<Integer, Boolean>();
		for (Integer v : g.map.keySet()) {
			if (state.containsKey(v)) continue;
			if (dfs(g, v, state)) return true; 
		}
		return false;
	}
	public static final Boolean VISITED = Boolean.TRUE;
	public static final Boolean VISITING = Boolean.FALSE;
	public static boolean dfs(Graph<Integer> g, Integer vertex, Map<Integer, Boolean> visited) {
		Boolean state = visited.get(vertex);
		if (state == VISITING) {
			return true;
		}
		visited.put(vertex, VISITING);
		for (Integer e : g.getEdges(vertex)) {
			if (visited.get(e) == VISITED) continue;
			if (dfs(g, e, visited)) return true;
		}
		visited.put(vertex, VISITED);
		return false;
	}
	public static Graph<Integer> cycleFree() {
		Integer[] vertex = new Integer[] { 0, 1, 2, 3, 4 };
		Graph<Integer> graph = new Graph<Integer>();
		graph.addEdge(vertex[0], vertex[1]);
		graph.addEdge(vertex[0], vertex[2]);
		graph.addEdge(vertex[1], vertex[2]);
		return graph;
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
	public static class Graph<T> {
		Map<T, Set<T>> map = new HashMap<T, Set<T>>();
		public void addEdge(T node1, T node2) {
			Set<T> set = map.get(node1);
			if (set == null) {
				set = new HashSet<T>();
				map.put(node1, set);
			}
			set.add(node2);
			if (!map.containsKey(node2)) {
				map.put(node2, new HashSet<T>());
			}
		}
		public Set<T> getEdges(T node) {
			return map.get(node);
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (T e : map.keySet()) {
				sb.append(e + ":" + map.get(e) + "\n");
			}
			return sb.toString();
		}
	}
	
}
