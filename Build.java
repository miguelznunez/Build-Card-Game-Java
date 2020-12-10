// Name: Miguel Nunez
// Date: 12/08/2020
// Description: Creates a Build card game GUI
// File Name: Build.java
import javax.swing.border.*;
import javax.swing.JComponent;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JOptionPane;
import java.util.concurrent.TimeUnit; 
import java.awt.event.*;
// class Build  -------------------------------------------------------------------
public class Build
{
    static int NUM_CARDS_PER_HAND = 7;
    static int THREE_STACK_CARDS = 3;
    static int NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] computerLabelsCopy = new JLabel[NUM_CARDS_PER_HAND];    
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
    static JLabel[] humanLabelsCopy = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] threeStackCardIcons = new JLabel[THREE_STACK_CARDS];
    static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
    static JLabel[] scores = new JLabel[THREE_STACK_CARDS];
    static Card[] computerCards = new Card[NUM_CARDS_PER_HAND];
    static Card[] humanCards = new Card[NUM_CARDS_PER_HAND];
    static Card[] threeStackCards = new Card[THREE_STACK_CARDS];
    static int player1Score = 0, player2Score = 0, iCantPlay = 0;

    // add Jlabels
    static void fillJlabels(CardGameFramework lcg)
    {
      int k;
      // create front card labels
      for(k = 0;k < NUM_CARDS_PER_HAND;k++)
      {
        // this array contains the front of the computer cards
        computerLabels[k] = new JLabel(GUICard.getIcon(lcg.getHand(0).inspectCard(k)));
        // this array contains the back of the computer cards
        computerLabelsCopy[k] = new JLabel(GUICard.getIcon(lcg.getHand(0).inspectCard(k)));
        // this array is a copy of the computer card labels but in "Card" form
        // the purpose of this is to compare cards with eachother
        computerCards[k] = lcg.getHand(0).inspectCard(k);
        // this array contains the human cards
        humanLabels[k] = new JLabel(GUICard.getIcon(lcg.getHand(1).inspectCard(k)));
        // this array is simply a copy of the human card labels
        // the purpose of this is to make a card dissapear in place after you click it
        humanLabelsCopy[k] = new JLabel(GUICard.getIcon(lcg.getHand(1).inspectCard(k)));
        // this array is a also a copy of the human card labels but in "Card" form
        // the purpose of this is to compare cards with eachother
        humanCards[k] = lcg.getHand(1).inspectCard(k);
      }
      // create three random card labels one for each stack
      for(k = 0;k < THREE_STACK_CARDS;k++)
      {
        threeStackCards[k] = lcg.getCardFromDeck();
        threeStackCardIcons[k] = new JLabel(GUICard.getIcon(threeStackCards[k]));
      }
      // create score text labels
      for(k = 0;k < 3;k++)
      {
          if(k == 0)
          {
            scores[k] = new JLabel("SCORE", JLabel.CENTER);
            scores[k].setFont(new Font("Sans", Font.BOLD, 20));
            scores[k].setForeground(Color.WHITE);
          }

          if(k == 1)
          {
            scores[k] = new JLabel("0 - 0", JLabel.CENTER);
            scores[k].setFont(new Font("Sans", Font.BOLD, 18));
            scores[k].setForeground(Color.WHITE);
          }

          if(k == 2)
          {
            scores[k] = new JLabel("Player 1", JLabel.CENTER);
            scores[k].setFont(new Font("Sans", Font.BOLD, 16));
            scores[k].setForeground(Color.WHITE);
          }
      }
    }

    static void addScore(CardTable ct)
    {
      ct.getPnlScore().add(scores[0]);
      ct.getPnlScore().add(scores[1]);
      ct.getPnlScore().add(scores[2]);
    }
    // load the cards on the table
    static void addCardsToTable(CardTable ct)
    {
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         ct.getPnlComputerHand().add(computerLabelsCopy[k]);
         ct.getPnlHumanHand().add(humanLabelsCopy[k]);
      }
      // this is the set up our play area is going to have as soon as we run the program
      ct.getPnlPaneHolder(0).add(threeStackCardIcons[0]);
      ct.getPnlPaneHolder(1).add(threeStackCardIcons[1]);
      ct.getPnlPaneHolder(2).add(threeStackCardIcons[2]);
     
    }

    // add events listeners (mouse listeners) to the cards in your hand
   static void addHumanEvents(CardGameFramework lcg, CardTable ct)
   {
    for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
    {
      final int index = k;

        humanLabelsCopy[k].addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){                
        // value of your card
        int humanCardValue = Card.valueOfCard(lcg.getHand(1).inspectCard(index));

        if(humanCardValue - 1 == Card.valueOfCard(threeStackCards[0]) || humanCardValue + 1 == Card.valueOfCard(threeStackCards[0]))
        {
          // make the card you clicked invisible 
          humanLabelsCopy[index].setVisible(false);
          // remove the card thats currently in stack1
          ct.getPnlPaneHolder(0).remove(threeStackCardIcons[0]);
          // replace that card with the one you clicked
          threeStackCardIcons[0] = humanLabels[index];
          // actually place it in stack1
          ct.getPnlPaneHolder(0).add(threeStackCardIcons[0]);

          // place the card you clicked in the stack you placed it in (this is for us to check the value of the card)
          threeStackCards[0] = humanCards[index];
          // set human card value to a random number (1000) to ensure next if condition is not accidentaly met
          humanCardValue = 1000;
        }

        if(humanCardValue - 1 == Card.valueOfCard(threeStackCards[1]) || humanCardValue + 1 == Card.valueOfCard(threeStackCards[1]))
        {
          humanLabelsCopy[index].setVisible(false);
          ct.getPnlPaneHolder(1).remove(threeStackCardIcons[1]);
          threeStackCardIcons[1] = humanLabels[index];
          ct.getPnlPaneHolder(1).add(threeStackCardIcons[1]); 

          threeStackCards[1] = humanCards[index];  
          humanCardValue = 1000;   
        }

        if(humanCardValue - 1 == Card.valueOfCard(threeStackCards[2]) || humanCardValue + 1 == Card.valueOfCard(threeStackCards[2]))
        {
          humanLabelsCopy[index].setVisible(false);
          ct.getPnlPaneHolder(2).remove(threeStackCardIcons[2]);
          threeStackCardIcons[2] = humanLabels[index];
          ct.getPnlPaneHolder(2).add(threeStackCardIcons[2]);

          threeStackCards[2] = humanCards[index];  
          humanCardValue = 1000;
        }      
       }// mousepressed
      });// end mouselistener

    }// end for loop

   }// end method
   static void addComputerEvents(CardGameFramework lcg, CardTable ct)
   {
    for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
    {
      final int index = k;

        computerLabelsCopy[k].addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){                

        int computerCardValue = Card.valueOfCard(lcg.getHand(0).inspectCard(index));

        if(computerCardValue - 1 == Card.valueOfCard(threeStackCards[0]) || computerCardValue + 1 == Card.valueOfCard(threeStackCards[0]))
        {
          // make the card the computer clicked invisible 
          computerLabelsCopy[index].setVisible(false);
          // remove the card thats currently in stack1
          ct.getPnlPaneHolder(0).remove(threeStackCardIcons[0]);
          // replace that card with the one the computer clicked
          threeStackCardIcons[0] = computerLabels[index];
          // actually place it in stack1
          ct.getPnlPaneHolder(0).add(threeStackCardIcons[0]);
          // place the card you clicked in the stack you placed it in (this is for us to check the value of the card)
          threeStackCards[0] = computerCards[index];
          // set computer card value to a random number (1000) to ensure next if condition is not accidentaly met
          computerCardValue = 1000;
        }

        if(computerCardValue - 1 == Card.valueOfCard(threeStackCards[1]) || computerCardValue + 1 == Card.valueOfCard(threeStackCards[1]))
        {
          computerLabelsCopy[index].setVisible(false);
          ct.getPnlPaneHolder(1).remove(threeStackCardIcons[1]);
          threeStackCardIcons[1] = computerLabels[index];
          ct.getPnlPaneHolder(1).add(threeStackCardIcons[1]); 

          threeStackCards[1] = computerCards[index];  
          computerCardValue = 1000;
        }

        if(computerCardValue - 1 == Card.valueOfCard(threeStackCards[2]) || computerCardValue + 1 == Card.valueOfCard(threeStackCards[2]))
        {
          computerLabelsCopy[index].setVisible(false);
          ct.getPnlPaneHolder(2).remove(threeStackCardIcons[2]);
          threeStackCardIcons[2] = computerLabels[index];
          ct.getPnlPaneHolder(2).add(threeStackCardIcons[2]);

          threeStackCards[2] = computerCards[index];  
          computerCardValue = 1000;
        } 

       }// mousepressed
      });// end mouselistener

    }// end for loop
   }// end method
   static void generateThreeStackCards(CardGameFramework lcg, CardTable ct)
   {
      int k;

      for(k = 0;k < THREE_STACK_CARDS;k++)
      {
        ct.getPnlPaneHolder(k).remove(threeStackCardIcons[k]);     
      }

      for(k = 0;k < THREE_STACK_CARDS;k++)
      {
        threeStackCards[k] = lcg.getCardFromDeck();
        threeStackCardIcons[k] = new JLabel(GUICard.getIcon(threeStackCards[k]));
      }
      
      for(k = 0;k < THREE_STACK_CARDS;k++)
      {
        ct.getPnlPaneHolder(k).add(threeStackCardIcons[k]);
        ct.revalidate();
        ct.repaint();
      }
   }
    static void addCannotPlayBtn(CardGameFramework lcg, CardTable ct)
     {
      JButton icpBtn = new JButton("I cannot play");
      icpBtn.setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(Color.WHITE, 4),
               BorderFactory.createLineBorder(Color.decode("#028A0F"), 17)));
      icpBtn.setFocusPainted(false);
      // icpBtn.setMargin(new Insets(0,12,18,12));
      icpBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            iCantPlay++;
            if((iCantPlay % 2) == 1)
            {
              player1Score++;
              ct.getPnlScore().remove(scores[1]);
              scores[1] = new JLabel(Integer.toString(player1Score) + " - " + Integer.toString(player2Score), JLabel.CENTER);
              scores[1].setFont(new Font("Sans", Font.BOLD, 18));
              scores[1].setForeground(Color.WHITE);
              ct.getPnlScore().add(scores[1]);
              ct.revalidate();
              ct.repaint();

              ct.getPnlScore().remove(scores[2]);
              scores[2] = new JLabel("Player 2", JLabel.CENTER);
              scores[2].setFont(new Font("Sans", Font.BOLD, 16));
              scores[2].setForeground(Color.WHITE);
              ct.getPnlScore().add(scores[2]);
              ct.revalidate();
              ct.repaint();
            }
            if((iCantPlay % 2) == 0)
            {
              player2Score++;
              ct.getPnlScore().remove(scores[1]);
              scores[1] = new JLabel(Integer.toString(player1Score) + " - " + Integer.toString(player2Score), JLabel.CENTER);
              scores[1].setFont(new Font("Sans", Font.BOLD, 18));
              scores[1].setForeground(Color.WHITE);
              ct.getPnlScore().add(scores[1]);
              ct.revalidate();
              ct.repaint();

              ct.getPnlScore().remove(scores[2]);
              scores[2] = new JLabel("Player 1", JLabel.CENTER);
              scores[2].setFont(new Font("Sans", Font.BOLD, 16));
              scores[2].setForeground(Color.WHITE);
              ct.getPnlScore().add(scores[2]);
              ct.revalidate();
              ct.repaint();
            }
            if(iCantPlay == 4){
              generateThreeStackCards(lcg, ct);
              iCantPlay = 0;
            }
            
         }
      });
      ct.getPnlTimer().add(icpBtn);
     }
   // adds event listeners to the start and stop buttons
   static void addTimerBtnEvents(Timer t)
   {
     t.getBtnStart().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try{
            t.start();
          }
          catch(Exception ex){
            t.myresume();
          }
         }
      });

      t.getBtnStop().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            t.mysuspend();
         }
      });
   }
   public static void main(String[] args)
   {
     int numPacksPerDeck = 1;
     int numJokersPerPack = 2;
     int numUnusedCardsPerPack = 0;
     Card[] unusedCardsPerPack = null;

     // establish main frame in which program will run
     CardTable myCardTable 
         = new CardTable("Build Card Game by Miguel Nunez", NUM_CARDS_PER_HAND, NUM_PLAYERS);
     myCardTable.setSize(800, 600);
     myCardTable.setLocationRelativeTo(null);
     myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     // create a CardGameFramework object
     CardGameFramework LowCardGame = new CardGameFramework( 
          numPacksPerDeck, numJokersPerPack,  
          numUnusedCardsPerPack, unusedCardsPerPack, 
          NUM_PLAYERS, NUM_CARDS_PER_HAND);
     // create GUIcard object
     GUICard guiC = new GUICard();
     // create a Timer object
     Timer t = new Timer();
     // deal the cards
     LowCardGame.deal();
     // create labels
     fillJlabels(LowCardGame);
     // add event listeners
     addHumanEvents(LowCardGame, myCardTable); //PLAYER 1
     addComputerEvents(LowCardGame, myCardTable);//PLAYER 2     
     // add labels to panels
     addScore(myCardTable);
     addCardsToTable(myCardTable);
     // add the "I cannot play" button-----------------------------
     addCannotPlayBtn(LowCardGame, myCardTable);   
     // add the the panel from the Timer object onto myCardTable
     myCardTable.getPnlTimer().add(t.getPanel());
     // add an event listener to the start/stop button
     addTimerBtnEvents(t);
     // make everything visible to user
     myCardTable.setVisible(true);
    }
}

