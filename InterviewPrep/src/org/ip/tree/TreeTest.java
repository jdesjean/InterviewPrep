package org.ip.tree;

import java.util.Iterator;

import org.ip.tree.Tree.ArrayVisitor;
import org.ip.tree.Tree.BooleanReducer;

public class TreeTest {
	public static void main(String[] s) {
		testLargestBst();
	}

	public static Tree<Integer> small() {
		Node<Integer> root = new Node<Integer>(2, new Node<Integer>(1), new Node<Integer>(3));
		return new Tree<Integer>(root);
	}

	public static Tree<Integer> big() {
		Node<Integer> root = new Node<Integer>(7, new Node<Integer>(6), new Node<Integer>(8));
		return new Tree<Integer>(root);
	}

	public static Tree<Integer> bst1() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, new Node<Integer>(1), null),
				new Node<Integer>(7, new Node<Integer>(6), new Node<Integer>(8, null, new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}

	public static Tree<Integer> bst2() {
		Node<Integer> root = new Node<Integer>(45,
				new Node<Integer>(25, new Node<Integer>(15, new Node<Integer>(10), new Node<Integer>(20)),
						new Node<Integer>(30)),
				new Node<Integer>(65, new Node<Integer>(55, new Node<Integer>(50), new Node<Integer>(60)),
						new Node<Integer>(75, null, new Node<Integer>(80))));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> nonBST1() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, new Node<Integer>(1), null),
				new Node<Integer>(7, new Node<Integer>(7), new Node<Integer>(8, null, new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}
	public static Tree<Integer> nonBST2() {
		Node<Integer> root = new Node<Integer>(5, new Node<Integer>(2, null, null),
				new Node<Integer>(7, null, new Node<Integer>(7, new Node<Integer>(4, new Node<Integer>(2), new Node<Integer>(6)), new Node<Integer>(9))));
		return new Tree<Integer>(root);
	}
	public static void testLargestBst(){
		Tree<Integer> tree = nonBST2();
		System.out.println(tree.largestBst());
	}
	public static void testFlip() {
		Tree<Integer> tree = bst1();
		tree.flip();
		for (Iterator<Node<Integer>> iterator = tree.bfsIterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	public static void testClone() {
		Tree<Integer> tree1 = bst1();
		Tree<Integer> tree2 = tree1.clone();
		System.out.println(tree1.equals(tree2));
	}

	public static void testLCA() {
		Tree<Integer> tree = bst2();
		System.out.println(tree.lca(10, 20));
		System.out.println(tree.lca(50, 80));
		System.out.println(tree.lca(20, 60));
	}

	public static void testPopulateSibling() {
		Tree<Integer> tree = bst1();
		tree.populateSibling();
		for (Iterator<Node<Integer>> it1 = new BFSIterator<Integer>(tree); it1.hasNext();) {
			System.out.println(it1.next());
		}
	}

	public static void testPrintAllPaths() {
		bst1().printAllPaths(new ArrayVisitor<Integer>() {

			@Override
			public void visit(Integer[] visit, int length) {
				for (int i = 0; i < length; i++) {
					System.out.print(visit[i]);
					if (i < length - 1)
						System.out.print(",");
				}
				System.out.println("");
			}
		});
		testBSTBuild();
	}

	public static void testMerge() {
		Tree<Integer> tree = small();
		tree.merge(big());
		Iterator<Node<Integer>>[] iterators = new Iterator[] { tree.inOrderIterator(), tree.bfsIterator(),
				tree.postOrderIterator(), tree.preOrderIterator() };
		for (int i = 0; i < iterators.length; i++) {
			for (Iterator<Node<Integer>> iterator = iterators[i]; iterator.hasNext();) {
				System.out.println(iterator.next().value);
			}
			System.out.println("***");
		}
		System.out.println(tree.iterativeIsBSTReducer().execute());
	}

	public static void testBST() {
		Tree<Integer>[] trees = new Tree[] { bst1(), nonBST1() };
		for (Tree<Integer> tree : trees) {
			BooleanReducer[] executors = new BooleanReducer[] { tree.recursiveIsBSTReducer(),
					tree.iterativeIsBSTReducer() };
			for (int i = 0; i < executors.length; i++) {
				System.out.println(executors[i].execute());
			}
			System.out.println("***");
		}
	}

	public static void testIterators() {
		Tree<Integer>[] trees = new Tree[] { bst1(), nonBST1() };
		for (Tree<Integer> tree : trees) {
			Iterator<Node<Integer>>[] iterators = new Iterator[] { tree.inOrderIterator(), tree.bfsIterator(),
					tree.postOrderIterator(), tree.preOrderIterator() };
			for (int i = 0; i < iterators.length; i++) {
				for (Iterator<Node<Integer>> iterator = iterators[i]; iterator.hasNext();) {
					System.out.println(iterator.next().value);
				}
				System.out.println("***");
			}
		}
	}

	public static void testBSTBuild() {
		Tree<Integer> bst = TreeTest.bst1();
		int count = bst.count();
		Integer[] pre = new Integer[count];
		Integer[] in = new Integer[count];
		int index = 0;
		for (Iterator<Node<Integer>> iterator = new PreOrderIterator<Integer>(bst); iterator.hasNext();) {
			pre[index++] = iterator.next().value;
		}
		index = 0;
		for (Iterator<Node<Integer>> iterator = bst.inOrderIterator(); iterator.hasNext();) {
			in[index++] = iterator.next().value;
		}
		Tree<Integer> copy = Tree.fromPreIn(pre, in);
		for (Iterator<Node<Integer>> iterator = new PreOrderIterator<Integer>(copy); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
}
