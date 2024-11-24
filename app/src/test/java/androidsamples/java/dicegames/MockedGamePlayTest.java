package androidsamples.java.dicegames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class MockedGamePlayTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;
    @Mock
    private Application mockApplication;
    @Mock
    private SharedPreferences mockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;
    @Mock
    Die walletDie;

    private AutoCloseable closeable;
    private GamesViewModel m2;
//    @InjectMocks
//    // InjectMocks annotation will automatically inject the mocks into the GamesViewModel
//    private GamesViewModel m2;
//    @Before
//    public void setUp() {
////        MockitoAnnotations.initMocks(this);
//        // Mock SharedPreferences correctly
//        MockitoAnnotations.openMocks(this);
//        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
//                .thenReturn(mockSharedPreferences);
//        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(0); // Default balance
//        when(mockSharedPreferences.getInt("dieValue", 0)).thenReturn(0);
//        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
//        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor); // Support chaining
////        when(mockEditor.apply()).thenAnswer(invocation -> null); // Mock apply() behavior
//        doNothing().when(mockEditor).apply(); // This will do nothing when apply() is called
//
//
//        // Initialize the GamesViewModel with the mocked Application
//        m2 = new GamesViewModel(mockApplication);
//        m2.die = walletDie;
//    }
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
    m2 = spy(new GamesViewModel(mockApplication));
    // Inject the mocked Die into the ViewModel

    m2.die = walletDie;

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
    public void fourAlikeWinsWhenAllDiceReturn1() {
        GamesViewModel spyViewModel = spy(m2);
        when(m2.diceValues()).thenReturn(new int[]{1, 1, 1, 1});  // Mocking the dice values

        m2.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m2.setBalance(100);  // Set balance for the game
        m2.setWager(5);  // Set wager for the game

        GameResult result = m2.play();
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m2.getBalance().getValue(), is(120));  // Check if the balance is updated after winning
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m2.diceValues()).thenReturn(new int[]{2, 1, 1, 1});  // Mocking the dice values

        m2.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m2.setBalance(100);  // Set balance for the game
        m2.setWager(5);  // Set wager for the game

        GameResult result = m2.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.LOSS));  // Check if the result is LOSS
        assertThat(m2.getBalance().getValue(), is(80));  // Check if the balance is updated after losing
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m2.diceValues()).thenReturn(new int[]{3, 3, 1, 4});  // Mocking the dice values

        m2.setGameType(GameType.TWO_ALIKE);  // Set game type to TWO_ALIKE
        m2.setBalance(100);  // Set balance for the game
        m2.setWager(5);  // Set wager for the game

        GameResult result = m2.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m2.getBalance().getValue(), is(110));  // Check if the balance is updated after winning
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m2.diceValues()).thenReturn(new int[]{1, 1, 1, 2});  // Mocking the dice values

        m2.setGameType(GameType.THREE_ALIKE);  // Set game type to THREE_ALIKE
        m2.setBalance(100);  // Set balance for the game
        m2.setWager(5);  // Set wager for the game

        GameResult result = m2.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m2.getBalance().getValue(), is(115));  // Check if the balance is updated after winning
    }
    @Test
    public void testPlayGameWin() {
        // Set the balance and mock die rolls for "3 alike" game
        m2.setBalance(10);
        m2.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
//        when(walletDie.value()).thenReturn(3, 3, 3, 2);  // 3 alike
//        Mockito.doReturn(new int[]{3, 3, 3, 2}).when(m1.diceValues());
        when(m2.diceValues()).thenReturn(new int[]{3, 3, 3, 2});

        m2.setWager(3);  // **Set wager before playing**
        GameResult result = m2.play();  // **Call play() method instead of playGame()**

        // Balance should increase by 3 * 3 = 9 coins
        assertThat(m2.getBalance().getValue(), is(19));  // **Using getter method**
        assertThat(result, is(GameResult.WIN));  // **Check for win result**
    }

    @Test
    public void testPlayGameLose() {
        // Set the balance and mock die rolls for "3 alike" game
        m2.setBalance(10);
        m2.setGameType(GameType.THREE_ALIKE);  // **Set the game type**
        when(walletDie.value()).thenReturn(2, 2, 3, 4);  // Not 3 alike

        m2.setWager(3);  // **Set wager before playing**
        GameResult result = m2.play();

        // Balance should decrease by 3 * 3 = 9 coins
        assertThat(m2.getBalance().getValue(), is(1));  // **Using getter method**
        assertThat(result, is(GameResult.LOSS));  // **Check for loss result**
    }

    @Test
    public void canPlaceWagerReturnsTrueWhenWagerIsValid() {
        m2.setBalance(10); // Set initial balance
        m2.setWager(5); // **Set wager before validation**
        m2.setGameType(GameType.TWO_ALIKE);
        assertThat(m2.isValidWager(), is(true)); // Adjusted to validate wager correctly
    }

    @Test
    public void canPlaceWagerReturnsFalseWhenWagerIsTooHigh() {
        m2.setBalance(10); // Set initial balance
        m2.setWager(5); // **Set wager before validation**
        m2.setGameType(GameType.THREE_ALIKE); // **Set the game type**
        assertThat(m2.isValidWager(), is(false)); // Ensure method checks correctly
    }

    @Test
    public void playGameWinsWhenDiceAreFourAlike() {
        m2.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {1, 1, 1, 1}; // Mocking dice values

        when(m2.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m2.setWager(wager); // **Set wager before playing**
        m2.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m2.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.WIN)); // Check for win result
        assertThat(m2.getBalance().getValue(), is(120)); // Verify balance after win
    }

    @Test
    public void playGameLosesWhenDiceAreNotFourAlike() {
        m2.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {2, 1, 1, 1}; // Mocking dice values

        when(m2.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        m2.setWager(wager); // **Set wager before playing**
        m2.setGameType(GameType.FOUR_ALIKE); // **Set the game type**
        GameResult result = m2.play(); // Call play method instead of playGame
        assertThat(result, is(GameResult.LOSS)); // Check for loss result
        assertThat(m2.getBalance().getValue(), is(80)); // Verify balance after loss
    }
}
