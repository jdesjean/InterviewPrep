package org.ip.tree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/sum-root-to-leaf-numbers/">LC: 129</a>
 */
public class SumRootToLeafNumbers {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {25, new Integer[] {1,2,3}};
		Object[] tc2 = new Object[] {1026, new Integer[] {4,9,0,5,1}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[])tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Morris() };
		Test.apply(solvers, tcs);
	}
	/*
	 * O(N) time, O(1) space
	 */
	static class Morris implements Problem {

		@Override
		public int applyAsInt(TreeNode root) {
			int rootToLeaf = 0, currNumber = 0;
		    int steps;
		    TreeNode predecessor;

		    while (root != null) {
		      // If there is a left child,
		      // then compute the predecessor.
		      // If there is no link predecessor.right = root --> set it.
		      // If there is a link predecessor.right = root --> break it.
		      if (root.left != null) {
		        // Predecessor node is one step to the left
		        // and then to the right till you can.
		        predecessor = root.left;
		        steps = 1;
		        while (predecessor.right != null && predecessor.right != root) {
		          predecessor = predecessor.right;
		          ++steps;
		        }

		        // Set link predecessor.right = root
		        // and go to explore the left subtree
		        if (predecessor.right == null) {
		          currNumber = currNumber * 10 + root.val;
		          predecessor.right = root;
		          root = root.left;
		        }
		        // Break the link predecessor.right = root
		        // Once the link is broken,
		        // it's time to change subtree and go to the right
		        else {
		          // If you're on the leaf, update the sum
		          if (predecessor.left == null) {
		            rootToLeaf += currNumber;
		          }
		          // This part of tree is explored, backtrack
		          for(int i = 0; i < steps; ++i) {
		            currNumber /= 10;
		          }
		          predecessor.right = null;
		          root = root.right;
		        }
		      }

		      // If there is no left child
		      // then just go right.
		      else {
		        currNumber = currNumber * 10 + root.val;
		        // if you're on the leaf, update the sum
		        if (root.right == null) {
		          rootToLeaf += currNumber;
		        }
		        root = root.right;
		      }
		    }
		    return rootToLeaf;
		  }
		
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(TreeNode value) {
			return dfs(value, 0);
		}
		int dfs(TreeNode current, int parent) {
			if (current == null) {
				return 0;
			}
			int val = parent * 10 + current.val;
			if (current.left == null && current.right == null) {
				return val;
			} else {
				return dfs(current.left, val) + dfs(current.right, val); 
			}
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(TreeNode value) {
			AtomicInteger ai = new AtomicInteger();
			dfs(value, 0, ai);
			return ai.get();
		}
		void dfs(TreeNode current, int parent, AtomicInteger sum) {
			if (current == null) {
				return;
			}
			int val = parent * 10 + current.val;
			dfs(current.left, val, sum);
			dfs(current.right, val, sum);
			if (current.left == null && current.right == null) {
				sum.addAndGet(val);
			}
		}
		
	}
	interface Problem extends ToIntFunction<TreeNode> {
		
	}
}
