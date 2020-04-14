class Card {
	private int rank;
	private int suit;
	public Card(int r, int s) {
		this.rank = r;
		this.suit = s;
	}
	public String toString() {
		String[] ranks = {null, null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

		return ranks[this.rank] + suits[this.suit];
	}
	public int getRank() {
		return this.rank;
	}
	public int getSuit() {
		return this.suit;
	}
	public int compareTo(Card c) {
		if(this.rank < c.rank)
			return -1;
		else if(this.rank > c.rank)
			return 1;
		// else if(this.suit < c.suit)
		// 	return -1;
		// else if(this.suit > c.suit)
		// 	return 1;
		return 0;
	}
}
