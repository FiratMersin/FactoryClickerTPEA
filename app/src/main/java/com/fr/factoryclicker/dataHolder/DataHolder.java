package com.fr.factoryclicker.dataHolder;

import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.automation.miner.MinersList;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.upgrade.Goals;

import java.util.List;
import java.util.Map;

public class DataHolder {
    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private List<Resource> userInventory = null;
    private Map<ResourceType, Resource> mapUserInventory = null;

    private Resource currentResourceOnClick;

    private MinersList minersList = null;
    private MachineList smelterList = null;
    private MachineList constructorList = null;
    private MachineList assemblerList = null;
    private MachineList manufacturerList = null;
    private Goals goals = null;

    private DataHolder() {
    }

    public void setMapUserInventory(Map<ResourceType, Resource> mapUserInventory) {
            this.mapUserInventory = mapUserInventory;
    }

    public void setUserInventory(List<Resource> userInventory) {
            this.userInventory = userInventory;
    }

    public void setCurrentResourceOnClick(Resource currentResourceOnClick) {
        this.currentResourceOnClick = currentResourceOnClick;
    }

    public void setMinersList(MinersList minersList) {
            this.minersList = minersList;
    }

    public void setSmelterList(MachineList smelterList) {
            this.smelterList = smelterList;
    }

    public void setAssemblerList(MachineList assemblerList) {
            this.assemblerList = assemblerList;
    }

    public void setConstructorList(MachineList constructorList) {
            this.constructorList = constructorList;
    }

    public void setManufacturerList(MachineList manufacturerList) {
            this.manufacturerList = manufacturerList;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public List<Resource> getUserInventory() {
        return userInventory;
    }

    public Map<ResourceType, Resource> getMapUserInventory() {
        return mapUserInventory;
    }

    public Resource getCurrentResourceOnClick() {
        return currentResourceOnClick;
    }

    public MinersList getMinersList() {
        return minersList;
    }

    public MachineList getSmelterList() {
        return smelterList;
    }

    public MachineList getConstructorList() {
        return constructorList;
    }

    public MachineList getAssemblerList() {
        return assemblerList;
    }

    public MachineList getManufacturerList() {
        return manufacturerList;
    }

    public Goals getGoals() {
        return goals;
    }
}
