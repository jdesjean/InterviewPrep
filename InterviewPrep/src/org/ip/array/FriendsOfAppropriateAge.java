package org.ip.array;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/friends-of-appropriate-ages/">LC: 825</a>
 */
public class FriendsOfAppropriateAge {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {16,16}};
		Object[] tc2 = new Object[] {2, new int[] {16,17,18}};
		Object[] tc3 = new Object[] {3, new int[] {20,30,100,110,120}};
		Object[] tc4 = new Object[] {4, new int[] {101,56,69,48,30}};
		Object[] tc5 = new Object[] {3, new int[] {108,115,5,24,82}};
		Object[] tc6 = new Object[] {2, new int[] {118,14,7,63,103}};
		Object[] tc7 = new Object[] {21, new int[] {101,98,80,20,1,97,3,77,114,109}};
		Object[] tc8 = new Object[] {2, new int[] {15,15}};
		Object[][] tcs = new Object[][] {tc8, tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8};
		Problem[] solvers = new Problem[] {new Solver3(), new Solver2(), new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver3 implements Problem {

		@Override
		public Integer apply(int[] t) {
			int[] counts = cumSum(t);
			int sum = 0;
			for (int i = 1; i < 121; i++) {
				int count = counts[i];
				counts[i] = counts[i - 1] + counts[i];
				
				if (count == 0 || i < 15) continue; //smallest >= i
				int smallest = (int)(0.5 * i + 7);
				int diff = counts[i] - counts[smallest] - 1;
				sum += diff * count;
			}
			return sum;
		}
		int[] cumSum(int[] t) {
			int[] cumSum = new int[121]; // no zero, but make it simple with age = index
			for (int i = 0; i < t.length; i++) {
				cumSum[t[i]]++;
			}
			return cumSum;
		}
	}
	public static class Solver2 implements Problem  {

		@Override
		public Integer apply(int[] t) {
			return numFriendRequests(t);
		}
		public int numFriendRequests(int[] ages) {
	        int[] count = new int[121];
	        for (int age: ages) count[age]++;

	        int ans = 0;
	        for (int ageA = 0; ageA <= 120; ageA++) {
	            int countA = count[ageA];
	            for (int ageB = 0; ageB <= 120; ageB++) {
	                int countB = count[ageB];
	                if (ageA * 0.5 + 7 >= ageB) continue;
	                if (ageA < ageB) continue;
	                if (ageA < 100 && 100 < ageB) continue;
	                ans += countA * countB;
	                if (ageA == ageB) ans -= countA;
	            }
	        }

	        return ans;
	    }
		
	}
	public static class Solver implements Problem {

		@Override
		public Integer apply(int[] t) {
			int[] cumSum = cumSum(t);
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				int smallest = (int)(0.5 * t[i] + 7);
				// smallest - 1 to get cumSum prior to smallest	
				int largest = t[i];
				// use Math.max(0,x) for edge case where + 7 makes smallest greater or equal to largest.
				sum += Math.max(cumSum[largest] - cumSum[smallest] - 1, 0); // -1 because we don't want to count ourselves
			}
			return sum;
		}
		int[] cumSum(int[] t) {
			int[] cumSum = new int[121]; // no zero, but make it simple with age = index
			for (int i = 0; i < t.length; i++) {
				cumSum[t[i]]++;
			}
			int sum = 0;
			for (int i = 0; i < 121; i++) {
				sum += cumSum[i];
				cumSum[i] = sum;
			}
			return cumSum;
		}
	}
	public interface Problem extends Function<int[], Integer> {
		
	}
}
