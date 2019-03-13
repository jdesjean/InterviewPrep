package org.ip.sort;

import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SchedulerLockFree extends Scheduler {
	final BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();
	boolean run = true;
	PriorityQueue<Task> schedule = new PriorityQueue<Task>();
	public void interrupt() {
		run = false;
		super.interrupt();
	}
	public void schedule(Task event) {
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public void run() {
		while (run) {
			final long time = System.currentTimeMillis();
			while (!schedule.isEmpty() && time - schedule.peek().time >= 0) {
				schedule.remove().run();
			}
			try {
				Task taskToAdd = !schedule.isEmpty() ? queue.poll(schedule.peek().time - time, TimeUnit.MILLISECONDS) : queue.take();
				if (taskToAdd != null) {
					schedule.add(taskToAdd);
				}
			} catch (InterruptedException ie) {}
		}
	}
}