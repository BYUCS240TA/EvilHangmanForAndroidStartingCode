package cs240.byu.edu.evilhangman_android.StudentPackage;

import java.io.InputStreamReader;
import java.util.Set;

/**
 * YOU SHOULD <strong>NOT</strong> IMPLEMENT THIS INTERFACE!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * YOU SHOULD IMPLEMENT the StudentEvilHangmanGameController interface!!!!!!!!!!!!
 */


public interface IEvilHangmanGame {

    @SuppressWarnings("serial")
    public static class GuessAlreadyMadeException extends Exception {
    }

    /**
     * Starts a new game of evil hangman using words from <code>dictionary</code>
     * with length <code>wordLength</code>.
     *	<p>
     *	This method should set up everything required to play the game,
     *	but should not actually play the game. (ie. There should not be
     *	a loop to prompt for input from the user.)
     *
     * @param dictionary Dictionary of words to use for the game
     * @param wordLength Number of characters in the word to guess
     */
    public void startGame(InputStreamReader dictionary, int wordLength);


    /**
     * Make a guess in the current game.
     *
     * @param guess The character being guessed
     *
     * @return The set of strings that satisfy all the guesses made so far
     * in the game, including the guess made in this call. The game could claim
     * that any of these words had been the secret word for the whole game.
     *
     * @throws GuessAlreadyMadeException If the character <code>guess</code>
     * has already been guessed in this game.
     */
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException;

}
