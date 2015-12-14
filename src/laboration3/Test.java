package laboration3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import laboration3.DAryHeap.Node;

public class Test {
	public static void main(String[] args) {
		Graph g = new Graph();
		g = g.initGraph(g);
		Djikstra d = new Djikstra(g, "0", "E");
		DAryHeap h = new DAryHeap(g.graph.size(), 3);
		/*for (String vertex : g.Vertices()) {
			if (vertex != "C") {
				Node n = h.new Node(vertex);
				h.insert(n);
			} else {
				Node s = h.new Node("C");
				s.dist=7;
				h.insert(s);
			}
		}
		for(Node n : h.data){
			System.out.println(n.id + " - " + n.dist);
		}
		System.out.println();
		Node s = h.new Node("C");
		s.dist = 7;
		s.position = (4);
		h.decreaseKey(s, -4);
		for(Node n : h.data){
			System.out.println(n.id + " - " + n.dist);
		}*/
		for(DAryHeap.Node n : d.DijkstraHeap()){
			System.out.print(n.id + " -- " + n.dist + " -- ");
			if(n.parent != null) System.out.print(n.parent.id);
			System.out.println();
		}/*
		try {
			FileInputStream fstream = new FileInputStream("C:/Users/moxxan/Desktop/test.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String str;

			while ((str = br.readLine()) != null) {
				// char character = list.charAt();

				String list1 = str;
				List<String> indexList = Arrays.asList(list1.split("\\s*,\\s*"));
				System.out.print(indexList.get(0));
				g.addEdge(indexList.get(0), indexList.get(1), Integer.parseInt(indexList.get(2)));
				System.out.print(indexList.get(0));
				System.out.print(" to ");
				System.out.print(indexList.get(1));
				System.out.print(" distance: ");
				System.out.println(indexList.get(2));
			}
			in.close();
		} catch (Exception e) {
			System.err.println(e);
		}*/
	}

}
