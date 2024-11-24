package androidsamples.java.dicegames;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class GamePlayTest {
    @Mock
    private Application mockApplication;

    @Mock
    private SharedPreferences mockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;

//    private GamesViewModel m1; // No longer using @InjectMocks
    private GamesViewModel m;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock SharedPreferences behavior
        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(0); // Default balance
        when(mockSharedPreferences.getInt("dieValue", 0)).thenReturn(0);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor); // Support chaining
//        when(mockEditor.apply()).thenAnswer(invocation -> null); // Mock apply() behavior
        doNothing().when(mockEditor).apply(); // This will do nothing when apply() is called


        // Initialize the GamesViewModel with the mocked Application
        m = new GamesViewModel(mockApplication);
    }

    @Test
    public void fourAlikeWinsWhenAllDiceReturn1() {
        // Use spy to override diceValues
        GamesViewModel spyViewModel = spy(m);
        doReturn(new int[]{1, 1, 1, 1}).when(spyViewModel).diceValues();
        m.setBalance(100);
        spyViewModel.setGameType(GameType.FOUR_ALIKE);
        spyViewModel.setWager(5);

        GameResult result = spyViewModel.play();
        assertThat(result, is(GameResult.WIN));
        assertThat(spyViewModel.getBalance().getValue(), is(120));  // 100 + 4 * wager (5)
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        // Use spy to override diceValues
        GamesViewModel spyViewModel = spy(m);
        doReturn(new int[]{2, 1, 1, 1}).when(spyViewModel).diceValues();
        m.setBalance(100);
        spyViewModel.setGameType(GameType.FOUR_ALIKE);
        spyViewModel.setWager(5);

        GameResult result = spyViewModel.play();
        assertThat(result, is(GameResult.LOSS));
        assertThat(spyViewModel.getBalance().getValue(), is(80));  // 100 - 4 * wager (5)
    }
    @Test
    public void wager20Balance20GameType2Invalid() {
        m.setWager(20);
        m.setBalance(20);
        m.setGameType(GameType.TWO_ALIKE);
        System.out.println("Wager: " + m.getWager());
        System.out.println("Balance: " + m.getBalance().getValue());
        System.out.println("GameType: " + m.getGameType());

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

//package androidsamples.java.dicegames;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class GamePlayTest {
//    @Mock
//    private Application mockApplication;
//
//    @Mock
//    private SharedPreferences mockSharedPreferences;
//
//    @Mock
//    private SharedPreferences.Editor mockEditor;
//
//    @InjectMocks
//    private GamesViewModel m1; // Use InjectMocks to automatically inject mocks
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        // Mock SharedPreferences
//        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
//                .thenReturn(mockSharedPreferences);
//        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Default balance
//        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
//        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor); // Mock chaining
//
//        // Inject mocks into GamesViewModel
//        m1 = new GamesViewModel(mockApplication);
//    }
//
//    @Test
//    public void fourAlikeWinsWhenAllDiceReturn1() {
//        // Mock diceValues method to return specific values
//        GamesViewModel spyViewModel = spy(m1);
//        doReturn(new int[]{1, 1, 1, 1}).when(spyViewModel).diceValues();
//
//        spyViewModel.setGameType(GameType.FOUR_ALIKE);
//        spyViewModel.setWager(5);
//
//        GameResult result = spyViewModel.play();
//        assertThat(result, is(GameResult.WIN));
//        assertThat(spyViewModel.getBalance().getValue(), is(120));  // 100 + 4 * wager (5)
//    }
//
//    @Test
//    public void fourAlikeLossWhenD1Different() {
//        // Mock diceValues method to return specific values
//        GamesViewModel spyViewModel = spy(m1);
//        doReturn(new int[]{2, 1, 1, 1}).when(spyViewModel).diceValues();
//
//        spyViewModel.setGameType(GameType.FOUR_ALIKE);
//        spyViewModel.setWager(5);
//
//        GameResult result = spyViewModel.play();
//        assertThat(result, is(GameResult.LOSS));
//        assertThat(spyViewModel.getBalance().getValue(), is(80));  // 100 - 4 * wager (5)
//    }
//
//    @Test
//    public void twoAlikeWinsWhenD1D2Same() {
//        // Mock diceValues method to return specific values
//        GamesViewModel spyViewModel = spy(m1);
//        doReturn(new int[]{3, 3, 1, 4}).when(spyViewModel).diceValues();
//
//        spyViewModel.setGameType(GameType.TWO_ALIKE);
//        spyViewModel.setWager(5);
//
//        GameResult result = spyViewModel.play();
//        assertThat(result, is(GameResult.WIN));
//        assertThat(spyViewModel.getBalance().getValue(), is(110));  // 100 + 2 * wager (5)
//    }
//
//    @Test
//    public void threeAlikeWinsWhenD1D2D3Same() {
//        // Mock diceValues method to return specific values
//        GamesViewModel spyViewModel = spy(m1);
//        doReturn(new int[]{1, 1, 1, 2}).when(spyViewModel).diceValues();
//
//        spyViewModel.setGameType(GameType.THREE_ALIKE);
//        spyViewModel.setWager(5);
//
//        GameResult result = spyViewModel.play();
//        assertThat(result, is(GameResult.WIN));
//        assertThat(spyViewModel.getBalance().getValue(), is(115));  // 100 + 3 * wager (5)
//    }
//}
//
////package androidsamples.java.dicegames;
////
////import static org.hamcrest.MatcherAssert.assertThat;
////import static org.hamcrest.core.Is.is;
////import static org.junit.Assert.assertThrows;
////import static org.mockito.ArgumentMatchers.anyInt;
////import static org.mockito.ArgumentMatchers.anyString;
////import static org.mockito.Mockito.when;
////
////import android.app.Application;
////import android.content.Context;
////import android.content.SharedPreferences;
////
////import org.junit.Before;
////import org.junit.Test;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.MockitoAnnotations;
////
////public class GamePlayTest {
////    @Mock
////    private Application mockApplication;
////    @Mock
////    private SharedPreferences mockSharedPreferences;
////    @Mock
////    private SharedPreferences.Editor mockEditor;
////
////    @InjectMocks
////    // InjectMocks annotation will automatically inject the mocks into the GamesViewModel
////    private GamesViewModel m;
////    @Before
////    public void setUp() {
////        MockitoAnnotations.initMocks(this);
////
////        // Mock SharedPreferences behavior
////        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
////                .thenReturn(mockSharedPreferences);
////        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);
////        //change
////        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
////        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor); // Mock chaining
////        //change
////
////
////        // Manually create ViewModel with mocked application
////        m = new GamesViewModel(mockApplication);
//////        MockitoAnnotations.initMocks(this);
//////        // Mock SharedPreferences correctly
//////        when(mockApplication.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE))
//////                .thenReturn(mockSharedPreferences);
//////        // Mock SharedPreferences.getInt to return a value (e.g., 5)
//////        when(mockSharedPreferences.getInt("balance", 0)).thenReturn(100);  // Assume default balance is 100
//////        m = new GamesViewModel(mockApplication);
////    }
////
////    @Test
////    public void wager20Balance20GameType2Invalid() {
////        m.setWager(20);
////        m.setBalance(20);
////        m.setGameType(GameType.TWO_ALIKE);
////        assertThat(m.isValidWager(), is(false));
////    }
////
////    @Test
////    public void wager20Balance40GameType2Valid() {
////        m.setWager(20);
////        m.setBalance(40);
////        m.setGameType(GameType.TWO_ALIKE);
////        assertThat(m.isValidWager(), is(true));
////    }
////
////    @Test
////    public void wager20Balance40GameType3Invalid() {
////        m.setWager(20);
////        m.setBalance(40);
////        m.setGameType(GameType.THREE_ALIKE);
////        assertThat(m.isValidWager(), is(false));
////    }
////
////    @Test
////    public void wager20Balance60GameType3Valid() {
////        m.setWager(20);
////        m.setBalance(60);
////        m.setGameType(GameType.THREE_ALIKE);
////        assertThat(m.isValidWager(), is(true));
////    }
////
////    @Test
////    public void wager20Balance60GameType4Invalid() {
////        m.setWager(20);
////        m.setBalance(60);
////        m.setGameType(GameType.FOUR_ALIKE);
////        assertThat(m.isValidWager(), is(false));
////    }
////
////    @Test
////    public void wager20Balance40GameType4Valid() {
////        m.setWager(20);
////        m.setBalance(80);
////        m.setGameType(GameType.FOUR_ALIKE);
////        assertThat(m.isValidWager(), is(true));
////    }
////
////    @Test
////    public void wager0isInvalid() {
////        m.setWager(0);
////        assertThat(m.isValidWager(), is(false));
////    }
////
////    @Test
////    public void callingPlayWithoutSettingWagerThrowsException() {
////        m.setGameType(GameType.TWO_ALIKE);
////        IllegalStateException thrown = assertThrows(IllegalStateException.class, m::play);
////        assertThat(thrown.getMessage(), is("Wager not set, can't play!"));
////    }
////
////    @Test
////    public void callingPlayWithoutSettingGameTypeThrowsException() {
////        m.setWager(20);
////        IllegalStateException thrown = assertThrows(IllegalStateException.class, m::play);
////        assertThat(thrown.getMessage(), is("Game Type not set, can't play!"));
////    }
////
////}