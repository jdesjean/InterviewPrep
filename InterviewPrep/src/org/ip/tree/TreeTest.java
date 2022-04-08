package org.ip.tree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ip.primitives.Sequence;
import org.ip.tree.Tree.ArrayVisitor;
import org.ip.tree.iterator.BFS;
import org.ip.tree.iterator.OrderExterior;
import org.ip.tree.iterator.OrderInLeaves;
import org.ip.tree.iterator.OrderInParent;
import org.ip.tree.iterator.OrderPre;
import org.ip.tree.reducer.BooleanVoid;
import org.ip.tree.reducer.IsBSTIterative;
import org.ip.tree.reducer.IsBSTRecursive;

public class TreeTest {
	public static void main(String[] s) {
		testSibling();
	}

	public static Tree<Integer> small() {
		Node<Integer> root = node(2,node(1),node(3));
		return tree(root);
	}

	public static Tree<Integer> big() {
		Node<Integer> root = node(7,node(6),node(8));
		return tree(root);
	}

	public static Tree<Integer> bst1() {
		Node<Integer> root = 
				node(5,
					node(2,
							node(1),
							null),
					node(7, 
							node(6), 
							node(8, 
									null, 
									node(9))));
		return tree(root);
	}
	
	public static Tree<Integer> bst1Parent() {
		Node<Integer> root = node(5,node(2,node(1),null),node(7, node(6), node(8, null, node(9))));
		Tree<Integer> tree = tree(root);
		tree.assignParent();
		return tree;
	}
	
	public static Tree<Integer> balanced() {
		return bst1();
	}
	public static Tree<Integer> symetric() {
		Node<Integer> root = node(5,node(2,node(1),null),node(2, null, node(1)));
		return tree(root);
	}
	public static Tree<Integer> nonSymetric() {
		Node<Integer> root = node(5,node(2,node(1),null),node(2, node(1), null));
		return tree(root);
	}
	
	public static Tree<Integer> nonBalanced() {
		Node<Integer> root = node(5,node(2,node(1),null),node(7, node(6), node(8, null, node(9,null,node(10)))));
		return tree(root);
	}

