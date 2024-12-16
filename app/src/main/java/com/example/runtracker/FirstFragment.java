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
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    private Timer timer;
    private NotificationHandler notificationHandler;
    private StepCounter stepCounter;
    private RunTrackerViewModel viewModel;

    private List<Integer> runTimes = new ArrayList<>();
    private List<Integer> stepCounts = new ArrayList<>();

    public int currentTime = 0;
    public int steps = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {


        return inflater.inflate(R.layout.fragment_first, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity()).get(RunTrackerViewModel.class);

        //getting the notification handler from main activity
        notificationHandler = ((MainActivity) requireActivity()).notificationHandler;
        Log.d("FirstFragment", "MainActivity and NotificationHandler initialized: " + (notificationHandler != null));

        //widgets
        TextView timerTextView = view.findViewById(R.id.timerTextView);
        TextView stepView = view.findViewById(R.id.stepView);
        EditText editText = view.findViewById(R.id.editText);
        Button setButton = view.findViewById(R.id.setButton);
        Button startButton = view.findViewById(R.id.startButton);
        Button resetButton = view.findViewById(R.id.resetButton);

        //Initializing timer and step counter
        timer = new Timer(timerTextView);
        stepCounter = new StepCounter();



        // setButton action listener, runs code on button click
        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("FirstFragment", "setButton clicked");
                String userTime = editText.getText().toString();
                int userNumber = Integer.parseInt(userTime);
                timer.setTimer(userNumber * 60); // Assumes user will input minutes.
            }
        });

        // startButton action listener, runs code on button click
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // starts the timer and step counter
                currentTime = Integer.parseInt(editText.getText().toString());
                timer.startTimer();
                stepCounter.toggle();

                Thread thread = new Thread(() -> {
                    while (timer.isRunning()) {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            Log.e("FirstFragment", "Sleep interrupted!", new RuntimeException(e));
                        }

                        getActivity().runOnUiThread(() -> {
                            stepView.setText(String.valueOf(stepCounter.getStepcount()));
                        });
                    }
                    Log.d("FirstFragment", String.valueOf(timer.getTimerFinished()));
                    if (timer.getTimerFinished()) {
                        notificationHandler.sendNotification(notificationHandler.createNotification());
                        Log.d("FirstFragment", "NotificationHandler in fragment: " + (notificationHandler != null));
                        steps = stepCounter.getStepcount();

                        if (runTimes.size() >= 10) {
                            runTimes.remove(0);
                            stepCounts.remove(0);
                        }

                        runTimes.add(currentTime);
                        stepCounts.add(steps);

                        viewModel.addRunData(currentTime, steps);
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
                steps = stepCounter.getStepcount();
                viewModel.addRunData(currentTime, steps);
            }
        });


    }
}