package org.ip.interval;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.ip.Test;

/**
 * Cruise
 * 
 * 0: |----|  |--|
 * 1:    |-----|
 * 
 * 0: [1, 5, 0]
 * 1: [3, 9, 1]
 * 0: [8, 11, 0]
 * 
 * 0: 7 / 10 -> 70
 * 1: 6 / 10 -> 60
 */
public class ChannelPercentageWithinTopic {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, Arrays.asList(new Interval[] {new Interval(1,5,0), new Interval(3,9,1), new Interval(8,11,0)})};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static Comparator<Interval> START_COMPARATOR = (a, b) -> Integer.compare(a.start, b.start);
		
		@Override
		public Map<Integer, Integer> apply(List<Interval> t) {
			Map<Integer, Integer> res = new HashMap<>();
			int total = merged(t, res);
			for (Map.Entry<Integer, Integer> entry : res.entrySet()) {
				res.compute(entry.getKey(), (k, v) -> v * 100 / total);
			}
			return res;
		}
		int merged(List<Interval> intervals, Map<Integer, Integer> res) {
			Collections.sort(intervals, START_COMPARATOR);
			Deque<Interval> deque = new ArrayDeque<>();
			int size = 0;
			for (Interval interval : intervals) {
				res.compute(interval.channel, (k, v) -> (interval.end - interval.start) + (v == null ? 0 : v));
				if (!deque.isEmpty() 
						&& deque.peek().end > interval.start) {
					if (interval.end > deque.peek().end) {
						size += interval.end - deque.peek().end;
						deque.peek().end = interval.end;
					}
				} else {
					deque.add(new Interval(interval.start, interval.end, interval.channel));
					size += interval.end - interval.start;
				}
			}
			return size;
		}
	}
	interface Problem extends Function<List<Interval>, Map<Integer, Integer>> {
		
	}
	static class Interval {
	    int start;
	    int end;
	    int channel;

	    public Interval() {}

	    public Interval(int _start, int _end, int _topic) {
	        start = _start;
	        end = _end;
	        channel = _topic;
	    }

		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
	    
	};
}
