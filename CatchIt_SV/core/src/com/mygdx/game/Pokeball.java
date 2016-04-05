package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by venujan on 04/04/16.
 */
public class Pokeball {

    Texture sprite;
    int xCoordinates;
    int yCoordinates;
    int speed;

    public void setLeftMove(boolean leftMove) {
        this.leftMove = leftMove;
    }

    public void setRightMove(boolean rightMove) {
        this.rightMove = rightMove;
    }

    boolean leftMove;
    boolean rightMove;


    public Pokeball ( ) {
        super();
        sprite = new Texture("pokeball.png");
        xCoordinates = new Random().nextInt(Gdx.graphics.getWidth() - this.getTexture().getWidth());
        yCoordinates = Gdx.graphics.getHeight() - this.getTexture().getHeight();
        speed = new Random().nextInt(400)+ 200;
    }
    void physics() {

        this.yCoordinates-= speed * Gdx.graphics.getDeltaTime();
//        System.out.println("Y coordinates " + this.yCoordinates);
//
//        System.out.println("X Coordinates " + this.xCoordinates);

        if ( this.yCoordinates <=  0 ) {

            recycleObject();
        }

    }
    void recycleObject() {
        xCoordinates = new Random().nextInt(Gdx.graphics.getWidth() - this.getTexture().getWidth());
        System.out.println("Ball Coordinates " + xCoordinates);
        yCoordinates = Gdx.graphics.getHeight() - this.getTexture().getHeight();
        speed = new Random().nextInt(500)+ 200;

    }
    void draw(SpriteBatch batch) {

        batch.draw(this.getTexture(), xCoordinates, yCoordinates);

    }
}