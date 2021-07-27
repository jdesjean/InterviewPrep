package org.ip.bst;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.com/problems/binary-search-tree-iterator/">LC: 173</a>
 */
public class BSTInOrderIterator {
	public static void main(String[] s) {
		BSTInOrderIterator iterator = new BSTInOrderIterator(
				new TreeNode(7,
						new TreeNode(3),
						new TreeNode(15,
								new TreeNode(9),
								new TreeNode(20))));
		while (iterator.hasNext()) {
			System.out.println(iterator.next() + ",");
		}
	}
	
	Deque<TreeNode> _stack = new LinkedList<>();
	
	public BSTInOrderIterator(TreeNode root) {
         pushLeft(root);
    }
	
	void pushLeft(TreeNode node) {
		if (node == null) return;
		_stack.push(node);
		while (node.left != null) {
			node = node.left;
			_stack.push(node);
		}
	}
    
    public int next() {
        TreeNode node = _stack.pop();
        if (node.right != null) {
        	pushLeft(node.right);
        }
        return node.val;
    }
    
    public boolean hasNext() {
        return !_stack.isEmpty();
    }
    
    public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}
