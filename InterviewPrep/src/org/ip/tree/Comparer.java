package org.ip.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.ip.tree.Tree.Visitor;
/*
 * BST, V, k
   k nodes in the tree which are closest to V
            
                  12
           10            17
         9     11     13     20

       7  9.8       12.1
              
   V = 11.6
  
   k = 3
   
   12, 11, 13   
 */
public interface Comparer {
	public static void main(String[] s){
		Tree<Integer> tree = TreeTest.bst1();
		Comparer[] closest = new Comparer[]{new ClosestHeapAll(), new ClosestInOrder(), new ClosestInOrderReverse(), new ClosestMutable()};
		for (int i = 0; i < closest.length; i++) {
			closest[i].closest(tree, PRINT_VISITOR, 4, 3);
			System.out.println();
		}
	}
	public static final Visitor PRINT_VISITOR = new PrintVisitor();
	public static class PrintVisitor implements Visitor {

		@Override
		public void visit(Node node) {
			System.out.print(node);
			
		}
		
	}
	public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k);

	//Time: nlog(k), Space: log(n)+k
	public static class ClosestHeapAll implements Comparer{
		 public static class ComparatorV implements Comparator<Node<Integer>>{
	         private int v;
			 public ComparatorV(int v) {
	             this.v = v;
	         }
	         public int compare(Node<Integer> v1, Node<Integer> v2) {
	             int diff1 = Math.abs(v1.value-v);
	             int diff2 = Math.abs(v2.value-v);
	             if (diff1 < diff2) {
	                 return 1;
	             } else if (diff1 > diff2) {
	                 return -1;
	             } else {
	                 return 0;
	             }
	         }
	     }
		 @Override
	     public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
	         PriorityQueue<Node<Integer>> heap = new PriorityQueue<Node<Integer>>(k, new ComparatorV(v));
	         for (Iterator<Node<Integer>> iterator = tree.inOrderIterator(); iterator.hasNext();) {
	        	 Node<Integer> next = iterator.next();
	        	 if (heap.size() < k) heap.add(next);
	        	 else if (Math.abs(heap.peek().value - v) > Math.abs(next.value - v)) {
	        		 heap.remove();
	        		 heap.add(next);
	        	 }
	         }
	         for (Iterator<Node<Integer>> iterator = heap.iterator(); iterator.hasNext();) {
	        	 visitor.visit(iterator.next());
	         }
	     }
	}
	
	//Time: n, Space: n+log(n)
	public static class ClosestInOrder implements Comparer {

		@Override
		public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
			Node<Integer>[] sorted = tree.sorted();
			int i = 0;
			int j = sorted.length-1;
			for (int kk = j - i + 1; kk > k && i < j; kk--) {
				int diff1 = Math.abs(sorted[i].value - v);
				int diff2 = Math.abs(sorted[j].value - v);
				if (diff1 > diff2) i++;
				else j--;
			}
			for (int l = i; l <= j ; l++) {
				visitor.visit(sorted[l]);
			}
		}
		
	}
	
	//Time: n, Space: log(n)
	public static class ClosestInOrderReverse implements Comparer {

		@Override
		public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
			Iterator<Node<Integer>> forward = tree.inOrderIterator();
			Iterator<Node<Integer>> backward = tree.reverseOrderIterator();
			Node<Integer> forwardNode = forward.hasNext() ? forward.next() : null;
			Node<Integer> backwardNode = backward.hasNext() ? backward.next() : null;
			int count = tree.count();
			for (; count > k && forwardNode != backwardNode; count--) {
				int diff1 = Math.abs(forwardNode.value - v);
				int diff2 = Math.abs(backwardNode.value - v);
				if (diff1 > diff2) forwardNode = forward.next();
				else backwardNode = backward.next();
			}
			
			for (; count > 0; count--) {
				visitor.visit(forwardNode);
				if (forward.hasNext()) forwardNode = forward.next();
			}
		}
		
	}
	
	//Time: klog(n), Space: log(n)
	public static class ClosestMutable implements Comparer {

		//
		public Node<Integer> closest(Tree<Integer> tree, int v) {
			return closest(null, tree.root(),v,Integer.MAX_VALUE);
		}
		public Node<Integer> closest(Node<Integer> parent, Node<Integer> node, int v, int minDiff) {
			if (node == null) return null;
			int diff = Math.abs(node.value - v);
			Node<Integer> candidate = null;
			if (node.value < v) {
				candidate = closest(node,node.getRight(),v,Math.min(diff, minDiff));
			} else if (node.value > v){
				candidate = closest(node,node.getLeft(),v,Math.min(diff, minDiff));
			}
			if (candidate != null) return candidate;
			else if (diff < minDiff) {
				node.parent = parent;
				return node;
			}
			else return null;
		}
		public void replace(Tree<Integer> tree, Node<Integer> node) {
			Node<Integer> successor = Tree.successorChild(node);
			if (successor == null) successor = Tree.predecessorChild(node);
			Tree.remove(successor);
			tree.replace(node, successor);
		}
		@Override
		public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
			for (int i = 0; i < k; i++) {
				Node<Integer> closest = closest(tree,v);
				visitor.visit(closest);
				replace(tree, closest);
			}
		}
	}
	
}
