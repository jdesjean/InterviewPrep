package org.ip;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.ip.ArrayUtils.Filter;
import org.ip.ArrayUtils.PermutationVisitor;

public interface Permutator {
	public void permute(PermutationVisitor visitor, int[] array);
	
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
	/*
	 * Knuth's L-Algorithm
	 * Computes all permutation in factoradic manner
	 */
	public static class PermutatorIterative implements Permutator {
		private Filter filter;

		public PermutatorIterative(Filter filter) {
			this.filter = filter;
		}
		private static void reverse(int[] array, int startIndex, int endIndex) {
	        int size = endIndex + 1 - startIndex;
	        int limit = startIndex + size / 2;
	        for (int i = startIndex; i < limit; i++) {
	            ArrayUtils.swap(array, i, 2 * startIndex + size - 1 - i);
	        }
	    }
		public static class PermutatorIntSupplier implements Supplier<int[]> {
			private int[] array;
			private Filter filter;
			public PermutatorIntSupplier(int[] array,Filter filter) {
				this.array = array;
				this.filter = filter;
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
	                            ArrayUtils.swap(array, j, pivotIndex);
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
			Stream.generate(new PermutatorIntSupplier(array,filter)).limit(ArrayUtils.factorial(array.length)-1)
			.filter(i -> filter.test(i, array.length-1))
			.forEach(i -> visitor.visit(i));
		};
	}
	public static class PermutatorFactoradicIterative implements Permutator {
		private Filter filter;

		public PermutatorFactoradicIterative(Filter filter) {
			this.filter = filter;
		}
		private int permute(int[] array, int[] buffer, int factorial, int size) {
			for (int i = 0; i < array.length; i++) {
				buffer[i] = array[i]; 
			}
			int current = factorial;
			for (int i = size-1, j = 0; i > 0; i--, j++) {
				int fact = ArrayUtils.factorial(i);
				int value = current / fact;
				current -= value*fact;
				int tmp = buffer[j+value];
				shift(buffer,j,j+value);
				buffer[j] = tmp;
				if (!filter.test(buffer, j)) {
					return factorial+fact;
				}
			}
			if (!filter.test(buffer, array.length-1)) {
				return factorial+ArrayUtils.factorial(size-1);
			} else {
				return factorial;
			}
		}
		private static void shift(int[] array, int left, int right) {
			for (int i = left, prev = array[i]; i < right; i++) {
				int tmp = array[i+1];
				array[i+1] = prev;
				prev = tmp;
			}
		}
		
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			int[] buffer = new int[array.length];
			int from = 0;
			int to = ArrayUtils.factorial(array.length)-1;
			
			for (int i = from; i <= to;) {
				int tmp = permute(array,buffer,i,array.length);
				if (tmp == i) {
					i++;
					visitor.visit(buffer);
				} else {
					i = tmp;
				}
			}
		};
	}
}


