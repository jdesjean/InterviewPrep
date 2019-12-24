package org.ip.honor;

import java.util.function.IntConsumer;

import org.ip.queue.QueueWithMax2;

// EPI 24.12
public class SlidingWindowMax {
	public static void main(String[] s) {
		TimeValue[] a = new TimeValue[] {tv(0,1),tv(2,2),tv(3,3),tv(5,3),tv(6,3),tv(8,2),tv(9,2),tv(14,1)};
		new SlidingWindowMax().solve(x -> System.out.println(x), a, 3);
	}
	QueueWithMax2<TimeValue> queue = new QueueWithMax2<TimeValue>();
	public void solve(IntConsumer consumer, TimeValue[] a, int k) {
		queue.enqueue(a[0]);
		for (int i = 1; i < a.length; i++) {
			queue.enqueue(a[i]);
			while (a[i].time - queue.peek().time > k) {
				queue.dequeue();
			}
			consumer.accept(queue.max().value);
		}
	}
	public static TimeValue tv(int time, int value) {
		return new TimeValue(time, value); 
	}
	public static class TimeValue implements Comparable<TimeValue> {
		int time;
		int value;
		public TimeValue(int time, int value) {
			super();
			this.time = time;
			this.value = value;
		}
		@Override
		public int compareTo(TimeValue o) {
			return Integer.compare(value, o.value);
		}
		
	}
}
