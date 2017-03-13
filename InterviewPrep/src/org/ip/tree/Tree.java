package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Tree<T extends Comparable<T>> {
	private Node<T> root;
	public static void main(String[] s) {
		testMerge();
	}
	public static void testMerge() {
		Tree<Integer> tree = small();
		tree.merge(big());
		Iterator<Node>[] iterators = new Iterator[]{tree.inOrderIterator(), tree.bfsIterator(), tree.new PostOrderIterator(), tree.new PreOrderIterator()};
		for (int i = 0; i < iterators.length; i++) {
			for (Iterator<Node> iterator = iterators[i]; iterator.hasNext();) {
				System.out.println(iterator.next().value);
			}
			System.out.println("***");
		}
		System.out.println(tree.new IterativeIsBSTExecutor().execute());
	}
	public static void testBST() {
		Tree[] trees = new Tree[]{BST(), nonBST()};
		for (Tree tree : trees) {
			Executor[] executors = new Executor[]{tree.new RecursiveIsBSTExecutor(), tree.new IterativeIsBSTExecutor()};
			for (int i = 0; i < executors.length; i++) {
				System.out.println(executors[i].execute());
			}
			System.out.println("***");
		}
	}
	public static void testIterators() {
		Tree[] trees = new Tree[]{BST(), nonBST()};
		for (Tree tree : trees) {
			Iterator<Node>[] iterators = new Iterator[]{tree.inOrderIterator(), tree.bfsIterator(), tree.new PostOrderIterator(), tree.new PreOrderIterator()};
			for (int i = 0; i < iterators.length; i++) {
				for (Iterator<Node> iterator = iterators[i]; iterator.hasNext();) {
					System.out.println(iterator.next().value);
				}
				System.out.println("***");
			}
		}
	}
	public Tree(Node<T> root) {
		this.root=root;
	}
	public static Tree<Integer> small() {
		Node<Integer> root = new Node<Integer>(2, new Node<Integer>(1), new Node<Integer>(3));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> big() {
		Node<Integer> root = new Node<Integer>(7, new Node<Integer>(6), new Node<Integer>(8));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> BST() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, new Node<Integer>(1), null), new Node<Integer>(7, new Node<Integer>(6), new Node<Integer>(8, null, new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> nonBST() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, new Node<Integer>(1), null), new Node<Integer>(7, new Node<Integer>(7), new Node<Integer>(8, null, new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}
	public void merge(Tree<T> tree) {
		Node<T> head1 = this.toLinkedList();
		Node<T> head2 = tree.toLinkedList();
		head1 = mergeLinkedList(head1,head2);
		int size = sizeLinkedList(head1);
		root = bstify(new Wrapper(head1),size);
	}
	public Node<T> toLinkedList() {
		Node<T> head = null, prev = null;
		for (Iterator<Node<T>> iterator = inOrderIterator(); iterator.hasNext();) {
			Node<T> current = iterator.next();
			if (head == null) head = current;
			current.childs[0] = prev;
			if (prev != null) prev.childs[1] = current;
			prev = current;
		}
		prev.childs[1] = null;
		return head;
	}
	public class Wrapper {
		public Node<T> node;
		public Wrapper(Node<T> node){this.node=node;}
	}
	private Node<T> advance(Node<T> head, int step) {
		Node<T> current = head; 
		for (; current != null && step > 0; current = current.getRight(), step--) {}
		return current;
	}
	private Node<T> bstify(Wrapper head, int size) {
		if (size <= 0) return null;
		if (size == 1) {
			Node<T> root = head.node;
			head.node = head.node.childs[1];
			root.childs[0] = null;
			root.childs[1] = null;
			return root;
		}
		int leftSize = size/2;
		int rightSize = size-leftSize - 1;
		Node<T> left = bstify(head,leftSize);
		Node<T> root = head.node;
		head.node = head.node.getRight();
		Node<T> right = bstify(head,rightSize);
		root.childs[0] = left;
		root.childs[1] = right;
		return root;
	}
	private int sizeLinkedList(Node<T> root) {
		int count = 0;
		for (Node<T> current = root; current != null; current = current.getRight()) {
			count++;
		}
		return count;
	}
	private Node<T> mergeLinkedList(Node<T> root1, Node<T> root2) {
		Node<T> head = smallest(root1,root2);
		Node<T> largest = head == root1 ? root2 : root1; 
		for (Node<T> current1 = head != null ? head.getRight() : head, current2 = largest, prev = head; current1 != null || current2 != null;) {
			Node<T> smallest = smallest(current1,current2);
			largest = smallest == current1 ? current2 : current1;
			prev.childs[1] = smallest;
			current1 = smallest.childs[1];
			current2 = largest;
			prev = smallest; 
		}
		return head;
	}
	private void toTree(Node<T> root1, Node<T> root2) {
		
	}
	private Node<T> smallest(Node<T> a, Node<T> b) {
		if (b == null || (a != null && a.compareTo(b) <= 0)) return a;
		else return b;
	}
	public interface BooleanVisitor<T extends Comparable<T>> {
		public boolean visit(Node<T> node);
	}
	public interface Executor {
		public boolean execute();
	}
	public class RecursiveIsBSTExecutor implements Executor {

		@Override
		public boolean execute() {
			return isBST(root,null,null);
		}
		private boolean isBST(Node<T> node, Node<T> left, Node<T> right) {
			if (node == null) return true;
			if ((left == null && right == null)
					|| (left == null && node.compareTo(right) < 0)
					|| (right == null && node.compareTo(left) > 0)
					|| (node.compareTo(left) > 0 && node.compareTo(right) < 0)) {
				return isBST(node.getLeft(), left, node) && isBST(node.getRight(), node, right);
			} else  {
				return false;
			}
		}
	}
	public class IterativeIsBSTExecutor implements Executor {
		@Override
		public boolean execute() {
			Node<T> previous = null;
			for (Iterator<Node<T>> iterator = inOrderIterator(); iterator.hasNext();) {
				Node<T> prev = previous;
				previous = iterator.next();
				if (prev != null && prev.compareTo(previous) >= 0) return false;
			}
			return true;
		}
		
	}
	public class RecursivePreOrderExecutor implements Executor{
		private BooleanVisitor<T> reducer;
		public RecursivePreOrderExecutor(BooleanVisitor<T> reducer) {
			this.reducer = reducer;
		}
		@Override
		public boolean execute() {
			if (root == null) return true;
			return inOrder(reducer,root);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current) {
			if (!visitor.visit(current)) return false;
			for (Node<T> child : current.childs) {
				if (!inOrder(visitor,child)) return false;
			}
			return true;
		}
	}
	public class RecursiveInOrderExecutor implements Executor{
		private BooleanVisitor<T> reducer;
		public RecursiveInOrderExecutor(BooleanVisitor<T> reducer) {
			this.reducer = reducer;
		}
		@Override
		public boolean execute() {
			if (root == null) return true;
			return inOrder(reducer,root);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current) {
			int mid = current.childs.length/2;
			for (int i = 0; i < current.childs.length; i++) {
				Node<T> child = current.childs[i];
				if (i < mid) {
					if (!inOrder(visitor,child)) return false;
				}
				if (!visitor.visit(child)) return false;
				if (i >= mid)  {
					if (!inOrder(visitor,child)) return false;
				}
			}
			return true;
		}
	}
	public class RecursivePostOrderExecutor implements Executor{
		private BooleanVisitor<T> reducer;
		public RecursivePostOrderExecutor(BooleanVisitor<T> reducer) {
			this.reducer = reducer;
		}
		@Override
		public boolean execute() {
			if (root == null) return true;
			return inOrder(reducer,root);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current) {
			for (Node<T> child : current.childs) {
				if (!inOrder(visitor,child)) return false;
			}
			return visitor.visit(current); 
		}
	}
	private class PreOrderIterator  implements Iterator<Node<T>> {
		Deque<Node<T>> stack = new LinkedList<Node<T>>();
		public PreOrderIterator(){
			if (root != null) stack.push(root);
		}
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public Node<T> next() {
			Node<T> current = stack.pop();
			if (current.getRight() != null) stack.push(current.getRight());
			if (current.getLeft() != null) stack.push(current.getLeft());
			return current;
		}
		
	}
	private class PostOrderIterator implements Iterator<Node<T>> {
		Deque<Node<T>> stack = new LinkedList<Node<T>>();
		Node<T> prev;
		public PostOrderIterator(){
			pushLeft(root);
		}
		public void pushLeft(Node<T> start) {
			for (Node<T> current = start; current != null;current = current.getLeft()) {
				stack.push(current);
			}
		}
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public Node<T> next() {
			Node<T> peek = stack.peek();
			Node<T> right = peek.getRight();
			if (right == null) return prev = stack.pop();
			if (prev == right) return prev = stack.pop();
			stack.push(right);
			while(right.getLeft() == null && right.getRight() != null) {
				right = right.getRight();
				stack.push(right);
			}
			pushLeft(right.getLeft());
			return prev = stack.pop();
		}
		
	}
	private class InOrderIterator implements Iterator<Node<T>> {
		Deque<Node<T>> stack = new LinkedList<Node<T>>();
		public InOrderIterator() {
			pushLeft(root);
		}
		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		public void pushLeft(Node<T> start) {
			for (Node<T> current = start; current != null;current = current.getLeft()) {
				stack.push(current);
			}
		}
		@Override
		public Node<T> next() {
			Node<T> removed = stack.pop();
			if (removed.getRight() != null) {
				stack.push(removed.getRight());
				pushLeft(removed.getRight().getLeft());
			}
			return removed;
		}
		
	}
	public Iterator<Node<T>> inOrderIterator() {
		return new InOrderIterator();
	}
	public Iterator<Node<T>> bfsIterator() {
		return new BFSIterator();
	}
	private class BFSIterator implements Iterator<Node<T>> {
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		public BFSIterator() {
			if (root == null) return;
			queue.add(root);
		}
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public Node<T> next() {
			Node<T> current = queue.remove();
			for (Node<T> child : current.childs) {
				if (child == null) continue;
				queue.add(child);
			}
			return current;
		}
	}
}
