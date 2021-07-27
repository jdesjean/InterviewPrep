package org.ip.tree;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/serialize-and-deserialize-binary-tree/">LC: 297</a>
 * <a href="https://leetcode.com/problems/serialize-and-deserialize-bst/">LC: 449</a>
 */
public class SerializeTree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {2,1,3}, new Integer[] {2,1,3}};
		Object[] tc2 = new Object[] {new Integer[] {null}, new Integer[] {null}};
		Object[] tc3 = new Object[] {new Integer[] {3,1,null,null,2}, new Integer[] {3,1,null,null,2}};
		Object[] tc4 = new Object[] {new Integer[] {4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2}, new Integer[] {4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2}};
		
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4);
		Codec[] solvers = new Codec[] {new PreOrderDecimal(), new PreOrderBytes()};
		for (Object[] tc : tcs) {
			TreeNode.print(TreeNode.fromBfs((Integer[]) tc[0]));
			for (Codec solver : solvers) {
				TreeNode node = TreeNode.fromBfs((Integer[])tc[1]);
				String serialized = solver.serialize(node);
				System.out.print(" : {serialize:"  + serialized + ", deserialize: ");
				System.out.println();
				TreeNode.print(solver.deserialize(serialized));
				System.out.print("}");
			}
			System.out.println();
			System.out.println();
		}
	}
	private static void intToString(StringBuilder buf, int value) {
        for (int i = 1; i >= 0; i--) {
        	buf.append((char) ((value >> (16 * i)) & 0xffff));
        }
    }
	private static int stringToInt(String buf, int start, int end) {
        int result = 0;
        for (int i = start; i < end; i++) {
            result = (result << 16) + (int) buf.charAt(i); 
        }
        return result;
    }
	public static class PreOrderBytes implements Codec {

		@Override
		public String serialize(TreeNode root) {
			StringBuilder sb = new StringBuilder(TreeNode.len(root));
			serialize(x -> {
				if (x == null) {
					intToString(sb, Integer.MIN_VALUE);
				} else {
					intToString(sb, x.val);
				}
			}, root);
			return sb.toString();
		}
		
		void serialize(Consumer<TreeNode> consumer, TreeNode node) {
			if (node == null) {
				consumer.accept(null);
				return;
			}
			consumer.accept(node);
			serialize(consumer, node.left);
			serialize(consumer, node.right);
		}

		@Override
		public TreeNode deserialize(String data) {
			Iterator<Integer> iterator = new Iterator<>() {
				int index = 0;
				@Override
				public boolean hasNext() {
					return index < data.length();
				}

				@Override
				public Integer next() {
					Integer value = stringToInt(data, index, index += 2);
					return value != Integer.MIN_VALUE ? value : null;
				}
				
			};
			return deserialize(iterator);
		}
		
		TreeNode deserialize(Iterator<Integer> iterator) {
			if (!iterator.hasNext()) {
				return null;
			}
			Integer next = iterator.next();
			if (next == null) {
				return null;
			}
			TreeNode node = new TreeNode(next);
			node.left = deserialize(iterator);
			node.right = deserialize(iterator);
			return node;
		}
		
	}
	public static class PreOrderDecimal implements Codec {

		@Override
		public String serialize(TreeNode root) {
			StringBuilder sb = new StringBuilder();
			serialize(x -> {
				if (sb.length() > 0) {
					sb.append(",");
				}
				if (x == null) {
					sb.append("N");
				} else {
					sb.append(x.val);
				}
			}, root);
			return sb.toString();
		}
		
		void serialize(Consumer<TreeNode> consumer, TreeNode node) {
			if (node == null) {
				consumer.accept(null);
				return;
			}
			consumer.accept(node);
			serialize(consumer, node.left);
 			serialize(consumer, node.right);
		}

		@Override
		public TreeNode deserialize(String s) {
			String[] val = s.split(",");
			Integer[] vals = new Integer[val.length];
			for (int i = 0; i < val.length; i++) {
				if (val[i].compareTo("N") != 0) {
					vals[i] = Integer.parseInt(val[i]);
				}
			}
			return deserialize(vals, new AtomicInteger());
		}
		TreeNode deserialize(Integer[] vals, AtomicInteger index) {
			if (index.get() >= vals.length) {
				return null;
			}
			int current = index.getAndIncrement();
			if (vals[current] == null) {
				return null;
			}
			TreeNode node = new TreeNode(vals[current]);
			node.left = deserialize(vals, index);
			node.right = deserialize(vals, index);
			return node;
		}
		
	}
	public interface Codec {
		public String serialize(TreeNode root);
		public TreeNode deserialize(String s);
	}
}
