package org.ip.graph;

import java.util.Iterator;

import org.ip.graph.AdjacencyList.Visitor;

public class MinimalBuilder {
	public static void main(String[] s) {
		AdjacencyList<Integer> graph = build(6,2);
		System.out.println(maxDistance(graph));
		for (Iterator<Integer> iterator = graph.map.keySet().iterator(); iterator.hasNext();) {
			for (Iterator<Integer> iterator2 = graph.map.get(iterator.next()).iterator(); iterator2.hasNext();) {
				System.out.print(iterator2.next());
				if (iterator2.hasNext()) System.out.print(" ");
			}
			System.out.println("");
		}
	}
	public static class MaxVisitor implements Visitor<Integer> {
		private int max = 0;
		@Override
		public void visit(Integer t, int depth) {
			max = Math.max(max, depth);
		}
		public int getMax() { return max; }
		
	}
	public static int maxDistance(AdjacencyList<Integer> graph) {
		MaxVisitor visitor = new MaxVisitor();
		graph.bfs(visitor);
		return visitor.getMax();
	}
	public static AdjacencyList<Integer> build(int n, int m) {
		Integer[] aVertex = new Integer[n];
		AdjacencyList<Integer> graph = new AdjacencyList<Integer>();
		for (int i = 0; i < n; i++) {
			aVertex[i] = new Integer(i);
		}
		for (int i = 0; i < n; i++) {
			graph.addEdge(aVertex[i], aVertex[(i+1)%n]);
			graph.addEdge(aVertex[i], aVertex[(i-1+n)%n]);
		}
		if (m <= 2) return graph;
		for (int i = 0; i < n; i++) {
			int step = n / (m-1);
			for (int j = i; j < n; j+=step) {
				graph.addEdge(aVertex[j], aVertex[(j+step)%n]);
			}
		}
		return graph;
	}
}
