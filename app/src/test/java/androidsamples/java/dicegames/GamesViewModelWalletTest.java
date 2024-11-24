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
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class GamesViewModelWalletTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;

    @Mock
    private Application mockApplication;  // Mocking the Application object
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;


//    @Spy
//    private Die walletDie = new Die();;
    @Mock
    Die walletDie;

    private GamesViewModel m1;


    private AutoCloseable closeable;
    @Before
    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        m = Mockito.spy(new GamesViewModel(mockApplication));
//        // Mock SharedPreferences if needed
//        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
//                .thenReturn(mockSharedPreferences);
//        // Mock SharedPreferences.getInt to return a value (e.g., 5)
//        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Assume default balance is 100
        MockitoAnnotations.openMocks(this);

        // Mock SharedPreferences behavior
        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(0); // Default balance
        when(mockSharedPreferences.getInt("dieValue", 0)).thenReturn(0);



        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor);// Support chaining

//        when(mockEditor.apply()).thenAnswer(invocation -> null); // Mock apply() behavior
        doNothing().when(mockEditor).apply(); // This will do nothing when apply() is called


        // Initialize the GamesViewModel with the mocked Application
        m1 = spy(new GamesViewModel(mockApplication));
        // Inject the mocked Die into the ViewModel

        m1.die = walletDie;

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
        int oldBalance = m1.balance.getValue(); // Capture the initial balance

        // Mock die's behavior
        when(walletDie.value()).thenReturn(WIN_VALUE); // Force a WIN_VALUE roll

        // Call rollWalletDie
        m1.rollWalletDie();

        // Assert that the balance is updated correctly
        assertThat(m1.getBalance().getValue(), is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        int oldBalance = m1.balance.getValue();

        when(walletDie.value()).thenReturn(1);

        m1.rollWalletDie();
        assertThat(m1.getBalance().getValue(), is(oldBalance));
    }

    //more tests
    @Test
    public void rolling3DoesNotChangeBalance() {
        int oldBalance = m1.balance.getValue();
        when(walletDie.value()).thenReturn(3);  // Non-winning number (3)

        m1.rollWalletDie();
        assertThat(m1.getBalance().getValue(), is(oldBalance));  // Balance should not change
    }

    @Test
    public void twoConsecutiveWinsIncrementBalanceBy10() {
        int oldBalance = m1.getBalance().getValue();
        System.out.println("old bal: " + oldBalance) ;
        when(walletDie.value()).thenReturn(WIN_VALUE);  // Winning number (6)
        System.out.println(m1.getBalance().getValue());
        // Roll twice with a winning value
        m1.rollWalletDie();
        System.out.println(m1.getBalance().getValue());
        m1.rollWalletDie();
        System.out.println(m1.getBalance().getValue());

        // Each roll increases balance by 5, so after 2 rolls, balance should increase by 10
        assertThat(m1.getBalance().getValue(), is(oldBalance + 5+10));
    }



    @Test
    public void testPlayGameWin() {
        // Set the balance and mock die rolls for "3 alike" game
        m1.setBalance(10);
        m1.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
//        when(walletDie.value()).thenReturn(3, 3, 3, 2);  // 3 alike
//        Mockito.doReturn(new int[]{3, 3, 3, 2}).when(m1.diceValues());
        when(m1.diceValues()).thenReturn(new int[]{3, 3, 3, 2});

        m1.setWager(3);  // **Set wager before playing**
        GameResult result = m1.play();  // **Call play() method instead of playGame()**

        // Balance should increase by 3 * 3 = 9 coins
        assertThat(m1.getBalance().getValue(), is(19));  // **Using getter method**
        assertThat(result, is(GameResult.WIN));  // **Check for win result**
    }

    @Test
    public void testPlayGameLose() {
        // Set the balance and mock die rolls for "3 alike" game
        m1.setBalance(10);
        m1.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
        when(walletDie.value()).thenReturn(2, 2, 3, 4);  // Not 3 alike

        m1.setWager(3);  // **Set wager before playing**
        GameResult result = m1.play();

        // Balance should decrease by 3 * 3 = 9 coins
        assertThat(m1.getBalance().getValue(), is(1));  // **Using getter method**
        assertThat(result, is(GameResult.LOSS));  // **Check for loss result**
    }

    @Test
    public void canPlaceWagerReturnsTrueWhenWagerIsValid() {
        m1.setBalance(10); // Set initial balance
        m1.setWager(5); // **Set wager before validation**
        m1.setGameType(GameType.TWO_ALIKE);
        assertThat(m1.isValidWager(), is(true)); // Adjusted to validate wager correctly
    }

    @Test
    public void canPlaceWagerReturnsFalseWhenWagerIsTooHigh() {
        m1.setBalance(10); // Set initial balance
        m1.setWager(5); // **Set wager before validation**
        m1.setGameType(GameType.THREE_ALIKE); // **Set the game type**
        assertThat(m1.isValidWager(), is(false)); // Ensure method checks correctly
    }

    @Test
    public void playGameWinsWhenDiceAreFourAlike() {
        m1.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {1, 1, 1, 1}; // Mocking dice values

        when(m1.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m1.setWager(wager); // **Set wager before playing**
        m1.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m1.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.WIN)); // Check for win result
        assertThat(m1.getBalance().getValue(), is(120)); // Verify balance after win
    }

    @Test
    public void playGameLosesWhenDiceAreNotFourAlike() {
        m1.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {2, 1, 1, 1}; // Mocking dice values

        when(m1.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m1.setWager(wager); // **Set wager before playing**
        m1.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m1.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.LOSS)); // Check for loss result
        assertThat(m1.getBalance().getValue(), is(80)); // Verify balance after loss
    }
}

