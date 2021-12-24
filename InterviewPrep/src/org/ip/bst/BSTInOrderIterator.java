package org.ip.bst;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;
import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/binary-search-tree-iterator/">LC: 173</a>
 */
public class BSTInOrderIterator {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3, 7, 9, 15, 20}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		TestCaseSolver[] solvers = new TestCaseSolver[] { new SolverAbstractProblem(InOrderIterator.class) };
		Test.apply(solvers, tcs);
	}
	
	static class InOrderIterator implements Problem {
		Deque<TreeNode> deque = new LinkedList<>();

		public InOrderIterator(TreeNode node) {
			addLeft(node);
		}
		
		void addLeft(TreeNode node) {
			if (node == null) return;
			deque.add(node);
			while (node.left != null) {
				deque.add(node.left);
				node = node.left;
			}
		}

		@Override
		public boolean hasNext() {
			return !deque.isEmpty();
		}

		@Override
		public int next() {
			TreeNode next = deque.removeLast();
			addLeft(next.right);
			return next.val;
		}
		
	}
	
	interface Problem {
		boolean hasNext();
		int next();
	}
	
	static class SolverAbstractProblem implements TestCaseSolver {
		private Class<? extends Problem> _clazz;

		public SolverAbstractProblem(Class<? extends Problem> clazz) {
			_clazz = clazz;
		}

		@Override
		public int[] apply(TestCase t) {
			return t.apply(_clazz);
		}
		
	}
    interface TestCaseSolver extends Function<TestCase, int[]> {
    	
    }
    static class TestCase1 implements TestCase {

		@Override
		public int[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(TreeNode.class).newInstance(TreeNode.fromBfs(new Integer[] {7, 3, 15, null, null, 9, 20, null, null, null, null}));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			List<Integer> res = new ArrayList<>();
			while (t.hasNext()) {
				res.add(t.next());
			}
			return res.stream().mapToInt(x -> x.intValue()).toArray();
		}
		
	}
    interface TestCase extends Function<Class<? extends Problem>, int[]> {
    	
    }
}
