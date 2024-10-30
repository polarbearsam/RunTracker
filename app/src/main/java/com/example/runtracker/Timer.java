/**
 * Author: Samuel Freer
 * Created: 10/30/2024
 * Modified: 10/30/2024
 * Creates a timer to be used to track running time.
 */
package com.example.runtracker;

/**
 * Creates a timer to be used to track running time.
 */
public class Timer {
    private Boolean running = false;
    private int time = 0;

    /**
     * A thread that counts down every second.
     * @return Returns whether the timer hit 0 or was stopped before
     */
    private Boolean runTimer() {
        // Must be run as a separate thread every time
        // TODO: This code is non-functional.
        while (time > 0 && running) {
            // Wait 1 second
            time--;
        }

        if (time <= 0) {
            resetTimer();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds given amount of time to timer
     * @param time to add to timer in seconds
     */
    public void addTime(int time) {
        this.time += time;
    }

    /**
     * Sets timer to specific value
     * @param time to set timer to in seconds
     */
    public void setTimer(int time) {
        this.time = time;
    }

    /**
     * Gets remaining time on timer
     * @return remaining time in seconds
     */
    public int getRemainingTime() {
        return time;
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        running = true;
        runTimer();
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        running = false;
    }

    /**
     * Resets the timer to 0 and stops the timer.
     */
    public void resetTimer() {
        running = false;
        time = 0;
    }
}
