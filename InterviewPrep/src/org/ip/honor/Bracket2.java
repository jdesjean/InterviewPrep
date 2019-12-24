package org.ip.honor;

import java.util.BitSet;

import org.ip.tree.Node;

// Google 08/16/12
public class Bracket2 {
	public static void main(String[] s) {
		//Teams: 0,1,2,3,4,5,6,7
		//Games are numbered last game first
		//Games: 7, 6,5, 4,3,2,1
		//Games: 1, 2,3, 4,5,6,7
		
		//TC1:   7, 3,7, 1,3,5,7
		//TC2:   7, 3,7, 0,3,4,7
		BitSet bitset = new BitSet();
		bitset.set(0);
		bitset.set(1);
		bitset.set(2);
		//bitset.set(3);
		bitset.set(4);
		//bitset.set(5);
		bitset.set(6);
		int ngames = 7; //n team, n - 1 games
		Bracket2 bracket = new Bracket2();
		for (int i = 7; i >= 1; i--) {
			System.out.println(bracket.whoWon(bitset, i, ngames));
		}
	}
	public int whoWon(BitSet bitset, int g, int size) {
		int w1 = followWinner(bitset, g, size);
		int w2 = followParent(bitset, g, size);
		return w1 | w2;
	}
	public int followParent(BitSet bitset, int g, int size) {
		int height = (int)Math.ceil(Math.log(size)) + 1;
		int index = size - g;
		int winner = 0;
		int power = 1;
		int count = 0;
		for (int i = index + 1; i > 0; ) {//7-1
			int parent = i / 2;
			if (parent <= 0) break;
			count++;
			if (parent * 2 + 1 == i) { // winner on the right
				winner |= power;
			}
			power <<= 1;
			i = parent;
		}
		return winner << (height - count);
	}
	public int followWinner(BitSet bitset, int g, int size) {
		int winner = 0;
		for (int i = size - g + 1; i <= size;) { //1-7
			winner <<= 1;
			int value = bitset.get(i - 1) ? 1 : 0; 
			winner |= value;
			i = i * 2 + value;
		}
		return winner;
	}
	public static Node toNode(BitSet bitset, int size) {
		return _toNode(bitset, 0, size - 1, new IntegerHolder());
	}
	public static Node _toNode(BitSet bitset, int l, int r, IntegerHolder holder) {
		if (l > r) return null;
		//0 -> 1,2  1-> 3,4  2 -> 5,6
		int index = (r - l) / 2 + l;
		Node<Integer> left = _toNode(bitset, l, index - 1, holder);
		int value = 0;
		if (left == null) {
			value = holder.value++; 
		}
		Node<Integer> right = _toNode(bitset, index + 1, r, holder);
		if (bitset.get(index)) {
			if (right != null) {
				value = right.value;
			}
		} else {
			if (left != null) {
				value = left.value;
			}
		}
		return new Node(value, left, right);
	}
	public static int toBitset(Node node, BitSet bitset) {
		IntegerHolder holder = new IntegerHolder();
		_toBitset(node, bitset, holder);
		return holder.value;
	}
	private static void _toBitset(Node node, BitSet bitSet, IntegerHolder index) {
		if (node == null) return;
		_toBitset(node.getLeft(), bitSet, index);
		if (node.getRight() != null && node.value == node.getRight().value) {
			bitSet.set(index.value);
			index.value++;
		} else {
			index.value++;
		}
		_toBitset(node.getRight(), bitSet, index);
	}
	public static class IntegerHolder {
		int value = 0;
	}
	public static Node node(int value) {
		return new Node(value, null, null);
	}
	public static Node node(int value, Node left, Node right) {
		return new Node(value, left, right);
	}
	public static Node node(int value, Node left) {
		return new Node(value, left, null);
	}
	
}
