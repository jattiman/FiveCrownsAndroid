package com.johnatti.fivecrownsandroid;

/*******************************************************
 Runs one round of 5Crowns game
 @author John Atti
 @since 10/27/2019
  ******************************************************** */
import java.io.Serializable;
import java.util.Vector;
import java.util.regex.*;

public class Round implements Serializable
{
   // *********************************************************
   // **************** Configuration Variables ****************
   // *********************************************************

   // *********************************************************
   // ******************** Class Constants ********************
   // *********************************************************

   /** constant representing the TOTAL number of players in this game */
   public static final int TOTAL_PLAYERS = 2;

   // *********************************************************
   // ******************** Class Variables ********************
   // *********************************************************

   /** The number of the round */
   private int roundNumber;

   /** The array of players of this round */
   private Player[] playerList;

   /** The card dealer for this rond */
   private CardDealer cardDealer;

   /** The draw pile for this round */
   private Vector<Card> drawPile;

   /** The discard pile for this round */
   private Vector<Card> discardPile;

   /** Holds the string of our file to be parsed */
   private Vector<String> fileString;

   /** Holds the next player up (modded in game) */
   private int nextPlayerIndex;

   Human ourHuman;

   Computer ourComputer;


   // *********************************************************
   // ******************** GUI Components *********************
   // *********************************************************



   // *********************************************************
   // ******************** Constructor ************************
   // *********************************************************

