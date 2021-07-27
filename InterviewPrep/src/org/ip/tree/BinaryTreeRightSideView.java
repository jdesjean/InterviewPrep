package org.ip.tree;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/binary-tree-right-side-view/">LC: 199</a>
 */
public class BinaryTreeRightSideView {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {new int[] {1,3,4}, new Integer[] {1,2,3,null,5,null,4}};
		Object[] tc2 = new Object[] {new int[] {1,3}, new Integer[] {1,null,3}};
		Object[] tc3 = new Object[] {new int[] {}, new Integer[] {}};
		Object[] tc4 = new Object[] {new int[] {1,3,5}, new Integer[] {1,2,3,5}};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			Utils.print((int[])tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(TreeNode.class).newInstance(TreeNode.fromBfs((Integer[]) tc[1]));
				System.out.print(function.get());
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private TreeNode root;
		private final static TreeNode SENTINEL = new TreeNode(0);

		public Solver(TreeNode root) {
			this.root = root;
		}

		@Override
		public List<Integer> get() {
			if(root == null) return new ArrayList<>();
			List<Integer> res = new ArrayList<Integer>();
			Deque<TreeNode> queue = new LinkedList<>();
			queue.add(root);
			queue.add(SENTINEL);
			TreeNode prev = null;
			while (!queue.isEmpty()) {
				TreeNode current = queue.removeFirst();
				if (current == SENTINEL) {
					if (!queue.isEmpty()) {
						queue.addLast(SENTINEL);
					}
					res.add(prev.val);
					continue;
				}
				if (current.left != null) {
					queue.addLast(current.left);
				}
				if (current.right != null) {
					queue.addLast(current.right);
				}
				prev = current;
			}
			return res;
		}
		
	}
	public interface Problem extends Supplier<List<Integer>> {
		
	}
}
