package org.ip;

public class Levenshtein {
	public static void main(String[] s) {
		System.out.println(distance("kitten","sitting"));
		//System.out.println(distance("aaa","bbb"));
		//System.out.println(distance("aaa",""));
	}
	public static int distance(String word1, String word2) {
		return distance(word1,word1.length()-1,word2,word2.length()-1);
	}
	public static int distance(String word1, int n1, String word2, int n2) {
		if (n1 < 0 && n2 < 0) return 0;
		else if (n1 < 0) return n2+1;
		else if (n2 < 0) return n1+1;
		
		if (word1.charAt(n1) == word2.charAt(n2)) return distance(word1,n1-1,word2,n2-2);
		
		int nRemove = distance(word1,n1-1,word2,n2);
		int nAdd = distance(word1,n1,word2,n2-1);
		int nEdit = distance(word1,n1-1,word2,n2-1);
		return ArrayUtils.min(nRemove, nAdd, nEdit) + 1;
	}
}
