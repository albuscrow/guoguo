package com.nineton.guoguo.listener;

import com.nineton.guoguo.adapter.RandomItemListAdapter;
import com.nineton.guoguo.common.AppConfig;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;

public class MySensorEventListener implements SensorEventListener {

	private static final int RANGE = 11;
	private RandomItemListAdapter randomItemListAdapter;
	private Context context;
	private boolean flag = true;

	public MySensorEventListener(RandomItemListAdapter randomItemListAdapter, Context context) {
		this.randomItemListAdapter = randomItemListAdapter;
		this.context = context;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (flag&&AppConfig.HAS_WIFI) {
			int sensorType = event.sensor.getType();
			float[] values = event.values;
			if (sensorType == Sensor.TYPE_ACCELEROMETER){
				if (Math.abs(values[0]) > RANGE || Math.abs(values[1]) > RANGE || Math.abs(values[2]) > RANGE){
					((Vibrator)context.getSystemService(Activity.VIBRATOR_SERVICE)).vibrate(100);
					randomItemListAdapter.refresh();
					flag = false;
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							flag = true;
						}
					}).start();
				}
			}	
		}
	}

}
