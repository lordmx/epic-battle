package com.quadrolord.epicbattle.view.town.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.quadrolord.epicbattle.logic.town.building.BuildingItem;
import com.quadrolord.ejge.view.AbstractScreen;
import com.quadrolord.epicbattle.screen.town.MapGrid;
import com.quadrolord.ejge.view.TextureManager;

/**
 * Created by Quadrowin on 08.03.2016.
 */
public class SmithyView extends AbstractBuildingView {

    public SmithyView(AbstractScreen screen, MapGrid map, BuildingItem building) {
        super(screen, map, building);
    }

    @Override
    public TextureRegion loadBuildingTexture(TextureManager textures) {
        Texture t = textures.get("town/smithy128.png");
        return new TextureRegion(t, 0f, 0f, 1f, 1f);
    }

}
