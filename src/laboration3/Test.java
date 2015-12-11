package laboration3;

import java.rmi.dgc.DGC;

public class Test {

	public static void main(String[] args) {
		Graph g = new Graph();
		g = g.initGraph(g);
		Djikstra d = new Djikstra(g, "C", "E");
		//d.update();
	}

}