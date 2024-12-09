package com.example.runtracker;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.runtracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public Timer timer;
    
    public EditText editText;
    public NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        notificationHandler = new NotificationHandler(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initializes the timer and sends timerTextView to it
        TextView timerTextView = findViewById(R.id.timerTextView);
        timer = new Timer(timerTextView);

        // initializes buttons
        Button setButton = findViewById(R.id.setButton);
        Button startButton = findViewById(R.id.startButton);
        Button resetButton = findViewById(R.id.resetButton);

        //setSupportActionBar(binding.toolbar);

        /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
         */

        /*
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

         */

        // setButton action listener, runs code on button click
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // sets the timer to 60 seconds | This will become a user inputted variable later
                Log.d("MainActivity", "setButton clicked");
                editText = findViewById(R.id.editText);
                String userTime = editText.getText().toString();
                int userNumber = Integer.parseInt(userTime);
                timer.setTimer(userNumber);
            }
        });

        // startButton action listener, runs code on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // starts the timer
                timer.startTimer();

                while (timer.isRunning()) {

                }
                Log.d("MainActivity", String.valueOf(timer.getTimerFinished()));

                if (timer.getTimerFinished()) {
                    notificationHandler.sendNotification(notificationHandler.createNotification());
                }
            }
        });

        // resetButton action listener, runs code on button click
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // resets the timer to 0s and stops it running
                timer.resetTimer();
            }
        });

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
     */

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}