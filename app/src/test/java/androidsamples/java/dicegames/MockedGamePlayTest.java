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
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE.ordinal());
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.playGame(m.getWager(),m.getGameType()), is(GameResult.WIN));
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m.diceValues()).thenReturn(new int[]{2, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE.ordinal());
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.playGame(m.getWager(),m.getGameType()), is(GameResult.LOSS));
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m.diceValues()).thenReturn(new int[]{3, 3, 1, 4});

        m.setGameType(GameType.TWO_ALIKE.ordinal());
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.playGame(m.getWager(),m.getGameType()), is(GameResult.WIN));
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 2});

        m.setGameType(GameType.THREE_ALIKE.ordinal());
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.playGame(m.getWager(),m.getGameType()), is(GameResult.WIN));
    }
}