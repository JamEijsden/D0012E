package laboration3;


public class Test {

	public static void main(String[] args) {
		Graph g = new Graph();
		g = g.initGraph(g);
		/*
		 * Djikstra d = new Djikstra(g, "C", "E"); d.update();
		 */
		DAryHeap heap = new DAryHeap(g.graph.size(), 3);
		for(String v : g.Vertices()){
			heap.insert(v);
		}
		heap
		heap.printHeap();
	}

}