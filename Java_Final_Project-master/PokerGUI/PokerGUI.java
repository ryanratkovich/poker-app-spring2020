import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PokerGUI extends JFrame {
   public static void main(String args[]) { 
      PokerFrame pokerFrame = new PokerFrame(); 
      pokerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pokerFrame.setSize(800, 500);
      pokerFrame.setVisible(true);
   }
}

class PokerFrame extends JFrame {

   //boolean variables for game logic
   private Boolean playing;

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
   private JTextField messageBox;

   //create all JPanels to display
   private JPanel table = new JPanel();
   private JPanel opponentPanel = new JPanel();
   private JPanel cardPanel= new JPanel();
   private JPanel playerPanel= new JPanel();

   //create JLabels to portray cards
   private JLabel[] opponentCards;
   private JTextField opponentCash;
   private JLabel[] deckCards;
   private JTextField pot;
   private double potAmount;
   private JLabel[] playerCards;
   private JTextField playerCash;

   //create arrays for cards
   private String files[] = {
      "2C.png", "2D.png", "2H.png", "2S.png",
      "3C.png", "3D.png", "3H.png", "3S.png",
      "4C.png", "4D.png", "4H.png", "4S.png",
      "5C.png", "5D.png", "5H.png", "5S.png",
      "6C.png", "6D.png", "6H.png", "6S.png",
      "7C.png", "7D.png", "7H.png", "7S.png",
      "8C.png", "8D.png", "8H.png", "8S.png",
      "9C.png", "9D.png", "9H.png", "9S.png",
      "10C.png", "10D.png", "10H.png", "10S.png",
      "JC.png", "JD.png", "JH.png", "JS.png",
      "QC.png", "QD.png", "QH.png", "QS.png",
      "KC.png", "KD.png", "KH.png", "KS.png",
      "AC.png", "AD.png", "AH.png", "AS.png"
   };

   //card to hide opponent cards
   private ImageIcon hiddenCard = new ImageIcon(getClass().getResource("gray_back.png"));

   //array to hold images of cards
   private ImageIcon icons[] = { 
      new ImageIcon(getClass().getResource(files[0])),
      new ImageIcon(getClass().getResource(files[1])), 
      new ImageIcon(getClass().getResource(files[2])),
      new ImageIcon(getClass().getResource(files[3])),
      new ImageIcon(getClass().getResource(files[4])),
      new ImageIcon(getClass().getResource(files[5])), 
      new ImageIcon(getClass().getResource(files[6])),
      new ImageIcon(getClass().getResource(files[7])),
      new ImageIcon(getClass().getResource(files[8])),
      new ImageIcon(getClass().getResource(files[9])), 
      new ImageIcon(getClass().getResource(files[10])),
      new ImageIcon(getClass().getResource(files[11])),
      new ImageIcon(getClass().getResource(files[12])),
      new ImageIcon(getClass().getResource(files[13])), 
      new ImageIcon(getClass().getResource(files[14])),
      new ImageIcon(getClass().getResource(files[15])),
      new ImageIcon(getClass().getResource(files[16])),
      new ImageIcon(getClass().getResource(files[17])), 
      new ImageIcon(getClass().getResource(files[18])),
      new ImageIcon(getClass().getResource(files[19])),
      new ImageIcon(getClass().getResource(files[20])),
      new ImageIcon(getClass().getResource(files[21])), 
      new ImageIcon(getClass().getResource(files[22])),
      new ImageIcon(getClass().getResource(files[23])),
      new ImageIcon(getClass().getResource(files[24])),
      new ImageIcon(getClass().getResource(files[25])), 
      new ImageIcon(getClass().getResource(files[26])),
      new ImageIcon(getClass().getResource(files[27])),
      new ImageIcon(getClass().getResource(files[28])),
      new ImageIcon(getClass().getResource(files[29])), 
      new ImageIcon(getClass().getResource(files[30])),
      new ImageIcon(getClass().getResource(files[31])),
      new ImageIcon(getClass().getResource(files[32])),
      new ImageIcon(getClass().getResource(files[33])), 
      new ImageIcon(getClass().getResource(files[34])),
      new ImageIcon(getClass().getResource(files[35])),
      new ImageIcon(getClass().getResource(files[36])),
      new ImageIcon(getClass().getResource(files[37])), 
      new ImageIcon(getClass().getResource(files[38])),
      new ImageIcon(getClass().getResource(files[39])),
      new ImageIcon(getClass().getResource(files[40])),
      new ImageIcon(getClass().getResource(files[41])), 
      new ImageIcon(getClass().getResource(files[42])),
      new ImageIcon(getClass().getResource(files[43])),
      new ImageIcon(getClass().getResource(files[44])),
      new ImageIcon(getClass().getResource(files[45])), 
      new ImageIcon(getClass().getResource(files[46])),
      new ImageIcon(getClass().getResource(files[47])),
      new ImageIcon(getClass().getResource(files[48])),
      new ImageIcon(getClass().getResource(files[49])), 
      new ImageIcon(getClass().getResource(files[50])),
      new ImageIcon(getClass().getResource(files[51]))
   };

