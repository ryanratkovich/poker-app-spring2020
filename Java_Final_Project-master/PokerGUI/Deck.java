import java.util.*;

public class Deck {

	private final int size = 52;
	private Card[] deck = new Card[size];
	private int actualAmount = 52;

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

	public void shuffle() {
		List<Card> deckList = Arrays.asList(deck);
		Collections.shuffle(deckList, new Random(System.nanoTime()));
		deckList.toArray(deck);
	}

	public Card deal(){
		return deck[--actualAmount];
	}

}