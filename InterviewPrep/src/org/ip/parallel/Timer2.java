package org.ip.parallel;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// EPI 2018: 19.8
public class Timer2 implements Runnable {
	public static void main(String[] s) {
		long now = System.currentTimeMillis();
		Timer2 timer = new Timer2();
		Thread thread = new Thread(timer);
		thread.start();
		Random random = new Random(0);
		for (int i = 0; i < 10; i++) {
			int dt = random.nextInt(3) * 1000;
			final int value = i;
			timer.schedule("" + i, now + dt, new Runnable() {
				@Override
				public void run() {
					System.out.println(dt + " " + value);
				}
			});
		}
		timer.cancel("" + 3);
		try {
			Thread.sleep(3000);
			thread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	BlockingQueue<IEvent> queue = new LinkedBlockingQueue<IEvent>();
	PriorityQueue<Event> minHeap = new PriorityQueue<Event>();
	Set<String> deleted = new HashSet<String>();
	public void schedule(String schedule, long time, Runnable runnable) {
		queue.add(new Event(schedule, time, runnable));
	}
	public void cancel(String schedule) {
		queue.add(new EventDelete(schedule));
	}
	@Override
	public void run() {
		long timeout = Long.MAX_VALUE;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		while (!Thread.interrupted()) {
			try {
				IEvent qevent = queue.poll(timeout, unit);
				if (qevent != null) {
					if (qevent instanceof Event) {
						minHeap.add((Event)qevent);
					} else {
						deleted.add(((EventDelete)qevent).schedule);
					}
				}
				if (!minHeap.isEmpty()) {
					Event event = minHeap.peek();
					
					long now = System.currentTimeMillis();
					if (event.time <= now) {
						event = minHeap.remove();
						if (!deleted.contains(event.schedule)) {
							event.task.run(); // Could do this in thread pool as well
						} else {
							deleted.remove(event.schedule);
						}
					}
				}
				long next = minHeap.isEmpty() ? Long.MAX_VALUE : minHeap.peek().time;
				timeout = Math.max(next - System.currentTimeMillis(), 0L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static class Event implements Comparable<Event>, IEvent {
		long time;
		Runnable task;
		private String schedule;
		public Event(String schedule, long time, Runnable task) {
			super();
			this.schedule=schedule;
			this.time = time;
			this.task = task;
		}
		@Override
		public int compareTo(Event o) {
			return Long.compare(time, o.time);
		}
		
	}
	public static class EventDelete implements IEvent{
		String schedule;
		public EventDelete(String schedule) {
			this.schedule = schedule;
		}
	}
	public interface IEvent {}
}
