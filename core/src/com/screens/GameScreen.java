package com.screens;

import com.gameworld.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.gestureDetection.GestureRecognizerInputProcessor;

/**
 * Created by yanyee on 2/22/2016.
 */
public class GameScreen implements Screen {

    String tag = "GameScreen";
    GameWorld gameWorld;
    GameRenderer gameRenderer;
    private float runTime;

    public GameScreen() {
        float screenWidth  = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        //float gameHeight = screenHeight / (screenWidth / gameWidth);
        GestureRecognizerInputProcessor  gestureRecognizerInputProcessor = new GestureRecognizerInputProcessor();

        gameWorld = new GameWorld();
        gameRenderer = new GameRenderer(gameWorld, gestureRecognizerInputProcessor);

        Gdx.input.setInputProcessor(gestureRecognizerInputProcessor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.app.log(tag, " render called");
        Gdx.app.log("GameScreen fps", (1 / delta) + " ");
        runTime += delta;

        gameWorld.update(delta);
        gameRenderer.render(runTime);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
