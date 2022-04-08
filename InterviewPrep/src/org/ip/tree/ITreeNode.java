package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;

import org.ip.siblingtree.Node;

public interface ITreeNode<T> extends Cloneable {
	public void addChild(ITreeNode<T> node);

	public T getVal();

	public List<ITreeNode<T>> getChilds();

	public static <T> ITreeNode<T> fromBfs(Integer[] values, IntFunction<ITreeNode<T>> constructor) {
		if (values == null || values.length == 0 || values[0] == null)
			return null;
		List<ITreeNode<T>> parents = new ArrayList<>();
		ITreeNode<T> root = constructor.apply(values[0]);
		parents.add(root);
		for (int i = 1; i < values.length;) {
			List<ITreeNode<T>> childs = new ArrayList<>(parents.size() * 2);
			for (ITreeNode<T> parent : parents) {
				Integer left = i < values.length ? values[i++] : null;
				Integer right = i < values.length ? values[i++] : null;
				if (left != null) {
					ITreeNode<T> child = constructor.apply(left);
					parent.addChild(child);
					childs.add(child);
				}
				if (right != null) {
					ITreeNode<T> child = constructor.apply(right);
					parent.addChild(child);
					childs.add(child);
				}
			}
			parents = childs;
		}
		return root;
	}

	public static <T> void toBfs(ITreeNode<T> node, Integer[] values) {
		AtomicInteger idx = new AtomicInteger();
		toBfs(node, (value) -> values[idx.getAndIncrement()] = value != null ? (Integer) value.getVal() : null);
	}
	
	public static <T> void toBfs(ITreeNode<T> node, Consumer<ITreeNode<T>> consumer) {
		Deque<ITreeNode<T>> nodes = new LinkedList<>();
		nodes.add(node);
		while (!nodes.isEmpty()) {
			ITreeNode<T> current = nodes.removeFirst();
			consumer.accept(current);
			if (current != null) {
				List<ITreeNode<T>> childs = current.getChilds();
				for (ITreeNode<T> child : childs) {
					nodes.addLast(child);
				}
			}
		}
	}

	public static <T> void print(ITreeNode<T> node) {
		Integer[] values = new Integer[len(node)];
		ITreeNode.toBfs(node, values);
		Arrays.toString(values);
	}

	public static <T> int len(ITreeNode<T> node) {
		AtomicInteger count = new AtomicInteger();
		len(node, count);
		return count.get();
	}

	private static <T> void len(ITreeNode<T> node, AtomicInteger count) {
		count.incrementAndGet();
		if (node == null)
			return;
		for (ITreeNode<T> child : node.getChilds()) {
			len(child, count);
		}
	}
	public static <T> ITreeNode<T> find(ITreeNode<T> node, Integer k) {
		if (node == null)
			return null;
		if (node.getVal() == k)
			return node;
		for (ITreeNode<T> child : node.getChilds()) {
			ITreeNode<T> left = find(child, k);
			if (left != null)
				return left;
		}
		return null;
	}
	
	public static <T> ITreeNode<T> clone(ITreeNode<T> node, IntFunction<ITreeNode<T>> constructor) {
		Integer[] values = new Integer[len(node)];
		toBfs(node, values);
		return fromBfs(values, constructor);
	}
	
	public static <T> String toString(ITreeNode<T> node) {
		Integer[] values = new Integer[len(node)];
		toBfs(node, values);
		return Arrays.toString(values);
	}

	public static <T> int depth(ITreeNode<T> node) {
		if (node == null) return 0;
		int max = 0;
		for (ITreeNode<T> child : node.getChilds()) {
			max = Math.max(max, depth(child) + 1);
		}
		return max;
	}
}
