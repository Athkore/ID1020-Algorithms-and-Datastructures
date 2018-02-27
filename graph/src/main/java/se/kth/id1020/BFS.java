package se.kth.id1020;

import java.util.Stack;

public class BFS {
	
	public int[] bfsnw(Graph G, Vertex s) {
		int[] distance = new int[G.numberOfVertices()];
		Vertex[] p = new Vertex[G.numberOfVertices()];
		boolean[] marked = new boolean[G.numberOfVertices()];
		for(int i = 0; i < distance.length; i++)distance[i] = -1;
		Stack<Vertex> stack = new Stack<Vertex>();
		stack.push(s);
		distance[s.id] = 0;
		marked[s.id] = true;
		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			for (Edge w : G.adj(v.id)) {
				if (!marked[w.to]||distance[w.from]<distance[w.to]-1) {
					p[w.to] = v;
					distance[w.to] = distance[v.id]+1;
					//System.out.println(G.vertex(w.to).label);
					marked[w.to] = true;
					stack.push(G.vertex(w.to));
				}
			}
		}
		return distance;
	}	
	public int[] bfsw(Graph G, Vertex s) {
		int[] distance = new int[G.numberOfVertices()];
		double[] weight = new double[G.numberOfVertices()];
		Vertex[] p = new Vertex[G.numberOfVertices()];
		boolean[] marked = new boolean[G.numberOfVertices()];
		for(int i = 0; i < distance.length; i++)distance[i] = -1;
		Stack<Vertex> stack = new Stack<Vertex>();
		stack.push(s);
		distance[s.id] = 0;
		marked[s.id] = true;
		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			for (Edge w : G.adj(v.id)) {
				if (!marked[w.to]||weight[w.to]>weight[w.from]+w.weight) {
					p[w.to] = v;
					weight[w.to] = weight[w.from]+w.weight;
					distance[w.to] = distance[v.id]+1;
					//System.out.println(G.vertex(w.to).label);
					marked[w.to] = true;
					stack.push(G.vertex(w.to));
				}
			}
		}
		return distance;
	}	
}
