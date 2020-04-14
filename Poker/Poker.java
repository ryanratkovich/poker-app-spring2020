class Poker {
	public static void main(String[] args) {
		Deck d = new Deck();
		d.shuffle();
		Card[] h = new Card[5];
		h[0] = new Card(14, 2);
		h[1] = new Card(13, 2);
		h[2] = new Card(12, 2);
		h[3] = new Card(11, 2);
		h[4] = new Card(10, 2);

		Hand a = new Hand(h);

		Card[] h1 = new Card[5];

		h1[0] = new Card(10, 2);
		h1[1] = new Card(10, 2);
		h1[2] = new Card(10, 2);
		h1[3] = new Card(10, 1);
		h1[4] = new Card(7, 2);

		Hand b = new Hand(h1);

		if(a.compareTo(b) == 1)
			System.out.println("8 beats 3");
		else if(a.compareTo(b) == -1)
			System.out.println("3 beats 8");
		else
			System.out.println("same hands, its a draw");


	}
}