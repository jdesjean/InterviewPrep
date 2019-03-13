package org.ip.linkedlist;

// EPI: 8.8
public class DupRemover {
	public static void main(String[] s) {
		DupRemover remover = new DupRemover();
		System.out.println(remover.remove(LList.testListDups()));
	}
	public Node remove(Node node) {
		for (Node current = node.next, prev = node; current != null; prev = current, current = current.next) {
			if (prev.value == current.value) {
				prev.next = current.next;
			}
		}
		return node;
	}
}
