package androidsamples.java.dicegames;

import static android.app.BroadcastOptions.fromBundle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GamesFragment extends Fragment {
    private GamesViewModel viewModel;
    private TextView tvCoins;
    private RadioGroup rgGameType;
    private EditText etWager;
    private Button btnGo;
    private Button[] diceButtons;
    private Button btnInfo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);

//

        int balance = GamesFragmentArgs.fromBundle(getArguments()).getBal();
        tvCoins = view.findViewById(R.id.tvCoins);

        rgGameType = view.findViewById(R.id.rgGameType);
        etWager = view.findViewById(R.id.etWager);
        btnGo = view.findViewById(R.id.btnGo);
        btnInfo=view.findViewById(R.id.btnInfo);

        diceButtons = new Button[]{
                view.findViewById(R.id.dice1),
                view.findViewById(R.id.dice2),
                view.findViewById(R.id.dice3),
                view.findViewById(R.id.dice4)
        };
        viewModel.getBalance().observe(getViewLifecycleOwner(), currentBalance -> {
            tvCoins.setText("Coins: " + currentBalance); // Update the balance text in the UI
        });
        viewModel.getDiceValues().observe(getViewLifecycleOwner(), diceValues -> {
            Log.d("GamesFragment", "Updating dice buttons: " + Arrays.toString(diceValues));
            for (int i = 0; i < diceButtons.length; i++) {
                diceButtons[i].setText(String.valueOf(diceValues[i]));
            }
        });

//        setupUI(balance);
//        setupInfoButton();
        setupGameTypeRadioButtons();
        setupGoButton();
        setupInfoButton();

        return view;
    }
    private void setupGameTypeRadioButtons() {
        rgGameType.setOnCheckedChangeListener((group, checkedId) -> {
            GameType gameType;
            if (checkedId == R.id.rbTwoAlike) gameType = GameType.TWO_ALIKE;
            else if (checkedId == R.id.rbThreeAlike) gameType = GameType.THREE_ALIKE;
            else gameType = GameType.FOUR_ALIKE;
            viewModel.setGameType(gameType);
        });
    }

    private void setupGoButton() {
        btnGo.setOnClickListener(v -> {
            try {
                int wager = Integer.parseInt(etWager.getText().toString());
                viewModel.setWager(wager);
                if (viewModel.isValidWager()) {
                    GameResult result = viewModel.play();  // Play the game
                    showResult(result); // Show the result (win/loss)
                } else {
                    Toast.makeText(getContext(), "Invalid wager", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid wager", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupInfoButton() {
        btnInfo.setOnClickListener(v -> {
            // Navigate to the InfoFragment
            NavDirections action = GamesFragmentDirections.actionGamesFragmentToInfoFragment();
            Navigation.findNavController(v).navigate(action);
        });
    }

    private void setupUI(int balance) {
//        viewModel.setBalance(balance);
        tvCoins.setText("Coins: " + balance);
//        viewModel.getBalance().observe(getViewLifecycleOwner(), balance -> {
//            tvCoins.setText("Coins: " + balance);
//        });

        rgGameType.setOnCheckedChangeListener((group, checkedId) -> {
            GameType gameType;
            if (checkedId == R.id.rbTwoAlike) gameType = GameType.TWO_ALIKE;
            else if (checkedId == R.id.rbThreeAlike) gameType = GameType.THREE_ALIKE;
            else gameType = GameType.FOUR_ALIKE;
            viewModel.setGameType(gameType);
        });
        viewModel.getDiceValues().observe(getViewLifecycleOwner(), diceValues -> {
            Log.d("GamesFragment", "Updating UI with dice values: " + Arrays.toString(diceValues));
            for (int i = 0; i < diceButtons.length; i++) {
                diceButtons[i].setText(String.valueOf(diceValues[i])); // Update button text with dice value
            }
        });

        // Setup game type radio buttons
        rgGameType.setOnCheckedChangeListener((group, checkedId) -> {
            GameType gameType;
            if (checkedId == R.id.rbTwoAlike) gameType = GameType.TWO_ALIKE;
            else if (checkedId == R.id.rbThreeAlike) gameType = GameType.THREE_ALIKE;
            else gameType = GameType.FOUR_ALIKE;
            viewModel.setGameType(gameType);
        });

        btnGo.setOnClickListener(v -> {
            try {
                int wager = Integer.parseInt(etWager.getText().toString());
                viewModel.setWager(wager);
                if (viewModel.isValidWager()) {
                    GameResult result = viewModel.play();
                    //updateDiceUI();
                    showResult(result);
                } else {
                    Toast.makeText(getContext(), "Invalid wager", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid wager", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDiceUI() {
        int[] diceValues = viewModel.diceValues();
        for (int i = 0; i < diceButtons.length; i++) {
            diceButtons[i].setText(String.valueOf(diceValues[i]));
        }


    }

    private void showResult(GameResult result) {
        String message = result == GameResult.WIN ? "You won!" : "You lost!";
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}