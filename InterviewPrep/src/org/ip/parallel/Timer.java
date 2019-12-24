package org.ip.parallel;

import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

// EPI: 20.8
public class Timer implements Runnable {
	public static void main(String[] s) {
		Timer timer = new Timer(Executors.newCachedThreadPool());
		Thread thread = new Thread(timer);
		thread.start();
		long current = System.currentTimeMillis();
		System.out.println(current);
		timer.add(current-10, new PrintTask(0));
		timer.add(current+20, new PrintTask(1));
		timer.add(current+10, new PrintTask(2));
	}
	public static class PrintTask implements Runnable {
		private int value;
		public PrintTask(int value) {
			this.value = value;
		}
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":" + value);
		}
	}
	ExecutorService service;
	public Timer(ExecutorService service) {
		this.service=service;
	}
	@Override
	public void run() {
		long next = Long.MAX_VALUE;
		while (true) {
			Event event;
			try {
				event = queue.poll(next, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			long currentTime = System.currentTimeMillis();
			if (event == null) {
				if (heap.isEmpty()) {
					next = Long.MAX_VALUE;
					continue;
				} else if (currentTime < heap.peek().time) {
					next = heap.peek().time - currentTime; 
					continue;
				}
				event = heap.remove();
			}
			long diff = event.time - currentTime; 
			if (diff <= 0) {
				service.submit(event.task);
				next = 0;
			} else {
				heap.add(event);
				next = Math.min(diff, heap.peek().time) - 1;
			}
		}
	}
	PriorityQueue<Event> heap = new PriorityQueue<Event>();
	BlockingQueue<Event> queue = new LinkedBlockingDeque<Event>();
	public void add(long time, Runnable task) {
		queue.add(new Event(time, task));
	}
	public static class Event implements Comparable<Event> {
		public final Runnable task;
		public final long time;
		public Event(long time, Runnable task) {this.time=time;this.task=task;}

		@Override
		public int compareTo(Event o) {
			if (this.time < o.time) {
				return -1;
			} else if (this.time > o.time) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
}