//class CardTable  -------------------------------------------------------------------
class CardTable extends JFrame 
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games


   private int numCardsPerHand;
   private int numPlayers;


   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlTimer, pnlScore;
   public JPanel[] paneHolder;


   public CardTable(String title, int numCardsPerHand, int numPlayers) 
   {
         //check that the data coming in is valid before proceeding 
         if((numCardsPerHand <= MAX_CARDS_PER_HAND) && (numPlayers <= MAX_PLAYERS))
         {
             this.numCardsPerHand = numCardsPerHand;
             this.numPlayers = numPlayers;
             //give the Jframe a title and set the gridLayout
             setTitle(title);

             //getRootPane().setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, Color.decode("#47d147")));
             setLayout(new GridBagLayout());
             GridBagConstraints c = new GridBagConstraints(); 
             c.fill = GridBagConstraints.BOTH;
             c.weightx = 1.0;
             c.weighty = 1.0;

             pnlScore = new JPanel();
             pnlScore.setLayout(new GridLayout(0,1, 0, 0));
             pnlScore.setBackground(Color.decode("#028A0F"));
             c.gridx = 0;
             c.gridy = 0;
             add(pnlScore, c);
             //initialize the pnlComputerHand JPanel and add it to the JFrame
             pnlComputerHand = new JPanel();
             pnlComputerHand.setLayout(new GridLayout(0,numCardsPerHand, 10, 0));
             pnlComputerHand.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10),"Player 1 Hand",TitledBorder.LEFT,TitledBorder.TOP,null,Color.WHITE));
             pnlComputerHand.setBackground(Color.decode("#028A0F"));
             c.gridx = 0;
             c.gridy = 1;
             add(pnlComputerHand, c);
             //initialize the pnlPlayArea JPanel and add it to the JFrame

             Border whiteline = BorderFactory.createLineBorder(Color.WHITE, 4);
             pnlPlayArea = new JPanel();
             paneHolder = new JPanel[3];
             for(int i  = 0;i < 3;i++)
             {
              paneHolder[i] = new JPanel();
              paneHolder[i].setBackground(Color.decode("#028A0F"));
              paneHolder[i].setBorder(whiteline);
              pnlPlayArea.add(paneHolder[i]);
             }
             //pnlPlayArea.setLayout(new GridLayout(0, 3, 20, -50));
             pnlPlayArea.setBackground(Color.decode("#028A0F"));
             pnlPlayArea.setBorder(whiteline);
             c.gridx = 0;
             c.gridy = 2;
             add(pnlPlayArea, c);

             //initialize the pnlHumanHand JPanel and add it to the JFrame
             pnlHumanHand = new JPanel();
             pnlHumanHand.setLayout(new GridLayout(0,numCardsPerHand, 10, 0));
             pnlHumanHand.setBackground(Color.decode("#028A0F"));
             pnlHumanHand.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10),"Player 2 Hand",TitledBorder.LEFT,TitledBorder.TOP,null,Color.WHITE));            
             c.gridx = 0;
             c.gridy = 3;
             add(pnlHumanHand, c);
             
             // create a JPannel for the timer 
             pnlTimer = new JPanel();
             pnlTimer.setBackground(Color.decode("#028A0F"));
             c.gridx = 0;
             c.gridy = 4;
             add(pnlTimer, c);

         }
   }


   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }

   public JPanel getPnlScore()
   {
      return pnlScore;
   }
   public JPanel getPnlComputerHand()
   {
      return pnlComputerHand;
   }

   public JPanel getPnlHumanHand()
   {
      return pnlHumanHand;
   }

   public JPanel getPnlPlayArea()
   {
      return pnlPlayArea;
   }

   public JPanel getPnlPaneHolder(int i)
   {
      return paneHolder[i];
   }

   public JPanel getPnlTimer()
   {
      return pnlTimer;
   }
}

