package org.ip.sort;

import org.ip.sort.HeapInt.Big;
import org.ip.sort.HeapInt.Best;
import org.ip.sort.HeapInt.Small;

public class TopKHeapInt implements TopKInt{
	public enum Top {
		MIN
		{
	        @Override
	        public int index(int length, int k) {
	            return 0;
	        }
	    },
	  	MAX {
	    	@Override
	        public int index(int length, int k) {
	            return length - k;
	        }
	    };
		private Best best;
	    static {
	        MAX.best = new Big();
	        MIN.best = new Small();
	    }
	    public abstract int index(int length, int k);
	    public Best best(){return best;}
	}
	private Top top;
	public TopKHeapInt(){this.top = Top.MAX;}
	public TopKHeapInt(Top top){
		this.top=top;
	}
	@Override
	public void solve(int[] array, int k) {
		HeapInt heap = new HeapInt(k,top.best());
		int size = Math.min(k, array.length);
		for (int i = 0; i < size; i++) {
			heap.add(array[i]);
		}
		for (int i = size; i < array.length; i++) {
			if (array[i] > heap.peek()) {
				heap.remove();
				heap.add(array[i]);
			}
		}
		heap.copyTo(array, top.index(array.length, k));
	}

}
