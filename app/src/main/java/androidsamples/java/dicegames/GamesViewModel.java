package androidsamples.java.dicegames;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends AndroidViewModel {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;
    private static final int INCR_DOUBLE_SIXES_VALUE=10;

    public MutableLiveData<Integer> balance = new MutableLiveData<>(0); // Initialize with 0
    private MutableLiveData<Integer> dieValue = new MutableLiveData<>(0); // Store die value here
    private SharedPreferences prefs;
    private Die[] dice; // Array to hold dice objects
    private int numberOfDice;
    private int wager;  // Wager for the game
    private GameType gameType;// Type of the game being played
    private int currentDieVal=0;
    private int prevDieVal=0;

    Die die;

    public GamesViewModel(Application application) {
        //change
        super(application);
        prefs = application.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE);
        int savedBalance = prefs.getInt("balance", 0);
        balance.setValue(savedBalance);
        // Initialize die value from saved value or set a default
        int savedDieValue = prefs.getInt("dieValue", 0);
        dieValue.setValue(savedDieValue);

        //change
        if (balance.getValue() == null) {
            balance.setValue(savedBalance);
        }

        die = new Die6();
        numberOfDice = 4;  // Initialize with 4 dice
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new Die6();  // Assuming Die6 class represents a 6-sided die
        }
    }

    public LiveData<Integer> getBalance() {
        return balance; // Return LiveData
    }

    public int dieValue() {
        return die.value();
    }
    public LiveData<Integer> getDieValue() {
        return dieValue;
    }

    public void rollWalletDie() {

        die.roll();
        int currentRoll = die.value();
        prevDieVal=currentDieVal;
        currentDieVal=currentRoll;
        // Save the die value to LiveData
        dieValue.setValue(currentDieVal);

        Log.d("GamesViewModel", "Die rolled: " + currentRoll);
        if (prevDieVal==WIN_VALUE && currentRoll == WIN_VALUE) {
            setBalance(INCR_DOUBLE_SIXES_VALUE);
        }
        else if(currentRoll == WIN_VALUE){
            setBalance(INCR_VALUE);
        }
    }
    // Save the die value to SharedPreferences when updated
    private void saveDieValue(int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("dieValue", value);
        editor.apply();
        Log.d("GamesViewModel", "Saved die value: " + value);
    }
    public void setBalance(int increment) {
        int currentBalance = balance.getValue() != null ? balance.getValue() : 0;
        int newBalance = currentBalance + increment;
        balance.setValue(newBalance);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("balance", newBalance);
        editor.apply();

        Log.d("GamesViewModel", "Balance updated: " + newBalance);
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
        if (wager == 0) return false;
        Integer currentBalance = balance.getValue();
        if (currentBalance == null) return false; // Ensure balance is not null
        switch (gameType) {
            case TWO_ALIKE:
                return wager > 0 && 2 * wager <= currentBalance;
            case THREE_ALIKE:
                return wager > 0 && 3 * wager <= currentBalance;  // Correct the condition if necessary
            case FOUR_ALIKE:
                return wager > 0 && 4 * wager <= currentBalance;  // Correct the condition if necessary
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
        GameResult result = GameResult.LOSS;  // Default to loss

        switch (gameType) {
            case TWO_ALIKE:
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2] || diceValues[0] == diceValues[1] && diceValues[1] == diceValues[3] || diceValues[0] == diceValues[3] && diceValues[3] == diceValues[2] || diceValues[3] == diceValues[1] && diceValues[1] == diceValues[2]) {
                    updateBalance(-2);
                }
                else if (diceValues[0] == diceValues[1] || diceValues[0] == diceValues[2] || diceValues[0] == diceValues[3] ||
                        diceValues[1] == diceValues[2] || diceValues[1] == diceValues[3] ||
                        diceValues[2] == diceValues[3]) {
                    result = GameResult.WIN;
                    updateBalance(2);
                } else {
                    updateBalance(-2);
                }
                break;
            case THREE_ALIKE:
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2] || diceValues[0] == diceValues[1] && diceValues[1] == diceValues[3] || diceValues[0] == diceValues[3] && diceValues[3] == diceValues[2] || diceValues[3] == diceValues[1] && diceValues[1] == diceValues[2]) {
                    result = GameResult.WIN;
                    updateBalance(3);
                } else {
                    updateBalance(-3);
                }
                break;
            case FOUR_ALIKE:
                if (diceValues[0] == diceValues[1] && diceValues[1] == diceValues[2] && diceValues[2] == diceValues[3]) {
                    result = GameResult.WIN;
                    updateBalance(4);
                } else {
                    updateBalance(-4);
                }
                break;
        }

        return result;  // Return the result of the game
    }

    public void updateBalance(int multiplier) {
        int currentBalance = balance.getValue() != null ? balance.getValue() : 0;
        int newBalance = currentBalance + (wager * multiplier);
        balance.setValue(newBalance);

        // Save to SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("balance", newBalance);
        editor.apply();

        Log.d("GamesViewModel", "Balance updated and saved: " + newBalance);
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

