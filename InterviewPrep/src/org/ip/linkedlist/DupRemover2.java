package org.ip.linkedlist;

// EPI 2018: 7.8
public class DupRemover2 {
	public static void main(String[] s) {
		DupRemover2 remover = new DupRemover2();
		System.out.println(remover.remove(LList.testListDups()));
	}
	public Node remove(Node h) {
		for (Node n = h; n != null && n.next != null; n = n.next) {
			if (n.next.value == n.value) {
				n.next = n.next.next;
			}
		}
		return h;
	}
}
