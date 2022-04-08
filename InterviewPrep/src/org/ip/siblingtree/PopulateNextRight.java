package org.ip.siblingtree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node/">LC: 116</a>
 * EPI 2018: 9.16
 */
public class PopulateNextRight {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {1,null,2,3,null,4,5,6,7,null}, new Integer[] {1,2,3,4,5,6,7}};
		Object[][] tcs = new Object[][] {tc1};
		for (Object[] tc : tcs) {
			tc[1] = Node.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public Node apply(Node root) {
			if (root == null) return root;
			for (Node head = root; head != null; head = head.left) {
				for (Node n = head; n != null; n = n.next) {
					if (n.left != null) {
						n.left.next = n.right;
					}
					if (n.next != null && n.right != null) {
						n.right.next = n.next.left;
					}
				}
			}
			return root;
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public Node apply(Node t) {
			if (t == null) return t;
			_apply(t.left, t.right);
			_apply(t.right, null);
			return t;
		}
		void _apply(Node l, Node r) {
			if (l == null) return;
			l.next = r;
			_apply(l.left, l.right);
			_apply(l.right, r != null ? r.left : null);
		}
		
	}
	static class Solver implements Problem {

		@Override
		public Node apply(Node t) {
			if (t == null) return t;
			Deque<Node> deque = new ArrayDeque<>();
			deque.addLast(t);
			while (!deque.isEmpty()) {
				int size = deque.size();
				Node prev = null;
				while (size-- > 0) {
					Node node = deque.remove();
					if (node.left != null) {
						deque.addLast(node.left);
					}
					if (node.right != null) {
						deque.addLast(node.right);
					}
					if (prev != null) {
						prev.next = node;
					}
					prev = node;
				}
			}
			return t;
		}
		
	}
	interface Problem extends Function<Node, Node> {
		
	}
}
