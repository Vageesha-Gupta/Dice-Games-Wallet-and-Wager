package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends ViewModel {
    public int balance = 0;
    private Die walletDie;
    public int[] diceValues = new int[4];// Array to store the rolled values
    Die[] diceRolls = new Die[4];// Four dice rolls
    int gameType;
    int wager;

    public GamesViewModel() {
        this.walletDie = new Die() {
            @Override
            public void roll() {

            }

            @Override
            public int value() {
                return 0;
            }
        };
    }
    public void setGameType(int type){
        this.gameType = type;
    }
    public int getGameType(){
        return gameType;
    }
    public void setWager(int wager){
        this.wager = wager;
    }
    public int getWager(){
        return wager;
    }
    public void rollWalletDie() {
        walletDie.roll();
        int value = walletDie.value();
        if(value == 6){
            balance += 5;
        }
    }
    public int getWalletBalance(){
        return balance;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }

    public boolean canPlaceWager(int wager, int gameType) {
        int maxBet = balance / gameType;
        return wager <= maxBet;
    }


    public boolean playGame(int wager, int gameType) {

        for (int i = 0; i < 4; i++) {
            diceRolls[i] = new Die() {
                @Override
                public void roll() {
                }
                @Override
                public int value() {
                    return 0;
                }
            };
            diceRolls[i].roll();  // Roll the die
        }

        int alikeCount = checkAlikeCount(diceRolls);  // Count alike dice
        if (alikeCount >= gameType) {
            balance += wager * gameType;
            return true; // Win case
        } else {
            balance -= wager * gameType;
            return false;// Lose case
        }
    }
    public int[] diceValues() {
        int[] values = new int[4];
        for (int i = 0; i < 4; i++) {
            values[i] = diceRolls[i].value();  // Get the value of each die
        }
        return values;  // Return the array of die values
    }

    private int checkAlikeCount(Die[] diceRolls) {
        int[] counts = new int[7];  // To count dice values from 1 to 6
        for (Die die : diceRolls) {
            counts[die.value()]++;  // Count the occurrence of each die value
        }
        int maxAlike = 0;
        for (int count : counts) {
            if (count > maxAlike) {
                maxAlike = count;
            }
        }
        return maxAlike;
    }


    public String isValidWager() {
        if (wager <= 0) {
            return "Wager must be greater than 0";
        }
        if (wager > balance) {
            return "Wager must be less than or equal to balance";
        }
        if (wager % gameType != 0) {
            return "Wager must be a multiple of game type";
        }
        return "Wager is valid";
    }
}