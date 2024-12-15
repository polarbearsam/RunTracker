package com.example.runtracker;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.runtracker.NotificationHandler;

public class FirstFragment extends Fragment {

    private Timer timer;
    private NotificationHandler notificationHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {


        return inflater.inflate(R.layout.fragment_first, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getting the notification handler from main activity
        notificationHandler = ((MainActivity) requireActivity()).notificationHandler;
        Log.d("FirstFragment", "MainActivity and NotificationHandler initialized: " + (notificationHandler != null));

        //widgets
        TextView timerTextView = view.findViewById(R.id.timerTextView);
        EditText editText = view.findViewById(R.id.editText);
        Button setButton = view.findViewById(R.id.setButton);
        Button startButton = view.findViewById(R.id.startButton);
        Button resetButton = view.findViewById(R.id.resetButton);

        //Initializing timer
        timer = new Timer(timerTextView);



        // setButton action listener, runs code on button click
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // sets the timer to 60 seconds | This will become a user inputted variable later
                Log.d("FirstFragment", "setButton clicked");
                String userTime = editText.getText().toString();
                int userNumber = Integer.parseInt(userTime);
                timer.setTimer(userNumber * 60); // Assumes user will input minutes.
            }
        });

        // startButton action listener, runs code on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // starts the timer
                timer.startTimer();
                Thread thread = new Thread(() -> {
                    while (timer.isRunning()) {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            Log.e("FirstFragment", "Sleep interrupted!", new RuntimeException(e));
                        }
                    }
                    Log.d("FirstFragment", String.valueOf(timer.getTimerFinished()));
                    if (timer.getTimerFinished()) {
                        notificationHandler.sendNotification(notificationHandler.createNotification());
                        Log.d("FirstFragment", "NotificationHandler in fragment: " + (notificationHandler != null));
                    }
                });

                thread.start();
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
}