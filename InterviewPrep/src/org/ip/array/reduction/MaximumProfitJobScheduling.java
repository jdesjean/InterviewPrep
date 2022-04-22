package org.ip.array.reduction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/maximum-profit-in-job-scheduling/">LC: 1235</a>
 */
public class MaximumProfitJobScheduling {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {120, new int[] {1,2,3,3}, new int[] {3,4,5,6}, new int[] {50,10,40,70}};
		Object[] tc2 = new Object[] {150, new int[] {1,2,3,4,6}, new int[] {3,5,10,6,9}, new int[] {20,20,100,70,60}};
		Object[] tc3 = new Object[] {6, new int[] {1,1,1}, new int[] {2,3,4}, new int[] {5,6,4}};
		Object[] tc4 = new Object[] {41, new int[] {6,15,7,11,1,3,16,2}, new int[] {19,18,19,16,10,8,19,8}, new int[] {2,9,1,19,5,7,3,19}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private final static Comparator<Event> START_COMPARATOR = (a, b) -> Integer.compare(a.start, b.start);
		private final static Comparator<Event> END_COMPARATOR = (a, b) -> Integer.compare(a.end, b.end);

		@Override
		public Integer apply(int[] a, int[] b, int[] c) {
			Event[] events = toEvent(a, b, c);
			Arrays.sort(events, START_COMPARATOR);
			PriorityQueue<Event> pq = new PriorityQueue<>(END_COMPARATOR);
			int max = 0;
			for (int i = 0; i < events.length; i++) {
				while (!pq.isEmpty() && pq.peek().end <= events[i].start) {
					max = Math.max(pq.remove().profit, max);
				}
				events[i].profit += max;
				pq.add(events[i]);
			}
			return pq.stream().reduce(max, (p, e) -> Math.max(p, e.profit), (p, e) -> Math.max(p, p));
		}
		Event[] toEvent(int[] a, int[] b, int[] c) {
			Event[] events = new Event[a.length];
			for (int i = 0; i < a.length; i++) {
				events[i] = new Event(a[i], b[i], c[i]);
			}
			return events;
		}
		static class Event {
			int start;
			int end;
			int profit;
			public Event(int start, int end, int profit) {
				super();
				this.start = start;
				this.end = end;
				this.profit = profit;
			}
			@Override
			public String toString() {
				return "Event [start=" + start + ", end=" + end + ", profit=" + profit + "]";
			}
			
		}
	}
	static class Solver implements Problem {

		private final static Comparator<Event> END_COMPARATOR = (a, b) -> Integer.compare(a.end, b.end);
				
		@Override
		public Integer apply(int[] a, int[] b, int[] c) {
			if (b.length == 0) return 0;
			int endMax = Arrays.stream(b).max().getAsInt();
			int startMax = Arrays.stream(a).max().getAsInt();
			Event[] events = toEvent(a, b, c);
			Arrays.sort(events, END_COMPARATOR);
			int[] cache = new int[endMax + 1];
			int res = 0;
			for(int i = 0; i < events.length; i++) {
				int profits = cache[events[i].start] + events[i].profit;
				res = Math.max(res, profits);
				for (int j = events[i].end; j <= startMax && profits > cache[j]; j++) {
					cache[j] = profits;
				}
			}
			
			return res;
		}
		Event[] toEvent(int[] a, int[] b, int[] c) {
			Event[] events = new Event[a.length];
			for (int i = 0; i < a.length; i++) {
				events[i] = new Event(a[i], b[i], c[i]);
			}
			return events;
		}
	}
	static class Event {
		int start;
		int end;
		int profit;
		public Event(int start, int end, int profit) {
			super();
			this.start = start;
			this.end = end;
			this.profit = profit;
		}
		
	}
	interface Problem extends TriFunction<int[], int[], int[], Integer> {
		
	}
}
