package org.ip.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// EPI 2018: 18.1
public class Maze2 {
	public static void main(String[] s) {
		// 0 is black, 1 is white, 2 is start, 3 is exit
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
		List<Position> list = new Maze2().dfs(graph);
		System.out.println(list);
	}
	public List<Position> dfs(Graph g) {
		Set<Position> visited = new HashSet<Position>();
		Map<Position, Position> parents = new HashMap<Position, Position>();
		Position start = g.start();
		if (start == null) throw new RuntimeException("No start position");
		Position end = _dfs(g, g.start(), visited, parents);
		if (end == null) throw new RuntimeException("Could not find end");
		return traverseParent(parents, start, end);
	}
	public List<Position> traverseParent(Map<Position, Position> parents, Position start, Position end) {
		Position current = end;
		List<Position> list = new ArrayList<Position>();
		while (current != null && !current.equals(start)) {
			list.add(current);
			current = parents.get(current);
		}
		if (current != null) {
			list.add(current);
		}
		return list;
	}
	private Position _dfs(Graph g, Position p, Set<Position> visited, Map<Position, Position> parents) {
		if (g.isEnd(p)) {
			return p;
		}
		for (Position edge : g.edges(p)) {
			if (visited.contains(edge)) continue;
			visited.add(edge);
			parents.put(edge, p);
			Position result = _dfs(g, edge, visited, parents);
			if (result != null) return result;
		}
		return null;
	}
	public static class Position {
		int x;
		int y;
		public Position(int x, int y) {this.x=x;this.y=y;}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}
		
	}
	public static class Graph {
		private byte[][] maze;
		public Graph(byte[][] maze) {
			this.maze=maze;
		}
		public Position start() {
			for (int y = 0; y < maze.length; y++) {
				for (int x = 0; x < maze[y].length; x++) {
					if (maze[y][x] == 2) return new Position(x, y);
				}
			}
			return null;
		}
		public List<Position> edges(Position p) {
			int y = p.y;
			int x = p.x;
			List<Position> list = new ArrayList<Position>(4);
			if (x + 1 < maze[0].length && maze[y][x+1] != 0) {
				list.add(new Position(x + 1, y));
			}
			if (x - 1 >= 0 && maze[y][x - 1] != 0) {
				list.add(new Position(x - 1, y));
			}
			if (y + 1 < maze.length && maze[y + 1][x] != 0) {
				list.add(new Position(x, y + 1));
			}
			if (y - 1 >= 0 && maze[y - 1][x] != 0) {
				list.add(new Position(x, y - 1));
			}
			return list;
		}
		public boolean isEnd(Position p) {
			return maze[p.y][p.x] == 3;
		}
	}
}
