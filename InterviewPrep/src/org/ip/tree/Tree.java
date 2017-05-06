package org.ip.tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ip.array.ArrayUtils;

public class Tree<T extends Comparable<T>> {
	Node<T> root;
	public Tree(Node<T> root) {
		this.root=root;
	}
	public static <T extends Comparable<T>> Tree<T> tree(Node<T> root) {
		return new Tree<T>(root);
	}
	public static <T extends Comparable<T>> Node<T> node(T t) {
		return new Node<T>(t);
	} 
	public static <T extends Comparable<T>> Node<T> node(T t, Node<T> c1, Node<T> c2) {
		return new Node<T>(t,c1,c2);
	}
	public static <T extends Comparable<T>> Tree<T> fromPreIn(T[] preOrder, T[] inOrder) {
		Node<T> node = fromPreIn(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1);
		return tree(node);
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
		return node(current,left,right);
	}
	public static <T extends Comparable<T>> Tree<T> fromIn(T[] inOrder) {
		return tree(fromIn(inOrder,0,inOrder.length-1));
	}
	private static <T extends Comparable<T>> Node<T> fromIn(T[] in, int left, int right) {
		if (left > right) return null;
		int mid = (right-left)/2+left;
		return node(in[mid],fromIn(in,left,mid-1),fromIn(in,mid+1,right));
	}
	public Node<T> root() {return root;}
	public void populateSibling() {
		Node<T> prev = null;
		int depth = 0;
		for (Iterator<NodeWrapper<T>> it1 = new IteratorBFSWrapper<T>(this); it1.hasNext();) {
			NodeWrapper<T> wrapper = it1.next();
			Node<T> current = wrapper.node;
			if (depth != wrapper.depth) prev = null;
			if (prev != null) prev.sibling = current;
			prev = current;
			depth = wrapper.depth;
		}
	}
	public int countUnival() {
		return countUnival(root,null).max;
	}
	private SizeWrapper countUnival(Node<T> current, T value) {
		if (current == null) return new SizeWrapper();
		//if (value.equals(current.value))
		int size = 1;
		int max = 0;
		boolean bst = (value == null) 
				|| (current.value.equals(value));
		SizeWrapper wrapper = countUnival(current.getLeft(),current.value);
		if (wrapper.bst) size+=wrapper.current;
		max = Math.max(max, wrapper.max);
		wrapper = countUnival(current.getRight(),current.value);
		max = Math.max(max, wrapper.max);
		if (wrapper.bst) size+=wrapper.current;
		max = Math.max(max, size);
		return new SizeWrapper(size,max,bst);
	}
	public int count() {
		int count =0;
		for (Iterator<Node<T>> iterator = new IteratorPreOrder<T>(this); iterator.hasNext();) {
			iterator.next();
			count++;
		}
		return count;
	}
	public class SizeWrapper {
		int current;
		int max;
		boolean bst;
		public SizeWrapper(){}
		public SizeWrapper(int current, int max, boolean bst){this.current=current;this.max=max;this.bst=bst;}
	}
	public int largestBst() {
		return largestBst(root,null,null).max;
	}
	private SizeWrapper largestBst(Node<T> current, T bottom, T top) {
		if (current == null) return new SizeWrapper();
		int max = 0;
		boolean bst = (bottom == null && top == null)
				|| (bottom == null && current.value.compareTo(top) < 0)
				|| (top == null && bottom.compareTo(current.value) < 0)
				|| (bottom.compareTo(current.value) < 0 && current.value.compareTo(top) < 0);
		int size = 1;
		SizeWrapper wrapper = largestBst(current.getLeft(),bst ? bottom : null,current.value);
		max = Math.max(max, wrapper.max);
		if (wrapper.bst) size+=wrapper.current;
		wrapper = largestBst(current.getRight(),current.value,bst ? top : null);
		max = Math.max(max, wrapper.max);
		if (wrapper.bst) size+=wrapper.current;
		max = Math.max(max, size);
		return new SizeWrapper(size,max,bst);
	}
	public boolean equals(Tree<T> tree) {
		for (Iterator<Node<T>> it1 = this.inOrderIterator(), it2 = tree.inOrderIterator(); it1.hasNext() && it2.hasNext();) {
			if (!it1.next().equals(it2.next())) return false;
		}
		for (Iterator<Node<T>> it1 = this.preOrderIterator(), it2 = tree.preOrderIterator(); it1.hasNext() && it2.hasNext();) {
			if (!it1.next().equals(it2.next())) return false;
		}
		return true;
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
	public void flip() {
		for (Iterator<Node<T>> iterator = this.inOrderIterator(); iterator.hasNext();) {
			Node<T> current = iterator.next();
			ArrayUtils.reverse(current.childs, 0, current.childs.length-1);
		}
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
		ReducerPreOrderRecursive<T> executor = new ReducerPreOrderRecursive<T>(this,new PrintBooleanVisitor(visitor));
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
	public Node<T>[] sorted() {
		int size = count();
		Node<T>[] sorted = new Node[size];
		int i = 0;
		for (Iterator<Node<T>> iterator = inOrderIterator(); iterator.hasNext();) {
			sorted[i++] = iterator.next();
		}
		return sorted;
	}
	public void replace(Node<T> node1, Node<T> node2) {
		Node<T> parent = node1.parent;
		if (parent == null) parent = root;
		Node<T> left = parent.getLeft();
		Node<T> right = parent.getRight();
		
		node2.childs[0] = left;
		node2.childs[1] = right;
		if (node1.parent == null) { //root
			root = node2;
		} else if (node1.parent.getRight() == node1){
			parent.childs[1] = node2;
		} else if (node1.parent.getLeft() == node1) {
			parent.childs[0] = node2;
		}
	}
	public static <T extends Comparable<T>>  void remove(Node<T> node) {
		if (node.getLeft() != null) {
			node.parent = node.childs[0];
		} else if (node.getRight() != null) {
			node.parent = node.childs[1];
		} else if (node.parent.getLeft() == node) {
			node.parent.childs[0] = null;
		} else if (node.parent.getRight() == node) {
			node.parent.childs[1] = null;
		}
		node.childs[0] = null;
		node.childs[1] = null;
	}
	public static <T extends Comparable<T>>  Node<T> successorChild(Node<T> node) {
		if (node.getRight() == null) return null;
		Node<T> current = node.getRight();
		Node<T> parent = node;
		while (current.getLeft() != null) {
			parent = current;
			current = current.getLeft();
		}
		current.parent = parent;
		return current;
	}
	public static <T extends Comparable<T>>  Node<T> predecessorChild(Node<T> node) {
		if (node.getLeft() == null) return null;
		Node<T> current = node.getLeft();
		Node<T> parent = node;
		while (current.getRight() != null) {
			parent = current;
			current = current.getRight();
		}
		current.parent = parent;
		return current;
	}
	public static int count(int n) {
		if (n == 0 || n == 1) return 1;
		
		int count = 0;
		n--;
		for (int i = 0; i <= n; i++) {
			count+=count(i)*count(n-i);
		}
		
		return count;
	}
	private static class Diameter {
		public static final Diameter EMPTY = new Diameter(0,0);
		public int current;
		public int child;
		public Diameter(int current, int child) {this.current=current;this.child=child;}
	}
	public static int diameter(Node<Integer> root) {
		return diameterRecursive(root).child;
	}
	public static Diameter diameterRecursive(Node<Integer> root) {
		if (root == null) return Diameter.EMPTY;
		
		Diameter max = new Diameter(root.value,0);
		int maxCurrent = 0;
		int maxCurrent2 = 0;
		int maxDiameter = 0;
		for (Node<Integer> child : root.childs) {
			Diameter current = diameterRecursive(child);
			if (current.current >= maxCurrent2 && current.current > maxCurrent) {
				maxCurrent2 = maxCurrent;
				maxCurrent = current.current;
			} else if (current.current >= maxCurrent2) {
				maxCurrent2 = current.current;
			}
			maxDiameter = Math.max(current.child, maxDiameter);
		}
		max.current+=maxCurrent;
		max.child=Math.max(maxCurrent2 > 0 ? maxCurrent+maxCurrent2 : 0, maxDiameter);
		
		return max;
	}
	public List<List<Node<T>>> bfs() {
		List<List<Node<T>>> list = new ArrayList<List<Node<T>>>();
		for (Iterator<Iterator<Node<T>>> levelIterator = new IteratorBFSIterator<T>(this); levelIterator.hasNext(); ) {
			List<Node<T>> siblings = new ArrayList<Node<T>>();
			for (Iterator<Node<T>> siblingsIterator = levelIterator.next(); siblingsIterator.hasNext();) {
				siblings.add(siblingsIterator.next());
			}
			list.add(siblings);
		}
		return list;
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
	public interface ObjectVisitor<S, T extends Comparable<T>> {
		public S visit(Node<T> node, int depth, S[] s, S previous);
	}
	public interface Visitor<T extends Comparable<T>> {
		public void visit(Node<T> node);
	}
	public interface BooleanReducer {
		public boolean execute();
	}
	public interface Reducer<T> {
		public T execute(T init);
		public T get();
	}
	public Iterator<Node<T>> reverseOrderIterator(int k) {
		return new IteratorReverseOrder<T>(this, k);
	}
	public Iterator<Node<T>> reverseOrderIterator() {
		return new IteratorReverseOrder<T>(this);
	}
	public BooleanReducer iterativeIsBSTReducer() {
		return new ReducerIsBSTIterative<T>(this);
	}
	public BooleanReducer recursiveIsBSTReducer() {
		return new ReducerIsBSTRecursive<T>(this);
	}
	public Iterator<Node<T>> postOrderIterator() {
		return new IteratorPostOrder<T>(this); 
	} 
	public Iterator<Node<T>> preOrderIterator() {
		return new IteratorPreOrder<T>(this); 
	}
	public Iterator<Node<T>> inOrderIterator(int k) {
		return new IteratorInOrder<T>(this,k);
	}
	public Iterator<Node<T>> inOrderIterator() {
		return new IteratorInOrder<T>(this);
	}
	public Iterator<Node<T>> bfsIterator() {
		return new IteratorBFS<T>(this);
	}
}
