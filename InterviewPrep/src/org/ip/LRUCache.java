package org.ip;

import java.util.HashMap;

/**
 * <a href="https://leetcode.com/problems/lru-cache/">LC: 146</a>
 */
public class LRUCache {
	public static void main(String[] s) {
		tc2();
		System.out.println();
		tc1();
	}
	public static void tc1() {
		var cache = new LRUCache(2);
		cache.put(1, 1);
		cache.put(2, 2);
		System.out.println(cache.get(1));
		cache.put(3, 3);
		System.out.println(cache.get(2));
		cache.put(4, 4);
		System.out.println(cache.get(1));
		System.out.println(cache.get(3));
		System.out.println(cache.get(4));
	}
	public static void tc2() {
		var cache = new LRUCache(1);
		cache.put(2, 1);
		System.out.println(cache.get(2));
		cache.put(3, 2);
		System.out.println(cache.get(2));
		System.out.println(cache.get(3));
	}
	private HashMap<Integer, ListNode> map;
	private int capacity;
	private List list;

	public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;
        this.list = new List();
    }
    
    public int get(int key) {
    	ListNode node = map.get(key);
    	if (node == null) return -1;
    	list.moveToHead(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        ListNode node = map.get(key);
        if (node == null) {
        	node = new ListNode(key, value);
        	map.put(key, node);
        	list.add(node);
        } else {
        	node.val = value;
        	list.moveToHead(node);
        }
        if (map.size() <= capacity) return;
        ListNode tail = list.removeTail();
        if (tail == null) return;
        map.remove(tail.key);
    }
    static class List {
    	ListNode head = new ListNode(0, 0);
    	ListNode tail = new ListNode(0, 0);
    	public List() {
    		head.next = tail;
    		tail.prev = head;
    	}
    	public void add(int key, int val) {
    		add(new ListNode(key, val));
    	}
    	public void moveToHead(ListNode node) {
    		removeNode(node);
    		add(node);
    	}
    	public void add(ListNode node) {
    		node.prev = head;
    		node.next = head.next;
    		head.next.prev = node;
    		head.next = node;
    	}
    	public void removeNode(ListNode node) {
    		ListNode prev = node.prev;
		    ListNode next = node.next;

		    prev.next = next;
		    next.prev = prev;
    	}
    	public ListNode removeTail() {
    		ListNode pTail = tail.prev;
    		if (pTail == head) return null;
    		removeNode(tail.prev);
    		return pTail;
    	}
    }
    static class ListNode {
    	public Object key;
		public int val;
    	public ListNode next;
    	public ListNode prev;
    	public ListNode() {}
    	public ListNode(int key, int val) { this.key = key; this.val = val; }
    }
}
