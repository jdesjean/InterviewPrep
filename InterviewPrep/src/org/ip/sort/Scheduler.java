package org.ip.sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Scheduler extends Thread {
	public abstract void schedule(Task task);
	public static void main(String[] s) {
		Scheduler[] schedulers = new Scheduler[]{new SchedulerLock(), new SchedulerLockFree()};
		for (Scheduler scheduler : schedulers) {
			System.out.println(scheduler);
			long time = System.currentTimeMillis();
			for (int i = 3; i >= 0; i--) {
				scheduler.schedule(new Task(time + i * 1000, "T" + i));
			}
			scheduler.start();
			try {
				Thread.sleep(3100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			scheduler.interrupt();
		}
	}
	public static class Task implements Comparable<Task>, Runnable {
		public final long time;
		public final String content;
		private final static DateFormat dateFormat = new SimpleDateFormat();
		public Task(long time, String content) {
			this.time = time;
			this.content = content;
		}
		@Override
		public int compareTo(Task o) {
			return (int)(time - o.time); // Casting is ok for comparison 
		}
		@Override
		public void run() {
			System.out.println(dateFormat.format(new Date(time)) + ":" + content);
		}
	}
}
