package org.ip.primitives;

import java.util.Deque;
import java.util.LinkedList;

public class RateLimiter2 {
	public static void main(String[] s) {
		RateLimiter2 limiter = new RateLimiter2(2);
		for (int i = 0; i < 5; i++)  {
			System.out.println(limiter.accept());
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private Deque<Long> queue;
	private int rate;
	// request per second
	public RateLimiter2(int rate) {
		this.queue = new LinkedList<Long>();
		this.rate = rate;
	}
	public boolean accept() {
		long current = System.currentTimeMillis();
		if (this.queue.size() < rate) {
			this.queue.addLast(current);
		} else {
			if (this.queue.peek() <= current - 1000) {
				this.queue.remove();
				this.queue.push(current);
			} else {
				return false;
			}
		}
		return true;
	}
}
