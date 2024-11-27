package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Mock
    private SharedPreferences mockSharedPrefs;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Setup mock SharedPreferences
        when(mockSharedPrefs.getInt("balance", 0)).thenReturn(100); // Default balance
        when(mockSharedPrefs.edit()).thenReturn(mockEditor);
        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor);
    }



    @Test
    public void testDieRoll() {
        onView(withId(R.id.btn_die)).perform(click());
        onView(withId(R.id.btn_die))
                .check(matches(ViewMatchers.isEnabled())); // Check button still functional
    }

    @Test
    public void testNavigationToGames() {
        onView(withId(R.id.btnToGames)).perform(click());
        onView(withId(R.id.rgGameType)).check(matches(isDisplayed()));
    }

    @Test
    public void testGameTypeSelection() {
        onView(withId(R.id.btnToGames)).perform(click());
        onView(withId(R.id.rbTwoAlike)).perform(click());
        onView(withId(R.id.rbTwoAlike)).check(matches(isChecked()));

        onView(withId(R.id.rbThreeAlike)).perform(click());
        onView(withId(R.id.rbThreeAlike)).check(matches(isChecked()));

        onView(withId(R.id.rbFourAlike)).perform(click());
        onView(withId(R.id.rbFourAlike)).check(matches(isChecked()));
    }

    @Test
    public void testValidWagerInput() {
        onView(withId(R.id.btnToGames)).perform(click());
        onView(withId(R.id.etWager)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.rbTwoAlike)).perform(click());
        onView(withId(R.id.btnGo)).perform(click());

        // Verify game starts (check dice or result visibility)
        onView(withId(R.id.dice1)).check(matches(isDisplayed()));
    }


    @Test
    public void testDiceDisplay() {
        onView(withId(R.id.btnToGames)).perform(click());
        onView(withId(R.id.etWager)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.rbTwoAlike)).perform(click());
        onView(withId(R.id.btnGo)).perform(click());

        // Check all dice display values
        onView(withId(R.id.dice1)).check(matches(isDisplayed()));
        onView(withId(R.id.dice2)).check(matches(isDisplayed()));
        onView(withId(R.id.dice3)).check(matches(isDisplayed()));
        onView(withId(R.id.dice4)).check(matches(isDisplayed()));
    }

    @Test
    public void testInfoButtonNavigation() {
        onView(withId(R.id.btnToGames)).perform(click());
        onView(withId(R.id.btnInfo)).perform(click());

    }

//    @Test
//    public void testBalanceUpdateAfterGame() {
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Play a game
//        onView(withId(R.id.etWager)).perform(typeText("10"), closeSoftKeyboard());
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Verify balance updated
//        onView(withId(R.id.tvCoins)).check(matches(not(withText("100")))); // Initial balance is mocked
//    }
//}

//package androidsamples.java.dicegames;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.not;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.core.app.ApplicationProvider;
//import androidx.test.espresso.accessibility.AccessibilityChecks;
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.assertion.ViewAssertions;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.filters.LargeTest;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import com.google.androidgamesdk.GameActivity;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public  class ExampleInstrumentedTest {
//    @Rule
//    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
//
//    @Mock
//    private SharedPreferences mockSharedPrefs;
//    @Mock
//    private SharedPreferences.Editor mockEditor;
//    private GamesViewModel viewModel;
//
//
//
////    @Rule
////    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
//
//    @BeforeClass
//    public static void enableAccessibilityChecks() {
//        AccessibilityChecks.enable().setRunChecksFromRootView(true);
//    }
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        Context context = ApplicationProvider.getApplicationContext();
//
//        // Setup SharedPreferences mock
//        when(mockSharedPrefs.getInt(eq("balance"), anyInt())).thenReturn(100);
//        when(mockSharedPrefs.edit()).thenReturn(mockEditor);
//        when(mockEditor.putInt(anyString(), anyInt())).thenReturn(mockEditor);
//
//        viewModel = new GamesViewModel(ApplicationProvider.getApplicationContext());
//    }
//
//
//    @Test
//    public void dummyAccessibilityTest() {
//        onView(withId(R.id.btn_die)).perform(click());
//    }
//
//
//    // Wallet Fragment Tests
//    @Test
//    public void testWalletInitialBalance() {
//        // Check if initial balance is displayed correctly
//        onView(withId(R.id.tvCoinsValue))
//                .check(matches(withText("0")));
//    }
//
//    @Test
//    public void testDieRoll() {
//        // Click die button
//        onView(withId(R.id.btn_die)).perform(click());
//
//        // Verify die value is displayed
//        onView(withId(R.id.btn_die))
//                .check(matches(withText(containsString(""))));
//    }
//
//    @Test
//    public void testNavigationToGames() {
//        // Click on Games button
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Verify we're on Games fragment by checking for its unique element
//        onView(withId(R.id.rgGameType)).check(matches(isDisplayed()));
//    }
//
//    // Games Fragment Tests
//    @Test
//    public void testGameTypeSelection() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Select Two Alike
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//        onView(withId(R.id.rbTwoAlike)).check(matches(isChecked()));
//
//        // Select Three Alike
//        onView(withId(R.id.rbThreeAlike)).perform(click());
//        onView(withId(R.id.rbThreeAlike)).check(matches(isChecked()));
//
//        // Select Four Alike
//        onView(withId(R.id.rbFourAlike)).perform(click());
//        onView(withId(R.id.rbFourAlike)).check(matches(isChecked()));
//    }
//
//    @Test
//    public void testValidWagerInput() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Input valid wager
//        onView(withId(R.id.etWager)).perform(typeText("10"));
//
//        // Select game type
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//
//        // Click Go button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Verify game starts (dice values should be displayed)
//        onView(withId(R.id.dice1)).check(matches(withText(containsString(""))));
//    }
//
//    @Test
//    public void testInvalidWagerInput() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Input invalid wager (more than balance)
//        onView(withId(R.id.etWager)).perform(typeText("1000"));
//
//        // Select game type
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//
//        // Click Go button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Verify error message is shown
//        onView(withText("Invalid wager")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testDiceDisplay() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Input valid wager
//        onView(withId(R.id.etWager)).perform(typeText("10"));
//
//        // Select game type
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//
//        // Click Go button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Verify all dice show values
//        onView(withId(R.id.dice1)).check(matches(withText(containsString(""))));
//        onView(withId(R.id.dice2)).check(matches(withText(containsString(""))));
//        onView(withId(R.id.dice3)).check(matches(withText(containsString(""))));
//        onView(withId(R.id.dice4)).check(matches(withText(containsString(""))));
//    }
//
//    @Test
//    public void testInfoButtonNavigation() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Click Info button
//        onView(withId(R.id.btnInfo)).perform(click());
//
//    }
//
//    @Test
//    public void testConsecutiveSixesBonus() {
//        // Test the double sixes bonus feature
//        onView(withId(R.id.btn_die)).perform(click());
//        // Need to verify balance changes appropriately when consecutive sixes are rolled
//        // This might need additional setup with mocked die values
//    }
//
//    @Test
//    public void testBalanceUpdateAfterGame() {
//        // Navigate to Games fragment
//        onView(withId(R.id.btnToGames)).perform(click());
//
//        // Get initial balance
//        String initialBalance = viewModel.getBalance().getValue().toString();
//
//        // Play a game
//        onView(withId(R.id.etWager)).perform(typeText("10"));
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Verify balance has changed
//        onView(withId(R.id.tvCoins)).check(matches(not(withText(initialBalance))));
//    }
//}