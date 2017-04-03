package org.ip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intervals {
	public static void main(String[] s) {
		System.out.println(merge(new Interval[]{i(1,3),i(2,4),i(5,7),i(6,8)}));
	}
	public static Interval i(int start, int end) {
		return new Interval(start,end);
	}
	public static class Interval implements Comparable<Interval>{
		public int start;
		public int end;
		public Interval(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
		@Override
		public int compareTo(Interval o) {
			if (o == null) return -1;
			else return this.start - o.start;
		}
		@Override
		public Object clone() {
			return i(start,end);
		}
		public boolean overlap(Interval interval) {
			return isWithin(start,end-1,interval.start) || isWithin(start+1,end,interval.end);
		}
		public void extend(Interval interval) {
			start = Math.min(start, interval.start);
			end = Math.max(end, interval.end);
		}
		public static boolean isWithin(int left, int right, int value) {
			return left <= value && value <= right;
		}
	}
	public static List<Interval> merge(Interval[] intervals) {
		Arrays.sort(intervals);
		List<Interval> cInterval = new ArrayList<Interval>();
		for (int i = 0; i < intervals.length; i++) {
			Interval interval = intervals[i];
			if (cInterval.isEmpty()) cInterval.add((Interval)interval.clone());
			else {
				Interval prev = cInterval.get(cInterval.size()-1);
				if (prev.overlap(interval)) prev.extend(interval);
				else cInterval.add((Interval)interval.clone());
			}
		}
		return cInterval;
	}
}
