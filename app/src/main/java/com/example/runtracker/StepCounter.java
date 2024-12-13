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

    public StepCounter ()
    {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        steps = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (steps == null)
        {
            System.out.println("sensor not found");
        }
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, steps, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    public int getStepcount ()
    {
        return 0;
    }
}
