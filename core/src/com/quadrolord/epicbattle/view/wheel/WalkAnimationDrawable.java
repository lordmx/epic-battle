package com.quadrolord.epicbattle.view.wheel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.quadrolord.epicbattle.view.SpriteAnimationDrawable;

/**
 * Created by Quadrowin on 10.07.2016.
 */
public class WalkAnimationDrawable extends SpriteAnimationDrawable {

    private Texture mTexture;
    private TextureRegion mTextureRegion;

    public WalkAnimationDrawable(Animation animation, int width, int height, boolean isLooped) throws Exception {
        super(animation, width, height, isLooped);
        throw new Exception("Not implemented");
    }

    public WalkAnimationDrawable(Texture texture) {
        super(null, texture.getWidth(), texture.getHeight(), true);
        mTexture = texture;
        mTextureRegion = new TextureRegion(mTexture);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        float halfWidth = getWidth() / 2;
        float halfHeight = getHeight() / 2;
        batch.draw(
                mTexture,
                -halfWidth, -halfHeight,           // x, y
                halfWidth, halfHeight,  // originX, originY (центр колеса)
                getWidth(), getHeight(), // width, height
                1f, 1f,         // scaleX, scaleY
                -3.14f * getDeltaX(),
                0, 0,           // srcX, srcY
                mTexture.getWidth(), mTexture.getHeight(),  // srcWidth, srcHeight
                false, false    // flipX, flipY
        );
    }

    @Override
    public boolean isAnimationFinished(float stateTime) {
        return false;
    }

    @Override
    public TextureRegion getTexture() {
        return mTextureRegion;
    }

}
