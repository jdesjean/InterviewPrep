package org.ip.interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/employee-free-time/">LC: 759</a>
 */
public class EmployeeFreeTime {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{3,4}}, new int[][][] {{{1,2},{5,6}},{{1,3}},{{4,10}}}};
		Object[] tc2 = new Object[] {new int[][] {{5,6},{7,9}}, new int[][][] {{{1,3},{6,7}},{{2,4}},{{2,5},{9,12}}}};
		Object[] tc3 = new Object[] {new int[][] {{20,29},{30,43},{76,86}}, new int[][][] {{{43,76},{86,91}},{{17,20},{29,30}}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		for (int i = 0; i < tcs.length; i++) {
			int[][][] triple = (int[][][])tcs[i][1];
			List<List<Interval>> list = new ArrayList<>(triple.length);
			for (int j = 0; j < triple.length; j++) {
				List<Interval> list2 = new ArrayList<>(triple[j].length);
				list.add(list2);
				for (int k = 0; k < triple[j].length; k++) {
					list2.add(new Interval(triple[j][k][0], triple[j][k][1]));
				}
			}
			tcs[i][1] = list;
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<Interval> apply(List<List<Interval>> schedule) {
			PriorityQueue<Pair> pq = new PriorityQueue<>(new ScheduleComparator(schedule));
			for (int i = 0; i < schedule.size(); i++) {
				pq.add(new Pair(i, 0));
			}
			int prev = schedule.get(pq.peek().indexEmployee).get(pq.peek().indexSchedule).end;
			List<Interval> res = new ArrayList<>();
			while (!pq.isEmpty()) {
				Pair pair = pq.remove();
				Interval i1 = schedule.get(pair.indexEmployee).get(pair.indexSchedule);
				if (i1.start > prev) {
					res.add(new Interval(prev, i1.start));
				}
				prev = Math.max(prev, i1.end);
				if (pair.indexSchedule < schedule.get(pair.indexEmployee).size() - 1) {
					pq.add(new Pair(pair.indexEmployee, pair.indexSchedule + 1));
				}
			}
			return res;
		}
		static class ScheduleComparator implements Comparator<Pair> {
			private List<List<Interval>> schedule;

			public ScheduleComparator(List<List<Interval>> schedule) {
				this.schedule = schedule;
			}

			@Override
			public int compare(Pair o1, Pair o2) {
				Interval i1 = schedule.get(o1.indexEmployee).get(o1.indexSchedule);
				Interval i2 = schedule.get(o2.indexEmployee).get(o2.indexSchedule);
				return Integer.compare(i1.start, i2.start);
			}
		}
		static class Pair {
			int indexEmployee;
			int indexSchedule;
			public Pair(int indexEmployee, int indexSchedule) {
				super();
				this.indexEmployee = indexEmployee;
				this.indexSchedule = indexSchedule;
			}
			
		}
	}
	interface Problem extends Function<List<List<Interval>>, List<Interval>> {
		
	}
	static class Interval {
	    public int start;
	    public int end;

	    public Interval() {}

	    public Interval(int _start, int _end) {
	        start = _start;
	        end = _end;
	    }

		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
	    
	};
}
