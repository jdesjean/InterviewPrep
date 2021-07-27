package org.ip.tree;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

// Google 10/03/19
// Find ancestors in genealogy (2 parents)
// What happens when both parents have same grandparent
public class CommonAncestors {
	public static class Solver2 implements Problem {
		private final static TreeNode SENTINEL = new TreeNode(0);
		@Override
		public Boolean apply(TreeNode r1, TreeNode r2) {
			if (r1 == null || r2 == null) return null;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(r1);
			deque.add(r2);
			deque.add(SENTINEL);
			Set<TreeNode> visited = new HashSet<>();
			while (!deque.isEmpty()) {
				TreeNode current = deque.remove();
				if (current == SENTINEL) {
					if (!deque.isEmpty()) {
						deque.addLast(SENTINEL);
					}
					continue;
				}
				if (!visited.add(current)) {
					return true;
				}
				if (current.left != null) {
					deque.addLast(current.left);
				}
				if (current.right != null) {
					deque.addLast(current.right);
				}
			}
			return false;
		}
		
	}
	public static class Solver implements Problem {

		@Override
		public Boolean apply(TreeNode r1, TreeNode r2) {
			Deque<TreeNode> d1 = new LinkedList<>();
			Deque<TreeNode> d2 = new LinkedList<>();
			d1.add(r1);
			d2.add(r2);
			Map<TreeNode, TreeNode> map = new HashMap<>();
			while (!d1.isEmpty() && !d2.isEmpty()) {
				TreeNode n1 = d1.remove();
				TreeNode n2 = d2.remove();
				TreeNode m1 = map.get(n1);
				if (m1 == r2) return true;
				TreeNode m2 = map.get(n2);
				if (m2 == r1) return true;
				map.put(n1, r1);
				map.put(n2, r2);
				if (n1.left != null) {
					d1.add(n1.left);
				}
				if (n1.right != null) {
					d1.add(n1.right);
				}
				if (n2.left != null) {
					d2.add(n2.left);
				}
				if (n2.right != null) {
					d2.add(n2.right);
				}
			}
			Deque<TreeNode> d = !d1.isEmpty() ? d1 : d2;
			while (!d.isEmpty()) {
				TreeNode n = d1.remove();
				if (n.left != null) {
					d.add(n.left);
				}
			}
			return false;
		}
		
	}
	public interface Problem extends BiFunction<TreeNode, TreeNode, Boolean> {
		
	}
}
