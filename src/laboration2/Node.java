package laboration2;

public class Node {
	 
	Node succ, pred;
	int value, key;
	
	 public Node(int key, Node s, Node p){
		 this.succ = s;
		 this.pred = p;
		 this.key = key;
	}

}
