package org.ip.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/clone-graph/">LC: 133</a>
 */
public class CloneGraph {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				CloneGraph::tc1,
				CloneGraph::tc2,
				CloneGraph::tc3,
				CloneGraph::tc4);
		Solver[] solvers = new Solver[] {new BFS()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		one.neighbors.add(two);
		one.neighbors.add(four);
		two.neighbors.add(one);
		two.neighbors.add(three);
		three.neighbors.add(two);
		three.neighbors.add(four);
		four.neighbors.add(one);
		four.neighbors.add(three);
		print(one);
		Node copy = solver.solve(one);
		print(copy);
	}
	public static void tc2(Solver solver) {
		Node one = new Node(1);
		print(one);
		Node copy = solver.solve(one);
		print(copy);
	}
	public static void tc3(Solver solver) {
		Node one = null;
		print(one);
		Node copy = solver.solve(one);
		print(copy);
	}
	public static void tc4(Solver solver) {
		Node one = new Node(1);
		Node two = new Node(2);
		one.neighbors.add(two);
		two.neighbors.add(one);
		print(one);
		Node copy = solver.solve(one);
		print(copy);
	}
	static void print(Node node) {
		if (node == null) return;
		Deque<Node> queue = new LinkedList<Node>();
		queue.add(node);		
		Set<Node> visited = new HashSet<Node>();
		visited.add(node);
		while (!queue.isEmpty()) {
			Node current = queue.removeFirst();
			System.out.print(current.neighbors);
			for (Node neighbor : current.neighbors) {
				if (visited.contains(neighbor)) continue;
				queue.addFirst(neighbor);
				visited.add(neighbor);
			}
		}
		System.out.println();
	}
	public static class BFS implements Solver {

		@Override
		public Node solve(Node node) {
			if (node == null) return null;
			Map<Node, Node> nodes = new HashMap<>();
			Deque<Node> queue = new LinkedList<>();
			queue.add(node);
			nodes.put(node, new Node(node.val));
			while (!queue.isEmpty()) {
				Node current = queue.removeFirst();
				Node copy = nodes.get(current);
				for (Node neighbor : current.neighbors) {
					Node neighborCopy = nodes.get(neighbor);
					if (neighborCopy == null) {
						neighborCopy = new Node(neighbor.val);
						nodes.put(neighbor, neighborCopy);
						queue.addFirst(neighbor);
					}
					copy.neighbors.add(neighborCopy);
				}
			}
			
			return nodes.get(node);
		}
		
	}
	public interface Solver {
		Node solve(Node node);
	}
	public static class Node {
	    public int val;
	    public List<Node> neighbors;
	    public Node() {
	        val = 0;
	        neighbors = new ArrayList<Node>();
	    }
	    public Node(int _val) {
	        val = _val;
	        neighbors = new ArrayList<Node>();
	    }
	    public Node(int _val, ArrayList<Node> _neighbors) {
	        val = _val;
	        neighbors = _neighbors;
	    }
		@Override
		public String toString() {
			return String.valueOf(val);
		}
	    
	}
}
