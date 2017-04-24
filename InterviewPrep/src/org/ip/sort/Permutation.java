package org.ip.sort;

import org.ip.array.ArrayUtils;

public class Permutation {
	public static void main(String[] s) {
		//1 through N
		//input: 1,7,4,3,2,7,4, output: 4 or 7
		//input:3,1,2, output:-1
		System.out.println(duplicate(new int[]{1,7,4,3,2,7,4}));
		System.out.println(duplicate(new int[]{3,1,2}));
	}
	public static int duplicate(int[] array) {
		for (int i = 0; i < array.length;) {
			int indexTarget = indexOf(array[i]);
			boolean isInPlace = indexTarget == i;
			if (hasFoundDuplicate(isInPlace,isTargetInPlace(array,indexTarget))) {
				return array[i];
			} else if (!isInPlace) {
				ArrayUtils.swap(array, indexTarget, i);
			}
			else i++;
		}
		return -1;
	}
	private static boolean hasFoundDuplicate(boolean isInPlace, boolean isTargetInPlace) {
		return !isInPlace && isTargetInPlace;
	}
	private static boolean isTargetInPlace(int[] array, int indexTarget) {
		return indexOf(array[indexTarget]) == indexTarget;
	}
	private static int indexOf(int value) {
		return value - 1;
	}
}
