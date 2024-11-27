package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WalletInstrumentedTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private GamesViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private Die mockDie;

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }
    @Before
    public void setUp() {
        // Clear SharedPreferences before each test
        sharedPreferences = activityRule.getActivity().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        // Set initial balance in SharedPreferences
        sharedPreferences.edit().putInt("balance", 100).apply();

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(activityRule.getActivity()).get(GamesViewModel.class);

        // Mock the Die
        mockDie = mock(Die.class);
        viewModel.die = mockDie;  // Inject the mocked Die into the ViewModel

        // Load the WalletFragment
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new WalletFragment())
                .commit();
    }

    @Test
    public void testRollWalletDieUpdatesBalanceWhenDieRollIsSix() {
        // Mock the die to return 6
        when(mockDie.value()).thenReturn(6);

        // Perform click on the die button to roll the dice
        onView(withId(R.id.btn_die)).perform(click());

        // Add a short delay to allow the UI to update
        SystemClock.sleep(500);

        // Check that the balance has been updated correctly (increase by 5)
        onView(withId(R.id.txt_balance)).check(matches(withText("105")));  // 100 + 5
    }

    @Test
    public void testRollWalletDieNoChangeInBalanceWhenDieNotSix() {
        // Mock the die to return a number other than 6
        when(mockDie.value()).thenReturn(3);

        // Perform click on the die button to roll the dice
        onView(withId(R.id.btn_die)).perform(click());

        // Add a short delay to allow the UI to update
        SystemClock.sleep(500);

        // Check that the balance remains the same (100 in this case)
        onView(withId(R.id.txt_balance)).check(matches(withText("100")));
    }

    @Test
    public void testMultipleDieRollsUpdatesBalance() {
        // Mock the die to return 6 twice in a row
        when(mockDie.value()).thenReturn(6).thenReturn(6);

        // Perform click on the die button twice to roll the dice
        onView(withId(R.id.btn_die)).perform(click());
        onView(withId(R.id.btn_die)).perform(click());

        // Add a short delay to allow the UI to update
        SystemClock.sleep(500);

        // Check that the balance has been updated correctly (should be 110 after two rolls of 6)
        onView(withId(R.id.txt_balance)).check(matches(withText("110")));  // 100 + (5 * 2)
    }
    @Test
    public void accessibilityTestBtnGames() {
        onView(withId(R.id.btn_games))
                .check(matches(isDisplayed()));
    }
    @Test
    public void accessibilityTestBalanceLabel() {
        onView(withId(R.id.tvCoins))
                .check(matches(isDisplayed()));
    }
}
