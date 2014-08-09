package com.nineton.guoguo.utils;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.nineton.guoguo.adapter.RandomItemListAdapter;
import com.nineton.guoguo.listener.MySensorEventListener;

public class MySensorManager {

	private static SensorManager sensorManager;
	private static MySensorEventListener sensorEventListener;
	private static boolean isregistered;

	public static void init(Activity activity, RandomItemListAdapter randomItemListAdapter) {
		sensorManager = (SensorManager)activity.getSystemService(Activity.SENSOR_SERVICE);
		sensorEventListener = new MySensorEventListener(randomItemListAdapter,activity);		
	}

	public static void unregisterListener() {
		sensorManager.unregisterListener(sensorEventListener);
		isregistered = false;
	}

	public static void registerListener() {
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		isregistered = true;
	}
	
	public static void leaveViewPager(){
		if (isregistered) {
			unregisterListener();
			isregistered = true;
		}
	}
	
	public static void arriveViewPager(){
		if (isregistered) {
			registerListener();
		}
	}

}
