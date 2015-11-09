package laborations;

import java.util.Arrays;

public class Laboration1 {
	private int[] array;

	public Laboration1() {

	}

	public static void main(String[] args) {
		Laboration1 lab = new Laboration1();
		lab.array = lab.generateRandomList(7);
		// lab.mMergeSort1(s, 0, s.length);
		lab.doMergeSort(3);
	}

	public void doMergeSort(int k) {
		mMergeSort1(this.array, k, 0, this.array.length);
	}

	private void mMergeSort1(int[] array, int k, int start, int end){
		int mid = (start+end)/2;
		System.out.println(end-start+ " >? " + k);
		if((end-start) > k){
			mMergeSort1(array, k, mid, end);
			mMergeSort1(array,k, start, mid);
		}else {
			array = bInsertionSort(array, start, end);
			System.out.println(Arrays.toString(array));
		}
		merge(array, start, mid+1);
		
		
	}
	
	private void merge(int[] array, int high, int low){
		
	}

	private void mMergeSort2() {
		// Hej hej tomte
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
		System.out.println("Sorting: " + Arrays.toString(list) + ", Interval " + (start) +" - " + (end-1));
		int ins, i, j;
		int tmp;

		for (i = start+1; i < end; i++) {
			ins = BinarySearch(list, start, i, list[i]);
			tmp = list[i];
			for (j = i - 1; j >= ins; j--)
				list[j + 1] = list[j];
			list[ins] = tmp;
		}
		// System.out.println(Arrays.toString(this.seq));
		return list;
	}

	public int[] InsertionSort(int[] list) {
		int[] s = list;
		System.out.println("Sorting: " + Arrays.toString(s));
		for (int i = 1; i < s.length; i++) {
			int key = s[i];
			for (int j = i - 1; j >= 0; j--) {
				if (key < s[j]) {
					// System.out.println(key + " < " + s[j]);
					s[j + 1] = s[j];
					s[j] = key;
				} else {
					// System.out.println(key + " >= " + s[j]);
					s[j + 1] = key;
					// System.out.println(Arrays.toString(s)+"\n");
					break;
				}
			}
		}
		return list;
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
