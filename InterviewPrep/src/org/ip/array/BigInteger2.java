package org.ip.array;

import org.ip.array.BigInteger.LL;
import org.ip.array.BigInteger.Node;

//EPI 2018: 5.3
public class BigInteger2 {
	public static void main(String[] s) {
		test2();
	}
	public static void test2() {
		LL list1 = new LL();
		list1.add(1);
		list1.add(9);
		LL list2 = new LL();
		list2.add(7);
		list2.add(6);
		System.out.println(new BigInteger2().multiply(list1, list2));
	}
	public static void test1() {
		LL list1 = new LL();
		list1.add(1);
		list1.add(9);
		list1.add(3);
		list1.add(7);
		list1.add(0);
		list1.add(7);
		list1.add(7);
		list1.add(2);
		list1.add(1);
		LL list2 = new LL();
		list2.add(7);
		list2.add(6);
		list2.add(1);
		list2.add(8);
		list2.add(3);
		list2.add(8);
		list2.add(2);
		list2.add(5);
		list2.add(7);
		list2.add(2);
		list2.add(8);
		list2.add(7);
		System.out.println(new BigInteger2().multiply(list1, list2));
	}
	public LL multiply(LL b1, LL b2) {
		int sign = b1.head != null && b2.head != null && b1.head.value * b2.head.value < 0 ? -1 : 1;
		LL b3 = new LL();
		int carry = 0;
		int i = 0;
		for (Node n2 = b2.tail; n2 != null;n2 = n2.previous) {
			Node n3 = b3.tail;
			for (int j = 0; j < i && n3 != null; j++) {
				n3 = n3.previous;
			}
			for (Node n1 = b1.tail; n1 != null;n1 = n1.previous) {
				int value = Math.abs(n1.value) * Math.abs(n2.value) + carry;
				if (n3 != null) {
					value = value + n3.value;
				}
				carry = value / 10;
				value = value % 10;
				
				if (n3 != null) {
					n3.value = value;
					n3 = n3.previous;
				} else {
					b3.prepend(value);
				}
			}
			if (carry > 0) {
				if (n3 != null) {
					n3.value = carry;
					n3 = n3.previous;
				} else {
					b3.prepend(carry);
				}
				carry = 0;
			}
			i++;
		}
		if (b3.head != null) {
			b3.head.value *= sign;
		}
		return b3;
	}
}

