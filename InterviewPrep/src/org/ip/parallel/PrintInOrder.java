package org.ip.parallel;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// EPI: 20.3
public class PrintInOrder {
	public static void main(String[] s) {
		mainPrinterOrder();
	}
	public static void mainPrinterOrder() { // Implementation that guarantees total order
		Order order = new Order();
		new Thread(new PrinterOrder(order, new ModIterator(-3,3,100))).start();
		new Thread(new PrinterOrder(order, new ModIterator(-2,3,100))).start();
		new Thread(new PrinterOrder(order, new ModIterator(-1,3,100))).start();
	}
	public static void mainPrinterFair() { // Does not guarantee total order
		Lock lock = new ReentrantLock(true);
		lock.lock();
		new Thread(new PrinterLock(lock, new ModIterator(-3,3,100))).start();
		new Thread(new PrinterLock(lock, new ModIterator(-2,3,100))).start();
		new Thread(new PrinterLock(lock, new ModIterator(-1,3,100))).start();
		lock.unlock();
	}
	public static void mainPrinterYield() { // Best effort
		int nbThreads = 2;
		Runnable[] threads = new Runnable[nbThreads];
		Monitor monitor = new Monitor(1000);
		for (int i = 0; i < nbThreads; i++) {
			threads[i] = new PrinterYield(monitor);
			new Thread(threads[i]).start();
		}
	}
	public static class ModIterator implements Iterator<Integer> {
		private int max;
		private int i;
		private int start;
		private int mod;

		public ModIterator(int start, int mod, int max) {
			this.max=max;
			this.start=start;
			this.mod=mod;
			this.i = start;
		}
		@Override
		public boolean hasNext() {
			return i + mod <= max;
		}

		@Override
		public Integer next() {
			return i+=mod;
		}
	}
	public static class PrinterOrder implements Runnable  {
		private Order lock;
		private Iterator<Integer> iterator;
		public PrinterOrder(Order lock, Iterator<Integer> iterator) {
			this.lock=lock;
			this.iterator = iterator;
		}
		@Override
		public void run() {
			while (true) {
				if (!iterator.hasNext()) break;
				Integer next = iterator.next();
				try {
					lock.get(next);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
	}
	public static class PrinterLock implements Runnable  {
		private Lock lock;
		private Iterator<Integer> iterator;
		public PrinterLock(Lock lock, Iterator<Integer> iterator) {
			this.lock=lock;
			this.iterator = iterator;
		}
		@Override
		public void run() {
			while (true) {
				if (!iterator.hasNext()) break;
				Integer next = iterator.next();
				try {
					lock.lock();
					System.out.println(Thread.currentThread() + ":" + next);
				} finally {
					lock.unlock();
				}
			}
		}
		
	}
	public static class PrinterYield implements Runnable {
		private Monitor monitor;

		public PrinterYield(Monitor monitor) {
			this.monitor=monitor;
		}

		@Override
		public void run() {
			while (monitor.printNext() < monitor.max) {Thread.yield();}
		}
		
	}
	public static class Monitor {
		private int value = 0;
		private final int max;
		public Monitor(int max) {
			this.max = max;
		}
		public synchronized int printNext() {
			if (value < max) {
				int next = ++value;
				System.out.println(Thread.currentThread() + ":" + next);
				return next;
			}
			return value;
		}
	}
	public static class Order {
		public int value = -1;
		public synchronized void get(int value) throws InterruptedException {
			while (this.value + 1 != value) {
				this.wait();
			}
			this.value++;
			System.out.println(Thread.currentThread() + ":" + this.value);
			this.notifyAll();
		}
	}
}
