/**
 * Author: Samuel Freer
 * Created: 10/30/2024
 * Modified: 10/30/2024
 * Creates a timer to be used to track running time.
 */
package com.example.runtracker;

import android.util.Log;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;

/**
 * Creates a timer to be used to track running time.
 */
public class Timer {
    private Boolean running = false;
    private Boolean timerFinished = false;
    private int time = 0;

    private final TextView timerTextView;

    private final Handler handler;
    public Timer(TextView textView) {
        this.timerTextView = textView;
        this.handler = new Handler(Looper.getMainLooper());
    }

    private void updateGUI() {
        handler.post(() -> timerTextView.setText(String.valueOf(time)));
    }

    /**
     * A thread that counts down every second.
     */
    private void runTimer() { //I was not able to update the GUI from this function if it is static, so i made it not static for now - Brian Downey
        // Must be run as a separate thread every time
        // TODO: This code is non-functional.
        timerFinished = false;


        Thread thread = new Thread(() -> {
            while (time > 0 && running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("Timer", "Sleep interrupted!", new RuntimeException(e));
                }
                time--;
                Log.d("Timer", String.valueOf(time));
                updateGUI();
            }

            if (time <= 0) {
                resetTimer();
                timerFinished = true;
                Log.i("Timer", "Timer finished!");
            } else {
                timerFinished = false;
                Log.i("Timer", "Timer stopped.");
            }
        });
        thread.start();
    }

    /**
     * Adds given amount of time to timer
     * @param seconds to add to timer in seconds
     */
    public void addTime(int seconds) {
        time += seconds;
        Log.i("Timer", "Adding " + String.valueOf(seconds) + " seconds to timer.");
    }

    /**
     * Gets remaining time on timer
     * @return remaining time in seconds
     */
    public int getRemainingTime() {
        return time;
    }

    /**
     * Resets the timer to 0 and stops the timer.
     */
    public void resetTimer() {
        running = false;
        time = 0;
        timerFinished = false;
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
        time = seconds;
        Log.i("Timer", "Setting timer to " + String.valueOf(seconds) + " seconds.");
        Log.d("Timer", String.valueOf(time));
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
        time = seconds;
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
    public int getRemainingSeconds(int seconds) {
        return seconds % 60;
    }

    /**
     * Returns current time in minutes and seconds.
     * @return Array containing remaining minutes and remaining seconds in that order.
     */
    public int[] getCurrentTime() {
        int[] currentTime = new int[2];
        currentTime[0] = getMinutesFromSeconds(time);
        currentTime[1] = getRemainingSeconds(time);
        return currentTime;
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
}
