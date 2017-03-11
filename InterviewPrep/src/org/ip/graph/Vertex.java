package org.ip.graph;

public class Vertex<T> {
	T val;
	public Vertex(){}
	public Vertex(T val) {this.val=val;}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vertex)) return false;
		Vertex v = (Vertex)o;
		return v.val == val;
	}
	@Override
	public int hashCode() {
		return val.hashCode();
	}
	@Override
	public String toString() {
		return String.valueOf(val);
	}
}
