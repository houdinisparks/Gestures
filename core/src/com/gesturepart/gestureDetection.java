package com.gesturepart;

import com.badlogic.gdx.Game;
import com.screens.GameScreen;

/**
 * Created by yanyee on 3/2/2016.
 */
public class gestureDetection extends Game {
    @Override
    public void create() {
        //this part if where the code runs, or initialise. its
        //like the onCreate of android apps.
        //load the game images first before setting up the screen.

        setScreen(new GameScreen());
    }
}
