package org.ip.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/design-add-and-search-words-data-structure/">LC: 211</a>
 */
public class WordDictionary {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new boolean[] {false, true, true, true}, new String[] {"bad", "dad", "mad"}, new String[] {"pad", "bad", ".ad", "b.."}};
		Object[] tc2 = new Object[] {new boolean[] {false}, new String[] {"dady"}, new String[] {"dad"}};
		List<Object[]> tcs = Arrays.asList(tc1, tc2);
		for (Object[] tc : tcs) {
			WordDictionary[] solvers = new WordDictionary[] {new WordDictionary()}; 
			Utils.print((boolean[]) tc[0]);
			for (WordDictionary solver : solvers) {
				System.out.print(" : ");
				String[] words = (String[])tc[1];
				for (String word : words) {
					solver.addWord(word);
				}
				words = (String[]) tc[2];
				for (String word : words) {
					System.out.print(", " + solver.search(word));
				}
			}
			System.out.println();
		}
	}
	
	/** Initialize your data structure here. */
	Trie trie;
    public WordDictionary() {
        trie = new Trie();
    }
    
    public void addWord(String word) {
        trie.addWord(word);
    }
    
    public boolean search(String word) {
        return trie.search(word);
    }
    static class Trie {
    	private final Node root = new Node();
    	public void addWord(String word) {
    		Node current = root;
    		for (int i = 0; i < word.length(); i++) {
    			current = current.set(word.charAt(i));
    		}
    		current.end = true;
    	}
    	public boolean search(String word) {
    		return search(root, word, 0);
    	}
    	public boolean search(Node node, String word, int index) {
    		if (index == word.length()) {
    			return node.end == true;
    		}
    		if (word.charAt(index) == '.') {
    			for (Map.Entry<Character, Node> entry : node.childs.entrySet()) {
    				if (search(entry.getValue(), word, index + 1)) {
    					return true;
    				}
    			}
    			return false;
    		} else {
    			Node next = node.get(word.charAt(index));
    			return next != null && search(next, word, index + 1);
    		}
    	}
    }
    static class Node {
    	private Map<Character, Node> childs = new HashMap<>();
    	public boolean end = false;
    	public Node set(char character) {
    		return childs.computeIfAbsent(character, k -> new Node());
    	}
    	public Node get(char character) {
    		return childs.get(character);
    	}
    }
}
