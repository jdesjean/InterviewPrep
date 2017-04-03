package org.ip.sort;

import java.util.BitSet;

public interface Missing {
	public static void main(String[] s) {
		//4 billions integers
		//1GiB mem | 10MiB mem
		
		//1) 1 integer = 4B
		//2) 4B * 4 billions = 2^2B * 2^30 = 2^32B
		//3) 1GiB = 1024^3 = 2^33B
		//4) 10MiB = 10*1024^2 = 2^3*2^23=2^26
		//5) 32 bit integer holds 2^31-1 positive values = 2^28B
		
		//1GiB can hold all integers in memory as well as bitmap
		//10MiB cannot hold bitmap  nor all integers
		
		
		int[] array = new int[128];
		for (int i = 0; i < array.length; i++) {
			if (i != 8) array[i] = i;
			else array[i] = 129;
		}
		Missing[] missing = new Missing[]{new MissingInMemory(), new MissingOOM(64)};
		File file = new File(array);
		for (int i = 0; i < missing.length; i++) {
			System.out.println(missing[i].missing(file));
		}
	}
	public int missing(File file);
	
	public static class File {
		private int[] array;
		public File(int[] array) {
			this.array=array;
		}
		public int size(){return array.length;}
		public int read(int[] buffer, int offset, int length) {
			int j = 0;
			for (int i = offset, right = Math.min(offset+length-1, array.length-1); i <= right; i++, j++) {
				buffer[j] = array[i];
			}
			return j;
		}
	}
	
	public static class MissingInMemory implements Missing {
		private int max;
		private int min;

		public MissingInMemory(){this.min=0;this.max=Integer.MAX_VALUE;}
		public MissingInMemory(int min, int max){this.min=min;this.max=max;}
		@Override
		public int missing(File file) {
			BitSet bitset = fileToBitset(file,min,max);
			int size = max-min;
			for (int i = 0; i < size; i++) {
				if (!bitset.get(i)) return min+i;
			}
			return -1;
		}
		
		private BitSet fileToBitset(File file, int min, int max) {
			int range = max - min;
			BitSet bitset = new BitSet(range);
			int size = file.size();
			int[] array = new int[size];
			int read = 0;
			int i = 0;
			do {
				read = file.read(array, i, size);
				if (read > 0) toBitSet(array,bitset,min,max);
				i+=read;
			} while(read != 0);
			return bitset;
		}
		
		private void toBitSet(int[] array, BitSet bitset, int min, int max) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] < min || array[i] > max) continue;
				bitset.set(array[i]-min);
			}
		}
	}
	public static class MissingOOM implements Missing {
		private int sizeInBits;
		public MissingOOM(int sizeInBits) {
			this.sizeInBits = sizeInBits;
		}
		@Override
		public int missing(File file) {
			int max = (int)Math.ceil(file.size()/sizeInBits); //max size of bitset if we use min number of buckets
			int min = sizeInBits/8/4; //min size of bitset if we use max number of buckets
			int bucketCount = min+(max-min)/2; //mid point uses less memory
			int bucketSize = Integer.MAX_VALUE/bucketCount;
			int[] buckets = new int[bucketCount];
			
			/*for (int i = 0; i < a.length; i++) {
				if (a[i] >= 0) buckets[a[i]/bucketsSize]++;
			}*/
			fromFileToBucket(file,buckets, new int[Math.min(file.size(), sizeInBits-bucketCount)], bucketSize);

			for (int i = 0; i < bucketCount; i++) {
				if (buckets[i] == bucketSize) continue; 
				return new MissingInMemory(i*bucketSize,i*bucketSize+bucketSize-1).missing(file);
			}
			return -1;
		}
		private void fromFileToBucket(File file, int[] buckets, int[] buffer, int bucketSize) {
			int j = 0;
			int read = 0;
			do {
				read = file.read(buffer, j, buffer.length);
				if (read > 0) toBuckets(buffer, buckets,bucketSize,read);
				j+=read;
			} while(read != 0);
		}
		private void toBuckets(int[] buffer, int[] buckets, int bucketSize,int read) {
			for (int i = 0; i < read; i++) {
				buckets[buffer[i]/bucketSize]++;
			}
		}
	}
}
