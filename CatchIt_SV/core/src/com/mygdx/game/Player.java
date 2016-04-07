package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by venujan on 02/04/16.
 */
public class Player {

    Texture sprite;
    int xCoordinates;
    int yCoordinates;

    //setting movements for player
    public void setLeftMove(boolean leftMove) {
        this.leftMove = leftMove;
    }

    public void setRightMove(boolean rightMove) {
        this.rightMove = rightMove;
    }

    boolean leftMove;
    boolean rightMove;


    public Player( ) {
        super();
        //loading player picture
        sprite = new Texture("core/assets/rsz_chansey.png");

    }
    //movement results
    void updateMotion() {

        if (leftMove)
        {
            this.xCoordinates -= 800 * Gdx.graphics.getDeltaTime();
        }
        if (rightMove)
        {
            this.xCoordinates += 800 * Gdx.graphics.getDeltaTime();
        }

        if ( this.xCoordinates < 0) {
            this.xCoordinates = 0;
        }
        if ( this.xCoordinates  > Gdx.graphics.getWidth() - this.getTexture().getWidth() ) {

            this.xCoordinates = Gdx.graphics.getWidth() - this.getTexture().getWidth();
        }

    }
    void draw(SpriteBatch batch) {

        batch.draw(this.getTexture(), xCoordinates, yCoordinates);

    }

    public Rectangle getRectangle() {

        return new Rectangle(xCoordinates, yCoordinates, this.getTexture().getWidth(),this.getTexture().getHeight()
        );
    }
    Texture  getTexture(){
        return sprite;
    }
}
