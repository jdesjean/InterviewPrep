package org.ip.tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/flatten-binary-tree-to-linked-list/">LC: 114</a>
 */
public class FlattenBinaryTreeToLinkedList {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {1,null,2,null,3,null,4,null,5,null,6}, new Integer[] {1,2,5,3,4,null,6}};
		Object[][] tcs = new Object[][] {tc1};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public void accept(TreeNode t) {
			for (; t != null; t = t.right) {
				if (t.left == null) continue;
				TreeNode last = last(t.left);
				last.right = t.right;
				t.right = t.left;
				t.left = null;
			}
		}
		TreeNode last(TreeNode t) {
			while (t.right != null) {
				t = t.right;
			}
			return t;
		}
	}
	static class Solver2 implements Problem {

		@Override
		public void accept(TreeNode t) {
			_accept(t, new AtomicReference<>());
		}
		void _accept(TreeNode t, AtomicReference<TreeNode> last) {
			if (t == null) return;
			_accept(t.right, last);
			_accept(t.left, last);
			t.right = last.get();
			t.left = null;
			last.set(t);
		}
	}
	static class Solver implements Problem {

		@Override
		public void accept(TreeNode t) {
			if (t == null) return;
			//preorder use stack, keep track of previous element where to add on right
			Deque<TreeNode> deque = new ArrayDeque<>();
			TreeNode parent = new TreeNode(0);
			deque.add(t);
			while (!deque.isEmpty()) {
				TreeNode current = deque.remove();
				for (TreeNode child : Arrays.asList(current.right, current.left)) {
					if (child != null) {
						deque.push(child);
					}
				}
				parent.left = null;
				parent.right = current;
				parent = current;
			}
		}
		
	}
	interface Problem extends Consumer<TreeNode> {
		
	}
}
