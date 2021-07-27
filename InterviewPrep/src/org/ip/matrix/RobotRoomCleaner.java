package org.ip.matrix;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.ip.Test;
import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/robot-room-cleaner/">LC: 489</a>
 */
public class RobotRoomCleaner {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[0][0], new int[][] {{ 1, 1, 1}, {1, 0, 1}, {1, 0, 1}}, new int[] {0, 0}, Direction.N};
		Object[][] tcs = new Object[][] { tc1 };
		for (Object[] tc : tcs) {
			tc[1] = new Robot((int[][]) tc[1], (int[]) tc[2], (Direction) tc[3]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	private final static int[][] directions = new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
	public static class Solver implements Problem {
		
		@Override
		public void accept(Robot t) {
			Set<Point> visited = new HashSet<>();
			_accept(t, visited, new Point(0, 0), Direction.N);
		}
		
		void _accept(Robot t, Set<Point> visited, Point current, Direction d) {
			visited.add(current);
			t.clean();
			for (int i = 0; i < directions.length; i++) {
				int ll = current.l + directions[d.value][0];
				int cc = current.c + directions[d.value][1];
				Point next = new Point(ll, cc);
				if (!visited.contains(next) && t.move()) {
					_accept(t, visited, next, d);
					goBack(t);
				}
				d = d.turnRight();
				t.turnRight();
			}
		}
		
		void goBack(Robot t) {
			t.turnRight();
			t.turnRight();
			t.move();
			t.turnRight();
			t.turnRight();
		}
	}
	public static class Point {
		public final int l;
		public final int c;
		public Point(int l, int c) {
			this.l = l;
			this.c = c;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + l;
			result = prime * result + c;
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
			Point other = (Point) obj;
			if (c != other.c)
				return false;
			if (l != other.l)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Point [l=" + l + ", c=" + c + "]";
		}
		
	}

	public interface Problem extends Consumer<Robot> {

	}
	
	enum Direction {
		N(0), E(1), S(2), W(3);
		int value; 
		Direction(int value) {
			this.value = value;
		}
		public Direction turnLeft() {
			int value = this.value - 1;
			if (value < 0) value = value + 4;
			return Direction.fromValue(value);
		}
		public Direction turnRight() {
			int value = (this.value + 1) % 4;
			return Direction.fromValue(value);
		}
		public static Direction fromValue(int value) {
			if (value == 0) return N;
			else if (value == 1) return E;
			else if (value == 2) return S;
			else if (value == 3) return W;
			else {
				throw new RuntimeException();
			}
		}
	}

	public static class Robot {
		Direction direction;
		int c;
		int l;
		int[][] grid;
		public Robot(int[][] grid, int[] pos, Direction direction) {
			this.grid = grid;
			this.c = pos[0];
			this.l = pos[1];
			this.direction = direction;
		}
		public boolean move() {
			int ll = l + directions[direction.value][0];
			int cc = c + directions[direction.value][1];
			if (ll >= grid.length || ll < 0) return false;
			if (cc >= grid[ll].length || cc < 0) return false;
			if (grid[ll][cc] == 0) return false;
			l = ll;
			c = cc;
			return true;
		}

		// Robot will stay in the same cell after calling turnLeft/turnRight.
		// Each turn will be 90 degrees.
		public void turnLeft() {
			direction = direction.turnLeft();
		}

		public void turnRight() {
			direction = direction.turnRight();
		}

		// Clean the current cell.
		public void clean() {
			grid[l][c] = 2;
		}
		
		@Override
		public String toString() {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    final String utf8 = StandardCharsets.UTF_8.name();
		    try (PrintStream ps = new PrintStream(baos, true, utf8)) {
		        Utils.print(ps, grid);
		        return baos.toString(utf8);
		    } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    return "";
		}
	}
}
