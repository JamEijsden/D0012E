package laboration2;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Laboration2 {
	private int[] hashArray;
	int ld = 0, lu = 0;

	public int linearProbing(int hx, int[] hashArray) {
		int probe = hx % hashArray.length;
		int probesDone = 0;
		while (true) {
			if (hashArray[probe] == 0) {
				System.out.println("Number of probes: " + probesDone);
				if (ld <= lu) ld++; else lu++;
				return probe;
			}
			
			if (ld <= lu) probe++; else probe--;
			if(probe == hashArray.length) probe = 0;
	
			
			probesDone++;
			
		}
	}

	public static void main(String[] args) {
		Laboration2 lab = new Laboration2();
		lab.createStorage(10);
		lab.insert();

	}

	public void insert() { // the n will be hashed before inserting, later.
		int[] listElement = { 11, 65, 33, 92, 21, 21, 21, 87, 39, 19 };
		for (int i = 0; i < 10; i++) {
			int hashedValue = hashFunction(listElement[i]);			
			int index = this.linearProbingMod1(hashedValue, hashArray);
			hashArray[index] = hashedValue;
			System.out.println(Arrays.toString(hashArray));
		}
	}
	public int hashFunction(int key) {
		return (key*(key+3) % (hashArray.length));
		
	}

	public int[] createStorage(int k) {
		hashArray = new int[k];
		return hashArray;
	}
	
	public Node LinkedList(int n){
		Node currentNode  = new Node(0, n);
		for(int i = 1; i != n;i++){
			Node newNode  = new Node(i, n);
			currentNode.setSucc(newNode);
			currentNode = currentNode.getSucc();
		}
		return currentNode;
	}
}


