package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;

    private TextView mBalanceTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        Log.d(TAG, "VM: " + vm);

        mBalanceTextView = view.findViewById(R.id.txt_balance); // Ensure correct ID
        Button rollDieButton = view.findViewById(R.id.btn_die);
        Button goToGamesButton = view.findViewById(R.id.btn_games);

        // Set up click listeners


        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
            Log.d(TAG, "Rolled");
        });

        view.findViewById(R.id.btn_games).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            NavDirections a = WalletFragmentDirections.actionWalletFragmentToGamesFragment();
            Navigation.findNavController(view).navigate(a);
        });

        updateBalanceDisplay();
    }

    private void rollDice() {
        vm.rollWalletDie();  // Call the method to roll the die
        Log.d(TAG, "Rolled: " + vm.dieValue());
        updateBalanceDisplay();  // Update the balance display after rolling the die
    }

    private void updateBalanceDisplay() {
        // Update the balance text view with the current balance from the ViewModel
        mBalanceTextView.setText("Balance: " + vm.balance); // Use the correct variable
    }

}