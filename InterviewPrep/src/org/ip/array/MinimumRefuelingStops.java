package org.ip.array;

import java.util.Collections;
import java.util.PriorityQueue;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/minimum-number-of-refueling-stops/">LC: 871</a>
 */
public class MinimumRefuelingStops {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {0, 1, 1, new int[][] {}};
		Object[] tc2 = new Object[] {-1, 100, 1, new int[][] {{10,100}}};
		Object[] tc3 = new Object[] {2, 100, 10, new int[][] {{10,60},{20,30},{30,30},{60,40}}};
		Object[] tc4 = new Object[] {17, 1000, 75, new int[][] {{14,41},{55,70},{88,26},{102,34},{179,63},{230,24},{238,58},{258,59},{280,50},{314,46},{317,66},{345,14},{393,72},{417,66},{523,27},{528,1},{567,75},{621,68},{636,48},{669,74},{786,12},{806,23},{809,30},{815,13},{904,14}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(Integer a, Integer b, int[][] stations) {
			int target = a;
			int startFuel = b;
			if (target <= startFuel) return 0;
			PriorityQueue<Integer> refuelStation = new PriorityQueue<>(Collections.reverseOrder());
			int refuel = 0;
			int i = 0;
			for (int pos = startFuel, currentFuel = 0; pos < target; pos += currentFuel, currentFuel = 0) {
				for (; i < stations.length && stations[i][0] <= pos; i++) {
					refuelStation.add(stations[i][1]);
				}
				if (refuelStation.isEmpty()) return -1;
				currentFuel += refuelStation.remove();
				refuel++;
			}
			return refuel;
		}
		
	}
	interface Problem extends TriFunction<Integer, Integer, int[][], Integer> {
		
	}
}
