package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/nested-list-weight-sum/">LC: 339</a>
 */
public class NestedListSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {10, tc1()};
		Object[] tc2 = new Object[] {27, tc2()};
		Object[] tc3 = new Object[] {0, tc3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static List<NestedInteger> tc1() {
		//[[1,1],2,[1,1]]
		//10
		NestedInteger one = new NestedInteger(1);
		NestedInteger two = new NestedInteger(2);
		NestedInteger l1 = new NestedInteger();
		l1.add(one);
		l1.add(one);
		NestedInteger l2 = new NestedInteger();
		l2.add(one);
		l2.add(one);
		return Arrays.asList(l1, two, l1);
	}
	public static List<NestedInteger> tc2() {
		//[1,[4,[6]]]
		//27
		NestedInteger one = new NestedInteger(1);
		NestedInteger four = new NestedInteger(4);
		NestedInteger six = new NestedInteger(6);
		NestedInteger l1 = new NestedInteger();
		l1.add(six);
		NestedInteger l2 = new NestedInteger();
		l2.add(four);
		l2.add(l1);
		
		return Arrays.asList(one, l2);
	}
	public static List<NestedInteger> tc3() {
		//[0]
		//0
		return Arrays.asList(new NestedInteger(0));
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(List<NestedInteger> value) {
			int sum = 0;
			for (NestedInteger nic : value) {
				sum += applyAsInt(nic, 1);
			}
			return sum;
		}
		
		int applyAsInt(NestedInteger ni, int depth) {
			if (ni.isInteger()) {
				return ni.getInteger() * depth;
			}
			int next = depth + 1;
			int sum = 0;
			for (NestedInteger nic : ni.getList()) {
				sum += applyAsInt(nic, next);
			}
			return sum;
		}
	}
	public interface Problem extends ToIntFunction<List<NestedInteger>> {
		
	}
}
