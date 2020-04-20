import java.util.*;

public class Hand {
	public Card [] hand;
	public int [] groups;
	public int [] kickers;
	private final int size = 5;

	public Hand(Card[] h) {
		this.hand = new Card[this.size];
		this.groups = new int[size];
		this.kickers = new int[size];

		for(int i = 0; i < this.size; i++) {
			hand[i] = new Card(h[i].getRank(), h[i].getSuit());
			groups[i] = 0;
			kickers[i] = 0;
		}		
	}

	public void sort() {
		int i, j;
		Card key;
		for(i = 1; i < size; i++) {
			key = hand[i];
			j = i-1;
			while(j >= 0 && hand[j].compareTo(key) >= 0) {
				hand[j+1] = hand[j];
				j--;
			}
			hand[j+1] = key;
		}
	}

	public void print() {
		for(int i = 0; i < size; i++)
			System.out.println(hand[i]);
	}

	public int evaluate() {
		this.sort();
		int handValue = this.hasGroup();

		if(handValue != 1)			
			return handValue;

		if(this.straight() && this.flush()) {
			if(hand[size-1].getRank() == 14)
				return 10;
			else
				return 9;
		}

		else if(this.flush())		return 6;
		else if(this.straight()) 	return 5;

		return 1;
	}

	//return group value (pairs, three of a kind, etc.)
	public int hasGroup() {
		int[] ranks = new int[15];
		//group 1 and 2
		int g1 = 0, g2 = 0;
		for(int i = 0; i < 15; i++)
			ranks[i] = 0;

		for(int i = 0; i < size; i++) {
			int curr = hand[i].getRank();
			++ranks[curr];
			if(ranks[curr] > g1) {
				g1 = ranks[curr];
				groups[g1] = curr;
			}
			else if(ranks[curr] > g2) {
				g2 = ranks[curr];
				if(g2 == g1)
					groups[0] = curr;
				else
					groups[g2] = curr;
			}
		}
		for(int i = 14, j = size-1; i >= 0; i--) {
			if(ranks[i] == 1) {
				kickers[j] = i;
				if(--j < 0)	
					break;
			}

		}

		int g = g1 + g2;
		switch(g) {
			case 2:
				return 1;
			case 3:
				return 2;
			case 4:
				if(g1 == 2)
					return 3;
				return 4;
			case 5:
				if(g1 == 2 || g1 == 3)
					return 7;
				return 8;
		}
		return 0;
	}

	public boolean straight() {
		for(int i = 1; i < 5; i++)
			if(hand[i].getRank() - hand[i-1].getRank() != 1)
				return false;
		return true;
	}

	public boolean flush() {
		int suit = hand[0].getSuit();
		for(int i = 1; i < size; i++)
			if(hand[i].getSuit() != suit)
				return false;
		return true;
	}

	public int compareTo(Hand h) {
		int thisValue = this.evaluate();
		int hValue = h.evaluate();

		if(thisValue < hValue)	return -1;
		if(thisValue > hValue)	return 1;

		switch(thisValue) {
			case 2:
				if(this.groups[2] > h.groups[2])		return 1;
				else if(this.groups[2] < h.groups[2])	return -1;
				return this.kickerComp(h);

			case 3:
				int firstP = this.groups[0], secondP = this.groups[2];
				int hfirstP = h.groups[0], hsecondP = h.groups[2];

				int thisMin = firstP < secondP ? firstP : secondP;
				int thisMax = thisMin == firstP ? secondP : firstP;
				int hMin = hfirstP < hsecondP ? hfirstP : hsecondP;
				int hMax = hMin == hfirstP ? hsecondP : hfirstP;

				if(thisMax < hMax)			return -1;
				else if(thisMax > hMax)		return 1;
				else if(thisMin < hMin)		return -1;
				else if(thisMin > hMin)		return 1;
				return this.kickerComp(h);

			case 4:
				if(this.groups[3] > h.groups[3])		return 1;
				else if(this.groups[3] < h.groups[3])	return -1;
				return this.kickerComp(h);

			case 7:
				int pair = this.groups[0], triple = this.groups[3];
				int hpair = h.groups[0], htriple = h.groups[3];

				if(triple < htriple)		return -1;
				else if(triple > htriple)	return 1;
				else if(pair < hpair)		return -1;
				else if(pair > hpair)		return 1;
				return 0;

			case 8:
				int four = this.groups[4], hfour = h.groups[4];
				if(four < hfour)			return -1;
				else if(four > hfour)		return 1;
				return this.kickerComp(h);

			default:
				return this.kickerComp(h);
		}
	}

	public int kickerComp(Hand h) {
		int i = size-1;
		while(this.kickers[i] == h.kickers[i])  {
			i--;
			if(i < 0)	return 0;
		}
		return Integer.compare(this.kickers[i], h.kickers[i]);
	}

}
	/*
		NOTE: 	1 = HighCard
				2 = Pair
				3 = TwoPair
				4 = Triple
				5 = Straight
				6 = Flush
				7 = FullHouse
				8 = Quad
				9 = StraightFlush
				10 = RoyalFlush	
	*/