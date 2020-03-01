package com.johnatti.fivecrownsandroid;

/*******************************************************
 Creates a computer player.
 @author John Atti
 @since 11/13/2019
  ******************************************************** */

import java.util.Scanner;
import java.util.Vector;

public class Computer extends Player
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

   /** Scanner variable to intake user input */
   private Scanner scanner;

   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    Human player constructor. Sets up scanner for user input.
    */
   public Computer(){
      // Ensure parent class is initialized
      super();

      // Create a scanner class for input
      scanner = new Scanner(System.in);

      // Assign player name
      playerName = "Computer";

      // Set humanity
      isHuman = false;
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

   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /**
    Draws a card from either the draw or discard pile according to user input.
    @param drawPile Vector of card objects representing the draw pile
    @param discardPile Vector of card objects representing the discard pile
    */
   public String drawCard(Vector<Card> drawPile, Vector<Card> discardPile){
      Card reviewCard=discardPile.get(0);
      // if card is useful
      if(isCardUseful(reviewCard).contains("yes")){
         String choiceReason="";
         //print out why, based on the card's status (book/run, partial book/run, wildcard/joker, etc)
         choiceReason+="\nComputer is picking " + reviewCard.toString() + " from the discard pile because:";
         if(reviewCard.isWildCard()){
            choiceReason+="\n\tThis card is a wildcard or joker.";
         }
         if(reviewCard.getPartialBook() || reviewCard.getInBook()){
            choiceReason+="\n\tThis card could be part of a book.";
         }
         if(reviewCard.getInRun()){
            choiceReason+="\n\tThis card could be part of a run.";
         }
         if(reviewCard.isRunAbove()){
            choiceReason+="\n\tThis card could help form a run.";
         }
         if(reviewCard.isRunBelow()){
            choiceReason+="\n\tThis card could help form a run.";
         }
         choiceReason+="\n";

         // take the top card from the discard pile
         addCard(discardPile.get(0));
         // remove the card from the discard pile
         discardPile.remove(0);
         return choiceReason;

      }
      else{
         String drawReason="";
         // explain why card isn't useful
         drawReason+="Computer is picking " + drawPile.get(0).toString() + " from the draw pile because the discard card does not contribute to its books or runs.\n";
         // take the top card from the pile
         addCard(drawPile.get(0));
         // remove the card from the draw pile
         drawPile.remove(0);
         return drawReason;
      }
   }

   /**
    Discards a card to the discard pile, based on user selection
    @param discardPile Vector of card objects representing the discard pile
    */
   public String discardCard(Vector<Card> discardPile){
      String computerLogicUpdate="";
      // Display user hand and prompt for card selection
      computerLogicUpdate+="Computer is deciding which card to discard based on the following presumed level of importance: ";
      resetCards();
      computerLogicUpdate+=updateCardImportance();

      int response = discardReview();

      // reset the card before it goes back into the discard pile
      // resetCards();
      hand.get(response).resetCard();

      // Add the chosen card to the discard pile
      discardPile.insertElementAt(hand.get(response),0);

      // remove the chosen card from the player hand
      computerLogicUpdate+=("\nThe computer discards " + hand.get(response).toString());
      hand.remove(response);

      return computerLogicUpdate;

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
   /**
    Confirms that integer-required input from user is valid
    @return integer representing the valid input
    Note: method not needed - this was previously utilized before being converted from a Player class
    */
   public int returnValidInt(){
      int ourInt;
      // confirm the scanner has accepted a valid integer.
      // While input is invalid, prompt for new input
      while(!scanner.hasNextInt()){
         String invalidInput = scanner.next();
         System.out.println("Invalid input. Please try again. You entered "
               + invalidInput
               + ", but we need an integer.");
      }
      // Once valid input is confirmed from scanner, record it into ourInt
      ourInt=scanner.nextInt();
      // Ensure our scanner is cleared in case the user entered multiple valid ints on one line
      scanner.nextLine();
      return ourInt;
   }
   // *********************************************************
   // ******************** Printing Methods *******************
   // *********************************************************

   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method for the object
    @param args string value default parameter for main in java
    */
   public static void main( String args[] )
   {

   }
};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

