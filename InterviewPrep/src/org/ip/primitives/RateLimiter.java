package org.ip.primitives;

public class RateLimiter {
	public static void main(String[] s) {
		RateLimiter limiter = new RateLimiter(2);
		for (int i = 0; i < 5; i++)  {
			System.out.println(limiter.accept());
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private float max;
	private float rate;
	private long prev = 0;
	//Request per second
	public RateLimiter(int rate) {
		this.max=rate;
	}
	public boolean accept() {
		long current = System.currentTimeMillis();
		long diff = current - prev;
		prev = current;
		float toRemove = diff / 1000f * max;

		rate = Math.max(rate - toRemove, 0);

		float next = rate + 1;
		System.out.println(rate + ":" + next + ":" + toRemove);
		if (next <= max) {
			rate = next;
			return true;
		} else {
			return false;
		}
	}
}
