package org.ip.array;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/shuffle-the-array/">LC: 1470</a>
 */
public class ShuffleArray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {2,3,5,4,1,7}, new int[] {2,5,1,3,4,7}, 3};
		Object[] tc2 = new Object[] {new int[] {1,4,2,3,3,2,4,1}, new int[] {1,2,3,4,4,3,2,1}, 4};
		Object[] tc3 = new Object[] {new int[] {1,2,1,2}, new int[] {1,1,2,2}, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver(), new SolverInPlace() };
		Test.apply(solvers, tcs);
	}
	static class SolverInPlace implements Problem {

		@Override
		public int[] apply(int[] nums, Integer n) {
			int max = 1001; // M in the above explanation
	        
			//Store number pairs in right half
	        for(int i = n;i < nums.length; i++){
	            nums[i] = max*nums[i]+nums[i-n];
	        }
	        
			//Put pairs in proper positions
	        int ind = 0;
	        for(int i = n; i < nums.length; i++){
	            nums[ind] = nums[i] % max;
	            nums[ind + 1] = nums[i] / max;
	            ind += 2;
	        }
	        
	        return nums;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			int[] res = new int[t.length];
			for (int i = 0, j = u, k = 0; i < u; i++, j++, k += 2) {
				res[k] = t[i];
				res[k + 1] = t[j];
			}
			return res;
		}
		
	}
	interface Problem extends BiFunction<int[], Integer, int[]> {
		
	}
}
