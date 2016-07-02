package com.quadrolord.epicbattle.logic.skill;

import com.quadrolord.ejge.entity.AbstractEntity;
import com.quadrolord.epicbattle.logic.tower.BattleGame;
import com.quadrolord.epicbattle.logic.tower.Tower;

/**
 * Общая логика и описание скила.
 * Created by Quadrowin on 14.01.2016.
 */
abstract public class AbstractSkillEntity extends AbstractEntity<SkillItem> {

    private String mDescription;
    private String mIcon;
    private String mName;

    public String getDescription() {
        return mDescription;
    }


    /**
     * Иконка (для активных скилов)
     * @return Путь до файла с иконкой
     */
    public String getIcon() {
        return mIcon;
    }

    public String getName() {
        return mName;
    }

    @Override
    public Class<? extends SkillItem> getItemClass() {
        return SkillItem.class;
    }

    @Override
    public void initItem(SkillItem item) {

    }

    public void initSkill(BattleGame game)
    {

    }

    public void act(SkillItem skill, float delta) {

    }

    /**
     * Инициализация башни в начале уровня
     * @param tower
     */
    public void initTower(SkillItem skill, Tower tower) {

    }

    /**
     * Использование скила в битве. Только для активных скилов.
     */
    public void use(SkillItem skill) {

    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public void setName(String name) {
        mName = name;
    }

}
