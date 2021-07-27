package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/employee-importance/">LC: 690</a>
 */
public class EmployeeImportance {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 30, Arrays.asList(new Employee[] {
				new Employee(1, 15, Arrays.asList(new Integer[] {2})),
				new Employee(2, 10, Arrays.asList(new Integer[] {3})),
				new Employee(3, 5, Arrays.asList()),
				}), 1};
		Object[][] tcs = new Object[][] { tc1 };
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(List<Employee> t, Integer u) {
			Map<Integer, Employee>  map = t.stream().collect(Collectors.toMap(employee -> employee.id, Function.identity()));
			return visit(map, u);
		}
		int visit(Map<Integer, Employee> map, int id) {
			Employee employee = map.get(id);
			int importance = employee.importance;
			for (Integer subordinates : employee.subordinates) {
				importance += visit(map, subordinates);
			}
			return importance;
		}
		
	}
	interface Problem extends ToIntBiFunction<List<Employee>, Integer> {
		
	}
	static class Employee {
		public int id;
	    public int importance;
	    public List<Integer> subordinates;
	    public Employee(int id, int importance, List<Integer> subordinates) {
	    	this.id = id;
	    	this.importance = importance;
	    	this.subordinates = subordinates;
	    }
	}
}
