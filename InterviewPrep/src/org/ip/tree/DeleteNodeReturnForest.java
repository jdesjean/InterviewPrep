package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ip.Test;
import org.ip.tree.ClosestLeafBinaryTree.Problem;

/**
 * <a href="https://leetcode.com/problems/delete-nodes-and-return-forest/">LC: 1110</a>
 */
public class DeleteNodeReturnForest {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[][] {{1,2,null,4},{6},{7}}, new Integer[] {1,2,3,4,5,6,7}, new int[] {3,5}};
		Object[] tc2 = new Object[] {new Integer[][] {{1,2,4}}, new Integer[] {1,2,4,null,3}, new int[] {3}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public List<TreeNode> apply(TreeNode t, int[] u) {
			if (t == null) return null;
			List<TreeNode> res = new ArrayList<>();
			Set<Integer> toDelete = Arrays.stream(u).boxed().collect(Collectors.toSet());
			delete(t, toDelete, true, res);
			return res;
		}
		TreeNode delete(TreeNode t, Set<Integer> set, boolean isRoot, List<TreeNode> res) {
			if (t == null) return t;
			boolean toDelete = set.contains(t.val);
			if (isRoot && !toDelete) {
				res.add(t);
			}
			t.left = delete(t.left, set, toDelete, res);
			t.right = delete(t.right, set, toDelete, res);
			return toDelete ? null : t;
		}
	}
	static class Solver2 implements Problem {

		@Override
		public List<TreeNode> apply(TreeNode t, int[] u) {
			if (t == null) return null;
			List<TreeNode> res = new ArrayList<>();
			TreeNode temp = new TreeNode();
			temp.left = t;
			Set<Integer> toDelete = Arrays.stream(u).boxed().collect(Collectors.toSet());
			delete(t, toDelete, res);
			if (temp.left != null) {
				res.add(temp.left);
			}
			return res;
		}
		void delete(TreeNode t, Set<Integer> set, List<TreeNode> res) {
			if (t == null) return;
			delete(t.left, set, res);
			delete(t.right, set, res);
			if (t.left != null && set.contains(t.left.val)) {
				
				delete(t.left, res);
				t.left = null;
			}
			if (t.right != null && set.contains(t.right.val)) {
				
				delete(t.right, res);
				t.right = null;
			}
		}
		void delete(TreeNode t, List<TreeNode> res) {
			Stream.of(t.left, t.right).filter(n -> n != null).forEach(n -> res.add(n));
		}
	}
	static class Solver implements Problem {

		@Override
		public List<TreeNode> apply(TreeNode t, int[] u) {
			if (t == null) return null;
			List<TreeNode> res = new ArrayList<>();
			Set<Integer> toDelete = Arrays.stream(u).boxed().collect(Collectors.toSet());
			delete(t, toDelete, null, res);
			return res;
		}
		void delete(TreeNode t, Set<Integer> set, TreeNode parent, List<TreeNode> res) {
			if (t == null) return;
			boolean toDelete = set.contains(t.val);
			if (parent == null && !toDelete) {
				res.add(t);
			}
			if (parent != null && toDelete) {
				if (parent.left == t) {
					parent.left = null;
				} else {
					parent.right = null;
				}
			}
			delete(t.left, set, toDelete ? null : t, res);
			delete(t.right, set, toDelete ? null : t, res);
		}
	}
	interface Problem extends BiFunction<TreeNode, int[], List<TreeNode>> {
		
	}
}
