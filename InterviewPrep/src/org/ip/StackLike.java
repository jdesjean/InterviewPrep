package org.ip;

import java.util.SortedMap;
import java.util.TreeMap;

// LinkedIn 10/3/2019
// popMax
public class StackLike<T extends Comparable<T>> {
	public static void main(String[] s) {
		t1();
		System.out.println(" ");
		t2();
	}
	public static void t1() {
		StackLike stack = new StackLike();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		while (!stack.isEmpty()) {
			System.out.println(stack.popMax());
		}
	}
	public static void t2() {
		StackLike stack = new StackLike();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(3);
		stack.push(2);
		stack.push(1);
		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
	}
	SortedMap<T, Node> map = new TreeMap<T, Node>();
	Node tail;
	public void push(T t) {
		Node list = map.get(t);
		Node next = new Node(t);
		addLast(next);
		next.prev = list;
		map.put(t, next);
	}
	private T pop() {
		if (map.isEmpty()) throw new RuntimeException("empty");
		Node last = removeLast();
		Node list = map.get(last.value);
		Node prev = list.prev;
		list.prev = null;
		if (prev != null) {
			map.put(last.value, prev);
		} else {
			map.remove(last.value);
		}
		return last.value;
	}
	private T peekMax() {
		if (map.isEmpty()) throw new RuntimeException("empty");
		return map.lastKey();
	}
	private T popMax() {
		if (map.isEmpty()) throw new RuntimeException("empty");
		T t = map.lastKey();
		Node list = map.get(t);
		Node prev = list.prev;
		list.prev = null;
		if (prev != null) {
			map.put(t, prev);
		} else {
			map.remove(t);
		}
		remove(list);
		return t;
	}
	private void addLast(Node next) {
		next.left = tail;
		if (tail != null) {
			tail.right = next;
		}
		tail = next;
	}
	private Node removeLast() {
		Node prev = tail;
		Node next = tail.left;
		if (next != null) {
			next.right = null;
		}
		tail.left = null;
		tail = next;
		return prev;
	}
	private void remove(Node node) {
		Node prev = node.left;
		Node next = node.right;
		if (prev != null) {
			prev.right = next;
		}
		if (next != null) {
			next.left = prev;
		}
		node.left = null;
		node.right = null;
	}
	public boolean isEmpty() {
		return map.isEmpty();
	}
	private class Node {
		Node left;
		Node right;
		Node prev;
		T value;
		public Node(T t) {
			this.value=t;
		}
	}
}
