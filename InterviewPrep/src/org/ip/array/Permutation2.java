package org.ip.array;

// EPI 6.10
public class Permutation2 {
	public static void main(String[] s) {
		Permutation2 permutation = new Permutation2();
		int[] array = new int[] {0,1,2,3};
		//0123, 0132, 0213, 0231, 0312, 0321, 
		//1023, 1032, 1203, 1230, 1302, 1320, 
		//2013, 2031, 2103, 2130, 2301, 2310, 
		//3012, 3021, 3102, 3120, 3201, 3210
		
		//012345, 012354, 012435
		while (permutation.next(array)) {
			Utils.println(array, array.length);
		}
	}
	public boolean next(int[] array) {
		if (array.length <= 1) return false;
		int last = -1;
		for (int i = array.length - 2; i >= 0; i--) {
			// last bit i order
			if (array[i] < array[i+1]) {
				last = i;
				break;
			}
		}
		
		if (last == -1) {
			return false;
		}
		
		for (int i = array.length - 1; i > last; i--) {
			if (array[i] > array[last] ) {
				Utils.swap(array, last, i);
				break;
			}
		}
		Utils.reverse(array, last + 1, array.length - 1);
		return true;
	}
}
