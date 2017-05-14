package org.ip.tree;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.iterator.InputStreamNode;
import org.ip.tree.iterator.OrderPost;

//EPI: 10.13
//Time: O(n), Space: H 
public class SerializerOrderPost implements Serializer{
	@Override
	public void serialize(Tree<Integer> tree, OutputStream os) {
		Visitor<Integer> visitor = new VisitorOutputStream(os);
		for (Iterator<Node<Integer>> iterator = new OrderPost<Integer>(tree,true); iterator.hasNext();) {
			Node<Integer> node = iterator.next();
			visitor.visit(node);
		}
	}

	@Override
	public Tree<Integer> deserialize(InputStream is) {
		Iterator<Node<Integer>> iterator = new InputStreamNode(is);
		Node<Integer> root = node(iterator);
		return new Tree<Integer>(root);
	}
	
	private Node<Integer> node(Iterator<Node<Integer>> iterator) {
		Deque<Node<Integer>> stack = new LinkedList<Node<Integer>>();
		while (iterator.hasNext()) {
			Node<Integer> current = iterator.next();
			while (current == null) {
				stack.push(current);
				current = iterator.next();
			}
			Node<Integer> node = current;
			node.childs[1] = stack.pop();
			node.childs[0] = stack.pop();
			stack.push(node);
		}
		return stack.pop();
	}

}
