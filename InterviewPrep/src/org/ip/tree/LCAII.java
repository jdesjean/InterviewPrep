package org.ip.tree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/">LC: 1644</a>
 */
public class LCAII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 1};
		Object[] tc2 = new Object[] {5, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 4};
		Object[] tc3 = new Object[] {1, new Integer[] {1,2}, 1, 2};
		Object[] tc4 = new Object[] {null, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 10};
		Object[] tc5 = new Object[] {5, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 4};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
			tc[2] = TreeNode.find((TreeNode) tc[1], (Integer) tc[2]);
			TreeNode q = TreeNode.find((TreeNode) tc[1], (Integer) tc[3]);
			tc[3] = q != null ? q : new TreeNode((Integer)tc[3]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs, false);
	}
	static class Solver2 implements Problem {

		@Override
		public TreeNode apply(TreeNode root, TreeNode p, TreeNode q) {
			AtomicInteger sum = new AtomicInteger();
			TreeNode lca = _apply(root, p, q, sum);
			return sum.get() == 2 ? lca : null;
		}
		TreeNode _apply(TreeNode root, TreeNode p, TreeNode q, AtomicInteger sum) {
			if (root == null) return root;
			TreeNode left = _apply(root.left, p, q, sum);
			if (sum.get() == 2) {
				return left;
			}
			TreeNode right = _apply(root.right, p, q, sum);
			if (root == p || root == q) {
				sum.incrementAndGet();
				return root;
			}
			return left != null ? right != null ? root : left : right;
		}
	}
	static class Solver implements Problem {

		@Override
		public TreeNode apply(TreeNode root, TreeNode p, TreeNode q) {
			LCAWrapper wrapper = _apply(root, p, q);
			return wrapper.count >= 2 ? wrapper.node : null;
		}
		LCAWrapper _apply(TreeNode root, TreeNode p, TreeNode q) {
			if (root == null) return new LCAWrapper(null, 0);
			LCAWrapper left = _apply(root.left, p, q);
			if (left.count >= 2) return left;
			LCAWrapper right = _apply(root.right, p, q);
			boolean target = root == p || root == q;
			int sum = (target ? 1 : 0) + left.count + right.count;
			if (target || sum > 1) return new LCAWrapper(root, sum);
			return left.count != 0 ? left : right;
		}
		static class LCAWrapper {
			TreeNode node;
			int count;
			public LCAWrapper(TreeNode node, int count) {
				super();
				this.node = node;
				this.count = count;
			}
			
		}
	}
	
	interface Problem extends TriFunction<TreeNode, TreeNode, TreeNode, TreeNode> {
		
	}
}
