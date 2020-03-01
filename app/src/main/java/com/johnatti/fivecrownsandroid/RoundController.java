/*******************************************************
 Serves as the view controller of the 5Crowns round
 @author John Atti
 @since 11/13/2019
  ******************************************************** */
package com.johnatti.fivecrownsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.AlertDialog;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class RoundController extends AppCompatActivity {

   /** All of our TextViews, for debugging/demo */
   private TextView
         discard,
         draw,
         playerHand,
         playerPoints,
         computerHand,
         computerPoints,
         instructions,
         roundNumText,
         roundDebug;

   /** All of our ImageViews, for displaying non-clickable cards of the computer hand */
   private ImageView
         compHand1,
         compHand2,
         compHand3,
         compHand4,
         compHand5,
         compHand6,
         compHand7,
         compHand8,
         compHand9,
         compHand10,
         compHand11,
         compHand12,
         compHand13,
         compHand14;

   /** All image views for draw/discard cards */
   private ImageView
         drawCardTop,
         discardCardTop;

   /** All image buttons, for player hand cards (clickable to select cards) */
   private ImageButton
         humanHand1,
         humanHand2,
         humanHand3,
         humanHand4,
         humanHand5,
         humanHand6,
         humanHand7,
         humanHand8,
         humanHand9,
         humanHand10,
         humanHand11,
         humanHand12,
         humanHand13,
         humanHand14;

   /** Computer hand vector, used to storage ImageViews of cards */
   Vector<ImageView> compHandView = new Vector<ImageView>();

   /** Human hand vector, used to storage ImageButtons of cards */
   Vector<ImageButton> humanHandView = new Vector<ImageButton>();

   /** Game object model holder */
   private Game game;

   /** Round object model holder */
   private Round round;

   /** Human object model holder */
   private Human human;

   /** Computer object model holder */
   private Computer computer;

   /** Coin toss result representing if human goes first */
   private boolean humanFirst;

   /** Round number being played */
   private int roundNumber;

   /** Holds human score */
   private int humanScore;

   /** Holds computer score */
   private int computerScore;


   /** File load string - contains file information */
   private String fileString;

   /** boolean telling us if there's a file to be loaded */
   private boolean isFileLoaded;

   /** Set up information for intent popup */
   final Context ourContext = this;
   private String fileStringIntent="";

   /** onCreate function to begin the round */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_play_round);

      // lock screen to portrait orientation
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      // pull any variables in as needed. Also handles file loading
      updateIntents();

      // initialize views
      initializeViews();

      // initialize models
      initializeModels();

      // update views
      updateViews();
      announce();


   }

   /** onBackPressed function to override the back button and prompt user to exit appropriately */
   @Override
   public void onBackPressed() {
      // ensure that user can't back out of the activity without saving/quitting
      // otherwise, the cards will shuffle when toggling back to the coin toss.
      //moveTaskToBack(true);
      instructions.append("\nStop trying to escape. Press save to exit.");
   }

   /** Ensure that this view gets the intents appropriately */
   public void updateIntents(){
      Intent intent = getIntent();
      // set up if the human is up next, the round number, and if a file is to be loaded
      humanFirst = intent.getExtras().getBoolean("humanUpNext");
      roundNumber = intent.getExtras().getInt("roundNumber");
      isFileLoaded = intent.getExtras().getBoolean("loadingStatus");

      // If a file is loaded, get the file string
      if(isFileLoaded) {
         fileString = intent.getExtras().getString("fileLoad");
      }
      // Otherwise, set the human and computer score to whatever values passed in
      else{
         humanScore = intent.getExtras().getInt("humanScore");
         computerScore = intent.getExtras().getInt("computerScore");
      }
   }

   /** Initialize the views of the function, including all cards and buttons */
   public void initializeViews(){
      // initialize the views and adjust accordingly
//      discard=(TextView) findViewById(R.id.discardCards);
//      draw=(TextView) findViewById(R.id.drawCards);
//      playerHand=(TextView) findViewById(R.id.playerCards);
      playerPoints=(TextView) findViewById(R.id.playerPointsText);
//      computerHand=(TextView) findViewById(R.id.opponentCards);
      computerPoints=(TextView) findViewById(R.id.opponentPointsText);
      roundNumText=(TextView) findViewById(R.id.roundNumberDisplay);
      instructions=(TextView) findViewById(R.id.playerPromptText);
      roundDebug=(TextView) findViewById(R.id.roundDebugString);

      // declare computer view
      compHand1 =(ImageView) findViewById(R.id.compCard1);
      compHand2 =(ImageView) findViewById(R.id.compCard2);
      compHand3 =(ImageView) findViewById(R.id.compCard3);
      compHand4 =(ImageView) findViewById(R.id.compCard4);
      compHand5 =(ImageView) findViewById(R.id.compCard5);
      compHand6 =(ImageView) findViewById(R.id.compCard6);
      compHand7 =(ImageView) findViewById(R.id.compCard7);
      compHand8 =(ImageView) findViewById(R.id.compCard8);
      compHand9 =(ImageView) findViewById(R.id.compCard9);
      compHand10 =(ImageView) findViewById(R.id.compCard10);
      compHand11 =(ImageView) findViewById(R.id.compCard11);
      compHand12 =(ImageView) findViewById(R.id.compCard12);
      compHand13 =(ImageView) findViewById(R.id.compCard13);
      compHand14 =(ImageView) findViewById(R.id.compCard14);

      // declare human view
      humanHand1 =(ImageButton) findViewById(R.id.humanCard1);
      humanHand2 =(ImageButton) findViewById(R.id.humanCard2);
      humanHand3 =(ImageButton) findViewById(R.id.humanCard3);
      humanHand4 =(ImageButton) findViewById(R.id.humanCard4);
      humanHand5 =(ImageButton) findViewById(R.id.humanCard5);
      humanHand6 =(ImageButton) findViewById(R.id.humanCard6);
      humanHand7 =(ImageButton) findViewById(R.id.humanCard7);
      humanHand8 =(ImageButton) findViewById(R.id.humanCard8);
      humanHand9 =(ImageButton) findViewById(R.id.humanCard9);
      humanHand10 =(ImageButton) findViewById(R.id.humanCard10);
      humanHand11 =(ImageButton) findViewById(R.id.humanCard11);
      humanHand12 =(ImageButton) findViewById(R.id.humanCard12);
      humanHand13 =(ImageButton) findViewById(R.id.humanCard13);
      humanHand14 =(ImageButton) findViewById(R.id.humanCard14);

      // declare top of draw/discard pile
      drawCardTop =(ImageView) findViewById(R.id.drawCard1);
      discardCardTop =(ImageView)findViewById(R.id.discardCard1);


      // make sure the instructions view is scrollable/movable by user
      instructions.setMovementMethod(ScrollingMovementMethod.getInstance());
      roundDebug.setMovementMethod(ScrollingMovementMethod.getInstance());


   }

   public void initializeModels(){

      // start up the game model
      game = new Game();

      // set who goes first
      game.initializeHuman(humanFirst);

      // set round number
      game.setRoundNumber(roundNumber);

      // prime the round
      game.initializeRound();

      // retrieve the round object
      round = game.getRound();

      // retrieve human and computer objects
      human = game.getHuman();
      computer = game.getComputer();
      human.setScore(humanScore);
      computer.setScore(computerScore);

      // if file is loaded ... overwrite everything with the new data
      if(isFileLoaded){
         String resultString="";
         resultString+=round.loadGame(fileString);
         //ensure that the first player is determined appropriately
         humanFirst=human.getUpNext();
         game.initializeHuman(humanFirst);
         // Inform user if the file is loaded right.
         instructions.setText(resultString);

         // get the correct round of the game
         round=game.getRound();
      }

   }

   /** Updates the views so that they have the right starting data */
   public void updateViews(){

      roundDebug.append(round.toString());
      roundNumText.setText("Round " + round.getRoundNumber());

      // display player/computer points
      playerPoints.setText(human.getScoreText());
      computerPoints.setText(computer.getScoreText());

      // populate the compHand vector
      compHandView.add(compHand1);
      compHandView.add(compHand2);
      compHandView.add(compHand3);
      compHandView.add(compHand4);
      compHandView.add(compHand5);
      compHandView.add(compHand6);
      compHandView.add(compHand7);
      compHandView.add(compHand8);
      compHandView.add(compHand9);
      compHandView.add(compHand10);
      compHandView.add(compHand11);
      compHandView.add(compHand12);
      compHandView.add(compHand13);
      compHandView.add(compHand14);

      // update the computer's hand visuals to the screen
      updateComputerView();

      // populate the humanHand vector
      humanHandView.add(humanHand1);
      humanHandView.add(humanHand2);
      humanHandView.add(humanHand3);
      humanHandView.add(humanHand4);
      humanHandView.add(humanHand5);
      humanHandView.add(humanHand6);
      humanHandView.add(humanHand7);
      humanHandView.add(humanHand8);
      humanHandView.add(humanHand9);
      humanHandView.add(humanHand10);
      humanHandView.add(humanHand11);
      humanHandView.add(humanHand12);
      humanHandView.add(humanHand13);
      humanHandView.add(humanHand14);

      // show the human hand visuals to the screen
      updateHumanView();

      // populate discard card tops
      updateDiscardView();

      // Ensure human buttons are not clickable (regardless of who goes first, they only activate during a specific point of the human's turn).
      disableHumanCards();

      // set buttons to begin the round, based on human/computer player
      if(humanFirst){
         hideComputerChoices();
         showPlayerChoice();
      }
      else{
         hidePlayerChoice();
         showComputerChoices();
      }
   }

   /** Updates the computer card view images, according to data in the model */
   public void updateComputerView(){
      // set an index to update the hand images appropriately
      int i = 0;
      // iterate through the computer hand
      for(Iterator<Card> ourIterator = computer.getHand().iterator(); ourIterator.hasNext();){
         Card thisCard = ourIterator.next();
         updateImage(compHandView.get(i),thisCard);
         i++;
      }
      if(i<14) {
         // ensure discarded cards don't stick behind
         compHandView.get(i).setImageResource(R.drawable.card_bg);
      }
   }

   /** Updates the human card view images, according to data in the model */
   public void updateHumanView(){
      // create index
      int i=0;

      // update human hand images based on vector contents
      for(Iterator<Card> ourIterator = human.getHand().iterator(); ourIterator.hasNext();){
         Card thisCard = ourIterator.next();
         updateImage(humanHandView.get(i),thisCard);
         i++;
      }
      if(i<14) {
         // ensure discarded cards don't stick behind
         humanHandView.get(i).setImageResource(R.drawable.card_bg);
      }
   }

   /** Updates the discard card view images, according to data in the model */
   public void updateDiscardView(){

      // as long as the discard pile has a card in it
      if(round.returnDiscardPile().size()>0){

         // update the image
         updateImage(discardCardTop, round.returnDiscardPile().get(0));
      }

      // If the discard pile is empty
      else {

         // prompt this in the debug window
         roundDebug.append("\nDiscard pile is empty.");

         // set the drawable for the pile to white
         discardCardTop.setImageResource(R.drawable.blank);
      }
   }

   /** Updates a card image
    @param passedView ImageView item being passed in
    @param ourCard Card item being used to populate the image view
    */
   public void updateImage(ImageView passedView, Card ourCard){
      // create a string from the card
      String cardString=ourCard.toString();

      // Run a switch against the string. Not the most eloquent solution, but gets the job done.
      // This switch will update the imageView of a card based on the card face
      switch(cardString){
         case "3T":
            passedView.setImageResource(R.drawable.t3);
            break;
         case "4T":
            passedView.setImageResource(R.drawable.t4);
            break;
         case "5T":
            passedView.setImageResource(R.drawable.t5);
            break;
         case "6T":
            passedView.setImageResource(R.drawable.t6);
            break;
         case "7T":
            passedView.setImageResource(R.drawable.t7);
            break;
         case "8T":
            passedView.setImageResource(R.drawable.t8);
            break;
         case "9T":
            passedView.setImageResource(R.drawable.t9);
            break;
         case "XT":
            passedView.setImageResource(R.drawable.tx);
            break;
         case "JT":
            passedView.setImageResource(R.drawable.tj);
            break;
         case "QT":
            passedView.setImageResource(R.drawable.tq);
            break;
         case "KT":
            passedView.setImageResource(R.drawable.tk);
            break;
         case "3D":
            passedView.setImageResource(R.drawable.d3);
            break;
         case "4D":
            passedView.setImageResource(R.drawable.d4);
            break;
         case "5D":
            passedView.setImageResource(R.drawable.d5);
            break;
         case "6D":
            passedView.setImageResource(R.drawable.d6);
            break;
         case "7D":
            passedView.setImageResource(R.drawable.d7);
            break;
         case "8D":
            passedView.setImageResource(R.drawable.d8);
            break;
         case "9D":
            passedView.setImageResource(R.drawable.d9);
            break;
         case "XD":
            passedView.setImageResource(R.drawable.dx);
            break;
         case "JD":
            passedView.setImageResource(R.drawable.dj);
            break;
         case "QD":
            passedView.setImageResource(R.drawable.dq);
            break;
         case "KD":
            passedView.setImageResource(R.drawable.dk);
            break;
         case "3S":
            passedView.setImageResource(R.drawable.s3);
            break;
         case "4S":
            passedView.setImageResource(R.drawable.s4);
            break;
         case "5S":
            passedView.setImageResource(R.drawable.s5);
            break;
         case "6S":
            passedView.setImageResource(R.drawable.s6);
            break;
         case "7S":
            passedView.setImageResource(R.drawable.s7);
            break;
         case "8S":
            passedView.setImageResource(R.drawable.s8);
            break;
         case "9S":
            passedView.setImageResource(R.drawable.s9);
            break;
         case "XS":
            passedView.setImageResource(R.drawable.sx);
            break;
         case "JS":
            passedView.setImageResource(R.drawable.sj);
            break;
         case "QS":
            passedView.setImageResource(R.drawable.sq);
            break;
         case "KS":
            passedView.setImageResource(R.drawable.sk);
            break;
         case "3C":
            passedView.setImageResource(R.drawable.c3);
            break;
         case "4C":
            passedView.setImageResource(R.drawable.c4);
            break;
         case "5C":
            passedView.setImageResource(R.drawable.c5);
            break;
         case "6C":
            passedView.setImageResource(R.drawable.c6);
            break;
         case "7C":
            passedView.setImageResource(R.drawable.c7);
            break;
         case "8C":
            passedView.setImageResource(R.drawable.c8);
            break;
         case "9C":
            passedView.setImageResource(R.drawable.c9);
            break;
         case "XC":
            passedView.setImageResource(R.drawable.cx);
            break;
         case "JC":
            passedView.setImageResource(R.drawable.cj);
            break;
         case "QC":
            passedView.setImageResource(R.drawable.cq);
            break;
         case "KC":
            passedView.setImageResource(R.drawable.ck);
            break;
         case "3H":
            passedView.setImageResource(R.drawable.h3);
            break;
         case "4H":
            passedView.setImageResource(R.drawable.h4);
            break;
         case "5H":
            passedView.setImageResource(R.drawable.h5);
            break;
         case "6H":
            passedView.setImageResource(R.drawable.h6);
            break;
         case "7H":
            passedView.setImageResource(R.drawable.h7);
            break;
         case "8H":
            passedView.setImageResource(R.drawable.h8);
            break;
         case "9H":
            passedView.setImageResource(R.drawable.h9);
            break;
         case "XH":
            passedView.setImageResource(R.drawable.hx);
            break;
         case "JH":
            passedView.setImageResource(R.drawable.hj);
            break;
         case "QH":
            passedView.setImageResource(R.drawable.hq);
            break;
         case "KH":
            passedView.setImageResource(R.drawable.hk);
            break;
         case "J1":
            passedView.setImageResource(R.drawable.joker);
            break;
         case "J2":
            passedView.setImageResource(R.drawable.joker);
            break;
         case "J3":
            passedView.setImageResource(R.drawable.joker);
            break;
         default:
            passedView.setImageResource(R.drawable.blank);
            break;
      }
   }

   /** Announce who is up at the start of the round, and prompt the user accordingly. */
   public void announce() {
      // announce start of round
      if(human.getUpNext()) {
         instructions.append("\nHuman is up. Please select either \"Draw\" or \"Discard\" below.");
      }
      else{
         instructions.append("\nComputer is up. Ready for it to move? Click \"Opponent\'s turn below.");
      }
   }

   /** Creates a file in internal storage */
   public void saveGame(View view){
      // Prompt the user to enter the save file name with a popup
      displayAlertToSave();

   }

   /** Prompt user if they want to save the file or quit, and respond accordingly. */
   public void displayAlertToSave(){
      // Begin prompt for file. I hate popups, but I hate making textboxes on the page even more,
      // which is what I did on the start page!

      // "Inflate" our layout
      LayoutInflater ourLayout = LayoutInflater.from(ourContext);

      // Call the specific layout we made for the prompt
      View promptsView = ourLayout.inflate(R.layout.file_name_prompt, null);

      // Prime the alert dialogue
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            ourContext);

      // Display the prompt as an alert
      alertDialogBuilder.setView(promptsView);

      // Organize the user input view (pulls from the prompt's EditText field)
      final EditText userInput = (EditText) promptsView.findViewById(R.id.fileNameInput);

      // Build the alert
      alertDialogBuilder
            // Make sure user can't cancel out
            .setCancelable(false)
            // Set confirmation action button
            .setPositiveButton("Confirm",
                  new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int id) {
                        // Pull user input from editText to our string
                        fileStringIntent=userInput.getText().toString();
                        // Save the file
                        saveUserFile();
                     }
                  })
            // Allow user to quit without saving
            .setNeutralButton("Quit Without Save",
                  new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int id) {
                        // Gracefully exit to the start screen
                        gracefulQuit();
                     }
                  })
            // Set cancel action
            .setNegativeButton("Cancel",
                  new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int id) {
                        // Close down the dialogue and go back to game screen
                        dialog.cancel();
                     }
                  });

      // Display the alert
      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();
   }

   /** Allows the user to leave the game without saving. */
   public void gracefulQuit(){
      Intent intent = new Intent(this,StartScreenController.class);
      startActivity(intent);
   }

   /** Creates a save file for the user on the android device. */
   public void saveUserFile(){
      try {
         // Create a file to internal storage

         // Find the path android is using to internal storage
         File path = getFilesDir();

         // Decide on the file name
         // If the user didn't enter anything, save with a default name
         if(fileStringIntent.length()==0){
            fileStringIntent="PlayerSaveTest.txt";
         }

         // Otherwise, use the string given to us by the previous intent.
         File testFile = new File(path, fileStringIntent);

         if (!testFile.exists()) {
            testFile.createNewFile();
         }


         // Adds a line to the file
         BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, false));
         writer.write(round.toString());
         writer.close();
         instructions.append("\nGame is saved!");

         // Dump user back in start screen
         Intent intent = new Intent(this,StartScreenController.class);
         intent.putExtra("StartMessage","Game saved successfully.");
         startActivity(intent);

      } catch (IOException e) {
         instructions.append("\nGame cannot save.");
         Log.e("ReadWriteFile", "Won't write to file.");
      }
   }

   /** Allows the human to select a card to discard by clicking the screen
    @param view the button clicking to go to this function */
   public void humanCardSelect(View view) {
      int discardChoice;
      // Switch statement based on button ID. ID returns card index to be discarded.
      switch (view.getId()){
         case R.id.humanCard1:
            discardChoice=0;
            break;
         case R.id.humanCard2:
            discardChoice=1;
            break;
         case R.id.humanCard3:
            discardChoice=2;
            break;
         case R.id.humanCard4:
            discardChoice=3;
            break;
         case R.id.humanCard5:
            discardChoice=4;
            break;
         case R.id.humanCard6:
            discardChoice=5;
            break;
         case R.id.humanCard7:
            discardChoice=6;
            break;
         case R.id.humanCard8:
            discardChoice=7;
            break;
         case R.id.humanCard9:
            discardChoice=8;
            break;
         case R.id.humanCard10:
            discardChoice=9;
            break;
         case R.id.humanCard11:
            discardChoice=10;
            break;
         case R.id.humanCard12:
            discardChoice=11;
            break;
         case R.id.humanCard13:
            discardChoice=12;
            break;
         case R.id.humanCard14:
            discardChoice=13;
            break;
         default:
            instructions.append("\nHow did you press this?");
            return;
      }

      // Announce which card you'll be discarding
      instructions.append("\nDiscarding " + human.getHand().get(discardChoice).toString());

      // discard that specific card
      human.discardSpecific(round.returnDiscardPile(),discardChoice);

      // update your views/disable buttons as needed
      updateDiscardView();
      disableHumanCards();
      updateHumanView();

      // Check if human is out
      if(human.checkOut()){
         instructions.append("\nYou're out! Congratulations, you made it!");
      }

      // the human's turn has finished, so set it to the next player.
      round.setNextPlayer(1);

      // output debug information about piles
      roundDebug.append("\nNew Discard: " + round.returnDiscardPile().toString() + "\nNew human hand: " + human.toString() + "\nNew draw top:" + round.returnDrawPile().get(0).toString());

      // show the computer's movement buttons
      showComputerChoices();

   }

   /** Pulls a card from the draw pile for the human based on their input
    @param view View of the draw button */
   public void drawCardToPile(View view) {

      if(human.isOut()){
         round.tallyComputerScore();
         human.clearHands();
         endRound();
      }
      else {
         // Output which card is being drawn from the draw pile
         instructions.append("\nDrawing " + round.returnDrawPile().get(0).toString());

         // Have model draw from draw pile
         human.drawFromPile(round.returnDrawPile());

         // Update views
         updateHumanView();

         // Hide buttons so player can't keep drawing additional cards until the next round
         hidePlayerChoice();

         // Instruct player to tap card they want to vanish
         instructions.append("\nSelect (tap) card to discard.");

         // output updated round information
         roundDebug.append("\nNew draw top:" + round.returnDrawPile().get(0).toString() + "\nTemp human hand: " + human.toString());

         // Ensure that human can now click on cards to discard
         enableHumanCards();
      }
   }

   /** Pulls a card from the discard pile for the human based on their input
    @param view View of the discard button */
   public void discardCardToPile(View view){

      if(human.isOut()){
         round.tallyComputerScore();
         human.clearHands();
         endRound();
      }
      else{
         // Output which card is being drawn from the discard pile
         instructions.append("\nDrawing " + round.returnDiscardPile().get(0).toString());

         // Have model draw from pile
         human.drawFromPile(round.returnDiscardPile());

         // Update views. This includes the discard pile, since we're pulling from it.
         updateHumanView();
         updateDiscardView();

         // Hide buttons so player can't keep drawing additional cards until the next round
         hidePlayerChoice();

         // Instruct player to tap card they want to vanish
         instructions.append("\nSelect (tap) card to discard.");

         // Output updated round information
         if(round.returnDiscardPile().size()>0) {
            roundDebug.append("\nNew discard top:" + round.returnDiscardPile().get(0).toString() + "\nTemp human hand: " + human.toString());
         }
         else{
            roundDebug.append("\nNew discard top: Empty.\nTemp human hand: " + human.toString());
         }
         // Ensure that human can now click on cards to discard
         enableHumanCards();
      }
   }

   /** Hides the player choice buttons so that they cannot act more than once in a row. */
   public void hidePlayerChoice(){
      // Hide buttons that would move the player (including the save button, since you can't save mid-round).
      findViewById(R.id.drawButton).setVisibility(View.INVISIBLE);
      findViewById(R.id.discardButton).setVisibility(View.INVISIBLE);
      findViewById(R.id.saveButton).setVisibility(View.INVISIBLE);
   }

   /** Disables the human card image buttons so that humans cannot discard more than 1 card in a row */
   public void disableHumanCards(){
      // Disable all human ImageButtons so that the cards can't be discarded
      for(ImageButton ourImage: humanHandView){
         ourImage.setEnabled(false);
      }
   }

   /** Enables human card image buttons so that they can select a card to discard. */
   public void enableHumanCards(){
      // Enable the ImageButtons so they can be clicked
      for(int i = 0; i < human.getHand().size();i++){
         humanHandView.get(i).setEnabled(true);
      }

   }

   /** Shows the human player choices so that they can interact with the round (draw/discard/save) */
   public void showPlayerChoice(){
      // Show buttons so the player can move/save as needed
      findViewById(R.id.drawButton).setVisibility(View.VISIBLE);
      findViewById(R.id.discardButton).setVisibility(View.VISIBLE);
      findViewById(R.id.saveButton).setVisibility(View.VISIBLE);
   }

   /** Updates the view when the computer moves. */
   public void moveComputer(View view){

      // Check if the computer has previously gone out. If so, tally human points and end the round.
      if(computer.isOut()){
         // add to the human score, since they lost
         round.tallyHumanScore();
         // clear our hand (for passing points later)
         computer.clearHands();
         // display end round screen
         endRound();
         //return;
      }
      else {
         // Set up string to display computer thought process
         String computerLogic = "";

         // Have the computer draw a card and report back logic to user
         instructions.append("\n" + computer.drawCard(round.returnDrawPile(), round.returnDiscardPile()));

         // ensure views are updated for computer hand and discard pile (in case)
         updateComputerView();
         updateDiscardView();

         // Have the computer discard a card and report back its logic.
         instructions.append(computer.discardCard(round.returnDiscardPile()));

         // Update the computer and discard pile views.
         updateComputerView();
         updateDiscardView();

         // Check if computer is out
         if (computer.checkOut()) {
            instructions.append("\nComputer is out. Womp womp.");
         }

         // Ensure that the human displays as up next, for serialization
         round.setNextPlayer(0);
         roundDebug.append("\nNew Discard: " + round.returnDiscardPile().toString() + "\nNew computer hand: " + computer.toString() + "\nNew draw top:" + round.returnDrawPile().get(0).toString());

         // now that computer has gone, change button layout for human player
         hideComputerChoices();
         showPlayerChoice();
         instructions.append("\nComputer round has ended. Continue as needed.");
      }
   }

   /** Shows the buttons related to computer choice (and allows the user to save at the start of the computer round) */
   public void showComputerChoices(){
      findViewById(R.id.saveButton).setVisibility(View.VISIBLE);
      findViewById(R.id.computerMove).setVisibility(View.VISIBLE);
   }

   /** Hides the buttons related to computer choice */
   public void hideComputerChoices(){
      findViewById(R.id.computerMove).setVisibility(View.INVISIBLE);
   }

   /** Show end of round buttons and hide all other user interaction */
   public void endRound(){
      hidePlayerChoice();
      hideComputerChoices();
      findViewById(R.id.roundSummary).setVisibility(View.VISIBLE);
   }


   /** Collects info from the end of the round and passes it to the round summary screen
    @param view View of the button pressed to access this function.
    */
   public void showRoundSummary(View view) {
      Intent endRoundIntent = new Intent(this, GameResultsController.class);
      endRoundIntent.putExtra("nextRound",round.getRoundNumber()+1);
      endRoundIntent.putExtra("humanTotalPoints", human.getScore());
      endRoundIntent.putExtra("computerTotalPoints",computer.getScore());
      endRoundIntent.putExtra("humanRoundPoints",human.getFinalPoints());
      endRoundIntent.putExtra("computerRoundPoints",computer.getFinalPoints());
      startActivity(endRoundIntent);

   }

   /** Help function that allows a human to get feedback before moving.
    @param view View of the button pressed to access this function.
    */
   public void askForHelp(View view) {
      // If we're choosing which card to discard
      if(humanHand1.isEnabled()) {
         instructions.append("\nHere's what we think about your cards:" + "\n" + human.helpFunction('D',game.returnDiscardPile()));
      }
      // If we're deciding which pile to draw from ...
      else if(findViewById(R.id.drawButton).isShown()){
         instructions.append("\n" + human.helpFunction('d',game.returnDiscardPile()));
      }
      // Otherwise ...
      else{
         instructions.append("\nI think you should play the game.\n(The computer is going. Stop pressing buttons and be patient!)");
      }

   }
}
