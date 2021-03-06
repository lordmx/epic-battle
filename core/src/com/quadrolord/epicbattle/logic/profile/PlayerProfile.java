package com.quadrolord.epicbattle.logic.profile;

import com.badlogic.gdx.utils.Array;
import com.quadrolord.epicbattle.logic.skill.AbstractSkillEntity;
import com.quadrolord.epicbattle.logic.town.building.BuildingItem;

import java.util.Iterator;

/**
 * Created by Quadrowin on 14.01.2016.
 */
public class PlayerProfile {

    /**
     * Кол-во запусков битв
     */
    private int battlesStarted = 0;

    /**
     * Кол-во побед
     */
    private int battlesVictory = 0;

    /**
     * Кол-во поражений
     */
    private int battlesDefeat = 0;

    /**
     * Текущий опыт
     */
    private long experience = 0;

    /**
     * Всего получено опыта
     */
    private long experienceTotal = 0;

    private String name;

    private Array<ProfileSkill> skills = new Array<ProfileSkill>();

    private Array<ProfileBuilding> buildings = new Array<ProfileBuilding>();

    public Array<ProfileBuilding> getBuildings() {
        return buildings;
    }

    public ProfileBuilding addBuilding(Class<? extends BuildingItem> buildingClass) {
        ProfileBuilding building = new ProfileBuilding();
        building.setBuildingClass(buildingClass);
        buildings.add(building);
        return building;
    }

    public ProfileBuilding addBuildingSafe(Class<? extends BuildingItem> buildingClass) {
        if (hasBuilding(buildingClass)) {
            return null;
        }
        return addBuilding(buildingClass);
    }

    public ProfileSkill addSkillSafe(Class<? extends AbstractSkillEntity> skillClass, int level) {
        if (hasSkill(skillClass)) {
            return null;
        }
        return addSkill(skillClass, level);
    }

    public ProfileSkill addSkill(Class<? extends AbstractSkillEntity> skillClass, int level) {
        ProfileSkill sk = new ProfileSkill();
        sk.setSkillClass(skillClass);
        sk.setLevel(level);
        skills.add(sk);
        return sk;
    }

    public long decExperience(long value) {
        return experience -= value;
    }

    public long getExperience() {
        return experience;
    }

    public long getExperienceTotal() {
        return experienceTotal;
    }

    public boolean hasBuilding(Class<? extends BuildingItem> buildingClass) {
        String buildingClassName = buildingClass.getName();
        for (Iterator<ProfileBuilding> it = buildings.iterator(); it.hasNext(); ) {
            ProfileBuilding pb = it.next();
            if (pb.getBuildingName().equals(buildingClassName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSkill(Class<? extends AbstractSkillEntity> skillClass) {
        for (Iterator<ProfileSkill> it = skills.iterator(); it.hasNext(); ) {
            ProfileSkill sk = it.next();
            if (sk.getSkillClass() == skillClass) {
                return true;
            }
        }
        return false;
    }

    public long incExperience(long value) {
        experienceTotal += value;
        return experience += value;
    }

    public String getName() {
        return name;
    }

    public Array<ProfileSkill> getSkills() {
        return skills;
    }

    public void setName(String value) {
        name = value;
    }

    public void setSkills(Array<ProfileSkill> value) {
        skills = value;
    }

}
