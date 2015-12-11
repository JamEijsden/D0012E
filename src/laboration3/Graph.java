


 		

package laboration3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Graph {
	//int size;
	HashMap<String, ArrayList<Edge>> graph;
	public Graph(){
		//this.size = size;
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
		
		public void setWeight(int i){
			this.weigth = i;
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
	public void addEdge(String id, String dest, int weight){
		Edge e = new Edge(dest, weight);
		Edge dE = new Edge(id, weight);
		if(!this.graph.containsKey(id)) {
			ArrayList<Edge> newConn = new ArrayList<Edge>();
			newConn.add(e);
			this.graph.put(id, newConn);
		} else
			this.graph.get(id).add(e);
		
		if(!this.graph.containsKey(dest)){
			ArrayList<Edge> newConn = new ArrayList<Edge>();
			newConn.add(dE);
			this.graph.put(dest, newConn);
		}else
			this.graph.get(dest).add(dE);		
	}
	
	public Set<String> Vertices (){
		return this.graph.keySet();
	}
	
	public String[] adjacentTo(String id){
		String[] neighbours = new String[this.graph.get(id).size()];
		for(int i = 0; i < this.graph.get(id).size(); i++){
			neighbours[i] = (this.graph.get(id)).get(i).getDest();	
		}
		return neighbours;
	}
	
	public Graph initGraph(Graph G) {
		
        G.addEdge("A", "B", 38);
        G.addEdge("A", "C", 2);
        G.addEdge("C", "D", 7);
        G.addEdge("D", "E", 8);
        G.addEdge("B", "E", 3);
        G.addEdge("C", "B", 12);
        G.addEdge("D", "G", 1);
        G.addEdge("E", "G", 5);
        //G.addVertex("H");

        // print out graph
        System.out.println("Graph");

        // print out graph again by iterating over vertices and edges
        for (String v : G.Vertices()) {
        	System.out.print(v + ": ");
            for (String w : G.adjacentTo(v)) {
            	System.out.print(w + " ");
            }
            System.out.println();
        }
        return G;
    }
}
