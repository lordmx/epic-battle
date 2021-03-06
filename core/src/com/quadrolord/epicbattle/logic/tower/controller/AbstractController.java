package com.quadrolord.epicbattle.logic.tower.controller;

import com.quadrolord.epicbattle.logic.tower.BattleGame;
import com.quadrolord.epicbattle.logic.tower.Tower;

/**
 * Created by Quadrowin on 12.01.2016.
 */
abstract public class AbstractController {

    private BattleGame mGame;

    private Tower mTower;

    public AbstractController(BattleGame game) {
        mGame = game;
    }

    abstract public void act(float delta);

    public BattleGame getGame() {
        return mGame;
    }

    public Tower getTower() {
        return mTower;
    }

    abstract public void reset();

    public void setTower(Tower tower) {
        mTower = tower;
    }

}
