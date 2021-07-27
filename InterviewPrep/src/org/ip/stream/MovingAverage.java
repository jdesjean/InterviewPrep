package org.ip.stream;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/moving-average-from-data-stream/">LC: 346</a>
 */
public class MovingAverage {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Double[] {1.0, 5.5, 4.66667, 6.0}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3() };
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		LimitedArrayDeque<Integer> circularQueue;
		double sum = 0;
		
		public Solver3() {
		}

		public Solver3(int n) {
			circularQueue = new LimitedArrayDeque<>(n);
		}
		
		@Override
		public double[] apply(Function<Problem, double[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int n) {
			return new Solver3(n);
		}

		@Override
		public double next(int n) {
			sum += n;
			sum -= valueOrDefault(circularQueue.add(n), 0);
			return sum / circularQueue.size();
		}
		<T> T valueOrDefault(T value, T def) {
			return value != null ? value : def;
		}
		class LimitedArrayDeque<T> {

		    private int maxSize;
		    private ArrayDeque<T> queue;

		    public LimitedArrayDeque(int maxSize) {
		        this.maxSize = maxSize;
		        queue = new ArrayDeque<T>(maxSize);
		    }

		    public T add(T t) {
		    	T prev = null;
		        if (queue.size() == maxSize) {
		            prev = queue.removeFirst();
		        }
		        queue.add(t);
		        return prev;
		    }

		    public boolean remove(T t) {
		        return queue.remove(t);
		    }

		    public boolean contains(T t) {
		        return queue.contains(t);
		    }
		    
		    public int size() {
		    	return queue.size();
		    }

		    @Override
		    public String toString() {
		        return queue.toString();
		    }
		}
		
	}
	static class Solver2 implements Problem {

		double sum = 0;
		final CircularQueue circularQueue;
		public Solver2() {
			circularQueue = new CircularQueue(0);
		}

		public Solver2(int n) {
			circularQueue = new CircularQueue(n);
		}
		
		@Override
		public double[] apply(Function<Problem, double[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int n) {
			return new Solver2(n);
		}

		@Override
		public double next(int n) {
			sum += n;
			sum -= circularQueue.add(n);
			return sum / circularQueue.count();
		}
		static class CircularQueue {
			private final int[] buffer;
			private int current = -1;
			private int count = 0;
			public CircularQueue(int size) {
				buffer = new int[size];
			}
			public int last() {
				return buffer[current];
			}
			public int add(int n) {
				if (++current >= buffer.length) {
					current = 0;
				}
				int prev = buffer[current];
				buffer[current] = n;
				if (count < buffer.length) {
					count++;
				}
				return prev;
			}
			public int count() {
				return count;
			}
		}
	}
	static class Solver implements Problem {

		private final int size;
		double sum = 0;
		Deque<Integer> deque = new LinkedList<>();
		public Solver() {
			size = 0;
		}

		public Solver(int n) {
			this.size = n;
		}
		
		@Override
		public double[] apply(Function<Problem, double[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int n) {
			return new Solver(n);
		}

		@Override
		public double next(int n) {
			sum += n;
			deque.add(n);
			if (deque.size() > size) {
				sum -= deque.remove();
			}
			return sum / deque.size();
		}
		
	}
	interface Problem extends Function<Function<Problem, double[]>, double[]> {
		public Problem problem(int n);
		public double next(int n);
	}
	static class TestCase1 implements Function<Problem, double[]> {

		@Override
		public double[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(3);
			return new double[] {
					copy.next(1),
					copy.next(10),
					copy.next(3),
					copy.next(5)
			};
		}
		
	}
}
