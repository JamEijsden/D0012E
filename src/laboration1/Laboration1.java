package laboration1;

import java.util.Arrays;

public class Laboration1 {
	int[] seq;
	public Laboration1(int[] seq){
		this.seq = seq;
	}
	
	public static void main(String[] args) {
		int[] s = {1,24,7,2,8,6,2,1,7,80,6,3,2,6}; 
		Laboration1 lab = new Laboration1(s); 
		lab.InsertionSort();
	}
	
	
	public void mMergeSort(){
		//Hej hej tomte
	}
	
	public void bInsertionSort(){
		
	}
	
	public void InsertionSort(){		
		int[] s = this.seq; 
		System.out.println("Sorting: " + Arrays.toString(s));
		for(int i = 1; i < s.length; i++){
			int key = s[i];
			for(int j = i-1; j >= 0; j--){
				if(key < s[j]){
					//System.out.println(key + " < " + s[j]);
					s[j+1] = s[j];
				}else {
					//System.out.println(key + " >= " + s[j]);
					s[j+1] = key; 
					break;
				}
			}		
		}
		System.out.println("Sorting: " + Arrays.toString(s));
				
	}
}
