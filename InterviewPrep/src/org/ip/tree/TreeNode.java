package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.ip.array.Utils;

public class TreeNode {
	 public int val;
	 public TreeNode left;
	 public TreeNode right;
	 public TreeNode() {}
	 public TreeNode(int val) { this.val = val; }
	 public TreeNode(int val, TreeNode left, TreeNode right) {
		 this.val = val;
		 this.left = left;
		 this.right = right;
	 }
	 public static int len(TreeNode node) {
		 AtomicInteger count = new AtomicInteger();
		 len(node, count);
		 return count.get();
	 }
	 private static void len(TreeNode node, AtomicInteger count) {
		 count.incrementAndGet();
		 if (node == null) return;
		 len(node.left, count);
		 len(node.right, count);
	 }
	 public static TreeNode find(TreeNode node, int k) {
		 if (node == null) return null;
		 if (node.val == k) return node;
		 TreeNode left = find(node.left, k);
		 if (left != null) return left;
		 return find(node.right, k);
	 }
	 public static TreeNode fromBfs(Integer[] values) {
		if (values == null || values.length == 0 || values[0] == null) return null;
		List<TreeNode> parents = new ArrayList<>();
		TreeNode root = new TreeNode(values[0]);
		parents.add(root);
		for (int i = 1; i < values.length;) {
			List<TreeNode> childs = new ArrayList<TreeNode>(parents.size() * 2);
			for (TreeNode parent : parents) {
				Integer left = i < values.length ? values[i++] : null;
				Integer right = i < values.length ? values[i++] : null;
				if (left != null) {
					parent.left = new TreeNode(left);
					childs.add(parent.left);
				}
				if (right != null) {
					parent.right = new TreeNode(right);
					childs.add(parent.right);
				}
			}
			parents = childs;
		}
		return root;
	}
	public static void toBfs(TreeNode node, Integer[] values) {
		Deque<TreeNode> nodes = new LinkedList<TreeNode>();
		nodes.add(node);
		int index = 0;
		while (!nodes.isEmpty()) {
			TreeNode current = nodes.removeFirst();
			values[index++] = current != null ? current.val : null;
			if (current != null) {
				nodes.addLast(current.left);
				nodes.addLast(current.right);
			}
		}
	}
	public static void print(TreeNode node) {
		Integer[] values = new Integer[TreeNode.len(node)]; 
		TreeNode.toBfs(node, values);
		Arrays.toString(values);
	}
	
	@Override
	public String toString() {
		Integer[] values = new Integer[TreeNode.len(this)]; 
		TreeNode.toBfs(this, values);
		return Arrays.toString(values);
	}
	
	@Override
	public TreeNode clone() {
		Integer[] values = new Integer[TreeNode.len(this)]; 
		TreeNode.toBfs(this, values);
		return TreeNode.fromBfs(values);
	}
}
