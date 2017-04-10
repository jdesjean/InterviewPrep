package org.ip;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class MedianStream {
	public static void main(String[] s) {
		printMedians(Arrays.asList(new Integer[]{5,6,8,2,4,1}).iterator());
	}
	public static void printMedians(Iterator<Integer> input) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>()
	    {
	        public int compare( Integer x, Integer y )
	        {
	            return y - x;
	        }
	    });
	    for (;input.hasNext();) {
	        int value = input.next();
	        if (maxHeap.size() == 0 || value < maxHeap.peek()){
	        	maxHeap.add(value);
	        } else {
	        	minHeap.add(value);
	        }
	        if (maxHeap.size() > minHeap.size()) {
	           minHeap.add(maxHeap.remove());
	        } else if (minHeap.size() > maxHeap.size()) {
	            maxHeap.add(minHeap.remove());
	        }
	        
	        //print
	        int median = 0;
	        if (minHeap.size() > maxHeap.size()){
	        	median = minHeap.peek();
	        } else if (maxHeap.size() > minHeap.size() || maxHeap.size() > 0){
	        	median = maxHeap.peek();
	        }
	        System.out.println(median);
	    }
	}
}
