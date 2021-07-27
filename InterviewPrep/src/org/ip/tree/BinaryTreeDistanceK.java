package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/">LC: 863</a>
 */
public class BinaryTreeDistanceK {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(7,4,1), new Integer[] {3,5,1,6,2,0,8,null,null,7,4}, 5, 2};
		/**
		 *       3
		 *    5      1
		 *  6   2   0 8
		 * N N 7 4
		 */
		Object[] tc2 = new Object[] {Arrays.asList(1), new Integer[] {0,1,null,3,2}, 2, 1};
		/**
		 *   0
		 *  1   N
		 * 3 2
		 */
		Object[] tc3 = new Object[] {Arrays.asList(2), new Integer[] {0,2,1,null,null,3}, 3, 3};
		/**
		 *    0
		 *  2   1
		 * N N 3
		 */
		Object[] tc4 = new Object[] {Arrays.asList(3), new Integer[] {0,1,null,null,2,null,3,null,4}, 3, 0};
		Object[] tc5 = new Object[] {Arrays.asList(6,1), new Integer[] {0,1,null,3,2,6,null,5,4}, 3, 1};
		
		Object[][] tcs = new Object[][] {tc5, tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
			tc[2] = TreeNode.find((TreeNode) tc[1], (Integer) tc[2]); 
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public List<Integer> apply(TreeNode root, TreeNode target, Integer k) {
			List<Integer> res = new ArrayList<>();
			_find(res, root, target, k);
			return res;
		}
		void _apply(List<Integer> acc, TreeNode node, TreeNode target, int distance) {
			if (node == null) return;
			if (distance == 0) {
				acc.add(node.val);
				return;
			} else if (distance < 0) {
				return;
			}
			_apply(acc, node.left, target, distance - 1);
			_apply(acc, node.right, target, distance - 1);
		}
		int _find(List<Integer> res, TreeNode node, TreeNode target, int k) {
			if (node == null) return -1;
			if (target.val == node.val) {
				_apply(res, node, target, k);
				return 1;
			}
			int left = _find(res, node.left, target, k);
			if (left != -1) {
				if (left == k) {
					res.add(node.val);
				}
				_apply(res, node.right, target, k - (left + 1));
				return left + 1;
			}
			int right = _find(res, node.right, target, k);
			if (right != -1) {
				if (right == k) {
					res.add(node.val);
				}
				_apply(res, node.left, target, k - (right + 1));
				return right + 1;
			}
			return -1;
		}
	}
	public interface Problem extends TriFunction<TreeNode, TreeNode, Integer, List<Integer>> {
		
	}
}
