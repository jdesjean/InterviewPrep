package org.ip.hashtable;

import java.util.HashSet;
import java.util.Set;

import org.ip.tree.LCAParentfull2.Node;

//EPI 2018: 12.4
public class LCA {
	public static void main(String[] s) {
		Node root = new Node(45,
				new Node(25, new Node(15, new Node(10), new Node(20)),
						new Node(30)),
				new Node(65, new Node(55, new Node(50), new Node(60)),
						new Node(75, null, new Node(80))));
		System.out.println(new LCA().solve(root.left.left.left, root.left.left.right));
	}
	public Node solve(Node n1, Node n2) {
		Set<Node> set = new HashSet<Node>();
		while (n1 != null || n2 != null) {
			if (n1 != null) {
				if (!set.add(n1)) {
					return n1;
				}
				n1 = n1.parent;
			}
			if (n2 != null) {
				if (!set.add(n2)) {
					return n2;
				}
				n2 = n2.parent;
			}
		}
		return null;
	}
}
