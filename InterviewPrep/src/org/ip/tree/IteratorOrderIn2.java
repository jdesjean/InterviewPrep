package org.ip.tree;

import java.util.Iterator;

import org.ip.tree.Successor.Node;

// EPI 2018: 9.11
public class IteratorOrderIn2 implements Iterator<Node>{
	public static void main(String[] s) {
		Node root = new Node(45,
				new Node(25, 
						new Node(15, 
								new Node(10), 
								new Node(20)),
						new Node(30)),
				new Node(65, 
						new Node(55, 
								new Node(50), 
								new Node(60)),
						new Node(75, 
								null, 
								new Node(80))));
		for (IteratorOrderIn2 it = new IteratorOrderIn2(root); it.hasNext();) {
			System.out.println(it.next());
		}
	}
	Node current;
	Successor successor = new Successor();
	public IteratorOrderIn2(Node root) {
		if (root == null) return;
		current = root;
		while (current.left != null) {
			current = current.left;
		}
	}
	@Override
	public boolean hasNext() {
		return current != null;
	}
	@Override
	public Node next() {
		Node prev = current;
		current = successor.find(current);
		return prev;
	}
}
