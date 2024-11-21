package androidsamples.java.dicegames;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GamePlayTest {
    @Mock
    private Application mockApplication;
    @Mock
    private SharedPreferences mockSharedPreferences;

    @InjectMocks
    // InjectMocks annotation will automatically inject the mocks into the GamesViewModel
    private GamesViewModel m;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock SharedPreferences behavior
        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);
        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);

        // Manually create ViewModel with mocked application
        m = new GamesViewModel(mockApplication);
//        MockitoAnnotations.initMocks(this);
//        // Mock SharedPreferences correctly
//        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
//                .thenReturn(mockSharedPreferences);
//        // Mock SharedPreferences.getInt to return a value (e.g., 5)
//        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Assume default balance is 100
//        m = new GamesViewModel(mockApplication);
    }

    @Test
    public void wager20Balance20GameType2Invalid() {
        m.setWager(20);
        m.setBalance(20);
        m.setGameType(GameType.TWO_ALIKE);
        assertThat(m.isValidWager(), is(false));
    }

    @Test
    public void wager20Balance40GameType2Valid() {
        m.setWager(20);
        m.setBalance(40);
        m.setGameType(GameType.TWO_ALIKE);
        assertThat(m.isValidWager(), is(true));
    }

    @Test
    public void wager20Balance40GameType3Invalid() {
        m.setWager(20);
        m.setBalance(40);
        m.setGameType(GameType.THREE_ALIKE);
        assertThat(m.isValidWager(), is(false));
    }

    @Test
    public void wager20Balance60GameType3Valid() {
        m.setWager(20);
        m.setBalance(60);
        m.setGameType(GameType.THREE_ALIKE);
        assertThat(m.isValidWager(), is(true));
    }

    @Test
    public void wager20Balance60GameType4Invalid() {
        m.setWager(20);
        m.setBalance(60);
        m.setGameType(GameType.FOUR_ALIKE);
        assertThat(m.isValidWager(), is(false));
    }

    @Test
    public void wager20Balance40GameType4Valid() {
        m.setWager(20);
        m.setBalance(80);
        m.setGameType(GameType.FOUR_ALIKE);
        assertThat(m.isValidWager(), is(true));
    }

    @Test
    public void wager0isInvalid() {
        m.setWager(0);
        assertThat(m.isValidWager(), is(false));
    }

    @Test
    public void callingPlayWithoutSettingWagerThrowsException() {
        m.setGameType(GameType.TWO_ALIKE);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, m::play);
        assertThat(thrown.getMessage(), is("Wager not set, can't play!"));
    }

    @Test
    public void callingPlayWithoutSettingGameTypeThrowsException() {
        m.setWager(20);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, m::play);
        assertThat(thrown.getMessage(), is("Game Type not set, can't play!"));
    }

}