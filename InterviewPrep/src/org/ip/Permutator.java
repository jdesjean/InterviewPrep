package org.ip;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.ip.array.ArrayUtils;
import org.ip.array.ArrayUtils.Filter;
import org.ip.array.ArrayUtils.PermutationVisitor;
import org.ip.primitives.Sequence;

public interface Permutator {
	public static void main(String[] s) {
		Permutator[] permutators = new Permutator[]{new PermutatorStatic(new int[]{2,0,1,3}), new PermutatorOfflineRandom(3), new PermutatorOnlineRandom(SUPPLIER_ORDERED, 10)};
		for (int i = 0; i < permutators.length; i++) {
			permutators[i].permute(PERMUTATION_VISITOR_PRINT, new int[]{0,1,2,3});
		}
	}
	public static final IntSupplier SUPPLIER_ORDERED = new IntSupplier(){
		int i = 0;
		@Override
		public int getAsInt() {
			return i++;
		}
		
	};
	public static final PermutationVisitor PERMUTATION_VISITOR_PRINT = new PermutationVisitor(){
		@Override
		public void visit(int[] array) {
			ArrayUtils.println(array, 0, array.length-1);
		}
	};
	public void permute(PermutationVisitor visitor, int[] array);
	public static class PermutatorOnlineRandom implements Permutator{
		private Random random;
		private IntSupplier supplier;
		private int count;
		private int size;
		public PermutatorOnlineRandom(IntSupplier supplier, int size) {
			random = new Random();
			this.supplier=supplier;
			count = 0;
			this.size = size;
		}
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			count = array.length;
			for (int i = count; i < size; i++,count++) {
				int nextCount = count+1;
				int nextIndex = random.nextInt(nextCount);
				int next = supplier.getAsInt();
				boolean shouldAdd = (nextIndex < array.length);
				if (shouldAdd) array[nextIndex] = next; 
				visitor.visit(array);
			}
		}
	}
	public static class PermutatorOfflineRandom implements Permutator{
		private int k;
		private Random random;
		public PermutatorOfflineRandom(int k) {
			this.k = k;
			random = new Random();
		}
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			for (int i = 0; i < k; i++) { 
				int next = random.nextInt(array.length-i);
				ArrayUtils.swap(array, i, i+next);
			}
			visitor.visit(array);
		}
	}
	
	public static class PermutatorStatic implements Permutator{
		private int[] permutation;
		public PermutatorStatic(int[] permutation) {
			this.permutation=permutation;
		}
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			for (int i = 0; i < permutation.length;i++) {
				while (permutation[i] != i) {
					ArrayUtils.swap(array, i, permutation[i]);
					ArrayUtils.swap(permutation, i, permutation[i]);
				}
			}
			visitor.visit(array);
		}
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
	/*
	 * Knuth's L-Algorithm
	 * Computes all permutation in sorted order
	 */
	public static class PermutatorIterative implements Permutator {
		private Filter filter;

		public PermutatorIterative(Filter filter) {
			this.filter = filter;
		}
		public static boolean next(int[] array) {
			final int N = array.length;
            for (int i = N - 1; i >= 1; i--) {
                if (array[i - 1] < array[i]) {
                	int pivotIndex = i - 1;
                    //TODO: Check if we can filter here
                    for (int j = N - 1; j > pivotIndex; j--) {
                        if (array[j] > array[pivotIndex]) {
                            ArrayUtils.swap(array, j, pivotIndex);
                            return true;
                        }
                    }
                    
                    ArrayUtils.reverse(array, pivotIndex + 1, N - 1);
                    return true;
                }
            }
            return false;
		}
		public static class PermutatorIntSupplier implements Supplier<int[]> {
			private int[] array;
			public PermutatorIntSupplier(int[] array,Filter filter) {
				this.array = array;
			}
			@Override
			public int[] get() {
				next(array);
				return array;
			}
		}
		
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			Stream.generate(new PermutatorIntSupplier(array,filter)).limit(Sequence.factorial(array.length)-1)
			.filter(i -> filter.test(i, array.length-1))
			.forEach(i -> visitor.visit(i));
		};
	}
	public static class PermutatorFactoradicIterative implements Permutator {
		private Filter filter;

		public PermutatorFactoradicIterative(Filter filter) {
			this.filter = filter;
		}
		private int permute(int[] array, int[] buffer, int left, int right) {
			for (int i = 0; i < array.length; i++) {
				buffer[i] = array[i]; 
			}
			int current = left;
			for (int i = right, j = 0; i > 0; i--, j++) {
				int factorial = Sequence.factorial(i);
				int value = current / factorial;
				current -= value*factorial;
				buffer[j] = ArrayUtils.shift(buffer,j,j+value);
				if (!filter.test(buffer, j)) {
					return left+factorial;
				}
			}
			if (!filter.test(buffer, array.length-1)) {
				return left+Sequence.factorial(right);
			} else {
				return left;
			}
		}
		
		@Override
		public void permute(PermutationVisitor visitor, int[] array) {
			int[] buffer = new int[array.length];
			int from = 0;
			int to = Sequence.factorial(array.length)-1;
			
			for (int i = from; i <= to;) {
				int tmp = permute(array,buffer,i,array.length-1);
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


