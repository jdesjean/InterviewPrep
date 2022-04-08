package org.ip.stream;

import java.lang.reflect.InvocationTargetException;
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
		Object[] tc1 = new Object[] { new double[] {1.0, 5.5, 4.66667, 6.0}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		TestCaseSolver[] solvers = new TestCaseSolver[] { 
				new SolverAbstractProblem(Solver.class),
				new SolverAbstractProblem(Solver2.class),
				new SolverAbstractProblem(Solver3.class),
				new SolverAbstractProblem(Solver4.class)};
		Test.apply(solvers, tcs);
	}
	static class Solver4 implements Problem {
		private final int size;
		private final Deque<Integer> deque;
		double sum = 0;

		public Solver4(int size) {
			this.size = size;
			deque = new ArrayDeque<>(size);
		}

		@Override
		public double next(int n) {
			if (deque.size() >= size) {
				sum -= deque.removeFirst();
			}
			deque.addLast(n);
			sum += n;
			return sum / deque.size();
		}
		
	}
	static class Solver3 implements Problem {

		LimitedArrayDeque<Integer> circularQueue;
		double sum = 0;
		
		public Solver3(int n) {
			circularQueue = new LimitedArrayDeque<>(n);
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

		public Solver2(int n) {
			circularQueue = new CircularQueue(n);
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

		public Solver(int n) {
			this.size = n;
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
	
	interface Problem {
		public double next(int n);
	}
	static class TestCase1 implements TestCase {

		@Override
		public double[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(Integer.TYPE).newInstance(3);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			return new double[] {
					t.next(1),
					t.next(10),
					t.next(3),
					t.next(5)
			};
		}
		
	}
	
	static class SolverAbstractProblem implements TestCaseSolver {
		private Class<? extends Problem> _clazz;

		public SolverAbstractProblem(Class<? extends Problem> clazz) {
			_clazz = clazz;
		}

		@Override
		public double[] apply(TestCase t) {
			return t.apply(_clazz);
		}
		
	}
    interface TestCaseSolver extends Function<TestCase, double[]> {
    	
    }
    interface TestCase extends Function<Class<? extends Problem>, double[]> {
    	
    }
}
