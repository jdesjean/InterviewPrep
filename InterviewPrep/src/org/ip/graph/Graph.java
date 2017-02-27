package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Graph {
	Map<Vertex,Set<Vertex>> map = new HashMap<Vertex,Set<Vertex>>();
	public Vertex[] aVertex;
	public Graph(Vertex[] aVertex) {
		this.aVertex=aVertex;
	}
	public void addEdge(Vertex node1, Vertex node2) {
		Set<Vertex> set = map.get(node1);
		if (set == null) {
			set = new HashSet<Vertex>();
			map.put(node1, set);
		}
		set.add(node2);
		if (!map.containsKey(node2)) {
			map.put(node2, new HashSet<Vertex>());
		}
	}
	public interface Visitor<T> {
		public void visit(T t, int depth);
	}
	public final static Vertex EMPTY = new Vertex(0);
	public void dfs(Visitor<Vertex> visitor) {
		boolean[] visited = new boolean[map.keySet().size()];
		Deque<Vertex> queue = new LinkedList<Vertex>();
		queue.addLast(aVertex[0]);
		queue.addLast(EMPTY);
		visited[aVertex[0].val] = true;
		int depth = 0;
		while(!queue.isEmpty()) {
			Vertex current = queue.removeFirst();
			if (current == EMPTY) {
				depth++;
				if (!queue.isEmpty()) queue.addLast(EMPTY);
				continue;
			}
			visitor.visit(current, depth);
			for (Iterator<Vertex> iterator = map.get(current).iterator(); iterator.hasNext();) {
				Vertex neighbor = iterator.next();
				if (visited[neighbor.val]) continue;
				visited[neighbor.val] = true;
				queue.addLast(neighbor);
			}
		}
	}
	public void postOrder(Visitor<Vertex> visitor) {
		boolean[] visited = new boolean[map.keySet().size()];
		for (Iterator<Vertex> iterator = map.keySet().iterator(); iterator.hasNext();) {
			Vertex v = iterator.next();
			if (visited[v.val]) continue;
			postOrder(visited,v,visitor,1);
		}
	}
	public void postOrder(boolean[] visited, Vertex v, Visitor<Vertex> visitor, int depth) {
		visited[v.val] = true;
		for (Iterator<Vertex> iterator = map.get(v).iterator(); iterator.hasNext();) {
			Vertex vv = iterator.next();
			if (visited[vv.val]) continue;
			postOrder(visited,vv,visitor,depth+1);
		}
		visitor.visit(v,depth);
	}
}
