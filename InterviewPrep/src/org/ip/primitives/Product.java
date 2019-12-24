package org.ip.primitives;

import java.util.Arrays;

// leetcode 238
public class Product {
	public static void main(String[] s) {
		/*
		 * Input : [1, 2, 3, 4, 5]

			Output: [(2*3*4*5), (1*3*4*5), (1*2*4*5), (1*2*3*5), (1*2*3*4)]
			
			= [120, 60, 40, 30, 24]
			without using division
		 */
		System.out.println(Arrays.toString(product(new int[]{1,2,3,4,5})));
		System.out.println(Arrays.toString(product(new int[]{0,0})));
	}
	public static int[] product(int[] array) {
		int[] result = Arrays.copyOf(array, array.length);
		for (int i = 1; i < array.length; i++) {
			array[i] = array[i]*array[i-1];
		}
		int product = 1;
		for (int i = array.length-1; i >= 0; i--) {
			int tmp = result[i];
			int next = i - 1 >= 0 ? array[i-1] : 1; 
			result[i] = product*next;
			product*=tmp;
		}
		return result;
	}
}