// class Timer  ----------------------------------------------------------------------
class Timer extends Thread 
{
  private boolean suspendFlag;

  public JFrame frame;
  public JPanel panel;
  public JLabel label;
  public JButton btnStart, btnStop;

  public Timer() 
  {
    suspendFlag = false;

    panel = new JPanel();
    Border whiteline = BorderFactory.createLineBorder(Color.WHITE, 4);
    panel.setBorder(whiteline);
    panel.setBackground(Color.decode("#028A0F"));
    panel.setLayout(new GridBagLayout());        
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weighty = 1;

    label = new JLabel("00:00");
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Sans", Font.BOLD, 18));
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(label, gbc);

    btnStart = new JButton("Start");
    btnStart.setFocusPainted(false);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(btnStart, gbc);

    btnStop = new JButton("Stop");
    btnStop.setFocusPainted(false);
    gbc.gridx = 1;
    gbc.gridy = 1;
    panel.add(btnStop, gbc);
  }

  public void run()
  { 
    boolean isTrue = true;
    Long seconds = 0L;
    Long minutes = 0L;
      
    try
    {
      while(isTrue)
      {
        doNothing();
        seconds++;
        if(seconds == 60)
        {
          seconds = 0L;
        }
        if((seconds % 60) == 0)
        {
          minutes++;
        }
        // this adds a zero in the front of the second/minute if its less than 10
        if(minutes < 10)
          if(seconds < 10)
            label.setText(String.valueOf("0" + minutes +":"+ "0" + seconds));
          else
            label.setText(String.valueOf("0" + minutes +":"+ seconds));
        else
          if(seconds < 10)
            label.setText(String.valueOf(minutes +":"+ "0" + seconds));
          else
            label.setText(String.valueOf(minutes +":"+ seconds));
          
        synchronized(this)
        {
          while(suspendFlag)
          {
            wait();
          }
        }
      }//end while loop       
    }//end try
    catch (InterruptedException ex)
    {
      System.out.println("interrupted");
    }
  }

