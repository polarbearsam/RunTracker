package com.example.runtracker;
import static android.content.Context.SENSOR_SERVICE;

import android.app.Activity;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class StepCounter extends Activity implements SensorEventListener2 {
    public SensorManager mSensorManager;
    public Sensor steps;
    public boolean active;

    public int stepcount;

    public StepCounter ()
    {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        steps = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (steps == null)
        {
            System.out.println("sensor not found");
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
        if (active == false) {
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
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (event == null) return;

        stepcount += event.values[0];
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    public int getStepcount ()
    {
        return stepcount;
    }
}
