package org.ip.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// EPI 2018: 13.7
public class IntervalMerge {
	public static void main(String[] s) {
		List<Event> events = new ArrayList<Event>(Arrays.asList(new Event[]{event(-4,-1), event(0,2), event(3,6), event(7,9), event(11,12), event(14,17)}));
		new IntervalMerge().merge(events, event(1,8));
		new IntervalMerge().merge(events, event(18,19));
		new IntervalMerge().merge(events, event(-6,-5));
		System.out.println(events);
	}
	public void merge(List<Event> list, Event e) {
		int min = e.start;
		int max = e.end;
		boolean toAdd = true;
		for (int i = 0; i < list.size();) {
			if (e.end > list.get(i).start && e.start < list.get(i).end) { //overlap
				min = Math.min(min, list.get(i).start);
				max = Math.max(max, list.get(i).end);
				list.remove(i);
			} else if (toAdd && max < list.get(i).start) {
				toAdd = false;
				list.add(i, new Event(min, max));
				min = max = -1;
				i+=2;
			} else {
				i++;
			}
		}
		if (toAdd) {
			list.add(new Event(min, max));
		}
	}
	public static Event event(int start, int end) {
		return new Event(start, end);
	}
	public static class Event {
		int start;
		int end;
		public Event(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
		@Override
		public String toString() {
			return "Event [start=" + start + ", end=" + end + "]";
		}
	}
}