  public void doNothing() throws InterruptedException
  {
    Thread.sleep(1000);
  }

  synchronized void mysuspend()
  {
    suspendFlag = true;
  }
     
  synchronized void myresume()
  { 
    suspendFlag = false;
    notify();
  }

  public JPanel getPanel()
  {
    return panel;
  }

  public JLabel getLabel()
  {
    return label;
  }

  public JButton getBtnStart()
  {
    return btnStart;
  }

  public JButton getBtnStop()
  {
    return btnStop;
  }
}
// class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;


   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
                                    // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
                                    // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                        // in the game.  e.g. pinochle does not
                                        // use cards 2-8 of any suit


   public CardGameFramework( int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
   {
      int k;


      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
            numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
            / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;


      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);


      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];


      // prepare deck and shuffle
      newGame();
   }


   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }


   public Hand getHand(int k)
   {
      // hands start from 0 like arrays


      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();


      return hand[k];
   }


   public Card getCardFromDeck() { return deck.dealCard(); }


   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }


   public void newGame()
   {
      int k, j;


      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();


      // restock the deck
      deck.init(numPacks);


      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );


      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );


      // shuffle the cards
      deck.shuffle();
   }


   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;


      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();


      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }


      return enoughCards;
   }


   void sortHands()
   {
      int k;


      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }


   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
          cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);      
      }
   
      // return the card played
      return hand[playerIndex].playCard(cardIndex);
   
   }

   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;
     
       // Are there enough Cards?
       if (deck.getNumCards() <= 0)
          return false;


       return hand[playerIndex].takeCard(deck.dealCard());
   }


}

