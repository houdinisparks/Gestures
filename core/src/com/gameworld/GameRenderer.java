package com.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gestureDetection.GestureRecognizerInputProcessor;
import com.gesturepath.swipe.mesh.SwipeTriStrip;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by yanyee on 2/22/2016.
 */
public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private String tag = "GameRenderer";
    private GestureRecognizerInputProcessor gestureRecognizerInputProcessor;
    private Texture texture;
    private SpriteBatch batch;
    private SwipeTriStrip swipeTriStrip;

    private Rectangle gameObject;

    public GameRenderer(GameWorld world , GestureRecognizerInputProcessor gestureRecognizerInputProcessor) {

        int screenWidth  = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        this.gestureRecognizerInputProcessor = gestureRecognizerInputProcessor;
        myWorld = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(false ,screenWidth  , screenHeight);

        swipeTriStrip = new SwipeTriStrip();
        this.gestureRecognizerInputProcessor.minDistance = 10;
        this.gestureRecognizerInputProcessor.minDistance = 10;
        texture = new Texture(Gdx.files.internal("gesturepath/gradient.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        gameObject = world.getEnemyGang().getRect();

    }

    public void render(float runTime) {

        Gdx.app.log(tag, "rendering");
        /*
        Draw a black background.
         */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        try {
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(255.0f, 255.0f, 255.0f, 1);
//
//            Gdx.app.log(tag, "before rect rendering");
//            shapeRenderer.rect(0, 10f, 10f, 10f);
//            Gdx.app.log(tag, "after rect rendering");
//            shapeRenderer.end();
//        } catch (Exception e) {
//            Gdx.app.log(tag, "exception caught");
//
//            e.printStackTrace();
//        }

        texture.bind();
        swipeTriStrip.endcap = 5f;
        swipeTriStrip.thickness = 30f;
        swipeTriStrip.update(gestureRecognizerInputProcessor.path());
        swipeTriStrip.color = Color.WHITE;
        swipeTriStrip.draw(camera);


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(255.0f, 255.0f, 255.0f, 1);
//
//        Gdx.app.log(tag, "before rect rendering");
//        //shapeRenderer.rect(0, 10f, 10f, 10f);
//        Gdx.app.log(tag, "after rect rendering");
//
//
//
//        EnemyGang enemyGang = myWorld.getEnemyGang();
//        ArrayList<Rectangle> listOfRectangles= enemyGang.getEnemyArray();
//
//        try {
//
//            for (Rectangle rectangle :listOfRectangles) {
//                shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width,
//                        rectangle.height);
//            }
//
//            shapeRenderer.end();
//
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.setColor(255 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);
//
//            for (Rectangle rectangle : listOfRectangles) {
//                shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width,
//                        rectangle.height);
//            }
//
//
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        shapeRenderer.end();

        //EnemyGang = gameWorld.getEnemyGang.
        //Draw enemy gang.getx,gety,get use a for loop to draw the array
        //of triangles.


    }

}
