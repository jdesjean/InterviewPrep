package org.ip.stream;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/product-of-the-last-k-numbers/">LC: 1352</a>
 */
public class ProductLastK {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {null,null,null,null,null,20,40,0,null,32}, new TestCase1()};
		Object[] tc2 = new Object[] {new Integer[] {null,null,null,0,null,null,null,0,1728,0,null,null}, new TestCase2()};
		Object[] tc3 = new Object[] {new Integer[] {null,1,1,1,null,null,null}, new TestCase3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		TestCaseSolver[] solvers = new TestCaseSolver[] { new SolverAbstractProblem(Solver.class) };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		final List<Integer> products = new ArrayList<>(Arrays.asList(new Integer[] {1}));
		public Solver() {}
		
		@Override
		public void add(int key) {
			if (key == 0) {
				products.clear();
				products.add(1);
				return;
			}
			int prev = products.get(products.size() - 1);
			products.add(prev * key);
		}

		@Override
		public int get(int key) {
			if (products.size() <= key) return 0;
			int prev = products.get(products.size() - key - 1);
			return products.get(products.size() - 1) / prev;
		}
		
	}
	interface Problem {
		void add(int key);
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
				t = clazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			IntFunction<Integer> add = (n) -> {
				t.add(n);
				return null;
			};
			return new Integer[] {
				add.apply(3),
				add.apply(0),
				add.apply(2),
				add.apply(5),
				add.apply(4),
				t.get(2),
				t.get(3),
				t.get(4),
				add.apply(8),
				t.get(2)
			};
		}
		
	}
    static class TestCase2 implements TestCase {

		@Override
		public Integer[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			IntFunction<Integer> add = (n) -> {
				t.add(n);
				return null;
			};
			return new Integer[] {
				add.apply(0),
				add.apply(0),
				add.apply(9),
				t.get(3),
				add.apply(8),
				add.apply(3),
				add.apply(8),
				t.get(5),
				t.get(4),
				t.get(6),
				add.apply(8),
				add.apply(8),
			};
		}
		
	}
    static class TestCase3 implements TestCase {

		@Override
		public Integer[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			IntFunction<Integer> add = (n) -> {
				t.add(n);
				return null;
			};
			return new Integer[] {
				add.apply(1),
				t.get(1),
				t.get(1),
				t.get(1),
				add.apply(7),
				add.apply(6),
				add.apply(7),
			};
		}
		
	}
    interface TestCase extends Function<Class<? extends Problem>, Integer[]> {
    	
    }
}
