package laboration2;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Laboration2 {
	private int[] hashArray;
	int ld = 0, lu = 0;

	public int linearProbing(int hx, int[] hashArray) {
		int probe = hx % hashArray.length;
		int probesDone = 0;
		while (true) {
			if (hashArray[probe] == 0) {
				System.out.println("Number of probes: " + probesDone);
				return probe;
			}
			probe++;
			if (probe == hashArray.length)
				probe = 0;

			probesDone++;

		}
	}

	public int linearProbingMod1(int hx, int[] hashArray) {
		int probe = hx % hashArray.length;
		int probesDone = 0;
		while (true) {
			if (hashArray[probe] == 0) {
				System.out.println("Number of probes: " + probesDone);
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
			if (probe == hashArray.length)
				probe = 0;
			if (probe == -1)
				probe = hashArray.length - 1;

			probesDone++;

		}
	}

	public int linearProbingMod2(int hx, int c) {
		int probe = hx % hashArray.length;
		int probesDone = 0;
		int j;
		while (true) {
			if (hashArray[probe] == 0) {
				j = probe;
				if (Math.abs(j - hx) <= c) {
					System.out.println("[j] <- x");
					return j;
				} else {
					int _j;
					int y = 0;
					for (_j = hx; _j <= (hx + c); _j++) {
						if (Math.abs(j - hashFunction(y)) <= c && y != 0) {
							if (_j > hashArray.length - 1) {
								//_j = (_j - (hashArray.length - 1)) - 1;
								_j = hashArray.length;
							}
							y = hashArray[_j];
							hashArray[j] = y;
							System.out.println("[j] <- y");
							return _j;
						}
					}
					int reHx;
					for (int i = 0; i < hashArray.length; i++) {
						reHx = hashArray[i];
						if (reHx != 0)
							System.out.println("Rehashing: " + reHx);
							insert(reHx);
					}

					return 0;

				}
			}
			probe++;
			if (probe == hashArray.length)
				probe = 0;
			if (probe == -1)
				probe = hashArray.length - 1;

			probesDone++;

		}

	}

	public static void main(String[] args) {
		Laboration2 lab = new Laboration2();
		int[] listElement = { 11, 65, 33, 92, 21, 21, 21, 87, 39, 19 };
		lab.createStorage(10);
		for (int i = 0; i < 10; i++) {
			lab.insert(listElement[i]);
		}

	}

	public void insert(int x) { // the n will be hashed before inserting, later.
		int hashedValue = hashFunction(x);
		int index = this.linearProbingMod2(hashedValue, 7);
		if (index != 0)
			hashArray[index] = x;
		System.out.println(Arrays.toString(hashArray));
	}

	public int hashFunction(int key) {
		return key * (key + 3) % hashArray.length;

	}

	public int[] createStorage(int k) {
		hashArray = new int[k];
		return hashArray;
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
