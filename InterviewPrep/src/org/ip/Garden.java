package org.ip;

import java.util.Arrays;

import org.ip.Permutator.PermutatorFactoradicIterative;
import org.ip.Permutator.PermutatorIterative;
import org.ip.Permutator.PermutatorRecursive;
import org.ip.array.Utils;
import org.ip.array.Utils.Filter;
import org.ip.array.Utils.PermutationVisitor;

public class Garden {
	public static void main(String[] s) {
		solve(7, 11);
	}
	public static final double MIN = Math.pow(10, -12);
	public static int[] square(int n) {
		int[] squares = new int[n+1];
		for (int i = 0; i <= n; i++) {
			squares[i] = (int)Math.pow(i,2);
		}
		return squares;
	}
	public static class Pair {
		public int x;
		public int y;
		public Pair(int x, int y) {this.x = x; this.y=y;}
		public String toString() {
			return x + " " + y;
		}
	}
	public static void solve(int x1, int y1) {
		double[][] garden = new double[25][25];
		double[][] garden2 = new double[25][25];
		int[][] position = new int[25][25];
		int[] squares = square(25);
		
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				garden[i][j] = Math.sqrt(sum);
				garden2[i][j] = garden[i][j];
				int index = Arrays.binarySearch(squares, sum);
				position[i][j] = index >= 0 ? -1 : 0;
			}
		}
		
		position[x1+12][y1+12] = 1;
		for (int k = 0; k < 11; k++) {
			for (int x = 0; x < 25; x++) {
				for (int y = 0; y < 25; y++) {
					if (position[x][y] != 0) continue;
					double precision1 = Math.log10(Math.abs(garden2[x][y] - Math.round(garden2[x][y])));
					for (int i = 0; i < 25; i++) {
						for (int j = 0; j < 25; j++) {
							if (position[i][j] != 0) continue;
							double distance = garden2[x][y]+garden[i][j];
							double precision2 = Math.log10(Math.abs(distance - Math.round(distance)));
							if (precision2 < precision1) {
								precision1 = precision2;
								garden2[x][y] = distance;
							}
						}
					}
				}
			}
			double precisionMin = 0;
			int minI = 0;
			int minJ = 0;
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 25; j++) {
					if (position[i][j] != 0) continue;
					double precision = Math.log10(Math.abs(garden2[i][j] - Math.round(garden2[i][j])));
					if (precision < precisionMin) {
						precisionMin = precision;
						minI = i;
						minJ = j;
					}
				}
			}
			position[minI][minJ] = 1;
		}
		double precisionMin = 0;
		for (int x = 0; x < 25; x++) {
			for (int y = 0; y < 25; y++) {
				if (position[x][y] != 0) continue;
				double precision = Math.log10(Math.abs(garden2[x][y] - Math.round(garden2[x][y])));
				precisionMin = Math.min(precisionMin, precision);
			}
		}
		for (int x = 0; x < 25; x++) {
			for (int y = 0; y < 25; y++) {
				if (position[x][y] <= 0) continue;
				System.out.println((x-12) + " " + (y-12));
			}
		}
		System.out.println(precisionMin);
	}
	public static void solve4(int x, int y) {
		int[] squares = square(25);
		int count = 0;
		double[][] garden = new double[25][25];
		for (int i = 0, k = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				int index = Arrays.binarySearch(squares, sum);
				garden[i][j] = Math.sqrt(sum);
				if (index >= 0) continue;
				count++;
			}
		}
		Pair[] pairs = new Pair[count];
		for (int i = 0, k = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				int index = Arrays.binarySearch(squares, sum);
				if (index >= 0) continue;
				pairs[k++] = new Pair(i-12,j-12);
			}
		}
		System.out.println(greedy(pairs,garden,garden[x+12][y+12]));
		for (int i = 0; i < 12; i++) {
			System.out.println(pairs[i]);
		}
	}
	public static double greedy(Pair[] pairs, double[][] garden, double distance) {
		for (int i = 0; i < 12; i++) {
			double minPrecision = 0;
			double minDistance = distance;
			int k = 0;
			for (int j = i; j < pairs.length; j++) {
				double d2 = distance+garden[pairs[j].x+12][pairs[j].y+12];
				double precision = Math.log10(Math.abs(d2 - Math.round(d2)));
				if (precision < minPrecision) {
					minPrecision = precision;
					minDistance = d2;
					k = j;
				}
			}
			Utils.swap(pairs, i, k);
			distance = minDistance;
		}
		return distance;
	}
	public static void solve3(int x, int y) {
		int[] squares = square(25);
		int count = 0;
		double[][] garden = new double[25][25];
		for (int i = 0, k = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				int index = Arrays.binarySearch(squares, sum);
				garden[i][j] = Math.sqrt(sum);
				if (index >= 0) continue;
				count++;
			}
		}
		Pair[] pairs = new Pair[count];
		for (int i = 0, k = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				int index = Arrays.binarySearch(squares, sum);
				if (index >= 0) continue;
				pairs[k++] = new Pair(i-12,j-12);
			}
		}
		
		Filter filter = new Filter(){
			@Override
			public boolean test(Pair[] array, int i) {
				return true;
			}
		};
		PermutationVisitor visitor = new PermutationVisitor(){
			@Override
			public boolean visit(Pair[] array, double distance) {
				if (Math.log10(Math.abs(distance - Math.round(distance))) > -12) return false;
				for (int i = 0; i < 2; i++) {
					System.out.println(array[i]);
				}
				return true;
			}
		};
		PermutatorRecursive permutator = new PermutatorRecursive(filter);
		permutator.permute(visitor, pairs,garden,garden[x+12][y+12]);
	}
	public interface PermutationVisitor {
		public boolean visit(Pair[] pair,double distance);
	}
	public interface Filter {
		public boolean test(Pair[] pair, int i);
	}
	public static class PermutatorRecursive {
		private Filter filter;

		public PermutatorRecursive(Filter filter) {
			this.filter=filter;
		}
		private boolean permute(PermutationVisitor visitor, Filter filter, Pair[] array, int index, double[][] garden, double distance) {
			if (index == 3) {
				return visitor.visit(array, distance);
			}
			for (int i = index; i < array.length; i++) {
				Utils.swap(array,i,index);
				if (filter.test(array,index)) {
					if (permute(visitor,filter,array,index+1,garden,distance+garden[array[i].x+12][array[i].y+12])) return true;
				}
				Utils.swap(array,i,index);
			}
			return false;
		}

		public void permute(PermutationVisitor visitor, Pair[] array, double[][] garden, double distance) {
			permute(visitor,filter,array,0, garden, distance);
		}
	}
	
	public static void solve2(int x, int y) {
		double[][] garden = new double[25][25];
		byte[][] position = new byte[25][25];
		int[] squares = square(25);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				int sum = (int)Math.pow(i-12, 2) + (int)Math.pow(j-12, 2);
				int index = Arrays.binarySearch(squares, sum);
				garden[i][j] = Math.sqrt(sum);
				position[i][j] = (byte)(index >= 0 ? -1 : 0);
			}
		}
		double distance = garden[x+12][y+12];
		position[x+12][y+12] = 1;
		solve2(garden,position,11,distance);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				if (position[i][j] == 1) {
					System.out.println(i + " " + j);
				}
			}
		}
	}
	public static double solve2(double[][] garden, byte[][] position, int depth, double distance) {
		if (depth == 0) {
			return distance;
		}
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				if (position[i][j] != 0) continue;
				position[i][j] = 1;
				double d = solve2(garden,position,depth-1,distance+garden[i][j]);
				if (Math.log10(Math.abs(d - Math.round(d))) <= -12) return d;
				position[i][j] = 0;
			}
		}
		return distance;
	}
}
