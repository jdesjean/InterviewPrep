package org.ip;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/lru-cache/">LC: 146</a>
 */
public class LRUCache {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {null, null, null, 1, null, -1, null, -1, 3, 4}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		TestCaseSolver[] solvers = new TestCaseSolver[] { new SolverAbstractProblem(Solver.class) };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		Map<Integer, ListNode> map = new HashMap<>();
		private int capacity;
		ListNode head = new ListNode(0,0), tail = new ListNode(0,0);

		public Solver(int capacity) {
			if (capacity <= 0) throw new IllegalArgumentException();
			head.next = tail;
			tail.prev = head;
			this.capacity = capacity;
		}

		@Override
		public void put(int key, int value) {
			ListNode pNode = map.get(key);
			if (pNode == null && map.size() >= capacity) {
				evictlru();
			}
			if (pNode != null) {
				remove(pNode);				
			}
			pNode = map.compute(key, (pkey, pvalue) -> new ListNode(key, value));
			addToTail(pNode);
		}

		@Override
		public int get(int key) {
			ListNode pNode = map.get(key);
			if (pNode != null) {
				remove(pNode);
				addToTail(pNode);
				return pNode.val;
			}
			return -1;
		}
		void remove(ListNode pNode) {
			ListNode prev = pNode.prev;
			ListNode next = pNode.next;
			pNode.prev.next = next;
			pNode.next.prev = prev;
		}
		void addToTail(ListNode pNode) {
			tail.prev.next = pNode;
			pNode.next = tail;
			pNode.prev = tail.prev;
			tail.prev = pNode;
		}
		
		void evictlru() {
			map.remove(head.next.key);
			head.next = head.next.next;
		}

		static class ListNode {
	    	public int key;
			public int val;
	    	public ListNode next;
	    	public ListNode prev;
	    	public ListNode() {}
	    	public ListNode(int key, int val) { this.key = key; this.val = val; }
	    }
	}
	
    interface Problem {
		void put(int key, int value);
		int get(int key);
	}
	
	static class SolverAbstractProblem implements TestCaseSolver {
		private Class<? extends Problem> _clazz;

		public SolverAbstractProblem(Class<? extends Problem> clazz) {
			_clazz = clazz;
		}

		@Override
		public Integer[] apply(TestCase t) {
			return t.apply(_clazz);
		}
		
	}
    interface TestCaseSolver extends Function<TestCase, Integer[]> {
    	
    }
    static class TestCase1 implements TestCase {

		@Override
		public Integer[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(Integer.TYPE).newInstance(2);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			t.put(1, 1);
			t.put(2, 2);
			int v1 = t.get(1); // 1
			t.put(3, 3);
			int v2 = t.get(2); // -1
			t.put(4, 4);
			int v3 = t.get(1); // -1
			int v4 = t.get(3); // 3
			int v5 = t.get(4); // 4
			return new Integer[] {
				null,
				null,
				null,
				v1,
				null,
				v2,
				null,
				v3,
				v4,
				v5
			};
		}
		
	}
    interface TestCase extends Function<Class<? extends Problem>, Integer[]> {
    	
    }
}
