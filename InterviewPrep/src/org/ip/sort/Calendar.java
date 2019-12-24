package org.ip.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// EPI 2018: 3.6
public class Calendar {
	public static void main(String[] s) {
		Event[] events = new Event[] {event(1,5), event(6,10), event(11,13), event(14,15), event(2,7), event(8,9), event(12,15), event(4,5), event(9,17)};
		System.out.println(new Calendar().height(events));
	}
	public int height(Event[] events) {
		int max = 0;
		List<Point> list = new ArrayList<Point>(events.length * 2);
		for (int i = 0; i < events.length; i++) {
			list.add(new Point(events[i].start, true));
			list.add(new Point(events[i].end, false));
		}
		Collections.sort(list, new PointComparator());
		int current = 0;
		for (Point p : list) {
			if (p.isStart) current++;
			else current--;
			max = Math.max(max, current);
		}
		return max;
	}
	public static Event event(int start, int end) {
		return new Event(start,end);
	}
	public static class Event {
		int start;
		int end;
		public Event(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
	}
	public static class PointComparator implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			int value = Integer.compare(o1.time, o2.time);
			if (value != 0) return value;
			return Boolean.compare(o1.isStart, o2.isStart);
		}
		
	}
	public static class Point {
		int time;
		boolean isStart;
		public Point(int time, boolean isStart) {
			super();
			this.time = time;
			this.isStart = isStart;
		}
		
	}
}
