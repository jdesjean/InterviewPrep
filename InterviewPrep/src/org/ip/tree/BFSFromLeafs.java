package org.ip.tree;

import static org.ip.tree.TreeTest.bst1;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.ip.tree.Node;

// LinkedIn 10/03/2019
public class BFSFromLeafs {
	public static void main(String[] s) {
		Node root = bst1().root();
		List<Set<Node>> list = new BFSFromLeafs().bfsLeafs(root);
		for (Set<Node> set : list) {
			for (Node node : set) {
				System.out.print(node.value + ",");
			}
			System.out.println();
		}
	}
	public List<Set<Node>> bfsLeafs(Node root) {
		List<Set<Node>> list = new LinkedList<Set<Node>>();
		bfsLeafs(root, list);
		return list;
	}
	private int bfsLeafs(Node root, List<Set<Node>> list) {
		if (root == null) return -1;
		int max = -1;
		for (Node child : root.childs) {
			max = Math.max(max, bfsLeafs(child, list));
		}
		int index = max + 1;
		if (index <= list.size()) {
			list.add(new HashSet<Node>());
		}
		list.get(index).add(root);
		return index;
	}
}
