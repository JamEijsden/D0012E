package laboration2;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Laboration2 {
	private int[] hashArray, emptyArray, valueArray;
	int ld = 0, lu = 0, c, probesDone, longestCollChain, insertionCollEncounters;
	long startTime, endTime, totalTime, startTime2, endTime2, totalTime2;

	public Laboration2(int size, int[] testValues) {
		this.hashArray = createStorage(size);
		this.emptyArray = hashArray.clone();
		this.valueArray = testValues;
	}

	public void resetVariables() {
		longestCollChain = 0;
		insertionCollEncounters = 0;
		probesDone = 0;
		hashArray = emptyArray.clone();
	}

	public void printVariables(String test) {
		System.out.println("Test: " + test);
		System.out.println("Longest Collision Chain: " + longestCollChain);
		System.out.println("Number of insertions that encountered collisions: " + insertionCollEncounters);
		System.out.println("Number of total probes/collisions: " + probesDone);
		System.out.println("Start values: " + Arrays.toString(valueArray));
	}

	public int linearProbing(int hx, int[] array) {
		int probe = hx % array.length;
		int collChain = 0;
		int encColl = 0;
		while (true) {
			if (array[probe] == 0) {
				if (encColl == 1)
					insertionCollEncounters++;
				// System.out.println("Number of probes: " + probesDone);
				return probe;
			}
			probe++;
			if (probe == array.length)
				probe = 0;
			collChain++;
			if (collChain > longestCollChain)
				longestCollChain = collChain;
			probesDone++;
			encColl = 1;

		}

	}

	public int linearProbingMod1(int hx, int[] array) {
		int probe = hx % array.length;
		int collChain = 0;
		int encColl = 0;
		while (true) {
			if (array[probe] == 0) {
				if (encColl == 1)
					insertionCollEncounters++;
				// System.out.println("Number of probes: " + probesDone);
				if (ld <= lu)
					ld++;
				else
					lu++;
				return probe;
			}
			if (ld <= lu)
				probe++;
			else
				probe--;
			if (probe == array.length)
				probe = 0;
			if (probe == -1)
				probe = array.length - 1;
			collChain++;
			if (collChain > longestCollChain)
				longestCollChain = collChain;
			probesDone++;
			encColl = 1;

		}
	}

	public int linearProbingMod2(int hx, int c, int[] array) {
		int probe = hx % array.length;
		probesDone = 0;

		int j;
		while (true) {
			if (array[probe] == 0) {
				j = probe;
				if (Math.abs(j - hx) <= c) {
					System.out.println("[j] <- x");
					return j;
				} else {
					int _j;
					int y = 0;
					for (_j = hx; _j <= (hx + c); _j++) {
						if (_j > array.length - 1)
							_j = (_j - (array.length - 1)) - 1;
						y = array[_j];

						if (Math.abs(j - hashFunction(y)) <= c && y != 0) {

							array[j] = y;
							System.out.println("[_j] <- x, [j] <- y");
							return _j;
						}
					}
					System.out.println("Beginning rehash");
					array = rehash(array);

					System.out.println("<<<<< Rehashing done >>>>>\n" + Arrays.toString(array));
					return linearProbingMod2(hx, this.c, array);

				}
			}
			probe++;
			if (probe == array.length)
				probe = 0;

			probesDone++;

		}
	}

	public static void main(String[] args) {
		// int[] testArray = { 11, 65, 33, 92, 21, 21, 21, 87, 39, 19, 11, 65,
		// 33, 92, 21, 21, 21, 87, 39, 19 };
		int[] testArray = { 1, 65, 2, 7, 89, 23, 65, 23, 673, 78, 2, 123, 90, 27, 872, 63, 871, 47, 156, 92, 18, 72, 92,
				45, 16, 52, 86, 27, 61 };
		Laboration2 lab = new Laboration2(testArray.length, testArray);

		lab.c = 2;
		// lab.linearTest();
		// lab.linearMod1Test();
		for (int i = 0; i < testArray.length; i++) {
			int x = testArray[i];
			lab.insert(x, lab.hashArray);
		}
		System.out.println("Tests done!");

	}

	public int[] insert(int x, int[] hashTable) { // the n will be hashed before
													// inserting, later.
		int hashedValue = hashFunction(x);
		// System.out.println("Probing " + x + " hashed " + hashedValue);
		int index = this.linearProbingMod2(hashedValue, this.c, hashArray);
		hashTable[index] = x;
		return hashTable;
	}

	public int[] rehash(int[] hashTable) {
		int[] newHashTable = createStorage(hashTable.length * 2);
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != 0)
				newHashTable = insert(hashTable[i], newHashTable);
		}
		return newHashTable;

	}

	public void linearTest() {
		int x;
		resetVariables();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < hashArray.length; i++) {
			x = valueArray[i];
			int hashedValue = hashFunction(x);
			int index = this.linearProbing(hashedValue, hashArray.clone());
			hashArray[index] = x;
			// System.out.println(Arrays.toString(lab.hashArray));
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		printVariables("Normal");
		System.out.println("Execution time: " + formatter.format((totalTime) / 1000d) + " seconds\n");

	}

	public void linearMod1Test() {

		int x;
		resetVariables();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < hashArray.length; i++) {
			x = valueArray[i];
			int hashedValue = hashFunction(x);
			int index = this.linearProbingMod1(hashedValue, hashArray.clone());
			hashArray[index] = x;
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		printVariables("Mod1");
		System.out.println("Execution time: " + formatter.format((totalTime) / 1000d) + " seconds\n");

	}

	public int hashFunction(int key) {
		return key * (key + 3) % hashArray.length;

	}

	public int[] createStorage(int k) {
		return new int[k];
	}

	public Node LinkedList(int n) {
		Node currentNode = new Node(0, n);
		for (int i = 1; i != n; i++) {
			Node newNode = new Node(i, n);
			currentNode.setSucc(newNode);
			currentNode = currentNode.getSucc();
		}
		return currentNode;
	}
}
