package org.ip.tree;

import java.util.function.Function;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/">LC: 236</a>
 * EPI 10.3
 * EPI 2018: 9.3
 */
public class LCA {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 1};
		Object[] tc2 = new Object[] {5, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 4};
		Object[] tc3 = new Object[] {1, new Integer[] {1,2}, 1, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
			tc[2] = TreeNode.find((TreeNode) tc[1], (Integer) tc[2]);
			tc[3] = TreeNode.find((TreeNode) tc[1], (Integer) tc[3]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs, false);
	}
	static class Solver implements Problem {

		@Override
		public TreeNode apply(TreeNode a, TreeNode b, TreeNode c) {
			return new State(b, c).apply(a);
		}
		static class State implements Function<TreeNode, TreeNode> {
			private TreeNode p;
			private TreeNode q;

			public State(TreeNode p, TreeNode q) {
				this.p = p;
				this.q = q;
			}

			@Override
			public TreeNode apply(TreeNode t) {
				if (t == null || t == p || t == q) return t;
				TreeNode left = apply(t.left);
				TreeNode right = apply(t.right);
				if (left != null && right != null) {
					return t;
				}
				return left != null ? left : right;
			}
		}
	}
	
	interface Problem extends TriFunction<TreeNode, TreeNode, TreeNode, TreeNode> {
		
	}
}
