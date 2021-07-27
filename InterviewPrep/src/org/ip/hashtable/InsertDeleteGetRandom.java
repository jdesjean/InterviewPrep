package org.ip.hashtable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/insert-delete-getrandom-o1/">LC: 380</a>
 */
public class InsertDeleteGetRandom {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Object[] {null, true, false, true, 2, true, false, 2}, new TestCase1()};
		Object[] tc2 = new Object[] { new Object[] {null,false,false,true,0,true,true}, new TestCase2()};
		Object[] tc3 = new Object[] { new Object[] {null,true,true,true,true,true,2}, new TestCase3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		Map<Integer, Integer> set = new HashMap<Integer, Integer>();
		List<Integer> list = new ArrayList<>();
		Random random = new Random(0);
		
		@Override
		public Object[] apply(Function<Problem, Object[]> t) {
			return t.apply(this);
		}

		@Override
		public boolean insert(int value) {
			if (!set.containsKey(value)) {
				set.put(value, list.size());
				list.add(value);
				return true;
			}
			return false;
		}

		@Override
		public boolean remove(int v) {
			Integer index = set.get(v);
			if (index == null) return false;
			set.remove(v);
			if (index != list.size() - 1) {
				set.remove(list.get(list.size() - 1));
				set.put(list.get(list.size() - 1), index);
			}
			Collections.swap(list, index, list.size() - 1);
			list.remove(list.size() - 1);
			return true;
		}

		@Override
		public int getRandom() {
			return list.get(random.nextInt(list.size()));
		}

	}
	interface Problem extends Function<Function<Problem, Object[]>, Object[]> {
		boolean insert(int v);
		boolean remove(int v);
		int getRandom();
	}
	static class TestCase1 implements Function<Problem, Object[]> {

		@Override
		public Object[] apply(Problem t) {
			try {
				Problem copy = t.getClass().newInstance();
				return new Object[] {
						null,
						copy.insert(1),
						copy.remove(2),
						copy.insert(2),
						copy.getRandom(),
						copy.remove(1),
						copy.insert(2),
						copy.getRandom()
				};
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	static class TestCase2 implements Function<Problem, Object[]> {

		@Override
		public Object[] apply(Problem t) {
			try {
				Problem copy = t.getClass().newInstance();
				return new Object[] {
						null,
						copy.remove(0),
						copy.remove(0),
						copy.insert(0),
						copy.getRandom(),
						copy.remove(0),
						copy.insert(0)
				};
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	static class TestCase3 implements Function<Problem, Object[]> {

		@Override
		public Object[] apply(Problem t) {
			try {
				Problem copy = t.getClass().newInstance();
				return new Object[] {
						null,
						copy.insert(0),
						copy.insert(1),
						copy.remove(0),
						copy.insert(2),
						copy.remove(1),
						copy.getRandom()
				};
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
}
