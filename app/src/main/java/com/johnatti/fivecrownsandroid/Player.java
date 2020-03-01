package com.johnatti.fivecrownsandroid;

/*******************************************************
 Base class of human and computer
 @author John Atti (really professor Kumar, though)
 @since 10/27/2019
  ******************************************************** */
import java.io.Serializable;
import java.util.Vector;
// for enumerating through the vectors
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;

public class Player implements Serializable
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

   /** The current score of the player */
   protected int score;

   /** The current hand of the player */
   protected Vector<Card> hand;

   /** The hand of the player under review (to be edited) */
   protected Vector<Card> reviewHand;

   /** The wild cards that the player holds */
   protected Vector<Card> playerWildCards;

   /** The non-wild cards that the player holds */
   protected Vector<Card> playerNormalCards;

   /** Leftover cards not in a book or run */
   protected Vector<Card> leftoverCards;

   /** Holds books for the player */
   protected Vector<Card> playerBooks;

   /** Holds potential books */
   protected Vector<Integer> potentialBooks;

   /** Holds runs for the player */
   protected Vector<Card> playerRuns;

   /** The order of the player */
   protected boolean upNext;

   /** The name of the player */
   protected String playerName;

   /** If a player is human */
   protected boolean isHuman;

   /** If a player is out */
   protected boolean isOut;

   /** Debugging variable */
   boolean debugOn=true;

   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************

   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************


   /**
    Player class constructor. Allocated initial hand size and score
    @return player object
    */
   public Player(){
      // Set the score to 0
      score=0;

      // Set upNext to false
      upNext=false;

      // Set isOut to false
      isOut=false;

      // Allocate to hand
      hand = new Vector<Card>(3,3);

      // Allocate to other hand types
      playerWildCards = new Vector<Card>(3,3);
      reviewHand = new Vector<Card>(3,3);
      playerBooks = new Vector<Card>(3,3);
      playerNormalCards = new Vector<Card>(3,3);
      playerRuns = new Vector<Card>(3,3);
      potentialBooks = new Vector<Integer>(3,3);

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
    Selector to return if player is out
    @return boolean representing if the player is out
    */
   public boolean isOut() {
      return isOut;
   }

   /**
    Selector to return score
    @return integer of score
    */
   public int getScore(){
      return score;
   }

   public String getScoreText(){
      return String.valueOf(score);
   }

   /**
    Selector to return if the player is up next
    @return boolean if player up next
    */
   public boolean getUpNext() {
      return upNext;
   }

   /**
    Selector to return player name
    @return string of player name
    */
   public String getPlayerName(){
      return playerName;
   }

   /**
    Selector to return if the player is human
    @return boolean if player is human
    Note: this is no longer used.
    */
   public boolean getHumanity() {
      return isHuman;
   }

   public Vector<Card> getHand(){
      return hand;
   }

   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /**
    Sets if the player is out
    @param isOut boolean representing if the player is out
    */
   public void setOut(boolean isOut) {
      this.isOut = isOut;
   }

   /**
    Sets the player name, if needed
    @param newName String representing player's new name
    */
   public void setName(String newName){
      this.playerName=newName;
   }

   /**
    Sets if the player is human
    @param ifHuman boolean variable declaring if the player is human
    Note: this is no longer used.
    */
   public void setHumanity(boolean ifHuman) {
      this.isHuman = ifHuman;
   }

   /**
    Sets if the player is up next
    @param upNext boolean variable declaring if the player is up next
    */
   public void setUpNext(boolean upNext) {
      this.upNext = upNext;
   }

   /**
    Adds a card to the player's hand
    @param newCard Card object to be added to the player hand
    */
   public void addCard(Card newCard){
      hand.add(newCard);
      Collections.sort(hand);
   }

   /**
    Adds a card to the player's hand without shuffling (for file load)
    @param newCard Card object to be added to the player hand
    */
   public void addExact(Card newCard){
      hand.add(newCard);
   }

   /**
    Allows the player to play a turn in a round
    @param drawPile vector of Card objects representing the draw pile
    @param discardPile vector of Card objects representing the discard pile
    @return int representing if the player can go out
    */
   public int play(Vector <Card> drawPile, Vector <Card> discardPile){

      // Draw a card from Draw Pile or Discard Pile
      drawCard(drawPile,discardPile);
      //drawCard(drawPile);
      // Discard a card
      discardCard(discardPile);

      // Check if all books and runs, and report if player can go out
      if(checkOut()){
         System.out.println("Player can go out.");
         return 0;
      }

      return 1;
   }

   /** Sets the player score */
   public void setScore(int newScore){
      score=newScore;
   }

   /**
    Updates the player score
    @param newScore integer representing the number to be added to the player score
    */
   public void addToScore(int newScore){
      score+=newScore;
   }

   /**
    Draws the first card in a draw pile and removes it from the draw pile
    @param drawPile vector of cards representing the draw pile
    @param discardPile vector of cards representing the discard pile.
    */
   public String drawCard(Vector<Card> drawPile, Vector<Card> discardPile){
      // Add the first card in draw pile
      addCard(drawPile.get(0));
      drawPile.remove(0);
      return "";
   }

   /**
    Draws the first card in any pile designated
    @param ourPile vector of cards representing the pile we want to draw from
    */
   public void drawFromPile(Vector<Card> ourPile){
      // Add the first card in draw pile
      addCard(ourPile.get(0));
      ourPile.remove(0);
   }

   /**
    Discards a player card
    @param discardPile vector of cards representing the discard pile
    */
   public String discardCard(Vector<Card> discardPile){
      // remove first card
      discardPile.insertElementAt(hand.get(0),0);
      hand.remove(0);
      return "";
   }

   /**
    Discards a specific card
    @param pile vector of cards representing the destination pile
    @param pileChoice integer representing the specific card being discarded
    */
   public void discardSpecific(Vector<Card> pile, int pileChoice){
      pile.insertElementAt(hand.get(pileChoice),0);
      hand.remove(pileChoice);
   }

   /**
    Checks if the player hand can go out
    @return boolean variable if player can go out
    */
   public boolean checkOut(){
      boolean isPlayerOut=false;
      int numberOfWilds=0;

      // count the number of wilds in our deck, and remove them from the temp hand
      numberOfWilds=this.countWilds(hand);

      // if 1 or 0 cards remain, then our entire hand is wild. We can return.
      if(hand.size()<=1){
         System.out.println("Your deck is full of wild cards!");
         isPlayerOut=true;
         setOut(true);
      }
      // check remaining hand create books and runs
      else{
         isPlayerOut=bookRunCheck(hand, numberOfWilds);
      }


      // add back in wild cards to hand
      for(Card ourCard:playerWildCards){
         hand.add(ourCard);

      }

      if(isPlayerOut){
         setOut(true);
         return isPlayerOut;
      }

      // TODO find out where the demo bug is here, and destroy it

      if(!isPlayerOut){
         // if cards are in both book and run, decide where to put them

         // if playerBooks are no longer full books, try to apply wild cards

         // if playerRuns are no longer runs, try to apply wild cards

         // if partial books/runs exist (that are not double counted), try to apply wild cards
         for(Card ourCard:hand){
            if(numberOfWilds>0 && ourCard.getPartialRun()){
               numberOfWilds--;
               ourCard.setInRun(true);
               ourCard.setInPartialRun(false);
            }
            if(numberOfWilds>0 && ourCard.getPartialBook()){
               numberOfWilds--;
               ourCard.setInBook(true);
               ourCard.setInPartialBook(false);
            }
         }
         // if all the wilds have been removed on book/run integration
//         if(numberOfWilds==0){
//            resetHandImportanceOnly();
//            // remove them from the hand
//            numberOfWilds=this.countWilds(hand);
//            // rerun book run check without them
//            isPlayerOut=bookRunCheck(hand,numberOfWilds);
//            // add them back in the hand
//            for(Card ourCard:playerWildCards){
//               hand.add(ourCard);
//            }
//            // check if out
//         }
      }

      return isPlayerOut;
   }

   /**
    Checks how many wild cards are in the player's hand, and separates them from the hand
    @param tempHand Vector of cards holding the same values as the player hand
    @return int representing wild cards
    */
   public int countWilds(Vector<Card> tempHand){
      // Ensure no wildcards remain from the last check
      playerWildCards.clear();

      // initialize number of wilds to 0
      int numberOfWilds=0;

      // iterate through deck, trimming and counting wildCards
      for(Iterator<Card> ourIterator = tempHand.iterator(); ourIterator.hasNext();){
         Card thisCard = ourIterator.next();
         if(thisCard.isWildCard() || thisCard.isJoker()){
            playerWildCards.add(thisCard);
            numberOfWilds+=1;
            ourIterator.remove();
         }
      }

      return numberOfWilds;
   }
   /**
    Checks if all cards are in books or runs
    @param tempHand Vector of cards holding the same values as the player hand, minus wildcards
    @param wildCount int representing the number of wildcards in the deck
    @return bool representing if all cards are in books or runs
    */
   public boolean bookRunCheck(Vector<Card> tempHand, int wildCount){
      boolean playerOut=true;

      // Keep track of the count of each face in the hand
      // create an array that has 1 space per card face
      Integer[] face = {0,0,0,0,0,0,0,0,0,0,0};

      // Iterate through cards and update the array so that it holds the number of each face
      for(Card ourCard:tempHand){
         face[ourCard.getFace()-3]+=1;
      }

      // If there are more than 3 of a face (including wildCards)
      int maxFace=Collections.max(Arrays.asList(face));
      if((maxFace+wildCount)>2){
         // see if we can create a book before runs
         playerOut = createBook(tempHand,wildCount);
      }

      // TODO: if there are books, remove the book cards that aren't POTENTIALLY needed for runs.

      // see if we can create a run
      createRun(tempHand, wildCount);

      // if all of the cards aren't in a book or a run, the player is not able to go out
      for(Card ourCard:tempHand){
         if(!ourCard.getInBook() && !ourCard.getInRun()){
            playerOut=false;
         }
      }
      return playerOut;
   }

   /**
    Checks if all cards are in runs
    @param tempHand Vector of cards holding the same values as the player hand, minus wildcards
    @param wildCount int representing the number of wildcards in the deck
    @return bool representing if all cards are in runs
    */
   public boolean createRun(Vector<Card> tempHand, int wildCount){

      // First, confirm there are enough cards to actually return a run
      if((tempHand.size()+wildCount)<3){
         return false;
      }

      // variable confirming that the cards can be in a run
      boolean runConfirmed=true;

      // iterate through the tempHand to mark cards in potential runs
      // TODO change to remove need for 2 passes
      for(Card ourCard:tempHand){
         isCardInRun(ourCard, tempHand, wildCount);
      }

      for(Card ourCard:tempHand){
         if(!isCardInRun(ourCard, tempHand, wildCount)){
            runConfirmed=false;
         }
         else{
            playerRuns.add(ourCard);
         }
      }
      return runConfirmed;
   }

   /**
    Checks if all cards are in runs
    @param tempHand Vector of cards holding the same values as the player hand, minus wildcards
    @param wildCount int representing the number of wildcards in the deck
    @return bool representing if all cards are in runs
    */
   public boolean isCardInRun(Card ourCard, Vector<Card> tempHand, int wildCount){

      // iterate through the hand, comparing it to the selected card
      for(Card compareCard:tempHand){
         if(ourCard.getSuit()==compareCard.getSuit()){

            // if we find the face card above ours
            if(((int)ourCard.getFace()+1)==compareCard.getFace()){

               // if that face card is in a run, we're part of the run
               if(compareCard.getInRun()){
                  ourCard.setInRun(true);
               }

               // either way, confirm there's a card above us
               ourCard.setRunAbove(true);
               ourCard.setInPartialRun(true);
            }

            // if we find the face card below ours
            else if(((int)ourCard.getFace()-1)==compareCard.getFace()){

               // if that card is in a run, we're part of the run
               if(compareCard.getInRun()){
                  ourCard.setInRun(true);
               }

               // either way, confirm there's a card below us
               ourCard.setRunBelow(true);
               ourCard.setInPartialRun(true);
            }
         }
      }
      // if we have a card above and below us, we're in a run
      if(ourCard.isRunAbove() && ourCard.isRunBelow()){
         ourCard.setInRun(true);
      }
      // if a card is in a run, you can remove that it's in a partial run
      if(ourCard.getInRun()){
         ourCard.setInPartialRun(false);
      }
      return ourCard.getInRun();
   }

   /**
    Checks if all cards are in books
    @param tempHand Vector of cards holding the same values as the player hand, minus wildcards
    @param wildCount int representing the number of wildcards in the deck
    @return bool representing if all cards are in books
    */
   public boolean createBook(Vector<Card> tempHand, int wildCount){
      boolean bookConfirmed=true;

      // Iterate through cards and update the array
      for(Card ourCard:tempHand){
         if(!isCardInBook(ourCard, tempHand, wildCount)){
            bookConfirmed=false;
         }
         else{
            playerBooks.add(ourCard);
         }
      }

      return bookConfirmed;
   }

   /**
    Checks if all cards are in books
    @param tempHand Vector of cards holding the same values as the player hand, minus wildcards
    @param wildCount int representing the number of wildcards in the deck
    @return bool representing if all cards are in books
    */
   public boolean isCardInBook(Card ourCard, Vector<Card> tempHand, int wildCount){
      int cardAppearances=0;
      // iterate through the deck, and see if a card face appears 3+ times
      for(Card compareCard: tempHand){
         if(compareCard.getFace()==ourCard.getFace()){
            cardAppearances+=1;
         }
         if(compareCard.getInBook()){
            ourCard.setInBook(true);
         }
      }
      if(cardAppearances<3 && !ourCard.getInBook()){

         ourCard.setInBook(false);
      }
      // changed from 2 to 1 - trying to see why books aren't found
      if(cardAppearances==1){
         ourCard.setInPartialBook(true);
      }
      // changed from >2 to >=2 - trying to see why partial books aren't found
      if(cardAppearances>=2){
         ourCard.setInBook(true);
         // remove partial book, since they're in a full book.
         ourCard.setInPartialBook(false);
      }

      return ourCard.getInBook();
   }

   /**
    Checks if all cards are in books
    @param cardToInspect card object being examined
    @return bool representing if the card is a useful addition to the hand
    */
   public String isCardUseful(Card cardToInspect){
      boolean cardIsUseful=true;
      String usefulString="yes";

      // if discard pile is wild or joker, take it
      if(cardToInspect.isWildCard()){
         usefulString="yes, it's wild";
         return usefulString;
      }
      else{
         reviewHand.clear();

         // create a copy of our hand
         for(Card ourCard:hand){
            reviewHand.add(ourCard);
         }
         reviewHand.add(cardToInspect);

         // get # of wild cards
         int ourWilds = countWilds(reviewHand);

         // update the discard card
         isCardInRun(cardToInspect, hand, ourWilds);
         isCardInRun(cardToInspect, hand, ourWilds);
         isCardInBook(cardToInspect,hand, ourWilds);
      }
      if(!cardToInspect.getInBook() && !cardToInspect.getInRun() && !cardToInspect.isRunAbove() && !cardToInspect.isRunBelow() && !cardToInspect.getPartialBook()){
         usefulString=(cardToInspect.toString() + " isn't in a book/run, and isn't wild.");
         return usefulString;
      }
      //return cardIsUseful;
      return usefulString;
   }

   /**
    Review cards and send ranked importance to player
    @return string listing card importance levels
    */
   public String updateCardImportance(){
      // Note: This function may contribute to the bug.
      // I think that setting the importance level to the default before adding to it
      // would help avoid increasing importance unnecessarily.
      String importanceInformation="";

      // iterate through hand
      for(Card ourCard: hand){
         // Check to see if a card is useful from the hand
         isCardUseful(ourCard);

         // Based on card inspection, update importance levels
         if(ourCard.getInBook()){
            ourCard.addImportanceLevel(20);
         }
         if(ourCard.getPartialBook()){
            ourCard.addImportanceLevel(10);
         }
         if(ourCard.getInRun()){
            ourCard.addImportanceLevel(20);
         }
         if(ourCard.isRunAbove()){
            ourCard.addImportanceLevel(10);
         }
         if(ourCard.isRunBelow()){
            ourCard.addImportanceLevel(10);
         }

         // Store result of findings in a string
         importanceInformation+=("\n\t" + ourCard.toString() + " importance: " + ourCard.getImportanceLevel());
      }

      // Return string for eventual output to user
      return importanceInformation;

   }

   /**
    Finds the weakest card in your hand
    @return int representing the weakest number in the hand
    */
   public int discardReview(){
      int minNum=999;
      for(Card ourCard:hand){
         if(ourCard.getImportanceLevel() < minNum){
            minNum=ourCard.getImportanceLevel();
         }
      }

      int index=0;
      for(Iterator<Card> ourIterator = hand.iterator(); ourIterator.hasNext();){
         index++;
         Card thisCard = ourIterator.next();
         if(thisCard.getImportanceLevel()==minNum){
            break;
         }
      }
      index--;
      return index;
   }

   /** Examines the discard pile to see which card should be picked
    @param discardCards Vector<Card> representing the discard pile
    @return String representing why the discard pile is good or bad to pick from. */
   public String examineDiscardPile(Vector<Card> discardCards){
      Card reviewCard=discardCards.get(0);
      String choiceReason="";
      // if card is useful
      if(isCardUseful(reviewCard).contains("yes")){
         //print out why
         choiceReason+="\nWe recommend you pick " + reviewCard.toString() + " from the discard pile because:";
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

      }
      else {
         choiceReason += "\nWe recommend you pick from the draw pile because the discard card does not contribute to books or runs.\n";
      }

      return choiceReason;

   }

   /** Help function that sets up displays for user
    @param cardPile Vector<Card> for the pile being passed in
    @param typeHelp char representing the type of help needed
    @return string representing the advice to be given to the player*/
   public String helpFunction(char typeHelp, Vector<Card> cardPile){
      // create the temp string
      String returnString="";

      // If the help function is for where to draw the card from
      if(typeHelp=='d'){

         // Examine the discard pile (visible) and report back to the player
         returnString+=examineDiscardPile(cardPile);
      }

      // If the player is asking which card to discard
      else if(typeHelp=='D'){

         // Update card importance and write back
         returnString+=updateCardImportance();

         // reset the cards so that their importance is not permanently augmented.
         resetCards();
      }


      return returnString;
   }

   /** Resets the importance level of a hand */
   public void resetHandImportanceOnly(){
      for(Card ourCard:hand){
         ourCard.refreshImportanceLevel();
      }
   }

   /** Resets all aspects of the cards in a hand */
   public void resetCards(){
      for(Card ourCard:hand){
         ourCard.resetCard();
      }
   }

   /** Gets the point values for the cards in a hand
    @param tempHand the Vector of cards being examined
    @return totalPoints int representing the total point values of the hand
    */
   protected int getCardPoints(Vector<Card> tempHand){
      // iterate through hand, and tally the points of all cards not in a book/run
      int totalPoints=0;
      for(Card ourCard:tempHand){
         if(!ourCard.getInBook() && !ourCard.getInRun()){
            totalPoints+=ourCard.getPointValue();
         }
      }
      return totalPoints;
   }

   /** Gets the point values for the cards in the player hand
    @return totalPoints int representing the total point values of the hand
    */
   protected int getFinalPoints(){
      if(hand.isEmpty()){
         return 0;
      }
      if(this.isOut()){
         return 0;
      }
      int totalPoints=0;
      resetCards();
      checkOut();
      for(Card ourCard:hand){
         if(!ourCard.getInBook() && !ourCard.getInRun()){
            totalPoints+=ourCard.getPointValue();
         }
      }
      return totalPoints;
   }


   /** Clears all hands of the player. */
   protected void clearHands(){
      hand.clear();
      playerWildCards.clear();
      reviewHand.clear();
      playerBooks.clear();
      playerNormalCards.clear();
      playerRuns.clear();
      potentialBooks.clear();
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
    Prints the current hand of the player with numbers next to it, for user prompts
    */
   protected void printHand(){
      String result = "";
      for(int index=0;index<hand.size();index++){
         result+=(index+1)+". " + hand.get(index) + " ";
      }
      System.out.println(result);
   }
   // *********************************************************
   // ******************** Printing Methods *******************
   // *********************************************************

   /**
    Prints the player hand to a string
    @return String variable with player cards separated by spaces
    */
   public String toString(){
      String result = "";
      for(Card ourCard:hand){
         result += ourCard.toString()+ " ";
      }

      return result;
   }
   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method for the object - deals cards to player
    @param args string value default parameter for main in java
    */
   public static void main( String args[] )
   {
      Deck deck=new Deck();
      Player player = new Player();
      for(int count =0; count < 10 ; count++){
         player.addCard(deck.dealCard());
         //System.out.println(player.toString());
      }

      System.out.println(player.toString());
      System.out.println("Player out?: " + player.checkOut());
      //System.out.println("Player hand points: " + player.getCardPoints(player.hand));
      System.out.println("Player hand points: " + player.getFinalPoints());
      System.out.println("Player books: " + player.playerBooks.toString());
      System.out.println("Player runs: " + player.playerRuns.toString());

   }

};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

// for(Card c : tempHand){
//     if(c.isWildCard()){
//         //System.out.println(c.toString() + " is wild!");
//         playerWildCards.add(c);
//         numberOfWilds+=1;
//     }
// }