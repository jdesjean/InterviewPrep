package org.ip;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// Linkedin: compute average & presence over window
// Average window 1,2,3,4. window 3.
// Average window 1,2,3,6. window 3.
// Average window 2,2,3,3. window 3.
public class Window {
	public static void main(String[] s) {
		System.out.println(4/3d-1/3d+2d);
		System.out.println((2+3+4)/3d);
		
		System.out.println(6/3d-1/3d+2d);
		System.out.println((2+3+6)/3d);
		
		System.out.println((3-2)/3d+(2+2+3)/3d);
		System.out.println((2+3+3)/3d);
		Window window = new Window(3);
		window.add(1);
		window.add(2);
		window.add(3);
		System.out.println(window.average());
		System.out.println(window.present(1));
		window.add(4);
		System.out.println(window.average());
		System.out.println(window.present(1));
	}
	private int size;
	private double average;
	Map<Integer,Integer> counts = new HashMap<Integer,Integer>();
	Deque<Integer> list = new LinkedList<Integer>();
	public Window(int size) {
		this.size=size;
	}
	public void add(int value) {
		list.add(value);
		counts.compute(value, (k,v) -> v == null ? 1 : v + 1);
		if (list.size() > size) {
			int removed = list.remove();
			average-=removed/size;
			int count = counts.compute(removed, (k,v) -> v - 1);
			if (count == 0) {
				counts.remove(removed);
			}
		}
		average+=value/size;
	}
	public double average() {
		return average;
	}
	public boolean present(int value) {
		return counts.containsKey(value);
	}
}
