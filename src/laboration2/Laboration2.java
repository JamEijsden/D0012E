package laboration2;

public class Laboration2 {

	public int linearProbing(int hx, int[] hashArray) {
		int probe = hx % hashArray.length;
		int ld = 0,lu = 0;
		int probesDone = 0;
		while(true) {
			if (hashArray[probe] == 0){ 
				System.out.println("Number of probes: " + probesDone);
				return probe; 
			}
			if(ld <= lu) probe++; else probe--;
			probesDone++;
		}
	}

}
