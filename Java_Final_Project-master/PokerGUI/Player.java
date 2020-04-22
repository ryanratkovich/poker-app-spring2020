import java.util.*;
class Player {
	//stores the player's name (to be displayed in the game)
	private String name;
	//stores the player's two cards
	private Card [] startCards = new Card[2];
	//stores every possible hand that can be made from the player's two cards and the cards in the pool
	private Hand [] allHands;
	//stores the best of all hands
	private Hand best;
	private double cash;

/////// CONSTRUCTORS & SETTER FUCNTIONS ///////
	public Player(String n, int c) {
		name = n;
		cash = c;
	}
	public void setStartCards(Card[] c) {
		startCards[0] = c[0];
		startCards[1] = c[1];
	}
	//stores all of the possible hands into an array of hands
	//used to find the best hand available to the player
	public void storeHands(Card [] pool, int poolSize) {
		int num = possibleHands(poolSize);
		allHands = new Hand[num];

		int n = 2*Utilities.comb(poolSize, 4);
		chooseFour(pool, poolSize, 0, n);
		chooseThree(pool, poolSize, n, num);

		findBestHand();

		return;
	}
	public void bet(double bet) {
		if(bet > cash)
			return;
		cash -= bet;
	}

	public void changeCash(double d){
		cash += d;
	}
/////// ACCESSOR FUNCTIONS ///////
	public Card[] getStartCards(){
		Card[] cards = {startCards[0], startCards[1]};
		return cards;
	}

	public String getName() {
		return name;
	}
	//returns the best of all the possible hands available
	public Hand getBestHand() {
		return best;
	}
	//returns all the possible hands
	public Hand[] getHands() {
		return allHands;
	}
	public double getCash() {
		return cash;
	}

/////// HELPER FUNCTIONS ///////
	//determines the best hand
	private void findBestHand() {
		int size = allHands.length;
		best = allHands[0];
		for(int i = 1; i < size; i++)
			if(best.compareTo(allHands[i]) == -1)
				best = allHands[i];
	}
	//returns the number of possible hands that the player can find given the pool size
	private int possibleHands(int poolSize) {
		if(poolSize == 3)		return 1;
		else if(poolSize == 4)	return 6;
		else if(poolSize == 5)	return 20;
		else 			return 0;
	}
	//creates all possible combinations of hands from BOTH of the player's two starting cards and THREE cards from the pool
	private void chooseThree(Card[] pool, int poolSize, int start, int end) {
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
	//creates all possible combinations of hands from ONE of the player's two starting cards and FOUR cards from the pool
	private void chooseFour(Card[] pool, int poolSize, int start, int end) {
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