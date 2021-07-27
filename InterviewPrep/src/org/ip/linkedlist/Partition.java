package org.ip.linkedlist;

// EPI: 8.12
public class Partition {
	public static void main(String[] s) {
		Partition partition = new Partition();
		System.out.println(partition.partition(newList(), 2));
		System.out.println(partition.partition(newList(), 3));
		System.out.println(partition.partition(newList(), 5));
		System.out.println(partition.partition(newList(), 7));
		System.out.println(partition.partition(newList(), 11));
	}
	private static Node newList() {
		return new Node(3, new Node(2, new Node(2, new Node(11, new Node(7, new Node(5, new Node(11)))))));
	}
	public Node partition(Node node, int k) {
		List smaller = new List();
		List equal = new List();
		List larger = new List();
		for (Node current = node; current != null; current = current.next) {
			if (current.val < k) {
				smaller.append(current);
			} else if (current.val == k) {
				equal.append(current);
			} else {
				larger.append(current);
			}
		}
		smaller.append(equal);
		smaller.append(larger);
		return smaller.head;
	}
}
