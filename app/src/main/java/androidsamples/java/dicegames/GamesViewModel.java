package androidsamples.java.dicegames;

import android.util.Log;
import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends ViewModel {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;
    public int balance = 0;

    private Die[] dice; // Array to hold dice objects
    private int numberOfDice;
    private int wager;  // Wager for the game
    private GameType gameType;  // Type of the game being played

    Die die;

    public GamesViewModel() {
        balance = 0;
        die = new Die6();
        numberOfDice = 4;  // Initialize with 4 dice
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new Die6();  // Assuming Die6 class represents a 6-sided die
        }
    }

    public int getBalance() {
        return balance;
    }

    public int dieValue() {
        return die.value();
    }

    public void rollWalletDie() {
        die.roll();
        int currentRoll = die.value();
        if (currentRoll == WIN_VALUE) {
            balance += INCR_VALUE;
        }
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setWager(int wager) {
        this.wager = wager;
    }

    public int getWager() {
        return wager;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public boolean isValidWager() {
        switch (gameType) {
            case TWO_ALIKE:
                return wager > 0 && wager <= balance;
            case THREE_ALIKE:
                return wager > 0 && wager <= balance;  // Correct the condition if necessary
            case FOUR_ALIKE:
                return wager > 0 && wager <= balance;  // Correct the condition if necessary
            default:
                return false;
        }
    }

    public GameResult play() {
        if (wager <= 0) {
            throw new IllegalStateException("Wager not set, can't play!");
        }
        if (gameType == null) {
            throw new IllegalStateException("Game Type not set, can't play!");
        }

        int[] diceValues = diceValues();
        switch (gameType) {
            case TWO_ALIKE:
                // Check for two alike
                if (diceValues[0] == diceValues[1]) {
                    return GameResult.WIN;
                }
                break;
            case THREE_ALIKE:
                // Check for three alike
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2]) {
                    return GameResult.WIN;
                }
                break;
            case FOUR_ALIKE:
                // Check for four alike
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2] && diceValues[2] == diceValues[3]) {
                    return GameResult.WIN;
                }
                break;
        }
        return GameResult.LOSS;
    }

    public int[] diceValues() {
        int[] values = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i].roll();
            values[i] = dice[i].value();
        }
        return values;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
