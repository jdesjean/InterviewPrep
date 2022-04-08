package org.ip.siblingtree;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/">LC: 117</a>
 */
public class PopulateNextRightII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {1,null,2,3,null,4,5,7,null}, new Integer[] {1,2,3,4,5,null,7}};
		Object[] tc2 = new Object[] {new Integer[] {3,null,9,20,null,15,7,null}, new Integer[] {3,9,20,null,null,15,7}};
		Object[] tc3 = new Object[] {new Integer[] {1,null,2,3,null,4,5,6,null,7,8,null}, new Integer[] {1,2,3,4,5,null,6,7,null,null,null,null,8}};
		Object[] tc4 = new Object[] {new Integer[] {-1,null,-7,9,null,-1,-7,null,8,-9,null}, new Integer[] {-1,-7,9,null,null,-1,-7,null,8,-9}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = Node.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Node apply(Node root) {
			if (root == null) return root;
			for (Node head = root; head != null; head = next(head)) {
				for (Node n = head; n != null; n = n.next) {
					if (n.left != null) {
						n.left.next = n.right;
					}
					Node next = next(n.next);
					Node left = n.right != null ? n.right : n.left;
					if (left != null) {
						left.next = next;
					}
				}
			}
			return root;
		}
		Node next(Node node) {
			for (; node != null; node = node.next) {
				Node next = node.left != null ? node.left : node.right;
				if (next != null) return next;
			}
			return null;
		}
	}
	interface Problem extends Function<Node, Node> {
		
	}
}
