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
import android.widget.TextView;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;
    private TextView bal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        Log.d(TAG, "VM: " + vm);

//        bal = view.findViewById(R.id.txt_balance);
        bal = view.findViewById(R.id.tvCoins);

        updateBalanceDisplay();

        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
            Log.d(TAG, "Rolled");
            vm.rollWalletDie();  // Roll the die in the ViewModel
            updateBalanceDisplay();  // Update balance after the die roll
        });

        view.findViewById(R.id.btnToGames).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            // Use Safe Args to navigate
            NavDirections action = WalletFragmentDirections.actionWalletFragmentToGamesFragment();
            Navigation.findNavController(view).navigate(action);
        });

    }
    private void updateBalanceDisplay() {
        bal.setText(String.valueOf(vm.getBalance()));  // Set the balance from ViewModel
    }

}