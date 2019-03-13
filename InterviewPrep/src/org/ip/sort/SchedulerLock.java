package org.ip.sort;

import java.util.PriorityQueue;
import java.util.Timer;

public class SchedulerLock extends Scheduler {
	Timer timer;
	PriorityQueue<Task> schedule = new PriorityQueue<Task>();
	boolean run = true;
	public void interrupt() {
		run = false;
		super.interrupt();
	}
	public void schedule(Task event) {
		synchronized(this) {
			schedule.add(event);
			boolean newEventIsMin = schedule.peek() == event;
			if (newEventIsMin) {
				this.notify();
			}
		}
		
	}
	public Task peek() {
		synchronized(this) {
			return schedule.peek();
		}
	}
	public Task remove() {
		synchronized(this) {
			return schedule.remove();
		}
	}
	public void run() {
		while (run) {
			Task task;
			long time;
			synchronized (this) {
				time = System.currentTimeMillis();
				task = schedule.peek();
				while (task != null && time - task.time >= 0) {
					task.run();
					schedule.remove();
					task = schedule.peek();
				}
				try {
					if (task != null) {
						this.wait(task.time - time);
					} else {
						this.wait();
					}
				} catch (InterruptedException ie) {}
			}
		}
	}
}
