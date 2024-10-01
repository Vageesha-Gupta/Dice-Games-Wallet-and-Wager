package androidsamples.java.dicegames;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockedGamePlayTest {
    @Spy
    private GamesViewModel m = new GamesViewModel();

    @Test
    public void fourAlikeWinsWhenAllDiceReturn1() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 1});  // Mocking the dice values

        m.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m.setBalance(100);  // Set balance for the game
        m.setWager(5);  // Set wager for the game

        GameResult result = m.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m.getBalance(), is(105));  // Check if the balance is updated after winning
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m.diceValues()).thenReturn(new int[]{2, 1, 1, 1});  // Mocking the dice values

        m.setGameType(GameType.FOUR_ALIKE);  // Set game type to FOUR_ALIKE
        m.setBalance(100);  // Set balance for the game
        m.setWager(5);  // Set wager for the game

        GameResult result = m.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.LOSS));  // Check if the result is LOSS
        assertThat(m.getBalance(), is(95));  // Check if the balance is updated after losing
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m.diceValues()).thenReturn(new int[]{3, 3, 1, 4});  // Mocking the dice values

        m.setGameType(GameType.TWO_ALIKE);  // Set game type to TWO_ALIKE
        m.setBalance(100);  // Set balance for the game
        m.setWager(5);  // Set wager for the game

        GameResult result = m.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m.getBalance(), is(105));  // Check if the balance is updated after winning
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 2});  // Mocking the dice values

        m.setGameType(GameType.THREE_ALIKE);  // Set game type to THREE_ALIKE
        m.setBalance(100);  // Set balance for the game
        m.setWager(5);  // Set wager for the game

        GameResult result = m.play();  // Call play() instead of playGame()
        assertThat(result, is(GameResult.WIN));  // Check if the result is WIN
        assertThat(m.getBalance(), is(105));  // Check if the balance is updated after winning
    }
}
