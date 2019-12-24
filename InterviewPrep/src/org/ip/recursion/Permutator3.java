package org.ip.recursion;

import java.util.function.Consumer;

import org.ip.array.Utils;

// EPI 2018: 15.3
public class Permutator3 {
	public static void main(String[] s) {
		int[] a = new int[] {2,2,5,7};
		new Permutator3().solve((x) -> Utils.println(x), a);
	}
	//subsets: 4, 3, 2, 1
	//       : 1, 4, 6, 4
	
	//Perms: n!
	public void solve(Consumer<int[]> consumer, int[] array) {
		_solve(consumer,array,0);
	}
	public void _solve(Consumer<int[]> consumer, int[] array, int index) {
		if (index == array.length) {
			consumer.accept(array);
			return;
		}
		for (int i = index; i < array.length; i++) {
			Utils.swap(array, index, i);
			// If keys are not unique and we don't want dups 
			//if (index == i || array[index] != array[i]) { 
			_solve(consumer, array, index + 1);
			Utils.swap(array, index, i);
		}
	}
}
