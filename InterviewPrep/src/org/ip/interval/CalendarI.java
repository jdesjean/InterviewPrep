package org.ip.interval;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/my-calendar-i/">LC: 729</a>
 */
public class CalendarI {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Boolean[] {null, true, false, true}, new TestCase1()};
		Object[][] tcs = new Object[][] { tc1};
		Problem[] solvers = new Problem[] { new SolverTreeSet(), new SolverTreeMap() };
		Test.apply(solvers, tcs);
	}
	static class SolverTreeMap implements Problem {

		TreeMap<Integer, Integer> intervals = new TreeMap<>();
		
		@Override
		public Boolean[] apply(Function<Problem, Boolean[]> t) {
			return t.apply(this);
		}

		@Override
		public boolean book(int start, int end) {
			Entry<Integer, Integer> entry = intervals.lowerEntry(end);
			if (entry == null || entry.getValue() <= start) {
				intervals.put(start, end);
				return true;
			} else {
				return false;
			}
		}
		
	}
	static class SolverTreeSet implements Problem {

		TreeSet<Interval> intervals = new TreeSet<>(new IntervalComparator());
		
		@Override
		public Boolean[] apply(Function<Problem, Boolean[]> t) {
			return t.apply(this);
		}

		@Override
		public boolean book(int start, int end) {
			for (Iterator<Interval> it = intervals.tailSet(new Interval(0, start + 1)).iterator(); it.hasNext(); ) {
				Interval next = it.next();
				if (end <= next.start) break;
				return false;
			}
			intervals.add(new Interval(start, end));
			return true;
		}
	}
	static class Interval {
		int start;
		int end;
		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
		public boolean overlap(int start, int end) {
			return !(this.end <= start || this.start >= end);
		}
	}
	public static class IntervalComparator implements Comparator<Interval> {

		@Override
		public int compare(Interval o1, Interval o2) {
			int end = Integer.compare(o1.end, o2.end);
			if (end != 0) return end;
			return Integer.compare(o1.start, o2.start);
		}
		
	}
	interface Problem extends Function<Function<Problem, Boolean[]>, Boolean[]> {
		public boolean book(int start, int end);
	}
	static class TestCase1 implements Function<Problem, Boolean[]> {

		@Override
		public Boolean[] apply(Problem t) {
			return new Boolean[] {
					null,
					t.book(10, 20),
					t.book(15, 25),
					t.book(20, 30)
			};
		}
		
	}
}
