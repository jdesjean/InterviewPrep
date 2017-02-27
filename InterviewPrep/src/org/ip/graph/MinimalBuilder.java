package org.ip.graph;

import java.util.Iterator;

import org.ip.graph.Graph.Visitor;

public class MinimalBuilder {
	public static void main(String[] s) {
		Graph graph = build(6,2);
		System.out.println(maxDistance(graph));
		for (Vertex vertex : graph.aVertex) {
			for (Iterator<Vertex> iterator = graph.map.get(vertex).iterator(); iterator.hasNext();) {
				System.out.print(iterator.next());
				if (iterator.hasNext()) System.out.print(" ");
			}
			System.out.println("");
		}
	}
	public static class MaxVisitor implements Visitor<Vertex> {
		private int max = 0;
		@Override
		public void visit(Vertex t, int depth) {
			max = Math.max(max, depth);
		}
		public int getMax() { return max; }
		
	}
	public static int maxDistance(Graph graph) {
		MaxVisitor visitor = new MaxVisitor();
		graph.dfs(visitor);
		return visitor.getMax();
	}
	public static Graph build(int n, int m) {
		Vertex[] aVertex = new Vertex[n];
		Graph graph = new Graph(aVertex);
		for (int i = 0; i < n; i++) {
			aVertex[i] = new Vertex(i);
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
