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
	Pair current;
	Deque<Pair> stack = new LinkedList<Pair>();
	private static class Pair {
		public Node node;
		public boolean isLeftMost;
		public boolean isRightMost;
		public Pair(Node node, boolean isLeftMost, boolean isRightMost) {this.node=node;this.isLeftMost=isLeftMost;this.isRightMost=isRightMost;}
	}
	private static Pair pair(Node node,boolean isLeftMost, boolean isRightMost) {
		return new Pair(node,isLeftMost,isRightMost);
	}
	public enum State {
		LEFT {
			@Override
			public Pair getNext(Deque<Pair> stack) {
				Pair next = null;
				Pair pair = stack.peek();
				Node left = pair.node.getLeft();
				Node right = pair.node.getRight();
				if ((!pair.isRightMost || pair.isLeftMost)) stack.pop();
				if (right != null) {
					stack.push(next = pair(right,left == null && pair.isLeftMost,pair.isRightMost));
				}
				if (left != null) {
					stack.push(next = pair(left,pair.isLeftMost,right == null && pair.isRightMost));
				}
				return next.isLeftMost && next.node.isLeaf() ? null : next;
			}

			@Override
			public State getNextState() {
				return BOTTOM;
			}
		},BOTTOM {
			@Override
			public Pair getNext(Deque<Pair> stack) {
				Pair next = null;
				Pair pair = null;
				do {
					next = null;
					pair = stack.peek();
					if (!pair.isRightMost || pair.isLeftMost) stack.pop();
					Node left = pair.node.getLeft();
					Node right = pair.node.getRight();
					if (right != null) {
						stack.push(next = pair(right,left == null && pair.isLeftMost,pair.isRightMost));
					}
					if (left != null) {
						stack.push(next = pair(left,pair.isLeftMost,right == null && pair.isRightMost));
					}
				} while (pair.isRightMost && next != null);
				return pair.isRightMost && pair.node.isLeaf() ? null : pair;
			}

			@Override
			public State getNextState() {
				return RIGHT;
			}
		},RIGHT {
			@Override
			public Pair getNext(Deque<Pair> stack) {
				return !stack.isEmpty() ? stack.pop() : null;
			}

			@Override
			public State getNextState() {
				return null;
			}
		};
		public abstract Pair getNext(Deque<Pair> stack);
		public abstract State getNextState();
	}
	State state = State.LEFT;
	public OrderExterior(Tree<T> tree) {
		this.tree = tree;
		stack.push(pair(tree.root(),true,true));
		current = pair(tree.root(),true,true);
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}
	
	@Override
	public Node<T> next() {
		Pair prev = current;
		if (prev == null || state == null) return null;
		
		Pair next = state.getNext(stack);
		while (next == null && state != null) {
			state = state.getNextState();
			next = state != null ? state.getNext(stack) : null;
		}
		current = next;
		return prev.node;
	}

}
