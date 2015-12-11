package laboration3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Test {
	public char charAt(int index, int index2, int index3) {

	}

	public static void main(String[] args) {
		// TODO code application logic here
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
				System.out.print(" to ");
				System.out.print(indexList.get(1));
				System.out.print(" distance: ");
				System.out.println(indexList.get(2));
			}
			in.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/*
	 * Graph g = new Graph(); g = g.initGraph(g); Djikstra d = new Djikstra(g,
	 * "C", "E"); d.update(); }
	 */
}