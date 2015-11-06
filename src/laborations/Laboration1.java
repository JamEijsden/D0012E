package laborations;

import java.util.Arrays;

public class Laboration1 {

	public Laboration1(){
		
	}
	public static void main(String[] args) {
		Laboration1 lab = new Laboration1();
		int[] s = lab.generateRandomList(10);
		//lab.mMergeSort1(s, 0, s.length);
		System.out.println(Arrays.toString(lab.InsertionSort(s)));
	}
	
	
	public void mMergeSort1(int[] array, int start, int end){
		int mid = (start+end)/2;
		mMergeSort1(array, mid+1, end);
		mMergeSort1(array, start, mid);
		
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
	
	int[] bInsertionSort(int[] list){
	    int ins, i, j;
	    int tmp;

	    for (i = 1; i < list.length; i++) {
	        ins = BinarySearch (list, 0, i, list[i]);
	        tmp = list[i];
	        for (j = i - 1; j >= ins; j--)
	            list[j + 1] = list[j];
	        list[ins] = tmp;
	    }
	   // System.out.println(Arrays.toString(this.seq));
	    return list;
	}
	
	
	
	public int[] InsertionSort(int[] list){		
		int[] s = list; 
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
				System.out.println(Arrays.toString(s));
			}		


		}
		return list;			
	}
	
	public int[] generateRandomList(int k){
		int[] list = new int[k];
	    for (int i=0; i<k; i++){
	        int n = (int)(Math.random()*100);
	        list[i] = n;
	    }
        //System.out.println(Arrays.toString(list));
	    return list; 
	}
}
