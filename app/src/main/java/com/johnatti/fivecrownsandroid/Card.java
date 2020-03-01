package com.johnatti.fivecrownsandroid;

/*******************************************************
 Creates a card object
 @author John Atti
 @since 10/27/2019

  ******************************************************** */

import java.lang.Comparable;
import java.util.Observable;

public class Card implements Comparable <Card>
{
   // *********************************************************
   // **************** Configuration Variables ****************
   // *********************************************************

   // *********************************************************
   // ******************** Class Constants ********************
   // *********************************************************
   public static final char DEFAULT_SUIT='X';
   public static final int DEFAULT_FACE=0;
   // *********************************************************
   // ******************** Class Variables ********************
   // *********************************************************

   /** suit char representing the suit of the card: s/c/d/h/t */
   private char suit;

   /** face int representing the face of the card: 3-13 */
   private int face;

   /** Wild card */
   private boolean wildCard;

   /** Joker card */
   private boolean isJoker;

   /** Is the card in a book? */
   private boolean isInBook;

   /** Is the card a partial book? */
   private boolean isPartialBook;

   /** Is the card a partial book? */
   private boolean isPartialRun;

   /** Is the card in a run? */
   private boolean isInRun;

   /** Is it in a run with a card above it */
   private boolean isRunCardAbove;

   /** Is it in a run with a card below it */
   private boolean isRunCardBelow;

   /** int representing point values (modifies to reflect joker/wild status) */
   private int pointValue;

   /** int representing how important a card is in books/runs */
   private int importanceLevel;

   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    Constructor for the card class. Sets face/suit/wildcard status
    @param inSuit the new suit that is being set for the card
    @param inFace the new card face that is being set for the card
    */
   public Card(char inSuit, int inFace) {
      // ensure default importance level
      importanceLevel = 0;

      // set the card face
      if (! setFace(inFace))
         System.out.println("Card class reports incorrect face " + inFace );

      // set the card suit
      if ( ! setSuit(inSuit))
         System.out.println("Card class reports incorrect suit " + inSuit );

      // ensure card importance variables are set by defaults
      refreshImportanceLevel();

   }

