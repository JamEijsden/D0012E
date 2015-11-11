package laborations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Laboration1 {
	private int[] array;
	long startTime, endTime, totalTime, startTime2, endTime2, totalTime2;

	public Laboration1() {

	}

	public static void main(String[] args) {
		Laboration1 lab = new Laboration1();
		int[] newArr = lab.generateRandomList(10000000);
		
		// System.out.println("Start: "+Arrays.toString(lab.array));
		// lab.mMergeSort1(s, 0, s.length);
		lab.doMergeSort(1000000, newArr);
		//System.out.println(Arrays.toString(lab.InsertionSort(0, newArr.length-1)));

	}

	private int[] range(int low, int high) {
		int[] arr = new int[high - low];

		int i = 0;
		while (low <= high) {
			low = low + i;
			arr[i] = low;
			i++;

		}
		return arr;
	}

	public void doMergeSort(int k, int[] arr) {
		int[] sorted = range(0, 100000);
		int[] cpArr = arr.clone();
		this.array = arr;
		//System.out.println(Arrays.toString(array));
		NumberFormat formatter = new DecimalFormat("#0.00000");
		startTime = System.currentTimeMillis();
		mMergeSort1(k, 0, this.array.length - 1);
		
		//bInsertionSort(array, 0, array.length-1);
		
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("Execution time, binary, is " + formatter.format((totalTime) / 1000d) + " seconds");
		
		this.array = cpArr;
		//System.out.println(Arrays.toString(cpArr));
		startTime2 = System.currentTimeMillis();
		mMergeSort2(k, 0, this.array.length - 1);
		
		//InsertionSort(0, arr.length-1);
		
		endTime2 = System.currentTimeMillis();
		totalTime2 = endTime2 - startTime2;
		System.out.println("Execution time, linear, is " + formatter.format((totalTime2) / 1000d) + " seconds");
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
		// " + (start) +" - " + (end));
		for (int i = start; i <= end; i++) {

			int valueToSort = array[i];
			int j = i;
			while (j > 0 && array[j - 1] > valueToSort) {
				array[j] = array[j - 1];
				j--;
			}
			array[j] = valueToSort;
			/*
			 * int key = array[i]; for (int j = i - 1; j >= 0; j--) { if (key <
			 * array[j]) { // System.out.println(key + " < " + s[j]); array[j +
			 * 1] = array[j]; array[j] = key; } else { // System.out.println(key
			 * + " >= " + s[j]); array[j + 1] = key; //
			 * System.out.println(Arrays.toString(s)+"\n"); break; } }
			 */
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
