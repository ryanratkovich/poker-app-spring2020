import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PokerGUI extends JFrame {
   public static void main(String args[]) { 
      PokerFrame pokerFrame = new PokerFrame(); 
      pokerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pokerFrame.setSize(700, 500);
      pokerFrame.setVisible(true);
   }
}

class PokerFrame extends JFrame {
   //create all JPanels to display
   private JPanel table = new JPanel();
   private JPanel opponentPanel = new JPanel();
   private JPanel cardPanel= new JPanel();
   private JPanel playerPanel= new JPanel();
   private JPanel southPanel = new JPanel(); //to hold buttonPanel
   private JPanel buttonPanel = new JPanel();

   //create JLabels to portray cards
   private JLabel[] opponentCards;
   private JTextField opponentCash;
   private JLabel[] deckCards;
   private JTextField pot;
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


   //create the buttons for buttonPanel
   private JButton check = new JButton("Check");
   private JButton call = new JButton("Call");
   private JButton raise = new JButton("Raise");
   private JButton fold = new JButton("Fold");

   public PokerFrame() {

      //SET UP GUI
      super( "Texas Hold'em" );
      Deck d = new Deck();
      Player p1 = new Player("player 1", 5);
      Player p2 = new Player("player 2", 5);

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
      pot = new JTextField("$  ");
      pot.setEditable(false);
      cardPanel.add(pot);
      cardPanel.setBackground(Color.green);

      playerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      playerPanel.add(new JLabel("Player 1"), BorderLayout.WEST);
      playerCash = new JTextField("$  " + p1.getCash());
      playerCash.setEditable(false);
      playerPanel.add(playerCash, BorderLayout.EAST);
      playerPanel.setBackground(Color.green);

      southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      add(table, BorderLayout.CENTER); //add the table to center

      add(southPanel, BorderLayout.SOUTH); //add bottom panel

      buttonPanel.setLayout(new GridLayout(1,4,0,0));

      check.addActionListener( new ActionListener(){  
            public void actionPerformed( ActionEvent event )
            {

            }
         });
      buttonPanel.add(check);

      call.addActionListener(
         new ActionListener()
         {  
            public void actionPerformed( ActionEvent event )
            {

            }
         }
      );
      buttonPanel.add(call);

      raise.addActionListener(
         new ActionListener()
         {  
            public void actionPerformed( ActionEvent event )
            {
               //copyJList.setListData( colorJList.getSelectedValues() );
            }
         }
      );
      buttonPanel.add(raise);

      fold.addActionListener(
         new ActionListener()
         {  
            public void actionPerformed( ActionEvent event )
            {
               //copyJList.setListData( colorJList.getSelectedValues() );
            }
         }
      );
      buttonPanel.add(fold);
      southPanel.add(buttonPanel, BorderLayout.EAST); //add buttonPanel to southPanel

      //add panels to the table
      table.add(opponentPanel);
      table.add(cardPanel);
      table.add(playerPanel);


      //START GAME
      d.shuffle();

      //DEAL CARDS TO PLAYER AND OPPONENT
      p1.startCards[0] = d.deal();
      p2.startCards[0] = d.deal();
      p1.startCards[1] = d.deal();
      p2.startCards[1] = d.deal();


      //DISPLAY PLAYER AND OPPONENT CARDS
      JLabel p1Card1 = new JLabel();
      Image image = icons[p1.startCards[0].getID()].getImage();
      Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      ImageIcon imageIcon = new ImageIcon(newimg); 
      p1Card1.setIcon(imageIcon);
      playerPanel.add(p1Card1);

      JLabel p1Card2 = new JLabel();
      image = icons[p1.startCards[1].getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p1Card2.setIcon(imageIcon);
      playerPanel.add(p1Card2);

      JLabel p2Card1 = new JLabel();
      image = hiddenCard.getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p2Card1.setIcon(imageIcon);
      opponentPanel.add(p2Card1);

      JLabel p2Card2 = new JLabel();
      image = hiddenCard.getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      p2Card2.setIcon(imageIcon);
      opponentPanel.add(p2Card2);



      //DEALER DEALS 3 CARDS
      Card c1 = d.deal();
      Card c2 = d.deal();
      Card c3 = d.deal();

      //DISPLAY THE FLOP
      JLabel deck1stCard = new JLabel();
      image = icons[c1.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck1stCard.setIcon(imageIcon);
      cardPanel.add(deck1stCard);

      JLabel deck2ndCard = new JLabel();
      image = icons[c2.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck2ndCard.setIcon(imageIcon);
      cardPanel.add(deck2ndCard);

      JLabel deck3rdCard = new JLabel();
      image = icons[c3.getID()].getImage();
      newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg); 
      deck3rdCard.setIcon(imageIcon);
      cardPanel.add(deck3rdCard);


   }
}