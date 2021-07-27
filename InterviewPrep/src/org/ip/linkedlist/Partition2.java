package org.ip.linkedlist;

// EPI 2018: 7.12
public class Partition2 {
	public static void main(String[] s) {
		Partition2 partition = new Partition2();
		System.out.println(partition.partition(newList(), 2));
		System.out.println(partition.partition(newList(), 3));
		System.out.println(partition.partition(newList(), 5));
		System.out.println(partition.partition(newList(), 7));
		System.out.println(partition.partition(newList(), 11));
	}
	public Node partition(Node h, int p) {
		List l1 = new List();
		List l2 = new List();
		List l3 = new List();
		for (; h != null; h = h.next) {
			if (h.val < p) {
				l1.append(h);
			} else if (h.val == p) {
				l2.append(h);
			} else {
				l3.append(h);
			}
		}
		l1.append(l2);
		l1.append(l3);
		return l1.head;
	}
	private static Node newList() {
		return new Node(3, new Node(2, new Node(2, new Node(11, new Node(7, new Node(5, new Node(11)))))));
	}
}
