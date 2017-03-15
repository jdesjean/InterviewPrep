package org.ip.tree;

import java.lang.reflect.Array;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Tree<T extends Comparable<T>> {
	private Node<T> root;
	public static void main(String[] s) {
		testLCA();
	}
	public static void testLCA() {
		Tree<Integer> tree = bst2();
		System.out.println(tree.lca(10, 20));
		System.out.println(tree.lca(50, 80));
		System.out.println(tree.lca(20, 60));
	}
	public static void testPrintAllPaths() {
		bst1().printAllPaths(new ArrayVisitor<Integer>(){

			@Override
			public void visit(Integer[] visit, int length) {
				for (int i = 0; i < length; i++) {
					System.out.print(visit[i]);
					if (i < length-1) System.out.print(",");
				}
				System.out.println("");
			}
		});
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
		Tree[] trees = new Tree[]{bst1(), nonBST()};
		for (Tree tree : trees) {
			Executor[] executors = new Executor[]{tree.new RecursiveIsBSTExecutor(), tree.new IterativeIsBSTExecutor()};
			for (int i = 0; i < executors.length; i++) {
				System.out.println(executors[i].execute());
			}
			System.out.println("***");
		}
	}
	public static void testIterators() {
		Tree[] trees = new Tree[]{bst1(), nonBST()};
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
	public static Tree<Integer> bst1() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, new Node<Integer>(1), null), new Node<Integer>(7, new Node<Integer>(6), new Node<Integer>(8, null, new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> bst2() {
		Node<Integer> root = new Node<Integer>(45, new Node<Integer>(25, new Node<Integer>(15, new Node<Integer>(10), new Node<Integer>(20)), new Node<Integer>(30)), new Node<Integer>(65, new Node<Integer>(55, new Node<Integer>(50), new Node<Integer>(60)), new Node<Integer>(75, null, new Node<Integer>(80))));
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
		root = bstify(new BstifyWrapper(head1),size);
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
	public class BstifyWrapper {
		public Node<T> node;
		public BstifyWrapper(Node<T> node){this.node=node;}
	}
	public final LcaWrapper EMPTY = new LcaWrapper(null);
	public class LcaWrapper {
		public Node<T> node;
		int count = 0;
		public LcaWrapper(Node<T> node){this.node=node;}
	}
	public Node<T> lca(T v1, T v2) {
		LcaWrapper wrapper = lca(root,v1,v2);
		return wrapper.count >= 2 ? wrapper.node : null;
	}
	public LcaWrapper lca(Node<T> current, T v1, T v2) {
		if (current == null) return new LcaWrapper(null);
		//4 cases
		//1) 1 left & 1 right: Return current
		//2) 1 current & (1 left | right) : Return current;
		//3) 2 left | 2 right : Return left or right
		//4) Can't find both : Return left or right
		LcaWrapper wrapper = lca(current.getLeft(),v1,v2);
		if (wrapper.count >= 2) return wrapper;
		if (current.value == v1 || current.value == v2) wrapper.count++;
		if (wrapper.count < 2) {
			LcaWrapper wrapper2 = lca(current.getRight(),v1,v2);
			if (wrapper2.count >= 2) return wrapper2;
			wrapper.count += wrapper2.count;
			if (wrapper2.count > 0) wrapper.node = wrapper2.node; 
		}
		if (wrapper.count >= 2) wrapper.node = current;
		return wrapper;
	}
	private Node<T> bstify(BstifyWrapper head, int size) {
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
	private Node<T> smallest(Node<T> a, Node<T> b) {
		if (b == null || (a != null && a.compareTo(b) <= 0)) return a;
		else return b;
	}
	public interface ArrayVisitor<T extends Comparable<T>> {
		public void visit(T[] visit, int length);
	}
	public void printAllPaths(ArrayVisitor<T> visitor) {
		RecursivePreOrderExecutor executor = new RecursivePreOrderExecutor(new PrintBooleanVisitor(visitor));
		executor.execute();
	}
	public int height() {
		return height(root);
	}
	private int height(Node<T> root) {
		if (root == null) return 0;
		int max = 0;
		for (Node<T> child : root.childs) {
			max = Math.max(max, height(child)+1);
		}
		return max;
	}
	private final class PrintBooleanVisitor implements BooleanVisitor<T>{
		private ArrayVisitor<T> visitor;
		private T[] path;
		public PrintBooleanVisitor(ArrayVisitor<T> visitor) {
			if (root == null) return;
			this.visitor = visitor;
			int height = height();
			this.path = (T[])Array.newInstance(root.value.getClass(), height);
		}
		@Override
		public boolean visit(Node<T> node, int depth) {
			path[depth] = node.value;
			if (node.isLeaf()) visitor.visit(path, depth+1);
			return true;
		}
	}
	public interface BooleanVisitor<T extends Comparable<T>> {
		public boolean visit(Node<T> node, int depth);
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
			return inOrder(reducer,root,0);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current, int depth) {
			if (!visitor.visit(current,depth)) return false;
			for (Node<T> child : current.childs) {
				if (child == null) continue;
				if (!inOrder(visitor,child,depth+1)) return false;
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
			return inOrder(reducer,root,0);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current, int depth) {
			int mid = current.childs.length/2;
			for (int i = 0; i < current.childs.length; i++) {
				Node<T> child = current.childs[i];
				if (child == null) continue;
				if (i < mid) {
					if (!inOrder(visitor,child,depth+1)) return false;
				}
				if (!visitor.visit(current,depth)) return false;
				if (i >= mid)  {
					if (!inOrder(visitor,child,depth+1)) return false;
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
			return inOrder(reducer,root,0);
		}
		private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current, int depth) {
			for (Node<T> child : current.childs) {
				if (child == null) continue;
				if (!inOrder(visitor,child,depth+1)) return false;
			}
			return visitor.visit(current,depth); 
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
