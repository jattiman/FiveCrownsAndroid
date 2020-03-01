/*******************************************************
 Serves as the view controller for the start screen of the game
 @author John Atti
 @since 11/9/2019
  ******************************************************** */

package com.johnatti.fivecrownsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Vector;

public class StartScreenController extends AppCompatActivity {
//   public static final String EXTRA_MESSAGE = "com.johnatti.fivecrownsandroid.MESSAGE";

   /** Our announcement dialogue box, for debugging */
   TextView announce;
   EditText fileName;
   Button fileFind, caseLoader, playerLoader;


   /** Vector saving each line of the file */
   Vector<String> fileString = new Vector<String>();

   /** onCreate function to organize window orientation, initialize views and display opening message */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      // ensure the screen cannot rotate
      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

      // stop the keyboard from moving the layout around
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

      // initialize all buttons/text fields/ etc
      initializeViews();

      // display opening message
      openingMessage();

   }

   /** Prompts user to select an option from the screen. */
   public void openingMessage(){
      String openingMessage="\nSelect an option from above!";
      Intent returnIntent = getIntent();
      Bundle extras = returnIntent.getExtras();
      // If we received an intent with a separate start message, load that instead
      if(extras != null){
         openingMessage=extras.getString("StartMessage");
      }
      announce.append(openingMessage);

   }

   /** Called to set our buttons/views/etc */
   public void initializeViews(){
      announce=(TextView) findViewById(R.id.mainAnnouncement);
      fileName = (EditText) findViewById(R.id.fileNameText);
      fileFind = (Button) findViewById(R.id.loadGameButton);
      caseLoader = (Button) findViewById(R.id.caseLoad);
      playerLoader = (Button) findViewById(R.id.playerLoad);
   }

   /** Called when user taps the new game button
    @param view View of the button clicked to access this
    */
   public void newGame(View view){
      // If new game, pass the user to the coin toss activity
      Intent intent = new Intent(this, CoinTossController.class);
      startActivity(intent);
   }

   /** Show user the file names for all files in the round
    @param view View of the button clicked to access this
    */
   public void viewLoadOptions(View view){
      File ourFiles = getFilesDir();
      String[] fileList = ourFiles.list();
      Arrays.sort(fileList);

      // list file names
      announce.append("Player game names to input:\n");
      for(String ourString:fileList){
         announce.append("\n"+ourString);
      }
      announce.append("\n\nCase names to input:\ncase1.txt\ncase2.txt\ncase3.txt");

      // make file selection buttons visible
      fileName.setVisibility(View.VISIBLE);
      caseLoader.setVisibility(View.VISIBLE);
      playerLoader.setVisibility(View.VISIBLE);


   }

   /** Called when user taps the load cases button. Loads the Legendary Professor Kumar Cases
    @param view View button clicked
    */
   public void loadCases(View view){
      // set up the intent to begin the round with loaded information
      Intent intent = new Intent(this, RoundController.class);

      // load default values for now.
      intent.putExtra("humanUpNext",false);
      intent.putExtra("roundNumber",1);

      // load in the information from a separate function
      String caseFileBigString="";
      try{
         String ourFileString = fileName.getText().toString();
         // access the assets folder of pre-made cases, and select the user specified file
         //InputStream ourInput = getAssets().open("case3.txt");
         InputStream ourInput = getAssets().open(ourFileString);

         // clear our file vector to ensure old saves are no longer present
         fileString.clear();

         // read through the asset folder file
         BufferedReader ourBuff = new BufferedReader(new InputStreamReader(ourInput));
         // add file contents to our file string vector
         for(String line; (line=ourBuff.readLine())!=null;){
            if(line.contains(":")) {
               fileString.add(line);
               caseFileBigString+=line;
               caseFileBigString+="\n";
            }
         }

         // announce success to user
         announce.setText("\n\nFile found and loaded.");
         // output the contents of the file
         for(String ourString:fileString){
            announce.append("\n"+ourString);
         }
         intent.putExtra("loadingStatus",true);
         intent.putExtra("fileLoad",caseFileBigString);

         // begin the round
         startActivity(intent);

      }
      // if loading the file wasn't successful
      catch(IOException ex){
         // inform the user
         announce.setText("File NOT found.");
      }


   }

   /** Called when user taps the load game button
    @param view View button clicked
    */
   public void loadGame(View view){
      // set up the intent to begin the round with loaded information
      Intent intent = new Intent(this, RoundController.class);

      // load default values for now.
      intent.putExtra("humanUpNext",false);
      intent.putExtra("roundNumber",1);

      // load in the information from a separate function
      String playerFileString="";
      try{
         String ourFileString = fileName.getText().toString();
         // get the path of the file directory
         File path = getFilesDir();

         // open the specified file within the directory
         File testFile = new File(path, ourFileString);

         // clear the file string
         fileString.clear();

         // read from the file
         BufferedReader ourBuff = new BufferedReader(new FileReader(testFile));
         for(String line; (line=ourBuff.readLine())!=null;){
            //announce.append(line + "\n");
            if(line.contains(":")) {
               fileString.add(line);
               playerFileString+=line;
               playerFileString+="\n";
            }
         }

         // update user on success
         announce.setText("\n\nFile found. Loading ... ");

         // output file to debug window
         for(String ourString:fileString){
            announce.append("\n"+ourString);
         }

         intent.putExtra("loadingStatus",true);
         intent.putExtra("fileLoad",playerFileString);


         // begin the round
         startActivity(intent);

      }
      // If file doesn't open successfully
      catch(IOException ex){
         // inform user the file wasn't found
         announce.setText("File NOT found.");

      }

   }

   /** Add intents function. As this game grows more robust (after the demo), I can use this to add more passed functionality. */
   public void addIntents(){

   }

   /** Called when user taps the quit button
    @param view View button corresponding with quit function.
    */
   public void quitGame(View view){
      // wrap everything up and shut the app down
      finish();
      System.exit(0);
   }
}
