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
      "2C.png", "2D.png", "2H.png", "2S.png", "3C.png", "3D.png", "3H.png", "3S.png",
      "4C.png", "4D.png", "4H.png", "4S.png", "5C.png", "5D.png", "5H.png", "5S.png",
      "6C.png", "6D.png", "6H.png", "6S.png", "7C.png", "7D.png", "7H.png", "7S.png",
      "8C.png", "8D.png", "8H.png", "8S.png", "9C.png", "9D.png", "9H.png", "9S.png",
      "10C.png", "10D.png", "10H.png", "10S.png", "JC.png", "JD.png", "JH.png", "JS.png",
      "QC.png", "QD.png", "QH.png", "QS.png", "KC.png", "KD.png", "KH.png", "KS.png",
      "AC.png", "AD.png", "AH.png", "AS.png"
   };

   //card to hide cards from players
   private ImageIcon hiddenCard = new ImageIcon(getClass().getResource("gray_back.png"));

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
            if (e.getSource() == p1Check && playing && p2hasRaised) {
               messageBox.setText("Player 1 must call Player 2's raise or fold.");
            } else if (e.getSource() == p1Check && playing && firstRound){
               messageBox.setText("Player 1 checks.");
               p1.bet(0.5);
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               playerCash.setText("$  " + p1.getCash());
               p2Turn = true;
            } else if (e.getSource() == p1Check && playing && secondRound){
               messageBox.setText("Player 1 checks.");
               // p1.bet(0.5);
               // potAmount += 0.5;
               // pot.setText("$  " + potAmount);
               // playerCash.setText("$  " + p1.getCash());
               p2Turn = true;
            } else if (e.getSource() == p1Check && playing && finalRound){
               messageBox.setText("Player 1 checks.");
               // p1.bet(0.5);
               // potAmount += 0.5;
               // pot.setText("$  " + potAmount);
               // playerCash.setText("$  " + p1.getCash());
               p2Turn = true;
            } 
         }
      });

      p1Raise.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p1Raise && playing && p2hasRaised) {
               messageBox.setText("Player 1 must call Player 2's raise or fold.");
            } else if (e.getSource() == p1Raise && playing){
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
            if (e.getSource() == p1Call && playing && p2hasRaised) {
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
            if (e.getSource() == p1Fold && playing) {
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
                        // p2.bet(0.5);
                        // opponentCash.setText("$  " + p2.getCash());
                        // potAmount += 1;
                        // pot.setText("$  " + potAmount);
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
                        // p2.bet(0.5);
                        // opponentCash.setText("$  " + p2.getCash());
                        // potAmount += 1;
                        // pot.setText("$  " + potAmount);
                        messageBox.setText("Dealer deals 4th\ncard.");
                        deck4thCard.setVisible(true);
                        p2Turn = false;
                        firstRound = false;
                        secondRound = true;
                     } else if (e.getSource() == p2Check && playing && p2Turn && secondRound){
                        p1hasCalled = false;
                        messageBox.setText("Player 2 GO");
                        // p2.bet(0.5);
                        // opponentCash.setText("$  " + p2.getCash());
                        // potAmount += 1;
                        // pot.setText("$  " + potAmount);
                        messageBox.setText("Dealer deals 5th\ncard.");
                        deck5thCard.setVisible(true);
                        p2Turn = false;
                        secondRound = false;
                        finalRound = true;
                     } else if (e.getSource() == p2Check && playing && p2Turn && finalRound){
                        p1hasCalled = false;
                        messageBox.setText("Player 2 GO");
                        // p2.bet(0.5);
                        // opponentCash.setText("$  " + p2.getCash());
                        // potAmount += 1;
                        // pot.setText("$  " + potAmount);
                        if (p1.getBestHand().compareTo(p2.getBestHand()) == 1){
                           messageBox.setText("PLAYER 1 WON!");
                           p1.changeCash(potAmount);
                        } else {
                           messageBox.setText("PLAYER 2 WON!");
                           p2.changeCash(potAmount);
                        }
                        potAmount = 0;
                        opponentCash.setText("$  " + p2.getCash());
                        playerCash.setText("$  " + p1.getCash());
                        pot.setText("$  " + potAmount);
                        playing = false;
                        p2Turn = false;
                        finalRound = false;;
                     } else if (p1hasRaised && playing){
                        messageBox.setText("Player 2 must raise or fold.");
                     }
      }});

      p2Raise.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p2Raise && playing && p1hasRaised) {
               messageBox.setText("Player 2 must call Player 1's raise or fold.");
            } else if (e.getSource() == p2Raise && playing){
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
            if (e.getSource() == p2Call && playing && p1hasRaised) {
               messageBox.setText("Player 2 calls Player 1's raise for $0.50.");
               p1hasRaised = false;
               p2hasCalled = true;
               p1.bet(0.5);
               opponentCash.setText("$  " + p2.getCash());
               potAmount += 0.5;
               pot.setText("$  " + potAmount);
               p2Turn = false;
            }
         }
      });
      p2Fold.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            if (e.getSource() == p2Fold && playing) {
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

   //    //P1 STARTS
   //    while (true){
   //       messageBox.setText("Player 1 GO");
   //       if (p1hasFolded){
   //          p2.changeCash(potAmount);
   //          messageBox.setText("Game Over.");
   //          break;
   //       }
   //       p1hasChecked = false;
   //       p1hasCalled = false;
   //       p1hasRaised = false;
   //       p1hasFolded = false;

   //       if (p2Turn){  //P2 GOES
   //          while (true){
   //             messageBox.setText("Player 2 GO");
   //             if (p2hasFolded){
   //                playing = false;
   //                p2.changeCash(potAmount);
   //                break;
   //             }
   //             p2hasChecked = false;
   //             p2hasCalled = false;
   //             p2hasRaised = false;
   //             p2hasFolded = false;

   //             if (!p2Turn){ //DEALER DEALS 4TH CARD
   //                messageBox.setText("Time for the River!");

   //                Card c4 = d.deal();

   //                JLabel deck4thCard = new JLabel();
   //                image = icons[c4.getID()].getImage();
   //                newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                imageIcon = new ImageIcon(newimg); 
   //                deck4thCard.setIcon(imageIcon);
   //                cardPanel.add(deck4thCard);

   //                while (true){  //P1 GOES
   //                   messageBox.setText("Player 1 GO");
   //                   if (p1hasFolded){
   //                      p2.changeCash(potAmount);
   //                      messageBox.setText("Game Over.");
   //                      break;
   //                   }
   //                   p1hasChecked = false;
   //                   p1hasCalled = false;
   //                   p1hasRaised = false;
   //                   p1hasFolded = false;

   //                   if (p2Turn){   //P2 GOES
   //                      while (true){
   //                         messageBox.setText("Player 2 GO");
   //                         if (p2hasFolded){
   //                            playing = false;
   //                            p2.changeCash(potAmount);
   //                            break;
   //                         }
   //                         p2hasChecked = false;
   //                         p2hasCalled = false;
   //                         p2hasRaised = false;
   //                         p2hasFolded = false;

   //                         if (!p2Turn){
   //                            messageBox.setText("Final card is on the table!");

   //                            Card c5 = d.deal();

   //                            JLabel deck5thCard = new JLabel();
   //                            image = icons[c5.getID()].getImage();
   //                            newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                            imageIcon = new ImageIcon(newimg); 
   //                            deck5thCard.setIcon(imageIcon);
   //                            cardPanel.add(deck5thCard);

   //                            while (true){  //P1 GOES - FINAL ROUND OF BETTING
   //                               messageBox.setText("Player 1 GO");
   //                               if (p1hasFolded){
   //                                  p2.changeCash(potAmount);
   //                                  messageBox.setText("Game Over.");
   //                                  break;
   //                               }
   //                               p1hasChecked = false;
   //                               p1hasCalled = false;
   //                               p1hasRaised = false;
   //                               p1hasFolded = false;

   //                               if (p2Turn){
   //                                  while (true){
   //                                     messageBox.setText("Player 2 GO");
   //                                     if (p2hasFolded){
   //                                        playing = false;
   //                                        p2.changeCash(potAmount);
   //                                        break;
   //                                     }
   //                                     p2hasChecked = false;
   //                                     p2hasCalled = false;
   //                                     p2hasRaised = false;
   //                                     p2hasFolded = false;

   //                                     if (!p2Turn){
   //                                        messageBox.setText("Show your hands!");

   //                                        image = icons[p1.getStartCards()[0].getID()].getImage();
   //                                        newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                                        imageIcon = new ImageIcon(newimg); 
   //                                        p1Card1.setIcon(imageIcon);

   //                                        image = icons[p1.getStartCards()[1].getID()].getImage();
   //                                        newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                                        imageIcon = new ImageIcon(newimg); 
   //                                        p1Card2.setIcon(imageIcon);

   //                                        image = icons[p2.getStartCards()[0].getID()].getImage();
   //                                        newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                                        imageIcon = new ImageIcon(newimg); 
   //                                        p2Card1.setIcon(imageIcon);

   //                                        image = icons[p2.getStartCards()[1].getID()].getImage();
   //                                        newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
   //                                        imageIcon = new ImageIcon(newimg); 
   //                                        p2Card2.setIcon(imageIcon);

   //                                        Card[] tableCards = {c1, c2, c3, c4, c5};

   //                                        p1.storeHands(tableCards, 5);
   //                                        p2.storeHands(tableCards, 5);

   //                                        if (p1.getBestHand().compareTo(p2.getBestHand()) == 1){
   //                                           add(new JTextField("   PLAYER 1 WON    "), BorderLayout.EAST);
   //                                        } else {
   //                                           add(new JTextField("   PLAYER 2 WON    "), BorderLayout.EAST); 
   //                                        }
   //                                        p1ButtonPanel.add(playAgain);
   //                                        playing = false;
   //                                     }
   //                                  }
   //                               }
   //                            }
   //                         }
   //                      }
   //                   }
   //                }
   //             }
   //          }
   //       }
   //    } 
   }
} //end class
