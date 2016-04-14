package cs240.byu.edu.evilhangman_android;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import cs240.byu.edu.evilhangman_android.StudentPackage.IEvilHangmanGame;
import cs240.byu.edu.evilhangman_android.StudentPackage.StudentEvilHangmanGameController;

public class GamePlay extends AppCompatActivity
{
    StudentEvilHangmanGameController studentController;
    EditText guessInput;
    TextView guessesLeft;
    TextView usedLetters;
    TextView currentWord;
    Set<String> currentWorkingSet;
    String theCorrectWord;
    boolean playable = true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        guessInput = (EditText)findViewById(R.id.input_guess);

        guessesLeft = (TextView)findViewById(R.id.guesses_left_fill_in);
        usedLetters = (TextView)findViewById(R.id.used_letters_fill_in);
        currentWord = (TextView)findViewById(R.id.currentWord);

        Bundle b = this.getIntent().getExtras();
        int guessesPassedIn = b.getInt(MainActivity.guessAmountKey);
        int wordLength = b.getInt(MainActivity.wordLengthKey);
        String dictionaryFile = b.getString(MainActivity.fileSelectedKey);
        dictionaryFile = dictionaryFile == null ? "dictionary.txt" : dictionaryFile;

        guessesLeft.setText(String.valueOf(guessesPassedIn));
        usedLetters.setText("");

        //This only works on a few select devices (namely an emulator, but I am sure other devices
        //exist. It is useless on most devices however.
        guessInput.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    MakeGuess(v);
                    return true;
                }
                return false;
            }
        });


        /**
         * TODO attach your controller here. Your controller is your class that implements
         * {link #StudentEvilHangmanGameController}
         * e.g.: studentController = new MyEvilHangmanGame();
         */
        studentController = null;

        if(studentController == null)
        {
            //Display a message if studentController is null
            ScrollView nullScrollView = (ScrollView)findViewById(R.id.null_controller_view);
            nullScrollView.setVisibility(View.VISIBLE);
            ScrollView normalView = (ScrollView)findViewById(R.id.scroll_view);
            normalView.setVisibility(View.INVISIBLE);
        }
        else
        {
            studentController.setNumberOfGuesses(guessesPassedIn);

            try
            {
                InputStream is = getAssets().open(dictionaryFile);
                studentController.startGame(new InputStreamReader(is), wordLength);
                updateCurrentWord();
            } catch (IOException e)
            {
                Toast.makeText(this, "Dictionary File Not Found", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Called from the 'Make a Guess' button. This listener is attached in the XML under the button.
    public void MakeGuess(View v)
    {
        if(playable)
        {
            String inputGuess = guessInput.getText().toString();
            if (inputGuess.length() > 0)
            {
                try
                {
                    currentWorkingSet = studentController.makeGuess(inputGuess.charAt(0));
                } catch (IEvilHangmanGame.GuessAlreadyMadeException e)
                {
                    Snackbar.make(findViewById(android.R.id.content), "That letter has already been guessed. Try another.", Snackbar.LENGTH_SHORT).show();
                }

                updateUIBasedOnGameStatus(currentWorkingSet);
            } else
            {
                Snackbar.make(findViewById(android.R.id.content), "You have to guess a letter first!", Snackbar.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"The Game is over! The word was:" + theCorrectWord + ". Go back to play again", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUIBasedOnGameStatus(Set<String> currentWorkingSet)
    {
        updateNumberOfGuessesLeft();
        updateUsedLetters();
        updateCurrentWord();

        StudentEvilHangmanGameController.GAME_STATUS currentGameStatus = studentController.getGameStatus();
        switch (currentGameStatus)
        {
            case NORMAL:
                break;
            case PLAYER_WON:
                playable = false;
                if(currentWorkingSet.size() > 0)
                {
                    theCorrectWord = currentWorkingSet.iterator().next();
                    Toast.makeText(GamePlay.this, "You Won. The word was " + theCorrectWord, Toast.LENGTH_LONG).show();
                }
                else
                {
                    //If you get here during the game it probably means you choose a word length that didn't exist in the
                    //dictionary file. That is to say you picked word length of 2, and there were no words in the dictionary
                    //file that were only 2 letters long.
                    Toast.makeText(GamePlay.this, "No winning word was found.....", Toast.LENGTH_LONG).show();
                }
                break;
            case PLAYER_LOST:
                playable = false;
                if(currentWorkingSet.size() > 0)
                {
                    theCorrectWord = currentWorkingSet.iterator().next();
                    Toast.makeText(GamePlay.this, "You lost. The word was " + theCorrectWord, Toast.LENGTH_LONG).show();
                }
                else
                {
                    //If you get here during the game it probably means you choose a word length that didn't exist in the
                    //dictionary file. That is to say you picked word length of 2, and there were no words in the dictionary
                    //file that were only 2 letters long.
                    Toast.makeText(GamePlay.this, "No winning word was found......", Toast.LENGTH_LONG).show();
                }
                break;
        }

        guessInput.setText("");
    }

    private void updateNumberOfGuessesLeft()
    {
        guessesLeft.setText(String.valueOf(studentController.getNumberOfGuessesLeft()));
    }

    private void updateUsedLetters()
    {
        Set<Character> usedLettersSet = studentController.getUsedLetters();

        usedLetters.setText(usedLettersSet.toString());
    }

    private void updateCurrentWord()
    {
        String word = studentController.getCurrentWord();
        currentWord.setText(word);
    }
}
