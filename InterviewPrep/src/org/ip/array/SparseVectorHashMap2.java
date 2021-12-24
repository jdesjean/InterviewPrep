package org.ip.array;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/dot-product-of-two-sparse-vectors/">LC: 1570</a>
 */
public class SparseVectorHashMap2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {8, new TestCase1()};
		Object[] tc2 = new Object[] {0, new TestCase2()};
		Object[] tc3 = new Object[] {6, new TestCase3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new SparseVector()};
		Test.apply(solvers, tcs);
	}
	
    public static class SparseVector implements Problem {
    	Map<Integer, Integer> values = new HashMap<>();

		public SparseVector(int[] nums) {
    		for (int i = 0; i < nums.length; i++) {
    			if (nums[i] == 0) continue;
    			values.put(i, nums[i]);
    		}
    	}

		public SparseVector() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int applyAsInt(ToIntFunction<Problem> value) {
			return value.applyAsInt(this);
		}

		@Override
		public int dotProduct(Problem value) {
			SparseVector sparseVector = (SparseVector)value;
			Map<Integer, Integer> smallest = values.size() < (sparseVector.values).size() ? values : sparseVector.values;
			Map<Integer, Integer> largest  = values.size() < (sparseVector.values).size() ? sparseVector.values : values;
			int sum = 0;
			for (Map.Entry<Integer, Integer> entry : smallest.entrySet()) {
				sum += entry.getValue() * largest.getOrDefault(entry.getKey(), 0); 
				
			}
			return sum;
		}
    	
    }
    interface Problem extends ToIntFunction<ToIntFunction<Problem>> {
    	int dotProduct(Problem problem);
	}
	static class TestCase1 implements ToIntFunction<Problem> {

		@Override
		public int applyAsInt(Problem t) {
			Problem copy = newInstance(t, new int[] {1,0,0,2,3});
			Problem args = newInstance(t, new int[] {0,3,0,4,0});
			return copy.dotProduct(args);
		}

	}
	static class TestCase2 implements ToIntFunction<Problem> {

		@Override
		public int applyAsInt(Problem t) {
			Problem copy = newInstance(t, new int[] {0,1,0,0,0});
			Problem args = newInstance(t, new int[] {0,0,0,0,2});
			return copy.dotProduct(args);
		}
		
	}
	static class TestCase3 implements ToIntFunction<Problem> {

		@Override
		public int applyAsInt(Problem t) {
			Problem copy = newInstance(t, new int[] {0,1,0,0,2,0,0});
			Problem args = newInstance(t, new int[] {1,0,0,0,3,0,4});
			return copy.dotProduct(args);
		}
		
	}
	static Problem newInstance(Problem problem, int[] a) {
		try {
			return problem.getClass().getConstructor(int[].class).newInstance(a);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
