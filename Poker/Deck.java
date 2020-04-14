import java.util.*;
class Deck {
	private final int size = 52;
	private Card[] deck = new Card[size];

	public Deck() {
		int i = 0;
		for(int s = 0; s < 4; s++) {
			for(int r = 2; r <= 14; r++) {
				deck[i] = new Card(r, s);
				i++;
			}
		}
	}
	public Card cardAt(int index) {
		return deck[index];
	}
	public void print() {
		for(int i = 0; i < size; i++)
			System.out.println(deck[i]);
	}
	public void shuffle() {
		List<Card> deckList = Arrays.asList(deck);
		Collections.shuffle(deckList);
		deckList.toArray(deck);
	}
}