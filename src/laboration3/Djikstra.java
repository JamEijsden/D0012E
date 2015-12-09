package laboration3;

import java.util.ArrayList;
import java.util.HashMap;

import laboration3.Graph.Edge;

public class Djikstra {
	
	HashMap<String,Integer> dist;
	HashMap<String,Integer> perm;
	HashMap<String,String> parent;
	Graph g;
	
	public Djikstra(Graph g, String source){
		this.g = g;
		this.dist = new HashMap<String, Integer>();
		this.perm = new HashMap<String, Integer>();
		this.parent = new HashMap<String, String>();
		initialize(source);
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(String source){
		for(String vertex : this.g.Vertices()){
			this.dist.put(vertex, -1);
			this.parent.put(vertex, null);
		}
		this.dist.put(source, 0); //Distance from source to source
		this.perm.put(source, 0);
		System.out.println(perm.get("B"));
	}
	
	public void update(){
		HashMap<String, ArrayList<Edge>> graph = this.g.graph;
		for(String v : this.perm.keySet()){
			int parentMin = this.perm.get(v);
			
			for(Edge e : graph.get(v)){
				this.dist.put(e.getDest(), parentMin+this.perm.get(v));
				if(parentMin + e.getWeigth() > this.dist.get(e.getDest()) ){
					
				}
			}
		}
	}
}
