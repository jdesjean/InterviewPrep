package org.ip.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

public class SnapshotArray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Integer[] {null, 0, null, 5}, new TestCase1()};
		Object[] tc2 = new Object[] { new Integer[] {null, 0, null, 6}, new TestCase2()};
		Object[] tc3 = new Object[] { new Integer[] {null, null, null, 0, 13, 1}, new TestCase3()};
		Object[] tc4 = new Object[] { new Integer[] {0, 1, 0, null, null}, new TestCase4()};
		Object[] tc5 = new Object[] { new Integer[] {null,0,1,2,15,3,4,15}, new TestCase5()};
		Object[] tc6 = new Object[] { new Integer[] {0,0,0,null,0,null,0,null}, new TestCase6()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] { new Solver(0) };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private int snap = 0;
		private List<Entry>[] array;
		static class Entry {
			int snap;
			int value;
			public Entry(int snap, int value) {
				super();
				this.snap = snap;
				this.value = value;
			}
			
		}
		static class EntryComparator implements Comparator<Entry> {

			@Override
			public int compare(Entry o1, Entry o2) {
				return Integer.compare(o1.snap, o2.snap);
			}
			
		}
		private static final EntryComparator ENTRY_COMPARATOR = new EntryComparator();

		public Solver(int length) {
			array = new ArrayList[length];
		}

		@Override
		public Integer[] apply(Function<Problem, Integer[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int length) {
			// TODO Auto-generated method stub
			return new Solver(length);
		}

		@Override
		public void set(int index, int value) {
			if (array[index] == null) {
				array[index] = new ArrayList<>();
			}
			if (!array[index].isEmpty() && array[index].get(array[index].size() - 1).snap == snap) {
				array[index].get(array[index].size() - 1).value = value;
			} else {
				array[index].add(new Entry(snap, value));
			}
		}

		@Override
		public int snap() {
			return snap++;
		}

		@Override
		public int get(int index, int snap_id) {
			if (array[index] == null) return 0;
			int idx = Collections.binarySearch(array[index], new Entry(snap_id, 0), ENTRY_COMPARATOR);
			if (idx < 0) idx = - idx - 2; // - (idx - 1) // always larger, -1 to obtain next smallest
			if (idx < 0) return 0;
			return array[index].get(idx).value;
		}
		
	}
	interface Problem extends Function<Function<Problem, Integer[]>, Integer[]> {
		Problem problem(int length);
		void set(int index, int value);
		int snap();
		int get(int index, int snap_id);
	}
	static class TestCase1 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(3);
			return new Integer[] {
					set(copy, 0, 5),
					copy.snap(),
					set(copy, 0,6),
					copy.get(0, 0)
			};
		}
		
	}
	
	static class TestCase2 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(3);
			return new Integer[] {
					set(copy, 0, 5),
					copy.snap(),
					set(copy, 1, 6),
					copy.get(1, 0)
			};
		}
		
	}
	static class TestCase3 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(1);
			return new Integer[] {
					set(copy, 0, 4),
					set(copy, 0, 16),
					set(copy, 0, 13),
					copy.snap(),
					copy.get(0, 0),
					copy.snap()
			};
		}
		
	}
	static class TestCase4 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(4);
			return new Integer[] {
					copy.snap(),
					copy.snap(),
					copy.get(3, 1),
					set(copy, 2, 4),
					set(copy, 1, 4)
			};
		}
		
	}
	static class TestCase5 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(4);
			return new Integer[] {
					set(copy, 0, 15),
					copy.snap(),
					copy.snap(),
					copy.snap(),
					copy.get(0, 2),
					copy.snap(),
					copy.snap(),
					copy.get(0, 0)
			};
		}
		
	}
	static class TestCase6 implements Function<Problem, Integer[]> {

		@Override
		public Integer[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(2);
			return new Integer[] {
					copy.snap(),
					copy.get(1, 0),
					copy.get(0, 0),
					set(copy, 1, 8),
					copy.get(1, 0),
					set(copy, 0, 20),
					copy.get(0, 0),
					set(copy, 0, 7)
			};
		}
		
	}
	static Integer set(Problem copy, int index, int value) {
		copy.set(index, value);
		return null;
	}
}
