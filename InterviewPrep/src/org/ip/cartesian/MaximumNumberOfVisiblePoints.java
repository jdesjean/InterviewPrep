package org.ip.cartesian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/maximum-number-of-visible-points/">LC: 1610</a>
 */
public class MaximumNumberOfVisiblePoints {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, Arrays.asList(
				Arrays.asList(2, 1),
				Arrays.asList(2, 2),
				Arrays.asList(3, 3)
				), 90, Arrays.asList(1, 1)};
		Object[][] tcs = new Object[][] { tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(List<List<Integer>> points, Integer angle, List<Integer> location) {
			List<Double> angles = new ArrayList<>();
	        int count = 0;
	        for (List<Integer> p : points) {
	            int dx = p.get(0) - location.get(0);
	            int dy = p.get(1) - location.get(1);
	            if (dx == 0 && dy == 0) { // edge case of same point
	                count++;
	                continue;
	            }
	            angles.add(Math.toDegrees(Math.atan2(dy, dx)));
	        }
	        Collections.sort(angles);
	        List<Double> tmp = new ArrayList<>(angles);
	        for (double d : angles) tmp.add(d + 360); // concatenate to handle edge case
	        int res = count;
	        for (int i = 0, j = 0; i < tmp.size(); i++) {
	            while (tmp.get(i) - tmp.get(j) > angle) {
	                j++;
	            }
	            res = Math.max(res, count + i - j + 1);
	        }
	        return res;
		}
	}
	interface Problem extends TriFunction<List<List<Integer>>, Integer, List<Integer>, Integer> {
		
	}
}
