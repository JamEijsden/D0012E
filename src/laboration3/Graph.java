
package laboration3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph {
	// int size;
	HashMap<String, Vertex> graph;

	public Graph() {
		// this.size = size;
		this.graph = new HashMap<String, Vertex>();
	}

	public class Vertex {
		String id;
		ArrayList<Edge> adjecent;

		public Vertex(String id, Edge edge) {
			adjecent = new ArrayList<Edge>();
			this.id = id;
			adjecent.add(edge);
		}

	}

	public class Edge {
		private String dest;
		private int weigth;

		public Edge(String dest, int weight) {
			this.dest = dest;
			this.weigth = weight;
		}

		public void setWeight(int i) {
			this.weigth = i;
		}

		public int getWeigth() {
			return this.weigth;
		}

		public String getDest() {
			return this.dest;
		}

	}

	public int V() {
		return graph.size();
	}

	public void addEdge(String id, String dest, int weight) {

		Edge e = new Edge(dest, weight);
		Edge dE = new Edge(id, weight);
		if (!this.graph.containsKey(id)) {
			Vertex v = new Vertex(id, e);
			this.graph.put(id, v);
		} else
			this.graph.get(id).adjecent.add(e);

		if (!this.graph.containsKey(dest)) {
			Vertex v = new Vertex(dest, dE);
			this.graph.put(dest, v);
		} else
			this.graph.get(dest).adjecent.add(dE);
	}

	public Set<String> Vertices() {
		return this.graph.keySet();
	}

	public String[] adjacentTo(String id) {
		String[] neighbours = new String[this.graph.get(id).adjecent.size()];
		for (int i = 0; i < neighbours.length; i++) {
			neighbours[i] = (this.graph.get(id).adjecent).get(i).getDest();
		}
		return neighbours;
	}

	public Graph initGraph(Graph G) {

		/*
		 * G.addEdge("A", "B", 38); G.addEdge("A", "C", 2); G.addEdge("C", "D",
		 * 7); G.addEdge("D", "E", 8); G.addEdge("B", "E", 3); G.addEdge("C",
		 * "B", 12); G.addEdge("D", "G", 1); G.addEdge("E", "G", 5);
		 */// G.addVertex("H");
		// G.generateGraph(5000, 8000);
		// G.createCVSfile();
		// print out graph
		G.readCSV();
		G.printGraph();
		System.out.println("Graph");

		// print out graph again by iterating over vertices and edges

		return G;
	}

	public void printGraph() {
		for (String v : this.Vertices()) {
			System.out.print(v + ": ");
			for (String w : this.adjacentTo(v)) {
				System.out.print(w + " ");
			}
			System.out.println();
		}
	}

	@SuppressWarnings("resource")
	public void createCVSfile() {
		String line = "";
		PrintWriter writer;
		try {
			writer = new PrintWriter("generatedGraph.txt", "UTF-8");
			for (int i = 0; i < graph.size() - 1; i++) {
				for (Edge e : graph.get("" + i).adjecent) {
					line += i + "," + e.getDest() + "," + e.getWeigth();
					writer.println(line);
					line = "";
				}
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void generateGraph(int vertices, int edges) {
		if (vertices > edges)
			throw new GraphException("Less Edges than Vertices");
		if (!(edges < vertices * vertices))
			throw new GraphException("Too many edges, supposed to be < Vertices^2");

		for (int i = 0; i < vertices; i++) {
			int dist = (int) ((Math.random() * 30) + 1);
			this.addEdge("" + i, ("" + (i + 1)), dist);

		}
		int dist = (int) ((Math.random() * 30) + 1);
		this.addEdge("0", "" + (graph.size() - 1), dist);

		int dest, start, cost;
		for (int j = edges - (vertices + 1); j > 0; j--) {
			dest = (int) (Math.random() * vertices + 1);
			start = (int) (Math.random() * vertices + 1);
			while (hasEdge("" + start, "" + dest) || start == dest) {
				dest = (int) (Math.random() * vertices + 1);
				start = (int) (Math.random() * vertices + 1);
			}
			cost = (int) ((Math.random() * 30) + 1);
			this.addEdge("" + start, "" + dest, cost);
		}
	}

	private boolean hasEdge(String start, String dest) {
		for (Edge e : graph.get(start).adjecent) {
			if (e.getDest().equals(dest)) {
				// System.out.println(start + " has edge to " + e.getDest() +
				// "("+dest+")");
				return true;
			}
		}
		return false;

	}

	@SuppressWarnings("serial")
	public class GraphException extends RuntimeException {
		public GraphException(String message) {
			super(message);
		}
	}

	public void readCSV() {
		try {
			FileInputStream fstream = new FileInputStream("generatedGraph.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;

			while ((str = br.readLine()) != null) {
				// char character = list.charAt();

				String list1 = str;
				List<String> indexList = Arrays.asList(list1.split("\\s*,\\s*"));
				System.out.print(indexList.get(0));
				this.addEdge(indexList.get(0), indexList.get(1), Integer.parseInt(indexList.get(2)));
			}
			in.close();
		} catch (

		Exception e)

		{
			System.err.println(e);
		}

	}
}
