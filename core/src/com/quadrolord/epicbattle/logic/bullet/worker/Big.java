package com.quadrolord.epicbattle.logic.bullet.worker;

import com.quadrolord.epicbattle.logic.bullet.BulletInfo;
import com.quadrolord.epicbattle.logic.bullet.leveling.SimpleStrategy;
import com.quadrolord.epicbattle.logic.tower.BattleGame;

/**
 * Created by Quadrowin on 09.01.2016.
 */
public class Big extends AbstractBullet {

    public Big(BattleGame game) {
        super(game);
    }

    @Override
    public void initInfo(BulletInfo info) {
        info.setViewClass(com.quadrolord.epicbattle.view.worker.Big.class);
        info.setLevelingStrategy(new SimpleStrategy());
    }
}
