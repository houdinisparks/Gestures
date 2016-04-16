/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.gesture.builder;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.gesture.GestureOverlayView;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.widget.TextView;
import android.widget.Toast;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class CreateGestureActivity extends Activity {

    private static final float LENGTH_THRESHOLD = 120.0f;
    private static int ZShapeFileCount = 0;
    private static int InvertedZShapeFileCount = 0;
    private static int HorizontalLineFileCount = 0;
    private static int VerticalLineFileCount = 0;
    private static int VShapeTypeFileCount = 0;
    private static int InvertedVShapeFileCount = 0;
    private static int AlphaFileCount = 0;
    private static int GammaFileCount = 0;
    private static int SigmaFileCount = 0;
    private static int MShapeFileCount = 0;
    private static int ReverseCFileCount = 0;
    private static int TriangleFileCount = 0;


    private static int CurrentFileCount = 0;

    private static final String ZShapeType = "zshape";
    private static final String InvertedZShapeType = "invertedzshape";
    private static final String HorizontalLine = "horizontalline";
    private static final String VerticalLine = "verticalline";
    private static final String VShapeType = "vshape";
    private static final String InvertedVShapeType = "invertedvshape";
    private static final String AlphaType = "alpha";
    private static final String GammaType = "gamma";
    private static final String SigmaType = "sigma";
    private static final String MShapeType = "mshape";
    private static final String ReverseCShapeType = "revcshape";
    private static final String TriangleType = "triangle";


    private ArrayList<Point2D> listOfPoints = new ArrayList<Point2D>();

    private Gesture mGesture;
    private View mDoneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.create_gesture);

        mDoneButton = findViewById(R.id.done);

        GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        overlay.addOnGestureListener(new GesturesProcessor());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        if (mGesture != null) {
            outState.putParcelable("gesture", mGesture);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        
        mGesture = savedInstanceState.getParcelable("gesture");
        if (mGesture != null) {
            final GestureOverlayView overlay =
                    (GestureOverlayView) findViewById(R.id.gestures_overlay);
            overlay.post(new Runnable() {
                public void run() {
                    overlay.setGesture(mGesture);
                }
            });

            mDoneButton.setEnabled(true);
        }
    }

    /*
    Listener for the Done view button.
    Adds gesture and save it on phones memory.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public void addGesture(View v) {
        if (mGesture != null) {
            final TextView input = (TextView) findViewById(R.id.gesture_name);
            final CharSequence name = input.getText();
            if (name.length() == 0) {
                input.setError(getString(R.string.error_missing_name));
                return;
            }

            /*
            Parse into Gesture into a Json Object
             */


            final GestureLibrary store = GestureBuilderActivity.getStore();
            String typeOfShape = typeOfShape(name.toString());
            store.addGesture(typeOfShape + CurrentFileCount, mGesture);
            store.save();

            setResult(RESULT_OK);

            /*
            New file name for each Json Object saved
             */

            File pathDir = null;
            CreateJsonFile createJsonFile = new CreateJsonFile(listOfPoints);
            createJsonFile.addName(typeOfShape);

            try {

                pathDir = new File(Environment.getExternalStorageDirectory(),
                        typeOfShape + CurrentFileCount + ".json");
                FileWriter fileWriter = new FileWriter(pathDir);
                fileWriter.write(createJsonFile.generateJsonFile().toString());
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            Save Json Object into relevant directory
             */
            Toast.makeText(this, getString(R.string.save_success, pathDir.getAbsolutePath()), Toast.LENGTH_LONG).show();
        } else {
            setResult(RESULT_CANCELED);
        }


    }

    
    @SuppressWarnings({"UnusedDeclaration"})
    public void cancelGesture(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }


    /**
     * Step 1. Create JSON File
     * Step 2. Save the File onto the phone's internal memory.
     */
    private class GesturesProcessor implements GestureOverlayView.OnGestureListener {
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mDoneButton.setEnabled(false);
            mGesture = null;

            listOfPoints.clear();
            listOfPoints.add(new Point2D(event.getRawX(), event.getRawY()));

            Log.d("GestureStarted ", event.getRawX() + "  " + event.getRawY());

        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            listOfPoints.add(new Point2D(event.getRawX(), event.getRawY()));

            Log.d("GesturePoints: ", event.getRawX() + "  " + event.getRawY());

        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            if (mGesture.getLength() < LENGTH_THRESHOLD) {
                overlay.clear(false);
            }

            mDoneButton.setEnabled(true);
            Log.d("GestureEnded ", event.getRawX() + "  " + event.getRawY());

        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }

    public static String typeOfShape(String userInput) {

        if (userInput.contains("zshape")) {
            ZShapeFileCount += 1;
            CurrentFileCount = ZShapeFileCount;
            return ZShapeType;

        } else if (userInput.contains("invz")) {
            InvertedZShapeFileCount += 1;
            CurrentFileCount = InvertedZShapeFileCount;
            return InvertedZShapeType;

        } else if (userInput.contains("horizontal")) {
            HorizontalLineFileCount += 1;
            CurrentFileCount = HorizontalLineFileCount;
            return HorizontalLine;

        } else if (userInput.contains("vertical")) {
            VerticalLineFileCount += 1;
            CurrentFileCount = VerticalLineFileCount;
            return VerticalLine;

        } else if (userInput.contains("vshape")) {
            VShapeTypeFileCount += 1;
            CurrentFileCount = VShapeTypeFileCount;
            return VShapeType;

        } else if (userInput.contains("invv")) {
            InvertedVShapeFileCount += 1;
            CurrentFileCount = InvertedVShapeFileCount;
            return InvertedVShapeType;

        } else if (userInput.contains("alpha")) {
            AlphaFileCount += 1;
            CurrentFileCount = AlphaFileCount;
            return AlphaType;

        } else if (userInput.contains("gamma")) {
            GammaFileCount += 1;
            CurrentFileCount = GammaFileCount;
            return GammaType;

        } else if (userInput.contains("sigma")) {
            SigmaFileCount += 1;
            CurrentFileCount = SigmaFileCount;
            return SigmaType;
        } else if (userInput.contains("mshape")) {
            MShapeFileCount += 1;
            CurrentFileCount = MShapeFileCount;
            return MShapeType;
        } else if (userInput.contains("revcshape")) {
            ReverseCFileCount += 1;
            CurrentFileCount = ReverseCFileCount;
            return ReverseCShapeType;
        } else if (userInput.contains("triangle")) {
            TriangleFileCount += 1;
            CurrentFileCount = TriangleFileCount;
            return TriangleType;
        } else {
            return null;
        }
    }
}

