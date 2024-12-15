/**
 * Author: Samuel Freer
 * Created: 10/30/2024
 * Modified: 12/13/2024
 * Creates a timer to be used to track running time.
 */
package com.example.runtracker;

import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;

/**
 * Creates a timer to be used to track running time.
 */
public class Timer {
    // VARIABLES
    private Boolean running = false;
    private Boolean timerFinished = false;
    private final TextView timerTextView;
    private final Handler handler;
    private int timeRemaining = 0;
    private final StepCounter stepCounter;

    /**
     * Creates a timer object
     * @param textView timer will update
     */
    public Timer(TextView textView) {
        this.stepCounter = new StepCounter();
        this.timerTextView = textView;
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * A thread that counts down every second.
     */
    private void runTimer() {
        running = true;
        stepCounter.toggle();

        // Decreasing timeRemaining by 1 every second, must be run as a separate thread
        Thread thread = new Thread(() -> {
            while (timeRemaining > 0 && running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("Timer", "Sleep interrupted!", new RuntimeException(e));
                }
                timeRemaining--;
                //Log.d("Timer", String.valueOf(timeRemaining));
                updateGUI();
            }

            stepCounter.toggle();
            if (timeRemaining <= 0) {
                timerFinished = true;
                running = false;
                Log.i("Timer", stepCounter.getStepcount() + " steps taken.");
                Log.i("Timer", "Timer finished!");
            } else {
                Log.i("Timer", "Timer stopped.");
            }
        });

        thread.start(); // Runs the previous code as a separate thread.
    }

    /**
     * Adds given amount of time to timer
     * @param seconds to add to timer in seconds
     */
    public void addTime(int seconds) {
        timeRemaining += seconds;
        Log.i("Timer", "Adding " + String.valueOf(seconds) + " seconds to timer.");
    }

    /**
     * Gets remaining time on timer
     * @return remaining time in seconds
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Resets the timer to 0 and stops the timer.
     */
    public void resetTimer() {
        running = false;
        timerFinished = false;
        timeRemaining = 0;
    }

    /**
     * Resumes the timer
     */
    public void resumeTimer() {
        if (!running) {
            running = true;
            runTimer();
        }
    }

    /**
     * Sets timer to specific value
     * @param seconds to set timer to in seconds
     */
    public void setTimer(int seconds) {
        Log.i("Timer", "Setting timer to " + String.valueOf(seconds) + " seconds.");
        timeRemaining = seconds;
        updateGUI();
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        running = true;
        runTimer();
    }

    /**
     * Starts the timer
     * @param seconds = amount of seconds the timer will run for
     */
    public void startTimer(int seconds) {
        if (running) {
            resetTimer();
        }
        timeRemaining = seconds;
        running = true;
        runTimer();
    }

    /**
     * Converts a given number of seconds into minutes
     * @param seconds time to convert
     * @return minutes
     */
    public int getMinutesFromSeconds(int seconds) {
        return seconds / 60;
    }

    /**
     * Converts a given number of seconds into minutes and returns remaining seconds in that minute.
     * @param seconds time to convert
     * @return remaining seconds in the minutes
     */
    public int getSecondsLeftFromMinutes(int seconds) {
        return seconds % 60;
    }

    /**
     * Returns time in minutes and seconds.
     * @param seconds to convert to minutes and seconds
     * @return Array containing minutes and seconds in that order.
     */
    public int[] SecondsToTime(int seconds) {
        int[] currentTime = new int[2];
        currentTime[0] = getMinutesFromSeconds(seconds);
        currentTime[1] = getSecondsLeftFromMinutes(seconds);
        return currentTime;
    }

    /**
     * Returns remaining time in minutes and seconds.
     * @return Array containing remaining minutes and remaining seconds in that order.
     */
    public int[] RemainingTime() {
        return SecondsToTime(timeRemaining);
    }

    /**
     * Returns if the timer is running
     * @return is timer running
     */
    public Boolean isRunning() {
        return running;
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        running = false;
    }

    /**
     * Checks if the timer has reached zero.
     * @return timerFinished (true if timer reached zero, otherwise false)
     */
    public Boolean getTimerFinished() {
        return timerFinished;
    }

    /**
     * Updates the GUI to show the remaining time.
     */
    private void updateGUI() {
        int[] currentTime = RemainingTime();
        handler.post(() -> timerTextView.setText(String.valueOf(currentTime[0]) + ":" + String.valueOf(currentTime[1])));
    }
}