//class GUICard  ---------------------------------------------------------------------
class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;


   public GUICard()
   {
      //only proceed if iconsloaded is false
      if(iconsLoaded == false)
      {
         loadCardIcons();
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }           
   }


   static void loadCardIcons()
   {
      //create and array of values and an array of suits
      String numLetter[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X"};
      String suit[] = {"C", "D", "H", "S"};
      //use the two arrays with a nested loop to initialize the iconCards[][] 2D array
       for(int row = 0;row <  4;row++)
       {
           for(int col = 0;col < 14;col++)
           {
               iconCards[col][row] = new ImageIcon("images/" + numLetter[col] + suit[row] + ".gif");
           }
       }
   }
   public static int valueToInt(Card card)
   {
      return Card.valueOfCard(card);
   }
   public static int suitToNum(Card card)
   {
      Card.Suit cardSuit = card.getSuit();


      switch (cardSuit)
      {
         case clubs:
            return 0;
         case diamonds:
            return 1;
         case hearts:
            return 2;
         case spades:
            return 3;
         default:
            return -1;
      }
   }
   public static Icon getIcon(Card card)
   {
      return iconCards[valueToInt(card)][suitToNum(card)];
   }


   static public Icon getBackCardIcon()
   {
      return iconBack;     
   }


}
//class Card  ---------------------------------------------------------------------
class Card
{
  //Declare our variables
  public enum Suit {clubs, diamonds, hearts, spades};
  //NEWLY ADDED - to account for joker (X)
  //public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
  public static String valuRanks = "A23456789TJQKX";
  private char value;
  private Suit suit;
  private boolean errorFlag;
  //Default constructor 
  public Card()
  {
    value = 'A';
    suit = Card.Suit.spades;
    errorFlag = false;
  }
  //Overloaded constructor
  public Card(char value, Suit suit)
  {
    set(value, suit);
  }
  //NEWLY ADDED - to sort the cards using bubblesort
  static void arraySort(Card[] cards, int arraySize)
   {
      // Bubble sort cards using a temp variable and nested for loops
      int length = cards.length;
      Card temp;
      for(int i = 0; i < length; i++) 
      {
         for(int j = 1; j < (length-i); j++) 
         {
            if(cards[j-1].getValue() > cards[j].getValue()) 
            {
               temp = cards[j-1];
               cards[j-1] = cards[j];
               cards[j] = temp;
            }
         }
      }
   }
  
