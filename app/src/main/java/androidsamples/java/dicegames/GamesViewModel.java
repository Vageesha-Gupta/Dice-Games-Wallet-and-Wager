package androidsamples.java.dicegames;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;
    public int balance=0;


    private Die[] dice; // Array to hold dice objects
    private int numberOfDice;

    Die die;
//    // toast
//    private MutableLiveData<Boolean> sixRolledLiveData = new MutableLiveData<>();
    //test end
    public GamesViewModel() {
        balance = 0;
        die = new Die6();
    }

    public int getBalance() {
        return balance;
    }

    public int dieValue() {
        return die.value();
    }

//    public LiveData<Boolean> getSixRolledLiveData() {
//        return sixRolledLiveData;
//    }

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
                return wager > 0 && wager <= balance + 20; // Example condition
            case FOUR_ALIKE:
                return wager > 0 && wager <= balance + 40; // Example condition
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
                    return GameResult.WIN; // Example win condition
                }
                break;
            case THREE_ALIKE:
                // Check for three alike
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2]) {
                    return GameResult.WIN; // Example win condition
                }
                break;
            case FOUR_ALIKE:
                // Check for four alike
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2] && diceValues[2] == diceValues[3]) {
                    return GameResult.WIN; // Example win condition
                }
                break;
        }
        return GameResult.LOSS; // Default to loss
    }

    public int[] diceValues() {
        // Simulate rolling dice. This can be more sophisticated in a real game.
        return new int[]{1, 2, 3, 4}; // Placeholder for actual dice values
    }


    @Override
    protected void onCleared(){
        super.onCleared();
    }


}