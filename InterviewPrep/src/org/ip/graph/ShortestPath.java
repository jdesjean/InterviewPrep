package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

// EPI: 19.9
public class ShortestPath {
	public static void main(String[] s) {
		Graph<Character,Integer> g = new Graph<Character,Integer>();
		g.addEdge('a', 'b', 3);
		g.addEdge('b', 'a', 4);
		g.addEdge('b', 'k', 1);
		g.addEdge('k', 'i', 1);
		g.addEdge('i', 'j', 6);
		g.addEdge('j', 'l', 7);
		g.addEdge('l', 'i', 9);
		g.addEdge('j', 'f', 1);
		g.addEdge('f', 'g', 6);
		g.addEdge('g', 'f', 7);
		g.addEdge('g', 'h', 4);
		g.addEdge('a', 'c', 2);
		g.addEdge('c', 'e', 8);
		g.addEdge('e', 'd', 7);
		g.addEdge('d', 'c', 5);
		g.addEdge('d', 'h', 5);
		System.out.println(solve(g, 'a', 'h').toString());
	}
	public static Deque<Character> solve(Graph<Character,Integer> g, Character start, Character end) {
		PriorityQueue<Triple<Pair<Character,Character>,Pair<Integer, Integer>>> queue = new PriorityQueue<Triple<Pair<Character,Character>,Pair<Integer, Integer>>>();
		Map<Character,Character> results = new HashMap<Character,Character>();
		Set<Character> visited = new HashSet<Character>();
		solve(g, visited, queue, results, start, end);
		Character current = end;
		Deque<Character> results2 = new LinkedList<Character>();
		while (current != null) {
			results2.add(current);
			current = results.get(current);
		}
		return results2;
	}
	public static void solve(Graph<Character,Integer> g, Set<Character> visited, PriorityQueue<Triple<Pair<Character,Character>,Pair<Integer, Integer>>> queue, Map<Character,Character> results, Character start, Character end) {
		queue.add(new Triple<Pair<Character,Character>,Pair<Integer,Integer>>(new Pair<Character, Character>(start, null), new Pair<Integer,Integer>(0,0)));
		while (!queue.isEmpty()) {
			Triple<Pair<Character,Character>,Pair<Integer, Integer>> current = queue.remove();
			visited.add(current.v.v);
			results.put(current.v.v, current.v.e);
			if (current.v.v.equals(end)) {
				return;
			}
			for (Pair<Character,Integer> e: g.getEdges(current.v.v)) {
				if (visited.contains(e.v)) continue;
				queue.add(new Triple<Pair<Character,Character>,Pair<Integer,Integer>>(new Pair<Character, Character>(e.v, current.v.v), new Pair<Integer,Integer>(current.e.v + e.e, current.e.e + 1)));
			}
		}
	}
	public static class Triple<V,E  extends Comparable<E>> implements Comparable<Triple<V,E>>{
		V v;
		E e;
		public Triple(V v, E e) {this.v=v; this.e=e;}
		@Override
		public int compareTo(Triple<V,E> e) {
			return this.e.compareTo(e.e);
		}
		@Override
		public boolean equals(Object e) {
			return this.e.equals(e);
		}
		@Override
		public String toString() {
			return "v: {" + Objects.toString(v) + "}, e: {" + Objects.toString(e) + "}";
		}
		
	}
	public static class Pair<V,E> implements Comparable<Pair<V,E>>{
		V v;
		E e;
		public Pair(V v, E e) {this.v=v;this.e=e;}
		@Override
		public boolean equals(Object o) {
			return ((Pair<V,E>)o).v.equals(v) && ((Pair<V,E>)o).e.equals(e); 
		}
		@Override
		public int hashCode() {
			return Objects.hash(v, e);
		}
		@Override
		public String toString() {
			return "v: " + Objects.toString(v) + ", e: " + Objects.toString(e); 
		}
		@Override
		public int compareTo(Pair<V, E> o) {
			int cv = ((Comparable<V>)this.v).compareTo(o.v);
			if (cv != 0) return cv;
			return ((Comparable<E>)this.e).compareTo(o.e);
		}
	}
	public static class Graph<V, E> {
		Map<V, Set<Pair<V,E>>> map = new HashMap<V, Set<Pair<V,E>>>();
		public void addEdge(V node1, V node2, E edge) {
			Set<Pair<V,E>> set = map.get(node1);
			if (set == null) {
				set = new HashSet<Pair<V,E>>();
				map.put(node1, set);
			}
			set.add(new Pair<V,E>(node2, edge));
			if (!map.containsKey(node2)) {
				map.put(node2, new HashSet<Pair<V,E>>());
			}
		}
		public Set<Pair<V,E>> getEdges(V node) {
			return map.get(node);
		}
	}
}
