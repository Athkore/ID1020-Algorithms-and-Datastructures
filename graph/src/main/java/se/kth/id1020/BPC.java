package se.kth.id1020;

import java.util.List;
import java.util.ArrayList;
import se.kth.id1020.Graph;

public class BPC {
    List<Vertex> vlist = new ArrayList<Vertex>();
    boolean[] marked;
    public List<Vertex> connected(Graph g, int i){
        vlist = new ArrayList<Vertex>();
        connected(g, g.vertex(i));
        return vlist;
    }
    private void connected(Graph g, Vertex v){
        vlist.add(v);
        for(Edge e : g.adj(v.id))if(!vlist.contains(g.vertex(e.to)))connected(g, g.vertex(e.to));
    }
}
