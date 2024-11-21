package androidsamples.java.dicegames;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class MockedGamePlayTest {
    @Mock
    private Application mockApplication;
    @Mock
    private SharedPreferences mockSharedPreferences;

    @Spy
    private GamesViewModel m1;
//    @InjectMocks
//    // InjectMocks annotation will automatically inject the mocks into the GamesViewModel
//    private GamesViewModel m1;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Mock SharedPreferences correctly
        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);
        // Mock SharedPreferences.getInt to return a value (e.g., 5)
        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Assume default balance is 100

        m1 = new GamesViewModel(mockApplication);
    }
    @Test
    public void fourAlikeWinsWhenAllDiceReturn1() {
        when(m1.diceValues()).thenReturn(new int[]{1, 1, 1, 1});  // Mocking the dice values

        m1.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m1.setBalance(100);  // Set balance for the game
        m1.setWager(5);  // Set wager for the game

        GameResult result = m1.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m1.getBalance(), is(105));  // Check if the balance is updated after winning
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m1.diceValues()).thenReturn(new int[]{2, 1, 1, 1});  // Mocking the dice values

        m1.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m1.setBalance(100);  // Set balance for the game
        m1.setWager(5);  // Set wager for the game

        GameResult result = m1.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.LOSS));  // Check if the result is LOSS
        assertThat(m1.getBalance(), is(95));  // Check if the balance is updated after losing
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m1.diceValues()).thenReturn(new int[]{3, 3, 1, 4});  // Mocking the dice values

        m1.setGameType(GameType.TWO_ALIKE);  // Set game type to TWO_ALIKE
        m1.setBalance(100);  // Set balance for the game
        m1.setWager(5);  // Set wager for the game

        GameResult result = m1.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m1.getBalance(), is(105));  // Check if the balance is updated after winning
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m1.diceValues()).thenReturn(new int[]{1, 1, 1, 2});  // Mocking the dice values

        m1.setGameType(GameType.THREE_ALIKE);  // Set game type to THREE_ALIKE
        m1.setBalance(100);  // Set balance for the game
        m1.setWager(5);  // Set wager for the game

        GameResult result = m1.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m1.getBalance(), is(105));  // Check if the balance is updated after winning
    }
}
