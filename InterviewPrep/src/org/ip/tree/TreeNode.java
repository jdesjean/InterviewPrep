package org.ip.tree;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeNode implements ITreeNode<Integer> {
	public int val;
	public TreeNode left;
	public TreeNode right;

	public TreeNode() {
	}

	public TreeNode(int val) {
		this.val = val;
	}

	public TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}

	public static TreeNode find(TreeNode node, int k) {
		return (TreeNode) ITreeNode.find(node, k);
	}

	public static TreeNode fromBfs(Integer[] values) {
		return (TreeNode) ITreeNode.fromBfs(values, TreeNode::new);
	}

	public static void toBfs(TreeNode node, Integer[] values) {
		ITreeNode.toBfs(node, values);
	}
	public static int len(TreeNode node) {
		return ITreeNode.len(null);
	}
	
	public static void print(TreeNode node) {
		System.out.println(ITreeNode.toString(node));
	}

	@Override
	public String toString() {
		return ITreeNode.toString(this);
	}

	@Override
	public TreeNode clone() {
		return (TreeNode) ITreeNode.clone(this, TreeNode::new);
	}

	@Override
	public void addChild(ITreeNode<Integer> node) {
		if (left == null)
			left = (TreeNode) node;
		else
			right = (TreeNode) node;
	}

	@Override
	public Integer getVal() {
		return val;
	}

	@Override
	public List<ITreeNode<Integer>> getChilds() {
		return Arrays.asList(left, right);
	}
}
