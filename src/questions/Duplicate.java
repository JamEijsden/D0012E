package questions;

import java.util.Arrays;

public class Duplicate {

	private String[] array;
	private String[] tempMergArr;
	private int length;

	public static void main(String a[]) {

		String[] inputArr = { "a", "1", "b", "a", "c", "1", "3" };
		Duplicate mms = new Duplicate();
		mms.sort(inputArr);
		for (String i : inputArr) {
			System.out.print(i);
			System.out.print(" ");
		}
	}

	public void sort(String inputArr[]) {
		this.array = inputArr;
		this.length = inputArr.length;
		this.tempMergArr = new String[length];
		doMergeSort(0, length - 1);
	}

	private void doMergeSort(int lowerIndex, int higherIndex) {

		if (lowerIndex < higherIndex) {
			int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
			// Below step sorts the left side of the array
			doMergeSort(lowerIndex, middle);
			// Below step sorts the right side of the array
			doMergeSort(middle + 1, higherIndex);
			// Now merge both sides
			mergeParts(lowerIndex, middle, higherIndex);
		}
	}

	private void mergeParts(int lowerIndex, int middle, int higherIndex) {

		for (int i = lowerIndex; i <= higherIndex; i++) {
			tempMergArr[i] = array[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		int j2;
		while (i <= middle && j <= higherIndex) {
			j2 = j;
			while (j2 <= higherIndex) {
				if (tempMergArr[i] != tempMergArr[j]) {
					// array[k] = tempMergArr[i];
				

					i++;
				} else {
					array[k] = tempMergArr[j];
					j++;
				}
			}
			k++;

		}
		while (i <= middle) {
			array[k] = tempMergArr[i];
			k++;
			i++;
		}

	}
}