  static int valueOfCard(Card card)
  {


     // It traverses the valuRanks and check which matches the card value
     // Then it returns the index position as the value
     //System.out.print("Card Value: " + card.getValue() + "\n");
      if(valuRanks.indexOf(card.getValue()) > -1)
          return valuRanks.indexOf(card.getValue());
       else
          return -11;




  }
  //Returns true or false ( depending on validity of values )
  private boolean isValid(char value, Suit suit)
  { //check that value is one of these values, its not necessary to check for suit
    //because the program will NOT run without a valid suit
    if(value == 'A' || value == '2' || value == '3' || value == '4' || value == '5' ||
       value == '6' || value == '7' || value == '8' || value == '9' || value == 'T' ||
       value == 'J' || value == 'Q' || value == 'K' || value == 'X')
    {
       return true;
    }
    else
    {
      return false;
    }
  }
  //Creates a card if isValid() method is true, otherwise it does not
  public boolean set(char value, Suit suit)
  {
    if(isValid(value, suit))
    {
      this.value = value;
      this.suit = suit;
      errorFlag = false;
      return true;
    }
    else
    {
      errorFlag = true;
      return false;
    }
  }
  //Outputs the card if it's valid or "[ invalid ]"" if its not valid
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    if(getErrorFlag())
    {
      sb.append("[ invalid ]");
      return sb.toString();
    }
    else
    {
      sb.append(this.value).append(" of ").append(this.suit);
      return sb.toString();
    }
  }
  //Accessor for suit 
  public Suit getSuit()
  {
      return suit;
  }
  //Accessor for value
  public char getValue()
  {
    return value;
  }
  //Accesor for errorFlag
  public boolean getErrorFlag()
  {
    return errorFlag;
  }
  public boolean equals(Card card)
   {
      // Compare value and suit of this card to the other one
      if (card.getValue() == this.value && card.getSuit() == this.suit && card.getErrorFlag() == this.errorFlag)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}
