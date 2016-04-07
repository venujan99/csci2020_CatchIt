package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by venujan on 07/04/16.
 */
public class Egg {
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


    public Egg ( ) {
        super();
        sprite = new Texture("core/assets/rsz_egg.png");
        xCoordinates = new Random().nextInt(Gdx.graphics.getWidth() - this.getTexture().getWidth());
        yCoordinates = Gdx.graphics.getHeight() - this.getTexture().getHeight();
        // speed of eggs falling down
        speed = new Random().nextInt(150)+ 150;
    }
    void physics() {

        this.yCoordinates-= speed * Gdx.graphics.getDeltaTime();

        if ( this.yCoordinates <=  0 ) {

            recycleObject();
        }

    }
    void recycleObject() {
        xCoordinates = new Random().nextInt(Gdx.graphics.getWidth() - this.getTexture().getWidth());
        yCoordinates = Gdx.graphics.getHeight() - this.getTexture().getHeight();
        speed = new Random().nextInt(150)+ 150;

    }
    void draw(SpriteBatch batch) {

        batch.draw(this.getTexture(), xCoordinates, yCoordinates);

    }
    //checking for in game collision
    boolean isColliding(Player object) {
        Rectangle myRect = this.getRectangle();
        Rectangle theirRect = object.getRectangle();
        return theirRect.overlaps(myRect);

    }

    Rectangle getRectangle() {

        return new Rectangle(xCoordinates, yCoordinates, this.getTexture().getWidth(),this.getTexture().getHeight()
        );
    }
    Texture  getTexture(){
        return sprite;
    }
}
