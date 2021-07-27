package org.ip.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/">LC: 426</a>
 */
public class ConvertBSTToSortedToSortedCircularDoublyLinkedList {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {null, Node.from(null)};
		Object[] tc2 = new Object[] {new Integer[] {null}, Node.from(new Integer[] {null})};
		Object[] tc3 = new Object[] {new Integer[] {1,2}, Node.from(new Integer[] {2,1})};
		Object[] tc4 = new Object[] {new Integer[] {1,2}, Node.from(new Integer[] {1,null,2})};
		Object[] tc5 = new Object[] {new Integer[] {1,2,3}, Node.from(new Integer[] {2,1,3})};
		Object[] tc6 = new Object[] {new Integer[] {1,2,3,4,5,6,7}, Node.from(new Integer[] {4,2,6,1,3,5,7})};
		Object[] tc7 = new Object[] {new Integer[] {1,2,3,4,5}, Node.from(new Integer[] {4,2,5,1,3})};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6, tc7);
		Function<Node, Node>[] solvers = new Function[] {new Solver()};
		for (Object[] tc : tcs) {
			int length = tc[0] != null ? ((Integer[])tc[0]).length : 0;
			print(from((Integer[]) tc[0]), length);
			for (Function<Node, Node> solver : solvers) {
				System.out.print(":");
				print((Node) solver.apply((Node) tc[1]), length);
			}
			System.out.println();
		}
	}
	static void print(Node head, int length) {
		for (Node node = head; node != null && length > 0; node = node.right) {
			if (node.left != null) {
				System.out.print(",");
			}
			System.out.print(node.val);
			length--;
		}
	}
	static Node from(Integer[] values) {
		if (values == null) return null;
		Node root = new Node(0);
		Node current = root;
		for (Integer value : values) {
			Node next = value != null ? new Node(value) : null;
			current.right = next;
			if (next != null) {
				next.left = current;
			}
			current = next;
		}
		if (root.right != null) { // Circular
			current.right = root.right;
			root.right.left = current;
		}
		return root.right;
	}
	public static class Solver implements Function<Node, Node> {

		@Override
		public Node apply(Node t) {
			AtomicReference<Node> head = new AtomicReference<>();
			AtomicReference<Node> tail = new AtomicReference<>(); 
			_solve(t, head, tail);
			if (head.get() != null) { // circular
				head.get().left = tail.get();
				tail.get().right = head.get();
			}
			return head.get();
		}
		
		void _solve(Node node, AtomicReference<Node> head, AtomicReference<Node> tail) {
			if (node == null) return;
			_solve(node.left, head, tail);
			
			head.compareAndSet(null, node);
			if (tail.get() != null) {
				tail.get().right = node;
				node.left = tail.get();
			}
			tail.set(node);
			
			_solve(node.right, head, tail);
		}
		
	}
	public static class Node {
		public int val;
	    public Node left;
	    public Node right;

	    public Node() {}

	    public Node(int _val) {
	        val = _val;
	    }

	    public Node(int _val,Node _left,Node _right) {
	        val = _val;
	        left = _left;
	        right = _right;
	    }
	    public static Node from(Integer[] values) {
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
					}
					if (right != null) {
						parent.right = new Node(right);
						childs.add(parent.right);
					}
				}
				parents = childs;
			}
			return root;
		}
	}
}
