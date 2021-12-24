package org.ip.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/">LC: 1650</a>
 * EPI 2018: 12.4
 */
public class LCAIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, 5, 1, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc2 = new Object[] {5, 5, 4, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc3 = new Object[] {1, 1, 2, new Integer[] {1,2}};
		Object[] tc4 = new Object[] {3, 8, 4, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc5 = new Object[] {-1, 3, 8, new Integer[] {-1,0,3,-2,4,null,null,8}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		for (Object[] tc : tcs) {
			tc[3] = Node.fromBfs((Integer[])tc[3]);
			tc[1] = Node.find((Node) tc[3], (int) tc[1]);
			tc[2] = Node.find((Node) tc[3], (int) tc[2]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new SolverLoop(), new SolverHash()};
		Test.apply(solvers, tcs);
	}
	public static class SolverHash implements Problem {

		@Override
		public Node apply(Node p, Node q) {
			Set<Node> set = new HashSet<>();
			boolean bp = false;
			while (p != null && q != null && (bp = set.add(p)) && set.add(q)) {
				p = p.parent;
				q = q.parent;
			}
			if (p != null && q != null) {
				return bp ? q : p;
			}
			p = Objects.requireNonNullElse(p, q);
			while (set.add(p)) {
				p = p.parent;
			}
			return p;
		}
		
	}
	public static class SolverLoop implements Problem {

		@Override
		public Node apply(Node p, Node q) {
			Node a = p, b = q;
			while (a != b) {
				a = a.parent != null ? a.parent : q;
				b = b.parent != null ? b.parent : p;
			}
			return a;
		}
		
	}
	public static class Solver implements Problem {

		@Override
		public Node apply(Node p, Node q) {
			int ph = height(p);
			int qh = height(q);
			int dh = ph - qh;
			p = up(p, dh);
			q = up(q, -dh);
			return up(p, q);
		}
		Node up(Node p, Node q) {
			while (p != q) {
				p = p.parent;
				q = q.parent;
			}
			return p;
		}
		Node up(Node p, int h) {
			while (h > 0) {
				p = p.parent;
				h--;
			}
			return p;
		}
		int height(Node p) {
			int h = 0;
			while (p != null) {
				h++;
				p = p.parent;
			}
			return h;
		}
	}
	public interface Problem extends BiFunction<Node, Node, Node> {
		
	}
	public static class Node {
		public int val;
	    public Node left;
	    public Node right;
	    public Node parent;
	    public Node(int val) {
	    	this.val = val;
	    }
	    @Override
	    public String toString() {
	    	return Integer.toString(val);
	    }
	    public static Node fromBfs(Integer[] values) {
			if (values == null || values.length == 0 || values[0] == null) return null;
			List<Node> parents = new ArrayList<>();
			Node root = new Node(values[0]);
			parents.add(root);
			for (int i = 1; i < values.length;) {
				List<Node> childs = new ArrayList<>(parents.size() * 2);
				for (Node parent : parents) {
					Integer left = i < values.length ? values[i++] : null;
					Integer right = i < values.length ? values[i++] : null;
					if (left != null) {
						parent.left = new Node(left);
						childs.add(parent.left);
						parent.left.parent = parent;
					}
					if (right != null) {
						parent.right = new Node(right);
						childs.add(parent.right);
						parent.right.parent = parent;
					}
				}
				parents = childs;
			}
			return root;
		}
	    static Node find(Node node, int val) {
	    	if (node == null) return null;
	    	if (node.val == val) return node;
	    	Node left = find(node.left, val);
	    	Node right = find(node.right, val);
	    	if (left != null) return left;
	    	return right;
	    }
	}
}
