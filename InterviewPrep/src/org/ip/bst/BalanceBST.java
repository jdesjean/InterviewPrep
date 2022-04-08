package org.ip.bst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.ip.Test;
import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/balance-a-binary-search-tree/">LC:
 * 1382</a>
 */
public class BalanceBST {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Integer[] { 3, 2, 4, 1, null, null, null, null, null },
				new Integer[] { 1, null, 2, null, 3, null, 4 } };
		Object[] tc2 = new Object[] { new Integer[] { 3, 2, 4, 1, null, null, null, null, null },
				new Integer[] { 4, 3, null, 2, null, 1, null } };
		Object[] tc3 = new Object[] {
				new Integer[] { 5, 3, 8, 1, 4, 6, 9, null, null, null, null, null, null, null, null },
				new Integer[] { 5, 4, 6, 3, null, null, 8, 1, null, null, 9 } };
		Object[] tc4 = new Object[] { new Integer[] { 17, 5, 19, 4, 10, null, null, null, null, null, null },
				new Integer[] { 19, 10, null, 4, 17, null, 5 } };
		Object[] tc5 = new Object[] { new Integer[] { 12, 7, 15, 2, 11, 14, 17, 1, 3, 9, null, null, null, null, null, null, null, null, null, null, null },
				new Integer[] { 1, null, 15, 14, 17, 7, null, null, null, 2, 12, null, 3, 9, null, null, null, null, 11 } };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5 };
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver(), new SolverNSpace(), new SolverHSpace(), new SolverHSpace2() };
		Test.apply(solvers, tcs);
	}
	static class SolverHSpace2 implements Problem {

		@Override
		public TreeNode apply(TreeNode t) {
			if (t == null) return t;
			TreeNode grand = new TreeNode(0);
			grand.right = t;
			int length = _inOrder(grand);
 			return balance(grand, 0, length - 1);
		}
		int _inOrder(TreeNode grand) {
			int cnt = 0;
			var n = grand.right;
			while (n != null) {
				if (n.left != null) {
					var old_n = n;
					n = n.left;
					old_n.left = n.right;
					n.right = old_n;
					grand.right = n;
				} else {
					++cnt;
					grand = n;
					n = n.right;
				}
			}
			return cnt;
		}
		TreeNode balance(TreeNode grand, int left, int right) {
			if (left > right) return null;
			int mid = left + (right - left) / 2;
			TreeNode leftNode = balance(grand, left, mid - 1);
			TreeNode midNode = grand.right;
			grand.right = midNode.right;
			TreeNode rightNode = balance(grand, mid + 1, right);
			midNode.left = leftNode;
			midNode.right = rightNode;
			return midNode;
		}
		
	}
	static class SolverHSpace implements Problem {

		@Override
		public TreeNode apply(TreeNode t) {
			if (t == null) return t;
			AtomicReference<TreeNode> head = new AtomicReference<>();
			AtomicReference<TreeNode> tail = new AtomicReference<>();
			AtomicInteger length = new AtomicInteger();
			_inOrder(t, head, tail, length);
			return balance(head, 0, length.get() - 1);
		}
		void _inOrder(TreeNode t, AtomicReference<TreeNode> head, AtomicReference<TreeNode> tail, AtomicInteger length) {
			if (t == null) return;
			_inOrder(t.left, head, tail, length);
			length.incrementAndGet();
			TreeNode right = t.right;
			t.right = null;
			t.left = null;
			if (head.get() == null) {
				head.set(t);
				tail.set(t);
			} else {
				tail.get().right = t;
				tail.set(t);
			}
			_inOrder(right, head, tail, length);
		}
		TreeNode balance(AtomicReference<TreeNode> current, int left, int right) {
			if (left > right) return null;
			int mid = left + (right - left) / 2;
			TreeNode leftNode = balance(current, left, mid - 1);
			TreeNode midNode = current.get();
			current.set(current.get().right);
			TreeNode rightNode = balance(current, mid + 1, right);
			midNode.left = leftNode;
			midNode.right = rightNode;
			return midNode;
		}
		
	}
	static class SolverNSpace implements Problem {

		@Override
		public TreeNode apply(TreeNode t) {
			List<TreeNode> list = new ArrayList<TreeNode>();
			_inOrder(list, t);
			return balance(list);
		}
		void _inOrder(List<TreeNode> list, TreeNode t) {
			if (t == null) return;
			_inOrder(list, t.left);
			list.add(t);
			_inOrder(list, t.right);
		}
		TreeNode balance(List<TreeNode> list) {
			return balance(list, 0, list.size() - 1);
		}
		TreeNode balance(List<TreeNode> list, int left, int right) {
			if (left > right) return null;
			int mid = left + (right - left) / 2;
			TreeNode current = list.get(mid);
			TreeNode leftNode = balance(list, left, mid - 1);
			TreeNode rightNode = balance(list, mid + 1, right);
			current.left = leftNode;
			current.right = rightNode;
			return current;
		}
		
	}

	/**
	 * https://leetcode.com/problems/balance-a-binary-search-tree/discuss/541785/C%2B%2BJava-with-picture-DSW-O(n)orO(1)
	 */
	static class Solver implements Problem {

		@Override
		public TreeNode apply(TreeNode root) {
			TreeNode grand = new TreeNode(0);
			grand.right = root;
			int cnt = makeVine(grand);
			int m = (int) Math.pow(2, (int) (Math.log(cnt + 1) / Math.log(2))) - 1;
			compress(grand, cnt - m);
			for (m = m / 2; m > 0; m /= 2)
				compress(grand, m);
			return grand.right;
		}

		int makeVine(TreeNode grand) {
			int cnt = 0;
			var n = grand.right;
			while (n != null) {
				if (n.left != null) {
					var old_n = n;
					n = n.left;
					old_n.left = n.right;
					n.right = old_n;
					grand.right = n;
				} else {
					++cnt;
					grand = n;
					n = n.right;
				}
			}
			return cnt;
		}

		void compress(TreeNode grand, int m) {
			var n = grand.right;
			while (m-- > 0) {
				var old_n = n;
				n = n.right;
				grand.right = n;
				old_n.right = n.left;
				n.left = old_n;
				grand = n;
				n = n.right;
			}
		}
	}

	interface Problem extends Function<TreeNode, TreeNode> {

	}
}
