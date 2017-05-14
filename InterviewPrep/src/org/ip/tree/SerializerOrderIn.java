package org.ip.tree;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.iterator.InputStreamNode;
import org.ip.tree.iterator.OrderIn;

//Does not work - Impossible?
public class SerializerOrderIn implements Serializer{
	@Override
	public void serialize(Tree<Integer> tree, OutputStream os) {
		Visitor<Integer> visitor = new VisitorOutputStream(os);
		for (Iterator<Node<Integer>> iterator = new OrderIn<Integer>(tree,true); iterator.hasNext();) {
			Node<Integer> node = iterator.next();
			visitor.visit(node);
		}
	}

	@Override
	public Tree<Integer> deserialize(InputStream is) {
		Iterator<Node<Integer>> iterator = new InputStreamNode(is);
		Deque<Node<Integer>> stack = new LinkedList<Node<Integer>>();
		Node<Integer> root = null;
		while (iterator.hasNext()) {
			root = node(iterator,stack, true);
			stack.push(root);
		}
		return new Tree<Integer>(root);
	}
	
	private Node<Integer> node(Iterator<Node<Integer>> iterator, Deque<Node<Integer>> stack, boolean isLeft) {
		if (stack.isEmpty() && !iterator.hasNext()) return null;
		if (stack.isEmpty()) {
			Node<Integer> node = iterator.next();
			if (!isLeft && node == null) return null;
			stack.push(node);
			while (node != null) {
				node = iterator.next();
				stack.push(node);
			}
		}
		Node<Integer> left = stack.pop();
		Node<Integer> current = stack.isEmpty() ? iterator.next() : stack.pop();
		Node<Integer> right = node(iterator,stack, false);
		current.childs[0] = left;
		current.childs[1] = right;
		return current;
	}

}
