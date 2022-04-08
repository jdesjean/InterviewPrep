package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import java.util.Iterator;

import org.ip.tree.Node;
import org.ip.tree.iterator.OrderPre;

// EPI 2018: 14.5
public class NodeFromPreOrder {
	public static void main(String[] s) {
		Node<Integer> root = new NodeFromPreOrder().reconstruct(new OrderPre<Integer>(bst3()));
		System.out.println(root);
	}
	public Node<Integer> reconstruct(OrderPre<Integer> iterator) {
		if (!iterator.hasNext()) return null;
		return reconstruct(iterator, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public Node<Integer> reconstruct(OrderPre<Integer> iterator, int min, int max) {
		if (!iterator.hasNext()) return null;
		Node<Integer> root = iterator.peek();
		if (root.value < min || root.value > max) return null; 
		root = new Node<Integer>(iterator.next().value);
 		root.childs[0] = reconstruct(iterator, min, root.value);
		root.childs[1] = reconstruct(iterator, root.value, max);
		
		return root;
	}
}
