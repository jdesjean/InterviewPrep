package org.ip.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;
import org.ip.tree.DeleteNodeReturnForest.Problem;
import org.ip.tree.DeleteNodeReturnForest.Solver;
import org.ip.tree.DeleteNodeReturnForest.Solver2;
import org.ip.tree.DeleteNodeReturnForest.Solver3;

/**
 * <a href="https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/">LC: 103</a>
 */
public class ZigZagLevelOrder {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[][] {{3},{20,9},{15,7}}, new Integer[] {3,9,20,null,null,15,7}};
		Object[] tc2 = new Object[] {new Integer[][] {{1}}, new Integer[] {1}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		
		@Override
		public List<List<Integer>> apply(TreeNode t) {
			List<List<Integer>> res = new ArrayList<>();
			if (t == null) return res;
			Deque<TreeNode> deque = new ArrayDeque<>();
			deque.add(t);
			boolean zig = true;
			while (!deque.isEmpty()) {
				int size = deque.size();
				LinkedList<Integer> current = new LinkedList<>();
				Consumer<Integer> add = zig ? current::add : current::addFirst;
				while (size-- > 0) {
					TreeNode n = deque.remove();
					add.accept(n.val);
					for (TreeNode child : Arrays.asList(n.left, n.right)) {
						if (child == null) continue;
						deque.add(child);
					}
				}
				res.add(current);
				zig ^= true;
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		
		@Override
		public List<List<Integer>> apply(TreeNode t) {
			List<List<Integer>> res = new ArrayList<>();
			if (t == null) return res;
			Deque<TreeNode> deque = new ArrayDeque<>();
			deque.add(t);
			boolean zig = true;
			while (!deque.isEmpty()) {
				int size = deque.size();
				List<Integer> current = new ArrayList<>(size * 2);
				while (size-- > 0) {
					TreeNode n = deque.remove();
					current.add(n.val);
					for (TreeNode child : Arrays.asList(n.left, n.right)) {
						if (child == null) continue;
						deque.add(child);
					}
				}
				if (!zig) {
					Collections.reverse(current);
				}
				res.add(current);
				zig ^= true;
			}
			return res;
		}
		
	}
	interface Problem extends Function<TreeNode, List<List<Integer>>> {
		
	}
}
