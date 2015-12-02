package laboration2;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class Laboration2 {
	private int[] hashArray, emptyArray, valueArray;
	int ld = 0, lu = 0, c, probesDone, longestCollChain, insertionCollEncounters, numberRehash, encColl = 0;
	long startTime, endTime, totalTime, startTime2, endTime2, totalTime2;

	public Laboration2(int size, int[] testValues) {
		this.emptyArray = createStorage(size);
		this.hashArray = emptyArray.clone();
		this.valueArray = testValues;
	}

	public void resetVariables() {
		longestCollChain = 0;
		numberRehash = 0;
		insertionCollEncounters = 0;
		probesDone = 0;
		encColl = 1;
		hashArray = emptyArray.clone();
	}

	public void printVariables(String test) {
		System.out.println("Test: " + test);
		System.out.println("Longest Collision Chain: " + longestCollChain);
		System.out.println("Number of insertions that encountered collisions: " + insertionCollEncounters);
		System.out.println("Number of total probes/collisions: " + probesDone);
		System.out.println("Number rehashes: " + numberRehash);
		System.out.println("Ran with C set to: " + c);
		System.out.println("Start size of inputlist: " + valueArray.length);
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

	public int[] linearProbingMod2(int x, int c, int[] array) {
		int hx = hashFunction(x);
		int probe = hx % array.length;
		encColl = 0;
		System.out.println("Probe: " +probe);
		//probesDone = 0; 
		int collChain = 0;
		int j;
		while (true) {
			if (collChain > longestCollChain)
				longestCollChain = collChain;
			
			if (array[probe] == 0) {
				if (encColl == 1)
					insertionCollEncounters++;
				j = probe;
				if (Math.abs(j - hx) <= c) {
					
					array[j] = x;
					return array;
				} else {
					int _j;
					int y = 0;
					_j = hx;
					while((_j <= (hx + c)) && y != 0) {
						System.out.println("hx: " + hx +", c: "+ c + ", y: "+y);
						if (_j > array.length - 1)
							_j = (_j - (array.length - 1)) - 1;
						y = array[_j];

						if (Math.abs(j - hashFunction(y)) <= c && y != 0) {

							array[j] = y;
							//System.out.println("[_j] <- x, [j] <- y");
							if (encColl == 1)
								//insertionCollEncounters++;
							array[_j] = x;
							return array;
						}
						_j++;
					}
					//System.out.println("Beginning rehash: " +Arrays.toString(array));
					int[] newArray = rehash(array).clone();
					array = newArray.clone();
					if (encColl == 1)
						//insertionCollEncounters++;
					array = linearProbingMod2(hx, this.c, array);
					//System.out.println("Size of list " + array.length);
					return array;

				}
			}
			
			probe++;
			if (probe == array.length)
				probe = 0;
		
			
			collChain++;
			probesDone++;
			encColl = 1;
		}
		
	}

	public static void main(String[] args) {
		int[] testArray = {49007, 24793, 34070, 75944, 11994, 32572, 83360, 88733, 68197, 27454, 54886, 44671, 27958, 19232, 99108, 87829, 195, 97457, 47426, 89496, 68813, 1671, 57953, 41517, 95774, 98516, 76906, 84397, 11393, 61397, 7518, 77261, 3367, 40667, 18310, 60546, 70003, 32703, 15626, 84319, 60256, 4807, 51533, 75202, 83446, 9477, 37492, 93083, 34896, 26421, 86662, 85191, 50235, 3590, 13252, 79522, 70750, 29504, 76035, 72256, 74905, 58368, 64956, 54497, 7046, 26703, 94704, 92523, 13302, 45658, 87173, 22709, 76821, 26698, 95962, 47375, 29743, 18, 50340, 21049, 7672, 14822, 73564, 97969, 3943, 68525, 61014, 71114, 75575, 97946, 87277, 70689, 50585, 79747, 48143, 5652, 87187, 29651, 33418, 25783};
		//int[] temp = {61,0,0,74,32,0,48,0,0,0,56,71,0,28,0,13,8,1,0,0};
		Laboration2 lab = new Laboration2(100, testArray);
		//lab.emptyArray = temp.clone();
		lab.c = 75;
		//lab.linearTest();
		//lab.linearMod1Test()
		lab.linearMod2Test();
		//System.out.println(Arrays.toString(lab.genHashTable(500, 0.25)));
		//System.out.println(Arrays.toString(lab.generateRandomList(3750)));
		System.out.println("Tests done!\n");

	}

	public int[] insert(int x, int[] hashTable) { // the n will be hashed before
													// inserting, later.
		hashTable = this.linearProbingMod2(x, this.c, hashTable);
		return hashTable;
	}

	public int[] rehash(int[] hashTable) {
		numberRehash++;
		int[] newHashTable = createStorage(hashTable.length * 2);
		
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != 0)
				newHashTable = this.linearProbingMod2(hashTable[i], this.c, newHashTable);
				//System.out.println("Calling insert from hash, Size of list " + newHashTable.length);			
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
	public void linearMod2Test(){
		int x;
		resetVariables();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < (valueArray.length/2)+1; i++) {
			x = valueArray[i];
			hashArray = insert(x, hashArray).clone();
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		printVariables("Mod2");
		System.out.println("End size of hashTable: " + hashArray.length);
		System.out.println("Execution time: " + formatter.format((totalTime) / 1000d) + " seconds\n");
	}

	public int hashFunction(int key) {
		return key * (key + 3) % hashArray.length;

	}

	public int[] createStorage(int k) {
		return new int[k];
	}
	
	public int[] genHashTable(int m, double loadFactor){
		int[] hashTable = generateRandomList(m);
		int n;
		for (int i = 0; i < (((double) m) * (1.00 - loadFactor)); i++) {
			n = (int) ((Math.random() * m));
			while(hashTable[n] == 0){
				n++;
				if(n == m){
					n = 0;
				}
			}
			hashTable[n] = 0;	
		}
		return hashTable;
		
	}
	public int[] generateRandomList(int k) {
		int[] list = new int[k];
		for (int i = 0; i < k; i++) {
			int n = (int) ((Math.random() * 100000)+1);
			list[i] = n;
		}
		// System.out.println(Arrays.toString(list));
		return list;
	}

}