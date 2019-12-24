package org.ip.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

// EPI 2018: 9.14
public class IteratorLeaf implements Iterator<BasicNode>{
	public static void main(String[] s) {
		BasicNode root = new BasicNode(314,new BasicNode(6,new BasicNode(271,new BasicNode(28),new BasicNode(0)),new BasicNode(561,null,new BasicNode(3,new BasicNode(17)))),new BasicNode(6,new BasicNode(2,null,new BasicNode(1,new BasicNode(401,new BasicNode(641)),new BasicNode(257))),new BasicNode(271,null,new BasicNode(28))));
		for (IteratorLeaf it = new IteratorLeaf(root); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	private IteratorOrderIn iterator;
	private BasicNode node;
	public IteratorLeaf(BasicNode root) {
		this.iterator = new IteratorOrderIn(root);
		while (iterator.hasNext()) {
			this.node = this.iterator.next();
			if (this.node.isLeaf()) break;
		}
	}

	@Override
	public boolean hasNext() {
		return node != null;
	}

	@Override
	public BasicNode next() {
		if (node == null) throw new NoSuchElementException();
		BasicNode prev = node;
		while (iterator.hasNext()) {
			this.node = this.iterator.next();
 			if (this.node.isLeaf()) break;
		}
		if (!this.node.isLeaf() || prev == node) {
			this.node = null;
		}
		return prev;
	}
}
