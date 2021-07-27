package org.ip.interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href ="https://leetcode.com/problems/range-module/">LC: 715</a>
 */
public class RangeModule {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Boolean[] {null, null, true, false, true}, new TestCase1()};
		Object[] tc2 = new Object[] { new Boolean[] {null,null,null,null,null,null,null,true,true,true}, new TestCase2()};
		Object[] tc3 = new Object[] { new Boolean[] {null,null,null,true,null,null,true,true,null,null}, new TestCase3()};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		IntervalComparator comparator = new IntervalComparator();
		TreeSet<Interval> intervals = new TreeSet<>(comparator);
		
		@Override
		public Boolean[] apply(Function<Problem, Boolean[]> u) {
			return u.apply(this);
		}

		@Override
		public void addRange(int left, int right) {
			for (Iterator<Interval> it = intervals.tailSet(new Interval(0, left - 1)).iterator(); it.hasNext(); ) {
				Interval next = it.next();
				if (right < next.left) break;
				left = Math.min(left, next.left);
	            right = Math.max(right, next.right);
				it.remove();
			}
			intervals.add(new Interval(left, right));
		}

		@Override
		public boolean queryRange(int left, int right) {
			Interval iv = intervals.higher(new Interval(0, left));
	        return (iv != null && iv.left <= left && right <= iv.right);
		}

		@Override
		public void removeRange(int left, int right) {
			List<Interval> toAdd = new ArrayList<>();
			for (Iterator<Interval> it = intervals.tailSet(new Interval(0, left)).iterator(); it.hasNext(); ) {
				Interval next = it.next();
				if (right < next.left) break;
				it.remove();
				if (next.left < left) toAdd.add(new Interval(next.left, left));
	            if (right < next.right) toAdd.add(new Interval(right, next.right));
			}
			intervals.addAll(toAdd);
		}
		
	}
	static class Interval {
		int left;
		int right;
		public Interval(int start, int end) {
			super();
			this.left = start;
			this.right = end;
		}
	}
	static class IntervalComparator implements Comparator<Interval> {

		@Override
		public int compare(Interval o1, Interval o2) {
			 if (o1.right == o2.right) return o1.left - o2.left;
		     return o1.right - o2.right;
		}
		
	}
	interface Problem extends Function<Function<Problem, Boolean[]>, Boolean[]> {
		 public void addRange(int left, int right);
		 public boolean queryRange(int left, int right);
		 public void removeRange(int left, int right);
	}
	static class TestCase1 implements Function<Problem, Boolean[]> {

		@Override
		public Boolean[] apply(Problem t) {
			return new Boolean[] {
					addRange(t, 10, 20), 
					removeRange(t, 14, 16),
					t.queryRange(10, 14),
					t.queryRange(13, 15),
					t.queryRange(16, 17)
			};
		}
	}
	static class TestCase2 implements Function<Problem, Boolean[]> {

		@Override
		public Boolean[] apply(Problem t) {
			return new Boolean[] {
					null,
					addRange(t, 6, 8), 
					removeRange(t, 7, 8),
					removeRange(t, 8, 9),
					addRange(t, 8, 9),
					removeRange(t, 1, 3),
					addRange(t, 1, 8),
					t.queryRange(2, 4),
					t.queryRange(2, 9),
					t.queryRange(4, 6),
			};
		}
	}
	static class TestCase3 implements Function<Problem, Boolean[]> {

		@Override
		public Boolean[] apply(Problem t) {
			return new Boolean[] {
					null,
					removeRange(t, 4, 8), 
					addRange(t, 1, 10),
					t.queryRange(1, 7),
					addRange(t, 2, 3),
					removeRange(t, 2, 3),
					t.queryRange(8, 9),
					t.queryRange(6, 9),
					addRange(t, 2, 3),
					removeRange(t, 1, 8)
			};
		}
		
	}
	static Boolean removeRange(Problem t, int a, int b) {
		t.removeRange(a, b);
		return null;
	}
	static Boolean addRange(Problem t, int a, int b) {
		t.addRange(a, b);
		return null;
	}
}
