package laborations;

import java.util.Arrays;

public class Laboration1 {
	int[] seq;
	public Laboration1(int[] seq){
		this.seq = seq;
	}
	
	public static void main(String[] args) {
		int[] s = {1,24,7,2,8,6,2,1,7,80,6,3,2,6}; 
		Laboration1 lab = new Laboration1(s); 
		lab.bInsertionSort();
	}
	
	
	public void mMergeSort1(){
		//Hej hej tomte
	}
	
	public void mMergeSort2(){
		//Hej hej tomte
	}

	int BinarySearch (int a[], int low, int high, int key)
	{
	    int mid;

	    if (low == high)
	        return low;

	    mid = low + ((high - low) / 2);

	    if (key > a[mid])
	        return BinarySearch (a, mid + 1, high, key);
	    else if (key < a[mid])
	        return BinarySearch (a, low, mid, key);

	    return mid;
	}
	
	void bInsertionSort (){
	    int ins, i, j;
	    int tmp;

	    for (i = 1; i < this.seq.length; i++) {
	        ins = BinarySearch (this.seq, 0, i, this.seq[i]);
	        tmp = this.seq[i];
	        for (j = i - 1; j >= ins; j--)
	            this.seq[j + 1] = this.seq[j];
	        this.seq[ins] = tmp;
	    }
	    System.out.println(Arrays.toString(this.seq));
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
			System.out.println("Sorting: " + Arrays.toString(s));
		}
		
				
	}
}
