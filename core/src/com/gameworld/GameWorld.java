package com.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.gameobjects.EnemyGang;


/**
 * Created by yanyee on 2/22/2016.
 */

public class GameWorld {

    private String tag = "GameWorld";
    private EnemyGang enemies;

    public GameWorld() {
        /*
        Initialise game objects here;
         */
      enemies = new EnemyGang();
    }


    public void update(float delta) {
        //enemies.update(delta);
    }

    public EnemyGang getEnemyGang() {
        return enemies;
    }

}
