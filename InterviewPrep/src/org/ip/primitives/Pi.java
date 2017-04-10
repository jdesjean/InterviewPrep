package org.ip.primitives;

import java.util.Arrays;

public class Pi {
	public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679;
	public static final long[] DENUMS = new long[]{1, 0, 1, 7, 106, 113, 33102, 33215, 66317, 99532, 265381, 364913, 1360120, 1725033, 25510582, 52746197, 78256779, 131002976, 340262731, 811528438, 1963319607, 4738167652L, 6701487259L, 567663097408L, 1142027682075L, 1709690779483L, 2851718461558L, 44485467702853L}; 
	public static void main(String[] s){
		long denum = solve2(0,Long.MAX_VALUE);
		long num = denum == 0 ? 1 : (long)Math.round(denum*PI);
		System.out.println((long)num+"/"+denum);
	}
	public static long solve(long min, long max) {
		double minDiff = Integer.MAX_VALUE;
		long denum = 0;
		for (double i = min; i <= max; i++) {
			double top = Math.round(i*PI);
			double diff =  Math.abs(top / i - PI);
			if (diff < minDiff) {
				minDiff = diff;
				denum = (long)i;
			} 
		}
		return denum; 
	}
	public static long solve2(long min, long max) {
		int indexMax = Math.abs(Arrays.binarySearch(DENUMS, max));
		int indexMin = Math.abs(Arrays.binarySearch(DENUMS, min));
		long denum = 0;
		for (int i = Math.min(indexMax, DENUMS.length-1); i >= indexMin; i--) {
			if (DENUMS[i] > max) continue;
			denum = DENUMS[i];
			break;
		}
		return denum >= min && denum <= max ? denum : solve(min,max);
	}
}
