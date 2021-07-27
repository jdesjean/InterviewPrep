package org.ip.array;

import java.util.Arrays;
import java.util.Comparator;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/car-fleet/">LC: 853</a>
 */
public class CarFleet {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, new int[] {10,8,0,5,3}, new int[] {2,4,1,1,3}, 12};
		Object[] tc2 = new Object[] { 0, new int[] {}, new int[] {}, 12};
		Object[] tc3 = new Object[] { 1, new int[] {2,4}, new int[] {3,2}, 10};
		Object[] tc4 = new Object[] { 2, new int[] {6,8}, new int[] {3,2}, 10};
		Object[] tc5 = new Object[] { 1, new int[] {0,4,2}, new int[] {2,1,3}, 10};
		Object[] tc6 = new Object[] { 6, new int[] {8,3,7,4,6,5}, new int[] {4,4,4,4,4,4}, 10};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(int[] a, int[] b, Integer c) {
			int target = c;
			Car[] arrival = new Car[a.length];
			for (int i = 0; i < a.length; i++) {
				arrival[i] = new Car(a[i], (target - a[i]) / (double)b[i]);
			}
			Arrays.sort(arrival, new PositionComparator());
			int fleet = a.length > 0 ? 1 : 0;
			for (int i = arrival.length - 2, j = arrival.length - 1; i >= 0; i--) {
				if (arrival[i].time > arrival[j].time) {
					fleet++;
					j = i;
				}
			}
			return fleet;
		}
	}
	public static class Car {
		int position;
		double time;
		public Car(int position, double time) {
			this.position = position;
			this.time = time;
		}
	}
	static class PositionComparator implements Comparator<Car> {

		@Override
		public int compare(Car o1, Car o2) {
			return Integer.compare(o1.position, o2.position);
		}
		
	}
	
	interface Problem extends TriFunction<int[], int[], Integer, Integer> {
		
	}
}
