package org.ip.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/">LC: 1650</a>
 */
public class LCAIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, 5, 1, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc2 = new Object[] {5, 5, 4, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc3 = new Object[] {1, 1, 2, new Integer[] {1,2}};
		Object[] tc4 = new Object[] {3, 8, 4, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[3] = Node.fromBfs((Integer[])tc[3]);
			tc[1] = Node.find((Node) tc[3], (int) tc[1]);
			tc[2] = Node.find((Node) tc[3], (int) tc[2]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public Node apply(Node p, Node q) {
			if (p == null || q == null) return null;
			if (p.val == q.val) return p;
			int pHeight = height(p);
			int qHeight = height(q);
			if (pHeight > qHeight) {
				p = up(p, pHeight - qHeight);
			} else if (pHeight < qHeight) {
				q = up(q, qHeight - pHeight);
			}
			return up(p, q);
		}
		
		Node up(Node p, Node q) {
			if (p == null || q == null) return null;
			if (p.val == q.val) {
				return p;
			}
			return up(p.parent, q.parent);
		}
		
		Node up(Node node, int h) {
			for (int i = 0; i < h; i++) {
				node = node.parent;
			}
			return node;
		}
		
		int height(Node node) {
			if (node == null) return 0;
			return height(node.parent) + 1;
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
