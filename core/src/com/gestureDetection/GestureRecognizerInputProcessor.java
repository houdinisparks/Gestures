package com.gestureDetection;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Array;
import com.gesturepath.swipe.FixedList;
import com.gesturepath.swipe.SwipeResolver;
import com.gesturepath.swipe.simplify.ResolverRadialChaikin;

public class GestureRecognizerInputProcessor extends InputAdapter {

    private ProtractorGestureRecognizer recognizer;
    private ArrayList<Vector2> originalPath;

    private FixedList<Vector2> inputPoints;

    /**
     * The pointer associated with this swipe event
     */
    private int inputPointer = 0;

    /**
     * The minimum distance between the first and second point in a drawn line.
     */
    public int initialDistance = 10;

    /**
     * The minimum ditance between two points in a drawn line (starting at the second point)
     */
    public int minDistance = 20;

    private Vector2 lastPoint = new Vector2();

    private boolean isDrawing = false;

    private SwipeResolver simplifier = new ResolverRadialChaikin();
    private Array<Vector2> simplified;

    public GestureRecognizerInputProcessor() {
        super();

		/*-----------Gesture Detection------------*/
        recognizer = new ProtractorGestureRecognizer();

        recognizer.addGestureFromFile(Gdx.files.internal("gestures/rectangle.json"));
        recognizer.addGestureFromFile(Gdx.files.internal("gestures/triangle.json"));
        recognizer.addGestureFromFile(Gdx.files.internal("gestures/x.json"));

        originalPath = new ArrayList<Vector2>();

		/*-----------Gesture Path Display------------*/
        int maxInputPoints = 100;
        this.inputPoints = new FixedList<Vector2>(maxInputPoints, Vector2.class);
        simplified = new Array<Vector2>(true, maxInputPoints, Vector.class);
        resolve();
    }

    /**
     * Returns the fixed list of input points (not simplified).
     *
     * @return the list of input points
     */
    public Array<Vector2> input() {
        return this.inputPoints;
    }

    /**
     * Returns the simplified list of points representing this swipe.
     *
     * @return
     */
    public Array<Vector2> path() {
        return simplified;
    }

    /**
     * If the points are dirty, the line is simplified.
     */

    public void resolve() {
        simplifier.resolve(inputPoints, simplified);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        /*-------Gesture Detection-----------*/
        originalPath.add(new Vector2(x, y));

		/*-------Gesture Path Display---------*/
        if (pointer != inputPointer)
            return false;
        isDrawing = true;

        //clear points
        inputPoints.clear();

        //starting point
        lastPoint = new Vector2(x, Gdx.graphics.getHeight() - y);
        inputPoints.insert(lastPoint);

        resolve();
        return true; //return false
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        /*-------Gesture Detection-----------*/
        originalPath.add(new Vector2(x, y));

        /*-------Gesture Path Display---------*/

        if (pointer != inputPointer)
            return false;
        isDrawing = true;

        Vector2 v = new Vector2(x, Gdx.graphics.getHeight() - y);

        //calc length
        float dx = v.x - lastPoint.x;
        float dy = v.y - lastPoint.y;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        //TODO: use minDistanceSq

        //if we are under required distance
        if (len < minDistance && (inputPoints.size > 1 || len < initialDistance))
            return false;

        //add new point
        inputPoints.insert(v);

        lastPoint = v;

        //simplify our new line
        resolve();
        return true; //return false
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        /*-------Gesture Detection-----------*/

        if (originalPath.size() >= 10) {
            originalPath.add(new Vector2(x, y));
            MatchingGesture match = recognizer.Recognize(originalPath);

            if (match.getScore() < 2) {
                Gdx.app.log("Gesture Name/Score", "none matched. " + match.getScore());
            } else {

                Gdx.app.log("Gesture Name/Score", match.getGesture().getName()
                        + Float.toString(match.getScore()));
            }


        }
        originalPath.clear();

        /*-------Gesture Path Display---------*/
        resolve();
        //inputPoints.clear();
        isDrawing = false;

        return false;
    }

}
