package com.example.runtracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.widget.Toast;


public class PreferencesFragment extends Fragment {

    private EditText editDistance;
    private EditText editTime;
    private EditText editPace;
    private Button calcButton;
    private Button clearButton;
    private Button copyButton;


    public void copyToClipboard(String textToCopy) {
        // Getting the clipboard service
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard != null)
        {
            ClipData clip = ClipData.newPlainText("label", textToCopy); // creates clip data
            clipboard.setPrimaryClip(clip); // sets the clipboard text
            Toast.makeText(getActivity(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }


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
        copyButton = view.findViewById(R.id.copyButton);


        calcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // initalize values
                double distance = 0;
                double pace = 0;
                double time = 0;

                // format for rounding
                DecimalFormat df = new DecimalFormat("#");
                df.setRoundingMode(RoundingMode.HALF_UP);


                // gets the current values and sets not set values to 0
                String distanceText = editDistance.getText().toString();
                distance = !distanceText.isEmpty() ? Double.parseDouble(distanceText): 0;

                String paceText = editPace.getText().toString();
                pace = !paceText.isEmpty() ? Double.parseDouble(paceText) : 0;

                String timeText = editTime.getText().toString();
                time = !timeText.isEmpty() ? Double.parseDouble(timeText) : 0;


                // calculates and sets values based on what the user has already input
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
                    String formattedTime = df.format(time);
                    editTime.setText(formattedTime);
                }



            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editDistance.setText("");
                editPace.setText("");
                editTime.setText("");


            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String textToCopy = editTime.getText().toString(); // gets time string
                copyToClipboard(textToCopy); //calls copy function
            }
        });


    }
}