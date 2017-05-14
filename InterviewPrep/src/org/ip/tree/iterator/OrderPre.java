package org.ip.tree.iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;

//EPI 10.8
//Time: n, Space: H
public class OrderPre<T>  implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	boolean bIterateNullChilds = false;
	public OrderPre(Tree<T> tree){
		Node<T> node = tree.root();
		if (node != null) stack.push(node);
	}
	public OrderPre(Tree<T> tree, boolean bIterateNullChilds){
		Node<T> node = tree.root();
		if (bIterateNullChilds || node != null) stack.push(node);
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> current = stack.pop();
		if (current != null) {
			stack.push(current.getRight());
			stack.push(current.getLeft());
		}
		return current;
	}
	
}
