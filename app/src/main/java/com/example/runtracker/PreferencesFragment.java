package com.example.runtracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class PreferencesFragment extends Fragment {

    private EditText editDistance;
    private EditText editTime;
    private EditText editPace;
    private Button calcButton;
    private Button clearButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferences, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        editDistance = view.findViewById(R.id.distanceEdit);
        editPace = view.findViewById(R.id.paceEdit);
        editTime = view.findViewById(R.id.timeEdit);
        calcButton = view.findViewById(R.id.calculateButton);
        clearButton = view.findViewById(R.id.clearButton);


        calcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                double distance = 0;
                double pace = 0;
                double time = 0;


                String distanceText = editDistance.getText().toString();
                distance = !distanceText.isEmpty() ? Double.parseDouble(distanceText): 0;

                String paceText = editPace.getText().toString();
                pace = !paceText.isEmpty() ? Double.parseDouble(paceText) : 0;

                String timeText = editTime.getText().toString();
                time = !timeText.isEmpty() ? Double.parseDouble(timeText) : 0;


                if (distance == 0 && time != 0 && pace != 0){
                    distance = (pace * (time/60));
                    editDistance.setText(String.valueOf(distance));
                }


                if (pace == 0 && time != 0 && distance != 0) {
                    pace = ((60*distance)/time);
                    editPace.setText(String.valueOf(pace));
                }

                if (time == 0 && pace != 0 && distance != 0) {
                    time = (60*distance)/pace;
                    editTime.setText(String.valueOf(time));
                }



            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



            }
        });


    }
}