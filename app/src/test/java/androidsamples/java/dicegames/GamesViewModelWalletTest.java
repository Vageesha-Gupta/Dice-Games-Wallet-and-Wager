package androidsamples.java.dicegames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GamesViewModelWalletTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;


    @Spy
    private Die walletDie;
    @InjectMocks
    private GamesViewModel m = new GamesViewModel();
    private AutoCloseable closeable;

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
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(WIN_VALUE);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(1);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }

    @Test
    public void testCanPlaceWager() {
        m.setBalance(100);
        assertThat(m.canPlaceWager(5, 2), is(true));

        assertThat(m.canPlaceWager(3, 3), is(false));

        assertThat(m.canPlaceWager(2, 4), is(true));

    }

    @Test
    public void testPlayGameWin() {
        // Set the balance and mock die rolls for "3 alike" game
        m.setBalance(10);
        when(walletDie.value()).thenReturn(3, 3, 3, 2);  // 3 alike

        m.playGame(3, 3);  // Bet 3 coins on "3 alike"

        // Balance should increase by 3 * 3 = 9 coins
        assertThat(m.getWalletBalance(), is(19));
    }

    @Test
    public void testPlayGameLose() {
        // Set the balance and mock die rolls for "3 alike" game
        m.setBalance(10);
        when(walletDie.value()).thenReturn(2, 2, 3, 4);  // Not 3 alike

        m.playGame(3, 3);  // Bet 3 coins on "3 alike"

        // Balance should decrease by 3 * 3 = 9 coins
        assertThat(m.getWalletBalance(), is(1));
    }

    @Test
    public void canPlaceWagerReturnsTrueWhenWagerIsValid() {
        m.setBalance(10); // Set initial balance
        int wager = 5;
        assertThat(m.canPlaceWager(wager, 2), is(true)); // Betting on 2 alike game
    }

    @Test
    public void canPlaceWagerReturnsFalseWhenWagerIsTooHigh() {
        m.setBalance(10); // Set initial balance
        int wager = 5;
        assertThat(m.canPlaceWager(wager, 3), is(false)); // Betting on 3 alike game
    }

    @Test
    public void playGameWinsWhenDiceAreFourAlike() {
        m.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {1, 1, 1, 1}; // Mocking dice values

        when(m.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        // Directly check the game logic without setGameType
        boolean isWin = m.playGame(wager, 4); // Assuming playGame takes wager and type directly
        assertThat(isWin, is(true)); // Assuming playGame returns a boolean indicating win/loss
    }

    @Test
    public void playGameLosesWhenDiceAreNotFourAlike() {
        m.setBalance(100); // Initial balance
        int wager = 5; // Set wager
        int[] diceRolls = {2, 1, 1, 1}; // Mocking dice values

        when(m.diceValues()).thenReturn(diceRolls); // Assuming you have a method to get dice values

        // Directly check the game logic without setGameType
        boolean isWin = m.playGame(wager, 4); // Assuming playGame takes wager and type directly
        assertThat(isWin, is(false)); // Assuming playGame returns a boolean indicating win/loss
    }
}
