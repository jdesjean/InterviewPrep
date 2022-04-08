package org.ip.tree;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/closest-leaf-in-a-binary-tree/">LC: 742</a>
 */
public class ClosestLeafBinaryTree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new Integer[] {1,3,2}, 1};
		Object[] tc2 = new Object[] {1, new Integer[] {1}, 1};
		Object[] tc3 = new Object[] {3, new Integer[] {1,2,3,4,null,null,null,5,null,6}, 2};
		Object[] tc4 = new Object[] {10, new Integer[] {1,2,3,null,null,4,5,6,null,null,7,8,9,10}, 7};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	/*
	 * When distanceToK + closest < min(closest), closest must be in child without k
	 */
	static class Solver implements Problem {

		@Override
		public int applyAsInt(TreeNode root, Integer k) {
			LeafDistance closest = new LeafDistance();
			closest(root, k, closest);
			return closest.val;
		}
		/**
		 * 
		 * @param root
		 * @param k
		 * @param res: 0: closest leaf distance, 1: closest leaf
		 * @return 0: distance from K, 1: closest leaf distance, 2: closest leaf
		 */
		KDistance closest(TreeNode root, int k, LeafDistance res) {
			if (root == null) {
				return new KDistance();
			}
			KDistance left = closest(root.left, k, res);
			KDistance right = closest(root.right, k, res);
			KDistance closest = left.leaf.distance < right.leaf.distance ? left : right;
			closest.distance = root.val == k ? 0 : Math.min(left.distance, right.distance);
			if (closest.leaf.distance != Integer.MAX_VALUE) { // found leaf
				closest.leaf.distance++;
			} else { // leaf
				closest.leaf.distance = 1;
				closest.leaf.val = root.val;
			}
			if (closest.distance != Integer.MAX_VALUE) { // found k
				closest.distance++;
				int closestFromK = closest.distance + closest.leaf.distance;
				if (closestFromK < res.distance) {
					res.distance = closestFromK;
					res.val = closest.leaf.val;
				}
			}
			return closest;
		}
		static class KDistance {
			int distance = Integer.MAX_VALUE;
			LeafDistance leaf = new LeafDistance();
		}
		static class LeafDistance {
			int distance = Integer.MAX_VALUE;
			int val;
		}
	}
	static class Solver3 implements Problem {

		@Override
		public int applyAsInt(TreeNode t, Integer u) {
			int[] ans = new int[2]; // 0 -> minimum distance between k and a candidate leaf, 1 -> the candidate leaf
	        ans[0] = Integer.MAX_VALUE;
	        traverse(t, u, ans);
	        return ans[1];
		}
		private int[] traverse(TreeNode root, int k, int[] ans) {
	        if (root == null) {
	            return new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, -1};
	        }
	        
	        if (root.left == null && root.right == null) {
	            int val;
	            if (root.val == k) {
	                val = 1;
	                ans[0] = 1;
	                ans[1] = root.val;
	            } else {
	                val = Integer.MAX_VALUE;
	            }
	            return new int[]{val, 1, root.val};
	        }
	        
	        int[] left = traverse(root.left, k, ans);
	        int[] right = traverse(root.right, k, ans);
	        
	        int[] curr = new int[3]; 
			// 0 -> distance from the current node to k if it is an ancestor node
			// 1 -> minimum distance from the current node to the closest leaf in the subtree rooted with the current node
			// 2 -> the closest leaf in the subtree rooted with the current node
	        curr[0] = Integer.MAX_VALUE;
	        
	        if (left[0] < right[0]) {
	            curr[0] = left[0] + 1;
	        } else if (left[0] > right[0]) {
	            curr[0] = right[0] + 1;
	        } else if (root.val == k) {
	            curr[0] = 1;
	        }
	        
	        if (left[1] < right[1]) {
	            curr[1] = left[1] + 1;
	            curr[2] = left[2];
	        } else {
	            curr[1] = right[1] + 1;
	            curr[2] = right[2];
	        }
	        
	        if (curr[0] != Integer.MAX_VALUE) {
	            if (curr[0] + curr[1] < ans[0]) {
	                ans[0] = curr[0] + curr[1];
	                ans[1] = curr[2];
	            }
	        }
	        
	        return curr;
	    }
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(TreeNode t, Integer u) {
			int[] res = new int[] {Integer.MAX_VALUE, 0};
			closest(t, u, res);
			return res[1];
		}
		/**
		 * 
		 * @param t
		 * @param k
		 * @param res: 0: closest leaf distance, 1: closest leaf
		 * @return 0: distance from K, 1: closest leaf distance, 2: closest leaf
		 */
		int[] closest(TreeNode t, int k, int[] res) {
			if (t == null) {
				return new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, 0};
			}
			int[] left = closest(t.left, k, res);
			int[] right = closest(t.right, k, res);
			int[] cur = left[1] < right[1] ? left : right;
			int min = Math.min(left[0], right[0]);
			cur[0] = t.val == k ? 0 : min;
			if (cur[0] != Integer.MAX_VALUE) {
				cur[0]++;
			} 
			if (min == Integer.MAX_VALUE) {
				cur[1] = 1;
				cur[2] = t.val;
			}
			if (cur[1] < res[1]) {
				res[0] = cur[1];
				res[1] = cur[2];
			} else if (cur[0] + cur[1] < res[0]) {
				res[0] = cur[0] + cur[1];
				res[1] = cur[2];
			}
			return cur;
		}
	}
	interface Problem extends ToIntBiFunction<TreeNode, Integer> {
		
	}
}
