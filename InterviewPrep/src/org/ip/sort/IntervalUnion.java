package org.ip.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// EPI 2018: 13.8
public class IntervalUnion {
	public static void main(String[] s) {
		Interval[] intervals = new Interval[] {interval(0,false,3,false), interval(5,7,false), interval(9,false,11), interval(12,14), interval(1,1), interval(3,4,false), interval(7,8,false), interval(12,false,16), interval(2,4),interval(8,11,false),interval(13,false,15,false),interval(16,false,17,false)};
		System.out.println(new IntervalUnion().union(intervals));
	}
	public List<Interval> union(Interval[] intervals) {
		Arrays.sort(intervals, new IntervalComparatorStart());
		List<Interval> list = new ArrayList<Interval>();
		Interval current = null;
		for (int i = 0; i < intervals.length; i++) {
			if (current == null) {
				current = intervals[i];
			} else if (current.overlap(intervals[i])) {
				int start;
				boolean bStart;
				int end;
				boolean bEnd;
				if (current.start < intervals[i].start) {
					start = current.start;
					bStart = current.bStart;
				} else if (current.start == intervals[i].start) {
					start = current.start;
					bStart = current.bStart || intervals[i].bStart;
				} else {
					start = intervals[i].start;
					bStart = intervals[i].bStart;
				}
				if (current.end < intervals[i].end) {
					end = intervals[i].end;
					bEnd = intervals[i].bEnd;
				} else if (current.end == intervals[i].end) {
					end = current.end;
					bEnd = current.bEnd || intervals[i].bEnd;
				} else {
					end = current.end;
					bEnd = current.bEnd;
				}
				current.start = start;
				current.bStart = bStart;
				current.end = end;
				current.bEnd = bEnd;
			} else {
				list.add(current);
				current = intervals[i];
			}
		}
		list.add(current);
		return list;
	}
	public static Interval interval(int start, int end, boolean bEnd) {
		return new Interval(start, true, end, bEnd);
	}
	public static Interval interval(int start, boolean bStart, int end) {
		return new Interval(start, bStart, end, true);
	}
	public static Interval interval(int start, int end) {
		return new Interval(start, true, end, true);
	}
	public static Interval interval(int start, boolean bStart, int end, boolean bEnd) {
		return new Interval(start, bStart, end, bEnd);
	}
	public static class IntervalComparatorStart implements Comparator<Interval> {
		@Override
		public int compare(Interval o1, Interval o2) {
			return Integer.compare(o1.start, o2.start);
		}
	}
	public static class Interval {
		int start;
		boolean bStart;
		int end;
		boolean bEnd;
		public Interval(int start, boolean bStart, int end, boolean bEnd) {
			super();
			this.start = start;
			this.bStart = bStart;
			this.end = end;
			this.bEnd = bEnd;
		}

		@Override
		public String toString() {
			return "Interval [start=" + start + ", bStart=" + bStart + ", end=" + end + ", bEnd=" + bEnd + "]";
		}
		public boolean overlap(Interval interval) {
			boolean outside1 = this.start > interval.end || (this.start == interval.end && !this.bStart && !interval.bEnd);
			boolean outside2 = this.end < interval.start || (this.end == interval.start && !this.bEnd && !interval.bStart);
			return !(outside1 || outside2);
		}
	}
}
