package laboration3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import laboration3.DAryHeap.Node;
import laboration3.Graph.Edge;

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
		Node s = Q.new Node(source);
		s.dist = 0;
		Q.insert(s);
		visited.add(s);
	}

	public boolean contains(Node n, ArrayList<Node> array) {
		if (array.isEmpty())
			return false;
		for (Node m : array) {
			if (m.id.equals(n.id)) {
				return true;
			}
			
		}
		return false;
	}

	public ArrayList<Node> DijkstraHeap() {
		Node u;
		while (!Q.isEmpty()) {
			System.out.println();
			u = Q.extractMin();
			S.add(u);
			if (visited.contains(u))
				visited.remove(u);
			visited.trimToSize();
			for (Edge x : g.graph.get(u.id).adjecent) {

				Node tmp = Q.new Node(x.getDest());
				tmp.dist = x.getWeigth();
				// System.out.println("tmp distance: " + tmp.dist);
				if (!contains(tmp, S)) {

					Node new_node = Q.new Node(tmp.id);
					int new_dist = tmp.dist + u.dist;
					new_node.dist = new_dist;
					new_node.parent = u;
					if (!contains(new_node, visited)) {
						Q.insert(new_node);
						visited.add(new_node);
					} else {
						for (int j = 0; j < visited.size(); j++) {
							if (visited.get(j).id == tmp.id && visited.get(j).dist > new_node.dist) {
								new_node.position = visited.get(j).position;
								Q.decreaseKey(visited.get(j), new_node.dist);
								visited.set(j, new_node);
							}
						}
					}
				}
			}
		}
		return S;
	}
}
