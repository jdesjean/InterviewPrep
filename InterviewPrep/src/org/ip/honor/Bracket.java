package org.ip.honor;

import java.util.BitSet;

import org.ip.tree.Node;

// Google 08/16/12
public class Bracket {
	public static void main(String[] s) {
		//1,2,3,4,5,6,7,8
		Node node = 
			node(3, 
					node(1, 
							node(0), 
							node(1)),
					node(3,
							node(2),
							node(3)));
		BitSet bitset = new BitSet();
		int size = toBitset(node, bitset);
		Node root = toNode(bitset, size);
		System.out.println(new Bracket().whoWon(bitset, 0, 1, size));
		System.out.println(new Bracket().whoWon(bitset, 1, 3, size));
		System.out.println(new Bracket().whoWon(bitset, 2, 3, size));
		System.out.println(new Bracket().whoWon(bitset, 0, 3, size));
	}
	public int whoWon(BitSet bitset, int t1, int t2, int size) {
		BitSet result = new BitSet();
		BitSet status = new BitSet(size / 2 - 1);
		status.set(0, size / 2 - 1);
		for (int left = 0, right = size - 1, i = size / 2 - 2; i >= 0; i--) {
			int mid = (right - left) / 2 + left;
			status.clear(i);
			long[] a = status.toLongArray();
			int statusValue = a.length > 0 ? (int)a[0] : 0;
			if (t1 <= statusValue  && t2 <= statusValue ) {
				right = mid - 1;
			} else if (t1 > statusValue  && t2 > statusValue) {
				result.set(i);
				left = mid + 1;
			} else {
				if (bitset.get(mid)) {
					result.set(i);
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
		}
		long[] a = result.toLongArray();
		return a.length > 0 ? (int)a[0] : 0;
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
