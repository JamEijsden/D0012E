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
		encColl = 0;
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
		//encColl = 0;
		//System.out.println("Probe: " +probe);
		//probesDone = 0; 
		int collChain = 0;
		int j;
		while (true) {
			if (collChain > longestCollChain)
				longestCollChain = collChain;
			
			if (array[probe] == 0) {
				if (encColl == 1){
					insertionCollEncounters++;
					encColl = 0;
				}
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
		int[] testArray = {12796, 13170, 22752, 3459, 8197, 15634, 15531, 22306, 10319, 15794, 16458, 8587, 17871, 16624, 204, 3682, 9883, 13527, 20807, 3622, 18932, 18577, 11380, 25392, 11913, 10281, 11438, 21651, 1488, 15899, 6677, 6354, 28078, 26967, 3224, 26510, 17467, 8107, 19073, 29011, 12707, 24641, 19476, 26136, 11058, 25894, 10615, 28848, 17217, 28352, 26833, 23599, 3011, 26072, 11103, 25719, 21103, 14933, 26282, 937, 26082, 2391, 15926, 15842, 10542, 5476, 18230, 18403, 26443, 6732, 24729, 834, 13182, 733, 17063, 9254, 13350, 22654, 10091, 7649, 15148, 5186, 28114, 27680, 26885, 796, 2898, 20790, 23838, 3028, 27480, 5013, 11069, 13122, 8583, 25656, 7555, 26797, 22610, 26816, 17149, 10506, 15918, 9405, 20531, 16908, 9094, 20971, 29371, 25644, 7537, 13183, 19397, 17817, 6588, 4395, 3041, 20785, 8867, 7093, 24658, 4071, 4303, 3489, 22174, 27280, 9652, 26782, 28221, 16975, 13977, 941, 11543, 1432, 19744, 27993, 15539, 10126, 15780, 29002, 5424, 28648, 18344, 2963, 28414, 9554, 10064, 26849, 2855, 15377, 13368, 10035, 22027, 1706, 5394, 17156, 6696, 25832, 15132, 15342, 27237, 20029, 20793, 24486, 11310, 27987, 6012, 22936, 26722, 8378, 7626, 5625, 11163, 10840, 25278, 17231, 10984, 7008, 24051, 9476, 20146, 2430, 15780, 29122, 27818, 14370, 12478, 11618, 8933, 5617, 16654, 14651, 16252, 25481, 7948, 16168, 5160, 26090, 3736, 22583, 3556, 5086, 22877, 5050, 8757, 14620, 6867, 24770, 21334, 11370, 1087, 12280, 12215, 3039, 760, 20652, 4297, 3446, 1560, 8409, 3098, 936, 3615, 14782, 2724, 13386, 29832, 7718, 13621, 7565, 15159, 13724, 27214, 21009, 22470, 8292, 19569, 20962, 3291, 16208, 21742, 17191, 17560, 12588, 8388, 23751, 6013, 7894, 18602, 28920, 11625, 23621, 3742, 22643, 2825, 9626, 26276, 6862, 914, 9372, 21334, 16112, 27265, 25531, 18797, 23648, 22490, 15547, 28317, 12252, 9772, 14836, 12642, 8662, 18039, 14297, 16070, 16285, 17554, 18778, 14728, 22534, 10840, 14023, 3669, 4739, 5639, 11451, 22557, 12116, 13818, 2874, 6848, 772, 789, 10987, 25066, 4148, 24113, 26064, 14750, 12628, 27458, 4915, 20234, 23390, 17096, 14622, 24280, 24744, 22469, 1077, 13008, 18439, 10256, 10686, 9364, 28466, 22598, 12961, 24594, 25977, 4897, 1971, 9916, 26892, 16736, 5827, 1671, 1704, 11064, 25517, 22814, 15829, 1818, 17485, 1590, 13683, 8635, 8342, 14701, 10338, 9183, 15095, 17098, 1349, 5009, 20104, 23905, 13890, 25568, 16860, 11655, 20924, 27313, 412, 26287, 14597, 10391, 24971, 23891, 15466, 14565, 10879, 27324, 10528, 8525, 13678, 27262, 15323, 2718, 29551, 10224, 13596, 5220};
		int[] temp = {0, 0, 0, 17540, 12507, 5035, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19588, 6064, 0, 23942, 0, 18905, 0, 26516, 9415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21854, 8293, 0, 0, 0, 0, 0, 24223, 0, 0, 0, 0, 0, 0, 9365, 0, 0, 0, 0, 0, 0, 12847, 9799, 8745, 8594, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21915, 15273, 0, 5019, 17614, 2874, 0, 2208, 0, 0, 7342, 5941, 0, 0, 739, 0, 0, 0, 0, 14203, 0, 4195, 0, 0, 15932, 0, 0, 15167, 20672, 0, 0, 10673, 13052, 0, 26310, 4046, 0, 0, 0, 27649, 0, 5370, 0, 0, 0, 0, 0, 0, 24793, 11897, 24556, 4465, 0, 0, 8407, 4725, 0, 0, 19544, 0, 0, 0, 0, 25290, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23084, 10927, 1264, 0, 12611, 11459, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3780, 17314, 14339, 0, 0, 0, 0, 20477, 28045, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25215, 5682, 6388, 0, 0, 0, 22766, 0, 0, 0, 0, 0, 0, 0, 29497, 22172, 0, 0, 0, 10866, 0, 0, 0, 20462, 19717, 0, 0, 0, 10689, 0, 0, 0, 0, 22193, 11536, 0, 0, 24544, 12145, 16430, 0, 26151, 0, 1354, 0, 9107, 1926, 0, 0, 0, 3617, 16315, 19024, 12667, 0, 3341, 0, 15785, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1357, 10428, 0, 1836, 0, 0, 10460, 0, 0, 0, 0, 0, 16726, 0, 3928, 0, 21357, 0, 0, 0, 0, 12331, 17252, 17543, 16965, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10769, 15474, 0, 0, 0, 21963, 9890, 18315, 0, 0, 0, 0, 0, 6967, 532, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21730, 0, 0, 0, 0, 0, 15159, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29771, 21613, 27613, 0, 0, 0, 0, 29658, 0, 0, 0, 22552, 15285, 0, 0, 18792, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6458, 15453, 0, 0, 21186, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4747, 0, 4563, 0, 29951, 6753, 11322, 0, 0, 0, 4189, 3671, 8283, 13698, 14017, 1434, 0, 0, 0, 16125, 25321, 24732, 0, 0, 15538, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		Laboration2 lab = new Laboration2(100, testArray);
		lab.emptyArray = temp.clone();
		lab.c =1;
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
	public void testInsertion(int lf, int testToDo,int numTests, int tableSize, int c){
		int maxCol = 0, avgCol = 0, maxRehash = 0, avgChain = 0, avgRehash = 0, numInsertColl = 0;
		String testDone = "";
		int[] tmpTable;
		int x;
		switch(lf){
		//LOADFACTOR 0.25
		case 0:
			int[] table = {0, 0, 0, 17540, 12507, 5035, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19588, 6064, 0, 23942, 0, 18905, 0, 26516, 9415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21854, 8293, 0, 0, 0, 0, 0, 24223, 0, 0, 0, 0, 0, 0, 9365, 0, 0, 0, 0, 0, 0, 12847, 9799, 8745, 8594, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21915, 15273, 0, 5019, 17614, 2874, 0, 2208, 0, 0, 7342, 5941, 0, 0, 739, 0, 0, 0, 0, 14203, 0, 4195, 0, 0, 15932, 0, 0, 15167, 20672, 0, 0, 10673, 13052, 0, 26310, 4046, 0, 0, 0, 27649, 0, 5370, 0, 0, 0, 0, 0, 0, 24793, 11897, 24556, 4465, 0, 0, 8407, 4725, 0, 0, 19544, 0, 0, 0, 0, 25290, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23084, 10927, 1264, 0, 12611, 11459, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3780, 17314, 14339, 0, 0, 0, 0, 20477, 28045, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25215, 5682, 6388, 0, 0, 0, 22766, 0, 0, 0, 0, 0, 0, 0, 29497, 22172, 0, 0, 0, 10866, 0, 0, 0, 20462, 19717, 0, 0, 0, 10689, 0, 0, 0, 0, 22193, 11536, 0, 0, 24544, 12145, 16430, 0, 26151, 0, 1354, 0, 9107, 1926, 0, 0, 0, 3617, 16315, 19024, 12667, 0, 3341, 0, 15785, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1357, 10428, 0, 1836, 0, 0, 10460, 0, 0, 0, 0, 0, 16726, 0, 3928, 0, 21357, 0, 0, 0, 0, 12331, 17252, 17543, 16965, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10769, 15474, 0, 0, 0, 21963, 9890, 18315, 0, 0, 0, 0, 0, 6967, 532, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21730, 0, 0, 0, 0, 0, 15159, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29771, 21613, 27613, 0, 0, 0, 0, 29658, 0, 0, 0, 22552, 15285, 0, 0, 18792, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6458, 15453, 0, 0, 21186, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4747, 0, 4563, 0, 29951, 6753, 11322, 0, 0, 0, 4189, 3671, 8283, 13698, 14017, 1434, 0, 0, 0, 16125, 25321, 24732, 0, 0, 15538, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			testDone = "Loadfactor 0.25";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x), tmpTable);
				}else{
					linearProbingMod2(hashFunction(x), this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>avgChain) avgChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 1:
			int[] table1 = {};
			testDone = "Loadfactor 0.50";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table1.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x), tmpTable);
				}else{
					linearProbingMod2(hashFunction(x), this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>avgChain) avgChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 2:
			int[] table2 = {};
			testDone = "Loadfactor 0.75";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table2.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x), tmpTable);
				}else{
					linearProbingMod2(hashFunction(x), this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>avgChain) avgChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
			
			
		}
		System.out.println("Test: " + testDone);
		System.out.println("Average length of collision chains: " + avgChain);
		System.out.println("Number of insertions that encountered collisions: " + numInsertColl);
		System.out.println("Number of total probes/collisions: " + avgCol);
		System.out.println("max number of rehashes: " + maxRehash);
	}

}