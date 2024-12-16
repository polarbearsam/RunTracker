package com.example.runtracker;
import static android.content.Context.SENSOR_SERVICE;

import android.app.Activity;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

// Either the onCreate() function needs to be added to this class or it CANNOT be an Activity.
public class StepCounter extends Activity implements SensorEventListener2 {
    public SensorManager mSensorManager;
    public Sensor steps;
    public boolean active;
    private boolean firstEvent = true;
    private int originalSteps = 0;

    public int stepcount;

    public StepCounter ()
    {
        this.mSensorManager = MainActivity.sensorManager; // CANNOT be called before onCreate() is called.
        steps = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (steps == null)
        {
            Log.e("StepCounter", "No steps sensor!");
        }
        active = false;
    }
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
    public void toggle(){
        if (!active) {
            mSensorManager.registerListener(this, steps, SensorManager.SENSOR_DELAY_NORMAL);
            active = true;
        }
        else {
            mSensorManager.unregisterListener(this);
            active = false;
        }
    }

    public void reset () {
        stepcount = 0;
        mSensorManager.unregisterListener(this);
        active = false;
        firstEvent = false;
        originalSteps = 0;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (event == null) return;
        if (firstEvent) {
            originalSteps = (int) event.values[0];
            firstEvent = false;
        }
        Log.i("StepCounter", "Steps added: " + event.values[0]);
        stepcount = (int) event.values[0] - originalSteps;
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    public int getStepcount ()
    {
        return stepcount;
    }
}
