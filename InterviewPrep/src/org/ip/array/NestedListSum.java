package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/nested-list-weight-sum/">LC: 339</a>
 */
public class NestedListSum {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				NestedListSum::tc1,
				NestedListSum::tc2,
				NestedListSum::tc3);
		Solver[] solvers = new Solver[] {new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
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
		System.out.println(solver.solve(Arrays.asList(l1, two, l1)));
	}
	public static void tc2(Solver solver) {
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
		
		System.out.println(solver.solve(Arrays.asList(one, l2)));
	}
	public static void tc3(Solver solver) {
		//[0]
		//0
		System.out.println(solver.solve(Arrays.asList(new NestedInteger(0))));
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(List<NestedInteger> list) {
			int sum = 0;
			for (NestedInteger integer : list) {
				sum += solve(integer, 1);
			}
			return sum;
		}
		
		public int solve(NestedInteger integer, int depth) {
			if (integer.isInteger()) {
				return integer.getInteger() * depth;
			}
			int sum = 0;
			for (NestedInteger nested : integer.getList()) {
				sum += solve(nested, depth + 1);
			}
			return sum;
		}
	}
	public interface Solver {
		public int solve(List<NestedInteger> list);
	}
}
