package org.ip.array;

// EPI 6.5
public class SearcherUnique implements Searcher {
	public static void main(String[] s) {
		SearcherUnique unique = new SearcherUnique();
		int[] array = new int[] {2,3,5,5,11,11,11,13}; 
		int length = unique.search(array, 0, array.length - 1);
		Utils.println(array, array.length);
		System.out.println(length);
	}
	@Override
	public int search(int[] array, int left, int right) {
		int i = left + 1;
		for (int j = left + 1; j <= right;) {
			if (array[j] == array[j-1]) {
				j++;
			} else {
				array[i++] = array[j++]; 
			}
		}
		return i;
	}
}
