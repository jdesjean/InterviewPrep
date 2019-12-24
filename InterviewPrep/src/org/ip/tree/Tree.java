package org.ip.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ip.array.Utils;
import org.ip.tree.Tree.SizeWrapper;
import org.ip.tree.iterator.BFS;
import org.ip.tree.iterator.BFSIterator;
import org.ip.tree.iterator.BFSWrapper;
import org.ip.tree.iterator.NodeWrapper;
import org.ip.tree.iterator.OrderIn;
import org.ip.tree.iterator.OrderInLeaves;
import org.ip.tree.iterator.OrderPost;
import org.ip.tree.iterator.OrderPre;
import org.ip.tree.iterator.OrderReverse;
import org.ip.tree.reducer.BooleanVoid;
import org.ip.tree.reducer.IsBSTIterative;
import org.ip.tree.reducer.IsBSTRecursive;
import org.ip.tree.reducer.Object;
import org.ip.tree.reducer.OrderPostRecursive;
import org.ip.tree.reducer.OrderPreRecursive;
import org.ip.tree.reducer.ReducerBooleanPrint;

public class Tree<T> {
	Node<T> root;
	public Tree(Node<T> root) {
		this.root=root;
	}
	public static <T> Tree<T> tree(Node<T> root) {
		return new Tree<T>(root);
	}
	public static <T> Node<T> node(T t) {
		return new Node<T>(t);
	} 
	public static <T> Node<T> node(T t, Node<T> c1, Node<T> c2) {
		return new Node<T>(t,c1,c2);
	}
	//EPI: 10.12
	//Time: O(n^2), can improve complexity to O(n) with hashtable for inorder
	//Space: H
	public static <T> Tree<T> fromPreIn(T[] preOrder, T[] inOrder) {
		Node<T> node = fromPreIn(preOrder,0,preOrder.length-1,inOrder,0,inOrder.length-1);
		return tree(node);
	}
	private static <T> Node<T> fromPreIn(T[] pre, int leftPre, int rightPre, T[] in, int leftIn, int rightIn) {
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
		for (Iterator<NodeWrapper<T>> it1 = new BFSWrapper<T>(this); it1.hasNext();) {
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
	public static <T extends Comparable<T>> int largestBst(Tree<T> tree) {
		return largestBst(tree.root(),null,null).max;
	}
	private static <T extends Comparable<T>> SizeWrapper largestBst(Node<T> current, T bottom, T top) {
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
		for (Iterator<Node<T>> iterator = new OrderPre<T>(this); iterator.hasNext();) {
			iterator.next();
			count++;
		}
		return count;
	}
	public static class SizeWrapper {
		int current;
		int max;
		boolean bst;
		public SizeWrapper(){}
		public SizeWrapper(int current, int max, boolean bst){this.current=current;this.max=max;this.bst=bst;}
	}
	public boolean equals(Tree<T> tree) {
		for (Iterator<Node<T>> it1 = new OrderPre<T>(this,true), it2 = new OrderPre<T>(tree,true); it1.hasNext() || it2.hasNext();) {
			if (!it1.hasNext() || !it2.hasNext()) return false;
			Node<T> n1 = it1.next();
			Node<T> n2 = it2.next();
			if (n1 == null && n2 == null) continue;
			else if (n1 == null || n2 == null) return false;
			else if (!n1.equals(n2)) return false;
		}
		return true;
	}
	public Tree<T> clone() {
		return new Tree<T>(clone(root));
	}
	private Node<T> clone(Node<T> node) {
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
	
	public Node<T> lcaParentfull(T v1, T v2) {
		return new LCAParentfull(this).lca(v1, v2);
	}
	public Node<T> lcaParentless(T v1, T v2) {
		return new LCAParentless<T>(this).lca(v1, v2);
	}
	//EPI 10.5
	//Time: n, Space: H
	public static int sumRootToLeaf(Tree<Integer> tree) {
		return sumRootToLeaf(tree.root,0);
	}
	private static int sumRootToLeaf(Node<Integer> node, int previous) {
		if (node == null) return 0;
		int next = previous*2+node.value;
		if (node.getLeft() == null && node.getRight() == null) return next;
		
		return sumRootToLeaf(node.getLeft(), next) + sumRootToLeaf(node.getRight(),next); 
	}
	//EPI 10.6
	//Time: n, Space: H
	public static boolean hasTargetSum(Tree<Integer> tree, int target) {
		return hasTargetSum(tree.root, target, 0);
	}
	private static boolean hasTargetSum(Node<Integer> node, int target, int previous) {
		if (node == null) return false;
		int next = previous + node.value;
		if (node.getLeft() == null && node.getRight() == null) return next == target;
		return hasTargetSum(node.getLeft(),target,next) || hasTargetSum(node.getRight(),target,next);
	}
	public void flip() {
		for (Iterator<Node<T>> iterator = this.inOrderIterator(); iterator.hasNext();) {
			Node<T> current = iterator.next();
			Utils.reverse(current.childs, 0, current.childs.length-1);
		}
	}
	public void assignParent(){
		assignParent(null,root());
	}
	//Time: O(n), Space: O(H)
	private void assignParent(Node<T> parent, Node<T> node) {
		if (node == null) return;
		node.parent = parent;
		assignParent(node,node.getLeft());
		assignParent(node,node.getRight());
	}
	//EPI: 10.17
	//Time: O(n), Space: O(W)
	public void assignSibling() {
		for (Iterator<Iterator<Node<T>>> iteratorDepth = new BFSIterator<T>(this); iteratorDepth.hasNext();) {
			Node<T> prev = null;
			for (Iterator<Node<T>> iteratorBreath = iteratorDepth.next(); iteratorBreath.hasNext();) {
				Node<T> current = iteratorBreath.next();
				if (prev != null) prev.sibling = current;
				prev = current;
			}
		}
	}
	//EPI: 10.18
	//Time: O(H), Space: O(n)
	public static <T> boolean unlock(Node<T> node) {
		if (node == null || !node.locked) return false;
		node.locked = false;
		Node<T> current = node.parent;
		while (current != null) {
			current.lockCount-=1;
			current = current.parent;
		}
		return true;
	}
	//Time: O(H), Space: O(n)
	public static <T> boolean lock(Node<T> node) {
		if (node == null || node.locked || node.lockCount > 0) return false;
		Node<T> current = node.parent;
		while (current != null) {
			if (current.locked) return false;
			current = current.parent;
		}
		node.locked = true;
		current = node.parent;
		while (current != null) {
			current.lockCount+=1;
			current = current.parent;
		}
		return true;
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
	public interface ArrayVisitor<T> {
		public void visit(T[] visit, int length);
	}
	public void printAllPaths(ArrayVisitor<T> visitor) {
		OrderPreRecursive<T> executor = new OrderPreRecursive<T>(this,new ReducerBooleanPrint(this,visitor));
		executor.execute();
	}
	/*public int height() {
		ReducerOrderPost<Integer,T> reducer = new ReducerOrderPost<Integer,T>(){
			@Override
			public Integer visit(Node<T> node, int depth, Integer previous) {
				return previous + 1;
			}
		};
		Reducer<Integer,Integer> reducerChild = new Reducer<Integer,Integer>(){

			@Override
			public Integer visit(Integer current, Integer init) {
				return Math.max(current, init);
			}
			
		};
		ReducerOrderPostRecursive<T,Integer> rec = new ReducerOrderPostRecursive<T,Integer>(this,reducer,reducerChild);
		return rec.execute(0);
	}*/
	public int height() {
		return Tree.height(root);
	}
	private static <T> int height(Node<T> root) {
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
	public static <T>  void remove(Node<T> node) {
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
	//EPI: 10.10
	//Time: H, Space: H
	public static <T> Node<T> successor(Node<T> node) {
		if (node == null) return null;
		Node<T> right = successorChild(node);
		if (right != null) return right;
		
		Node<T> current = node;
		if (current.parent == null) {
			return successorChild(node);
		} else if (isLeftChild(current)) {
			return current.parent;
		} else {
			do {
				current = current.parent;
			} while (isRightChild(current));
			return successorChild(current);
		}
	}
	public static <T> boolean isLeftChild(Node<T> node) {
		return node != null && node.parent != null && node.parent.getLeft() == node;
	}
	public static <T> boolean isRightChild(Node<T> node) {
		return node != null && (node.parent == null || node.parent.getRight() == node);
	}
	public static <T>  Node<T> successorChild(Node<T> node) {
		if (node == null || node.getRight() == null) return null;
		Node<T> current = node.getRight();
		Node<T> parent = node;
		while (current.getLeft() != null) {
			parent = current;
			current = current.getLeft();
		}
		current.parent = parent;
		return current;
	}
	public static <T>  Node<T> predecessorChild(Node<T> node) {
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
		for (Iterator<Iterator<Node<T>>> levelIterator = new BFSIterator<T>(this); levelIterator.hasNext(); ) {
			List<Node<T>> siblings = new ArrayList<Node<T>>();
			for (Iterator<Node<T>> siblingsIterator = levelIterator.next(); siblingsIterator.hasNext();) {
				siblings.add(siblingsIterator.next());
			}
			list.add(siblings);
		}
		return list;
	}
	//EPI 10.1
	public boolean isBalanced() {
		org.ip.tree.reducer.OrderPost<Integer,T> reducer = new org.ip.tree.reducer.OrderPost<Integer,T>(){
			@Override
			public Integer visit(Node<T> node, int depth, Integer previous) {
				return previous == null ? 0 : previous >= 0 ? previous + 1 : -1;
			}
		};
		Object<Integer,Integer> reducerChild = new Object<Integer,Integer>(){

			@Override
			public Integer visit(Integer current, Integer previous) {
				return previous == null ? current : previous != -1 && Math.abs(current - previous) <= 1 ? Math.max(previous, current) : -1;
			}
			
		};
		OrderPostRecursive<T,Integer> rec = new OrderPostRecursive<T,Integer>(this,reducer,reducerChild);
		return rec.execute(null) >= 0;
	}
	//EPI 10.2
	public boolean isSymmetric() {
		return isSymetric(root.getLeft(),root.getRight());
	}
	private boolean isSymetric(Node<T> node1, Node<T> node2) {
		return ((node1 == null && node2 == null) || 
				(node1 != null && node2 != null && node1.value == node2.value)) && 
				(node1 == null || (isSymetric(node1.getLeft(),node2.getRight()) && isSymetric(node1.getRight(),node2.getLeft())));
	}
	
	//EPI 10.9
	//Time: H, Space: 1
	public static Node<Integer> inOrderKth(Tree<Integer> tree, int k) {
		
		Node<Integer> node = tree.root;
		while (node != null) {
			int nextK = k - node.value;
			if (nextK == 1) return node;
			else if (nextK <= 0) {
				node = node.getLeft();
			} else {
				node = node.getRight();
				k = nextK - 1;
			}
		}
		return null;
	}
	//EPI: 10.14
	//Time: O(n), Space: O(H);
	public LinkedList<Node<T>> toLinkedListLeaves() {
		LinkedList<Node<T>> list = new LinkedList<Node<T>>();
		for (Iterator<Node<T>> iterator = new OrderInLeaves<T>(this); iterator.hasNext();) {
			list.add(iterator.next());
		}
		return list;
	}
	public Iterator<Node<T>> reverseOrderIterator(int k) {
		return new OrderReverse<T>(this, k);
	}
	public Iterator<Node<T>> reverseOrderIterator() {
		return new OrderReverse<T>(this);
	}
	public Iterator<Node<T>> postOrderIterator() {
		return new OrderPost<T>(this); 
	} 
	public Iterator<Node<T>> preOrderIterator() {
		return new OrderPre<T>(this); 
	}
	public Iterator<Node<T>> inOrderIterator(int k) {
		return new OrderIn<T>(this,k);
	}
	public Iterator<Node<T>> inOrderIterator() {
		return new OrderIn<T>(this);
	}
	public Iterator<Node<T>> bfsIterator() {
		return new BFS<T>(this);
	}
	public static void print(Node root) {
		Map<Node,Pos> map = new HashMap<Node,Pos>();
		int height = height(root);
		int width = (int)Math.pow(2, height - 1);
		_print(root,0,width * 2,0,map); // accept double the width to handle 0.5f
		Pos prev = null;
		int count = 0;
		for (BFS bfs = new BFS(root); bfs.hasNext(); ) {
			Node node = bfs.next();
			Pos pos = map.get(node);
			if (prev != null && prev.y != pos.y) {
				System.out.println();
				count = 1;
				int spaces = (int)(pos.x * 2f);
				for (double i = 0; i < spaces; i++) {
					System.out.print(" ");
				}
				System.out.print(node.value);
			} else {
				int spaces = prev != null ? (int)((pos.x - prev.x) * 2f) : (int)(pos.x * 2f);
				spaces-=count;
				for (int i = 0; i < spaces; i++) {
					System.out.print(" ");
				}
				System.out.print(node.value);
				count++;
			}
			prev = pos;
		}
		System.out.println();
		System.out.println();
	}
	private static class Pos {
		double x;
		int y;
		public Pos(double x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Pos [x=" + x + ", y=" + y + "]";
		}
		
	}
	public static void _print(Node node, double left, double right, int y, Map<Node,Pos> map) {
		if (node == null) return;
		double x = left + (right - left) / 2;
		map.put(node, new Pos(x, y));
		_print(node.getLeft(), left, x, y + 1, map);
		_print(node.getRight(), x, right, y + 1, map);
	}
}
