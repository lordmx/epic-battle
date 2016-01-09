package com.quadrolord.epicbattle.logic.bullet.worker;

import com.quadrolord.epicbattle.logic.Game;
import com.quadrolord.epicbattle.logic.bullet.BulletInfo;

/**
 * Created by Quadrowin on 09.01.2016.
 */
public class Simple extends AbstractBullet {

    public Simple(Game game) {
        super(game);
    }

    @Override
    public void initInfo(BulletInfo info) {
        info.setInfo(
                "Simple",
                100,
                1000,
                50,
                1,
                1000,
                50,
                1,
                100
        );
    }
}