package org.ip.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/binary-tree-right-side-view/">LC: 199</a>
 */
public class BinaryTreeRightSideView {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,3,4}, new Integer[] {1,2,3,null,5,null,4}};
		Object[] tc2 = new Object[] {new int[] {1,3}, new Integer[] {1,null,3}};
		Object[] tc3 = new Object[] {new int[] {}, new Integer[] {}};
		Object[] tc4 = new Object[] {new int[] {1,3,5}, new Integer[] {1,2,3,5}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		private final static TreeNode SENTINEL = new TreeNode(0);
		@Override
		public List<Integer> apply(TreeNode t) {
			if (t == null) return new ArrayList<>();
			List<Integer> res = new ArrayList<>();
			bfs((current, height) -> {
				if (res.size() <= height) {
					res.add(current.val);
				} else {
					res.set(height, current.val);
				}
			}, t);
			return res;
		}
		void bfs(BiConsumer<TreeNode, Integer> consumer, TreeNode t) {
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(t);
			deque.add(SENTINEL);
			int height = 0;
			while (!deque.isEmpty()) {
				TreeNode current = deque.remove();
				if (current == SENTINEL) {
					if (!deque.isEmpty()) {
						deque.add(SENTINEL);
					}
					height++;
					continue;
				}
				consumer.accept(current, height);
				if (current.left != null) {
					deque.add(current.left);
				}
				if (current.right != null) {
					deque.add(current.right);
				}
			}
		}
	}
	public interface Problem extends Function<TreeNode, List<Integer>> {
		
	}
}
