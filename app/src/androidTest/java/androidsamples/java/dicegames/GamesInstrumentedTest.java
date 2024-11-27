
package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;
// Add this import

import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GamesInstrumentedTest {

//    @Rule
//    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
//
//    private GamesViewModel viewModel;
//    private SharedPreferences sharedPreferences;
//    private List<Die> mockedDieList;
//
//    @BeforeClass
//    public static void enableAccessibilityChecks() {
//        AccessibilityChecks.enable().setRunChecksFromRootView(true);
//    }
//    @Before
//    public void setUp() {
//        // Clear SharedPreferences before each test
//        sharedPreferences = activityRule.getActivity().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().apply();
//
//        // Set initial balance in SharedPreferences
//        sharedPreferences.edit().putInt("balance", 100).apply();
//
//        // Initialize the ViewModel
//        viewModel = new ViewModelProvider(activityRule.getActivity()).get(GamesViewModel.class);
//
//        // Load the GamesFragment
//        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.nav_host_fragment, new GamesFragment())
//                .commit();
//    }
//
//    private void setMockedDice(int[] values) {
//        Die[] mockedDieList = new Die[4];
//
//
//        viewModel.diceValuesLiveData.setValue(values);
//
//
//        viewModel.dice = mockedDieList;
//    }
//
//
//    @Test
//    public void testTwoAlikeLossUpdatesBalance() {
//        setMockedDice(new int[]{2, 6, 3, 4});
//
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//
//        onView(withId(R.id.etWager))
//                .perform(typeText("5"))
//                .perform(closeSoftKeyboard());
//
//        // Perform click on the "Go" button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Check if the balance has been updated correctly
//        onView(withId(R.id.txt_balance)).check(matches(withText("90"))); // 100 - (2 * 5)
//    }
//    @Test
//    public void testTwoAlikeWinUpdatesBalance() {
//        setMockedDice(new int[]{2, 1, 6, 2});
//
//        onView(withId(R.id.rbTwoAlike)).perform(click());
//
//        onView(withId(R.id.etWager)).perform(replaceText("5"));
//
//        onView(withId(R.id.btnGo)).perform(click());
//
//        onView(withId(R.id.txt_balance)).check(matches(withText("110"))); // 100 + (2 * 5)
//    }
//    @Test
//    public void testThreeAlikeWinUpdatesBalance() {
//        setMockedDice(new int[]{4, 4, 3, 4});
//
//        // Select "Three Alike" game type by clicking the corresponding RadioButton
//        onView(withId(R.id.rbThreeAlike)).perform(click());
//
//        // Perform wager entry on EditText
//        onView(withId(R.id.etWager))
//                .perform(typeText("10"))
//                .perform(closeSoftKeyboard());
//
//        // Perform click on the "Go" button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Check if the balance has been updated correctly
//        onView(withId(R.id.txt_balance)).check(matches(withText("130"))); // 100 + (3 * 10)
//    }
//
//    @Test
//    public void testThreeAlikeLossUpdatesBalance() {
//        setMockedDice(new int[]{1, 2, 3, 4});
//
//        // Select "Three Alike" game type by clicking the corresponding RadioButton
//        onView(withId(R.id.rbThreeAlike)).perform(click());
//
//        // Perform wager entry on EditText
//        onView(withId(R.id.etWager))
//                .perform(typeText("10"))
//                .perform(closeSoftKeyboard());
//
//        // Perform click on the "Go" button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Check if the balance has been updated correctly
//        onView(withId(R.id.txt_balance)).check(matches(withText("70"))); // 100 - (3 * 10)
//    }
//
//    @Test
//    public void testFourAlikeLossUpdatesBalance() {
//        setMockedDice(new int[]{4, 4, 3, 4});
//
//        // Select "Four Alike" game type by clicking the corresponding RadioButton
//        onView(withId(R.id.rbFourAlike)).perform(click());
//
//        // Perform wager entry on EditText
//        onView(withId(R.id.etWager))
//                .perform(typeText("10"))
//                .perform(closeSoftKeyboard());
//
//        // Perform click on the "Go" button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Check if the balance has been updated correctly
//        onView(withId(R.id.txt_balance)).check(matches(withText("60"))); // 100 - (4 * 10)
//    }
//
//    @Test
//    public void testFourAlikeWinUpdatesBalance() {
//        setMockedDice(new int[]{2, 2, 2, 2});
//
//        // Select "Four Alike" game type by clicking the corresponding RadioButton
//        onView(withId(R.id.rbFourAlike)).perform(click());
//
//        // Perform wager entry on EditText
//        onView(withId(R.id.etWager))
//                .perform(typeText("10"))
//                .perform(closeSoftKeyboard());
//
//        // Perform click on the "Go" button
//        onView(withId(R.id.btnGo)).perform(click());
//
//        // Check if the balance has been updated correctly
//        onView(withId(R.id.txt_balance)).check(matches(withText("140"))); // 100 + (4 * 10)
//    }
//    @Test
//    public void accessibilityTestDice() {
//        onView(withId(R.id.dice1))
//                .check(matches(isDisplayed()));
//        onView(withId(R.id.dice2))
//                .check(matches(isDisplayed()));
//        onView(withId(R.id.dice3))
//                .check(matches(isDisplayed()));
//        onView(withId(R.id.dice4))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void accessibilityTestBalanceLabel() {
//        onView(withId(R.id.tvCoins))
//                .check(matches(isDisplayed()));
//    }
//    @Test
//    public void accessibilityTestBtnInfo() {
//        onView(withId(R.id.btnInfo))
//                .check(matches(isDisplayed()));
//    }
//    private Die mockDie(int value) {
//        Die die = mock(Die.class);
//        when(die.value()).thenReturn(value);
//        return die;
//    }
}

