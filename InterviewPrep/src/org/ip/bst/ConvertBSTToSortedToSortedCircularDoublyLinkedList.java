package org.ip.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/">LC: 426</a>
 */
public class ConvertBSTToSortedToSortedCircularDoublyLinkedList {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {},  new Integer[] { null }};
		Object[] tc2 = new Object[] {new Integer[] {1,2}, new Integer[] { 2,1 } };
		Object[] tc3 = new Object[] {new Integer[] {1,2}, new Integer[] { 1,null,2 }};
		Object[] tc4 = new Object[] {new Integer[] {1,2,3}, new Integer[] {2,1,3}};
		Object[] tc5 = new Object[] {new Integer[] {1,2,3,4,5,6,7}, new Integer[] {4,2,6,1,3,5,7}};
		Object[] tc6 = new Object[] {new Integer[] {1,2,3,4,5}, new Integer[] {4,2,5,1,3}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5, tc6};
		for (Object[] tc : tcs) {
			tc[1] = Node.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		
		@Override
		public Node apply(Node t) {
			AtomicReference<Node> tail = new AtomicReference<>();
			AtomicReference<Node> head = new AtomicReference<>();
			new InOrderConsumer(new LList(head, tail)).inOrder(t);
			if (tail.get() != null) { // circular
				tail.get().right = head.get();
			}
			return head.get();
		}
		static class LList implements Consumer<Node> {
			private AtomicReference<Node> head;
			private AtomicReference<Node> tail;

			public LList(AtomicReference<Node> head, AtomicReference<Node> tail) {
				this.head = head;
				this.tail = tail;
			}

			@Override
			public void accept(Node node) {
				Node prev = tail.get();
				node.left = prev;
				if (prev != null) {
					prev.right = node;
				}
				tail.set(node);
				head.compareAndSet(null, node);
				head.get().left = node;
			}
			
		}
		static class InOrderConsumer {
			private Consumer<Node> consumer;
			public InOrderConsumer(Consumer<Node> consumer) {
				this.consumer = consumer;
			}
			public void inOrder(Node current) {
				if (current == null) return;
				inOrder(current.left);
				consumer.accept(current);
				inOrder(current.right);
			}
		}
	}
	interface Problem extends Function<Node, Node> {
		
	}
	public static class Node {
		 public int val;
		 public Node left;
		 public Node right;
		 public Node() {}
		 public Node(int val) { this.val = val; }
		 public Node(int val, Node left, Node right) {
			 this.val = val;
			 this.left = left;
			 this.right = right;
		 }
		 public static int len(Node node) {
			 if (node == null) return 0;
			 int len = 1;
			 for (Node current = node.right; current != node; len++, current = current.right) {}
			 return len;
		 }
		 public static Node find(Node node, int k) {
			 if (node == null) return null;
			 if (node.val == k) return node;
			 Node left = find(node.left, k);
			 if (left != null) return left;
			 return find(node.right, k);
		 }
		 public static Node fromBfs(Integer[] values) {
			if (values == null || values.length == 0 || values[0] == null) return null;
			List<Node> parents = new ArrayList<>();
			Node root = new Node(values[0]);
			parents.add(root);
			for (int i = 1; i < values.length;) {
				List<Node> childs = new ArrayList<Node>(parents.size() * 2);
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
		@Override
		public String toString() {
			Node node = this;
			Integer[] values = new Integer[Node.len(this)];
			for (int i = 0; i < values.length; i++) {
				values[i] = node.val;
				node = node.right;
			}
			return Arrays.toString(values);
		}
	}
}
