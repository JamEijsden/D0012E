package laboration3;

/*public class DAryHeap {
 */

/** Class D-ary Heap **/
class DAryHeap {
	public Node[] data;
	private int heapSize;
	private int d;

	public DAryHeap(int size, int d) {
		data = new Node[size];
		heapSize = 0;
		this.d = d;
	}

	public class Node {
		Integer dist = -1;
		String id;
		Node parent = null;
		Boolean unvisited = true;
		Integer position;

		public Node(String id) {
			this.id = id;

		}
	}

	public void decreaseKey(Node n, int value) {
		if (value < n.dist && n.id == data[n.position].id) {
			int index = n.position;
			data[index].dist = value;
			heapifyUp(index);
		}
	}

	public Node extractMin() {
		if (isEmpty()) {
			throw new HeapException("Heap is empty");
		} else {
			Node min = data[0];
			data[0] = data[heapSize - 1];
			data[0].position = 0;
			data[heapSize - 1] = null;
			heapSize--;
			this.heapifyDown(0);
			return min;
		}
	}

	public boolean isEmpty() {
		return (heapSize == 0);
	}

	private int getParentIndex(int i) {
		if (i == 0) {
			return i;
		}
		return (i - 1) / d;
	}

	/** Function to get index of k th child of i **/
	public int kthChild(int i, int k) {
		return d * i + k;
	}

	@SuppressWarnings("serial")
	public class HeapException extends RuntimeException {
		public HeapException(String message) {
			super(message);
		}
	}

	public void insert(Node n) {

/*		if (heapSize == data.length) {
			throw new HeapException("Heap is full");
		} else {*/
			n.position = heapSize;
			//printHeap();
			data[heapSize] = n;
			heapSize++;
			//System.out.println(n);

			heapifyUp(heapSize - 1);
		}
	
	public int size(){
		return heapSize;
	}

	public void heapifyUp(int childInd) {
		Node tmp = data[childInd];
		while (childInd > 0 && tmp.dist < data[getParentIndex(childInd)].dist) {
			data[childInd] = data[getParentIndex(childInd)];
			data[childInd].position = tmp.position;
			childInd = getParentIndex(childInd);
			tmp.position = childInd;
		}
		
		data[childInd] = tmp;
	}

	public void heapifyDown(int ind) {
		int child;
		Node tmp = data[ind];
		while (kthChild(ind, 1) < heapSize) {
			child = minChild(ind);
			if (data[child].dist < tmp.dist) {
				data[child].position = ind;
				data[ind] = data[child];

			} else
				break;
			ind = child;
			tmp.position = ind;
			
		}
		data[ind] = tmp;
	}

	public int minChild(int ind) {
		int bestChild = kthChild(ind, 1);
		int k = 2;
		int pos = kthChild(ind, k);
		while ((k < d) && (pos < heapSize)) {
			if (data[pos].dist < data[bestChild].dist)
				bestChild = pos;
			pos = kthChild(ind, k++);
			}
		return bestChild;
	}

	/** Function to print heap **/
	public void printHeap() {
		System.out.print("\nHeap = ");
		for (int i = 0; i < heapSize; i++)
			System.out.print(data[i].id + " ");
		System.out.println();
	}
}
