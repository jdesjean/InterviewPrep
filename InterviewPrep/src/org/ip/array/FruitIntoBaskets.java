package org.ip.array;

import java.util.HashMap;
import java.util.Map;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/fruit-into-baskets/">LC: 904</a>
 *
 */
public class FruitIntoBaskets {
	public static void main(String[] s) {
		System.out.println(new FruitIntoBaskets().max(new int[] {3,3,3,1,2,1,1,2,3,3,4}));
		System.out.println(new FruitIntoBaskets().max(new int[] {0,1,2,2}));
		System.out.println(new FruitIntoBaskets().max(new int[] {1,0,1,4,1,4,1,2,3}));
	}
	public int max(int[] tree) {
		Queue queue = new Queue(2);
		int max = 0;
		for (int i = 0; i < tree.length; i++) {
			queue.add(tree[i]);
			max = Math.max(max, queue.count());
		}
        return max;
    }
	private static class Queue {
		int count = 0;
		List list = new List();
		Map<Integer, Integer> map = new HashMap<>();
		private int max;
		public Queue(int max) {
			this.max = max;
		}
		public void add(int v) {
			list.add(v);
			if (map.containsKey(v)) {
				Integer value = map.get(v);
				map.put(v, value + 1);
			} else {
				while (map.size() >= max) {
					Node node = list.removeHead();
					Integer value = map.get(node.value);
					if (value == 1) {
						map.remove(node.value);
					} else {
						map.put(node.value, value - 1);
					}
					count--;
				}
				map.put(v, 1);
			}
			count++;
		}
		public int count() {
			return count;
		}
	}
	private static class List {
		Node head;
		Node tail;
		public Node add(int v) {
			Node next = new Node(v);
			if (tail != null) {
				tail.next = next;
				tail = next;
			} else {
				head = tail = next;
			}
			return next;
		}
		public Node removeHead() {
			Node prev = head;
			if (head == tail) {
				tail = null;
			}
			head = head.next;
			return prev;
		}
	}
	private static class Node {
		int value;
		Node next;
		public Node(int value) {
			this.value = value;
		}
	}
}
