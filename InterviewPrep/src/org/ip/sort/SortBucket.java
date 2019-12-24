package org.ip.sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ip.array.Utils;

// EPI 2018: 13.9
public class SortBucket {
	public static void main(String[] s) {
		Student[] students = new Student[] {student(25, "A"), student(20, "A"), student(22,"C"), student(20, "B") };
		new SortBucket().sort(students);
		Utils.println(students);
	}
	public void sort(Student[] students) {
		Map<Integer, Integer> mCount = new HashMap<Integer, Integer>();
		for (int i = 0; i < students.length; i++) {
			mCount.compute(students[i].age, (k, v) -> v == null ? 1 : v + 1);
		}
		Map<Integer,Integer> mIndex = new HashMap<Integer,Integer>();
		int index = 0;
		for (Entry<Integer,Integer> entry : mCount.entrySet()) {
			int target = index;
			index+=entry.getValue();
			mIndex.put(entry.getKey(), target);
		}
		while (!mIndex.isEmpty()) {
			Entry<Integer, Integer> entry = mIndex.entrySet().iterator().next();
			Student current = students[entry.getValue()];
			index = mIndex.get(current.age);
			Utils.swap(students, (int)entry.getValue(), index);
			int count = mCount.get(current.age);
			count--;
			if (count <= 0) {
				mIndex.remove(current.age);
			} else {
				mCount.put(current.age, count);
			}
		}
	}
	public static class StudentComparatorAge implements Comparator<Student> {

		@Override
		public int compare(Student o1, Student o2) {
			return Integer.compare(o1.age, o2.age);
		}
		
	}
	public static Student student(int age, String name) {
		return new Student(age, name);
	}
	public static class Student {
		int age;
		String name;
		public Student(int age, String name) {
			super();
			this.age = age;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Student [age=" + age + ", name=" + name + "]";
		}
		
	}
}
