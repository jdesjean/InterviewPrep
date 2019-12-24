package org.ip.parallel;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// EPI 2018: 19.6, 19.7
public class ReaderWriter {
	public static void main(String[] s) {
		Shared shared = new Shared();
		ReadWriteLock rwl = new ReadWriteLock();
		try {
			rwl.acquireWrite();
			Runnable[] runnable = new Runnable[] {new Writer(rwl, shared), new Reader(rwl, shared), new Reader(rwl, shared), new Writer(rwl, shared)};
			for (int i = 0; i < runnable.length; i++) {
				Thread t = new Thread(runnable[i]);
				t.start();
			}
			rwl.releaseWrite();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public static class ReadWriteLock {
		Semaphore rl = new Semaphore(1, false);
		Semaphore wl = new Semaphore(1, false);
		int readers = 0;
		int writers = 0;
		boolean hasWriteLock = false;
		enum Turn {
			READ, WRITE
		}
		Turn turn = Turn.READ;
		public void acquireRead() throws InterruptedException {
			rl.acquire();
			readers++;
			if (writers > 0 && turn == Turn.WRITE) { // If writer is waiting. don't acquire
				do {
					rl.release();
					synchronized(this) {
						this.wait();
					}
					rl.acquire();
				} while (writers > 0 && turn == Turn.WRITE);
			}
			if (!hasWriteLock) {
				wl.acquire();
				hasWriteLock = true;
			}
			rl.release();
		}
		public void releaseRead() throws InterruptedException {
			rl.acquire();
			if (readers-- == 0) {
				wl.release();
				hasWriteLock = false;
			}
			turn = Turn.WRITE;
			synchronized(this) {
				this.notify();
			}
			rl.release();
		}
		public void acquireWrite() throws InterruptedException {
			rl.acquire();
			writers++;
			if (readers > 0 && turn == Turn.READ) { // readers are waiting. wait
				do {
					rl.release();
					synchronized(this) {
						this.wait();
					}
					rl.acquire();
				} while(readers > 0 && turn == Turn.WRITE);
			}
			wl.acquire();
		}
		public void releaseWrite() {
			turn = Turn.READ;
			writers--;
			wl.release();
			rl.release();
		}
	}
	public static class Shared {
		int value;
	}
	public static class Reader implements Runnable {
		private ReadWriteLock rwl;
		private Shared shared;
		public Reader(ReadWriteLock rwl, Shared shared) {
			this.rwl=rwl;
			this.shared=shared;
		}
		private void read() {
			try {
				this.rwl.acquireRead();
				System.out.println(shared.value);
				this.rwl.releaseRead();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			read();
		}
	}
	public static class Writer implements Runnable {
		private Shared shared;
		private ReadWriteLock rwl;
		public Writer(ReadWriteLock rwl, Shared shared) {
			this.rwl = rwl;
			this.shared=shared;
		}
		private void write() {
			try {
				this.rwl.acquireWrite();
				shared.value = shared.value + 1;
				this.rwl.releaseWrite();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			write();
		}
	}
}
