package org.ip.tree;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.ip.tree.iterator.InputStreamNode;
import org.ip.tree.iterator.OrderPre;
//http://www.geeksforgeeks.org/serialize-deserialize-binary-tree/
//EPI: 10.13
//Time: O(n), Space: H 
public class SerializerOrderPre implements Serializer{
	@Override
	public void serialize(Tree<Integer> tree, OutputStream os) {
		Visitor<Integer> visitor = new VisitorOutputStream(os);
		for (Iterator<Node<Integer>> iterator = new OrderPre<Integer>(tree,true); iterator.hasNext();) {
			Node<Integer> node = iterator.next();
			visitor.visit(node);
		}
	}

	@Override
	public Tree<Integer> deserialize(InputStream is) {
		Iterator<Node<Integer>> iterator = new InputStreamNode(is);
		return new Tree<Integer>(node(iterator));
	}
	
	private Node<Integer> node(Iterator<Node<Integer>> iterator) {
		Node<Integer> node = iterator.next();
		if (node == null) return node;
		node.childs[0] = node(iterator);
		node.childs[1] = node(iterator);
		return node;
	}

}
