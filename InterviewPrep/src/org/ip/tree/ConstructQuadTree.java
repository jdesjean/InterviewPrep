package org.ip.tree;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;
import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/construct-quad-tree/">LC: 427</a>
 */
public class ConstructQuadTree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{0,1},{1,0},{1,1},{1,1},{1,0}}, new int[][] {{0,1},{1,0}}};
		Object[] tc2 = new Object[] {new int[][] {{0,1},{1,1},{0,1},{1,1},{1,0},null,null,null,null,{1,0},{1,0},{1,1},{1,1}}, new int[][] {{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0}}};
		Object[] tc3 = new Object[] {new int[][] {{1,1}}, new int[][] {{1,1},{1,1}}};
		Object[] tc4 = new Object[] {new int[][] {{1,0}}, new int[][] {{0}}};
		Object[] tc5 = new Object[] {new int[][] {{0,1},{1,1},{1,0},{1,0},{1,1}}, new int[][] {{1,1,0,0},{1,1,0,0},{0,0,1,1},{0,0,1,1}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3() };
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public Node apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			Node node = _apply(t, 0, 0, t.length - 1); 
			return node;
		}
		Node _apply(int[][] t, int r1, int c1, int l) {
			if (l == 0) {
				Node node = new Node();
				node.isLeaf = true;
				node.val = t[r1][c1] == 1;
				return node;
			}
			Node node = new Node();
			int ml = l / 2;
			int mr = r1 + ml;
			int mc = c1 + ml;
			Node topLeft = _apply(t, r1, c1, ml);
			Node topRight = _apply(t, r1, mc + 1, ml);
			Node bottomLeft =  _apply(t, mr + 1, c1, ml);
			Node bottomRight = _apply(t, mr + 1, mc + 1, ml);
			if (topLeft.isLeaf
					&& topRight.isLeaf
					&& bottomLeft.isLeaf
					&& bottomRight.isLeaf
					&& topLeft.val == topRight.val
					&& topLeft.val == bottomLeft.val
					&& topLeft.val == bottomRight.val){
				node.isLeaf = true;
			} else {
				node.topLeft = topLeft;
				node.topRight = topRight;
				node.bottomLeft = bottomLeft;
				node.bottomRight = bottomRight;
			}
			node.val = !node.isLeaf || t[r1][c1] == 1;
			return node;
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public Node apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			Node node = _apply(t, 0, 0, t.length - 1, t[0].length - 1); 
			return node;
		}
		Node _apply(int[][] t, int r1, int c1, int r2, int c2) {
			if (r1 > r2 || c1 > c2) return null;
			if (r1 == r2 && c1 == c2) {
				Node node = new Node();
				node.isLeaf = true;
				node.val = t[r1][c1] == 1;
				return node;
			}
			Node node = new Node();
			int mr = r1 + (r2 - r1) / 2;
			int mc = c1 + (c2 - c1) / 2;
			Node topLeft = _apply(t, r1, c1, mr, mc);
			Node topRight = _apply(t, r1, mc + 1, mr, c2);
			Node bottomLeft =  _apply(t, mr + 1, c1, r2, mc);
			Node bottomRight = _apply(t, mr + 1, mc + 1, r2, c2);
			if (topLeft.isLeaf
					&& topRight.isLeaf
					&& bottomLeft.isLeaf
					&& bottomRight.isLeaf
					&& topLeft.val == topRight.val
					&& topLeft.val == bottomLeft.val
					&& topLeft.val == bottomRight.val){
				node.isLeaf = true;
			} else {
				node.topLeft = topLeft;
				node.topRight = topRight;
				node.bottomLeft = bottomLeft;
				node.bottomRight = bottomRight;
			}
			node.val = !node.isLeaf || t[r1][c1] == 1;
			return node;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public Node apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			return _apply(t, 0, 0, t.length - 1, t[0].length - 1);
		}
		Node _apply(int[][] t, int r1, int c1, int r2, int c2) {
			if (r1 > r2 || c1 > c2) return null;
			if (r1 == r2 && c1 == c2) {
				Node node = new Node();
				node.isLeaf = true;
				node.val = t[r1][c1] == 1;
				return node;
			}
			Node node = new Node();
			node.isLeaf = uniform(t, r1, c1, r2, c2);
			node.val = !node.isLeaf || t[r1][c1] == 1;
			if (node.isLeaf) {
				return node;
			} else {
				int mr = r1 + (r2 - r1) / 2;
				int mc = c1 + (c2 - c1) / 2;
				node.topLeft = _apply(t, r1, c1, mr, mc);
				node.topRight = _apply(t, r1, mc + 1, mr, c2);
				node.bottomLeft = _apply(t, mr + 1, c1, r2, mc);
				node.bottomRight = _apply(t, mr + 1, mc + 1, r2, c2);
				return node;
			}
		}
		boolean uniform(int[][] t, int r1, int c1, int r2, int c2) {
			for (int r = r1; r <= r2; r++) {
				for (int c = c1; c <= c2; c++) {
					if (t[r][c] != t[r1][c1]) return false;
				}
			}
			return true;
		}
	}
	interface Problem extends Function<int[][], Node> {
		
	}
	static class Node {
	    public boolean val;
	    public boolean isLeaf;
	    public Node topLeft;
	    public Node topRight;
	    public Node bottomLeft;
	    public Node bottomRight;

	    public Node() {
	        this.val = false;
	        this.isLeaf = false;
	        this.topLeft = null;
	        this.topRight = null;
	        this.bottomLeft = null;
	        this.bottomRight = null;
	    }
	    
	    public Node(boolean val, boolean isLeaf) {
	        this.val = val;
	        this.isLeaf = isLeaf;
	        this.topLeft = null;
	        this.topRight = null;
	        this.bottomLeft = null;
	        this.bottomRight = null;
	    }
	    
	    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
	        this.val = val;
	        this.isLeaf = isLeaf;
	        this.topLeft = topLeft;
	        this.topRight = topRight;
	        this.bottomLeft = bottomLeft;
	        this.bottomRight = bottomRight;
	    }
	    public static void toBfs(Node node, Integer[][] values) {
			Deque<Node> nodes = new LinkedList<Node>();
			nodes.add(node);
			int index = 0;
			while (!nodes.isEmpty()) {
				Node current = nodes.removeFirst();
				values[index++] = current != null ? new Integer[] {current.isLeaf ? 1 : 0, current.val ? 1 : 0} : null;
				if (current != null) {
					if (!current.isLeaf) {
						nodes.addLast(current.topLeft);
						nodes.addLast(current.topRight);
						nodes.addLast(current.bottomLeft);
						nodes.addLast(current.bottomRight);
					}
				}
			}
		}
		@Override
		public String toString() {
			Integer[][] values = new Integer[len(this)][]; 
			toBfs(this, values);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final String utf8 = StandardCharsets.UTF_8.name();
			try {
				PrintStream ps = new PrintStream(baos, true, utf8);
				Utils.print(ps, values);
				return baos.toString(utf8);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		public static int len(Node node) {
			 AtomicInteger count = new AtomicInteger();
			 len(node, count);
			 return count.get();
		 }
		 private static void len(Node node, AtomicInteger count) {
			 count.incrementAndGet();
			 if (node.isLeaf) return;
			 len(node.topLeft, count);
			 len(node.topRight, count);
			 len(node.bottomLeft, count);
			 len(node.bottomRight, count);
		 }
	};
}
