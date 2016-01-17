package com.quadrolord.epicbattle.logic.town.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by morph on 17.01.2016.
 */
public abstract class Tile {
    protected Vector2 mPosition;
    protected Texture mTexture;
    protected TileView mView;

    public Tile() {

    }

    public Tile(int x, int y) {
        this();
        mPosition = new Vector2(x, y);
    }

    public Tile(Vector2 position) {
        this();
        mPosition = position;
    }

    public void setPosition(int x, int y) {
        mPosition.set(x, y);
    }

    public void setPosition(Vector2 position) {
        mPosition = position;
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public Texture getTexture() {
        return mTexture;
    }

    public void setTexture(Texture texture) {
        mTexture = texture;
    }

    public int getX() {
        return (int)mPosition.x;
    }

    public int getY() {
        return (int)mPosition.y;
    }

    public void setX(int x) {
        mPosition.x = x;
    }

    public void setY(int y) {
        mPosition.y = y;
    }

    public TileView getView() {
        return mView;
    }
}
