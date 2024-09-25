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

        bal = view.findViewById(R.id.txt_balance);

        updateBalanceDisplay();

        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
            Log.d(TAG, "Rolled");
        });

        view.findViewById(R.id.btn_games).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            NavDirections a = WalletFragmentDirections.actionWalletFragmentToGamesFragment();
            Navigation.findNavController(view).navigate(a);
        });
    }
    private void updateBalanceDisplay() {
        bal.setText(String.valueOf(vm.getWalletBalance()));  // Set the balance from ViewModel
    }

}