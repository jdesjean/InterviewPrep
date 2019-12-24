package org.ip.sort;

import java.util.Arrays;

import org.ip.array.Utils;

// EPI 2018: 13.4
public class Duplicates {
	public static void main(String[] s) {
		Name[] array = new Name[] {new Name("Ian", "Botham"), new Name("David", "Gower"), new Name("Ian", "Bell"), new Name("Ian", "Chappell"), new Name("Jeff", "Dodger"), new Name("Jeff", "Pogo"), new Name("Zinga", "Mendez")};
		int length = new Duplicates().filter(array);
		Utils.println(array, length);
	}
	public int filter(Name[] names) {
		Arrays.sort(names);
		int i = 0;
		for (int j = 1; j < names.length; j++) {
			names[i + 1] = names[j];
			if (names[j].compareTo(names[i]) != 0) {
				i++;
			}
		}
		return i + 1;
	}
	public static class Name implements Comparable<Name>{
		public String first;
		public String last;
		public Name(String first, String last) {
			super();
			this.first = first;
			this.last = last;
		}
		@Override
		public int compareTo(Name other) {
			return first.compareTo(other.first);
		}
		@Override
		public String toString() {
			return "Name [first=" + first + ", last=" + last + "]";
		}
	}
}
