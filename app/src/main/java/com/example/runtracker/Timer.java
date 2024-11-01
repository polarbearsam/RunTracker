/**
 * Author: Samuel Freer
 * Created: 10/30/2024
 * Modified: 10/30/2024
 * Creates a timer to be used to track running time.
 */
package com.example.runtracker;

import android.util.Log;

/**
 * Creates a timer to be used to track running time.
 */
public class Timer {
    private static Boolean running = false;
    private static Boolean timerFinished = false;
    private static int time = 0;

    /**
     * A thread that counts down every second.
     */
    private static void runTimer() {
        // Must be run as a separate thread every time
        // TODO: This code is non-functional.
        Thread thread = new Thread(() -> {
            while (time > 0 && running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                time--;
                Log.d("Timer", String.valueOf(time));
            }

            if (time <= 0) {
                resetTimer();
                timerFinished = true;
            } else {
                timerFinished = false;
            }
        });
        thread.start();
    }

    /**
     * Adds given amount of time to timer
     * @param time to add to timer in seconds
     */
    public static void addTime(int time) {
        Timer.time += time;
    }

    /**
     * Sets timer to specific value
     * @param time to set timer to in seconds
     */
    public static void setTimer(int time) {
        Timer.time = time;
    }

    /**
     * Gets remaining time on timer
     * @return remaining time in seconds
     */
    public static int getRemainingTime() {
        return time;
    }

    /**
     * Starts the timer
     */
    public static void startTimer() {
        running = true;
        runTimer();
    }

    /**
     * Stops the timer
     */
    public static void stopTimer() {
        running = false;
    }

    /**
     * Resets the timer to 0 and stops the timer.
     */
    public static void resetTimer() {
        running = false;
        time = 0;
    }
}
