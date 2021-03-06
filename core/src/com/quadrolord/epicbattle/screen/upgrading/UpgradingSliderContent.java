package com.quadrolord.epicbattle.screen.upgrading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.quadrolord.ejge.view.AbstractScreen;
import com.quadrolord.epicbattle.EpicBattle;
import com.quadrolord.epicbattle.logic.profile.PlayerProfile;
import com.quadrolord.epicbattle.logic.profile.ProfileSkill;
import com.quadrolord.epicbattle.logic.skill.AbstractSkillEntity;
import com.quadrolord.epicbattle.logic.tower.BattleGame;
import com.quadrolord.epicbattle.screen.slider.SliderContent;

/**
 * Created by Quadrowin on 28.06.2016.
 */
public class UpgradingSliderContent extends SliderContent<UpgradingItemData> {

    private EpicBattle mAdapter;

    private AbstractScreen mScreen;

    public UpgradingSliderContent(AbstractScreen screen) {
        mScreen = screen;
        mAdapter = (EpicBattle)screen.getAdapter();
    }

    @Override
    public int getCount() {
        PlayerProfile profile = mAdapter.getProfileManager().getProfile();
        Array<ProfileSkill> skills = profile.getSkills();
        Gdx.app.log("SliderList", "loaded skills: " + skills.size);
        return skills.size;
    }

    @Override
    public UpgradingItemData initButton(TextButton btn, int index) {
        PlayerProfile profile = mAdapter.getProfileManager().getProfile();
        Array<ProfileSkill> skills = profile.getSkills();
        ProfileSkill profileSkill = skills.get(index);

        AbstractSkillEntity skillEntity = mScreen.get(BattleGame.class).getSkillManager().get(profileSkill.getSkillClass());
        String txFile = skillEntity.getIcon();
        if (txFile == null) {
            Gdx.app.error("SliderList", "no slider icon for skill " + skillEntity.getName() + " of class " + profileSkill.getSkillClass().getName());
        }
        Texture tx = mScreen.getTextures().get(txFile);
        Drawable dr = new TextureRegionDrawable(new TextureRegion(tx));

        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle(
                dr,
                dr,
                dr,
                mScreen.getSkin().getFont("default")
        );

        btn.setStyle(tbs);

        UpgradingItemData data = new UpgradingItemData();
        data.profileSkill = profileSkill;

        Label lvlLabel = new Label(
                "lvl " + profileSkill.getLevel(),
                mScreen.getSkin().get("default-label-style", Label.LabelStyle.class)
        );
        lvlLabel.setAlignment(Align.left);
        lvlLabel.setBounds(0, 0, btn.getWidth(), 15);

        data.lvlLabel = lvlLabel;

        btn.addActor(lvlLabel);

        return data;
    }

    public void updateButton(UpgradingItemData data) {
        ProfileSkill profileSkill = data.profileSkill;
        data.lvlLabel.setText("lvl " + profileSkill.getLevel());
    }

}
