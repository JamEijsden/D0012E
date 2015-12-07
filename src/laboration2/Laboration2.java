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
		
		System.out.println(x);
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
		//lab.testInsertion(5, 2, 10000);
		System.out.println(Arrays.toString(lab.genHashTable(500, 0.4)));
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
			int n = (int) ((Math.random() * 30000)+1);
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
			int[] table3 = {0, 19895, 0, 0, 9089, 0, 0, 0, 5195, 6928, 10998, 0, 0, 0, 0, 0, 0, 9759, 23406, 0, 8, 19479, 15223, 14719, 29456, 0, 0, 0, 11949, 0, 0, 0, 0, 0, 0, 5450, 0, 9393, 25665, 0, 14936, 0, 0, 0, 0, 9598, 16913, 0, 0, 12279, 17027, 19304, 12764, 17330, 0, 0, 12788, 234, 0, 0, 0, 0, 0, 0, 13935, 19202, 0, 12523, 28358, 16804, 0, 0, 0, 2523, 0, 0, 0, 0, 0, 13938, 17869, 15299, 7874, 3175, 0, 9231, 6953, 0, 0, 839, 0, 24508, 16521, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14582, 11194, 0, 0, 0, 0, 0, 24325, 0, 0, 0, 0, 0, 10424, 0, 0, 18450, 24738, 7829, 10128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4503, 24307, 0, 0, 0, 0, 0, 2555, 0, 0, 0, 0, 11314, 10014, 21655, 28036, 9190, 0, 25709, 0, 0, 0, 0, 4736, 6998, 26920, 4915, 496, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13402, 0, 10692, 0, 10056, 0, 0, 0, 0, 0, 6813, 18807, 9987, 11123, 3190, 0, 25201, 829, 6073, 13558, 2616, 27991, 15212, 2767, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14825, 9004, 0, 0, 0, 16591, 0, 0, 0, 3423, 18867, 0, 28372, 0, 0, 0, 18004, 21470, 0, 0, 0, 0, 16419, 0, 0, 0, 15549, 0, 16266, 0, 0, 21782, 914, 0, 0, 0, 0, 139, 20680, 3905, 0, 27608, 28366, 4235, 0, 0, 21636, 18098, 12293, 16869, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9221, 0, 0, 29860, 0, 0, 0, 0, 0, 0, 29650, 14790, 0, 22415, 9974, 0, 0, 0, 13036, 12114, 17731, 0, 17461, 24967, 0, 0, 20601, 6047, 0, 0, 0, 0, 5792, 16516, 24890, 10658, 21050, 6343, 0, 9680, 0, 0, 0, 0, 0, 0, 0, 11681, 16610, 0, 0, 366, 0, 0, 0, 21453, 12789, 0, 13789, 1267, 14868, 0, 0, 0, 0, 0, 0, 17785, 0, 18629, 21562, 0, 0, 0, 3943, 0, 11648, 22114, 26627, 1105, 0, 14938, 2842, 0, 0, 0, 19669, 15641, 0, 0, 22113, 25497, 0, 0, 8951, 16238, 0, 0, 0, 0, 0, 0, 14830, 85, 0, 24715, 27116, 18558, 0, 0, 0, 0, 0, 0, 0, 5375, 18230, 19762, 0, 0, 3641, 0, 0, 2402, 0, 0, 0, 17018, 0, 1323, 0, 0, 0, 18717, 3587, 0, 0, 0, 27015, 0, 7087, 5676, 8011, 29007, 2446, 0, 11075, 15299, 0, 0, 0, 2106, 0, 21594, 0, 4611, 0, 0, 16692, 0, 10264, 9891, 0, 0, 0, 0, 0, 0, 0, 5354, 10041, 18501, 0, 28426, 7625, 10823, 28098, 0, 0, 0, 0, 0, 0, 0, 24435, 0, 28327, 0, 8054, 0, 0, 0, 2133, 0, 0, 0, 0, 0, 4558, 27889, 0, 25389, 0, 0, 0, 0, 0, 26234, 3996, 5203, 0, 0, 10853, 20933, 0, 0, 3349, 0, 16669, 24031, 0, 27015, 3724, 28130, 0, 0, 0};
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
			int[] table4 = {5452, 0, 19023, 14579, 5501, 0, 0, 7826, 28900, 20492, 27277, 17439, 17053, 7527, 0, 0, 2732, 0, 19101, 26892, 6068, 9637, 0, 0, 15001, 0, 7788, 24258, 0, 0, 7263, 0, 26556, 27351, 0, 19015, 15185, 0, 15093, 0, 29362, 22027, 16440, 0, 0, 0, 21439, 26459, 21327, 28332, 17652, 0, 8775, 2395, 8931, 22811, 14820, 23711, 1929, 0, 0, 0, 17748, 22969, 3494, 0, 18914, 17480, 26043, 25362, 6240, 12811, 29584, 12139, 16673, 0, 0, 0, 0, 0, 0, 0, 0, 13284, 0, 1207, 0, 7350, 7600, 0, 2450, 25932, 0, 16722, 0, 0, 0, 0, 18402, 0, 0, 0, 0, 0, 0, 9135, 0, 16308, 0, 0, 0, 0, 29306, 12609, 0, 22277, 0, 29238, 2294, 0, 7253, 26546, 27648, 14944, 0, 15411, 10397, 0, 0, 0, 0, 10686, 6995, 20142, 6023, 0, 0, 0, 3804, 0, 0, 21871, 2092, 25061, 898, 8479, 23559, 28705, 16164, 0, 2751, 8183, 0, 11266, 18579, 24134, 29675, 25622, 28873, 0, 0, 7166, 5693, 11494, 5114, 8346, 17000, 16756, 28642, 1737, 19279, 0, 0, 20157, 17006, 14354, 8604, 0, 0, 21340, 14923, 21546, 11252, 1486, 26183, 12900, 4911, 0, 0, 0, 0, 0, 434, 27398, 4945, 28134, 25593, 8859, 0, 0, 0, 0, 0, 0, 0, 29274, 21831, 29216, 17205, 5782, 29991, 25134, 17207, 0, 3517, 1414, 15901, 0, 0, 1455, 0, 0, 0, 13364, 16124, 4232, 12349, 14989, 29793, 2474, 18143, 21778, 9290, 21319, 13073, 22295, 14292, 0, 2805, 0, 26640, 0, 4482, 1048, 0, 16364, 28417, 28293, 10392, 5083, 13099, 9237, 26778, 3718, 23572, 6830, 0, 0, 0, 0, 0, 0, 5720, 11482, 0, 0, 14856, 0, 0, 20743, 14798, 0, 12387, 7225, 0, 23391, 13925, 27979, 16640, 19820, 17219, 0, 4992, 2417, 0, 7741, 6429, 24315, 0, 0, 0, 0, 0, 9037, 9979, 0, 0, 0, 24801, 18822, 0, 0, 18101, 10351, 23341, 22976, 13611, 0, 0, 0, 16849, 13080, 16448, 26015, 16556, 0, 0, 0, 13900, 6370, 12775, 10347, 9023, 22049, 0, 0, 28853, 16, 15098, 0, 0, 14159, 16991, 0, 22714, 6695, 17197, 251, 25765, 0, 0, 0, 19350, 0, 0, 0, 0, 0, 24986, 0, 19166, 5068, 0, 0, 0, 0, 0, 7306, 0, 2592, 0, 482, 0, 0, 21931, 4330, 133, 12295, 3331, 0, 21142, 26271, 5117, 0, 0, 0, 0, 0, 0, 0, 0, 29750, 1857, 0, 0, 24771, 9380, 10993, 25010, 29816, 0, 0, 0, 14500, 0, 9167, 5465, 0, 12635, 13350, 0, 18734, 0, 1011, 28733, 7840, 15106, 17392, 19019, 4844, 0, 21114, 17602, 10184, 6095, 24199, 15077, 27106, 6861, 26574, 10942, 73, 17458, 2613, 26833, 0, 23217, 22810, 0, 6734, 20066, 18889, 18119, 14319, 2277, 5593, 0, 0, 0, 16968, 22404, 6981, 0, 29251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29159, 0, 4652, 0, 0, 0, 18929, 3905, 0, 0, 15917, 0, 12663, 2194, 18413, 0, 28383, 788, 10291, 4936, 19575, 0, 0, 21766, 12836, 0, 0, 0, 0, 0, 11462, 8109, 3336, 22472, 26126, 19694, 26630, 1517, 0, 26453, 22503, 0, 0, 0, 0, 19096, 22550};
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
			int[] table5 = {19826, 29850, 2648, 2188, 21808, 7826, 23399, 16065, 1044, 0, 3467, 11011, 10731, 3971, 28285, 28064, 22908, 7264, 28934, 0, 27575, 25781, 0, 19036, 17824, 27412, 22933, 8204, 8352, 0, 5630, 20971, 21830, 14291, 27773, 5430, 24365, 26306, 9823, 14726, 7806, 29471, 1753, 8618, 22771, 4765, 27040, 0, 12136, 17622, 14482, 3751, 20277, 11093, 18889, 11748, 0, 25682, 3031, 20907, 2425, 0, 19205, 9711, 20332, 13206, 19509, 6109, 18428, 14441, 9269, 4845, 19664, 18532, 18137, 17266, 9018, 29638, 18144, 16911, 20218, 12904, 7422, 10738, 22265, 0, 2453, 14606, 6808, 12232, 20507, 22522, 9836, 24439, 27448, 22304, 23248, 3597, 24284, 25615, 23641, 2155, 18691, 7784, 7155, 1183, 15356, 12555, 20342, 12845, 11191, 28718, 27903, 13765, 18440, 19671, 18671, 15066, 339, 13187, 11424, 13406, 10282, 0, 12038, 23114, 6466, 27012, 0, 2519, 9694, 5849, 6360, 27658, 12362, 28157, 27413, 13931, 11768, 0, 20609, 20772, 10595, 5985, 0, 6103, 21887, 990, 15004, 16261, 14972, 646, 20067, 13154, 2585, 24305, 19335, 23670, 17962, 15356, 21532, 16842, 3873, 27563, 21363, 1893, 3431, 21860, 3060, 5142, 5092, 11066, 22772, 5759, 11517, 26834, 21725, 29571, 23895, 22224, 17611, 26513, 19988, 24506, 0, 20915, 5437, 16237, 15719, 24572, 13912, 28280, 1341, 504, 2357, 26216, 1057, 478, 22975, 21613, 18605, 11674, 23650, 21772, 22446, 17415, 3293, 5947, 0, 3581, 17995, 12598, 21638, 0, 18042, 944, 27770, 26999, 14644, 1058, 1204, 1277, 8743, 9812, 25986, 29266, 0, 25319, 2189, 23146, 16550, 29995, 24340, 13007, 5106, 2036, 29001, 0, 10363, 16989, 4515, 10809, 8226, 15124, 6485, 18698, 16106, 16576, 0, 25467, 0, 24640, 11659, 23801, 19012, 28230, 16622, 0, 15079, 17290, 10055, 696, 18246, 9982, 27740, 2877, 12705, 7590, 14140, 25929, 20694, 22513, 17129, 18203, 9757, 26209, 28770, 15836, 8274, 7746, 26451, 14508, 0, 29506, 26143, 21791, 10584, 24716, 16611, 15296, 24181, 1797, 20206, 19427, 2239, 2984, 22352, 8400, 9018, 19367, 17984, 0, 0, 1375, 19056, 0, 26998, 22748, 22782, 21325, 26067, 17315, 25466, 4326, 5029, 11302, 1737, 7087, 3307, 19527, 13550, 0, 13547, 1613, 7662, 0, 11841, 14381, 14199, 26712, 27175, 28722, 22658, 1180, 13741, 23646, 4986, 11161, 9216, 26728, 28445, 21337, 16230, 8942, 13689, 2519, 13074, 2777, 5880, 3796, 0, 25652, 8792, 20738, 1198, 0, 23616, 2323, 26059, 26562, 25672, 29092, 19614, 10162, 13375, 22044, 898, 3022, 24688, 6026, 23651, 27324, 6777, 12718, 26040, 18879, 4874, 6958, 7936, 5251, 26203, 0, 2089, 29974, 4463, 13193, 21248, 11374, 0, 11605, 13641, 19462, 16927, 2597, 14313, 21567, 17017, 8726, 29555, 26741, 8777, 24166, 16132, 503, 0, 0, 6107, 0, 0, 6166, 1924, 0, 0, 2117, 14868, 11934, 27988, 24960, 23267, 10025, 0, 23687, 24633, 0, 0, 29191, 8533, 11915, 28221, 8638, 24250, 2988, 21994, 0, 29189, 26119, 16222, 22010, 10676, 4851, 18934, 17207, 5063, 12699, 18368, 0, 4093, 29694, 14111, 27733, 19152, 25596, 27924, 1545, 19429, 0, 9120, 12378, 20063, 26509, 2963, 0, 0, 5815, 13966, 0, 7673, 22515, 28959, 25733, 15430, 27416, 0, 11058, 5086, 11459, 29935, 10005, 22201, 0, 24417, 13081, 27484, 6620, 28128, 7820, 5327, 16262, 6768, 11330, 25953, 29698, 0, 5699, 28820, 0, 27490, 0, 1867, 28624};
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