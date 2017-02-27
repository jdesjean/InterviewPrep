package org.ip.graph;

public class Vertex {
	int val;
	public Vertex(int val) {this.val=val;}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vertex)) return false;
		Vertex v = (Vertex)o;
		return v.val == val;
	}
	@Override
	public int hashCode() {
		return val;
	}
	@Override
	public String toString() {
		return String.valueOf(val);
	}
}
