package org.ip.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

import org.ip.Test;
import org.ip.Test.QuadFunction;

/**
 * <a href="https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/">LC: 2071</a>
 */
public class MaximumNumberTasksAssigned {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new int[] {3,2,1}, new int[] {0,3,3}, 1, 1};
		Object[] tc2 = new Object[] {1, new int[] {5,4}, new int[] {0,0,0}, 1, 5};
		Object[] tc3 = new Object[] {3, new int[] {5,9,8,5,9}, new int[] {1,6,4,2,6}, 1, 5};
		Object[] tc4 = new Object[] {2, new int[] {5,6}, new int[] {4,5}, 1, 2};
		Object[] tc5 = new Object[] {2, new int[] {25,30,40}, new int[] {11,21,31}, 1, 10};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver4(), new Solver5()};
		Test.apply(solvers, tcs);
	}
	static class Solver5 implements Problem {
		@Override
		public Integer apply(int[] tasks, int[] workers, Integer pills, Integer strength) {
			if (tasks.length == 0 || workers.length == 0) return 0;
	        Arrays.sort(tasks);
	        Arrays.sort(workers);
	        if (tasks[0] > workers[workers.length - 1] + (pills > 0 ? strength : 0)) {
	        	// No tasks can be done, avoid handling out of bounds in binary search i.e. l = -1
	        	return 0; 
	        }
	        int l = 0, r = Math.min(tasks.length, workers.length) - 1;
	        while (l < r) {
	            int m = l + (r - l + 1) / 2;
	            if (possible(tasks, workers, pills, strength, m)) {
	                l = m;
	            } else {
	                r = m - 1;
	            }
	        }

	        return l + 1; //# of tasks = tasks_idx + 1
		}
		boolean possible(int[] tasks, int[] workers, int pills, int strength, int m) {
			Deque<Integer> deque = new ArrayDeque<>(workers.length);
            for (int i = m, j = workers.length - 1; i >= 0; i--) {
            	for (; j >= 0 && tasks[i] <= workers[j] + strength; j--) {
            		deque.addFirst(workers[j]);
            	}
            	if (deque.isEmpty()) {
            		return false;
            	} else if (tasks[i] <= deque.peekLast()) {
                	// Assign to a worker without the pill
                	deque.removeLast();
                } else if (pills == 0) {
                	return false;
                } else {
                	// Assign to weakest worker with the pill
                	pills--;
                	deque.removeFirst();
                }
            }
            return true;
		}
	}
	static class Solver4 implements Problem {
		@Override
		public Integer apply(int[] tasks, int[] workers, Integer pills, Integer strength) {
			if (tasks.length == 0 || workers.length == 0) return 0;
	        Arrays.sort(tasks);
	        Arrays.sort(workers);
	        if (tasks[0] > workers[workers.length - 1] + (pills > 0 ? strength : 0)) return 0;

	        int l = 0, r = Math.min(tasks.length, workers.length) - 1;
	        TreeMap<Integer, Integer> freqMapOriginal = new TreeMap<>();
            for (int w : workers) {
            	freqMapOriginal.compute(w, (key, value) -> value == null ? 1 : value + 1);
            }
	        while (l < r) {
	            int m = l + (r - l + 1) / 2;
	            
	            TreeMap<Integer, Integer> freqMap = (TreeMap<Integer, Integer>) freqMapOriginal.clone();
	            if (possible(tasks, freqMap, pills, strength, m)) {
	                l = m;
	            } else {
	                r = m - 1;
	            }
	        }

	        return l + 1;
		}
		boolean possible(int[] tasks, TreeMap<Integer, Integer> freqMap, int pills, int strength, int m) {
            for (int i = m; i >= 0; i--) {
                if (tasks[i] <= freqMap.lastKey()) {
                	// Assign to a worker without the pill
                    freqMap.compute(freqMap.lastKey(), (key, value) -> value == 1 ? null : value - 1);
                } else {
                    // Assign to weakest worker with the pill
                    Map.Entry<Integer, Integer> weakest = freqMap.ceilingEntry(tasks[i] - strength);
                    if (weakest != null && --pills >= 0) {
                    	remove(freqMap, weakest);
                    } else {
                        return false;
                    }
                }
            }
            return true;
		}
		void remove(TreeMap<Integer, Integer> map, Map.Entry<Integer, Integer> entry) {
			if (entry.getValue() == 1) {
				map.remove(entry.getKey());
			} else {
				map.put(entry.getKey(), entry.getValue() - 1);
			}
		}
	}
	
	static class Solver2 implements Problem {
		@Override
		public Integer apply(int[] tasks, int[] workers, Integer pills, Integer strength) {
			Arrays.sort(tasks);
			Arrays.sort(workers);
			TreeMap<Integer, Integer> set = new TreeMap<>();
			for (int i = 0; i < workers.length; i++) {
				set.compute(workers[i], (k, v) -> v == null ? 1 : v + 1);
			}
			int res = 0;
			for (int t = tasks.length - 1; t >= 0 && !set.isEmpty(); t--) {
				Map.Entry<Integer, Integer> worker = set.lastEntry();
				if (tasks[t] <= worker.getKey()) {
					res++;
					remove(set, worker);
				} else if (pills > 0 && tasks[t] <= worker.getKey() + strength) {
					//find weakest worker that can take the task with the pill
					Map.Entry<Integer, Integer> weakest = set.ceilingEntry(tasks[t] - strength);
					if (weakest != null) {
						remove(set, weakest);
						res++;
						pills--;
					}
				}
			}
			return res;
		}
		void remove(TreeMap<Integer, Integer> map, Map.Entry<Integer, Integer> entry) {
			if (entry.getValue() == 1) {
				map.remove(entry.getKey());
			} else {
				map.put(entry.getKey(), entry.getValue() - 1);
			}
		}
	}
	static class Solver implements Problem {
		@Override
		public Integer apply(int[] tasks, int[] workers, Integer pills, Integer strength) {
			Arrays.sort(tasks);
			Arrays.sort(workers);
			int res = 0;
			for (int t = tasks.length - 1, w = workers.length - 1; t >= 0 && w >= 0; ) {
				if (workers[w] < 0) {
					w--;
					continue;
				} else if (tasks[t] <= workers[w]) {
					res++;
					w--;
				} else if (pills > 0 && tasks[t] <= workers[w] + strength) {
					//find weakest worker that can take the task with the pill
					int weakest = binarySearch(tasks[t], workers, 0, w, strength);
					if (weakest == w) {
						w--;
					} else {
						workers[weakest] *= -1;
					}
					res++;
					pills--;
				}
				t--;
			}
			return res;
		}
		int binarySearch(int task, int[] workers, int left, int right, int strength) {
			int maxRight = right;
			for (; left < right;) {
				int mid = left + (right - left) / 2;
				if (task <= Math.abs(workers[mid]) + strength) {
					right = mid;
				} else {
					left = mid + 1;
				}
			}
			while (workers[right] < 0 && right < maxRight) {
				right++;
			}
			return right;
		}
	}
	interface Problem extends QuadFunction<int[], int[], Integer, Integer, Integer> {
		
	}
}
