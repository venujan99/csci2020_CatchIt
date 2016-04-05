package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by venujan on 04/04/16.
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
        sprite = new Texture("playerCopy.png");

    }
    void updateMotion() {

        if (leftMove)
        {
            System.out.println("Left move " + this.xCoordinates );
            this.xCoordinates -= 800 * Gdx.graphics.getDeltaTime();
        }
        if (rightMove)
        {
            System.out.println("right move " + this.xCoordinates );
            this.xCoordinates += 800 * Gdx.graphics.getDeltaTime();
        }

        if ( this.xCoordinates < 0) {
            this.xCoordinates = 0;
        }
        if ( this.xCoordinates  > Gdx.graphics.getWidth() - this.getTexture().getWidth() ) {
            System.out.println("bad");
            this.xCoordinates = Gdx.graphics.getWidth() - this.getTexture().getWidth();
        }

    }
    void draw(SpriteBatch batch) {

        batch.draw(this.getTexture(), xCoordinates, yCoordinates);

    }
}
