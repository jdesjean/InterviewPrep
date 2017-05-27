package org.ip.sort;

import java.util.Arrays;

import org.ip.sort.HeapInt.Big;

public class IteratorArrayTopKTest {
	public static void main(String[] s) {
		int[] array = new int[]{5,7,8,3,2,7,8,3,7,9,2,1};
		HeapInt heap = new HeapInt(Arrays.copyOf(array, array.length),0,array.length-1,new Big());
		int k = 3;
		IteratorInt[] iterators = new IteratorInt[]{new IteratorArrayHeapTopKHeap(heap.a,k),new IteratorArrayTopKHeapInt(array,k), new IteratorArrayTopKPartitionInt(Arrays.copyOf(array, array.length),k)};
		for (int i = 0; i < iterators.length; i++) {
			for (IteratorInt iterator = iterators[i]; iterator.hasNext();) 
			{
				System.out.print(iterator.next());
				if (iterator.hasNext()) System.out.print(",");
			}
			System.out.println();
		}
	}
}
