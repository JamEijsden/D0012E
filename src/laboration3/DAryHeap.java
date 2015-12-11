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

		private int getLeftChildIndex(int nodeIndex) {
			return 2 * nodeIndex + 1;
		}

		private int getRightChildIndex(int nodeIndex) {
			return 2 * nodeIndex + 2;
		}

		private int getParentIndex(int nodeIndex) {
			return (nodeIndex - 1) / 2;
		}

		public class HeapException extends RuntimeException {
			public HeapException(String message) {
				super(message);
			}
		}
	}
}