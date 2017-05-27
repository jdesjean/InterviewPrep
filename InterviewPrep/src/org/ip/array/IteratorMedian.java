package org.ip.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

//EPI: 11.5
//Time: O(log(n)), Space O(n)
public class IteratorMedian implements Iterator<Float> {
	public static void main(String[] s) {
		for (Iterator<Float> iterator = new IteratorMedian(Arrays.asList(new Integer[] { 5, 6, 8, 2, 4, 1 }).iterator()); iterator.hasNext();) {
			System.out.print(iterator.next());
			if (iterator.hasNext()) System.out.print(",");
		}
	}

	private Iterator<Integer> iterator;
	private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>() {
		public int compare(Integer x, Integer y) {
			return y - x;
		}
	});

	public IteratorMedian(Iterator<Integer> input) {
		this.iterator = input;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Float next() {
		int value = iterator.next();
		
		boolean bIsMinTarget = minHeap.isEmpty()|| value >= minHeap.peek(); 
		PriorityQueue<Integer> target = bIsMinTarget ? minHeap : maxHeap;
		PriorityQueue<Integer> other = !bIsMinTarget ? minHeap : maxHeap;
		
		target.add(value);
		
		if (target.size() > other.size() + 1) other.add(target.remove()); 
		
		return target.size() > other.size() ? target.peek() : (float)(0.5*maxHeap.peek()+0.5*minHeap.peek());  
	}
}
