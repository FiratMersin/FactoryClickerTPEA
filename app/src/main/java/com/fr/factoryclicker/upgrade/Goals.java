package com.fr.factoryclicker.upgrade;

import android.util.Log;

import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Goals {
    private int nbInventoryUpgrade;
    private int nbPrestiges;

    private double inventoryMultiplier;
    private double addToInventoryMultplier;

    private List<LoadObj> loadObjs;
    private int currentObj;

    public Goals(){
        this.nbInventoryUpgrade = 0;
        this.nbPrestiges = 0;
        this.inventoryMultiplier = 1.1;
        this.addToInventoryMultplier = 0.1;
        this.currentObj = 0;
        initLoadObjs();
    }

    private void initLoadObjs(){
        loadObjs = new ArrayList<>();
        loadObjs.add(new LoadObj(ResourceType.IRON_ORE, 1000.0));
        loadObjs.add(new LoadObj(ResourceType.COPPER_INGOT, 2000.0));
        loadObjs.add(new LoadObj(ResourceType.IRON_PLATE, 4000.0));
        loadObjs.add(new LoadObj(ResourceType.WIRE, 8000.0));
        loadObjs.add(new LoadObj(ResourceType.REINFORCED_IRON_PLATE, 2000.0));

        loadObjs.add(new LoadObj(ResourceType.ROTOR, 4000.0));
        loadObjs.add(new LoadObj(ResourceType.MOTOR, 8000.0));
        loadObjs.add(new LoadObj(ResourceType.AI_LIMITER, 10000.0));
        loadObjs.add(new LoadObj(ResourceType.ENCASED_INDUSTRIAL_BEAM, 16000.0));
        loadObjs.add(new LoadObj(ResourceType.HEAT_SINK, 16000.0));

        loadObjs.add(new LoadObj(ResourceType.COMPUTER, 20000.0));
        loadObjs.add(new LoadObj(ResourceType.RADIO_CONTROL_UNIT, 25000.0));
        loadObjs.add(new LoadObj(ResourceType.HEAVY_MODULAR_FRAME, 50000.0));
        loadObjs.add(new LoadObj(ResourceType.SUPERCOMPUTER, 100000.0));
        loadObjs.add(new LoadObj(ResourceType.TURBO_MOTOR, 1000000.0));
    }

    public LoadObj getCurrentLoadObj(){
        return loadObjs.get(currentObj);
    }

    public void switchToNextLoadObj(){
        this.currentObj++;
    }

    public boolean isLoadObjOver(){
        if(this.currentObj == (loadObjs.size()-1)){
            return loadObjs.get(currentObj).isCompleted();
        }
        return false;
    }

    public List<RecipeElement> getNextInventoryUpgradeCost() {
        List<RecipeElement> recipe = new ArrayList<>();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        int n = this.nbInventoryUpgrade +1;
        ResourceType rt;
        if(n <= 5) {
            rt = ResourceType.IRON_ORE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.COPPER_ORE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.COAL;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.LIMESTONE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 10){
            rt = ResourceType.IRON_ORE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.IRON_INGOT;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.IRON_ROD;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.IRON_PLATE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 15){
            rt = ResourceType.COPPER_ORE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.COPPER_INGOT;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.WIRE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.CABLE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 20){
            rt = ResourceType.LIMESTONE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.CONCRETE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.SCREW;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.REINFORCED_IRON_PLATE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 25){
            rt = ResourceType.STEEL_PIPE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.STEEL_BEAM;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.RUBBER;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.PLASTIC;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 30){
            rt = ResourceType.MODULAR_FRAME;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.ROTOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.STATOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.ALUMINIUM_SHEET;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 35){
            rt = ResourceType.MOTOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.AI_LIMITER;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.CIRCUIT_BOARD;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.REINFORCED_IRON_PLATE;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else if(n <= 40){
            rt = ResourceType.CRYSTAL_OSCILLATOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.HEAT_SINK;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.MOTOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.HEAVY_MODULAR_FRAME;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }else{
            rt = ResourceType.COMPUTER;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.SUPERCOMPUTER;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.RADIO_CONTROL_UNIT;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
            rt = ResourceType.TURBO_MOTOR;
            recipe.add(new RecipeElement(rt, mapUserInventory.get(rt).getMax()));
        }
        return recipe;
    }

    public List<RecipeElement> getNextPrestigeCost() {
        List<RecipeElement> recipe = new ArrayList<>();
        int n = this.nbPrestiges +1;
        ResourceType rt;

        double value = Math.floor(50.0*(1.0+n/2.0));

        rt = ResourceType.HEAVY_MODULAR_FRAME;
        recipe.add(new RecipeElement(rt, value));
        rt = ResourceType.SUPERCOMPUTER;
        recipe.add(new RecipeElement(rt, value));
        rt = ResourceType.RADIO_CONTROL_UNIT;
        recipe.add(new RecipeElement(rt, value));
        rt = ResourceType.TURBO_MOTOR;
        recipe.add(new RecipeElement(rt, value));

        return recipe;
    }

    public boolean canBuyInventoryUpgrade(){
        List<RecipeElement> recipe = getNextInventoryUpgradeCost();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        RecipeElement re = recipe.get(0);
        double nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(1);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(2);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(3);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        return true;
    }

    public void buyInventoryUpgrade(){
        List<RecipeElement> recipe = getNextInventoryUpgradeCost();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        RecipeElement re = recipe.get(0);
        double nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING INVENTORY UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(1);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING INVENTORY UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(2);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING INVENTORY UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(3);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING INVENTORY UPGRADE WITH NOT ENOUGH RESOURCES");
        }
        upgradeInventory();
    }

    public boolean canBuyPrestigeUpgrade(){
        List<RecipeElement> recipe = getNextPrestigeCost();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        RecipeElement re = recipe.get(0);
        double nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(1);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(2);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        re = recipe.get(3);
        nb = re.getNumber();
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() < nb) return false;

        return true;
    }

    public void buyPrestigeUpgrade(){
        List<RecipeElement> recipe = getNextPrestigeCost();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        RecipeElement re = recipe.get(0);
        double nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING PRESTIGE UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(1);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING PRESTIGE UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(2);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING PRESTIGE UPGRADE WITH NOT ENOUGH RESOURCES");
        }

        re = recipe.get(3);
        nb = re.getNumber();
        if(! mapUserInventory.get(re.getIngredient()).consume(nb)){
            Log.i("BUG", "BUYING PRESTIGE UPGRADE WITH NOT ENOUGH RESOURCES");
        }
        prestige();
    }



    public void prestige(){
        this.nbPrestiges++;
        this.nbInventoryUpgrade = 0;
        this.inventoryMultiplier += this.addToInventoryMultplier;
    }

    public void upgradeInventory(){
        this.nbInventoryUpgrade++;
        List<Resource> userInventory = DataHolder.getInstance().getUserInventory();
        for(Resource r : userInventory){
            r.setMax(r.getMax()*inventoryMultiplier);
        }
    }

    public int getNbPrestiges() {
        return nbPrestiges;
    }
}
