package org.ip.linkedlist;

//EPI 2018: 7.2
public class Reverser2 {
	public static void main(String[] s) {
		Reverser2 reverse = new Reverser2();
		Node reversed = reverse.reverse(LList.testList2(), 1, 3);
		LList.println(reversed);
	}
	public Node reverse(Node h, int i, int j) {
		Node prev = null;
		Node n = h;
		for (int k = 0; k <= j && n != null; k++) {
			if (k < i) continue;
			Node tmp = n.next;
			n.next = prev;
			prev = n;
			n = tmp;
		}
		return prev;
	}
}
