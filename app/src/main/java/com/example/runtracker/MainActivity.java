package com.example.runtracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static SensorManager sensorManager;

    public NotificationHandler notificationHandler;

    private RunTrackerViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(RunTrackerViewModel.class);




        createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionHandler.requestPermission(this, this, Manifest.permission.ACTIVITY_RECOGNITION);
        }
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        notificationHandler = new NotificationHandler(this);
        Log.d("MainActivity", "NotificationHandler initialized: " + (notificationHandler != null));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FirstFragment()).commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        List<Integer> runTimes = viewModel.getRunTimes();
        List<Integer> stepCounts = viewModel.getStepCounts();

        if (id == R.id.home) {
            replaceFragment(new FirstFragment());
            return true;
        } else if (id == R.id.preferences) {
            replaceFragment(new PreferencesFragment());
            return true;
        } else if (id == R.id.history) {
            replaceFragment(new HistoryFragment());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    private void replaceFragment(Fragment fragment) {
        clearBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }


    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

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

        Log.d("MainActivity", "Notification channel created");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.d("MainActivity", "Requesting permission");
            PermissionHandler.requestPermission(this, this, Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}