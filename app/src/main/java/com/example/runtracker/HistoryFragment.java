package com.example.runtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RunTrackerViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(RunTrackerViewModel.class);

        // Get data from ViewModel
        List<Integer> runTimes = viewModel.getRunTimes();
        List<Integer> stepCounts = viewModel.getStepCounts();

        // Update TextViews with historical data
        for (int i = 0; i < runTimes.size(); i++) {
            int runTime = runTimes.get(i);
            int stepCount = stepCounts.get(i);

            // Find the corresponding TextView by ID
            String textViewId = "historyItem" + (i + 1);
            int resId = getResources().getIdentifier(textViewId, "id", requireContext().getPackageName());
            TextView textView = view.findViewById(resId);

            if (textView != null) {
                textView.setText((i + 1) + ". Run Time: " + runTime + " min | Steps: " + stepCount);
            }
        }
    }
}
