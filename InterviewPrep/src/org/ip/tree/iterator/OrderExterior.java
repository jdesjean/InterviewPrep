package org.ip.tree.iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;

//EPI: 10.15
//left side pre order
//bottom in order
//right side post order
//Time: O(n), Space: log(n)
public class OrderExterior<T> implements Iterator<Node<T>>{

	private Tree<T> tree;
	Node<T> current;
	Deque<Node> stack = new LinkedList<Node>();
	public enum State {
		LEFT {
			@Override
			public Node getNext(Node node, Deque<Node> stack) {
				Node next;
				Node left = node.getLeft();
				Node right = node.getRight();
				if (left != null) {
					stack.push(next = left);
				} else if (right != null) {
					stack.push(next = right);
				} else {
					next = null;
				}
				return next != null && !next.isLeaf() ? next : null;
			}

			@Override
			public State getNextState() {
				return BOTTOM;
			}
		},BOTTOM {
			@Override
			public Node getNext(Node node, Deque<Node> stack) {
				if (stack.isEmpty()) return null;
				do {
					node = stack.pop();
					Node right = node.getRight();
					if (right != null) {
						stack.push(right);
						Node left = right.getLeft(); 
						while (left != null) {
							stack.push(left);
							left = left.getLeft();
						}
					}
				} while (!node.isLeaf() && !stack.isEmpty());
				
				return node.isLeaf() ? node : null;
			}

			@Override
			public State getNextState() {
				return RIGHT;
			}
		},RIGHT {
			@Override
			public Node getNext(Node node, Deque<Node> stack) {
				if (node == null) return null;
				if (node.isRoot()) {
					do {
						Node right = node.getRight();
						Node left = node.getLeft();
						if (right != null) {
							node = right;
						} else if (left != null) {
							node = left;
						} else {
							node = null;
						}
						if (node != null && !node.isLeaf()) stack.push(node);
					} while (node != null);
				}
				return !stack.isEmpty() ? stack.pop() : null;
			}

			@Override
			public State getNextState() {
				return null;
			}
		};
		public abstract Node getNext(Node node, Deque<Node> stack);
		public abstract State getNextState();
	}
	State state = State.LEFT;
	public OrderExterior(Tree<T> tree) {
		this.tree = tree;
		stack.push(tree.root());
		current = tree.root();
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}
	
	@Override
	public Node<T> next() {
		Node<T> prev = current;
		if (prev == null || state == null) return null;
		
		Node<T> next = state.getNext(prev,stack);
		while (next == null && state != null) {
			state = state.getNextState();
			next = state != null ? state.getNext(tree.root(),stack) : null;
		}
		current = next;
		return prev;
	}

}
