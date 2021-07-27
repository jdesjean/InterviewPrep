package org.ip.array;

import java.util.ArrayList;
import java.util.List;

public class NestedInteger {
	 
    private Integer value;
    private List<NestedInteger> ni = new ArrayList<>();
    public NestedInteger() {
    	
    }

	// Constructor initializes a single integer.
    public NestedInteger(int value) {
    	this.value = value;
    }
    
    public NestedInteger(NestedInteger... integers) {
    	for (NestedInteger integer : integers) {
    		add(integer);
    	}
    }
 
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger() {
    	return value != null;
    }
 
    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger() {
    	return value;
    }
 
    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value) {
    	this.value = value;
    }
 
    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni) {
    	this.ni.add(ni);
    }
 
    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList() {
    	return ni;
    }
}
