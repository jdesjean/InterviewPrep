package org.ip;

import java.util.Scanner;

/**
 * <a href="https://leetcode.com/discuss/interview-question/396996/">OA 2020</a>
 */
public class GCDTree {
	public static void main(String[] s) {
		String tree = "3\n" + 
				"10\n" + 
				"9 18\n" + 
				"13 -1 8 8\n" + 
				"12 26 -1 -1 6 9";
		System.out.println(new GCDTree().solve(tree));
	}
	public int solve(String s) {
		Scanner sc = new Scanner(s);
	    int n = sc.nextInt();

	    if(n==-1){
	        System.out.print(-1);
	        return -1;
	    }

	    int minGCD = Integer.MAX_VALUE, maxGCD = Integer.MIN_VALUE;
	    int countPrev = 0;
	    for (int i = 0; i <= n; i++) {
	        if (i == 0) {
	            int root = sc.nextInt();
	            if(root !=-1)
	            countPrev = 1;
	        } else {
	            int count  = 0;
	            for (int j = 0; j < (2*countPrev); j+=2) {
	                int a = sc.nextInt();
	                if(a!=-1) {
	                	count++;
	                }
	                int b = sc.nextInt();
	                if(b!=-1) {
	                	count++;
	                }
	                if (a == -1 || b == -1) {
	                    continue;
	                }
	                int newGCD = gcd(a, b);
	                if (newGCD < minGCD) {
	                    minGCD = newGCD;
	                }
	                if (newGCD > maxGCD) {
	                    maxGCD = newGCD;
	                }
	            }
	            countPrev = count;
	        }
	    }
	    if (minGCD == Integer.MAX_VALUE && maxGCD == Integer.MIN_VALUE) {
	        return 0;
	    }
	    return maxGCD - minGCD;
	}

	public static int gcd (int a, int b) {
	    if(b == 0){
	        return a;
	    }
	    return gcd(b, a % b);
	}
}
