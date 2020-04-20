public class Card {

	private int rank;
	private int suit;

	public Card(int r, int s) {
		this.rank = r;
		this.suit = s;
	}

	public String toString() {
		String[] ranks = {null, null, "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K ", "A "};
		String[] suits = {" of Clubs", " of Diamonds", " of Hearts", " of Spades"};

		return ranks[this.rank] + suits[this.suit];
	}

	public int getRank() {
		return this.rank;
	}

	public int getSuit() {
		return this.suit;
	}

	public int getID(){
		if (rank == 2)
			return rank + suit - 2;
		if (rank == 3)
			return rank + suit + 1;
		if (rank == 4)
			return rank + suit + 4;
		if (rank == 5)
			return rank + suit + 7;
		if (rank == 6)
			return rank + suit + 10;
		if (rank == 7)
			return rank + suit + 13;
		if (rank == 8)
			return rank + suit + 16;
		if (rank == 9)
			return rank + suit + 19;
		if (rank == 10)
			return rank + suit + 22;
		if (rank == 11)
			return rank + suit + 25;
		if (rank == 12)
			return rank + suit + 28;
		if (rank == 13)
			return rank + suit + 31;
		if (rank == 14)
			return rank + suit + 33;
		else
			return -1;
	}
	
	public int compareTo(Card c) {
		if(this.rank < c.rank)
			return -1;
		else if(this.rank > c.rank)
			return 1;
		return 0;
	}
}
