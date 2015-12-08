package laboration3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Graph {
	int size;
	HashMap<String, ArrayList<Edge>> graph;
	public Graph(int size){
		this.size = size;
		this.graph = new HashMap<String,ArrayList<Edge>>();
	}
	
	/*public class Vertex{
		String id;
		ArrayList<Edge> connections;
		
		public Vertex(String id){
			this.id = id;
			this.connections = new ArrayList<Edge>();
		}
		
		public void addConnection(Edge e){
			this.connections.add(e);
		}
		
		public ArrayList<Edge> getAdjacent(){
			return this.connections;
		}
		
		public String getId(){
			return this.id;
		}
		
	}
	*/
	public class Edge{
		private String dest;
		private int weigth;
		public Edge(String dest, int weight){
			this.dest = dest;
			this.weigth = weight;
		}
		
		public int getWeigth(){
			return this.weigth;
		}
		
		public String getDest(){
			return this.dest;
		}
	}
	
	public int V(){
		return graph.size();
	}
	public void addVertex(String id, String dest, int weight){
		Edge e = new Edge(dest, weight);
		Edge dE = new Edge(id, weight);
		if(this.graph.get(id).isEmpty()) {
			ArrayList<Edge> newConn = new ArrayList<Edge>();
			newConn.add(e);
			this.graph.put(id, newConn);
		} else
			this.graph.get(id).add(e);
		if(this.graph.get(dest).isEmpty()){
			ArrayList<Edge> newConn = new ArrayList<Edge>();
			newConn.add(dE);
			this.graph.put(id, newConn);
		}else
			this.graph.get(dest).add(dE);
			
	}
	public Collection<ArrayList<Graph.Edge>> Vertices (){
		return this.graph.values();
	}
	
	public ArrayList<Edge> adjacentTo(String id){
		return this.graph.get(id);
	}
}
