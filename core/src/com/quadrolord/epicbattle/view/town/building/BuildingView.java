package com.quadrolord.epicbattle.view.town.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.quadrolord.epicbattle.logic.town.building.AbstractBuildingItem;
import com.quadrolord.epicbattle.screen.AbstractScreen;
import com.quadrolord.epicbattle.screen.town.MapGrid;

/**
 * Created by morph on 17.01.2016.
 */
public class BuildingView extends Group {

    private AbstractBuildingItem mBuilding;

    private Texture mBuildingTexture;

    private MapGrid mMap;

    public BuildingView(AbstractScreen screen, MapGrid map, AbstractBuildingItem building) {
        mBuilding = building;
        mMap = map;

        mBuildingTexture = new Texture("town/mine1.png");

        map.setChildPosition(this, building.getX(), building.getY());
        map.addActor(this);
    }

    @Override
    public void act(float delta) {
        mMap.setChildPosition(this, mBuilding.getX(), mBuilding.getY());
    }

    public void draw (Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        drawBuilding(batch);
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }

    public void drawBuilding(Batch batch) {
        float csx = mMap.getCellSideX();
        float csy = mMap.getCellSideY();
        batch.draw(
                mBuildingTexture,
                0, 0, csx * mBuilding.getSize().x, csy * mBuilding.getSize().y,
                0f, 1f, 1f, 0f
        );
    }

    public AbstractBuildingItem getBuilding() {
        return mBuilding;
    }

    public Texture getBuildingTexture() {
        return mBuildingTexture;
    }

    public MapGrid getMap() {
        return mMap;
    }

    public void setBuildingTexture(Texture texture) {
        mBuildingTexture = texture;
    }

}
