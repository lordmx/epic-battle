package com.quadrolord.epicbattle.logic.town;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.quadrolord.epicbattle.EpicBattle;
import com.quadrolord.epicbattle.logic.profile.PlayerProfile;
import com.quadrolord.epicbattle.logic.profile.ProfileBuilding;
import com.quadrolord.epicbattle.logic.thing.AbstractThingEntity;
import com.quadrolord.epicbattle.logic.thing.ThingCost;
import com.quadrolord.epicbattle.logic.thing.ThingCostElement;
import com.quadrolord.epicbattle.logic.thing.ThingItem;
import com.quadrolord.epicbattle.logic.thing.entity.Gemstone;
import com.quadrolord.epicbattle.logic.town.building.AbstractBuildingEntity;
import com.quadrolord.epicbattle.logic.town.building.BuildingItem;
import com.quadrolord.epicbattle.logic.town.building.CraftPlanItem;
import com.quadrolord.epicbattle.logic.town.building.entity.DoodleShop;
import com.quadrolord.epicbattle.logic.town.building.entity.IronMine;
import com.quadrolord.epicbattle.logic.town.building.entity.SheepFarm;
import com.quadrolord.epicbattle.logic.town.building.entity.Smithy;
import com.quadrolord.epicbattle.logic.town.building.entity.Warehouse;
import com.quadrolord.epicbattle.logic.town.tile.Tile;

import java.util.Iterator;

/**
 * Created by Quadrowin on 16.01.2016.
 */
public class MyTown {

    public static final int MAP_CELL_WIDTH = 60;
    public static final int MAP_CELL_HEIGHT = 40;

    public static final int MAP_SIZE_X = 10;
    public static final int MAP_SIZE_Y = 10;

    private EpicBattle mGame;

    private Array<BuildingItem> mBuildings = new Array<BuildingItem>();
    private Tile[][] mMap;

    private int mLevel = 1;
    private int mGemsCount = 1;

    private ArrayMap<Class<? extends AbstractThingEntity>, ThingItem> mResources = new ArrayMap<Class<? extends AbstractThingEntity>, ThingItem>();
    private BuildingInfoManager mBuildingInfoManager;

    private TownListener mListener;

    private float mTime = 0;

    /**
     * Номер тика для периодического запуска несрочных тяжелых операций
     */
    private int mTickNumber = 0;

    public MyTown(EpicBattle game) {
        mGame = game;
        mBuildingInfoManager = new BuildingInfoManager();
    }

    public void act(float delta) {
        mTime += delta;
        mTickNumber = (mTickNumber + 1) % 1000000000;

        if (mTickNumber % 11 == 0) {
            actBuildings();
        }
    }

    public void actBuildings() {
        for (Iterator<BuildingItem> it = mBuildings.iterator(); it.hasNext(); ) {
            BuildingItem building = it.next();
            if (building.isInConstruction() && building.getConstructionProgress() >= 1) {
                building.finishConstruction();
                mListener.onBuildingConstructed(building);
            }
            if (building.isInUpgrading() && building.getUpgradingProgress() >= 1) {
                building.upgradingSuccess();
                mListener.onBuildingUpgraded(building);
            }
        }
    }

    /**
     * Возвращает типы зданий, которые можно построить
     * @return
     */
    public Array<AbstractBuildingEntity> getAvailableBuildingTypes() {
        Array<AbstractBuildingEntity> bts = new Array<AbstractBuildingEntity>();
        bts.add(mBuildingInfoManager.getInfo(DoodleShop.class));
        bts.add(mBuildingInfoManager.getInfo(IronMine.class));
        bts.add(mBuildingInfoManager.getInfo(SheepFarm.class));
        bts.add(mBuildingInfoManager.getInfo(DoodleShop.class));
        bts.add(mBuildingInfoManager.getInfo(Smithy.class));
        bts.add(mBuildingInfoManager.getInfo(Warehouse.class));
        return bts;
    }

    public Array<BuildingItem> getBuildings() {
        return mBuildings;
    }

