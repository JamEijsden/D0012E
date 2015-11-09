package laborations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Laboration1 {
	private int[] array;

	public Laboration1() {

	}

	public static void main(String[] args) {
		Laboration1 lab = new Laboration1();
		lab.array = lab.generateRandomList(10);
		System.out.println("Start: "+Arrays.toString(lab.array));
		// lab.mMergeSort1(s, 0, s.length);
		lab.doMergeSort(4);
		
		
	}

	public void doMergeSort(int k) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		long startTime = System.currentTimeMillis();
		mMergeSort1(k, 0, this.array.length-1);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.print("Execution time is " + formatter.format((totalTime) / 1000d) + " seconds");
	}

	private void mMergeSort1(int k, int start, int end) {
		int mid = (start + end) / 2;
		//System.out.println(end - start + " >? " + k);
		if ((end - start) >= k) {
			//System.out.println("Split!");
			mMergeSort1(k, mid+1, end);
			mMergeSort1(k, start, mid);
		} else {
			array = bInsertionSort(array, start, end);
			//System.out.println(Arrays.toString(array));
		}
		merge(start, mid, end);

	}

	private void merge(int lowerIndex, int middle, int higherIndex) {
		//System.out.println("Init merge: "+Arrays.toString(array));
		int[] tempMergArr = new int[array.length];
		for (int i = lowerIndex; i <= higherIndex; i++) {
			tempMergArr[i] = array[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		while (i <= middle && j <= higherIndex) {
			if (tempMergArr[i] <= tempMergArr[j]) {
				array[k] = tempMergArr[i];
				i++;
			} else {
				array[k] = tempMergArr[j];
				j++;
			}
			k++;
		}
		while (i <= middle) {
			array[k] = tempMergArr[i];
			k++;
			i++;
		}
		
		System.out.println("Merged: " + lowerIndex + " <-> " + higherIndex +" "+ "\nResult: "+Arrays.toString(array));
	}

	private void mMergeSort2(int k, int start, int end){
		int mid = (start+end)/2;
		System.out.println(end-start+ " >? " + k);
		if((end-start) >= k){
			mMergeSort2(k, mid+1, end);
			mMergeSort2(k, start, mid);
		}else {
			array = InsertionSort(k, start, end);
			System.out.println(Arrays.toString(array));
			
		}
		merge(start, mid, end);	
	}

	private int BinarySearch(int a[], int low, int high, int key) {
		int mid;

		if (low == high)
			return low;

		mid = low + ((high - low) / 2);

		if (key > a[mid])
			return BinarySearch(a, mid + 1, high, key);
		else if (key < a[mid])
			return BinarySearch(a, low, mid, key);

		return mid;
	}

	private int[] bInsertionSort(int[] list, int start, int end) {
		//System.out.println("Sorting: " + Arrays.toString(list) + ", Interval " + (start) + " - " + (end - 1));
		int ins, i, j;
		int tmp;

		for (i = start + 1; i <= end; i++) {
			ins = BinarySearch(list, start, i, list[i]);
			tmp = list[i];
			for (j = i - 1; j >= ins; j--)
				list[j + 1] = list[j];
			list[ins] = tmp;
		}
		// System.out.println(Arrays.toString(this.seq));
		return list;
	}

	public int[] InsertionSort(int k, int start, int end) {
		System.out.println("Sorting: " + Arrays.toString(array) + ", Interval " + (start) +" - " + (end));
		for (int i = start; i <= end; i++) {
			int key = array[i];
			for (int j = i - 1; j >= 0; j--) {
				if (key < array[j]) {
					// System.out.println(key + " < " + s[j]);
					array[j + 1] = array[j];
					array[j] = key;
				} else {
					// System.out.println(key + " >= " + s[j]);
					array[j + 1] = key;
					// System.out.println(Arrays.toString(s)+"\n");
					break;
				}
			}
		}
		return array;
	}

	public int[] generateRandomList(int k) {
		int[] list = new int[k];
		for (int i = 0; i < k; i++) {
			int n = (int) (Math.random() * 1000);
			list[i] = n;
		}
		// System.out.println(Arrays.toString(list));
		return list;
	}
}
