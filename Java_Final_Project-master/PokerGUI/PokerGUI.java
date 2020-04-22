import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PokerGUI extends JFrame {
   public static void main(String args[]){ 
      PokerFrame pokerFrame = new PokerFrame(); 
      pokerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pokerFrame.setSize(800, 500);
      pokerFrame.setVisible(true);
   }
}

class PokerFrame extends JFrame{

   //boolean variables for game logic
   private Boolean playing;

   private Boolean firstRound;
   private Boolean secondRound;
   private Boolean finalRound;

   private Boolean p1Turn;
   private Boolean p1hasChecked;
   private Boolean p1hasCalled;
   private Boolean p1hasRaised;
   private Boolean p1hasFolded;

   private Boolean p2Turn;
   private Boolean p2hasChecked;
   private Boolean p2hasCalled;
   private Boolean p2hasRaised;
   private Boolean p2hasFolded;

   private Boolean raising;

   //JTextField to display messages to players
   private JTextArea messageBox;

   //create all JPanels for each part of table
   private JPanel table = new JPanel();
   private JPanel opponentPanel = new JPanel();
   private JPanel cardPanel= new JPanel();
   private JPanel playerPanel= new JPanel();

   //JLabels to portray player 2 cards
   private JLabel[] opponentCards;
   private JTextField opponentCash;

   //JLabels to portray table cards
   private JLabel[] deckCards;
   private JTextField pot;
   private double potAmount;

   //JLabels to portray player 1 cards
   private JLabel[] playerCards;
   private JTextField playerCash;

   //array to access card image files
   private String files[] = { 
      "Images/2C.png", "Images/2D.png", "Images/2H.png", "Images/2S.png", "Images/3C.png", 
      "Images/3D.png", "Images/3H.png", "Images/3S.png", "Images/4C.png", "Images/4D.png", 
      "Images/4H.png", "Images/4S.png", "Images/5C.png", "Images/5D.png", "Images/5H.png", 
      "Images/5S.png", "Images/6C.png", "Images/6D.png", "Images/6H.png", "Images/6S.png", 
      "Images/7C.png", "Images/7D.png", "Images/7H.png", "Images/7S.png", "Images/8C.png", 
      "Images/8D.png", "Images/8H.png", "Images/8S.png", "Images/9C.png", "Images/9D.png", 
      "Images/9H.png", "Images/9S.png", "Images/10C.png", "Images/10D.png", "Images/10H.png", 
      "Images/10S.png", "Images/JC.png", "Images/JD.png", "Images/JH.png", "Images/JS.png",
      "Images/QC.png", "Images/QD.png", "Images/QH.png", "Images/QS.png", "Images/KC.png", 
      "Images/KD.png", "Images/KH.png", "Images/KS.png","Images/AC.png", "Images/AD.png", 
      "Images/AH.png", "Images/AS.png"
   };

   //card to hide cards from players
   private ImageIcon hiddenCard = new ImageIcon(getClass().getResource("Images/gray_back.png"));

   //array to hold images of cards
   private ImageIcon icons[] = new ImageIcon[52];

   private JPanel southPanel = new JPanel(); //panel to hold player 1's button panel
   private JPanel p1ButtonPanel = new JPanel();

   private JPanel northPanel = new JPanel(); //panel to hold player 2's button panel
   private JPanel p2ButtonPanel = new JPanel();

   //buttons for buttonPanels
   private JButton p1Check = new JButton("Check");
   private JButton p1Call = new JButton("Call");
   private JButton p1Raise = new JButton("Raise");
   private JButton p1Fold = new JButton("Fold");

   private JButton p2Check = new JButton("Check");
   private JButton p2Call = new JButton("Call");
   private JButton p2Raise = new JButton("Raise");
   private JButton p2Fold = new JButton("Fold");

   //button to play again
   //private JButton playAgain = new JButton("Play Again");

   //create players
   private Player p1;
   private Player p2;

