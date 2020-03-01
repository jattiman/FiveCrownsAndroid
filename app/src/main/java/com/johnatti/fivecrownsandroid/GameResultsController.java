/*******************************************************
 Acts as the view/controller to show the game results
 @author John Atti
 @since 11/28/2019
  ******************************************************** */

package com.johnatti.fivecrownsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameResultsController extends AppCompatActivity {
   /** private integers representing game total/round scores and round number for the next round */
   private int
         humanTotal,
         humanRound,
         computerTotal,
         computerRound,
         nextRound;

   /** private TextViews that will display the game results to the user */
   private TextView
         gameOverStatus,
         gameWinnerDisplay,
         roundWinnerDisplay,
         humanRoundPoints,
         humanTotalPoints,
         computerRoundPoints,
         computerTotalPoints,
         roundNumberDisplay;

   /** onCreate method to lock portrait orientation, initialize/set views and intents
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_prompt_next_round);
      // ensure the screen cannot rotate
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      // initialize our intents to make sure our data came in
      initializeIntents();

      // Initialize our default views
      initializeViews();

      // set the views with starting data
      setViews();

   }

   /** Initializes all of the intents for the end of the game
    */
   public void initializeIntents(){
      Intent intent = getIntent();
      nextRound = intent.getExtras().getInt("nextRound");
      humanTotal = intent.getExtras().getInt("humanTotalPoints");
      humanRound = intent.getExtras().getInt("humanRoundPoints");
      computerTotal = intent.getExtras().getInt("computerTotalPoints");
      computerRound = intent.getExtras().getInt("computerRoundPoints");
   }

   /** Intializes all of the views that will be appearing to the screen */
   public void initializeViews(){
      gameOverStatus = findViewById(R.id.gameOverPrompt);
      gameWinnerDisplay = findViewById(R.id.gameWinner);
      roundWinnerDisplay = findViewById(R.id.roundWinner);
      humanRoundPoints = findViewById(R.id.humRoundPoints);
      humanTotalPoints = findViewById(R.id.humTotalPoints);
      computerRoundPoints = findViewById(R.id.compRoundPoints);
      computerTotalPoints = findViewById(R.id.compTotalPoints);
      roundNumberDisplay = findViewById(R.id.nextRoundUp);
   }

   /** Sets the views on the screen so that they have the correct data */
   public void setViews(){
      // Set view scripts based on intents
      humanRoundPoints.setText("\nHuman round points: " + String.valueOf(humanRound));
      computerRoundPoints.setText("\nComputer round points: " + String.valueOf(computerRound));
      humanTotalPoints.setText("Human total points: " + String.valueOf(humanTotal));
      computerTotalPoints.setText("Computer total points: " + String.valueOf(computerTotal));

      // Display round winner based on round score
      if(humanRound>computerRound){
         roundWinnerDisplay.setText("The computer won the round...");
      }
      else{
         roundWinnerDisplay.setText("You win the round!");
      }

      // If we see that it's the end of the game (round 11 ended)
      if(nextRound>11){
         // display game over message
         gameOverStatus.setText("Our game has ended!");

         // display who won
         if(humanTotal>computerTotal){
            gameWinnerDisplay.setText("Computer wins the game.");
         }
         else{
            gameWinnerDisplay.setText("You won the game!");
         }
         roundNumberDisplay.setText("All rounds have ended.");

         // make next round information invisible
         findViewById(R.id.nextRoundUp).setVisibility(View.INVISIBLE);
         findViewById(R.id.buttonNextRound).setVisibility(View.INVISIBLE);

      }
      // If there is another round to be played, prompt accordingly
      else{
         gameOverStatus.setText("There's still more fun to be had!");
         gameWinnerDisplay.setText("No one has won the game just yet!");
         roundNumberDisplay.setText("\nReady for round " + String.valueOf(nextRound) + "?");
      }
   }

   /** Sends user to coin toss portion of the game */
   public void goToGoinToss(View view) {
      Intent intent = new Intent(this, CoinTossController.class);
      startActivity(intent);
   }

   /** Starts the next round
    @param view View that creates an intent and starts the next round activity */
   public void startNextRound(View view) {

      Intent nextRoundIntent = new Intent(this,RoundController.class);

      // set the human and computer scores
      nextRoundIntent.putExtra("humanScore", humanTotal);
      nextRoundIntent.putExtra("computerScore",computerTotal);

      // send whether or not human is up next
      if(humanRound>computerRound){
         nextRoundIntent.putExtra("humanUpNext",false);
      }
      else {
         nextRoundIntent.putExtra("humanUpNext", true);
      }

      // Ensure round has the correct round number and loading status info
      nextRoundIntent.putExtra("roundNumber", nextRound);
      nextRoundIntent.putExtra("loadingStatus", false);
      startActivity(nextRoundIntent);
   }
}
