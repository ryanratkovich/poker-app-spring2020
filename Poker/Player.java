import java.util.*;
class Player {
	String name;
	Card [] startCards = new Card[2];
	Hand[] allHands;
	Hand best;

	public Player(String n) {
		name = n;
	}
	public void chooseBestHand() {
		int size = allHands.length;
		best = allHands[0];
		for(int i = 1; i < size; i++)
			if(best.compareTo(allHands[i]) == -1)
				best = allHands[i];
	}
	public Hand getBestHand() {
		return best;
	}
	public void storeHands(Card [] pool, int poolSize) {
		int num = possibleHands(poolSize);
		allHands = new Hand[num];

		int n = 2*Utilities.comb(poolSize, 4);
		chooseFour(pool, poolSize, 0, n);
		chooseThree(pool, poolSize, n, num);

		return;
	}
	public int possibleHands(int n) {
		if(n == 3)		return 1;
		else if(n == 4)	return 6;
		else if(n == 5)	return 20;
		else 			return 0;
	}

	public Hand[] getHands() {
		return allHands;
	}
	public void startCards(Card[] c) {
		startCards[0] = c[0];
		startCards[1] = c[1];
	}
	public void chooseThree(Card[] pool, int poolSize, int start, int end) {
		Card[] temp = new Card[5];
		int index = 0, i = start;
		while(i < end) {
			temp[index++] = startCards[0];
			temp[index++] = startCards[1];
			for(int j = 0; j < poolSize - 2; j++) {
				temp[index++] = pool[j];
				for(int k = j+1; k < poolSize - 1; k++) {
					temp[index++] = pool[k];
					for(int l = k+1; l < poolSize; l++) {
						temp[index] = pool[l];
						allHands[i] = new Hand(temp);
						i++;
					}
					index--;
				}
				index--;
			}
			index = 0;
		}
	}
	public void chooseFour(Card[] pool, int poolSize, int start, int end) {
		Card[] temp1 = new Card[5];
		Card[] temp2 = new Card[5];

		int index1 = 0, i = start;
		while(i < end) {
			temp1[index1] = startCards[0];
			temp2[index1++] = startCards[1];
			for(int j = 0; j < poolSize - 3; j++) {
				temp1[index1] = pool[j];
				temp2[index1++] = pool[j];
				for(int k = j+1; k < poolSize - 2; k++) {
					temp1[index1] = pool[k];
					temp2[index1++] = pool[k];
					for(int l = k+1; l < poolSize - 1; l++) {
						temp1[index1] = pool[l];
						temp2[index1++] = pool[l];
						for(int m = l+1; m < poolSize; m++) {
							temp1[index1] = pool[m];
							temp2[index1++] = pool[m];
							allHands[i++] = new Hand(temp1);
							allHands[i++] = new Hand(temp2);
							index1--;
						}
						index1--;
					}
					index1--;
				}
				index1--;
			}
			index1 = 0;
		}
	}
}