//class Hand  --------------------------------------------------------------------
class Hand
{
  //Declare our variables
  public int MAX_CARDS = 56;
  private Card[] myCards;
  private int numCards;
  //Default constructor
  public Hand()
  {
    resetHand();
  }
  //NEWLY ADDED - will sort the hand by calling the arraySort() method in the Card class
  void sort()
  {
      Card.arraySort(myCards, numCards);
  }
  //NEWLY ADDED - will remove the card at a location and slide all of the cards down
  //one spot in the myCards array
  public Card playCard(int cardIndex)
  {
    if ( numCards == 0 ) //error
    {
       //Creates a card that does not work
       return new Card('M', Card.Suit.spades);
    }
    //Decreases numCards.
    Card card = myCards[cardIndex];
      
    numCards--;
    for(int i = cardIndex; i < numCards; i++)
    {
       myCards[i] = myCards[i+1];
    }
     
    myCards[numCards] = null;
      
    return card;
  }
  //Resets (deletes current contents) of myCards array 
  public void resetHand()
  {
    myCards = new Card[MAX_CARDS];
    numCards = 0;
  }
  //Adds a card to the next available position in the myCards array
  public boolean takeCard(Card card)
  {
    myCards[numCards] = card;
    numCards++;
    return true;
  }
  //Removes and returns the top card of the myCards array
  public Card playCard()
  {
    numCards--;
    Card topCard = myCards[numCards];
    return topCard;
  }
  //A stringizer that the client can use to display the entire hand
  public String toString()
  {
    String handString = "Hand = (";
    for(int i = 0;i < numCards;i++)
    {
      if (i != 0) handString += ", ";
      handString += myCards[i].getValue() + " of " + myCards[i].getSuit();
    }
    return handString + ")";
  }
  //Accessor for numCards
  public int getNumCards()
  {
    return numCards;
  }
  public Card inspectCard(int k) 
  {
     // Deliberately create an invalid card so we can have one with an error flag set
     Card card = new Card('b', Card.Suit.clubs);
     if (k <= numCards)
     {
        // Set the card to the one at k if there is a card at that location
        if (myCards[k] != null)
        {
          card = myCards[k];
        }
     }
      return card;
   }
}
//class Deck  ---------------------------------------------------------------------
class Deck
{ //Declare variables
  private static boolean alreadyExecuted = false;
  public final int MAX_CARDS = 336;
  private static Card[] masterPack;
  private Card[] cards;
  private int topCard;
  //Default constructor
  public Deck()
  {
    //Create a new empty deck of cards
    cards = new Card[56];
    //Method that initializes masterPack[] with all possible cards
    allocateMasterPack();
    //initialize cards[] with init(numPacks) method
    init(1);
  }
  //Overloaded constructor
  public Deck(int numPacks)
  {
    //Create a new empty deck of cards
    cards = new Card[56 * numPacks];
    //Method that initializes masterPack[] with all possible cards
    allocateMasterPack();
    //initialize cards[] with init(numPacks) method
    init(numPacks);         
  }
  //NEWLY ADDED - 
  boolean addCard(Card card)
  {
    // We only allow a card to be added if we will not exceed the maximum number of cards
    if (topCard < cards.length)
    {
      // Iterate and look for an open spot
      for (Card c : cards)
      {
      // We have found a spot.
        if (c == null)
        {
            c = card;
            topCard += 1; // Increment since we added a card
            // Successfully added the card so return true
            return true;
        }
      }
    }
      // We have maxed out our cards or failed to add a card and so cannot add another one.
      return false;
   }
   //NEWLY ADDED 
   boolean removeCard(Card card)
   {  
     // Search for the card
     for (Card c: cards)
     {
       // We have found the card to remove
       if (c.getValue() == card.getValue() && c.getSuit() == card.getSuit())
       {
          // Temp for swapping the top card into this one's location
          Card temp = cards[topCard];
            
          // Swap topcard temp into the spot
          c = temp;
            
          // Don't want duplicate top card
          cards[topCard] = null;
          topCard -= 1; // Decrement since we removed a card
          return true; // Successfully removed the card
       }
      }
      // Did not find the card to remove so return false
      return false;
   }
   //NEWLY ADDED
   void sort() 
   {
      Card.arraySort(cards, topCard);
   }
   //NEWLY ADDED
   int getNumCards()
   {
      int cardCount = 0;
      for (Card card : cards)
      {
         if (card != null)
         {
            cardCount += 1;
         }
         else
         {
            // If we hit an empty slot all subsequent slots are empty, so save cycles
            break;
         }
      }
      return cardCount;
   }
  //Initializes cards[] with the use of masterPack[]
  public void init(int numPacks) 
  {
    int numberOfCards = numPacks * 56;
    if(numberOfCards <= MAX_CARDS)
    {
      int count = 0;
      topCard = 56 * numPacks;


      for(int i = 0;i < numPacks;i++)
      {
        for(int j = 0;j < 56;j++)
        {
          cards[count] = masterPack[j];
          count++;
        }
      }
    }
  }
  //Shuffles the contents of cards[]
  public void shuffle()
  {
    Random rgen = new Random();     
 
    for (int i = 0; i < cards.length; i++) {
      int randomPosition = rgen.nextInt(cards.length);
      Card temp = cards[i];
      cards[i] = cards[randomPosition];
      cards[randomPosition] = temp;
    }
  }
  //Returns and removes the card in the top occupied position of cards[]
  public Card dealCard()
  {
    topCard--;
    Card topC = cards[topCard];
    return topC;
  }
  //Accessor for topCard
  public int getTopCard()
  {
    return topCard;
  }


  public Card inspectCard(int k)
  {
    // Use a bad card as a default
    Card card = new Card('R', Card.Suit.spades);


    if (k < topCard)
    {
      card = cards[k];
    }
    return card;
  }
  //Initializes masterPack[] with all the possible cards
  private static void allocateMasterPack()
  {
    //This must only be executed once
    if(!alreadyExecuted)
    {
      alreadyExecuted = true;


      masterPack = new Card[56];


      char[] number = {'A','2','3','4','5','6','7','8','9','T','J','Q','K','X'};
      int count  = 0;


      for(int i = 0;i < 4;i++)
      {
        for(int j = 0;j < 14;j++)
        {
          if(i == 0) masterPack[count] = new Card(number[j], Card.Suit.clubs);
          if(i == 1) masterPack[count] = new Card(number[j], Card.Suit.diamonds);
          if(i == 2) masterPack[count] = new Card(number[j], Card.Suit.hearts);
          if(i == 3) masterPack[count] = new Card(number[j], Card.Suit.spades);
          count++;
        }
      }
    }
  }   
}