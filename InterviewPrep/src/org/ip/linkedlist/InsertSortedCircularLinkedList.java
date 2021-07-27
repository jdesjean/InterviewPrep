package org.ip.linkedlist;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/">LC: 708</a>
 */
public class InsertSortedCircularLinkedList {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {new int[] {3,4,1,2}, new int[] {3,4,1}, 2};
		Object[] tc2 = new Object[] {new int[] {1}, new int[] {}, 1};
		Object[] tc3 = new Object[] {new int[] {1,0}, new int[] {1}, 0};
		Object[] tc4 = new Object[] {new int[] {1,1}, new int[] {1}, 1};
		Object[] tc5 = new Object[] {new int[] {1,2,3,5}, new int[] {1,3,5}, 2};
		Object[] tc6 = new Object[] {new int[] {3,5,6,1}, new int[] {3,5,1}, 6};
		Object[] tc7 = new Object[] {new int[] {3,5,6}, new int[] {3,5}, 6};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6, tc7);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			Utils.print((int[])tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(Node.class).newInstance(from((int[])tc[1]));
				Utils.print(to((function.apply((Integer) tc[2]))));
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private Node head;

		public Solver(Node node) {
			head = node;
		}

		@Override
		public Node apply(Integer t) {
			if (head == null) {
				head = new Node(t);
				head.next = head;
			} else {
				Node current = head;
				for (; true; current = current.next) {
					if (current.next == head) {
						break;
					} else if (current.val <= t && t <= current.next.val) {
						break;
					} else if (current.val > current.next.val && t >= current.val) { // max
						break;
					} else if (current.val > current.next.val && t <= current.next.val) { // min
						break;
					}
				}
				Node next = current.next;
				current.next = new Node(t);
				current.next.next = next;
			}
			return head;
		}
		
	}
	
	public interface Problem extends Function<Integer, Node> {
		
	}
	
	static Node from(int[] v) {
		Node head = new Node(0);
		Node current = head;
		for (int i = 0; i < v.length; i++) {
			Node next = new Node(v[i]);
			current.next = next;
			current = next;
		}
		current.next = head.next;
		return head.next;
	}
	static int[] to(Node node) {
		List<Integer> list = new ArrayList<>();
		for (Node current = node; current != null; current = current.next) {
			list.add(current.val);
			if (current.next == node) break;
		}
		return list.stream().mapToInt(Integer::intValue).toArray();
	}
}
