package org.ip;

import java.util.NoSuchElementException;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayUtils {
	public static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	public interface PermutationVisitor {
		public void visit(int[] array);
	}
	public interface Filter {
		public boolean test(int[] array, int i);
	}
	public interface Permutator {
		public void permute(PermutationVisitor visitor, int[] array);
	}
	public static class PermutatorRecursive implements Permutator {
		private Filter filter;

		public PermutatorRecursive(Filter filter) {
			this.filter=filter;
		}
		private void permute(PermutationVisitor visitor, Filter filter, int[] array, int index) {
			if (index == array.length) {
				visitor.visit(array);
				return;
			}
			for (int i = index; i < array.length; i++) {
				ArrayUtils.swap(array,i,index);
				if (filter.test(array,index)) {
					permute(visitor,filter,array,index+1);
				}
				ArrayUtils.swap(array,i,index);
			}
			return;
		}

		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			permute(visitor,filter,array,0);
		}
	}
	public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	/*
	 * Knuth's L-Algorithm
	 * another option could be factoradics
	 */
	public static class PermutatorIterative implements Permutator {
		
		private static void reverse(int[] array, int startIndex, int endIndex) {
	        int size = endIndex + 1 - startIndex;
	        int limit = startIndex + size / 2;
	        for (int i = startIndex; i < limit; i++) {
	            swap(array, i, 2 * startIndex + size - 1 - i);
	        }
	    }
		public static class PermutatorIntSupplier implements Supplier<int[]> {
			private int[] array;
			public PermutatorIntSupplier(int[] array) {
				this.array = array;
			}
			@Override
			public int[] get() {
				final int N = array.length;
	            for (int i = N - 1; i >= 1; i--) {
	                if (array[i - 1] < array[i]) {
	                    int pivotIndex = i - 1;
	                    //TODO: Check if we can filter here
	                    for (int j = N - 1; j > pivotIndex; j--) {
	                        if (array[j] > array[pivotIndex]) {
	                            swap(array, j, pivotIndex);
	                            break;
	                        }
	                    }
	                    
	                    reverse(array, pivotIndex + 1, N - 1);
	                    break;
	                }
	            }
	            return array;
			}
			
		}
		
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			Stream.generate(new PermutatorIntSupplier(array)).limit(factorial(array.length)-1).forEach(i -> visitor.visit(i));
		};
	}
/*this.reduceArrayPermutationWithoutDups2 = function(data, f, o, ifBreakEarly, fk, initIdx, dataLength) {
if (!fk) {
	fk = function(o){return o;};
}
o = f(o,data);
while (true) {
	var k = dataLength - 2;
    while (fk(data[k]) >= fk(data[k + 1])) {
        k--;
        if (k < initIdx) {
            return o;
        }
    }
    var l = dataLength- 1;
    while (fk(data[k]) >= fk(data[l])) {
        l--;
    }
    this.swap(data, k, l);
    var length = dataLength - (k + 1);
    for (var i = initIdx; i < length / 2; i++) {
        this.swap(data, k + 1 + i, dataLength - i - 1);
    }
    o = f(o,data);
    if (ifBreakEarly(o,data)) return o;
}
};*/
	public static void println(int[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
	}
}
