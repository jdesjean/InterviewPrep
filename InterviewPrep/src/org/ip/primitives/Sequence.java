package org.ip.primitives;

import java.util.Arrays;
import java.util.Random;

import org.ip.array.Visitors.IntArrayVisitor;

public class Sequence {
	public static void main(String[] s) {
		testLookAndSay();
	}
	public static void testLookAndSay() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(lookAndSay(i));
		}
	}
	public static void testRandomDistribution() {
		random(new int[]{1,2,3}, new int[]{5,3,2});
	}
	public static void testFibonacci() {
		for (int i = 0; i <= 5; i++) {
			System.out.println(fibonacci(i));
		}
	}
	public static void testPascalTriangle() {
		pascalTriangle(6,new IntArrayVisitor(){

			@Override
			public void visit(int[] array, int length) {
				for (int i = 0; i < Math.min(array.length,length); i++) {
					if (i > 0) System.out.print(" ");
					System.out.print(array[i]);
				}
				System.out.println("");
			}
			
		});
	}
	public static void pascalTriangle(int n, IntArrayVisitor visitor) {
		int[] buffer = new int[n];
		buffer[0] = 1;
		for (int i = 0; i < n; i++) {
			int prev = 0;
			for (int j = 0; j <= i; j++) {
				int current = buffer[j];
				buffer[j] = prev + current;
				prev = current;
			}
			visitor.visit(buffer, i+1);
		}
	}
	public static int factorial(int n) {
		int fact = 1;
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}
	public static int catalan(int n) {
		return factorial(2*n) / (factorial(n+1)*factorial(n));
	}
	public static int fibonacci(int n) {
		if (n == 0) return n;
		if (n == 1 || n == 2) return 1;
		int p1 = 1;
		int p2 = 1;
		int sum = p2;
		for (int i = 3; i <= n; i++) {
			sum = p1+p2;
			p1 = p2;
			p2 = sum;
		}
		return p2;
	}
	private static int[] getCumulativeDistribution(int[] distribution) {
		int[] cumulativeDistribution = new int[distribution.length];
		int sum = 0;
		for (int i = 0; i < distribution.length; i++) {
			sum += distribution[i];
			cumulativeDistribution[i] = sum - 1;
		}
		return cumulativeDistribution;
	}
	public static int random(int[] numbers, int[] distribution) {
		int[] cumulativeDistribution = getCumulativeDistribution(distribution);
		int sum = org.ip.array.Utils.sum(distribution, distribution.length);
		Random random = new Random();
		int next = random.nextInt(sum);
		int index = Arrays.binarySearch(cumulativeDistribution, next);
		return index>=0 ? numbers[index] : numbers[Math.abs(index)-1];
	}
	public static int lookAndSay(int n) {
		if (n < 1) return 0;//throw
		int value = 1;
		for (int i = 1; i < n; i++) {
			value = lookAndSayNext(value);
		}
		return value;
	}
	private static int lookAndSayNext(int value) {
		int next = 0;
		int prevDigit = value % 10;
		int count = 0;
		while (value > 0) {
			int digit = value % 10;
			if (prevDigit == digit) {
				count++;
			} else if (count > 0){
				int length = Number.countDigit(count);
				int power = (int)Math.pow(10, length);
				next = next * power * 10 + prevDigit * power + count;
				count = 1;
			}
			value /= 10;
			prevDigit = digit;
		}
		if (count > 0) {
			int length = Number.countDigit(count);
			int power = (int)Math.pow(10, length);
			next = next * power * 10 + prevDigit * power + count;
		}
		return Number.reverse(next); 
	}
}
