package org.ip.narytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Node {
	 public int val;
	 public final List<Node> children;
	 public Node() {children = new ArrayList<>();}
	 public Node(int val) { this.val = val; children = new ArrayList<>();}
	 public Node(int val, ArrayList<Node> children) {
		 this.val = val;
		 this.children = children;
	 }
	 public static int len(Node node) {
		 AtomicInteger count = new AtomicInteger();
		 len(node, count);
		 return count.get();
	 }
	 private static void len(Node node, AtomicInteger count) {
		 count.incrementAndGet();
		 if (node == null) return;
		 for (Node child : node.children) {
			 len(child, count);
		 }
	 }
	 public static Node find(Node node, int k) {
		 if (node == null) return null;
		 if (node.val == k) return node;
		 for (Node child : node.children) {
			 Node left = find(child, k);
			 if (left != null) return left;
		 }
		 return null;
	 }
	 public static Node fromBfs(Integer[] values) {
		if (values == null || values.length == 0 || values[0] == null) return null;
		List<Node> parents = new ArrayList<>();
		Node root = new Node(values[0]);
		parents.add(root);
		for (int i = 2; i < values.length;) {
			List<Node> childs = new ArrayList<Node>(parents.size() * 2);
			for (Node parent : parents) {
				for (; i < values.length && values[i] != null; i++) {
					Node node = new Node(values[i]);
					childs.add(node);
					parent.children.add(node);
				}
				i++; // next non null node
			}
			parents = childs;
		}
		return root;
	}
	public static void toBfs(Node node, Integer[] values) {
		Deque<Node> nodes = new LinkedList<Node>();
		nodes.add(node);
		int index = 0;
		while (!nodes.isEmpty()) {
			Node current = nodes.removeFirst();
			values[index++] = current != null ? current.val : null;
			if (current != null) {
				for (Node child : current.children) {
					nodes.addLast(child);
				}
				nodes.addLast(null);
			}
		}
	}
	public static void print(Node node) {
		Integer[] values = new Integer[Node.len(node)]; 
		Node.toBfs(node, values);
		Arrays.toString(values);
	}
	
	@Override
	public String toString() {
		Integer[] values = new Integer[Node.len(this)]; 
		Node.toBfs(this, values);
		return Arrays.toString(values);
	}
	
	@Override
	public Node clone() {
		Integer[] values = new Integer[Node.len(this)]; 
		Node.toBfs(this, values);
		return Node.fromBfs(values);
	}
}
