package laboration3;

/*public class DAryHeap {

}*/
/**
 *   Java Program to Implement D-ary-Heap
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.NoSuchElementException;

/** Class D-ary Heap **/
class DAryHeap {
	private Node[] data;
	private int heapSize;
	private int d;

	public DAryHeap(int size, int d) {
		data = new Node[size];
		heapSize = 0;
		this.d = d;
	}

	public class Node {
		int dist;
		String id;

		public Node(String id, int dist) {
			this.dist = dist;
			this.id = id;

		}

		public void updateKey() {

		}

		public Node extractMin() {
			if (isEmpty())
				throw new HeapException("Heap is empty");
			else
				return data[0];
		}

		public boolean isEmpty() {
			return (heapSize == 0);
		}

		private int getParentIndex(int i) {
			return (i - 1) / d;
		}

		/** Function to get index of k th child of i **/
		private int kthChild(int i, int k) {
			return d * i + k;
		}

		public class HeapException extends RuntimeException {
			public HeapException(String message) {
				super(message);
			}
		}

		public void insert(int i) {
			if (heapSize == data.length) {
				// increase Size of heap
			}

		}
	}
}