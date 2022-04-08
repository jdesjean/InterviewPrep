package org.ip.siblingtree;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.ip.tree.ITreeNode;
import org.ip.tree.TreeNode;

public class Node implements ITreeNode<Integer> {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

	public static Node fromBfs(Integer[] values) {
		return (Node) ITreeNode.fromBfs(values, Node::new);
	}

	@Override
	public String toString() {
		int len = ITreeNode.len(this);
		int depth = ITreeNode.depth(this);
		int size = len + depth;
		Integer[] values = new Integer[size];
		AtomicInteger idx = new AtomicInteger();
		ITreeNode.toBfs(this, (value) -> {
			if (value == null) {
				values[idx.getAndIncrement()] = null;
			} else {
				values[idx.getAndIncrement()] = (Integer) value.getVal();
				if (((Node)value).next == null) {
					values[idx.getAndIncrement()] = null;
				}
			}
		});
		return Arrays.toString(values);
	}

	@Override
	public Node clone() {
		return (Node) ITreeNode.clone(this, Node::new);
	}

	@Override
	public void addChild(ITreeNode<Integer> node) {
		if (left == null)
			left = (Node) node;
		else
			right = (Node) node;
	}

	@Override
	public Integer getVal() {
		return val;
	}

	@Override
	public List<ITreeNode<Integer>> getChilds() {
		return Arrays.asList(left, right);
	}
};
