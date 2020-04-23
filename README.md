# Java_Final_Project
All code and assets for a Java Poker game.

- Using the GUI:
	- When it is a players turn, the players buttons will be enabled
	  and will be usable on a players turn.

	- To see a players cards, scroll over them to flip them over. Otherwise
	  they will stay hidden. NOTE: IT IS AGAINST THE RULES TO LOOK AT YOUR
	  OPPONENTS CARDS.

	- The game dialog box on the right-hand side of the GUI will display 
	  specifics about each players move as well as the winning player at 
	  the end of a round.

	- At the end of a round, there will be an option to play again. This
	  will re-shuffle and re-deal the cards. The game continues until either player loses all of their money.

- How to Play:
	- Player 1 (bottom of the sceen) starts the game.

	- Player 1 starts the game by choosing to Check, Raise, or Fold.

	- The game continues by taking turns between players one-by-one. 
	  BOTH PLAYERS MUST CHECK TO END A ROUND OF BETTING.

	- Players may raise (and call) in increments of $0.50 and 
	  both players start with $10. Player 1 is Small Blind and Player 2 
	  is Big Blind.

	- A round is over when all cards are dealt and betting is over, or 
	  when a player folds.

- Separation of Work:
	- Ryan:
		- Game GUI, Game Logic, Readme, modifications to OOP classes.
	
	- Chris
		- OOP classes, Game Logic, Readme, modifications to Game GUI.

- Known Bug(s):
	- After clicking "Play Again" after a round, there will sometimes be a
	  remaining fourth or fifth card that will show up from the previous
	  round. To remove the card from the screen, simply re-size the window. Despite our best efforts, (tried revalidate() and invalidate()) we
	  were unable to fix the bug. 