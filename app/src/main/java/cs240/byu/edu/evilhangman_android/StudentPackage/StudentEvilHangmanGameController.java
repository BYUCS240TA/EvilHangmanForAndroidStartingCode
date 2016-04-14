package cs240.byu.edu.evilhangman_android.StudentPackage;

import java.util.Set;

/**
 * YOU <strong>SHOULD</strong> IMPLEMENT THIS INTERFACE!!!!!
 */
public interface StudentEvilHangmanGameController extends IEvilHangmanGame
{
    /**
     * So the View knows what state the game is in. Return one of these from the method {@link #getGameStatus()}
     */
    enum GAME_STATUS{
        NORMAL, PLAYER_WON, PLAYER_LOST;
    }

    /**
     * This will get called after every valid guess. You should check to see if the user has won,
     *  lost, or is still in the heat of battle! (aka they are still playing)
     * @return {@link GAME_STATUS}
     */
    public GAME_STATUS getGameStatus();

    /**
     * Simply return the number of guesses the user has left before the game ends
     * @return number of guesses left
     */
    public int getNumberOfGuessesLeft();

    /**
     * Return what the current word looks like. For example, lets say the game has started and the
     *  word length is 5. Then you would return this string "-----" (that's 5 dashes). Now after the
     *  user has guess a letter (say the letter a), you would return this string "-a---". When the
     *  user guesses the letter z you return the string "-azz-".
     * @return the current word to show on the screen (dashes and letters)
     */
    public String getCurrentWord();

    /**
     * Return a set of characters of all the guesses that have been made already. This is simply to
     *  show on the screen what guesses have been made. YOU still need to check on every guess if that
     *  character has already been guessed or not
     * @return Set of all characters already guessed
     */
    public Set<Character> getUsedLetters();

    /**
     * This is called at the beginning of the game to let your controller know how many guesses to start
     *  the game with.
     * @param numberOfGuessesToStart
     */
    public void setNumberOfGuesses(int numberOfGuessesToStart);
}
