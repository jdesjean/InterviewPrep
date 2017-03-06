package org.ip.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.ip.graph.Graph.Visitor;

public class Rainfall {
	public static void main(String[] s) {
		System.out.println(Arrays.toString(solve(new int[][]{{1,5,2},{2,4,7},{3,6,9}})));
		System.out.println(Arrays.toString(solve(new int[][]{{10}})));
		System.out.println(Arrays.toString(solve(new int[][]{{1,0,2,5,8},{2,3,4,7,9},{3,5,7,8,9},{1,2,5,4,3},{3,3,5,2,1}})));
	}
	public interface VisitorInt {
		public void visit(int count);
	}
	public static class RainfallVisitor implements VisitorInt {
		boolean visited = false;
		@Override
		public void visit(int count) {
			if (visited) System.out.print(" ");
			System.out.print(count);
			visited = true;
		}
		
	}
	public static class CountVisitor implements Visitor<Vertex> {
		public int count = 0;;
		@Override
		public void visit(Vertex t, int depth) {
			count++;
		}
		
	}
	public static class Pair {
		public int col;
		public int line;
	}
	public static int[] solve(int[][] map) {
		if (map.length == 0) return new int[0];
		Graph graph = new Graph();
		Vertex[][] vertex = new Vertex[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				vertex[i][j] = new Vertex(index(i,j,map.length));
			}
		}
		Pair pair = new Pair();
		List<Vertex> sink = new ArrayList<Vertex>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				pair.line = i;
				pair.col = j;
				smallestNeighbor(map,pair);
				if (pair.line == i && pair.col == j) sink.add(vertex[i][j]);
				else graph.addEdge(vertex[pair.line][pair.col], vertex[i][j]);
			}
		}
		Set<Vertex> visited = new HashSet<Vertex>();// not needed
		int[] sizes = new int[sink.size()];
		for (int i = 0; i < sink.size(); i++) {
			sizes[i] = bfs(graph,sink.get(i),visited);
		}
		return sizes;
	}
	public static int bfs(Graph graph, Vertex v, Set<Vertex> visited) {
		Deque<Vertex> queue = new LinkedList<Vertex>();
		queue.addLast(v);
		CountVisitor visitor = new CountVisitor();
		graph.bfs(visited,queue,visitor,0);
		return visitor.count;
	}
	
	public static int index(int i, int j, int len) {
		return i*len+j;
	}
	public static void smallestNeighbor(int[][] map, Pair pair) {
		int i = pair.line;
		int j = pair.col;
		int min = map[i][j];
		if (i > 0 && map[i-1][j] < min) {
			pair.line = i-1;
			pair.col = j;
			min = map[i-1][j];
		}
		if (j > 0 && map[i][j-1] < min) {
			pair.line = i;
			pair.col = j-1;
			min = map[i][j-1];
		}
		if (i + 1 < map.length && map[i+1][j] < min) {
			pair.line = i+1;
			pair.col = j;
			min = map[i+1][j];
		}
		if (j + 1 < map[i].length && map[i][j+1] < min) {
			pair.line = i;
			pair.col = j+1;
			min = map[i][j+1];
		}
	}
}
