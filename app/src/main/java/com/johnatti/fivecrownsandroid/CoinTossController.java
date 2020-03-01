/*******************************************************
 Acts as the view/controller to organize the coin toss
 @author John Atti
 @since 11/13/2019
  ******************************************************** */

package com.johnatti.fivecrownsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CoinTossController extends AppCompatActivity {

   /** variable holding who is up next in the game */
   protected boolean upNext;

   /** Game model */
   Game game;

   /** TextView to show coin toss result */
   TextView coinResultTextView;

   /** TextView to show win/loss status */
   TextView winLoseTextView;

   /** ImageView to show a coin image. Got a bit ambitious, here, and this was not implemented. */
   ImageView coinImage;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_coin_toss);

      // ensure the screen cannot rotate
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      // Ensure that human is not set to start by default
      upNext=false;

      // Instantiate the new game object
      game = new Game();
   }

   /** Tosses a coin. If user guesses correctly, they go first.
    @return  int representing the coin toss result (random number) */
   public int tossCoin(){
      // Use model to toss coin
      game.coinToss();

      // Get result of coin tossed in model
      int ourRandomNumber=game.getTossResult();

      // Prime the coin toss result view
      coinResultTextView = (TextView) findViewById(R.id.coinTossResult);

      // Display what side the coin landed for: heads or tails
      if(ourRandomNumber==0){
         coinResultTextView.setText("Heads");
      }
      if(ourRandomNumber==1){
         coinResultTextView.setText("Tails");
      }

      // Return this result
      return ourRandomNumber;
   }

   /** If the user guesses heads, see if they guessed correctly
    @param view View parameter representing the button clicked
    */
   public void headsGuess(View view){
      //Note: I did this when first learning android, and now know that I could have both buttons
      // go to one function that would react based off of which button "view" clicked it.

      // create int to store the coin toss result
      int result = tossCoin();

      // Prime the win/lose text notification view
      winLoseTextView = (TextView) findViewById(R.id.winOrLose);

      // Notify user and set whether or not the human is up next accordingly
      if(result==0){
         winLoseTextView.setText("You guessed correctly! Go first.");
         upNext=true;
      }
      else{
         winLoseTextView.setText("You guessed incorrectly. Computer goes first.");
      }

      // Set unneeded buttons to invisible
      updateButtons();
   }

   /** If the user guesses tails, see if they guessed correctly
    @param view View parameter representing the button clicked
    */
   public void tailsGuess(View view){

      // create variable to hold coin toss result
      int result = tossCoin();

      // prime the view to notify user
      winLoseTextView = (TextView) findViewById(R.id.winOrLose);

      // notify user of whether or not their guess was correct
      if(result==1){
         winLoseTextView.setText("You guessed correctly! Go first.");
         upNext=true;
      }
      else{
         winLoseTextView.setText("You guessed incorrectly. Computer goes first.");
      }

      // update button invisibility
      updateButtons();
   }

   /** Updates button invisibility after the coin toss is over, to guide user.
    */
   public void updateButtons(){
      // ensure all non-needed buttons are now invisible so that the user can't click them
      // also ensure the buttons needed to progress to the next round, and the coin toss results,
      // are visible
      findViewById(R.id.buttonPlayAfterCoin).setVisibility(View.VISIBLE);
      findViewById(R.id.buttonHeads).setVisibility(View.INVISIBLE);
      findViewById(R.id.buttonTails).setVisibility(View.INVISIBLE);
      findViewById(R.id.winOrLose).setVisibility(View.VISIBLE);
      findViewById(R.id.coinTossResult).setVisibility(View.VISIBLE);
   }

   /** Starts the game based on the coin toss result
    @param view View parameter representing the start game button clicked
    */
   public void startGame(View view){

      Intent intent = new Intent(this, RoundController.class);

      // pass if the human is going first
      intent.putExtra("humanUpNext",upNext);

      // ensure that the game knows it's starting at the first round
      intent.putExtra("roundNumber",1);

      // ensure that the game knows it's not loading a file
      intent.putExtra("loadingStatus",false);

      // pass in default scores.
      // other controllers will pass these scores to the main game activity,
      // so this will ensure we don't run into conflicts.
      intent.putExtra("humanScore", 0);
      intent.putExtra("computerScore",0);

      // start game
      startActivity(intent);
   }

}
