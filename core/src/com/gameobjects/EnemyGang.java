package com.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yanyee on 2/22/2016.
 */
public class EnemyGang {

    int count;
    ArrayList<Rectangle> enemyGang;
    Rectangle rectangle;

    public EnemyGang(){
        float screenWidth  = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        count = 5;
        //rectangle = new Rectangle(10, 10, 10, 10);
        Random random = new Random();

       enemyGang = new ArrayList<Rectangle>();
        for (int i = 0; i < count; i++) {
            enemyGang.add(new Rectangle(random.nextInt((int)screenWidth), random.nextInt((int)screenHeight)
                    , 200, 200));
        }
    }

    public ArrayList<Rectangle> getEnemyArray() {
        return enemyGang;
    }

    public Rectangle getRect() {
        return rectangle;
    }

//    public void update(float delta) {
//        //destroy shape on touch.
//    }

    public float getX(int index) {
        return enemyGang.get(index).getX();
    }

    public float getY(int index) {
        return enemyGang.get(index).getY();
    }
}
