package org.ip.linkedlist;

public class LList {
	public static void main(String[] s) {
		testReverse();
	}
	public static void testAddMsb() {
		Node head = addMsb(testList1(),testList6());
		println(head);
		head = addMsb(testList8(),testList9());
		println(head);
	}
	public static void testAddLsb() {
		Node head = addLsb(reverse(testList1()),reverse(testList6()));
		println(head);
	}
	public static void testPartition() {
		Node head = partition(testList6(),3);
		println(head);
	}
	public static void testIsPalindrome() {
		System.out.println(isPalindrome(testList6()));
		System.out.println(isPalindrome(testList7()));
		System.out.println(isPalindrome(testList1()));
	}
	public static void testCyclingShift() {
		for (int i = 0; i <= 5; i++) {
			Node head = testList4();
			head = cyclicShift(head,i);
			println(head);
		}
	}
	public static void testEvenOdd() {
		Node h = testList5();
		evenOdd(h);
		println(h);
	}
	public static void testRemoveDups() {
		Node h = testListDups();
		removeDuplicate(h);
		println(h);
	}
	public static void testDeleteKth() {
		Node h = testList4();
		deleteKth(h,1);
		println(h);
	}
	public static void testDelete() {
		Node h = testList4();
		delete(h.next);
		println(h);
	}
	public static void testHasOverlap() {
		Node n1 = node(5,node(7,node(9,node(11))));
		Node n2 = node(2,n1);
		Node n3 = hasOverlap(n1,n2);
		if (n3 != null) System.out.println(n3.val);
		else System.out.println(false);
	}
	public static void testHasOverlap2() {
		Node n1 = node(0,node(1,testListCycle()));
		Node n2 = node(2,n1);
		Node n3 = hasOverlap(n1,n2);
		if (n3 != null) System.out.println(n3.val);
		else System.out.println(false);
	}
	public static void testHasCycle() {
		Node h = testList3();
		Node n = hasCycle(h);
		if (n != null) System.out.print(n.val);
		else System.out.println(false);
	}
	public static void testReverse() {
		Node h = testList2();
		h = reverse(h,1,3);
		println(h);
	}
	public static void testMerge() {
		Node h1 = testList1();
		Node h2 = node(3,node(11));
		Node h3 = merge(h1,h2);
		println(h3);
	}
	public static Node testList1() {
		return node(2,node(5,node(7)));
	}
	public static Node testList11() {
		return node(3,node(11));
	}
	public static Node testList2() {
		return node(2,testList4());
	}
	public static Node testList3() {
		return node(2,testListCycle());
	}
	public static Node testList4() {
		return node(5,node(7,node(9,node(11))));
	}
	public static Node testList6() {
		return node(2,node(3,node(4,node(3,node(2)))));
	}
	public static Node testList7() {
		return node(2,node(3,node(3,node(2))));
	}
	public static Node testListDups() {
		return node(2,node(2,node(3,node(5,node(7,node(11,node(11))))))); 
	}
	public static Node testList5() {
		return node(0,node(1,node(2,node(3,node(4,node(5, node(6))))))); 
	}
	public static Node testList55() {
		return node(0,node(1,node(2,node(3,node(4,node(5, node(6, new Node(7)))))))); 
	}
	public static Node testList8() {
		return node(9,node(3,node(4,node(3))));
	}
	public static Node testList9() {
		return node(9,node(5,node(7)));
	}
	public static Node testListCycle() {
		Node node = testList4();
		node.next.next.next.next = node;
		return node;
	}
	public static Node node(int value) {
		return new Node(value);
	}
	public static Node node(int value, Node next) {
		return new Node(value,next);
	}
	public static void println(Node h1) {
		for (Node current = h1; current != null; current = current.next) {
			if (current != h1) System.out.print(',');
			System.out.print(current.val);
		}
		System.out.println();
	}
	public static int length(Node h) {
		int count = 0;
		for (Node current = h; current != null; current = current.next){count++;}
		return count;
	}
	public static Node reverse(Node h) {
		return reverse(h,0,length(h)-1);
	}
	public static Node advance(Node h, int count) {
		if (count < 0) return null;
		Node current = h;
		for (; current != null && count > 0; current = current.next) {
			count--;
		}
		return current;
	}
	public static Node reverse(Node h, int left, int right) {
		if (left == right) return h;
		
		Node dummy = node(0,h);
		Node sublistHead = advance(dummy,left);
		for (Node current = sublistHead.next; current != null && left < right; left++) {
			Node next = current.next;
			current.next = next.next;
			next.next = sublistHead.next;
			sublistHead.next = next;
		}
		return dummy.next;
	}
	public static Node merge(Node h1, Node h2) {
		if (h1 == null) return h2;
		else if (h2 == null) return h1;
		Node head;
		if (h1.val <= h2.val) {
			head = h1;
			h1 = h1.next;
		} else {
			head = h2;
			h2 = h2.next;
		}
		Node current = head;
		while (h1 != null && h2 != null) {
			if (h1.val <= h2.val) {
				current.next = h1;
				h1 = h1.next;
			} else {
				current.next = h2;
				h2 = h2.next;
			}
			current = current.next;
		}
		if (h1 != null) current.next = h1;
		else if (h2 != null) current.next = h2;
		
		return head;
	}
	public static Node hasCycle(Node h) {
		Node slow = h, fast = h;
		for (; fast != null && fast.next != null;) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) break;
		}
		if (slow != fast) return null;
		for (Node current = h; current != fast; current = current.next, fast = fast.next) {}
		return fast;
	}
	public static Node hasOverlap(Node h1, Node h2) {
		Node cycle1 = hasCycle(h1);
		Node cycle2 = hasCycle(h2);
		if ((cycle1 == null && cycle2 != null) || (cycle1 != null && cycle2 == null)) return null;
		
		if (cycle1 == null && cycle2 == null) {
			int l1 = length(h1);
			int l2 = length(h2);
			if (l1 < l2) {
				h2 = advance(h2, l2-l1);
			} else if (l2 < l1) {
				h1 = advance(h1, l1-l2);
			}
			Node n1 = h1, n2 = h2;
			for (;n1 != n2 && n1 != null && n2 != null;n1 = n1.next, n2 = n2.next){}
			return n1 == n2 ? n1 : null;
		} else {
			Node current = cycle1.next;
			for (;current != cycle2 && current != cycle1; current = current.next) {}
			return current == cycle2 ? current : null;  
		}
		
	}
	public static void delete(Node node) {
		if (node == null || node.next == null) return;
		node.val = node.next.val;
		node.next = node.next.next;
	}
	public static void deleteKth(Node h, int k) {
		Node start = advance(h,k);
		if (start == null) return ;
		if (start.next == null) {
			delete(h);
			return;
		}
		Node slow = h;
		for (Node fast = start.next; fast != null && fast.next != null; fast = fast.next, slow = slow.next) {}
		slow.next = slow.next.next;
	}
	public static void removeDuplicate(Node h) {
		for (Node current = h.next, prev = h; current != null; current = current.next) {
			if (current.val == prev.val) {
				prev.next = current.next;
			} else {
				prev = prev.next;
			}
		}
	}
	public static Node cyclicShift(Node h, int k) {
		if (k == 0) return h;
		int length = 1;
		Node tail = h;
		for (; tail != null && tail.next != null; tail = tail.next) {
			length++;
		}
		k %= length;
		if (k  == 0) return h;
		tail.next = h;
		int kth = length - k - 1;
		Node tailNew = advance(h,kth);
		Node headNew = tailNew.next;
		tailNew.next = null;
		
		return headNew;
	}
	public static Node half(Node h) {
		Node slow = h;
		for (Node fast = h; fast != null && fast.next != null;) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}
	public static boolean isPalindrome(Node h) {
		int length = length(h);
		Node half = advance(h, (length-1)/2);
		Node reversed =  reverse(half.next);
		half.next = reversed;
		int itr = length / 2;
		for (Node first = h, second = reversed; itr > 0; itr--) {
			if (first.val !=second.val) return false;
			first = first.next;
			second = second.next;
		}
		return true;
	}
	public static Node partition(Node h, int pivot) {
		Node smallerHead = node(0), smallerTail = smallerHead;
		Node equalHead = node(0), equalTail = equalHead;
		Node largerHead = node(0), largerTail = largerHead;
		for (Node current = h; current != null; current = current.next) {
			if (current.val < pivot) {
				smallerTail.next = current;
				smallerTail = current;
			} else if (current.val == pivot) {
				equalTail.next = current;
				equalTail = current;
			} else {
				largerTail.next = current;
				largerTail = current;
			}
		}
		Node head = firstNotNull(smallerHead.next,equalHead.next,largerHead.next);
		if (smallerHead.next != null) {
			head = smallerHead.next;
			if (equalHead.next != null) {
				smallerTail.next = equalHead.next;
			} else if (largerHead.next != null) {
				smallerTail.next = largerHead.next;
			} else {
				smallerTail.next = null;
			}
		}
		if (equalHead.next != null && largerHead.next != null) {
			equalTail.next = largerHead.next;
		} else {
			equalTail.next = null;
		}
		largerTail.next = null;
		return head;
	}
	public static Node firstNotNull(Node h1, Node h2, Node h3) {
		return h1 != null ? h1 : h2 != null ? h2 : h3;
	}
	public static void evenOdd(Node h) {
		if (h == null) return;
		Node hEven = h, tailEven = h;
		Node hOdd = h.next, tailOdd = h.next;
		for (;tailEven != null && tailEven.next != null && tailOdd != null && tailOdd.next != null;) {
			tailEven.next = tailOdd.next;
			tailEven = tailOdd.next;
			tailOdd.next = tailEven.next;
			tailOdd = tailOdd.next;
		}
		tailEven.next = hOdd;
	}
	private static class NodeCarry {
		public final Node node;
		public final int carry;
		public NodeCarry(Node node, int carry){this.node=node;this.carry=carry;}
	}
	public static Node addMsb(Node h1, Node h2) {
		int l1 = length(h1);
		int l2 = length(h2);
		NodeCarry nodeCarry = getNodeCarry(l1,l2,h1,h2);
		Node head = null;
		if (nodeCarry.carry > 0) {
			head = node(nodeCarry.carry,nodeCarry.node); 
		} else {
			head = nodeCarry.node;
		}
		return head;
	}
	private static NodeCarry getNodeCarry(int l1, int l2, Node h1, Node h2) {
		if (h1 == null && h2 == null) return new NodeCarry(null,0);
		else if (l1 <= 0 && l2 <= 0) return new NodeCarry(null,0);
		NodeCarry nodeCarry = null;
		int v1 = 0;
		int v2 = 0;
		if (l1 < l2) {
			nodeCarry = getNodeCarry(l1,l2-1,h1,h2.next);
			v2 = h2 != null ? h2.val : 0;
		} else if (l1 > l2){
			nodeCarry = getNodeCarry(l1-1,l2,h1.next,h2);
			v1 = h1 != null ? h1.val : 0;
		} else {
			nodeCarry = getNodeCarry(l1-1,l2-1,h1.next,h2.next);
			v1 = h1 != null ? h1.val : 0;
			v2 = h2 != null ? h2.val : 0;
		}
		int value = nodeCarry.carry + v1 + v2;
		int digit = value % 10;
		int carry = value / 10;
		return new NodeCarry(node(digit,nodeCarry.node),carry);
	}
	public static Node addLsb(Node h1, Node h2) {
		int carry = 0;
		Node head = node(0), tail = head;
		for (Node n1 = h1, n2 = h2; n1 != null || n2 != null || carry > 0;) {
			int value = carry;
			if (n1 != null) {
				value += n1.val;
				n1 = n1.next;
			}
			if (n2 != null) {
				value += n2.val;
				n2 = n2.next;
			}
			int digit = value % 10;
			carry = value / 10;
			tail.next = node(digit);
			tail = tail.next;
		}
		return head.next;
	}
}
