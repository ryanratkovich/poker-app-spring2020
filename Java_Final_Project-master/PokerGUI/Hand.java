import java.util.*;

public class Hand {
	public Card [] hand;
	public int [] val;
	private final int size = 5;

	public Hand(Card[] h) {
		this.hand = new Card[this.size];
		this.val = new int[6];

		for(int i = 0; i < this.size; i++)
			hand[i] = new Card(h[i].getRank(), h[i].getSuit());	

		this.sort();
		int[] ranks = new int[15];

		for(int i = 0; i < 15; i++)
			ranks[i] = 0;
		for(int i = 0; i < size; i++)
			ranks[hand[i].getRank()]++;

		int group1 = 1, group2 = 1;
		int small = 0, large = 0;

		for(int i = 14; i > 0; i--) {
			if(ranks[i] > group1) {
				if(group1 != 1) {
					group2 = group1;
					small = large;
				}
				group1 = ranks[i];
				large = i;
			}
			else if(ranks[i] > group2) {
				group2 = ranks[i];
				small = i;
			}
		}

		int[] priority = new int[5];
		int index = 0;
		for(int i = 14; i >= 2; i--)
			if(ranks[i] == 1)
				priority[index] = i;

		if(group1 == 1) {
			val[0] = 1;
			for(int i = 1; i < 6; i++)
				val[i] = priority[i-1];
		}
		if(group1 == 2 && group2 == 1) {
			val[0] = 2;
			val[1] = large;
			val[2] = priority[0];
			val[3] = priority[1];
			val[4] = priority[2];
		}
		if(group1 == 2 && group2 == 2) {
			val[0] = 3;
			val[1] = large > small ? large : small;
			val[2] = large < small ? large : small;
			val[3] = priority[0];
		}
		if(group1 == 3 && group2 != 2) {
			val[0] = 4;
		}
		if(this.straight()) {
			val[0] = 5;
			val[1] = priority[0];
		}
		if(this.flush()) {
			val[0] = 6;
			for(int i = 1; i < 6; i++)
				val[i] = priority[i-1];
		}
		if(group1 == 3 && group2 == 2) {
			val[0] = 7;
			val[1] = large;
			val[2] = small;
		}
		if(group1 == 4) {
			val[0] = 8;
			val[1] = large;
			val[2] = priority[0];
		}
		if(this.straight() && this.flush()) {
			val[0] = 9;
			val[1] = priority[0];
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

	public String print() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < size; i++)
			s.append(hand[i].toString() + "\n");
		// 	System.out.println(hand[i]);
		return s.toString();
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
		for(int i = 0; i < 6; i++) {
			if(this.val[i] > h.val[i])
				return 1;
			else if(this.val[i] != h.val[i])
				return -1;
		}
		return 0;
	}

	public String toString() {
		switch(val[0]) {
			case 1:		return "High Card";
			case 2:		return "Pair";
			case 3:		return "Two Pair";
			case 4:		return "Three of a Kind";
			case 5:		return "Straight";
			case 6:		return "Flush";
			case 7:		return "Full House";
			case 8:		return "Four of a Kind";
			case 9:		
						if(val[1] < 14) 
							return "Straight Flush";
						else
							return "...a Royal Flush??!!??!!";
		}
		return "";
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
