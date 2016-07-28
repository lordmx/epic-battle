package com.quadrolord.epicbattle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.quadrolord.ejge.AbstractGameAdapter;
import com.quadrolord.ejge.view.AbstractScreen;
import com.quadrolord.epicbattle.logic.bullet.worker.AbstractBullet;
import com.quadrolord.epicbattle.logic.bullet.worker.AbstractLogic;
import com.quadrolord.epicbattle.logic.bullet.worker.BulletState;
import com.quadrolord.epicbattle.logic.bullet.worker.MockBullet;
import com.quadrolord.epicbattle.logic.profile.ProfileSkill;
import com.quadrolord.epicbattle.logic.skill.AbstractBulletSkill;
import com.quadrolord.epicbattle.logic.skill.AbstractSkillEntity;
import com.quadrolord.epicbattle.logic.tower.BattleGame;
import com.quadrolord.epicbattle.screen.slider.SliderList;
import com.quadrolord.epicbattle.screen.slider.SliderListener;
import com.quadrolord.epicbattle.screen.unitstest.UnitTestSliderContent;
import com.quadrolord.epicbattle.screen.upgrading.UpgradingItemData;
import com.quadrolord.epicbattle.screen.upgrading.UpgradingSliderContent;
import com.quadrolord.epicbattle.view.BulletUnitView;
import com.quadrolord.epicbattle.view.SpriteAnimationDrawable;

/**
 * Created by Goorus on 20.07.2016.
 */
public class UnitsTestScreen extends AbstractScreen {

    private Label mCuName;

    private Label mCuState;

    private MockBullet mBullet;

    private Group mBulletWrapper;

    private BulletUnitView mCurrentView;

    private int mCurrentStateIndex = 0;

    private BulletState[] mSwitchingStates = new BulletState[]{
            BulletState.WALK,
            BulletState.ATTACK,
            BulletState.DEATH,
    };

    public UnitsTestScreen(AbstractGameAdapter adapter) {
        super(adapter);
        initFitViewport();

        mBullet = new MockBullet(get(BattleGame.class));
        mBullet.setY(0);
        mBullet.setX(mStage.getWidth() / mPx / 2);

        mBulletWrapper = new Group();
        mBulletWrapper.setBounds(0, 200, mStage.getWidth() / mPx, 100);
        mStage.addActor(mBulletWrapper);

        mCuName = new Label("", mSkin.get("default-label-style", Label.LabelStyle.class));
        mCuName.setBounds(10, 270, 380, 30);
        getStage().addActor(mCuName);

        mCuState = new Label("", mSkin.get("default-label-style", Label.LabelStyle.class));
        mCuState.setBounds(10, 240, 380, 30);
        getStage().addActor(mCuState);

        final UnitTestSliderContent usc = new UnitTestSliderContent(this);
        usc.setSliderListener(new SliderListener<AbstractLogic>() {

            @Override
            public void onSelect(TextButton btn, AbstractLogic data) {
                setUnitView(data);
            }

        });

        final SliderList sl = new SliderList(this, usc);
        sl.triggerCurrentButtonClick();

        mBulletWrapper.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("uts", "view clicked");
                if (mCurrentView != null) {
                    switchAnimation();
                }
            }

        });
    }

    private void setUnitView(AbstractLogic logic) {
        float previousWidth = mBullet.getWidth();
        mBullet.setLogic(logic);
        mCuName.setText( logic.getTitle() );
        Class<? extends BulletUnitView> viewClass = logic.getViewClass();

        mBullet.setX(mBullet.getX() + (previousWidth - mBullet.getWidth()) / 2);

        if (mCurrentView != null) {
            mCurrentView.remove();
        }

        try {
            mCurrentView = viewClass.getConstructor(AbstractBullet.class, com.quadrolord.ejge.view.AbstractScreen.class).newInstance(mBullet, this);
        } catch (Exception e) {
            Gdx.app.error("uts", "view create " + viewClass.getName(), e.getCause());
            mCurrentView = new BulletUnitView(mBullet, this) {

                @Override
                protected void initAnimations(com.quadrolord.ejge.view.AbstractScreen screen) {

                }

            };
        }

        mCurrentView.setSize(mBullet.getWidth(), mBullet.getHeight());
        mBulletWrapper.addActor(mCurrentView);

        setStateAnimation(mCurrentStateIndex);
    }

    private void setStateAnimation(int stateIndex) {
        mCurrentStateIndex = stateIndex;
        BulletState newState = mSwitchingStates[mCurrentStateIndex];
        Gdx.app.log("uts", "new state " + newState.name());
        float dur = mCurrentView.getAnimation(newState).getBaseDuration();
        mBullet.setState(newState, dur);
        mCuState.setText(newState.name());
    }

    private void switchAnimation() {
        setStateAnimation( (mCurrentStateIndex + 1) % mSwitchingStates.length );
    }

    @Override
    public void update(float delta) {
        mBullet.act(delta);
        mBullet.moveX(delta, mBulletWrapper.getWidth());
//        mCurrentView.setX(mBullet.getX() - mBullet.getWidth() * .5f - delta * 10);
    }

}
