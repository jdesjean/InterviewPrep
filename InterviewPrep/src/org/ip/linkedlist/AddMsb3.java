package org.ip.linkedlist;

public class AddMsb3 {
	public static void main(String[] s) {
		ListNode n1 = new ListNode(4, new ListNode(1, new ListNode(3)));
		ListNode n2 = new ListNode(9, new ListNode(0, new ListNode(7)));
		ListNode n3 = new ListNode(9, new ListNode(7));
		ListNode n4 = new ListNode(7, new ListNode(2, new ListNode(4, new ListNode(3))));
		ListNode n5 = new ListNode(5, new ListNode(6, new ListNode(4)));
		AddMsb3 add = new AddMsb3();
		System.out.println(add.addTwoNumbers(n1, n2));
		System.out.println(add.addTwoNumbers(n1, n3));
		System.out.println(add.addTwoNumbers(n4, n5));
	}
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int s1 = length(l1), s2 = length(l2);
        ListNode node = add(l1, l2, s1, s2);
        if (node.val > 9) {
        	ListNode ret = new ListNode(node.val / 10);
        	node.val = node.val % 10;
        	ret.next = node;
        	return ret;
        }
        return node;
    }
    public ListNode add(ListNode l1, ListNode l2, int s1, int s2) {
        if (l1 == null) return l2;
        else if (l2 == null) return l1;
        ListNode next = null;
        int value = 0;
        if (s1 < s2) {
            next = add(l1, l2.next, s1, s2 - 1);
            value = l2.val;
        } else if (s2 < s1) {
            next = add(l1.next, l2, s1 - 1, s2);
            value = l1.val;
        } else if (s1 > 0) {
            next = add(l1.next, l2.next, s1 - 1, s2 - 1);
            value = l1.val + l2.val;
        }
        if (next != null) {
        	value += next.val / 10;
        	next.val = next.val % 10;
        }
        ListNode current = new ListNode(value);
        current.next = next;
        return current;
    }
    private int length (ListNode h) {
        int count = 0;
        for (ListNode n = h; n != null; n = n.next) {
            count++;
        }
        return count;
    }
    public static class ListNode {
    	public int val;
    	ListNode next;
    	public ListNode(int val) {this.val=val;}
    	public ListNode(int val, ListNode next) {this.val=val;this.next=next;}
    }
}