    public BuildingInfoManager getBuildingInfoManager() {
        return mBuildingInfoManager;
    }

    public Tile getMapCell(int col, int row) {
        Gdx.app.log("getMapCell", " " + mMap.length + " " + mMap[col].length + " at " + col + " " + row);
        if (col < 0 || row < 0 || col >= mMap.length || row >= mMap[col].length) {
            return null;
        }
        return mMap[col][row];
    }

    public boolean hasGems(int gemsCount) {
        return mGemsCount >= gemsCount;
    }

    public boolean hasLevel(int level) {
        return mLevel >= level;
    }

    public boolean hasResources(ThingCost cost) {
        Iterator<ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer>> iter = cost.getResources().iterator();

        while (iter.hasNext()) {
            ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer> next = iter.next();
            Class<? extends AbstractThingEntity> resourceClass = next.key;
            int costValue = next.value;

            if (!mResources.containsKey(resourceClass) || mResources.get(resourceClass).getCount() < costValue) {
                return false;
            }
        }

        return true;
    }

    public boolean canBuild(AbstractBuildingEntity entity, int col, int row) {
        if (col < 0 || row < 0) {
            return false;
        }
        int max_x = col + (int)entity.getSize().x - 1;
        int max_y = row + (int)entity.getSize().y - 1;
        if (max_x >= mMap.length || max_y >= mMap[max_x].length) {
            return false;
        }
        for (int i = col; i <= max_x; i++) {
            for (int j = row; j <= max_y; j++) {
                if (mMap[i][j] != null && !mMap[i][j].isFree()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canBuildItem(BuildingItem building, int col, int row) {
        if (col < 0 || row < 0 || col >= mMap.length || row >= mMap[col].length) {
            return false;
        }
        Tile cell = mMap[col][row];
        if (cell == null) {
            return true;
        }
        return cell.isFree() || cell.getBuilding() == building;
    }

    public void cancelBuildingMode() {
        mListener.onCancelBuildingMode();
    }

    /**
     * Подтверждение строительства здания
     */
    public void confirmBuilding() {
        mListener.onConfirmBuilding();
    }

    /**
     * Подтверждение мерещмения здания
     */
    public void confirmMoving() {
        mListener.onConfirmMoving();
    }

    public BuildingItem build(Class<? extends AbstractBuildingEntity> entityClass, int col, int row, boolean isRotated, boolean takeResources, boolean takeGems) {
        return build(mBuildingInfoManager.getInfo(entityClass), col, row, isRotated, takeResources, takeGems);
    }

    public BuildingItem build(AbstractBuildingEntity entity, int col, int row, boolean isRotated, boolean takeResources, boolean takeGems) {
        ThingCost cost = getBuildingCost(entity, takeResources, takeGems);

        if (!hasResources(cost)) {
            if (takeGems) {
                mListener.onUserActionFail(TownListener.BuildingAction.CREATE_NO_GEMS);
            } else {
                mListener.onUserActionFail(TownListener.BuildingAction.CREATE_NO_RESOURCES);
            }
            return null;
        }

        if (!hasLevel(entity.getRequiredLevel())) {
            mListener.onUserActionFail(TownListener.BuildingAction.CREATE_NO_LEVEL);
            return null;
        }

        if (!canBuild(entity, col, row)) {
            mListener.onUserActionFail(TownListener.BuildingAction.CREATE_NO_PLACE);
            return null;
        }

        takeAwayResources(cost);

        BuildingItem item = instantiateBuilding(entity);
        item.setPosition(col, row);

        if (isRotated && !item.isRotated()) {
            item.rotate();
        }

        mBuildings.add(item);
        if (takeResources || takeGems) {
            item.startConstruction(entity, cost);
        }

        for (int i = col; i < col + item.getWidth(); i++) {
            for (int j = row; j < row + item.getHeight(); j++) {
                if (mMap[i][j] == null) {
                    mMap[i][j] = new Tile();
                }
                mMap[i][j].markAsBusy(item);
            }
        }

        mListener.onBuildingAdd(item);
        return item;
    }

    public void enterBuildingMode(AbstractBuildingEntity be) {
        mListener.onEnterBuildingMode(be);
    }

    public void enterMovingMode(BuildingItem b) {
        mListener.onEnterBuildingMode(b);
    }

    public ThingCost getBuildingCost(AbstractBuildingEntity building, boolean takeResources, boolean takeGems) {
        ThingCost cost;
        if (takeGems) {
            ArrayMap<Class<? extends AbstractThingEntity>, Integer> res = new ArrayMap<Class<? extends AbstractThingEntity>, Integer>();
            res.put(Gemstone.class, building.getCostGem());
            cost = new ThingCost(res);
        } else if (takeResources) {
            cost = new ThingCost( building.getRequiredResources() );
        } else {
            cost = new ThingCost( new ArrayMap<Class<? extends AbstractThingEntity>, Integer>() );
        }
        return cost;
    }

    public ThingItem getResource(Class<? extends AbstractThingEntity> resourceClass) {
        if (!mResources.containsKey(resourceClass)) {
            ThingItem resource = new ThingItem();

            AbstractThingEntity info;
            try {
                info = resourceClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            resource.setInfo(info);

            mResources.put(resourceClass, resource);
            return resource;
        }
        return mResources.get(resourceClass);
    }

    public ArrayMap<Class<? extends AbstractThingEntity>, ThingItem> getResources() {
        return mResources;
    }

    public BuildingItem instantiateBuilding(AbstractBuildingEntity buildingInfo) {
        BuildingItem item;
        try {
            item = (BuildingItem)buildingInfo.getItemClass().getConstructor(MyTown.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        item.setInfo(buildingInfo);
        buildingInfo.initItem(item);
        return item;
    }

    public void setListener(TownListener listener) {
        mListener = listener;
    }

    /**
     * Забор ресурсов за постройку
     * @param cost
     */
    public void takeAwayResources(ThingCost cost) {
        Iterator<ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer>> iter = cost.getResources().iterator();

        while (iter.hasNext()) {
            ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer> next = iter.next();
            Class<? extends AbstractThingEntity> resourceClass = next.key;
            int costValue = next.value;

            if (mResources.containsKey(resourceClass)) {
                ThingItem res = mResources.get(resourceClass);
                res.incCount(-costValue);
            }
        }
    }

    public void levelUp(BuildingItem building, boolean isByGems) {
        AbstractBuildingEntity info = building.getInfo();
        AbstractBuildingEntity nextLevel = mBuildingInfoManager.getEntityLevel(info.getClass(), info.getLevel() + 1);
        ThingCost cost = getBuildingCost(nextLevel, !isByGems, isByGems);

        if (!hasResources(cost)) {
            if (isByGems) {
                mListener.onUserActionFail(TownListener.BuildingAction.LEVEL_NO_RESOURCES);
            } else {
                mListener.onUserActionFail(TownListener.BuildingAction.LEVEL_NO_RESOURCES);
            }
            return;
        }

        if (building.isInConstruction() || building.isInUpgrading()) {
            mListener.onUserActionFail(TownListener.BuildingAction.LEVEL_IN_UPDATING);
            return;
        }

        if (!hasLevel(info.getRequiredLevel())) {
            mListener.onUserActionFail(TownListener.BuildingAction.LEVEL_BAD_USER_LEVEL);
            return;
        }

        if (!building.getInfo().getLevelingStrategy().canLevelUp(building)) {
            mListener.onUserActionFail(TownListener.BuildingAction.LEVEL_ALREADY_MAX);
            return;
        }

        takeAwayResources(cost);
        building.startUpgrading(nextLevel, cost);
    }

    public void loadTown() {
        PlayerProfile profile = mGame.getProfileManager().getProfile();

        if (mMap == null) {
            mMap = new Tile[MAP_SIZE_X][MAP_SIZE_Y];
        } else {
            for (int x = 0; x < mMap.length; x++) {
                for (int y = 0; y < mMap[x].length; y++) {
                    mMap[x][y] = null;
                }
            }
        }

        for (Iterator<ProfileBuilding> it = profile.getBuildings().iterator(); it.hasNext(); ) {
            ProfileBuilding pb = it.next();
            Class<? extends AbstractBuildingEntity> buildingClass;
            try {
                buildingClass = (Class<? extends AbstractBuildingEntity>)Class.forName(pb.getBuildingName());
            } catch (Exception e) {
                continue;
            }
            build(
                    buildingClass,
                    pb.getX(),
                    pb.getY(),
                    pb.isRotated(),
                    false,
                    false
            );
        }
    }

    public void moveBuilding(BuildingItem building, int fromX, int fromY, int toX, int toY) {
        if (toX < 0 || toY < 0) {
            mListener.onUserActionFail(TownListener.BuildingAction.MOVE_NO_PLACE);
            return;
        }
        int toMaxX = toX + building.getWidth() - 1;
        int toMaxY = toY + building.getHeight() - 1;

        if (toX >= mMap.length || toY >= mMap[toX].length) {
            mListener.onUserActionFail(TownListener.BuildingAction.MOVE_NO_PLACE);
            return;
        }

        // проверка на возможность постройки
        for (int i = toX; i <= toMaxX; i++) {
            for (int j = toY; j <= toMaxY; j++) {
                if (
                        mMap[i][j] != null
                        && !mMap[i][j].isFree()
                        && mMap[i][j].getBuilding() != building
                ) {
                    mListener.onUserActionFail(TownListener.BuildingAction.MOVE_NO_PLACE);
                    return;
                }
            }
        }

        // освобождаем старые клекти
        for (int i = fromX; i < fromX + building.getWidth(); i++) {
            for (int j = fromY; j < fromY + building.getHeight(); j++) {
                mMap[i][j].markAsFree();
            }
        }

        building.setPosition(toX, toY);

        // занимаем новые
        for (int i = toX; i <= toMaxX; i++) {
            for (int j = toY; j <= toMaxY; j++) {
                if (mMap[i][j] == null) {
                    mMap[i][j] = new Tile();
                }
                mMap[i][j].markAsBusy(building);
            }
        }
    }

    public void demolish(BuildingItem building) {
        mBuildings.removeValue(building, true);
        mListener.onBuildingRemove(building);
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public void setSelected(int col, int row) {
        if (col < 0 || col >= mMap.length) {
            Gdx.app.log("select try fail", "x");
            return;
        }
        if (row < 0 || row >= mMap[col].length) {
            Gdx.app.log("select try fail", "y");
            return;
        }
        if (mMap[col][row] == null) {
            Gdx.app.log("select try fail", "null " + col + ":" + row);
            return;
        }
        BuildingItem b = mMap[col][row].getBuilding();
        if (b == null) {
            Gdx.app.log("select try fail", "no building");
            return;
        }
        Gdx.app.log("onBuildingSelect", b.getInfo().getTitle());
        mListener.onBuildingSelect(b);
        if (b.isInConstruction()) {
            b.getInfo().runOnSelectConstruction(b);
        } else if (b.isInUpgrading()) {
            b.getInfo().runOnSelectUpgrading(b);
        } else {
            b.getInfo().runOnSelect(b);
        }
    }

    public void terminateUpgrading(BuildingItem building) {
        building.upgradingTerminate();
    }

    public void tryOrderThing(BuildingItem building, AbstractThingEntity thing) {
        for (Iterator< ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer> > it = thing.getCost().getResources().iterator(); it.hasNext();) {
            ObjectMap.Entry<Class<? extends AbstractThingEntity>, Integer> el = it.next();
            ThingItem ri = mResources.get(el.key);
            if (ri == null || ri.getCount() < el.value) {
                // недостаток одного из ресурсов
                mListener.onOrderResourceLack(new ThingCostElement(el.key, el.value));
                //return;
            }
        }

        CraftPlanItem cpi = new CraftPlanItem();
        cpi.setCreated(mGame.getGameMillis());
        cpi.setThing(thing);
        building.getCraftPlan().add(cpi);

        mListener.onThingAddToPlan(building, cpi);
    }

}
