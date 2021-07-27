package org.ip;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

import org.ip.array.Utils;
import org.ip.tree.TreeNode;

public class Test {
	public static void apply(IntFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (IntFunction solver : solvers) {
				Utils.print(solver.apply((Integer)tc[1]));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(IntBinaryOperator[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (IntBinaryOperator solver : solvers) {
				Utils.print(solver.applyAsInt((Integer)tc[1], (Integer)tc[2]));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(ToDoubleFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (ToDoubleFunction solver : solvers) {
				Utils.print(solver.applyAsDouble(clone(tc[1])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(ToIntFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (ToIntFunction solver : solvers) {
				Utils.print(solver.applyAsInt(clone(tc[1])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(ToIntBiFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (ToIntBiFunction solver : solvers) {
				Utils.print(solver.applyAsInt(clone(tc[1]), clone(tc[2])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(Predicate[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (Predicate solver : solvers) {
				Utils.print(solver.test(clone(tc[1])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(BiPredicate[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (BiPredicate solver : solvers) {
				Utils.print(solver.test(clone(tc[1]), clone(tc[2])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(Consumer[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (Consumer solver : solvers) {
				Object tc1 = clone(tc[1]);
				solver.accept(tc1);
				Utils.print(tc1);
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(IntUnaryOperator[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (IntUnaryOperator solver : solvers) {
				Utils.print(solver.applyAsInt((Integer) tc[1]));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(Function[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (Function solver : solvers) {
				Utils.print(solver.apply(clone(tc[1])));
				System.out.println();
			}
			System.out.println();
		}
    }
	
	public static void apply(BiFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (BiFunction solver : solvers) {
				Utils.print(solver.apply(clone(tc[1]), clone(tc[2])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(VarArgsFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (VarArgsFunction solver : solvers) {
				Object[] copy = Arrays.copyOfRange(tc, 1, tc.length);
				Utils.print(solver.apply(copy));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(TriFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (TriFunction solver : solvers) {
				Utils.print(solver.apply(clone(tc[1]), clone(tc[2]), clone(tc[3])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(TriPredicate[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (TriPredicate solver : solvers) {
				Utils.print(solver.test(clone(tc[1]), clone(tc[2]), clone(tc[3])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public static void apply(QuadFunction[] solvers, Object[][] tcs) {
		for (int i = 0; i < tcs.length; i++) {
			Object[] tc = tcs[i];
			System.out.println("TC " + i);
			Utils.print(tc[0]);
			System.out.println();
			for (QuadFunction solver : solvers) {
				Utils.print(solver.apply(clone(tc[1]), clone(tc[2]), clone(tc[3]), clone(tc[4])));
				System.out.println();
			}
			System.out.println();
		}
    }
	public interface TriPredicate<A, B, C> {
		public boolean test(A a, B b, C c);
	}
	public interface TriFunction<A, B, C, R> {
		public R apply(A a, B b, C c);
	}
	public interface QuadFunction<A, B, C, D, R> {
		public R apply(A a, B b, C c, D d);
	}
	public interface VarArgsFunction<T> {
		public T apply(Object... obj);
	}
	static Object clone(Object o) {
		if (o instanceof int[][]) {
			int[][] oo = (int[][])o;
			if (oo.length == 0) return oo;
			int[][] copy = new int[oo.length][];
			for (int i = 0; i < oo.length; i++) {
				copy[i] = Arrays.copyOf(oo[i], oo[i].length);
			}
			return copy;
		} else if (o instanceof int[]) {
			int[] oo = (int[])o;
			int[] copy = new int[oo.length];
			System.arraycopy(oo, 0, copy, 0, oo.length);
			return copy;
		} else if (o instanceof char[][]) {
			char[][] oo = (char[][])o;
			if (oo.length == 0) return oo;
			char[][] copy = new char[oo.length][];
			for (int i = 0; i < oo.length; i++) {
				copy[i] = Arrays.copyOf(oo[i], oo[i].length);
			}
			return copy;
		} else if (o instanceof char[]) {
			char[] oo = (char[])o;
			char[] copy = new char[oo.length];
			System.arraycopy(oo, 0, copy, 0, oo.length);
			return copy;
		} else if (o instanceof Integer[]) {
			return ((Integer[])o).clone();
		} else if (o instanceof TreeNode) {
			return ((TreeNode) o).clone();
		}
		return o;
	}
}
