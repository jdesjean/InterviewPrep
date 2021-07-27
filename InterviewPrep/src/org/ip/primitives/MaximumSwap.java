package org.ip.primitives;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-swap/>LC: 670</a>
 */
public class MaximumSwap {
/**
 * 1234, 2134
 * 
 * 4312, 4321
 * 4315, 5431
 * 
 * 4132
 * 4321
 * 
 * take largest number that can be swaped with highest position
 * start from highest, find max from others, if max > current swap & break, else continue with next 
 * {4:0, 3:1, 1:2, 5:3}
 * {1:2, 3:1, 4:0, 5:3}
 * 
 * [5]
 * find max, find highest it can be swaped with,
 * 
 *  start on right, walk backward, keep track of max seen, & max position swaped
 */
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4231, 1234};
		Object[] tc2 = new Object[] {4321, 4312};
		Object[] tc3 = new Object[] {4321, 4321};
		Object[] tc4 = new Object[] {7236, 2736};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int operand) {
			int[] idx = idx(operand);
			int hasValue = operand;
			int i = 0;
			int swapIndexMax = 0;
			int swapIndexMin = 0;
			while (hasValue > 0) {
				int digit = hasValue % 10;
				for (int d = 9; d >= digit; d--) {
					if (idx[d] < i) {
						swapIndexMax = i;
						swapIndexMin = idx[d];
						break;
					}
				}
				i++;
				hasValue /=10;
			}
			if (swapIndexMax != swapIndexMin) {
				return swap(operand, swapIndexMax, swapIndexMin);
			}
			return operand;
		}
		
		int[] idx(int operand) {
			int[] idx = new int[10];
			Arrays.fill(idx, Integer.MAX_VALUE);
			int hasValue = operand;
			int i = 0;
			while (hasValue > 0) {
				int digit = hasValue % 10;
				idx[digit] = Math.min(idx[digit], i);
				hasValue /= 10;
				i++;
			}
			return idx;
		}
		int swap(int operand, int i, int j) {
			int di = operand % (int)Math.pow(10, i + 1) / (int)Math.pow(10, i);
			int dj = operand % (int)Math.pow(10, j + 1) / (int)Math.pow(10, j);
			int d = dj - di;
			return operand + d * (int)Math.pow(10, i) - d * (int) Math.pow(10, j); 
		}
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(int operand) {
			//How to handle negative?
			
			int hasValue = operand;
			int maxDigit = 0;
			int maxDigitIndex = 0;
			int maxIndex = 0;
			int swapIndex = 0;
			int index = 0;
			while (hasValue > 0) {
				int digit = hasValue % 10;
				if (maxDigit > digit) {
					maxIndex = index;
					swapIndex = maxDigitIndex;
				}
				
				if (digit > maxDigit) {
					maxDigit = digit;
					maxDigitIndex = index;
				}
				hasValue /= 10;
				index++;
			}
			if (maxIndex != swapIndex) {
				return swap(operand, maxIndex, swapIndex);
			}
			return operand;
		}
		int swap(int operand, int i, int j) {
			int di = operand % (int)Math.pow(10, i + 1) / (int)Math.pow(10, i);
			int dj = operand % (int)Math.pow(10, j + 1) / (int)Math.pow(10, j);
			int d = dj - di;
			return operand + d * (int)Math.pow(10, i) - d * (int) Math.pow(10, j); 
		}
		
	}
	public interface Problem extends IntUnaryOperator {
		
	}
}
