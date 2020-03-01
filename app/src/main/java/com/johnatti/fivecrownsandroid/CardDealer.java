package com.johnatti.fivecrownsandroid;

/*******************************************************
 Deals cards from multiple decks
 @author John Atti
 @since 10/27/2019
  ******************************************************** */
import java.io.Serializable;
import java.util.Random;

public class CardDealer implements Serializable
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

   /** The two decks from which cards are dealt */
   private Deck firstDeck,secondDeck;

   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    CardDealer object constructor
    @return returns Carddealer object with 2 decks.
    */
   public CardDealer(){
      // Create and shuffle the two decks
      firstDeck = new Deck();
      secondDeck = new Deck();
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
   /**
    Deals cards randomly from both decks until empty
    @return returns a Card object until no cards are left from either deck, then returns null.
    */
   public Card dealCard(){
      // If the first deck is empty
      if(firstDeck.isEmpty()){
         // and the second deck is also empty
         if(secondDeck.isEmpty()){
            // return null
            return null;
         }
         else{
            // If the second deck isn't also empty, deal from it
            return secondDeck.dealCard();
         }
      }
      // If the first deck isn't empty
      else{
         // and the second deck is empty
         if(secondDeck.isEmpty()){
            // return a card dealt from the first deck
            return firstDeck.dealCard();
         }
         // If both decks are full
         else{
            // randomly deal cards from both decks
            int choice = new Random().nextInt()%2;
            if(0==choice){
               return firstDeck.dealCard();
            }
            else{
               return secondDeck.dealCard();
            }
         }
      }


   }
   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /**
    Mutator to update wildcards in all decks.
    @param face integer value representing the card face that will become a wildcard
    */
   public void updateWildCards(int face){
      firstDeck.updateWildCards(face);
      secondDeck.updateWildCards(face);
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

   /**
    toString function to print the contents of the decks
    @return a string value representing the decks being dealt
    */
   public String toString(){
      return "Deck 1:\n" + firstDeck.toString()
            + "\n"
            + "Deck 2:\n" + secondDeck.toString();
   }
   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method for the object - prints deck contents
    @param args string value default parameter for main in java
    */
   public static void main( String args[] )
   {
      // make a new CardDealer object
      CardDealer cardDealer = new CardDealer();

      // print out the decks
      System.out.println(cardDealer.toString());

      // print out random cards to be dealt, from the decks
      for (int count = 0; count < 20; count++){
         System.out.print(cardDealer.dealCard().toString() + " " );
      }
   }
};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

