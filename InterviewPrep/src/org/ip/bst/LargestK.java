package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import java.util.Iterator;

import org.ip.tree.Node;
import org.ip.tree.iterator.OrderReverse;

// EPI 2018: 14.4
public class LargestK {
	public static void main(String[] s) {
		for (Iterator<Node<Integer>> iterator = new OrderReverse<Integer>(bst3(), 3); iterator.hasNext();) {
			Node<Integer> current = iterator.next();
			System.out.println(current.value);
		}
	}
}
