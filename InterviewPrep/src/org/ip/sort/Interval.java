package org.ip.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

// EPI 2018: 14.6
public class Interval {
	public static void main(String[] s) {
		int[] a = new int[] {5,10,15};
		int[] b = new int[] {3,6,9,12,15};
		int[] c = new int[] {8,16,24};
		System.out.println(new Interval().minimize(a,b,c));
	}
	public Event minimize(int[] a, int[] b, int[] c) {
		int[][] d = new int[][] {a,b,c};
		PriorityQueue<Pair> min = new PriorityQueue<Pair>(new ArrayComparator(d));
		min.add(new Pair(0,0));
		min.add(new Pair(1,0));
		min.add(new Pair(2,0));
		int max = Math.max(Math.max(a[0], b[0]), c[0]);
		int minInterval = Integer.MAX_VALUE;
		Event event = null;
		while (!min.isEmpty()) {
			Pair pair = min.remove();
			int diff = max - d[pair.array][pair.index];
			if (diff < minInterval) {
				event = new Event(d[pair.array][pair.index], max);
				minInterval = diff;
			}
			if (pair.index < d[pair.array].length - 1) {
				pair.index++;
				min.add(pair);
				max = Math.max(max, d[pair.array][pair.index]);
			} else { // Can only find a min interval when we get 1 element from each array
				break;
			}
		}
		return event;
	}
	public static class ArrayComparator implements Comparator<Pair> {
		private int[][] a;
		public ArrayComparator(int[][] a) {
			this.a=a;
		}
		@Override
		public int compare(Pair o1, Pair o2) {
			return Integer.compare(a[o1.array][o1.index], a[o2.array][o2.index]);
		}
	}
	public static class Pair {
		public int array;
		public int index;
		public Pair(int array, int index) {this.array=array;this.index=index;}
		public boolean equals(Pair pair) {
			return array==pair.array && index == pair.index;
		}
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
