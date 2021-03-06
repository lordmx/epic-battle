package com.quadrolord.epicbattle.logic.town;

import com.quadrolord.epicbattle.logic.thing.ThingCostElement;
import com.quadrolord.epicbattle.logic.town.building.AbstractBuildingEntity;
import com.quadrolord.epicbattle.logic.town.building.BuildingItem;
import com.quadrolord.epicbattle.logic.town.building.CraftPlanItem;
import com.quadrolord.epicbattle.logic.town.listener.OnThingAddToPlan;

/**
 * Created by Quadrowin on 31.01.2016.
 */
public interface TownListener extends OnThingAddToPlan {

    enum BuildingAction {
        /**
         * Недостаточно гемов
         */
        CREATE_NO_GEMS,

        /**
         * Недостаточно ресурсов
         */
        CREATE_NO_RESOURCES,
        CREATE_NO_LEVEL,
        CREATE_NO_PLACE,

        // нельзя переместить в указанное место
        MOVE_NO_PLACE,

        LEVEL_NO_RESOURCES,
        LEVEL_IN_UPDATING,
        LEVEL_BAD_USER_LEVEL,
        LEVEL_ALREADY_MAX,
    }

    void onBuildingAdd(BuildingItem building);

    void onBuildingRemove(BuildingItem building);

    void onBuildingSelect(BuildingItem building);

    void onBuildingConstructed(BuildingItem building);

    void onBuildingUpgraded(BuildingItem building);

    void onCancelBuildingMode();

    void onConfirmBuilding();

    void onConfirmMoving();

    void onEnterBuildingMode(AbstractBuildingEntity building);

    void onEnterBuildingMode(BuildingItem building);

    void onUserActionFail(BuildingAction action);

    /**
     * Недостаток ресурсов при попытке заказе
     * @param cost
     */
    void onOrderResourceLack(ThingCostElement cost);

    void onThingAddToPlan(BuildingItem building, CraftPlanItem plan);

}
