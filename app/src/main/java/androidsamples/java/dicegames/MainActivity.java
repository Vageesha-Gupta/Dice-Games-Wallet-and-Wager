package androidsamples.java.dicegames;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private GamesViewModel gamesViewModel;
//    private TextView txtBalance;
//    private Button btnDie, btnGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ViewModel
        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);

//         Bind UI components
//        txtBalance = findViewById(R.id.txt_balance);
//        btnDie = findViewById(R.id.btn_die);
//        btnGames = findViewById(R.id.btn_games);
//
//        // Set initial balance
//        txtBalance.setText(getString(R.string.coins) + " " + gamesViewModel.getBalance());
//
//        // Roll button click listener
//        btnDie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Roll the die and get the value
//                gamesViewModel.rollWalletDie();
//                int dieValue = gamesViewModel.dieValue(); // Roll the wallet die and get its value
//                // Update the button text to show the current die value
//                btnDie.setText(String.valueOf(dieValue));
//                // Update the balance display
//                txtBalance.setText(getString(R.string.coins) + " " + gamesViewModel.getBalance());
//            }
//        });
//
//        // Games button click listener (if needed)
//        btnGames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to games activity or fragment if needed
//                // Intent intent = new Intent(MainActivity.this, GamesActivity.class);
//                // startActivity(intent);
//            }
//        });
    }
}
