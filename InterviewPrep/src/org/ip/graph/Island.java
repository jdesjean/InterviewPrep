package org.ip.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Island {
	public static void main(String[] s) {
		System.out.println(solve(new int[][]{{1,1,0,0,0},{0,1,0,0,1},{1,0,0,1,1},{0,0,0,0,0},{1,0,1,0,1}}));
	}
	public static int solve(int[][] matrix) {
		int count = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				if (matrix[x][y] <= 0) continue;
				bfs(matrix, new Point(x,y));
				count++;
			}
		}
		return count;
	}
	public static class Point {
		int x;
		int y;
		public Point(int x, int y) {this.x=x;this.y=y;}
	}
	public static void bfs(int[][] matrix, Point start) {
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(start);
		matrix[start.x][start.y] = -1;
		while (!queue.isEmpty()) {
			Point current = queue.remove();
			int x = current.x;
			int y = current.y;
			for (int xx = Math.max(x-1, 0); xx <= Math.min(x+1, matrix.length-1); xx++) {
				for (int yy = Math.max(y-1, 0); yy <= Math.min(y+1, matrix[0].length-1); yy++) {
					if (matrix[xx][yy] <= 0) continue;
					matrix[xx][yy] = -1;
					queue.add(new Point(xx,yy));
				}
			}
		}
	}
}
