package org.ip.tree;

import java.lang.reflect.Array;
import java.util.Iterator;

public class Tree<T extends Comparable<T>> {
	private Node<T> root;
	public static void main(String[] s) {
		testPopulateSibling();
	}
	public Node<T> root() {return root;}
	public static void testPopulateSibling() {
		Tree<Integer> tree = bst1();
		tree.populateSibling();
		for (Iterator<Node<Integer>> it1 = new BFSIterator<Integer>(tree); it1.hasNext();) {
			System.out.println(it1.next());
		}
	}
	public static void testClone() {
		Tree<Integer> tree1 = bst1();
		Tree<Integer> tree2 = tree1.clone();
		System.out.println(tree1.equals(tree2));
	}
	public boolean equals(Tree<T> tree) {
		for (Iterator<Node<T>> it1 = new InOrderIterator<T>(this), it2 = new InOrderIterator<T>(tree); it1.hasNext() && it2.hasNext();) {
			if (!it1.next().equals(it2.next())) return false;
		}
		for (Iterator<Node<T>> it1 = new PreOrderIterator<T>(this), it2 = new PreOrderIterator<T>(tree); it1.hasNext() && it2.hasNext();) {
			if (!it1.next().equals(it2.next())) return false;
		}
		return true;
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
		testBSTBuild();
	}
	public void populateSibling() {
		Node<T> prev = null;
		int depth = 0;
		for (Iterator<NodeWrapper<T>> it1 = new BFSWrapperIterator<T>(this); it1.hasNext();) {
			NodeWrapper<T> wrapper = it1.next();
			Node<T> current = wrapper.node;
			if (depth != wrapper.depth) prev = null;
			if (prev != null) prev.sibling = current;
			prev = current;
			depth = wrapper.depth;
		}
	}
	public int count() {
		int count =0;
		for (Iterator<Node<T>> iterator = new PreOrderIterator<T>(this); iterator.hasNext();) {
			iterator.next();
			count++;
		}
		return count;
	}
	public static void testBSTBuild() {
		Tree<Integer> bst = bst1();
		int count = bst.count();
		Integer[] pre = new Integer[count];
		Integer[] in = new Integer[count];
		int index = 0;
		for (Iterator<Node<Integer>> iterator = new PreOrderIterator<Integer>(bst); iterator.hasNext();) {
			pre[index++] = iterator.next().value;
		}
		index = 0;
		for (Iterator<Node<Integer>> iterator = bst.inOrderIterator(); iterator.hasNext();) {
			in[index++] = iterator.next().value;
		}
		Tree<Integer> copy = Tree.fromPreIn(pre,in);
		for (Iterator<Node<Integer>> iterator = new PreOrderIterator<Integer>(copy); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	public static void testMerge() {
		Tree<Integer> tree = small();
		tree.merge(big());
		Iterator<Node>[] iterators = new Iterator[]{tree.inOrderIterator(), tree.bfsIterator(), new PostOrderIterator<Integer>(tree), new PreOrderIterator<Integer>(tree)};
		for (int i = 0; i < iterators.length; i++) {
			for (Iterator<Node> iterator = iterators[i]; iterator.hasNext();) {
				System.out.println(iterator.next().value);
			}
			System.out.println("***");
		}
		System.out.println(tree.new IterativeIsBSTExecutor().execute());
	}
	public static void testBST() {
		Tree<Integer>[] trees = new Tree[]{bst1(), nonBST()};
		for (Tree<Integer> tree : trees) {
			Executor[] executors = new Executor[]{tree.new RecursiveIsBSTExecutor(), tree.new IterativeIsBSTExecutor()};
			for (int i = 0; i < executors.length; i++) {
				System.out.println(executors[i].execute());
			}
			System.out.println("***");
		}
	}
	public static void testIterators() {
		Tree<Integer>[] trees = new Tree[]{bst1(), nonBST()};
		for (Tree<Integer> tree : trees) {
			Iterator<Node<Integer>>[] iterators = new Iterator[]{tree.inOrderIterator(), tree.bfsIterator(), new PostOrderIterator<Integer>(tree), new PreOrderIterator<Integer>(tree)};
			for (int i = 0; i < iterators.length; i++) {
				for (Iterator<Node<Integer>> iterator = iterators[i]; iterator.hasNext();) {
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
	public static <T extends Comparable<T>> Tree<T> fromPreIn(T[] preOrder, T[] inOrder) {
		Node<T> node = fromPreIn(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1);
		return new Tree<T>(node);
	}
	private static <T extends Comparable<T>> Node<T> fromPreIn(T[] pre, int leftPre, int rightPre, T[] in, int leftIn, int rightIn) {
		if (leftPre > rightPre || leftIn > rightIn) return null;
		T current = pre[leftPre];
		int indexIn;
		for (indexIn = leftIn; indexIn <= rightIn; indexIn++) {
			if (current == in[indexIn]) break;
		}
		Node<T> left = fromPreIn(pre,leftPre+1,rightPre,in,leftIn,indexIn-1);
		int len = indexIn - leftIn;
		Node<T> right = fromPreIn(pre,leftPre+len+1,rightPre,in,indexIn+1,rightIn);
		return new Node<T>(current,left,right);
	}
	public Tree<T> clone() {
		return new Tree<T>(clone(root));
	}
	public Node<T> clone(Node<T> node) {
		if (node == null) return null;
		Node<T>[] childs = new Node[node.childs.length];
		for (int i = 0; i < node.childs.length; i++) {
			childs[i] = clone(node.childs[i]);
		}
		return new Node<T>(node.value,childs);
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
	public Iterator<Node<T>> inOrderIterator() {
		return new InOrderIterator<T>(this);
	}
	public Iterator<Node<T>> bfsIterator() {
		return new BFSIterator<T>(this);
	}
}
