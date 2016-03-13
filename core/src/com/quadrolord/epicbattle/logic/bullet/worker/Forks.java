package com.quadrolord.epicbattle.logic.bullet.worker;

import com.quadrolord.epicbattle.logic.bullet.BulletInfo;
import com.quadrolord.epicbattle.logic.bullet.leveling.SimpleStrategy;
import com.quadrolord.epicbattle.logic.tower.BattleGame;

/**
 * Юнит раздваивается на 2 при смерти
 */
public class Forks extends AbstractBullet {

    private int mStage = 1;

    public Forks(BattleGame game) {
        super(game);
    }

    @Override
    public void onDeath() {
        if (mStage < 2) {
            for (int i = 0; i < 2; i++) {
                Forks child = (Forks)mGame.createUnitEx(mTower, mSkill);
                child.setStage(mStage + 1);
                child.setX(getX() - getVelocity() * 1);
            }
        }

        super.onDeath();
    }

    public void setStage(int stage) {
        mStage = stage;
    }


    @Override
    public void initInfo(BulletInfo info) {
        info.setViewClass(com.quadrolord.epicbattle.view.worker.Forks.class);
        info.setLevelingStrategy(new SimpleStrategy());
    }

}
