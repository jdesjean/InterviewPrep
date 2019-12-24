package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

// EPI: 19.8
public class Team {
	public static void main(String[] s) {
		Graph<Integer> g = new Graph<Integer>();
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(3, 0);
		System.out.println(solve(g));
	}
	public static int solve(Graph<Integer> g) {
		Deque<Integer> deque = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<Integer>();
		for (Integer e : g.map.keySet()) {
			dfs(g, visited, deque, e);
		}
		int max = 0;
		Map<Integer,Integer> distances = new HashMap<Integer,Integer>();
		while (!deque.isEmpty()) {
			Integer current = deque.removeFirst();
			int dc = distances.getOrDefault(current, 1);
			max = Math.max(max, dc);
			for (Integer e : g.getEdges(current)) {
				int de = distances.getOrDefault(e, 1);
				distances.put(e, Math.max(de, dc + 1));
			}
		}
		return max;
	}
	public static void dfs(Graph<Integer> g, Set<Integer> visited, Deque<Integer> deque, Integer v) {
		visited.add(v);
		for (Integer e : g.getEdges(v)) {
			if (visited.contains(e)) continue;
			dfs(g, visited, deque, e);
		}
		deque.addFirst(v);
	}
	public static class IntWrapper {
		int value;
		public IntWrapper(int value) {this.value=value;}
	}
	public interface Visitor {
		public void visit(Integer[] buffer, int length);
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
	}
}
