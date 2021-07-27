package org.ip.primitives;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/divide-two-integers/">LC: 29</a>
 * EPI: 5.6
 * EPI 2018: 4.6
 */
public class Divider {
	public static void main(String[] s) {
		Integer[] tc1 = new Integer[] {Integer.MIN_VALUE, -1, 2147483647};
		Integer[] tc2 = new Integer[] {Integer.MIN_VALUE, Integer.MIN_VALUE, 1};
		Integer[] tc3 = new Integer[] {1, 1, 1};
		Integer[] tc4 = new Integer[] {0, 1, 0};
		Integer[] tc5 = new Integer[] {8, -2, -4};
		Integer[] tc6 = new Integer[] {-2, -8, 0};
		Integer[] tc7 = new Integer[] {7, -3, -2};
		Integer[] tc8 = new Integer[] {9, 3, 3};
		Integer[] tc9 = new Integer[] {Integer.MAX_VALUE, -1, -2147483647};
		Integer[] tc10 = new Integer[] {Integer.MAX_VALUE, Integer.MAX_VALUE, 1};
		List<Integer[]> consumers = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10);
		BiFunction<Integer, Integer, Integer>[] solvers = new BiFunction[] {new NegativeSolver()};
		for (Integer[] consumer : consumers) {
			for (BiFunction<Integer, Integer, Integer> solver : solvers) {
				System.out.print(solver.apply(consumer[0], consumer[1]) + "," + consumer[2]);
			}
			System.out.println();
		}
	}
	
	public static class NegativeSolver implements BiFunction<Integer, Integer, Integer> {
		Substraction substraction = new Substraction();
		private final static int MASK_SIGN = 1 << 31;
		private final static int MASK_MSB = (1 << 30);

		@Override
		public Integer apply(Integer a, Integer b) {
			if (b == 0) throw new RuntimeException("division by zero");
			if (a == Integer.MIN_VALUE && b == -1) {
		        return Integer.MAX_VALUE; // overflow
		    }
			boolean sign = sign(a, b);
			// Need to use negative values because negative space (MIN_VALUE) > positive space (MAX_VALUE) by 1
			if (a > 0) {
				a = substraction.negate(a);
			}
			if (b > 0) {
				b = substraction.negate(b);
			}

			/*
			 * Get largest shift possible with msb(b)
			 * Alternative is to use shift = 30 & use long for bpower
			 * Question restricts use of long
			 */
			int bmsb = msb(b);
			int shift = Math.max(substraction.substract(30, bmsb), 0);
			int power = 1 << shift;
			int bpower = b << shift;
			int res = 0;
			while (a <= b) {
				while (bpower < a) {
					power >>= 1;
					bpower >>= 1;
				}
				res |= power;
				a = substraction.substract(a, bpower);
			}
			
			return sign ? res : substraction.negate(res);
		}
		
		int msb(int a) {
			int na = ~a;
			for (int mask = MASK_MSB, i = 31; i >= 0; mask >>= 1, i--) {
				if ((na & mask) != 0) return i;
			}
			return 0;
		}
		boolean sign(int a, int b) {
			int as = a & MASK_SIGN;
			int bs = b & MASK_SIGN;
			return as == bs;
		}
	}
}

