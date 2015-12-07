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
		int hx = hashFunction(x, array.length);
		
		//System.out.println(hx);
		int probe = hx % array.length;
		//System.out.println(probe);
		//encColl = 0;
		//System.out.println("Probe: " +probe);
		//probesDone = 0; 
		int collChain = 0;
		int j;
		while (true) {
			if (collChain > longestCollChain)
				longestCollChain = collChain;
			
			if (array[probe] == 0) {
				j = probe;
				if (Math.abs(j - hx) <= c) {
					if (encColl == 1){
						insertionCollEncounters++;
						encColl = 0;
					}
					array[j] = x;
					return array;
				} else {
					int _j;
					int y = 0;
					_j = hx;
					//System.out.println("hx: " + hx +", c: "+ c + ", y: "+y);
					while((_j <= (hx + c))) {
						
						if (_j > array.length - 1)
							_j = (_j - (array.length - 1)) - 1;
						y = array[_j];

						if (Math.abs(j - hashFunction(y, array.length)) <= c && y != 0) {
							if (encColl == 1){
								insertionCollEncounters++;
								encColl = 0;
							}
							array[j] = y;							
							array[_j] = x;
							return array;
						}
						_j++;
					}
					
					int[] newArray = rehash(array).clone();
					array = newArray.clone();
					//System.out.println(x);
					array = linearProbingMod2(x, this.c, array);
					if (encColl == 1){
						insertionCollEncounters++;
						encColl = 0;
					}
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
		//int[] temp = {0, 0, 0, 17540, 12507, 5035, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19588, 6064, 0, 23942, 0, 18905, 0, 26516, 9415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21854, 8293, 0, 0, 0, 0, 0, 24223, 0, 0, 0, 0, 0, 0, 9365, 0, 0, 0, 0, 0, 0, 12847, 9799, 8745, 8594, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21915, 15273, 0, 5019, 17614, 2874, 0, 2208, 0, 0, 7342, 5941, 0, 0, 739, 0, 0, 0, 0, 14203, 0, 4195, 0, 0, 15932, 0, 0, 15167, 20672, 0, 0, 10673, 13052, 0, 26310, 4046, 0, 0, 0, 27649, 0, 5370, 0, 0, 0, 0, 0, 0, 24793, 11897, 24556, 4465, 0, 0, 8407, 4725, 0, 0, 19544, 0, 0, 0, 0, 25290, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23084, 10927, 1264, 0, 12611, 11459, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3780, 17314, 14339, 0, 0, 0, 0, 20477, 28045, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25215, 5682, 6388, 0, 0, 0, 22766, 0, 0, 0, 0, 0, 0, 0, 29497, 22172, 0, 0, 0, 10866, 0, 0, 0, 20462, 19717, 0, 0, 0, 10689, 0, 0, 0, 0, 22193, 11536, 0, 0, 24544, 12145, 16430, 0, 26151, 0, 1354, 0, 9107, 1926, 0, 0, 0, 3617, 16315, 19024, 12667, 0, 3341, 0, 15785, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1357, 10428, 0, 1836, 0, 0, 10460, 0, 0, 0, 0, 0, 16726, 0, 3928, 0, 21357, 0, 0, 0, 0, 12331, 17252, 17543, 16965, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10769, 15474, 0, 0, 0, 21963, 9890, 18315, 0, 0, 0, 0, 0, 6967, 532, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21730, 0, 0, 0, 0, 0, 15159, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29771, 21613, 27613, 0, 0, 0, 0, 29658, 0, 0, 0, 22552, 15285, 0, 0, 18792, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6458, 15453, 0, 0, 21186, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4747, 0, 4563, 0, 29951, 6753, 11322, 0, 0, 0, 4189, 3671, 8283, 13698, 14017, 1434, 0, 0, 0, 16125, 25321, 24732, 0, 0, 15538, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		Laboration2 lab = new Laboration2(100, testArray);
		lab.c = 1;
		/*testInsertion(
		 * ARG1, (0 = 0.25, 1 = 0.5, 2 = 0.75, 3 = 0.4, 4 = 0.6, 5 = 0.90)
		 * ARG2, (0 = Normal, 1 = Mod1, 2 = Mod2)
		 * ARG3 (Number of inserts)
		 */
		lab.testInsertion(0, 2, 10000);
		//System.out.println(Arrays.toString(lab.genHashTable(500, 0.9)));
		System.out.println("Tests done!\n");

	}

	public int[] insert(int x, int[] hashTable) { // the n will be hashed before
													// inserting, later.
		hashTable = this.linearProbingMod2(x, this.c, hashTable);
		return hashTable;
	}

	public int[] rehash(int[] hashTable) {
		numberRehash++;
		//System.out.println(" REHASHING " + hashTable.length*2);
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
			int hashedValue = hashFunction(x,0);
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
			int hashedValue = hashFunction(x,0);
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

	public int hashFunction(int key, int tablesize) {
		return key * (key + 3) % (tablesize/23);

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
	public void testInsertion(int lf, int testToDo,double numTests){
		int maxCol = 0, avgCol = 0, maxChain=0, maxRehash = 0, avgChain = 0, avgRehash = 0, numInsertColl = 0;
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
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 1:
			int[] table1 = {0, 0, 6446, 18513, 0, 0, 0, 19340, 13119, 10436, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15955, 0, 4985, 27089, 0, 10144, 2339, 27370, 21424, 0, 3624, 0, 0, 0, 19053, 26900, 9883, 0, 0, 0, 2951, 0, 0, 0, 0, 11199, 0, 14871, 29418, 12397, 22556, 0, 29969, 4831, 11844, 414, 27508, 0, 8271, 0, 0, 0, 17278, 0, 0, 21370, 0, 0, 24648, 7916, 0, 0, 0, 15915, 2590, 20986, 28373, 6334, 15846, 13018, 0, 28817, 19415, 10357, 25953, 24006, 0, 0, 0, 0, 0, 0, 0, 3607, 0, 0, 15950, 0, 0, 27143, 0, 26528, 21780, 0, 0, 24754, 12116, 0, 22019, 0, 0, 13961, 18907, 0, 0, 4408, 0, 0, 0, 0, 0, 6463, 0, 22050, 22262, 0, 19831, 13751, 0, 0, 0, 24368, 12663, 23615, 0, 12405, 0, 0, 0, 0, 0, 24735, 16299, 2758, 0, 0, 0, 0, 0, 16798, 0, 0, 0, 0, 0, 17711, 17663, 8998, 10213, 27330, 0, 0, 0, 0, 12732, 0, 0, 681, 7282, 7287, 0, 0, 9170, 27490, 16643, 27685, 7202, 29277, 0, 0, 0, 0, 14361, 15021, 17313, 1435, 0, 20517, 15487, 0, 0, 0, 7726, 0, 0, 251, 15831, 1048, 22109, 0, 19942, 9493, 23611, 0, 0, 4658, 12580, 0, 0, 17885, 20865, 16196, 0, 0, 13968, 21966, 14155, 4585, 11304, 10381, 14187, 6971, 0, 0, 0, 25050, 25377, 12930, 0, 0, 0, 24821, 18615, 2917, 0, 0, 20512, 10189, 0, 10462, 0, 0, 0, 0, 24587, 0, 0, 0, 0, 23778, 24657, 0, 26559, 22064, 13751, 0, 9677, 24844, 25940, 14109, 9255, 5058, 0, 0, 0, 0, 0, 0, 1165, 14970, 19043, 10208, 23180, 0, 0, 10712, 0, 0, 0, 0, 3773, 24008, 19787, 6732, 0, 10386, 28656, 17838, 11401, 0, 0, 0, 0, 5853, 1480, 0, 0, 0, 0, 17801, 15545, 0, 0, 6655, 3573, 0, 0, 27633, 10959, 0, 0, 0, 0, 0, 0, 3697, 26975, 11675, 4752, 0, 0, 811, 2378, 0, 15809, 0, 27020, 0, 26454, 17064, 23432, 16768, 19101, 0, 0, 0, 0, 0, 0, 0, 0, 19839, 16409, 0, 6594, 0, 8683, 0, 21200, 0, 0, 16417, 26607, 9894, 0, 27840, 0, 0, 26177, 0, 0, 0, 1269, 20563, 0, 26588, 8015, 9009, 6087, 0, 0, 0, 23321, 14104, 9602, 0, 16337, 7899, 18414, 0, 0, 7779, 8743, 8275, 11889, 464, 7492, 0, 0, 16006, 8791, 3398, 0, 0, 0, 25398, 0, 0, 28333, 3111, 1591, 10575, 0, 0, 8809, 0, 11568, 0, 0, 5908, 23161, 14109, 0, 0, 21351, 20899, 0, 0, 9741, 13811, 11393, 0, 4408, 12976, 14258, 12457, 18879, 27445, 0, 0, 0, 0, 28158, 0, 18301, 27499, 15244, 0, 26438, 28785, 15944, 0, 0, 0, 15113, 0, 7756, 0, 0, 0, 0, 5419, 0, 6769, 0, 0, 1395, 0, 23983, 16542, 0, 22660, 0, 6915, 0, 0, 18368, 7198, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13011, 23701, 0, 25260, 17313, 0, 8775, 6279, 14508, 2125, 0, 15139, 0, 0, 0, 0, 13034, 18846, 5699, 0, 24872, 0, 0, 16898};
			testDone = "Loadfactor 0.50";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table1.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 2:
			int[] table2 = {0, 6202, 16800, 11314, 29079, 0, 0, 0, 12462, 20372, 26144, 0, 0, 0, 0, 5924, 10466, 11530, 11015, 25942, 17048, 179, 8723, 26230, 8497, 0, 0, 0, 22902, 28277, 26879, 16337, 5424, 9578, 4193, 20189, 0, 23822, 13679, 0, 24966, 18904, 27529, 17932, 19262, 0, 16438, 15378, 24807, 28419, 2397, 16980, 0, 0, 0, 8185, 0, 20202, 1825, 0, 21985, 0, 13294, 21331, 0, 14243, 0, 7752, 24587, 21408, 7416, 20038, 12201, 27833, 15631, 7020, 5045, 17652, 603, 0, 12084, 22078, 22736, 22898, 22591, 0, 27159, 0, 12309, 5799, 18972, 3889, 24514, 18060, 27529, 22415, 16505, 24598, 16304, 9667, 1494, 24044, 27913, 14093, 29993, 10368, 16586, 11934, 13621, 8933, 12810, 16406, 0, 0, 0, 0, 27666, 16938, 10453, 0, 25556, 1634, 14669, 13461, 23713, 23794, 18607, 6672, 0, 22033, 17617, 0, 0, 23238, 5681, 406, 28685, 665, 14141, 0, 0, 11714, 19616, 0, 22119, 12966, 0, 0, 23939, 0, 24512, 25500, 20196, 0, 5435, 0, 9835, 10652, 0, 23474, 26662, 24834, 25621, 0, 28111, 0, 0, 0, 15935, 23116, 9809, 0, 321, 25144, 10341, 0, 17453, 0, 28766, 24074, 23744, 0, 0, 5885, 7816, 16042, 2184, 21903, 13902, 13320, 27999, 0, 27580, 5598, 0, 0, 26703, 12667, 0, 27778, 0, 19136, 506, 0, 23296, 28257, 1898, 12557, 12730, 2725, 0, 16241, 8222, 5165, 10537, 25916, 4337, 0, 6624, 25520, 8942, 18670, 722, 0, 0, 26145, 19899, 0, 2335, 20057, 22266, 8948, 12646, 0, 0, 21938, 0, 23941, 21127, 14588, 15777, 2768, 0, 4634, 21688, 18616, 5858, 22492, 16565, 0, 15150, 0, 28734, 28478, 3775, 29888, 24683, 19929, 0, 16953, 0, 15014, 0, 11482, 0, 5878, 72, 16357, 17075, 0, 4631, 0, 22956, 13145, 27290, 26710, 3493, 25312, 0, 3728, 0, 14318, 247, 28059, 24043, 9653, 26547, 27514, 22887, 1950, 8922, 616, 14277, 13286, 27187, 29678, 26742, 0, 3631, 12934, 5346, 20214, 6106, 0, 26343, 18816, 1157, 18915, 0, 28091, 4099, 0, 11624, 20755, 19380, 8294, 26365, 0, 752, 25025, 0, 16167, 16618, 22744, 19815, 8764, 1445, 29779, 0, 23073, 29393, 7257, 0, 16640, 21181, 25771, 28403, 6468, 19830, 13945, 8414, 20553, 5228, 12897, 6476, 3140, 1393, 22914, 19166, 23250, 4015, 8216, 21240, 410, 29184, 2347, 0, 8090, 0, 20666, 1290, 0, 0, 0, 28300, 17762, 0, 0, 0, 28176, 2913, 0, 17211, 10403, 8832, 0, 6684, 27366, 20407, 21318, 26619, 12169, 20951, 19397, 0, 18792, 4097, 0, 0, 12751, 22261, 12053, 21105, 0, 10918, 7132, 391, 17873, 0, 23110, 20931, 2569, 29692, 0, 11943, 20308, 5074, 29054, 4280, 19522, 0, 3842, 29849, 0, 11756, 0, 22612, 21622, 0, 28242, 4096, 8677, 2071, 10177, 0, 20453, 5489, 25634, 0, 0, 0, 0, 5385, 3487, 0, 0, 27632, 6241, 0, 18993, 0, 3940, 8174, 10881, 10669, 10389, 0, 0, 12018, 2814, 2900, 21298, 18111, 23347, 0, 20996, 11590, 0, 17672, 0, 14081, 0, 18255, 17842, 28342, 16148, 0, 1239, 13027, 23333, 15327, 21688, 7324, 18724, 1614, 29530, 2824, 20542, 7630, 0, 24677, 14802, 21103, 0, 0, 10383, 28563, 879, 4377, 0, 24644, 0, 25353, 19529, 6649, 12266, 26888, 807, 17753, 22421};
			testDone = "Loadfactor 0.75";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table2.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 3:
			int[] table3 = {0, 0, 0, 6749, 0, 33739, 0, 21983, 0, 87882, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17876, 0, 43807, 66305, 42456, 90140, 0, 32986, 64205, 44214, 0, 41457, 75514, 61387, 0, 0, 0, 21956, 44445, 45619, 0, 47743, 51395, 0, 6561, 0, 0, 18340, 93231, 36712, 55483, 39132, 3954, 0, 0, 0, 18775, 54037, 54662, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18834, 7891, 70533, 43645, 45675, 65908, 49988, 0, 44317, 0, 62727, 55746, 34400, 52313, 60812, 21916, 73664, 74165, 0, 23669, 58268, 4620, 0, 0, 0, 23846, 0, 78065, 93457, 92288, 4964, 0, 0, 18225, 0, 57756, 25919, 34415, 18483, 60169, 89785, 0, 0, 86863, 0, 0, 0, 0, 0, 0, 0, 0, 89041, 86024, 89658, 0, 0, 0, 0, 99799, 63461, 84136, 0, 72968, 38756, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41150, 51093, 0, 0, 0, 85162, 21398, 72144, 79507, 27857, 78981, 0, 0, 0, 35268, 27935, 48128, 75096, 0, 12120, 0, 99093, 0, 0, 0, 83326, 46022, 0, 0, 77030, 2380, 0, 0, 0, 0, 92914, 0, 0, 85150, 74216, 0, 8547, 65479, 40689, 0, 0, 0, 0, 30835, 37105, 0, 0, 0, 0, 0, 31767, 81915, 0, 0, 0, 0, 0, 0, 34298, 11015, 65104, 0, 0, 0, 0, 0, 0, 68534, 72165, 0, 61660, 91225, 96021, 27121, 0, 0, 22850, 11721, 44804, 0, 0, 0, 75100, 77810, 0, 98100, 16617, 21304, 0, 0, 0, 0, 3468, 69512, 65105, 0, 0, 0, 0, 0, 0, 87780, 28777, 45038, 38612, 59423, 90248, 55777, 95975, 42348, 0, 27807, 0, 0, 35105, 76302, 83233, 29283, 7203, 0, 0, 0, 41931, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 57721, 19236, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1123, 83484, 23781, 0, 65413, 23937, 21623, 0, 0, 88325, 8013, 23630, 0, 65530, 52051, 875, 17155, 25440, 3810, 0, 17299, 92066, 0, 0, 0, 0, 0, 18233, 4302, 47839, 0, 0, 90702, 18500, 5373, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 83463, 0, 0, 94432, 0, 36573, 59777, 0, 0, 0, 0, 0, 18312, 3312, 15501, 0, 0, 59739, 1500, 0, 0, 0, 0, 0, 0, 72877, 0, 0, 0, 0, 0, 0, 0, 0, 25024, 55514, 0, 0, 5023, 0, 53346, 90497, 53057, 2784, 52644, 0, 44207, 79092, 0, 38528, 0, 67412, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 93240, 88804, 94166, 0, 0, 0, 0, 58741, 20073, 37095, 0, 0, 0, 0, 65848, 0, 0, 82615, 46229, 37595, 95787, 6122, 0, 28822, 35449, 0, 0, 0, 80447, 10503, 0, 0, 0, 17206, 92063, 31898, 0, 0};
			testDone = "Loadfactor 0.40";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table3.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 4:
			int[] table4 = {50097, 69536, 27993, 0, 6207, 0, 46048, 38670, 58749, 2165, 81968, 24118, 50485, 15158, 71506, 49252, 58509, 0, 0, 25366, 45242, 78757, 115, 0, 92572, 72710, 75470, 8742, 0, 0, 90992, 0, 7294, 79730, 33704, 24353, 0, 0, 93923, 0, 68710, 0, 0, 67810, 7963, 0, 0, 0, 17383, 0, 0, 0, 0, 76980, 78137, 0, 43235, 0, 48563, 54173, 52718, 0, 59440, 54496, 86993, 0, 0, 57595, 0, 5786, 25505, 0, 99897, 0, 61165, 90039, 0, 18579, 20364, 97485, 18297, 9887, 0, 38874, 40802, 16508, 0, 0, 34802, 0, 57723, 5529, 0, 0, 0, 0, 0, 56633, 0, 0, 0, 0, 0, 25816, 0, 20039, 89317, 0, 0, 0, 60170, 0, 0, 0, 0, 26834, 2339, 51671, 0, 0, 18165, 0, 25774, 94467, 48761, 0, 0, 0, 0, 0, 26567, 93993, 0, 88581, 61608, 0, 31860, 46056, 73588, 70157, 0, 0, 61222, 0, 30709, 66173, 0, 0, 0, 56426, 0, 57838, 48220, 22581, 53617, 0, 0, 93673, 64697, 86782, 823, 25196, 0, 32665, 50737, 93222, 0, 0, 18230, 15862, 88527, 75017, 27929, 23009, 6290, 0, 0, 32881, 0, 55654, 25892, 9614, 86790, 38776, 0, 69986, 50019, 0, 0, 79364, 1603, 1772, 0, 27010, 94839, 24296, 13002, 99150, 13919, 11458, 3457, 74539, 15813, 0, 2714, 0, 0, 0, 0, 0, 38699, 74080, 66953, 0, 34809, 0, 0, 66003, 86397, 25214, 0, 61712, 40421, 59141, 0, 0, 0, 0, 0, 94570, 0, 0, 0, 0, 0, 0, 0, 0, 0, 965, 79449, 0, 69867, 58210, 44272, 87591, 0, 41901, 0, 82408, 76604, 0, 37382, 99873, 63414, 0, 35699, 86591, 76093, 0, 85553, 0, 24676, 76890, 0, 95301, 98529, 0, 0, 3491, 57473, 0, 67308, 26645, 0, 0, 0, 0, 0, 0, 0, 57784, 81711, 0, 0, 0, 0, 88730, 78802, 62786, 26821, 19253, 48566, 0, 0, 0, 0, 81649, 83549, 72513, 0, 26967, 89743, 95274, 96088, 67735, 66676, 46426, 14240, 0, 63756, 33289, 6279, 0, 39796, 98353, 35114, 0, 26449, 95314, 0, 0, 33048, 0, 57122, 72241, 71912, 0, 20775, 0, 99670, 16231, 0, 61020, 0, 66421, 63458, 60575, 74275, 0, 0, 0, 0, 0, 0, 29108, 0, 0, 93229, 0, 65222, 0, 0, 0, 0, 78697, 7837, 96839, 22082, 93181, 9491, 6263, 0, 50737, 25694, 0, 22220, 68831, 34243, 30250, 72012, 94637, 11069, 10192, 17813, 69745, 56153, 0, 0, 0, 0, 0, 76453, 95439, 22052, 80033, 42144, 77226, 58098, 92923, 0, 0, 0, 0, 0, 948, 80152, 28904, 74207, 41103, 62406, 41480, 75224, 0, 92661, 0, 50271, 98117, 29537, 8237, 93265, 0, 0, 0, 0, 0, 50872, 80628, 0, 30120, 38671, 0, 23142, 3493, 85113, 0, 47336, 0, 0, 0, 0, 0, 38012, 80753, 0, 17710, 31669, 72164, 30678, 0, 5452, 82087, 0, 0, 0, 0, 0, 0, 61910, 15047, 46679, 13311, 91288, 13947, 85689, 21932, 25924, 77710, 0, 24481, 11400, 0, 95903, 0, 59207, 63448, 83480, 14932, 84728, 90502, 27685, 92480, 81048, 48631, 50914, 49651, 0, 0, 92446, 0, 0, 23602, 0, 63810, 31256, 66132, 23510, 14659, 0, 0, 20988, 18779, 59755, 2952, 86530, 22493, 29186, 65970, 63868, 0};
			testDone = "Loadfactor 0.60";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table4.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
		case 5:
			int[] table5 = {25229, 0, 52150, 24605, 95591, 81901, 51451, 53382, 12796, 87907, 35944, 69552, 68645, 59322, 23739, 43917, 57158, 88850, 11190, 173, 74430, 82283, 14644, 5943, 74854, 89596, 15269, 43750, 2338, 29598, 23303, 62617, 61382, 24868, 38351, 87599, 92168, 41911, 45720, 69361, 66280, 87845, 55288, 0, 0, 49388, 59034, 92910, 42489, 72496, 56107, 26648, 0, 71771, 70200, 0, 51627, 27544, 62978, 0, 2833, 75570, 29470, 5206, 0, 19326, 46554, 4869, 44542, 98151, 34067, 0, 59698, 0, 0, 26863, 86889, 73499, 72440, 25923, 63318, 9025, 90623, 57669, 48609, 19403, 84489, 0, 0, 38215, 5920, 58729, 32673, 70844, 38160, 45662, 77991, 60899, 0, 68777, 78100, 69347, 47791, 0, 0, 44192, 37569, 52698, 82729, 43059, 84600, 65284, 0, 33591, 36170, 21105, 0, 37322, 54712, 52333, 58308, 1526, 0, 49137, 66456, 80508, 45111, 0, 0, 82918, 33319, 47995, 91408, 94874, 62909, 70139, 40245, 20030, 51595, 67740, 65733, 87218, 18322, 588, 27107, 63857, 97217, 34858, 46114, 0, 26742, 30485, 11838, 4341, 51368, 0, 0, 61278, 30301, 14406, 75674, 26660, 7954, 21201, 43810, 82520, 64314, 18677, 72111, 15864, 29819, 45738, 56742, 6880, 18985, 81762, 67763, 57122, 9804, 53052, 19101, 99887, 71784, 84625, 45395, 3850, 24529, 28357, 89513, 27999, 90867, 72068, 75749, 10978, 93801, 35768, 68022, 81233, 34939, 91069, 89827, 55625, 83536, 97712, 0, 53709, 99956, 37135, 8251, 92450, 15075, 37232, 79482, 0, 88038, 24685, 9359, 15946, 68803, 11698, 67289, 0, 99005, 22476, 42658, 26930, 94949, 91454, 7732, 78879, 69915, 20090, 19295, 25512, 69455, 61901, 23383, 34126, 12995, 0, 36168, 77801, 57817, 52546, 35842, 44316, 27356, 46735, 15334, 28605, 74777, 21522, 13710, 43349, 84891, 87618, 65340, 57407, 52767, 49099, 80197, 24993, 15732, 8685, 96131, 99873, 56984, 18700, 24486, 38320, 0, 59192, 44163, 82156, 9992, 88943, 0, 78948, 46904, 7300, 89376, 40264, 13751, 85217, 5767, 60856, 44583, 72428, 0, 21765, 81945, 64308, 0, 78535, 34396, 16790, 31386, 92082, 20513, 30602, 47229, 94248, 31749, 87130, 51910, 92650, 19275, 7363, 17152, 10156, 0, 22679, 50214, 23202, 0, 91617, 0, 10075, 72933, 0, 57813, 45450, 31524, 46152, 10852, 0, 92594, 73144, 2273, 30416, 56704, 7142, 84327, 46640, 16648, 22909, 53077, 96698, 82586, 10926, 30878, 80262, 66883, 93258, 12474, 11918, 33198, 68443, 63701, 69063, 21501, 0, 0, 54628, 70938, 40851, 84781, 52384, 75852, 12110, 67885, 58938, 26771, 94836, 84413, 61002, 7804, 32854, 87303, 89126, 44629, 41126, 93330, 19771, 23154, 52829, 14898, 16508, 27921, 84740, 81557, 98027, 81459, 30186, 18441, 95817, 1043, 40471, 23137, 17518, 0, 35488, 9920, 63374, 23768, 92121, 70885, 52622, 80101, 0, 0, 4299, 0, 99022, 10286, 82629, 49656, 42905, 73571, 61046, 0, 78325, 86697, 80836, 91508, 21624, 12554, 80511, 90155, 16610, 24988, 50238, 13803, 99332, 98405, 21991, 64874, 89302, 85754, 40571, 94280, 92599, 50162, 73249, 48836, 91429, 27251, 6470, 0, 12036, 0, 46963, 52244, 8224, 0, 91516, 70781, 71327, 73778, 54813, 35084, 79097, 63558, 0, 11182, 25768, 82415, 81478, 8677, 33487, 58492, 26710, 42347, 31306, 85671, 5805, 56131, 2109, 54565, 97876, 88174, 40126, 5042, 41424, 59592, 32113, 17387, 85761, 76759, 0, 55366, 94061, 47172, 59676, 23819, 28945, 22314, 39809, 5799, 57822, 97460, 18532, 0, 99691, 17984, 83127, 86934, 97723, 64488, 0};
			testDone = "Loadfactor 0.90";
			for(int i = 0; i < numTests; i++){
				resetVariables();
				tmpTable = table5.clone();
				x = (int) ((Math.random() * 30000)+1);
				if(testToDo == 0){
					linearProbing(hashFunction(x, tmpTable.length), tmpTable);
				}else if(testToDo == 1){
					linearProbingMod1(hashFunction(x, tmpTable.length), tmpTable);
				}else{
					linearProbingMod2(x, this.c, tmpTable);
				}
				
				if(numberRehash>maxRehash) maxRehash = numberRehash;
				if(probesDone>maxCol) maxCol = probesDone;
				if(longestCollChain>maxChain) maxChain = longestCollChain;
				if(insertionCollEncounters != 0) numInsertColl++;
				avgCol += probesDone;
				avgRehash += numberRehash;
				avgChain += longestCollChain;
			}
			break;
			
			
			
		}
		System.out.println("Test: " + testDone);
		System.out.println("Max length of collision chains: " + maxChain);
		System.out.println("Average length of collision chains: " + (avgChain/numTests));
		System.out.println("Number of insertions that encountered collisions: " + numInsertColl);
		System.out.println("Max number of collisions: " + avgCol);
		System.out.println("max number of rehashes: " + maxRehash);
		System.out.println("Average number of rehashes: " + (avgRehash/numTests));
	}

}