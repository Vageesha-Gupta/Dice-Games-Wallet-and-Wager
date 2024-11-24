package androidsamples.java.dicegames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


@RunWith(MockitoJUnitRunner.class)
public class GamesViewModelWalletTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;

    @Mock
    private Application mockApplication;  // Mocking the Application object
    @Mock
    private SharedPreferences mockSharedPreferences;


    @Spy
    private Die walletDie;
    @InjectMocks
    private GamesViewModel m;


    private AutoCloseable closeable;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        m = Mockito.spy(new GamesViewModel(mockApplication));
        // Mock SharedPreferences if needed
        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);
        // Mock SharedPreferences.getInt to return a value (e.g., 5)
        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Assume default balance is 100
    }

    @Before
    public void openMocks() {

        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void rolling6IncrementsBalanceBy5() {
//        int oldBalance = m.balance.getValue();
        int oldBalance = 100;
        when(walletDie.value()).thenReturn(WIN_VALUE);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        int oldBalance = m.balance.getValue();
        when(walletDie.value()).thenReturn(1);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }

    //more tests
    @Test
    public void rolling3DoesNotChangeBalance() {
        int oldBalance = m.balance.getValue();
        when(walletDie.value()).thenReturn(3);  // Non-winning number (3)

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));  // Balance should not change
    }

    @Test
    public void twoConsecutiveWinsIncrementBalanceBy10() {
        int oldBalance = m.balance.getValue();
        when(walletDie.value()).thenReturn(WIN_VALUE);  // Winning number (6)

        // Roll twice with a winning value
        m.rollWalletDie();
        m.rollWalletDie();

        // Each roll increases balance by 5, so after 2 rolls, balance should increase by 10
        assertThat(m.balance, is(oldBalance + 2 * INCR_VALUE));
    }

    @Test
    public void testCanPlaceWager() {
        m.setBalance(100);
        assertThat(m.isValidWager(), is(true));  // **Updated to just check the validity, adjust method if needed**

        m.setWager(3);  // Set wager for next checks
        assertThat(m.isValidWager(), is(false));  // Adjusted to ensure method checks correctly
    }

    @Test
    public void testPlayGameWin() {
        // Set the balance and mock die rolls for "3 alike" game
        m.setBalance(10);
        m.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
//        when(walletDie.value()).thenReturn(3, 3, 3, 2);  // 3 alike
        Mockito.doReturn(new int[]{3, 3, 3, 2}).when(m).diceValues();

        m.setWager(3);  // **Set wager before playing**
        GameResult result = m.play();  // **Call play() method instead of playGame()**

        // Balance should increase by 3 * 3 = 9 coins
        assertThat(m.getBalance(), is(19));  // **Using getter method**
        assertThat(result, is(GameResult.WIN));  // **Check for win result**
    }

    @Test
    public void testPlayGameLose() {
        // Set the balance and mock die rolls for "3 alike" game
        m.setBalance(10);
        m.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
        when(walletDie.value()).thenReturn(2, 2, 3, 4);  // Not 3 alike

        m.setWager(3);  // **Set wager before playing**
        GameResult result = m.play();

        // Balance should decrease by 3 * 3 = 9 coins
        assertThat(m.getBalance(), is(1));  // **Using getter method**
        assertThat(result, is(GameResult.LOSS));  // **Check for loss result**
    }

    @Test
    public void canPlaceWagerReturnsTrueWhenWagerIsValid() {
        m.setBalance(10); // Set initial balance
        m.setWager(5); // **Set wager before validation**
        assertThat(m.isValidWager(), is(true)); // Adjusted to validate wager correctly
    }

    @Test
    public void canPlaceWagerReturnsFalseWhenWagerIsTooHigh() {
        m.setBalance(10); // Set initial balance
        m.setWager(5); // **Set wager before validation**
        m.setGameType(GameType.THREE_ALIKE); // **Set the game type**
        assertThat(m.isValidWager(), is(false)); // Ensure method checks correctly
    }

    @Test
    public void playGameWinsWhenDiceAreFourAlike() {
        m.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {1, 1, 1, 1}; // Mocking dice values

        when(m.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m.setWager(wager); // **Set wager before playing**
        m.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.WIN)); // Check for win result
        assertThat(m.getBalance(), is(105)); // Verify balance after win
    }

    @Test
    public void playGameLosesWhenDiceAreNotFourAlike() {
        m.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {2, 1, 1, 1}; // Mocking dice values

        when(m.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m.setWager(wager); // **Set wager before playing**
        m.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.LOSS)); // Check for loss result
        assertThat(m.getBalance(), is(95)); // Verify balance after loss
    }
}

