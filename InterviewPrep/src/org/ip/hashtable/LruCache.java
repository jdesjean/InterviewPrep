package org.ip.hashtable;

import java.util.HashMap;
import java.util.Map;

// EPI 2018: 12.3
public class LruCache {
	public static void main(String[] s) {
		LruCache cache = new LruCache();
		cache.insert(1, "2.00");
		cache.insert(2, "3.00");
		cache.insert(1, "3.00");
		cache.get(2);
		cache.erase(2);
	}
	Map<Integer, Node> map = new HashMap<Integer, Node>();
	Node head;
	public void insert(Integer key, String price) {
		Node node = map.get(key);
		if (node == null) {
			node = new Node(head, key, price);
			if (head != null) {
				head.prev = node;
			}
			head = node;
			map.put(key, node);
		} else if (head != node) {
			moveToHead(node);
		}
	}
	public String get(Integer key) {
		Node node = map.get(key);
		if (node != null) {
			moveToHead(node);
			return node.price;
		}
		return null;
	}
	public void erase(Integer key) {
		Node node = map.get(key);
		if (node == null) {
			return;
		}
		map.remove(key);
		if (node == head) {
			head = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		if (node.prev != null) {
			node.prev.next = node.next;
		}
		node.next = null;
		node.prev = null;
	}
	private void moveToHead(Node node) {
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		node.prev.next = node.next; // prev cannot be null because we're not head
		node.next = head;
		node.prev = null;
		head.prev = node; // head cannot be null because we have node in map
		head = node;
	}
	private static class Node {
		Node next;
		Node prev;
		int key;
		String price;
		public Node(Node next, int key, String price) {
			this.next=next;this.key=key;this.price=price;
		}
	}
}
