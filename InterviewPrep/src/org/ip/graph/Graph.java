package org.ip.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
	Map<T, Set<T>> map = new HashMap<T, Set<T>>();
	public void addEdge(T node1, T node2) {
		add(node1, node2);
		add(node2, node1);
	}
	private void add(T node1, T node2) {
		Set<T> set = map.get(node1);
		if (set == null) {
			set = new HashSet<T>();
			map.put(node1, set);
		}
		set.add(node2);
	}
	public Set<T> getEdges(T node) {
		return map.get(node);
	}
}
