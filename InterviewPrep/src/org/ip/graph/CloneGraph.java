package org.ip.graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/clone-graph/">LC: 133</a>
 */
public class CloneGraph {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{2,4},{1,3},{2,4},{1,3}}, new int[][] {{2,4},{1,3},{2,4},{1,3}}};
		Object[] tc2 = new Object[] {new int[][] {{}}, new int[][] {{}}};
		Object[] tc3 = new Object[] {new int[][] {},new int[][] {}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		for (int i = 0; i < tcs.length; i++) {
			tcs[i][1] = Node.fromAdjacencyList((int[][]) tcs[i][1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	
	public static class Solver implements Problem {

		@Override
		public Node apply(Node node) {
			if (node == null) return null;
			Map<Integer, Node> visited = new HashMap<>();
			Deque<Node> deque = new LinkedList<>();
			deque.add(node);
			visited.put(node.val, new Node(node.val));
			while (!deque.isEmpty()) {
				Node current = deque.removeFirst();
				Node clone = visited.get(current.val);
				for (Node neighbor : current.neighbors) {
					if (!visited.containsKey(neighbor.val)) {
						visited.put(neighbor.val, new Node(neighbor.val));
						deque.addLast(neighbor);
					}
					clone.neighbors.add(visited.get(neighbor.val));
				}
			}
			return visited.get(node.val);
		}
		
	}
	public interface Problem extends Function<Node, Node>{
		
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
			return print(this);
		}
	    public static Node fromAdjacencyList(int[][] a) {
	    	Map<Integer, Node> map = new HashMap<>();
	    	for (int i = 0; i < a.length; i++) {
	    		Node node = map.computeIfAbsent(i + 1, (k) -> new Node(k));
	    		for (int j = 0; j < a[i].length; j++) {
	    			node.neighbors.add(map.computeIfAbsent(a[i][j], (k) -> new Node(k)));
	    		}
	    	}
	    	return map.get(1);
	    }
	    static String print(Node node) {
			if (node == null) return "[]";
			Deque<Node> queue = new LinkedList<>();
			queue.add(node);
			Map<Integer, Node> map = new HashMap<>();
			Set<Node> visited = new HashSet<>();
			while (!queue.isEmpty()) {
				Node current = queue.removeFirst();
				map.put(current.val, current);
				for (Node neighbor : current.neighbors) {
					if (visited.contains(neighbor)) continue;
					queue.addFirst(neighbor);
					visited.add(neighbor);
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 1; i <= map.size(); i++) {
				Node current = map.get(i);
				if (current == null) continue;
				sb.append("[");
				int j = 0;
				for (Node neighbor : current.neighbors) {
					if (j++ > 0) {
						sb.append(",");
					}
					sb.append(neighbor.val);
				}
				sb.append("]");
			}
			sb.append("]");
			return sb.toString();
		}
	}
}
