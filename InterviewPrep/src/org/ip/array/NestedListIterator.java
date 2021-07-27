package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

public class NestedListIterator {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,1,2,1,1}, Arrays.asList(
				new NestedInteger(
						new NestedInteger(1), 
						new NestedInteger(1)), 
				new NestedInteger(2),
				new NestedInteger(
						new NestedInteger(1), 
						new NestedInteger(1))) };
		Object[] tc2 = new Object[] { new int[] {1,4,6}, Arrays.asList(
				new NestedInteger(new NestedInteger(1), 
						new NestedInteger(new NestedInteger(4), 
								new NestedInteger(6)))), };
		Object[] tc3 = new Object[] { new int[] {}, Arrays.asList(new NestedInteger()), };
		Object[] tc4 = new Object[] { new int[] {3}, Arrays.asList(new NestedInteger(new NestedInteger()), new NestedInteger(), new NestedInteger(3)), };
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class NestedIterator implements Iterator<Integer> {
		private LinkedList<NestedInteger> integers;
		private LinkedList<Integer> indexes;
		private final static NestedInteger DUMMY = new NestedInteger(0);

		public NestedIterator(List<NestedInteger> list) {
			integers = new LinkedList<NestedInteger>();
			indexes = new LinkedList<Integer>();
			NestedInteger nested = new NestedInteger();
			for (NestedInteger integer : list) {
				nested.add(integer);
			}
			add(nested);
		}
		
		void add(NestedInteger nested) {
			while (!nested.isInteger() && nested.getList().size() > 0) {
				indexes.addLast(0);
				integers.addLast(nested);
				nested = nested.getList().get(0);
			}
			
			if (nested.isInteger()) {
				integers.addLast(nested);
			} else {
				integers.addLast(DUMMY); 
				next(); // ensure top of stack is always integer
			}
		}

		@Override
		public boolean hasNext() {
			return !integers.isEmpty();
		}

		@Override
		public Integer next() {
			NestedInteger integer = integers.removeLast();
			while (!integers.isEmpty()) {
				int index = indexes.removeLast() + 1;
				if (index < integers.peekLast().getList().size()) {
					indexes.addLast(index);
					add(integers.peekLast().getList().get(index));
					break;
				} else {
					integers.removeLast();
				}
			}
			return integer.getInteger();
		}
		
	}
	interface Problem extends Function<List<NestedInteger>, List<Integer>> {
		
	}
	static class Solver implements Problem {

		@Override
		public List<Integer> apply(List<NestedInteger> t) {
			List<Integer> res = new ArrayList<>();
			for (Iterator<Integer> it = new NestedIterator(t); it.hasNext(); ) {
				res.add(it.next());
			}
			return res;
		}
		
	}
}