   private JPanel southPanel = new JPanel(); //to hold p1ButtonPanel
   private JPanel p1ButtonPanel = new JPanel();

   private JPanel northPanel = new JPanel(); //to hold p2ButtonPanel
   private JPanel p2ButtonPanel = new JPanel();

   //create the buttons for buttonPanel
   private JButton p1Check = new JButton("Check");
   private JButton p1Call = new JButton("Call");
   private JButton p1Raise = new JButton("Raise");
   private JButton p1Fold = new JButton("Fold");

   private JButton p2Check = new JButton("Check");
   private JButton p2Call = new JButton("Call");
   private JButton p2Raise = new JButton("Raise");
   private JButton p2Fold = new JButton("Fold");

   private JButton playAgain = new JButton("Play Again");

   public PokerFrame() {

      //SET UP GUI
      super( "Texas Hold'em" );
      playing = true;   //game is being played
      Deck d = new Deck();
      Player p1 = new Player("player 1", 10);
      Player p2 = new Player("player 2", 10);

      potAmount = 0;

      p1Turn = true;
      p1hasChecked = false;
      p1hasCalled = false;
      p1hasRaised = false;
      p1hasFolded = false;

      p2Turn = false;
      p2hasChecked = false;
      p2hasCalled = false;
      p2hasRaised = false;
      p2hasFolded = false;

      Clicklistener click= new Clicklistener();
   

      messageBox = new JTextField("Welcome to Texas Hold'em!");
      add(messageBox, BorderLayout.EAST);

      table.setBorder(BorderFactory.createLineBorder(Color.black));
      table.setLayout(new GridLayout(3,1));

      opponentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      opponentPanel.add(new JLabel("Player 2"), BorderLayout.WEST);
      opponentCash = new JTextField("$  " + p2.getCash());
      opponentCash.setEditable(false);
      opponentPanel.add(opponentCash, BorderLayout.EAST);
      opponentPanel.setBackground(Color.green);

      cardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      cardPanel.add(new JLabel("Deck"), BorderLayout.WEST);
      pot = new JTextField("$  " + potAmount);
      pot.setEditable(false);
      cardPanel.add(pot);
      cardPanel.setBackground(Color.green);

      playerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      playerPanel.add(new JLabel("Player 1"), BorderLayout.WEST);
      playerCash = new JTextField("$  " + p1.getCash());
      playerCash.setEditable(false);
      playerPanel.add(playerCash, BorderLayout.EAST);
      playerPanel.setBackground(Color.green);

      northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      p2ButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));  

      southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      p1ButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      add(table, BorderLayout.CENTER); //add the table to center

      add(northPanel, BorderLayout.NORTH);
      add(southPanel, BorderLayout.SOUTH);

      p1ButtonPanel.setLayout(new GridLayout(1,5,0,0));
      p2ButtonPanel.setLayout(new GridLayout(1,5,0,0));

      //add buttons to buttonPanel
      p1ButtonPanel.add(p1Check);
      p1ButtonPanel.add(p1Call);
      p1ButtonPanel.add(p1Raise);
      p1ButtonPanel.add(p1Fold);
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

      messageBox.setText("The game has begun!");

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

      //DISPLAY THE FLOP
      JLabel deck1stCard = new JLabel();                                                 //DISPLAY 1ST CARD
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

      //P1 STARTS BETTING

      if (p1Turn){   //p1 conditional
         p1Check.addActionListener(click);
         p1Raise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               messageBox.setText("Player 1 raises $0.50.");
               p1hasRaised = true;
               p1.changeCash(-0.5);
               potAmount += 0.5;
               p2Turn = true;
            }
         });
         p1Fold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               messageBox.setText("Player 1 folds. Player 2 wins the hand.");
               p1hasFolded = true;
               p2Turn = true;
            }
         });
         messageBox.setText("Player 1 GO");
         if (p1hasFolded){
            p2.changeCash(potAmount);
            messageBox.setText("Game Over.");
         }
         p1hasChecked = false;
         p1hasCalled = false;
         p1hasRaised = false;
         p1hasFolded = false;
         p1Turn = false;

         if (p2Turn){ //p2 conditional
            messageBox.setText("Player 2 GO");
            p2Check.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  messageBox.setText("Player 2 checks.");
               }
            });
            p2Call.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  messageBox.setText("Player 2 calls Player 1's raise.");
                  p2hasCalled = true;
                  p2.changeCash(-0.5);
                  potAmount += 0.5;
               }
            });
            p2Raise.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  messageBox.setText("Player 2 raises $1.");
                  p2hasRaised = true;
                  p2.changeCash(-1);
                  potAmount += 1;
               }
            });
            p2Fold.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  messageBox.setText("Player 2 folds. Player 1 wins the hand.");
                  p2hasFolded = true;
               }
            });
            if (p2hasFolded){
               playing = false;
               p2.changeCash(potAmount);
            }
            p2hasChecked = false;
            p2hasCalled = false;
            p2hasRaised = false;
            p2hasFolded = false;
         } //end p2 conditional

      } //end p1 conditional


   


      //P2 TAKES TURN


      //DEALER DEALS 4TH CARD

      messageBox.setText("Time for the River!");

      Card c4 = d.deal();

      JLabel deck4thCard = new JLabel();
      image = icons[c4.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck4thCard.setIcon(imageIcon);
      cardPanel.add(deck4thCard);

      //ROUND OF BETTING

      //DEALER DEALS 5TH CARD

      Card c5 = d.deal();

      JLabel deck5thCard = new JLabel();
      image = icons[c5.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck5thCard.setIcon(imageIcon);
      cardPanel.add(deck5thCard);

      //ROUND OF BETTING

      //EVALUATE CARDS

      Card[] tableCards = {c1, c2, c3, c4, c5};

      p1.storeHands(tableCards, 5);
      p2.storeHands(tableCards, 5);

      if (p1.getBestHand().compareTo(p2.getBestHand()) == 1){
         add(new JTextField("   PLAYER 1 WON    "), BorderLayout.EAST);
      } else {
         add(new JTextField("   PLAYER 2 WON    "), BorderLayout.EAST); 
      }
      p1ButtonPanel.add(playAgain);
      playing = false;
   }

   private class Clicklistener implements ActionListener
   {
      public void actionPerformed(ActionEvent e){
         if (e.getSource() == p1Check) {
            messageBox.setText("Player 1 checks.");
            p2Turn = true;
         }
         if (e.getSource() == p1Call) {
            
         }
         if (e.getSource() == p1Raise) {
            
         }
         if (e.getSource() == p1Fold) {
            
         }
         if (e.getSource() == p2Check) {
            
         }
         if (e.getSource() == p2Call) {
            
         }
         if (e.getSource() == p2Raise) {
            
         }
         if (e.getSource() == p2Fold) {
            
         }
      }
   }



}
