package se.kth.id1020;

import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;
import java.util.List;
import java.util.ArrayList;

public class grapher {
    static List<List<Vertex>> resultlist = new ArrayList<List<Vertex>>();
    static boolean [] marked;
    
    static List<Vertex> vlist = new ArrayList<Vertex>();
    public static List<Vertex> connected(Graph g, int i){
        vlist = new ArrayList<Vertex>();
        connected(g, g.vertex(i));
        return vlist;
    }
    private static void connected(Graph g, Vertex v){
        vlist.add(v);
        for(Edge e : g.adj(v.id))if(!vlist.contains(g.vertex(e.to)))connected(g, g.vertex(e.to));
    }
    
    static int subgraphnumber(Graph g) {
        int n = 0;
        int i = 0;
        while(n<g.numberOfVertices()){
            List<Vertex> vl = connected(g, i);
            n = n + vl.size();
            resultlist.add(vl);
            for(Vertex v : vl)marked[v.id] = true;
            for(int j = 0; j < marked.length; j++) {
            	boolean boo = marked[j];
            	if(boo==false) {
            		i = j;
            		break;
            	}
            }
        }
        return resultlist.size();
    }
    static BFS b = new BFS();
    
    public static void main(String[] args) 
    {
    	Graph g = DataSource.load();
        marked = new boolean[g.numberOfVertices()];
        int sz = subgraphnumber(g);
        System.out.println("There are " + sz + " number of subgraphs in the graph");
        int[] distanceunweighted = new int[g.numberOfVertices()];
        int[] distanceweighted = new int[g.numberOfVertices()];
        for(Vertex v : g.vertices()) {
        	if(v.label.equals("Renyn")) {
        		distanceunweighted = b.bfsnw(g,v);
        		distanceweighted = b.bfsw(g, v);
        	}
        }
        for(Vertex v : g.vertices())if(v.label.equals("Parses")) {
        	System.out.println("The unweighted distance between Renyn and Parses is " + distanceunweighted[v.id] + ".");
        	System.out.println("The weighted distance between Renyn and Parses is " + distanceweighted[v.id] + ".");
        }
    }
    
}


