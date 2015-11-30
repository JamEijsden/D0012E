package laboration2;

public class Node {
	 
	Node succ, pred;
	int value, key, length;
	
	 public Node(int key, int length){
		 this.succ = null;
		 this.pred = null;
		 this.key = key;
		 this.value = 0;
		 this.length = length;
	}

	public boolean isEmpty(int p){
		if (this.value == 0) {
			return true;
		}
		else{
			return false;
		}	
	}
	
	public int getValue(){
		return this.value;
			}
	
	public void setSucc(Node s){
		this.succ =  s;
	}
	
	public void setPred(Node p){
		this.pred = p;
	}
	

	public Node getSucc(){
		return this.succ;
	}
	
	public Node getPred(){
		return this.pred;
	}
	
	public void setValue(int v){
		this.value = v;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public boolean compareKeys(int k){
		if (this.key == k){
			return true;
		}
		else{
			return false;
		}
	}

}