   /**
    Constructor for the card class. Sets face/suit/wildcard status. For loading file cards in.
    @param inSuit the new suit that is being set for the card
    @param inFace the new card face that is being set for the card
    */
   public Card(char inSuit, int inFace, int roundNumber)
   {

      // ensure default importance level
      importanceLevel = 0;

      // set the card face
      if (! setFace(inFace))
         System.out.println("Card class reports incorrect face " + inFace );

      // set the card suit
      if ( ! setSuit(inSuit))
         System.out.println("Card class reports incorrect suit " + inSuit );

      // update wildCard status on card generation
      updateWildCards(roundNumber+2);

      refreshImportanceLevel();

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
    Selector for card suit
    @return returns a char for the suit value of the card
    */
   public char getSuit()
   {
      return suit;
   }

   /**
    Selector for card face
    @return returns an int for the face value of the card
    */
   public int getFace()
   {
      return face;
   }

   /**
    compareTo function override to sort cards based on their faces
    @param right Card object variable representing the card to the right of the current card
    @return int representing whether or not the current card face is higher or lower than the right card
    */
   public int compareTo(Card right){
      // First, sort by face so cards appear in numerical order
      if(face < right.face){
         return -1;
      }
      if(face > right.face){
         return 1;
      }
      // If faces are equal, sort by suit
      if(suit < right.suit){
         return -1;
      }
      if(suit > right.suit){
         return 1;
      }
      // If everything is equal, no further sorting is needed
      return 0;
   }

   /**
    Selector for  the wildcard status of the card
    @return boolean value representing if the card is wild or not
    */
   public boolean isWildCard(){
      return wildCard;
   }

   /**
    Selector to return the importance level of a card
    @return int indicating how important a card is (higher numbers = more important)
    */
   public int getImportanceLevel() {
      return importanceLevel;
   }

   /**
    Selector to return if a card is a joker
    @return boolean value represenging if the card is a joker
    */
   public boolean isJoker() {
      return isJoker;
   }

   /**
    Selector to return if a card is in a book
    @return boolean value represenging if the card is in a book
    */
   public boolean getInBook() {
      return isInBook;
   }

   /**
    Selector to return if a card is in a partial book
    @return boolean value represenging if the card is in a partial book
    */
   public boolean getPartialBook() {
      return isPartialBook;
   }

   /**
    Selector to return if a card is in a partial book
    @return boolean value represenging if the card is in a partial book
    */
   public boolean getPartialRun() {
      return isPartialRun;
   }

   /**
    Selector to return if a card is in a run
    @return boolean value representing if the card is in a run
    */
   public boolean getInRun() {
      return isInRun;
   }

   /**
    Selector to return if there's a run card above this
    @return boolean value representing if the card above it is part of a run
    */
   public boolean isRunAbove() {
      return isRunCardAbove;
   }

   /**
    Selector to return if there's a run card below this
    @return boolean value representing if the card below it is part of a run
    */
   public boolean isRunBelow() {
      return isRunCardBelow;
   }

   /**
    Selector to return a card's point value
    @return int value representing a card's point value
    */
   public int getPointValue() {
      return pointValue;
   }


   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /**
    Mutates the suit value of the card
    @param inSuit char parameter for the new suit of the card
    @return boolean return representing if the card suit is valid
    */
   public boolean setSuit( char inSuit )
   {
      // Just to be sure, converting to uppercase
      inSuit = Character.toUpperCase( inSuit );
      boolean status = false;

      switch (inSuit )
      {
         case 'C':
         case 'D':
         case 'H':
         case 'S':
         case 'T':
            suit = inSuit;
            status = true;
            break;
         case '1':
         case '2':
         case '3':
            // check if a joker is present
            if(face==50){
               status=true;
               suit = inSuit;
               wildCard=true;
               isJoker=true;
               break;
            }
            // otherwise, the card is invalid
         default:
            suit=DEFAULT_SUIT;
            status= false;
      }
      return status;
   }

   /**
    Mutator to set the card's face
    @param inFace int representing the new card face
    @return boolean value represenging if the face is valid.
    */
   public boolean setFace(int inFace)
   {
      boolean status = false;

      if ((inFace >= 3 && inFace <=13) || inFace==50){
         face = inFace;
         status=true;
         pointValue=face;
      }
      else{
         face = DEFAULT_FACE;
         status = false;
         pointValue=0;
      }
      return status;
   }

   /**
    Mutator to change the wildcard status of the card
    @param inFace int value representing the card face to check against
    */
   public void updateWildCards(int inFace){
      if(face==inFace){
         wildCard=true;
         pointValue=20;
         addImportanceLevel(100);
      }
   }

   /**
    Mutator to refresh the importance level of a card based on its starting status
    */
   public void refreshImportanceLevel(){
      this.importanceLevel=0;
      int newLevel=0;
      if(isWildCard() || isJoker()){
         newLevel+=100;
      }
      newLevel+=Math.abs(20-face);

      addImportanceLevel(newLevel);
   }


   /**
    Mutator to change the importance level of a card
    @param newImportanceLevel integer for additional importance to be added
    */
   public void addImportanceLevel(int newImportanceLevel){
      this.importanceLevel += newImportanceLevel;
   }

   /**
    Mutator to indicate if a card is a joker
    @param jokerStatus boolean representing if a card is a joker
    */
   public void setJoker(boolean jokerStatus){
      this.isJoker = jokerStatus;
   }

   /**
    Mutator to indicate if a card is in a book
    @param isInBook boolean represenging if a card is in a book
    */
   public void setInBook(boolean isInBook){
      this.isInBook = isInBook;
   }

   /**
    Mutator to indicate if a card contributes to a book
    @param contributesToBook boolean represenging if a card contributes to book
    */
   public void setInPartialBook(boolean contributesToBook){
      this.isPartialBook = contributesToBook;
   }

   /**
    Mutator to indicate if a card contributes to a run
    @param contributesToRun boolean representing if a card is only in a partial run
    */
   public void setInPartialRun(boolean contributesToRun){
      this.isPartialRun = contributesToRun;
   }

   /**
    Mutator to indicate if a card is in a run
    @param isInRun boolean representing if a card is in a run
    */
   public void setInRun(boolean isInRun) {
      this.isInRun = isInRun;
   }

   /**
    Mutator to indicate if a card above it is part of a run
    @param isRunAboveThis boolean representing if a card above it is in a run
    */
   public void setRunAbove(boolean isRunAboveThis) {
      this.isRunCardAbove = isRunAboveThis;
   }

   /**
    Mutator to indicate if a card below it is part of a run
    @param isRunBelowThis boolean representing if a card below it is in a run
    */
   public void setRunBelow(boolean isRunBelowThis) {
      this.isRunCardBelow = isRunBelowThis;
   }

   /**
    Mutator to reset if neighboring cards are in a run
    */
   public void resetRunStatus() {
      this.isInRun = false;
      this.isRunCardAbove = false;
      this.isRunCardBelow = false;
   }

   /**
    Mutator to reset book status
    */
   public void resetBookStatus() {
      this.isInBook = false;
      this.isPartialBook = false;
   }

   /**
    Mutator to reset a card's status
    */
   public void resetCard() {
      // reset run status
      this.isInRun = false;
      this.isRunCardAbove = false;
      this.isRunCardBelow = false;

      // reset book status
      this.isInBook = false;
      this.isPartialBook = false;

      // reset importance
      this.refreshImportanceLevel();
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
    Creates a string representation of the card, matching suit number to symbols as needed.
    @return string value representing the card.
    */
   public String toString()
   {
      switch (face) {
         case 10:
            return "X" + Character.toString(suit);
         case 11:
            return "J" + Character.toString(suit);
         case 12:
            return "Q" + Character.toString(suit);
         case 13:
            return "K" + Character.toString(suit);
         case 50:
            return "J" + Character.toString(suit);
         default:
            return face + Character.toString(suit);
      }

   }
   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debug method to print all cards
    @param args string array default parameter for main
    */
   public static void main( String args[] )
   {
      // verify the validity
      char suitArray[] = {'s','S','c','d','T','h','a','b', '1', '2', '3', '2'};
      int faceArray[] = {3,2,5,10,13, 8,4,9, 50,50,50, 9};

      for (int counter = 0; counter < suitArray.length; counter++){
         Card card = new Card(suitArray[counter],faceArray[counter]);
         System.out.println(card.toString() + "\tpts: " + card.pointValue + "\timp: " + card.getImportanceLevel());
      }
   }

};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

