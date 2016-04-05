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
}
