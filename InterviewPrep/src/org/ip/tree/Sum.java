package org.ip.tree;

import org.ip.array.Utils;
import org.ip.queue.BFS.Node;

// EPI 2018: 9.6
public class Sum {
	public static void main(String[] s) {
		Node root = node(314,node(6,node(271,node(28),node(0)),node(561,null,node(3,node(17)))),node(6,node(2,null,node(1,node(401,node(641)),node(257))),node(271,null,node(28))));
		Node[] node = new Node[6]; 
		new Sum().sum(root, 619, node, 0);
	}
	public Node sum(Node root, int target, Node[] node, int depth) {
		if (root == null) return null;
		node[depth++] = root;
		target -= root.value;
		if (root.left == null && root.right == null && target == 0) {
			Utils.println(node, depth);
			return root;
		} else {
			for (Node n : new Node[] {root.left, root.right}) {
				sum(n, target, node, depth);
			}
		}
		return null;
	}
	public static Node node(int value) {
		return new Node(value, null, null);
	}
	public static Node node(int value, Node left) {
		return new Node(value, left, null);
	}
	public static Node node(int v, Node left, Node right) {
		return new Node(v, left, right);
	}
}
