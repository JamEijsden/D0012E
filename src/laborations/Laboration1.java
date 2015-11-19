package laborations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class Laboration1 {
	private int[] array;
	long startTime, endTime, totalTime, startTime2, endTime2, totalTime2;

	public Laboration1() {

	}

	public static void main(String[] args) {
		Laboration1 lab = new Laboration1();
		 //int[] newArr = lab.generateRandomList(400000);
		// int[] almostSortArr = lab.almostSorted(2000000); //alternating
		lab.test();
	}

	private int[] range(int low, int high) {
		int[] arr = new int[high - low];

		int i = 0;
		while (low < high) {
			low = low + 1;
			arr[i] = low;
			i++;

		}
		return arr;
	}

	public void doMergeSort(int k, int[] arr) {
		// int[] sorted = range(0, 2000000);
		
		this.array = arr.clone();
		
		// this.array = sorted;
		// System.out.println(Arrays.toString(array));
		NumberFormat formatter = new DecimalFormat("#0.00000");
		startTime = System.currentTimeMillis();
		System.out.println("Binary Start list length: " + Arrays.toString(arr));
		mMergeSort1(k, 0, this.array.length - 1);
		int[] bSorted = array;
		// bInsertionSort(array, 0, array.length-1);

		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("Execution time, binary, is " + formatter.format((totalTime) / 1000d) + " seconds\n");

		this.array = arr.clone();
		// this.array = sorted;
		// System.out.println(Arrays.toString(cpArr));
		startTime2 = System.currentTimeMillis();
		System.out.println("Linear Start list length: " + Arrays.toString(arr));
		mMergeSort2(k, 0, this.array.length - 1);
		endTime2 = System.currentTimeMillis();
		totalTime2 = endTime2 - startTime2;
		System.out.println("Execution time, linear, is " + formatter.format((totalTime2) / 1000d) + " seconds\n");
		int[] lSorted = array;
		// InsertionSort(0, arr.length-1);
		for(int i = 0; i < lSorted.length; i++){
			if(lSorted[i] != bSorted[i]) {
				System.out.println("Linear Sorted == Binary Sorted = false\n");
				break;
			}else {
				if(i == lSorted.length-1){
					System.out.println("Linear Sorted == Binary Sorted = true\n");
				}
			}
		}
		System.out.println("Linear: "+ Arrays.toString(lSorted) + "\nBinary: " + Arrays.toString(bSorted));
	}

	private void mMergeSort1(int k, int start, int end) {
		int mid = (start + end) / 2;
		// System.out.println(end - start + " >? " + k);
		if ((end - start) >= k) {
			// System.out.println("Split!");
			mMergeSort1(k, mid + 1, end);
			mMergeSort1(k, start, mid);
		} else {
			array = bInsertionSort(array, start, end);
			// System.out.println(Arrays.toString(array));
		}
		merge(start, mid, end);

	}

	private void merge(int lowerIndex, int middle, int higherIndex) {
		// System.out.println("Init merge: "+Arrays.toString(array));
		int[] tempMergArr = new int[array.length];
		for (int i = lowerIndex; i <= higherIndex; i++) {
			tempMergArr[i] = array[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		while(k <= higherIndex) {
			if (i > middle) {
				array[k] = tempMergArr[j];
				j++;
			} else if(j > higherIndex) {
				array[k] = tempMergArr[i];
				i++;
			} else if(tempMergArr[j] < tempMergArr[i]){
				array[k] = tempMergArr[j];
				j++;
			} else{
				array[k] = tempMergArr[i];
				i++;
			}
			//System.out.println(Arrays.toString(array));
			k++;
		}
		/*while (i <= middle) {
			System.out.println(Arrays.toString(array));
			array[k] = tempMergArr[i];
			k++;
			i++;
		}*/

		// System.out.println("Merged: " + lowerIndex + " <-> " + higherIndex +"
		// "+ "\nResult: "+Arrays.toString(array));
	}

	private void mMergeSort2(int k, int start, int end) {
		int mid = (start + end) / 2;
		// System.out.println(end-start+ " >? " + k);
		if ((end - start) >= k) {
			mMergeSort2(k, mid + 1, end);
			mMergeSort2(k, start, mid);
		} else {
			array = InsertionSort(start, end);
			// System.out.println(Arrays.toString(array));

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
		// System.out.println("Sorting: " + Arrays.toString(list) + ", Interval
		// " + (start) + " - " + (end - 1));
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

	public int[] InsertionSort(int start, int end) {
		// System.out.println("Sorting: " + Arrays.toString(array) + ", Interval
		System.out.println((start) +" - " + (end));
		//if(start != 0) start = start-1;
		for (int i = start+1; i <= end; i++) {

			int valueToSort = array[i];
			int j = i-1;
			while (j >= start && array[j] > valueToSort) {
				array[j+1] = array[j];
				j--;
			}
			array[j+1] = valueToSort;
			/*
			 * int key = array[i]; for (int j = i - 1; j >= 0; j--) { if (key <
			 * array[j]) { // System.out.println(key + " < " + s[j]); array[j +
			 * 1] = array[j]; array[j] = key; } else { // System.out.println(key
			 * + " >= " + s[j]); array[j + 1] = key; //
			 * System.out.println(Arrays.toString(s)+"\n"); break; } }
			 */
		}
		System.out.println("================= " +Arrays.toString(array));
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

	public int[] almostSorted(int high) {
		int[] testarr = new int[high];
		int i = 0;
		while (i < high) {
			if (i < 1) {
				testarr[i] = 0;
			} else {
				if (i % 2 == 0) {
					testarr[i] = i;
				} else {
					testarr[i] = i + 2;
				}
			}
			i++;

		}
		// System.out.println(Arrays.toString(testarr));
		return testarr;
	}

	public void test() {
		int n = 10;
		int k = 10;
		int[] arr;
	
		while (n <= 40) {
			arr = this.generateRandomList(n);
			//System.out.println(Arrays.toString(arr));
			System.out.println("Test running with k=" + k + " and n=" + n);
			this.doMergeSort(k, arr);
			//array = arr.clone();
			//this.InsertionSort(5, 9);
			n = n*2;
			System.out.println("\n");
		}
	}
}
