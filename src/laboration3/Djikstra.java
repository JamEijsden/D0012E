package laboration3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import laboration3.DAryHeap.Node;
import laboration3.Graph.Edge;
import laboration3.Graph.Vertex;

public class Djikstra {

	HashMap<String, Integer> dist;
	LinkedHashMap<String, Integer> perm;
	HashMap<String, String> parent;
	// HEAP IMPL
	ArrayList<Node> visited = new ArrayList<Node>();
	ArrayList<Node> S = new ArrayList<Node>();
	DAryHeap Q;
	Graph g;
	String destination;

	public Djikstra(Graph g, String source, String dest) {
		this.g = g;
		destination = dest;
		dist = new HashMap<String, Integer>();
		perm = new LinkedHashMap<String, Integer>();
		parent = new HashMap<String, String>();
		Q = new DAryHeap(g.graph.size(), 3);
		// initialize(source);
		initHeap(source);
	}

	@SuppressWarnings("unchecked")
	public void initialize(String source) {
		for (String vertex : this.g.Vertices()) {

			this.dist.put(vertex, 10000);
		}
		this.dist.put(source, 0); // Distance from source to source
		this.perm.put(source, 0);

	}

	public void initHeap(String source) {
		/*
		 * for (String vertex : this.g.Vertices()) { if (vertex != source) {
		 * Node n = Q.new Node(vertex); Q.insert(n); } }
		 */
		Node s = Q.new Node(source);
		s.dist = 0;
		Q.insert(s);
		visited.add(s);
	}

	public boolean contains(Node n, ArrayList<Node> array) {
		if (array.isEmpty())
			return false;
		for (Node m : array)
			if (m.id == n.id)
				return true;
		return false;
	}

	public ArrayList<Node> DijkstraHeap() {
		Node u;
		while (!visited.isEmpty()) {
			System.out.println();
			u = Q.extractMin();
			S.add(u);
			if (visited.contains(u))
				visited.remove(u);
			visited.trimToSize();
			for (Edge x : g.graph.get(u.id).adjecent) {

				Node tmp = Q.new Node(x.getDest());
				tmp.dist = x.getWeigth();
				
				System.out.println(tmp.dist);
				if (!contains(tmp, S)) {
				
					Node new_node = Q.new Node(tmp.id);
					int new_dist = tmp.dist + u.dist;
					new_node.dist = new_dist;
					new_node.parent = u;
					if (!contains(new_node, visited)) {
						Q.insert(new_node);
						visited.add(new_node);
					} else {
						for (int j = 0; j < visited.size(); j++){
							System.out.println(visited.get(j).id +" =="+ tmp.id);
							System.out.println(visited.get(j).dist +">"+ new_node.dist);
							if (visited.get(j).id == tmp.id && tmp.dist > new_node.dist) {
								Q.decreaseKey(new_node, new_node.dist);
								System.out.println("HEJHEJ");
								visited.add(j, new_node);
							}
						}
					}
				}
			}

		}
		return S;
	}
}
