package com.johnatti.fivecrownsandroid;

import java.util.Scanner;
import java.util.Vector;

/*******************************************************
 Creates a human player.
 @author John Atti
 @since 10/27/2019
  ******************************************************** */
public class Human extends Player
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
   public Human(){
      // Ensure parent class is initialized
      super();

      // Create a scanner class for input
      scanner = new Scanner(System.in);

      // Assign player name
      playerName = "Human";

      // Assign humanity
      isHuman = true;
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

   /** Allows the player to interact with the coin toss.
    @param coinResult int representing the coin toss result
    */
   public void reviewCoinToss(int coinResult){
      int response;
      // Have the human guess the side of the coin before it's known
      do{
         System.out.println("Choose a card.\n1. Heads\n2.Tails\n");
         response=this.returnValidInt();
      }while(response!=1 && response !=2);

      // compare human guess to coin flip
      // if it's the same, the human is up next
      if((response-1)==coinResult){
         System.out.println("You guessed correctly! You go first.");
         setUpNext(true);
      }
      else{
         System.out.println("You guessed incorrectly! You go second.");
      }
   }

   /**
    Draws a card from either the draw or discard pile according to user input.
    @param drawPile Vector of card objects representing the draw pile
    @param discardPile Vector of card objects representing the discard pile
    */
   public String drawCard(Vector<Card> drawPile, Vector<Card> discardPile){
      // Ask whether to draw from drawPile or discardPile
      int response;
      do{
         System.out.println("Please enter 0 for draw pile and 1 for discard pile: ");
         // confirm valid input has been received
         response=this.returnValidInt();
      }while(response!=0 && response!=1);

      // If user selected draw pile
      if(response==0){
         // take the top card from the pile
//         addCard(drawPile.get(0));
         // remove the card from the draw pile
//         drawPile.remove(0);
         drawFromPile(drawPile);
      }
      // If the user selected the discard pile
      else{
         // take the top card from the discard pile
//         addCard(discardPile.get(0));
         // remove the card from the discard pile
//         discardPile.remove(0);
         drawFromPile(discardPile);
      }
      return "";
   }




   /**
    Discards a card to the discard pile, based on user selection
    @param discardPile Vector of card objects representing the discard pile
    @return String value represengint which card is discarded
    Note: this method is deprecated, as the human now clicks on the view/controller.
    */
   public String discardCard(Vector<Card> discardPile){
      // Display user hand and prompt for card selection
      int response;
      do{
         printHand();
         System.out.println("Please enter which card to discard: 1-> " + hand.size());
         // confirm valid input from user
         response=this.returnValidInt();
      } while(!(response>=1 && response<=hand.size()));

      // Add the chosen card to the discard pile
      discardPile.insertElementAt(hand.get(response-1),0);

      // remove the chosen card from the player hand
      hand.remove(response-1);

      return "";

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


