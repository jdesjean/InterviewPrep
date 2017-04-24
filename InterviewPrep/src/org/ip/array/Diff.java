package org.ip.array;

public class Diff {
	public static void main(String[] s) {
		int[] array = new int[]{310,315,275,295,260,270,290,230,225,250};
		System.out.println(maxOnce(array));
		System.out.println(maxTwice(array));
		System.out.println(maxUnlimited(array));
	}
	public static int maxOnce(int[] array) {
		int max = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			max = Math.max(array[i] - min, max);
			min = Math.min(min, array[i]);
		}
		return max;
	}
	public static int[] maxOnceArray(int[] array) {
		int max = 0;
		int min = Integer.MAX_VALUE;
		int[] maxArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			max = Math.max(array[i] - min, max);
			maxArray[i] = max;
			min = Math.min(min, array[i]);
		}
		return maxArray;
	}
	public static int maxTwice(int[] array) {
		int[] maxOnceArray = maxOnceArray(array);
		int maxValue = 0;
		int maxDiff = 0;
		int maxTwice = 0;
		for (int i = array.length-1; i >= 0; i--) {
			maxTwice = Math.max(maxTwice, maxDiff+maxOnceArray[i]);
			maxDiff = Math.max(maxValue-array[i], maxDiff);
			maxValue = Math.max(maxValue, array[i]);
		}
		return maxTwice;
	}
	public static int maxUnlimited(int[] array) {
		int max = 0;
		int min = 0;
		for (int i = 0; i < array.length; i++) {
			if (isLocalMinima(array,i)) min = array[i];
			else if (isLocalMaxima(array,i)) {
				max+=array[i]-min;
			}
		}
		return max;
	}
	public static boolean isLocalMinima(int[] array, int i) {
		return (i == 0 && array[i] < array[i+1])
		|| (i == array.length - 1 && array[i] < array[i-1] )
		|| (i > 0 && i < array.length - 1 && array[i] < array[i+1] && array[i] < array[i-1]);
	}
	public static boolean isLocalMaxima(int[] array, int i) {
		return (i == 0 && array[i] > array[i+1])
		|| (i == array.length - 1 && array[i] > array[i-1] )
		|| (i > 0 && i < array.length - 1 && array[i] > array[i+1] && array[i] > array[i-1]);
	}
}
