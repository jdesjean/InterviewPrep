package org.ip.parallel;

// EPI 2018: 19.3
public class PrintInOrder2 {
	public static void main(String[] s) {
		Monitor monitor = new Monitor();
		Thread t1 = new Thread(new Printer(0, 100, monitor));
		Thread t2 = new Thread(new Printer(1, 100, monitor));
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static class Monitor {
		private boolean value = true;

		public synchronized void waitForValue(boolean even) {
			while (this.value != even) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public synchronized void toggle() {
			value = !value;
			this.notify();
		}
	}
	public static class Printer implements Runnable {
		private int value;
		private int max;
		private Monitor monitor;

		public Printer(int start, int max, Monitor monitor) {
			this.value = start;
			this.max = max;
			this.monitor = monitor;
		}

		@Override
		public void run() {
			while (value <= max) {
				monitor.waitForValue(this.value % 2 == 0);
				System.out.println(value);
				value+=2;
				monitor.toggle();
			}
		}
	}
}
