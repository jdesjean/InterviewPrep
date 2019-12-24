package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class AdjacencyList<T> implements IGraph<T> {
	Map<T, Set<T>> map = new HashMap<T, Set<T>>();
	public static AdjacencyList<Integer>  graphLoop() {
		AdjacencyList<Integer> g = new AdjacencyList<Integer>();
		Integer[] v = new Integer[5];
		for (int i = 0; i < 5; i++) {
			v[i] = new Integer(i);
		}
		g.addEdge(v[2], v[0]);
		g.addEdge(v[2], v[1]);
		g.addEdge(v[0], v[3]);
		g.addEdge(v[1], v[4]);
		g.addEdge(v[3], v[4]);
		return g;

	}
	public static AdjacencyList<Integer>  singleNode() {
		AdjacencyList<Integer> g = new AdjacencyList<Integer>();
		Integer[] v = new Integer[5];
		for (int i = 0; i < 5; i++) {
			v[i] = new Integer(i);
		}
		g.addEdge(v[0], null);
		return g;
	}

	public static AdjacencyList<Integer> binaryTree() {
		AdjacencyList<Integer> g = new AdjacencyList<Integer>();
		Integer[] v = new Integer[5];
		for (int i = 0; i < 5; i++) {
			v[i] = new Integer(i);
		}
		g.addEdge(v[2], v[0]);
		g.addEdge(v[2], v[1]);
		g.addEdge(v[0], v[3]);
		g.addEdge(v[1], v[4]);
		return g;

	}
	public static AdjacencyList<Integer> linkedList() {
		AdjacencyList<Integer> g = new AdjacencyList<Integer>();
		Integer[] v = new Integer[5];
		for (int i = 0; i < 5; i++) {
			v[i] = new Integer(i);
		}
		g.addEdge(v[0], v[1]);
		g.addEdge(v[1], v[2]);
		g.addEdge(v[2], v[3]);
		g.addEdge(v[3], v[4]);
		return g;
	}
	public static void main(String[] s) {
		AdjacencyList<Integer>[] graphs = new AdjacencyList[]{singleNode(), binaryTree(), linkedList(), graphLoop()};
		for (int i = 0; i < graphs.length; i++) {
			System.out.println(graphs[i].compare(graphs[i].clone()));
		}
	}

	public static void testHasCycle() {
		AdjacencyList<Integer> graph = new AdjacencyList<Integer>();
		Integer[] vertex = new Integer[] { 0, 1, 2, 3, 4 };
		/*
		 * graph.addEdge(vertex[0],vertex[1]);
		 * graph.addEdge(vertex[0],vertex[2]);
		 * graph.addEdge(vertex[1],vertex[3]);
		 * graph.addEdge(vertex[2],vertex[4]);
		 * graph.addEdge(vertex[4],vertex[0]);
		 */
		graph.addEdge(vertex[0], vertex[1]);
		graph.addEdge(vertex[0], vertex[2]);
		graph.addEdge(vertex[1], vertex[2]);
		System.out.println(graph.hasCycle());
	}

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

	public interface Visitor<T> {
		public void visit(T t, int depth);
	}

	public final T EMPTY = null;

	public Deque<T> topoSort() {
		Deque<T> stack = new LinkedList<T>();
		postOrder(new Visitor<T>() {
			@Override
			public void visit(T t, int depth) {
				stack.push(t);
			}
		});
		return stack;
	}

	public AdjacencyList<T> clone() {
		AdjacencyList<T> clone = new AdjacencyList<T>();
		Set<T> visited = new HashSet<T>();
		for (Iterator<T> iterator = map.keySet().iterator(); iterator.hasNext();) {
			T key = iterator.next();
			if (map.get(key) == null || visited.contains(key))
				continue;
			visited.add(key);
			clone(clone, visited, key);

		}
		return clone;
	}

	public T clone(AdjacencyList<T> clone, Set<T> visited, T original) {
		for (Iterator<T> iterator2 = map.get(original).iterator(); iterator2.hasNext();) {
			T value = iterator2.next();
			if (visited.contains(value)) {
				clone.addEdge(original, value);
			} else {
				visited.add(value);
				clone.addEdge(original, clone(clone, visited, value));
			}
		}
		return original;
	}

	public boolean compare(AdjacencyList<T> graph) {
		Set<T> visited = new HashSet<T>();
		for (Iterator<T> iterator = map.keySet().iterator(); iterator.hasNext();) {
			T key = iterator.next();
			if (visited.contains(key))
				continue;
			visited.add(key);
			if (!graph.map.containsKey(key))
				return false;
			compare(graph, visited, key);
		}

		return true;
	}

	public boolean compare(AdjacencyList<T> graph, Set<T> visited, T original) {
		Set<T> set1 = map.get(original);
		Set<T> set2 = graph.map.get(original);
		if (set1.size() != set2.size())
			return false;

		for (Iterator<T> iterator = set1.iterator(); iterator.hasNext();) {
			T value = iterator.next();
			if (!set2.contains(value))
				return false;
			if (visited.contains(value))
				continue;
			visited.add(value);
			if (!compare(graph, visited, value))
				return false;
		}
		return true;
	}

	public boolean hasCycle() {
		Set<T> visited = new HashSet<T>();
		Deque<T> queue = new LinkedList<T>();
		if (map.isEmpty())
			return false;
		for (Iterator<T> iterator = map.keySet().iterator(); iterator.hasNext();) {
			T vertex = iterator.next();
			if (visited.contains(vertex))
				continue;
			queue.addLast(vertex);
			visited.add(vertex);
			while (!queue.isEmpty()) {
				T current = queue.removeFirst();
				for (Iterator<T> iterator2 = map.get(current).iterator(); iterator2.hasNext();) {
					T neighbor = iterator2.next();
					if (visited.contains(neighbor))
						return true;
					visited.add(neighbor);
					queue.addLast(neighbor);
				}
			}
		}
		return false;
	}

	public void bfs(Visitor<T> visitor) {
		Set<T> visited = new HashSet<T>();
		Deque<T> queue = new LinkedList<T>();
		if (map.isEmpty())
			return;
		for (Iterator<T> iterator = map.keySet().iterator(); iterator.hasNext();) {
			T vertex = iterator.next();
			if (visited.contains(vertex))
				continue;
			queue.addLast(vertex);
			queue.addLast(EMPTY);
			visited.add(vertex);
			bfs(visited, queue, visitor, 0);
		}
	}

	public void bfs(Set<T> visited, Deque<T> queue, Visitor<T> visitor, int depth) {
		while (!queue.isEmpty()) {
			T current = queue.removeFirst();
			if (current == EMPTY) {
				depth++;
				if (!queue.isEmpty())
					queue.addLast(EMPTY);
				continue;
			}
			visitor.visit(current, depth);
			Set<T> neighbors = map.get(current);
			if (neighbors == null)
				return;
			for (Iterator<T> iterator = neighbors.iterator(); iterator.hasNext();) {
				T neighbor = iterator.next();
				if (visited.contains(neighbor))
					continue;
				visited.add(neighbor);
				queue.addLast(neighbor);
			}
		}
	}

	public void postOrder(Visitor<T> visitor) {
		Set<T> visited = new HashSet<T>();
		for (Iterator<T> iterator = map.keySet().iterator(); iterator.hasNext();) {
			T v = iterator.next();
			if (visited.contains(v))
				continue;
			postOrder(visited, v, visitor, 1);
		}
	}

	public void postOrder(Set<T> visited, T v, Visitor<T> visitor, int depth) {
		visited.add(v);
		for (Iterator<T> iterator = map.get(v).iterator(); iterator.hasNext();) {
			T vv = iterator.next();
			if (visited.contains(vv))
				continue;
			postOrder(visited, vv, visitor, depth + 1);
		}
		visitor.visit(v, depth);
	}
}
