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
	public static void main(String[] s) {
		Graph graph = new Graph();
		Vertex[] vertex = new Vertex[]{new Vertex(0),new Vertex(1), new Vertex(2), new Vertex(3), new Vertex(4)};
		/*graph.addEdge(vertex[0],vertex[1]);
		graph.addEdge(vertex[0],vertex[2]);
		graph.addEdge(vertex[1],vertex[3]);
		graph.addEdge(vertex[2],vertex[4]);
		graph.addEdge(vertex[4],vertex[0]);*/
		graph.addEdge(vertex[0],vertex[1]);
		graph.addEdge(vertex[0],vertex[2]);
		graph.addEdge(vertex[1],vertex[2]);
		System.out.println(graph.hasCycle());
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
	public Deque<Vertex> topoSort() {
		Deque<Vertex> stack = new LinkedList<Vertex>();
		postOrder(new Visitor<Vertex>(){
			@Override
			public void visit(Vertex t, int depth) {
				stack.addFirst(t);
			}
		});
		return stack;
	}
	public boolean hasCycle() {
		Set<Vertex> visited = new HashSet<Vertex>();
		Deque<Vertex> queue = new LinkedList<Vertex>();
		if (map.isEmpty()) return false;
		for (Iterator<Vertex> iterator =  map.keySet().iterator(); iterator.hasNext();) {
			Vertex vertex = iterator.next();
			if (visited.contains(vertex)) continue;
			queue.addLast(vertex);
			visited.add(vertex);
			while(!queue.isEmpty()) {
				Vertex current = queue.removeFirst();
				for (Iterator<Vertex> iterator2 = map.get(current).iterator(); iterator2.hasNext();) {
					Vertex neighbor = iterator2.next();
					if (visited.contains(neighbor)) return true;
					visited.add(neighbor);
					queue.addLast(neighbor);
				}
			}
		}
		return false;
	}
	public void bfs(Visitor<Vertex> visitor) {
		Set<Vertex> visited = new HashSet<Vertex>();
		Deque<Vertex> queue = new LinkedList<Vertex>();
		if (map.isEmpty()) return ;
		for (Iterator<Vertex> iterator =  map.keySet().iterator(); iterator.hasNext();) {
			Vertex vertex = iterator.next();
			if (visited.contains(vertex)) continue;
			queue.addLast(vertex);
			queue.addLast(EMPTY);
			visited.add(vertex);
			bfs(visited,queue,visitor,0);
		}
	}
	public void bfs(Set<Vertex> visited, Deque<Vertex> queue, Visitor<Vertex> visitor, int depth) {
		while(!queue.isEmpty()) {
			Vertex current = queue.removeFirst();
			if (current == EMPTY) {
				depth++;
				if (!queue.isEmpty()) queue.addLast(EMPTY);
				continue;
			}
			visitor.visit(current, depth);
			Set<Vertex> neighbors = map.get(current);
			if (neighbors == null) return;
			for (Iterator<Vertex> iterator = neighbors.iterator(); iterator.hasNext();) {
				Vertex neighbor = iterator.next();
				if (visited.contains(neighbor)) continue;
				visited.add(neighbor);
				queue.addLast(neighbor);
			}
		}
	}
	public void postOrder(Visitor<Vertex> visitor) {
		Set<Vertex> visited = new HashSet<Vertex>();
		for (Iterator<Vertex> iterator = map.keySet().iterator(); iterator.hasNext();) {
			Vertex v = iterator.next();
			if (visited.contains(v)) continue;
			postOrder(visited,v,visitor,1);
		}
	}
	public void postOrder(Set<Vertex> visited, Vertex v, Visitor<Vertex> visitor, int depth) {
		visited.add(v);
		for (Iterator<Vertex> iterator = map.get(v).iterator(); iterator.hasNext();) {
			Vertex vv = iterator.next();
			if (visited.contains(vv)) continue;
			postOrder(visited,vv,visitor,depth+1);
		}
		visitor.visit(v,depth);
	}
}
