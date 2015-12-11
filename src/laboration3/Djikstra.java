package laboration3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import laboration3.DAryHeap.Node;
import laboration3.Graph.Edge;

public class Djikstra {

	HashMap<String, Integer> dist;
	LinkedHashMap<String, Integer> perm;
	HashMap<String, String> parent;
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

			this.dist.put(vertex, -1);
		}
		this.dist.put(source, 0); // Distance from source to source
		this.perm.put(source, 0);

	}

	public void initHeap(String source) {
		for (String vertex : this.g.Vertices()) {
			if (vertex != source) {
				Node n = Q.new Node(vertex);
				Q.insert(n);
			}
		}
		Node s = Q.new Node(source);
		s.dist = 0;
		Q.insert(s);
	}

	public void DijkstraHeap(){
		while(!Q.isEmpty()){
			Node min = Q.extractMin();
			min.unvisited = false;
		}
	}

	public void Dijkstra() {
		HashMap<String, ArrayList<Edge>> graph = this.g.graph;
		String[] stringArray = this.perm.keySet().toArray(new String[this.perm.keySet().size()]);
		while (!((stringArray[stringArray.length - 1] == this.destination))) {
			Edge candidate = null;
			String v = "";
			String parent = "";
			int min = -1;
			for (int i = (this.perm.size() - 1); i >= 0; i--) {

				v = stringArray[i];
				System.out.println("Perm: " + v);
				/* SET TEMPDIST TO NEIGHBOURS */
				for (Edge e : graph.get(v)) {
					if (!(this.perm.containsKey(e.getDest())) && (this.parent.get(v) != e.getDest())) {
						int tempNodeDist = this.perm.get(v) + e.getWeigth();
						// System.out.println(this.dist);
						if (this.dist.get(e.getDest()) > tempNodeDist || this.dist.get(e.getDest()) == -1) {
							this.dist.put(e.getDest(), tempNodeDist);
						}
						// tempNodeDist = this.dist.get(e.getDest());
						// System.out.println("Checking Edge: " + e.getDest() +
						// " - " + tempNodeDist);
						if (candidate != null) {
							// System.out.println("In if: " + min + " =? " +
							// tempNodeDist + " = " + this.perm.get(v)+ " + " +
							// e.getWeigth());
						}

						if (candidate == null || tempNodeDist < min || min == -1) {
							// System.out.println("cand: " + e.getDest() + "
							// parent: " + v);
							candidate = e;
							min = tempNodeDist;
							// candidate.setWeight(tempNodeDist);
							// candidate.setWeight(tempNodeDist);
							// System.out.println("Cand: " + tempNodeDist);
							parent = v;
						}

					}
				}

			}
			this.parent.put(candidate.getDest(), parent);
			this.perm.put(candidate.getDest(), this.dist.get(candidate.getDest())); // CHANGE
			// TO
			// DARYHEAP
			// STORAGE
			// System.out.println(this.parent + "\n " + this.perm);
			// System.out.println(this.dist);
			if (!graph.get(v).isEmpty()) {
				graph.get(v).remove(candidate);
			} else
				graph.remove(v);
			// System.out.println("Node: " + v + " Edges: " +
			// graph.get(v).size());
			System.out.println("Added: " + candidate.getDest() + "\n");
			stringArray = this.perm.keySet().toArray(new String[this.perm.keySet().size()]);
		}
		String chain = "";
		String stop = stringArray[stringArray.length - 1];
		while (this.parent.get(stop) != null) {
			chain += stop + " <- ";
			stop = this.parent.get(stop);
		}
		chain += stop;
		System.out.println(chain);
	}
	
}
