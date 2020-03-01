package com.johnatti.fivecrownsandroid;

/*******************************************************
 Begins the card game of Five Crowns
 @author John Atti
 @since 10/27/2019
  ******************************************************** */

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class Game implements Serializable
{
   // *********************************************************
   // **************** Configuration Variables ****************
   // *********************************************************

   // *********************************************************
   // ******************** Class Constants ********************
   // *********************************************************

   // *********************************************************
   // ******************** Class Variables ********************
   // *********************************************************

   /** The round number for the current round */
   private int roundNumber;

   /** The coin toss result for the game */
   private int coinTossResult;

   /** players of this game */
   private Human human;
   private Computer computer;

   /** round object for the game */
   private Round round;

   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    Game object constructor
    @return Game object, with new human and computer players, as well as a round number
    */
   public Game(){
      // Create 2 players
      human = new Human();
      computer = new Computer();

      // initialize round number
      roundNumber=1;

   }
   // *********************************************************
   // ******************** Paint - View ***********************
   // *********************************************************

   // *********************************************************
   // ******************** actionPerformed - Controller *******
   // *********************************************************

   // *********************************************************
   // ******************** Selectors **************************
   // *********************************************************

   /** Returns the result of the previous coin toss
    @return coinTossResult integer representing the result of a coin toss
    */
   public int getTossResult(){
      return this.coinTossResult;
   }

   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /** Tosses a coin. If user guesses correctly, they go first.
    @return integer representing coin toss result (for other functions)
    */
   public int coinToss(){
      Random randomNumber = new Random();
      int ourRandomNumber = randomNumber.nextInt(100);
      ourRandomNumber=ourRandomNumber%2;
      setTossResult(ourRandomNumber);
      return ourRandomNumber;
   }

   /** Sets the coin toss result indicator */
   public void setTossResult(int tossResult){
      this.coinTossResult=tossResult;
   }

   /** Sets the round number in the round */
   public void setRoundNumber(int newRoundNumber){
      this.roundNumber=newRoundNumber;
   }


   /**
    Plays a round of the game. Deprecated function - for console.
    */
   public void play(){
      // Do a coin toss to determine first player
      human.reviewCoinToss(this.getTossResult());

      // For each round 1-11
      for(;roundNumber <=11; roundNumber++){
         // Create a Round object
         //Round round = new Round (human, computer, roundNumber);
         round = new Round (human, computer, roundNumber);

         // play the round
         round.play();
      }
   }

   /** Helps organize the human player order
    @param isUp boolean representing if the human is up */
   public void initializeHuman(boolean isUp){
      human.setUpNext(isUp);
   }

   /** Initializes the round itself (separated so the view can interact with it) */
   public void initializeRound(){
      round = new Round (human,computer,roundNumber);
   }

   /** Plays the round. Deprecated function, for console. */
   public void playRound(){
      this.round.play();
   }

   /** Returns the round object of the game
    @return round Round object of the game */
   public Round getRound(){
      return this.round;
   }

   /** Returns the human object of the game
    @return human Human object of the game */
   public Human getHuman(){
      return this.human;
   }

   /** Returns the computer object of the game
    @return computer Computer object of the game */
   public Computer getComputer(){
      return this.computer;
   }

   /** Returns the human hand vector of the game
    @return hand human hand's Vector<Card>  of the game */
   public Vector<Card> returnHumanHand(){
      return human.hand;
   }

   /** Returns the computer hand vector of the game
    @return hand computer hand's Vector<Card>  of the game */
   public Vector<Card> returnComputerHand(){
      return computer.hand;
   }

   /** Returns the draw pile vector of the game
    @return returnDrawPile Vector<Card> representing draw pile of the game */
   public Vector<Card> returnDrawPile(){
      return round.returnDrawPile();
   }

   /** Returns the discard pile vector of the game
    @return returnDiscardPile Vector<Card> representing discard pile of the game */
   public Vector<Card> returnDiscardPile(){
      return round.returnDiscardPile();
   }

   /** Returns the human hand string
    @return hand String representing human hand */
   public String displayHumanHand(){
      return human.hand.toString();
   }

   /** Returns the human hand string
    @return hand String representing computer hand */
   public String displayComputerHand(){
      return computer.hand.toString();
   }





   // *********************************************************
   // ******************** Code Generation ********************
   // *********************************************************

   // *********************************************************
   // ******************** Code Explanation *******************
   // *********************************************************

   // *********************************************************
   // ******************** Utility Methods ********************
   // *********************************************************

   // *********************************************************
   // ******************** Printing Methods *******************
   // *********************************************************

   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method for the object - starts the game and begins playing rounds
    @param args string value default parameter for main in java
    */
   public static void main( String args[] )
   {
      Game game = new Game();
//      game.initialize();
//      game.play();
   }
};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

