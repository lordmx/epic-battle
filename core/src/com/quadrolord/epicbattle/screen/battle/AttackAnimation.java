package com.quadrolord.epicbattle.screen.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.quadrolord.epicbattle.logic.GameUnit;

/**
 * Created by Quadrowin on 10.01.2016.
 */
public class AttackAnimation extends Group {

    private Animation mAnim;

    private float mStateTime = 0;

    public AttackAnimation(GameUnit from, GameUnit to, Skin skin, Stage stage) {
        mAnim = getAnimation1(skin);
        setBounds(from.getX(), 20, 30, 30);
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        mStateTime += delta;
        if (mAnim.isAnimationFinished(mStateTime)) {
            remove();
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        TextureRegion frame = mAnim.getKeyFrame(mStateTime);
        batch.draw(frame, getX(), getY(), 30, 30);
    }

    private Animation getAnimation1(Skin skin) {
        if (skin.has("cut-animation-1", Animation.class)) {
            return skin.get("cut-animation-1", Animation.class);
        }
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture("animation/cut_a/cut_a_0001.png")));
        frames.add(new TextureRegion(new Texture("animation/cut_a/cut_a_0002.png")));
        frames.add(new TextureRegion(new Texture("animation/cut_a/cut_a_0003.png")));
        frames.add(new TextureRegion(new Texture("animation/cut_a/cut_a_0004.png")));
        frames.add(new TextureRegion(new Texture("animation/cut_a/cut_a_0005.png")));
        Animation anim = new Animation(0.1f, frames);
        skin.add("cut-animation-1", anim);
        return anim;
    }

}
