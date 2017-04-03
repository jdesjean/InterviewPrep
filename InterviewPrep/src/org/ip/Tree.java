package org.ip;

import java.util.Arrays;
import java.util.List;

public class Tree {
	public static class Node {
		public int value;
		public List<Node> childs;
		public Node(int value, Node ...childs) {this.value=value;this.childs=Arrays.asList(childs);}
	}
	public static void testDiameter() {
		/*{"{0,1,{5,1,{4,1,{7,0}}}}", 0},*/
		System.out.println(diameter(new Node(0, new Node(5, new Node(4, new Node(7))))) + "==0");
		/*{"{0,1,{5,2,{8,0},{7,0}}}", 15},*/
		System.out.println(diameter(new Node(0, new Node(5, new Node(8), new Node(7)))) + "==15");
		/*{"{0,3,{1,2,{5,0},{7,0}},{1,2,{6,0},{5,0}},{1,2,{10,0},{9,0}}}", 19},*/
		System.out.println(diameter(new Node(0, new Node(1, new Node(5), new Node(7)), new Node(1, new Node(6), new Node(5)), new Node(1, new Node(10), new Node(9)))) + "==19");
		/*{"{0,3,{5,2,{8,0},{7,0}},{5,2,{9,0},{8,0}},{5,2,{10,0}, {9,0}}}", 29},*/
		System.out.println(diameter(new Node(0,new Node(5, new Node(8), new Node(7)), new Node(5, new Node(9), new Node(8)), new Node(5, new Node(10), new Node(9))))  + "==29");
	}
	
	public static int diameter(Node root) {
		return diameterRecursive(root).child;
	}
	private static class Diameter {
		public static final Diameter EMPTY = new Diameter(0,0);
		public int current;
		public int child;
		public Diameter(int current, int child) {this.current=current;this.child=child;}
	}
	public static Diameter diameterRecursive(Node root) {
		if (root == null) return Diameter.EMPTY;
		
		Diameter max = new Diameter(root.value,0);
		int maxCurrent = 0;
		int maxCurrent2 = 0;
		int maxDiameter = 0;
		for (Node child : root.childs) {
			Diameter current = diameterRecursive(child);
			if (current.current >= maxCurrent2 && current.current > maxCurrent) {
				maxCurrent2 = maxCurrent;
				maxCurrent = current.current;
			} else if (current.current >= maxCurrent2) {
				maxCurrent2 = current.current;
			}
			maxDiameter = Math.max(current.child, maxDiameter);
		}
		max.current+=maxCurrent;
		max.child=Math.max(maxCurrent2 > 0 ? maxCurrent+maxCurrent2 : 0, maxDiameter);
		
		return max;
	}
}
