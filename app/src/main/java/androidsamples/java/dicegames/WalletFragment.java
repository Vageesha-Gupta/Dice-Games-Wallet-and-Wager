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
import android.widget.Toast;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;
    private TextView bal;
    private Button dieButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        dieButton = view.findViewById(R.id.btn_die);
        Log.d(TAG, "VM: " + vm);
        Integer initialBalance = vm.getBalance().getValue();
        Log.d(TAG, "Initial balance: " + vm.getBalance().getValue());
        bal = view.findViewById(R.id.tvCoinsValue);
        bal.setText(String.valueOf(initialBalance != null ? initialBalance : 0));
        Log.d(TAG, "TextView found: " + (bal != null));

        vm.getBalance().observe(getViewLifecycleOwner(), balance -> {
            Log.d(TAG, "Balance observer triggered with: " + balance);
            // Update the TextView when the balance changes
//            bal.setText(String.valueOf(balance));
            if (bal != null) {
                bal.setText(String.valueOf(balance));
                Log.d(TAG, "TextView updated to: " + balance);
            } else {
                Log.e(TAG, "TextView is null!");
            }
            Log.d(TAG, "Balance updated to: " + balance);
        });
        // Observe the die value LiveData and update the die button text
        vm.getDieValue().observe(getViewLifecycleOwner(), dieValue -> {
            Log.d(TAG, "Die value observer triggered with: " + dieValue);
            if (dieButton != null) {
                dieButton.setText(String.valueOf(dieValue));
                Log.d(TAG, "Die button updated to: " + dieValue);
            } else {
                Log.e(TAG, "Die button is null!");
            }
        });
        dieButton.setOnClickListener(v -> {
            Log.d(TAG, "Rolled");
            vm.rollWalletDie();
            if(vm.dieValue()==6){
                Toast.makeText(getContext(), "You won!", Toast.LENGTH_SHORT).show();
            }
            // Update the button text with the new die value
            //dieButton.setText(String.valueOf(vm.dieValue()));
        });

//        updateBalanceDisplay();

//        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
//            Log.d(TAG, "Rolled");
//            vm.rollWalletDie();  // Roll the die in the ViewModel
////            updateBalanceDisplay();  // Update balance after the die roll
//        });

        view.findViewById(R.id.btnToGames).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            // Use Safe Args to navigate
            int bal=vm.getBalance().getValue();
            NavDirections action = WalletFragmentDirections.actionWalletFragmentToGamesFragment(bal);
            Navigation.findNavController(view).navigate(action);
        });

    }
    private void updateBalanceDisplay() {
//        bal.setText(String.valueOf(vm.getBalance()));  // Set the balance from ViewModel
        vm.getBalance().observe(getViewLifecycleOwner(), balance -> {
            bal.setText(String.valueOf(balance));
        });

    }

}