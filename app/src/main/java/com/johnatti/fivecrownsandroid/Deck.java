package com.johnatti.fivecrownsandroid;


/*******************************************************
 Creates deck of card objects and updates cards as needed.
 @author John Atti
 @since 10/27/2019
  ******************************************************** */
import java.util.Vector;
import java.util.Collections;

public class Deck
{
   // *********************************************************
   // **************** Configuration Variables ****************
   // *********************************************************

   // *********************************************************
   // ******************** Class Constants ********************
   // *********************************************************

   /** These are the suits of the cards in this deck */
   public static final char SUITS[] = {'s','c','d','h','t'};

   /** These are the faces of the cards in this deck  */
   public static final int MIN_FACE = 3, MAX_FACE = 13;

   /** Deck size  */
   public static final int DECK_SIZE = 58;

   // *********************************************************
   // ******************** Class Variables ********************
   // *********************************************************

   /** The vector that holds all the cards in the deck */
   private Vector<Card> cardVector;


   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    Deck class constructor - creates the deck
    @return returns a deck object
    */
   public Deck()
   {
      // Allocate space to the vector of cards
      cardVector = new Vector<Card> (DECK_SIZE);

      // Create a card of each face/suit and add it to the deck
      for (int suitIndex = 0; suitIndex < SUITS.length; suitIndex++){
         for(int faceIndex = MIN_FACE; faceIndex <= MAX_FACE; faceIndex++){
            cardVector.add(new Card(SUITS[suitIndex],faceIndex));
         }
      }
      // Add jokers
      cardVector.add(new Card('1',50));
      cardVector.add(new Card('2',50));
      cardVector.add(new Card('3',50));

      // shuffle the deck
      Collections.shuffle(cardVector);
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
    Checks to see if the card deck is empty
    @return boolean value representing if the deck is empty
    */
   public boolean isEmpty(){
      return cardVector.size()==0;
   }
   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /**
    Pulls a card from the deck, to deal it.
    @return Card object that is simultaneously removed from the Deck. Returns null if deck is empty.
    */
   public Card dealCard(){
      // If deck is empty, return null
      if ( isEmpty()){
         return null;
      }

      // Get a reference to the first card
      Card returnCard = cardVector.get(0);

      // Remove the card from the cardVector
      cardVector.remove(0);

      // Return the reference to the removed first card
      return returnCard;

   }

   /**
    Updates all of the wild cards in a deck through the card class
    @param face integer representing the round number+2. Indicates which card should be made a wild card.
    */
   public void updateWildCards(int face){
      for (Card card:cardVector){
         card.updateWildCards(face);
      }
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
    Function to output the deck to string
    @return string value representing the deck
    */
   public String toString(){
      String result = "";
      for(int count = 0; count < cardVector.size(); count++){
         result += cardVector.get(count).toString()+ " ";
         // modified to insert a newline after every 10 entries, uniformly
         if((count+1) % 11 ==0 ){
            result+="\n";
         }
      }
      return result;
   }
   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method to print the deck
    @param args string value, default parameter for java main
    */
   public static void main( String args[] ){
      Deck deck = new Deck();
      System.out.println(deck.toString());

      Card nextCard = deck.dealCard();
      while(nextCard != null){
         System.out.print(nextCard.toString()+ " ");
         nextCard=deck.dealCard();
      }
      System.out.println();
   }
};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************