   /**
    Round class constructor
    @param human Human player class representing the human player
    @param computer Computer player class representing the human player
    @param inRoundNumber int representing the round number
    */
   public Round(Human human, Computer computer, int inRoundNumber){

      // Set a local copy of roundNumber
      roundNumber = inRoundNumber;

      // Reference the 2 players
      ourHuman=human;
      ourComputer=computer;

      // Add them to an iteration array
      playerList=new Player[TOTAL_PLAYERS];
      playerList[0] = human;
      playerList[1] = computer;

      // Change nextPlayerIndex for round start accordingly, based on coin toss result
      //if(human.upNext){
      if(ourHuman.upNext){
         nextPlayerIndex=0;
      }
      else{
         nextPlayerIndex=1;
      }

      // Create a CardDealer
      cardDealer = new CardDealer();

      // Update wild cards
      cardDealer.updateWildCards(roundNumber+2);

      // Deal cards to the 2 players
      for (int count = 0; count < roundNumber +2; count++){
         playerList[0].addCard(cardDealer.dealCard());
         playerList[1].addCard(cardDealer.dealCard());
      }

      // Deal rest of the cards to draw pile
      drawPile = new Vector<Card>(Deck.DECK_SIZE*2);
      Card nextCard = cardDealer.dealCard();
      while(nextCard!= null){
         drawPile.add(nextCard);
         nextCard = cardDealer.dealCard();
      }

      // Take the first card from the draw pile and place it in the discard pile
      discardPile=new Vector<Card>(Deck.DECK_SIZE*2);
      discardPile.add(drawPile.get(0));
      drawPile.remove(0);
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

   /** Returns the draw pile of the round
    @return drawPile - a Vector<Card> representing the draw pile
    */
   public Vector<Card> returnDrawPile(){
      return drawPile;
   }

   /** Returns the discard pile of the round
    @return discardPile - a Vector<Card> representing the draw pile
    */
   public Vector<Card> returnDiscardPile(){
      return discardPile;
   }

   /** Returns the round number for the round
    @return integer of round number
    */
   public int getRoundNumber(){
      return roundNumber;
   }

   /** Returns the next player index of the round
    @return nextPlayerIndex - an integer representing the next player up
    */
   public int getNextPlayerIndex(){
      return nextPlayerIndex;
   }


   // *********************************************************
   // ******************** Mutators ***************************
   // *********************************************************

   /** Sets the round number
    @param newRound integer representing the new round
    */
   public void setRoundNumber(int newRound){
      roundNumber=newRound;
   }

   /** Sets the next player index, deciding who the next player will be
    @param newIndex integer value representing the new index to start from
    */
   public void setNextPlayer(int newIndex){
      nextPlayerIndex=newIndex;
   }

   /**
    Plays a round of the game, invoking other functions as necessary. This is for the console version of the app.
    @deprecated only needed for console app, now.
    */
   public void play(){
      // the index value of the next player (to be modded)
//      nextPlayerIndex = 0;

      while(true){

         // Print the round stats
         System.out.println(this);

         // Have next player play
         System.out.println(playerList[nextPlayerIndex].getPlayerName() + "\'s turn");

         // If the player up goes out
         if(playerList[nextPlayerIndex].play(drawPile,discardPile)==0){
            // ensure the first player out goes up next
            playerList[nextPlayerIndex].setUpNext(true);
            // switch players
            nextPlayerIndex = (nextPlayerIndex+1) % TOTAL_PLAYERS;
            System.out.println(playerList[nextPlayerIndex].getPlayerName() + "\'s turn");
            playerList[nextPlayerIndex].setUpNext(false);
            break;
         }

         // Otherwise, switch players
         nextPlayerIndex = (nextPlayerIndex+1) % TOTAL_PLAYERS;

      }

      // Have the other player play once, and tally the score
      System.out.println(this);
      System.out.println(playerList[nextPlayerIndex].getPlayerName() + "\'s final turn");
      playerList[nextPlayerIndex].play(drawPile,discardPile);
      playerList[nextPlayerIndex].addToScore(playerList[nextPlayerIndex].getFinalPoints());

      // end the round by clearing the player hands
      roundEnd();

   }

   /** Tallies the score of the human player by updating the player object's score.
    */
   public void tallyHumanScore(){
      playerList[0].addToScore(playerList[0].getFinalPoints());
   }

   /** Tallies the score of the computer player by updating the computer object's score.
    */
   public void tallyComputerScore(){
      playerList[1].addToScore(playerList[1].getFinalPoints());
   }

   /** Ends the round by clearing the hands of the players. */
   public void roundEnd(){
      // clear the players' hands
      playerList[0].clearHands();
      playerList[1].clearHands();
   }

   /** Loads the game from a string representing a save game
    @param fileHolder String object generated from the android system from a save game file.
    @return returnMessage - a String representing the outcome of the load attempt.
    */
   public String loadGame(String fileHolder){
      // Create local variable to store string
      String returnMessage ="";
      // create a vector of strings to break up the file, and ensure that it's clear from any previous attempts
      fileString = new Vector<String>();
      fileString.clear();

      // split the file string into lines based on line returns
      String[] lines = fileHolder.split(System.getProperty("line.separator"));

      // for each line in the string,
      for(String ourLine:lines){
         // if it contains a :, add it to our vector (it contains data)
         if(ourLine.contains(":")){
            fileString.add(ourLine);
         }
      }
      // if the vector doesn't have exactly 10 entries, there was something wrong with the loaded file
      if(fileString.size()!=10){
         returnMessage+="File does not sync appropriately - lines are not accurate.";
         return returnMessage;
      }

      // For debug of any file issues
//      else{
//         System.out.println("File string is right size. Proceed.");
//      }
//      System.out.println("Printing vector: \n");
//      int i=0;
//      for(String vecString:fileString){
//         System.out.println(i+": "+vecString);
//         i++;
//      }

      // load round number. This effects the cards, later.
      if(!(loadNum(fileString.get(0),'r'))){
         returnMessage+="\nRound number load issue.\n";
         return returnMessage;
      }

      // load computer score
      if(!(loadNum(fileString.get(2),'c'))){
         returnMessage+="\nComputer score load issue.\n";
         return returnMessage;
      }

      // load computer hand
      if(!(loadHand(fileString.get(3),'C'))){
         returnMessage+="\nComputer hand load issue.\n";
         return returnMessage;
      }

      // load human score
      if(!(loadNum(fileString.get(5),'h'))){
         returnMessage+="\nHuman score load issue.\n";
         return returnMessage;
      }

      // load human hand
      if(!(loadHand(fileString.get(6),'H'))){
         returnMessage+="\nHuman hand load issue.\n";
         return returnMessage;

      }

      // load draw
      if(!(loadHand(fileString.get(7),'d'))){
         returnMessage+="\nDraw pile load issue.\n";
         return returnMessage;
      }

      // load discard
      if(!(loadHand(fileString.get(8),'D'))){
         returnMessage+="\nDiscard pile load issue.\n";
         return returnMessage;
      }

      // load who is up next
      if(!(loadNext(fileString.get(9)))){
         returnMessage+="\nLoad issue deciding player up next.\n";
         return returnMessage;
      }

      // update string to mention that the file was uploaded successfully, and return.
      returnMessage+="\nLoaded successfully.\n";
      return returnMessage;
   }

   /** Ensures that the number on a line is pulled, using the awesome power of REGEX
    @param numString a String representing the number to be loaded in
    @param numType a char representing the type of number being sent to the function (round, human score, computer score)
    @return boolean representing if the portion of the file loaded correctly or not.
    */
   public boolean loadNum(String numString, char numType){
      // create regex pattern to pull number from line (same from C++)
      Matcher matcher = Pattern.compile("[\\d]{1,}").matcher(numString);

      // Note: although this worked fine for the demo, I realize that the below pattern should
      // be changed so that it matches loadHand. In this pattern, if bad data is given to it,
      // no match will be found and it will return "true". Since the demo is over,
      // I will not change the code. However, I understand the error, and would change
      // it if I were to make a full app based on this. I also realize that the replaceall function
      // against the matcher group is unnecessary. This was leftover from an article I
      // read which explained how to perform a regex match in a similar way to C++.

      // while regex matches
      while(matcher.find()){
         if(numType=='r'){
            // pull the round number
            setRoundNumber(Integer.parseInt(matcher.group().replaceAll("\\|", "")));
         }
         else if(numType=='h'){
            // pull the human score
            ourHuman.setScore(Integer.parseInt(matcher.group().replaceAll("\\|", "")));
         }
         else if(numType=='c'){
            // pull the computer score
            ourComputer.setScore(Integer.parseInt(matcher.group().replaceAll("\\|", "")));
         }
         else {
            return false;
         }
      }
      return true;
   }

   /** Ensures that the cards on a line are pulled, using the awesome power of REGEX
    @param handString a String representing the number to be loaded in
    @param handDestination a char representing the type of number being sent to the function (round, human score, computer score)
    @return boolean representing if the portion of the file loaded correctly or not.
    */
   public boolean loadHand(String handString, char handDestination){
      // create regex pattern to pull cards from line (same from C++)
      Matcher matcher = Pattern.compile("([123456789XQKJ][1234CHTSD])").matcher(handString);

      // if the cards being pulled are for the computer
      if(handDestination=='C'){
         // Clear the computer hand so that the new hand doesn't add to it
         ourComputer.clearHands();
         // while the pattern runs against our current hand,
         while(matcher.find()){
            // add cards based on the pattern
            addCard(matcher.group(),'c');
         }
      }

      // if the cards being pulled are for the human hand
      if(handDestination=='H'){

         // clear human hand
         ourHuman.clearHands();

         // run regex and add cards accordingly
         while (matcher.find()) {
            addCard(matcher.group(),'h');
         }
      }

      // If the cards being pulled are for the draw pile
      if(handDestination=='d'){
         // Clear previous draw pile
         drawPile.clear();

         // Add cards accordingly
         while (matcher.find()) {
            addCard(matcher.group(),handDestination);
         }
      }

      // If the cards being pulled are for the discard pile
      if(handDestination=='D'){

         // Clear previous discard pile
         discardPile.clear();

         // Run regex and add cards accordingly
         while (matcher.find()) {
            addCard(matcher.group(),handDestination);
         }
      }
      return true;
   }

   /** Adds a card to a player hand - for file loading
    @param nextString String variable for the next player up
    @return boolean value representing if the round loads without error
    */
   public boolean loadNext(String nextString){
      // search the line for "man", and avoid regex this time
      if(nextString.contains("man")){
         // If man is found, human is up next. Set variables accordingly.
         ourHuman.setUpNext(true);
         ourComputer.setUpNext(false);
         setNextPlayer(0);
      }
      else{
         // Otherwise, the computer is up next
         ourHuman.setUpNext(false);
         ourComputer.setUpNext(true);
         setNextPlayer(1);
      }
      return true;
   }



   /** Adds a card to a player hand - for file loading
    @param passedCardString String variable representing the card
    @param location char representing where the new card will be placed
    */
   public void addCard(String passedCardString, char location){
      // pull the suit from the passed string
      char suit = passedCardString.charAt(1);
      // create integer to store the face
      int face;
      // throw a switch to route the face accordingly
      switch (passedCardString.charAt(0)){
         case '3':
            face = 3;
            break;
         case '4':
            face = 4;
            break;
         case '5':
            face = 5;
            break;
         case '6':
            face = 6;
            break;
         case '7':
            face = 7;
            break;
         case '8':
            face = 8;
            break;
         case '9':
            face = 9;
            break;
         case 'X':
            face = 10;
            break;
         case 'J':
            // remember to account for jokers
            if(suit=='1' || suit =='2' || suit =='3'){
               face = 50;
            }
            else {
               face = 11;
            }
            break;
         case 'Q':
            face = 12;
            break;
         case 'K':
            face = 13;
            break;
         default:
            face=0;
            break;
      }

      // If the end location is the human hand,
      if(location=='h'){

         // for debug
//         System.out.println("Add to human hand.");
//         if(playerList[0].isHuman) {
//            playerList[0].addCard(tempCard);
//         }
//         else{
//            playerList[1].addCard(tempCard);
//         }

         // add the card to the human hand in the exact order it was given
         ourHuman.addExact(new Card(suit,face,this.roundNumber));
      }

      // If the end location is the computer hand
      else if(location=='c'){

         // for debug
//         System.out.println("Add to computer hand.");
//         if(playerList[0].isHuman) {
//            playerList[1].addCard(tempCard);
//         }
//         else{
//            playerList[0].addCard(tempCard);
//         }

         // add the card to the computer hand in the exact order it was given
         ourComputer.addExact(new Card(suit,face,this.roundNumber));
      }
      // If the end location is the draw pile
      else if(location=='d'){
         // Add the card in accordingly
         drawPile.add(new Card(suit,face,this.roundNumber));
      }
      // If the end location is the discare pile
      else if(location=='D'){
         // Add it into the discard pile accordingly
         discardPile.add(new Card(suit,face,this.roundNumber));
      }
//      System.out.println(tempCard.toString());
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
    toString function to print the round status
    @return string variable representing the round information. This is the string used to save the file.
    */
   public String toString(){
      // Create variables corresponding to human and computer player
      int humanPlayer=0;
      int computerPlayer=1;

      // Add round number
      String result="Round: " + roundNumber + "\n";

      // Add Computer score
      result+= "Computer:\n\tScore: " + playerList[computerPlayer].getScore() + "\n\t"
            + "Hand: " + playerList[computerPlayer].toString() + "\n";

      // Add Human score
      result+= "Human:\n\tScore: " + playerList[humanPlayer].getScore() + "\n\t"
            + "Hand: " + playerList[humanPlayer].toString() + "\n";

      // Add draw pile
      result+= "\nDrawPile: ";
      for(Card card:drawPile){
         result+= card.toString() + " ";
      }
      result+="\n\n";

      // Add discard pile
      result+= "Discard Pile:" ;
      for(Card card:discardPile){
         result+= card.toString() + " ";
      }

      // add next player info
      result+="\n\nNext Player: ";
      if(nextPlayerIndex%TOTAL_PLAYERS==0){
         result+="Human";
      }
      else{
         result+="Computer";
      }

      return result;
   }
   // *********************************************************
   // ******************** Debugging Methods ******************
   // *********************************************************

   /**
    Debugging method for the object - plays a new round
    @param args string value default parameter for main in java
    */
   public static void main( String args[] )
   {
      Round round = new Round(new Human(), new Computer(), 1);

//      round.addCard("J1",'h');
//      round.addCard("J1",'D');
//      round.addCard("J1",'D');
//      round.addCard("J1",'D');
      String largeFile="Round: 11\n\n\tComputer:\n\tScore: 167\n\tHand: 3H 3C 4C 5D 6C 7T 7C 7D 8D 9D XS QS QT\n\n\tHuman:\n\tScore: 173\nHand: 3C 3D 4H 5C 6H 8T 9T XT XH XD XC JS QC\nDraw Pile: J3 KC KD J2 KT KH J1 6S 5S 4S 3S 7S J1 5C 6C 7C 9H JH QH KH 4T 6T J3 QS XS 9S 8C 4C 9C QC KC JC XC 8S JS KS J2 6H 3H 4H 5H XH 8H 7H XD QD KD 6D 5D 3D 4D 7D JD 9D 8D 8T 5T 3T 9T XT QT KT JT 7T \nDiscard Pile: 5H 3S 4S 8H 9H 7S 8S 9S KS 3T JH QH 8C 6D JD QD 5H 7H 6T JT 9C JC 5S 6S 4T 5T 4D \n\tNext Player: Human ";
      round.loadGame(largeFile);
      System.out.println(round.toString());
      //round.play();

   }
};

// *********************************************************
// ******************** Trash Methods **********************
// *********************************************************

