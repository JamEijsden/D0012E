package questions;

import java.util.Arrays;

public class ImplementQuestions {
	public int[] seq;
	public ImplementQuestions(int[] sequence){
		 seq = sequence;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] seq = {23,5,1,7,3,7,54,9,3,6,4};
		ImplementQuestions iq = new ImplementQuestions(seq);
		iq.isInSeq(4);
		iq.SelectionSort();
	}
	
	public int isInSeq(int v){
		System.out.println("Finding " + v + " in sequence " + Arrays.toString(this.seq)); 
		for (int i = 0; i < this.seq.length; i++) {
			if(this.seq[i] == v){
				System.out.println("Found at index: "+(i+1)+"\n");
				return i+1;
			}
		}
		System.out.println("Null\n"); //NULL
		return -1;
	}

	public void SelectionSort() {
		System.out.println("Sorting sequence: " + Arrays.toString(this.seq));
		int lowest;
		int lowestIndex;
		for(int i = 0; i < this.seq.length; i++) {
			lowest = this.seq[i];
			lowestIndex = i;
			for(int j = i+1; j < this.seq.length; j++) {
				if(this.seq[j] < lowest){
					lowest = this.seq[j];
					lowestIndex = j;
				}
			}
			this.seq[lowestIndex] = this.seq[i];
			this.seq[i] = lowest;
			//System.out.println(Arrays.toString(this.seq));
		}
		System.out.println(Arrays.toString(this.seq));
	}
	
}
