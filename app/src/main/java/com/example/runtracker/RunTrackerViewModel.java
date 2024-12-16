package com.example.runtracker;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class RunTrackerViewModel extends ViewModel {
    private List<Integer> runTimes = new ArrayList<>();
    private List<Integer> stepCounts = new ArrayList<>();

    public List<Integer> getRunTimes() {
        return runTimes;
    }

    public List<Integer> getStepCounts() {
        return stepCounts;
    }

    public void addRunData(int runTime, int stepCount) {
        if (runTimes.size() >= 10) {
            runTimes.remove(0); // Keep only the latest 10
            stepCounts.remove(0); // Remove corresponding step count
        }
        runTimes.add(runTime);
        stepCounts.add(stepCount);
    }
}