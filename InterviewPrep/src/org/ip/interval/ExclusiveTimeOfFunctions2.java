package org.ip.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/exclusive-time-of-functions/">LC: 636</a>
 */
public class ExclusiveTimeOfFunctions2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3,4}, 2, Arrays.asList(new String[] {
			"0:start:0",
			"1:start:2",
			"1:end:5",
			"0:end:6"
		})};
		Object[] tc2 = new Object[] {new int[] {8}, 1, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","0:start:6","0:end:6","0:end:7"
		})};
		Object[] tc3 = new Object[] {new int[] {7,1}, 2, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","1:start:6","1:end:6","0:end:7"
		})};
		Object[] tc4 = new Object[] {new int[] {8,1}, 2, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","1:start:7","1:end:7","0:end:8"
		})};
		Object[] tc5 = new Object[] {new int[] {1}, 1, Arrays.asList(new String[] {
			"0:start:0","0:end:0"
		})};
		Object[] tc6 = new Object[] {new int[] {6}, 1, Arrays.asList(new String[] {
			"0:start:0","0:start:1","0:start:2","0:end:3","0:end:4","0:end:5"
		})};
		Object[] tc7 = new Object[] {new int[] {1,1,2}, 3, Arrays.asList(new String[] {
			"0:start:0","0:end:0","1:start:1","1:end:1","2:start:2","2:end:2","2:start:3","2:end:3"
		})};
		Object[][] tcs = new Object[][] {tc2, tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int[] apply(Integer t, List<String> u) {
			Deque<int[]> deque = new LinkedList<>();
			int[] res = new int[t];
			for (String event : u) {
				String[] split = event.split(":");
				int proc = Integer.parseInt(split[0]);
				String type = split[1];
				int time = Integer.parseInt(split[2]);
				if (type.equals("start")) {
					deque.push(new int[] {proc, time});
				} else {
					int[] start = deque.pop();
					int diff = time - start[1] + 1;
					res[start[0]] += diff;
					if (!deque.isEmpty()) {
						res[deque.peek()[0]] -= diff; 
					}
				}
			}
			return res;
		}
		
	}

	interface Problem extends BiFunction<Integer, List<String>, int[]> {
		
	}
}
