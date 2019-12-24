package org.ip.parallel;

import java.util.concurrent.atomic.AtomicInteger;

// EPI 2018: 19.5
public class Deadlock {
	public static class Account {
		private int balance;
		private final int id;
		//private static int globalId; // This doesn't work because accounts might be created in different thread
		private static AtomicInteger globalId;
		Account(int balance) {
			this.balance = balance;
			this.id = globalId.getAndIncrement();
		}
		private boolean move(Account to, int amount) {
			//deadlock because with T1 & T2, T1 might lock A1 & T2 might lock A2 and second lock cannot be gathered;
			//by using id order to synchronize we avoid deadlock
			Object l1 = this.id < to.id ? this : to;
			Object l2 = this.id < to.id ? to : this;
			synchronized(l1) {
				synchronized(l2) {
					if (amount > balance) {
						return false;
					}
					to.balance += amount;
					this.balance -= amount;
					return true;
				}
			}
			
		}
		public static void transfer(final Account from, final Account to, final int amount) {
			Thread transfer = new Thread(new Runnable() {
				@Override
				public void run() {
					from.move(to, amount);
				}
			});
			transfer.start();
		}
	}
}
