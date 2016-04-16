package com.gesturepart;

import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gesturepart.gestureDetection;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new gestureDetection(), config);
		//add GestureViewHere
		//System.out.println("Initialised.");



	}
}