   public PokerFrame() {
      super( "Texas Hold'em" );

      for(int i = 0; i < 52; i++)
         icons[i] = new ImageIcon(getClass().getResource(files[i]));

      playing = true;   //game is being played
      firstRound = true;   //first round

      Deck d = new Deck();
      p1 = new Player("player 1", 10);
      p2 = new Player("player 2", 10);

      potAmount = 0; //pot is intially 0

      //initialze game logic booleans
      p1hasChecked = false;
      p1hasCalled = false;
      p1hasRaised = false;
      p1hasFolded = false;

      p2Turn = false;
      p2hasChecked = false;
      p2hasCalled = false;
      p2hasRaised = false;
      p2hasFolded = false;

      raising = false;

      //SET UP OF GUI

      messageBox = new JTextArea("Welcome to Texas Hold'em!", 10, 10);
      messageBox.setLineWrap(true);
      add(messageBox, BorderLayout.EAST);

      table.setBorder(BorderFactory.createLineBorder(Color.black));
      table.setLayout(new GridLayout(3,1));

      opponentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      opponentPanel.add(new JLabel("Player 2"), BorderLayout.WEST);
      opponentCash = new JTextField("$  " + p2.getCash());
      opponentCash.setEditable(false);
      opponentPanel.add(opponentCash, BorderLayout.EAST);
      opponentPanel.setBackground(new Color(0, 175, 0));

      cardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      cardPanel.add(new JLabel("Deck"), BorderLayout.WEST);
      pot = new JTextField("$  " + potAmount);
      pot.setEditable(false);
      cardPanel.add(pot);
      cardPanel.setBackground(new Color(0, 175, 0));

      playerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      playerPanel.add(new JLabel("Player 1"), BorderLayout.WEST);
      playerCash = new JTextField("$  " + p1.getCash());
      playerCash.setEditable(false);
      playerPanel.add(playerCash, BorderLayout.EAST);
      playerPanel.setBackground(new Color(0, 175, 0));

      northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      p2ButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));  

      southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      p1ButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      add(table, BorderLayout.CENTER); //add the table to center

      add(northPanel, BorderLayout.NORTH);
      add(southPanel, BorderLayout.SOUTH);

      p1ButtonPanel.setLayout(new GridLayout(1,5,0,0));
      p2ButtonPanel.setLayout(new GridLayout(1,5,0,0));

      //add buttons to both players button panels
      p1ButtonPanel.add(p1Check);
      p1ButtonPanel.add(p1Call);
      p1ButtonPanel.add(p1Raise);
      p1ButtonPanel.add(p1Fold);
      //p1ButtonPanel.add(playAgain);
      southPanel.add(p1ButtonPanel, BorderLayout.EAST); //add p1ButtonPanel to southPanel

      p2ButtonPanel.add(p2Check);
      p2ButtonPanel.add(p2Call);
      p2ButtonPanel.add(p2Raise);
      p2ButtonPanel.add(p2Fold);
      northPanel.add(p2ButtonPanel, BorderLayout.EAST); //add p2ButtonPanel to northPanel

      //add panels to the table
      table.add(opponentPanel);
      table.add(cardPanel);
      table.add(playerPanel);

      //START GAME

      d.shuffle();

      //DEAL CARDS TO PLAYER AND OPPONENT
      Card [] p1Cards = {d.deal(), d.deal()};
      Card [] p2Cards = {d.deal(), d.deal()};
      p1.setStartCards(p1Cards);
      p2.setStartCards(p2Cards);

      //DISPLAY PLAYER CARDS
      JLabel p1Card1 = new JLabel();
      Image image = hiddenCard.getImage();
      Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      ImageIcon imageIcon = new ImageIcon(newimg); 
      p1Card1.setIcon(imageIcon);
      p1Card1.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            Image image = icons[p1.getStartCards()[0].getID()].getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p1Card1.setIcon(imageIcon);
         }
         public void mouseExited(MouseEvent me) {
            Image image = hiddenCard.getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p1Card1.setIcon(imageIcon);
         }
      });
      playerPanel.add(p1Card1);

      JLabel p1Card2 = new JLabel();
      image = hiddenCard.getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p1Card2.setIcon(imageIcon);
      p1Card2.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            Image image = icons[p1.getStartCards()[1].getID()].getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p1Card2.setIcon(imageIcon);
         }
         public void mouseExited(MouseEvent me) {
            Image image = hiddenCard.getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p1Card2.setIcon(imageIcon);
         }
      });
      playerPanel.add(p1Card2);

      //DISPLAY OPPONENT CARDS
      JLabel p2Card1 = new JLabel();
      image = hiddenCard.getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p2Card1.setIcon(imageIcon);
      p2Card1.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            Image image = icons[p2.getStartCards()[0].getID()].getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p2Card1.setIcon(imageIcon);
         }
         public void mouseExited(MouseEvent me) {
            Image image = hiddenCard.getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p2Card1.setIcon(imageIcon);
         }
      });
      opponentPanel.add(p2Card1);

      JLabel p2Card2 = new JLabel();
      image = hiddenCard.getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p2Card2.setIcon(imageIcon);
      p2Card2.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            Image image = icons[p2.getStartCards()[1].getID()].getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p2Card2.setIcon(imageIcon);
         }
         public void mouseExited(MouseEvent me) {
            Image image = hiddenCard.getImage();
            Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(newimg); 
            p2Card2.setIcon(imageIcon);
         }
      });
      opponentPanel.add(p2Card2);

      //DEALER DEALS 3 CARDS
      Card c1 = d.deal();
      Card c2 = d.deal();
      Card c3 = d.deal();

      //GET NEXT RIVER CARDS BUT HIDE THEM
      Card c4 = d.deal();
      Card c5 = d.deal();

      //DISPLAY THE FLOP
       
      JLabel deck1stCard = new JLabel();                                                //DISPLAY 1ST CARD
      image = icons[c1.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck1stCard.setIcon(imageIcon);
      cardPanel.add(deck1stCard);

      JLabel deck2ndCard = new JLabel();                                                 //DISPLAY 2ND CARD
      image = icons[c2.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck2ndCard.setIcon(imageIcon);
      cardPanel.add(deck2ndCard);

      JLabel deck3rdCard = new JLabel();                                                  //DISPLAY 3RD CARD
      image = icons[c3.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck3rdCard.setIcon(imageIcon);
      cardPanel.add(deck3rdCard);

      //RIVER CARDS (HIDDEN INITIALLY)

      JLabel deck4thCard = new JLabel();
      image = icons[c4.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck4thCard.setIcon(imageIcon);
      deck4thCard.setVisible(false);
      cardPanel.add(deck4thCard);

      JLabel deck5thCard = new JLabel();
      image = icons[c5.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck5thCard.setIcon(imageIcon);
      deck5thCard.setVisible(false);
      cardPanel.add(deck5thCard);

      //CARD EVALUATION

      Card[] tableCards = {c1, c2, c3, c4, c5};

      p1.storeHands(tableCards, 5);
      p2.storeHands(tableCards, 5);

      p1.bet(0.5);
      p2.bet(1);
      potAmount += 1.50;
      pot.setText("$  " + potAmount);
      playerCash.setText("$  " + p1.getCash());
      opponentCash.setText("$  " + p2.getCash());

      messageBox.setText("Player 1 GO");

      p1Check.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p1Check && playing && p2hasRaised && !p2Turn) {
               messageBox.setText("Player 1 must call Player 2's raise or fold.");
            } else if (e.getSource() == p1Check && playing && p2hasCalled && !p2Turn){
            	messageBox.setText("Player 1 checks.");
            	p2hasCalled = false;
            	p2Turn = true;
            } else if (e.getSource() == p1Check && playing && firstRound && !p2Turn){
               messageBox.setText("Player 1 checks.");
               p1.bet(0.5);
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               playerCash.setText("$  " + p1.getCash());
               p2Turn = true;
            } else if (e.getSource() == p1Check && playing && secondRound && !p2Turn){
               messageBox.setText("Player 1 checks.");
               p2Turn = true;
            } else if (e.getSource() == p1Check && playing && finalRound && !p2Turn){
               messageBox.setText("Player 1 checks.");
               p2Turn = true;
            } 
         }
      });
      p1Raise.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p1Raise && playing && p2hasRaised && !p2Turn) {
               messageBox.setText("Player 1 must call Player 2's raise or fold.");
            } else if (e.getSource() == p1Raise && playing && !p2Turn && firstRound && raising){
               messageBox.setText("Player 1 raises \n$0.50.");
               p1hasRaised = true;
               p1.bet(0.5);
               playerCash.setText("$  " + p1.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = true;
            } else if (e.getSource() == p1Raise && playing && !p2Turn && firstRound){
               messageBox.setText("Player 1 raises \n$1.00.");
               p1hasRaised = true;
               p1.bet(1);
               playerCash.setText("$  " + p1.getCash());
               potAmount += 1;
               pot.setText("$  " + potAmount);
               p2Turn = true;
               raising = true;
            } else if (e.getSource() == p1Raise && playing && !p2Turn && secondRound){
               messageBox.setText("Player 1 raises \n$0.50.");
               p1hasRaised = true;
               p1.bet(0.5);
               playerCash.setText("$  " + p1.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = true;
            } else if (e.getSource() == p1Raise && playing && !p2Turn && finalRound){
               messageBox.setText("Player 1 raises \n$0.50.");
               p1hasRaised = true;
               p1.bet(0.5);
               playerCash.setText("$  " + p1.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = true;
            }
         }
      });
      p1Call.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p1Call && playing && p2hasRaised && !p2Turn) {
               messageBox.setText("Player 1 calls Player 2's raise for $0.50.");
               p2hasRaised = false;
               p1hasCalled = true;
               p1.bet(0.5);
               playerCash.setText("$  " + p1.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = true;
            }
         }
      });
      p1Fold.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p1Fold && playing && !p2Turn) {
               p1hasFolded = true;
               p2Turn = true;
               if (p1hasFolded){
                  p2.changeCash(potAmount);
                  potAmount = 0;
                  opponentCash.setText("$  " + p2.getCash());
                  pot.setText("$  " + potAmount);
                  messageBox.setText("Player 1 folds.\nGame Over.\nPlayer 2 wins the hand.\n");
                  playing = false;
                  firstRound = false;
                  secondRound = false;
                  finalRound = false;
               }
            }
         }
      });

      // playAgain.addActionListener(new ActionListener(){
      //    public void actionPerformed(ActionEvent e){
      //       if (e.getSource() == playAgain) {
      //          messageBox.setText("Player 1 GO");
      //          playing = true;
      //       }
      //    }
      // });
      p2Check.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     if (e.getSource() == p2Check && playing && p2Turn && p1hasCalled && firstRound){
                        p1hasCalled = false;
                        messageBox.setText("Player 2 GO");
                        messageBox.setText("Dealer deals 4th\ncard.");
                        deck4thCard.setVisible(true);
                        p2Turn = false;
                        firstRound = false;
                        secondRound = true;
                     } else if (e.getSource() == p2Check && playing && p2Turn && p1hasRaised){
                        messageBox.setText("Player 2 must call Player 1's raise or fold.");
                     } else if (e.getSource() == p2Check && playing && p2Turn && firstRound){
                        p1hasCalled = false;
                        messageBox.setText("Player 2 GO");
                        messageBox.setText("Dealer deals 4th\ncard.");
                        deck4thCard.setVisible(true);
                        p2Turn = false;
                        firstRound = false;
                        secondRound = true;
                     } else if (e.getSource() == p2Check && playing && p2Turn && secondRound){
                        p1hasCalled = false;
                        messageBox.setText("Dealer deals 5th\ncard.");
                        deck5thCard.setVisible(true);
                        p2Turn = false;
                        secondRound = false;
                        finalRound = true;
                     } else if (e.getSource() == p2Check && playing && p2Turn && finalRound){
                        p1hasCalled = false;
                        messageBox.setText("Player 2 GO");
                        if (p1.getBestHand().compareTo(p2.getBestHand()) == 1){
                           messageBox.setText("PLAYER 1 WON with a " + p1.getBestHand() + "!");
                           p1.changeCash(potAmount);
                        } else {
                           messageBox.setText("PLAYER 2 WON with a " + p1.getBestHand() + "!");
                           p2.changeCash(potAmount);
                        }
                        potAmount = 0;
                        opponentCash.setText("$  " + p2.getCash());
                        playerCash.setText("$  " + p1.getCash());
                        pot.setText("$  " + potAmount);
                        playing = false;
                        p2Turn = false;
                        finalRound = false;;
                     } else if (p1hasRaised && playing && p2Turn){
                        messageBox.setText("Player 2 must raise or fold.");
                     }
      }});
      p2Raise.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p2Raise && playing && p2Turn && p1hasRaised) {
               messageBox.setText("Player 2 must call Player 1's raise or fold.");
            } else if (e.getSource() == p2Raise && playing && p2Turn){
               messageBox.setText("Player 2 raises \n$0.50.");
               p2hasRaised = true;
               p2.bet(0.5);
               opponentCash.setText("$  " + p2.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = false;
            }
         }
      });
      p2Call.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p2Call && playing && p2Turn && p1hasRaised && firstRound){
               messageBox.setText("Player 2 calls Player 1's raise for $0.50.");
               p1hasRaised = false;
               p2hasCalled = true;
               p2.bet(0.5);
               opponentCash.setText("$  " + p2.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = false;
            } else if (e.getSource() == p2Call && playing && p2Turn && p1hasRaised && secondRound){
               messageBox.setText("Player 2 calls Player 1's raise for $0.50.");
               p1hasRaised = false;
               p2hasCalled = true;
               p2.bet(0.5);
               opponentCash.setText("$  " + p2.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = false;
            } else if (e.getSource() == p2Call && playing && p2Turn && p1hasRaised && finalRound){
               messageBox.setText("Player 2 calls Player 1's raise for $0.50.");
               p1hasRaised = false;
               p2hasCalled = true;
               p2.bet(0.5);
               opponentCash.setText("$  " + p2.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = false;
            }
         }
      });
      p2Fold.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p2Fold && p2Turn && playing) {
               p2hasFolded = true;
               p2Turn = false;
               if (p2hasFolded){
                  p1.changeCash(potAmount);
                  potAmount = 0;
                  playerCash.setText("$  " + p1.getCash());
                  pot.setText("$  " + potAmount);
                  messageBox.setText("Player 2 folds.\nGame Over.\nPlayer 1 wins the hand.\n");
                  playing = false;
                  firstRound = false;
                  secondRound = false;
                  finalRound = false;
               }
            }
         }
      }); 
   }
} //end class
