package org.ip.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//EPI: 19.1
public class Maze {
	public static void main(String[] s) {
		byte[][] maze = new byte[][] {
			{0,1,1,1,1,1,0,0,1,3},
			{1,1,0,1,1,1,1,1,1,1},
			{0,1,0,1,1,0,0,1,0,0},
			{1,1,1,0,0,0,1,1,0,1},
			{1,0,0,1,1,1,1,1,1,1},
			{1,0,0,1,1,0,1,0,0,1},
			{1,1,1,1,0,1,1,1,1,1},
			{0,1,0,1,0,1,0,1,1,1},
			{0,1,0,0,1,1,1,0,0,0},
			{2,1,1,1,1,1,1,0,0,1}
		};
		Graph graph = new Graph(maze);
		Vertex sync = dfs(graph);
		println(sync);
	}
	public static void println(Vertex v) {
		Vertex current = v;
		while (current != null) {
			System.out.print(", " + current.x + ":" + current.y);
			current = current.parent;
		}
	}
	public static Vertex dfs(Graph g) {
		List<Vertex> sources = g.getSources();
		Set<Vertex> visited = new HashSet<Vertex>();
		for (Vertex source : sources) {
			if (visited.contains(source)) continue;
			Vertex sync = dfs(g, source, visited);
			if (sync != null) return sync; 
		}
		return null;
	}
	public static Vertex dfs(Graph g, Vertex v, Set<Vertex> visited) {
		if (g.isSync(v)) {
			return v;
		}
		visited.add(v);
		List<Vertex> edges = g.getEdges(v);
		for (Vertex edge : edges) {
			if (visited.contains(edge)) continue;
			edge.setParent(v);
			Vertex sync = dfs(g, edge, visited);
			if (sync != null) {
				return sync;
			}
		}
		return null;
	}
	public static class Vertex {
		int x;
		int y;
		private Vertex parent;
		public Vertex(int x, int y) {this.x=x;this.y=y;}
		public void setParent(Vertex v) {this.parent=v;}
		@Override
		public boolean equals(Object v) {
			return this.x==((Vertex)v).x && this.y==((Vertex)v).y;
		}
		@Override
		public int hashCode() {
			return x * 10 + y;
		}
		@Override
		public String toString() {
			return x + ":" + y;
		}
	}
	public static class Graph {
		public byte WALL = 0;
		public byte OPEN = 1;
		public byte START = 2;
		public byte END = 3;
		private byte[][] map;
		private Set<Vertex> syncs;
		public Graph(byte[][] map) {
			this.map=map;
			this.syncs = new HashSet<Vertex>(this.getSyncs());
		}
		public boolean isSync(Vertex v) {
			return syncs.contains(v);
		}
		public List<Vertex> getSources() {
			List<Vertex> list = new LinkedList<Vertex>();
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					if (map[x][y] != START) continue;
					list.add(new Vertex(x,y));
				}
			}
			return list;
		}
		public List<Vertex> getSyncs() {
			List<Vertex> list = new LinkedList<Vertex>();
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
					if (map[x][y] != END) continue;
					list.add(new Vertex(x,y));
				}
			}
			return list;
		}
		public List<Vertex> getEdges(Vertex v) {
			List<Vertex> list = new LinkedList<Vertex>();
			int x = v.x;
			int y = v.y;
			if (x > 0 && map[x - 1][y] != WALL) {
				list.add(new Vertex(x - 1, y));
			}
			if (x < map.length - 1 && map[x + 1][y] != WALL) {
				list.add(new Vertex(x + 1, y));
			}
			if (y > 0 && map[x][y - 1] != WALL) {
				list.add(new Vertex(x, y - 1));
			}
			if (y < map[0].length - 1 && map[x][y + 1] != WALL) {
				list.add(new Vertex(x, y + 1));
			}
			return list;
		}
	}
}
