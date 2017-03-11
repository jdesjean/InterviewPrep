package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
	Map<Vertex<T>,Set<Vertex<T>>> map = new HashMap<Vertex<T>,Set<Vertex<T>>>();
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
	public void addEdge(Vertex<T> node1, Vertex<T> node2) {
		Set<Vertex<T>> set = map.get(node1);
		if (set == null) {
			set = new HashSet<Vertex<T>>();
			map.put(node1, set);
		}
		set.add(node2);
		if (!map.containsKey(node2)) {
			map.put(node2, new HashSet<Vertex<T>>());
		}
	}
	public interface Visitor<T> {
		public void visit(T t, int depth);
	}
	public final Vertex<T> EMPTY = new Vertex<T>();
	public Deque<Vertex<T>> topoSort() {
		Deque<Vertex<T>> stack = new LinkedList<Vertex<T>>();
		postOrder(new Visitor<Vertex<T>>(){
			@Override
			public void visit(Vertex<T> t, int depth) {
				stack.addFirst(t);
			}
		});
		return stack;
	}
	public boolean hasCycle() {
		Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
		Deque<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		if (map.isEmpty()) return false;
		for (Iterator<Vertex<T>> iterator =  map.keySet().iterator(); iterator.hasNext();) {
			Vertex<T> vertex = iterator.next();
			if (visited.contains(vertex)) continue;
			queue.addLast(vertex);
			visited.add(vertex);
			while(!queue.isEmpty()) {
				Vertex<T> current = queue.removeFirst();
				for (Iterator<Vertex<T>> iterator2 = map.get(current).iterator(); iterator2.hasNext();) {
					Vertex<T> neighbor = iterator2.next();
					if (visited.contains(neighbor)) return true;
					visited.add(neighbor);
					queue.addLast(neighbor);
				}
			}
		}
		return false;
	}
	public void bfs(Visitor<Vertex<T>> visitor) {
		Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
		Deque<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		if (map.isEmpty()) return ;
		for (Iterator<Vertex<T>> iterator =  map.keySet().iterator(); iterator.hasNext();) {
			Vertex<T> vertex = iterator.next();
			if (visited.contains(vertex)) continue;
			queue.addLast(vertex);
			queue.addLast(EMPTY);
			visited.add(vertex);
			bfs(visited,queue,visitor,0);
		}
	}
	public void bfs(Set<Vertex<T>> visited, Deque<Vertex<T>> queue, Visitor<Vertex<T>> visitor, int depth) {
		while(!queue.isEmpty()) {
			Vertex<T> current = queue.removeFirst();
			if (current == EMPTY) {
				depth++;
				if (!queue.isEmpty()) queue.addLast(EMPTY);
				continue;
			}
			visitor.visit(current, depth);
			Set<Vertex<T>> neighbors = map.get(current);
			if (neighbors == null) return;
			for (Iterator<Vertex<T>> iterator = neighbors.iterator(); iterator.hasNext();) {
				Vertex<T> neighbor = iterator.next();
				if (visited.contains(neighbor)) continue;
				visited.add(neighbor);
				queue.addLast(neighbor);
			}
		}
	}
	public void postOrder(Visitor<Vertex<T>> visitor) {
		Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
		for (Iterator<Vertex<T>> iterator = map.keySet().iterator(); iterator.hasNext();) {
			Vertex<T> v = iterator.next();
			if (visited.contains(v)) continue;
			postOrder(visited,v,visitor,1);
		}
	}
	public void postOrder(Set<Vertex<T>> visited, Vertex<T> v, Visitor<Vertex<T>> visitor, int depth) {
		visited.add(v);
		for (Iterator<Vertex<T>> iterator = map.get(v).iterator(); iterator.hasNext();) {
			Vertex<T> vv = iterator.next();
			if (visited.contains(vv)) continue;
			postOrder(visited,vv,visitor,depth+1);
		}
		visitor.visit(v,depth);
	}
}