class CreateJsonFile{

    private String TAG = "createJsonFile";

    ArrayList<Point2D> listOfPoints;
    boolean isItFinished;
    private JSONObject jsonObject;

    public CreateJsonFile(ArrayList<Point2D> listOfPoints) {
        this.listOfPoints = listOfPoints;
        this.isItFinished = false;
        jsonObject = new JSONObject();

    }

    public void addPoint(float x, float y) {
        listOfPoints.add(new Point2D(x, y));
        Log.e(TAG, "point added: " + x +" " + y);
    }

    public void setItFinished(boolean state) {
        this.isItFinished = state;
    }

    public void clearPoints() {
        if (isItFinished) {
            this.listOfPoints.clear();
        }
        else {
            Log.e(TAG, "clear points failed.");
        }

    }

    public void addName(String name) {

            jsonObject.put("Name", name);

    }

    public JSONObject generateJsonFile() {
        //Create a JSON Object.

        //Put key "Points" in jsonObject
        JSONArray jsonArrayOfHashMaps = new JSONArray();

        for (Point2D points : listOfPoints) {
            HashMap<String, Float> hashMap = new HashMap<String, Float>();

            hashMap.put("X", points.x);
            hashMap.put("Y", points.y);

            jsonArrayOfHashMaps.add(hashMap);
        }


            jsonObject.put("Points", jsonArrayOfHashMaps);



        return jsonObject;

    }


}

class Point2D{

    public float x;
    public float y;

    public Point2D(float x , float y) {
        this.x = x;
        this.y = y;
    }
}
