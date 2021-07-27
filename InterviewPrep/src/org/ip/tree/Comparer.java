package org.ip.tree;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.ip.bst.FinderBST;
import org.ip.tree.iterator.OrderIn;
import org.ip.tree.iterator.OrderReverse;

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
		Comparer[] closest = new Comparer[]{new ClosestHeapAll(), new ClosestInOrder(), new ClosestInOrderReverse(), new ClosestK(), new ClosestMutable()};
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
		public void replace(Tree<Integer> tree, Node<Integer> node) {
			Node<Integer> successor = Tree.successorChild(node);
			if (successor == null) successor = Tree.predecessorChild(node);
			Tree.remove(successor);
			tree.replace(node, successor);
		}
		public static class VisitorClosest implements Visitor<Integer>{
			private int v;
			private int minDiff = Integer.MAX_VALUE;
			Node<Integer> closest;
			public VisitorClosest(int v) {
				this.v = v;
			}
			@Override
			public void visit(Node<Integer> node) {
				int diff = Math.abs(node.value - v);
				if (diff < minDiff) {
					closest = node;
					minDiff = diff;
				}
			}
			
		}
		@Override
		public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
			for (int i = 0; i < k; i++) {
				//Node<Integer> closest = closest(tree,v, new Visitor<Integer>(){ public void visit(Node<Integer> node) {}});
				VisitorClosest visitorClosest = new VisitorClosest(v);
				new FinderBST<Integer>(tree, visitorClosest).find(v);
				Node<Integer> closest = visitorClosest.closest;
				visitor.visit(closest);
				replace(tree, closest);
			}
		}
	}
	//Time: log(n) + k, Space: log(n)
	public static class ClosestK implements Comparer {
		public static class VisitorClosest implements Visitor<Integer>{
			private int v;
			private int minDiff = Integer.MAX_VALUE;
			Deque<Node<Integer>> stack = new LinkedList<Node<Integer>>();
			Node<Integer> closest;
			public VisitorClosest(int v) {
				this.v = v;
			}
			@Override
			public void visit(Node<Integer> node) {
				stack.push(node);
				int diff = Math.abs(node.value - v);
				if (diff < minDiff) {
					closest = node;
					minDiff = diff;
				}
			}
			
		}
		@Override
		public void closest(Tree<Integer> tree, Visitor<Integer> visitor, int v, int k) {
			VisitorClosest visitorClosest = new VisitorClosest(v);
			new FinderBST<Integer>(tree,visitorClosest).find(v);
			Node<Integer> closest = visitorClosest.closest;
			Deque<Node<Integer>> stack = visitorClosest.stack;
			while (closest != stack.peek()) {
				stack.pop();
			}
			Iterator<Node<Integer>> iteratorForward = new OrderIn<Integer>((Deque<Node<Integer>>)((LinkedList)stack).clone());
			Iterator<Node<Integer>> iteratorBackward = new OrderReverse<Integer>(stack);
			Node<Integer> forward = iteratorForward.next();
			iteratorBackward.next();//closest
			Node<Integer> backward = iteratorBackward.next();
			for (int i = 0; i < k; i++) {
				if (forward != null && (backward == null || Math.abs(forward.value - v) <= Math.abs(backward.value - v) )) {
					visitor.visit(forward);
					forward = iteratorForward.hasNext() ? iteratorForward.next() : null;
				} else {
					visitor.visit(backward);
					backward = iteratorBackward.hasNext() ? iteratorBackward.next() : null; 
				}
			}
		}
		
	}
	
}
