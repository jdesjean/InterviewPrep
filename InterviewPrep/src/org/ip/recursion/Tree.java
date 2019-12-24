package org.ip.recursion;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Tree {
	public static void main(String[] s) {
		solve(new PrintVisitor(), 3);
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(Node node) {
			System.out.println("*****************");
			print(node);
		}
	}
	public interface Visitor {
		public void visit(Node node);
	}
	public static class IntWrapper {
		int value;
	}
	public static void solve(Visitor visitor, int n) {
		if (n == 0) return;
		n = n - 1;
		Node node = new Node();
		for (int i = 0; i <= n; i++) {
			int r = n - i;
			node.left = null;
			node.right = null;
			solve(visitor, i, node, node, true, r == 0);
			solve(visitor, r, node, node, false, i != 0);
		}
	}
	public static void solve(Visitor visitor, int n, Node root, Node parent, boolean left, boolean print) {
		if (n == 0) {
			return ;
		}
		n = n - 1;
		Node node = new Node();
		if (left) {
			parent.left = node;
		} else {
			parent.right = node;
		}
		for (int i = 0; i <= n; i++) {
			int r = n - i;
			node.left = null;
			node.right = null;
			solve(visitor, i, root, node, true, r == 0);
			solve(visitor, r, root, node, false, r != 0);
			if (n == 0 && print) {
				visitor.visit(root);
			}
		}
	}
	public static void print(Node node) {
		Map<Node,Pair> map = new HashMap<Node, Pair>();
		dfs(map, node, 0, 0);
		int min = Integer.MAX_VALUE;
		for (Entry<Node,Pair> entry : map.entrySet()) {
			if (entry.getValue().x < min) {
				min = entry.getValue().x; 
			}
		}
		bfs(map, node, min);
	}
	public static void bfs(Map<Node,Pair> map, Node node, int min) {
		Deque<Node> queue = new LinkedList<Node>();
		queue.add(node);
		min = Math.abs(min);
		int y = 0;
		while (!queue.isEmpty()) {
			Node current = queue.removeFirst();
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
			Pair pair = map.get(current);
			if (pair.y != y) {
				System.out.println("");
				y = pair.y;
			}
			for (int i = 0; i < pair.x + min; i++) {
				System.out.print(' ');
			}
			System.out.print('x');
		}
		System.out.println("");
	}
	public static void dfs(Map<Node,Pair> map, Node node, int x, int y) {
		if (node == null) return;
		map.put(node, new Pair(x,y));
		dfs(map, node.left, x - 1, y + 1);
		dfs(map, node.right, x + 1, y + 1);
	}
	public static class Pair {
		int x;
		int y;
		public Pair(int x, int y) {this.x=x; this.y=y;}
	}
	public static class Node {
		public Node left;
		public Node right;
	}
	
}