	public static Tree<Integer> bst2() {
		Node<Integer> root = new Node<Integer>(45,
				new Node<Integer>(25, new Node<Integer>(15, new Node<Integer>(10), new Node<Integer>(20)),
						new Node<Integer>(30)),
				new Node<Integer>(65, new Node<Integer>(55, new Node<Integer>(50), new Node<Integer>(60)),
						new Node<Integer>(75, null, new Node<Integer>(80))));
		return tree(root);
	}
	public static Tree<Integer> bst3() {
		//2,3,5,7,11,13,17,19,23,29,31,37,31,43,47,53
		Node<Integer> root = node(19,
				node(7, 
						node(3, 
								node(2),
								node(5)),
						node(11,
								null,
								node(17,
										node(13)))),
				node(43,
						node(23,
								null,
								node(37,
										node(29,
												null,
												node(31)),
										node(41))),
						node(47,
								null,
								node(53))));
		return tree(root);
	}
	public static Tree<Integer> nonBST1() {
		Node<Integer> root = node(5, node(2, node(1), null), node(7, node(7), node(8, null, node(9)))); 
		return tree(root);
	}
	public static Tree<Integer> nonBST2() {
		Node<Integer> root = node(5, node(2), node(7, null, node(7, node(4, node(2), node(6)), node(9)))); 
		return tree(root);
	}
	public static Tree<Integer> unival() {
		Node<Integer> root = node(5, node(5, node(5), node(5)), node(5, null, node(5)));
		return tree(root);
	}
	//EPI 10.1
	public static Tree<Integer> nonBST3() {
		Node<Integer> root = node(314,node(6,node(271,node(28),node(0)),node(561,null,node(3,node(17)))),node(6,node(2,null,node(1,node(401,node(641)),node(257))),node(271,null,node(28))));
		return tree(root);
	}
	public static <T extends Comparable<T>> Node<T> node(T t) {
		return new Node<T>(t);
	} 
	public static <T extends Comparable<T>> Node<T> node(T t, Node<T> c1) {
		return new Node<T>(t,c1,null);
	}
	public static <T extends Comparable<T>> Node<T> node(T t, Node<T> c1, Node<T> c2) {
		return new Node<T>(t,c1,c2);
	}
	public static <T extends Comparable<T>> Node<T> node(T t, Node<T> c1, Node<T> c2, Node<T> c3) {
		return new Node<T>(t,new Node[]{c1,c2,c3});
	}
	public static <T extends Comparable<T>> Tree<T> tree(Node<T> root) {
		return new Tree<T>(root);
	}
	public static void testHeight() {
		System.out.println(unival().height());
	}
	public static void testTopK() {
		for (Iterator<Node<Integer>> iterator = bst2().inOrderIterator(5); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	public static void testReverseIterator() {
		for (Iterator<Node<Integer>> iterator = bst2().reverseOrderIterator(5); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	public static void testFromIn() {
		for (Iterator<Node<Integer>> iterator = Tree.fromIn(new Integer[]{1,2,3}).bfsIterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
		System.out.println("");
		for (Iterator<Node<Integer>> iterator = Tree.fromIn(new Integer[]{8,10,12,15,16,20,25}).bfsIterator(); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	public static void testUnival() {
		Tree<Integer> tree = unival();
		System.out.println(tree.countUnival());
	}
	public static void testLargestBst(){
		Tree<Integer> tree = nonBST2();
		System.out.println(Tree.largestBst(tree));
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
		System.out.println(tree.lcaParentfull(10, 20));
		System.out.println(tree.lcaParentfull(50, 80));
		System.out.println(tree.lcaParentfull(20, 60));
	}

	public static void testPopulateSibling() {
		Tree<Integer> tree = bst1();
		tree.populateSibling();
		for (Iterator<Node<Integer>> it1 = new BFS<Integer>(tree); it1.hasNext();) {
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
		System.out.println(new IsBSTIterative<Integer>(tree).execute());
	}

	public static void testBST() {
		Tree<Integer>[] trees = new Tree[] { bst1(), nonBST1() };
		for (Tree<Integer> tree : trees) {
			BooleanVoid[] executors = new BooleanVoid[] { new IsBSTRecursive<Integer>(tree),
					new IsBSTIterative<Integer>(tree) };
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
		for (Iterator<Node<Integer>> iterator = new OrderPre<Integer>(bst); iterator.hasNext();) {
			pre[index++] = iterator.next().value;
		}
		index = 0;
		for (Iterator<Node<Integer>> iterator = bst.inOrderIterator(); iterator.hasNext();) {
			in[index++] = iterator.next().value;
		}
		Tree<Integer> copy = Tree.fromPreIn(pre, in);
		for (Iterator<Node<Integer>> iterator = new OrderPre<Integer>(copy); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	
	public static void testCount() {
		for (int i = 0; i < 5; i++) {
			System.out.println(Tree.count(i) + "==" + Sequence.catalan(i));
		}
	}
	
	public static void testDiameter() {
		/*{"{0,1,{5,1,{4,1,{7,0}}}}", 0},*/
		System.out.println(Tree.diameter(node(0, node(5, node(4, node(7))))) + "==0");
		/*{"{0,1,{5,2,{8,0},{7,0}}}", 15},*/
		System.out.println(Tree.diameter(node(0, node(5, node(8), node(7)))) + "==15");
		/*{"{0,3,{1,2,{5,0},{7,0}},{1,2,{6,0},{5,0}},{1,2,{10,0},{9,0}}}", 19},*/
		System.out.println(Tree.diameter(node(0, node(1, node(5), node(7)), node(1, node(6), node(5)), node(1, node(10), node(9)))) + "==19");
		/*{"{0,3,{5,2,{8,0},{7,0}},{5,2,{9,0},{8,0}},{5,2,{10,0}, {9,0}}}", 29},*/
		System.out.println(Tree.diameter(node(0,node(5, node(8), node(7)), node(5, node(9), node(8)), node(5, node(10), node(9))))  + "==29");
	}
	public static void testBfs() {
		Tree<Integer> tree = nonBST3();
		List<List<Node<Integer>>> list = tree.bfs();
		for (int i = 0; i < list.size(); i++) {
			List<Node<Integer>> siblings = list.get(i);
			if (i > 0) System.out.println();
			for (int j = 0; j < siblings.size(); j++) {
				if (j > 0) System.out.print(',');
				System.out.print(siblings.get(j).value);
			}
		}
		
	}
	public static void testBalanced() {
		System.out.println(balanced().isBalanced());
		System.out.println(nonBalanced().isBalanced());
	}
	public static void testSymetric() {
		System.out.println(symetric().isSymmetric());
		System.out.println(nonSymetric().isSymmetric());
	}
	public static void testSumRootToLeaf() {
		//1000, 1001, 10110, 1100, 11000 
		//EPI Figure 10.4
		Tree<Integer> tree = tree(node(1,node(0,node(0,node(0),node(1)), node(1,null,node(1,node(0)))),node(1,node(0,node(0)),node(0,node(0,node(0))))));
		System.out.println(Tree.sumRootToLeaf(tree));
	}
	public static void testHasTargetSum() {
		System.out.println(Tree.hasTargetSum(nonBST3(),591));
		System.out.println(Tree.hasTargetSum(nonBST3(),592));
	}
	public static void testInOrderKth() {
		Tree<Integer> tree = tree(node(3,node(1,node(0),node(0)),node(2,node(1,node(0)))));
		for (int i = 1; i <= 7; i++) {
			System.out.println(Tree.inOrderKth(tree, i));
		}
	}
	public static void testSerializeOrder() {
		Serializer[] serializers = new Serializer[]{new SerializerOrderPre(), new SerializerOrderPost()};
		Tree<Integer> tree = nonBST1();
		for (int i = 0; i < serializers.length; i++) {
			Serializer serializer = serializers[i];
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(tree.count()*3/2*4);//3/2 times the size for null nodes; 4 bytes per integer 
			serializer.serialize(tree, outputStream);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			Tree<Integer> copy = serializer.deserialize(inputStream);
			System.out.println(tree.equals(copy));
		}
	}
	public static void testSuccessor() {
		Tree<Integer> tree = bst1();
		for (int i = 1; i <= 9; i++) {
			System.out.println(i + ":" + tree.successor(new FinderComparable<Integer>(tree).find(i)));
		}
	}
	public static void testOrderInParent() {
		Tree<Integer> tree = bst1Parent();
		for (Iterator<Node<Integer>> iterator = new OrderInParent<Integer>(tree); iterator.hasNext();) {
			System.out.print(iterator.next());
			System.out.print(',');
		} 
	}
	public static void testInOrderLeaves() {
		Tree<Integer> tree = bst1();
		for (Iterator<Node<Integer>> iterator = new OrderInLeaves<Integer>(tree); iterator.hasNext();) {
			System.out.print(iterator.next() + ",");
		}
	}
	public static void testToLinkedListLeaves() {
		LinkedList<Node<Integer>> list = bst1().toLinkedListLeaves();
		System.out.print(list);
	}
	public static void testOrderExteriorIterator() {
		Tree<Integer> tree = bst1();
		for (Iterator<Node<Integer>> iterator = new OrderExterior<Integer>(tree); iterator.hasNext();) {
			System.out.print(iterator.next() + ",");
		}
	}
	public static void testSibling() {
		Tree<Integer> tree = bst1();
		tree.assignSibling();
		for (Iterator<Node<Integer>> iterator = tree.bfsIterator(); iterator.hasNext();) {
			System.out.println(iterator.next().sibling);
		}
	}
	
}
