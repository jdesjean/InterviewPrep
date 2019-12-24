package org.ip;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// Google 10/03/19
// Find ancestors in genealogy (2 parents)
// What happens when both parents have same grandparent
public class CommonAncestors {
	public boolean solve(Node r1, Node r2) {
		Deque<Node> d1 = new LinkedList<Node>();
		Deque<Node> d2 = new LinkedList<Node>();
		d1.add(r1);
		d2.add(r2);
		Map<Node, Node> map = new HashMap<Node,Node>();
		while (!d1.isEmpty() && !d2.isEmpty()) {
			Node n1 = d1.remove();
			Node n2 = d2.remove();
			Node m1 = map.get(n1);
			if (m1 == r2) return true;
			Node m2 = map.get(n2);
			if (m2 == r1) return true;
			map.put(n1, r1);
			map.put(n2, r2);
			if (n1.father != null) {
				d1.add(n1.father);
			}
			if (n1.mother != null) {
				d1.add(n1.mother);
			}
			if (n2.father != null) {
				d2.add(n2.father);
			}
			if (n2.mother != null) {
				d2.add(n2.mother);
			}
		}
		Deque<Node> d = !d1.isEmpty() ? d1 : d2;
		while (!d.isEmpty()) {
			Node n = d1.remove();
			if (n.father != null) {
				d.add(n.father);
			}
		}
		return false;
	}
	public static class Node {
		public Node father;
		public Node mother;
	}
}
