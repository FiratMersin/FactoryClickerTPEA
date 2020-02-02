package com.fr.factoryclicker.automation.machines;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.ui.main.MyMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineList {

    protected int max_machines = 10000;

    protected MachineType type;

    protected int nbMachines = 0;
    protected int nbMachinesNotUsed = 0;

    protected List<ProductionData> machines;
    protected Map<ResourceType, ProductionData> mapMachines;

    public MachineList(MachineType type){
        this.machines = new ArrayList<>();
        this.mapMachines = new HashMap<>();
        this.type = type;

        switch (type){
            case SMELTER: initSmelter(); break;
            case CONSTRUCTOR: initConstructor(); break;
            case ASSEMBLER: initAssembler(); break;
            case MANUFACTURER: initManufacturer(); break;
            default: Log.i("BUG", "UNKNOWN MACHINETYPE IN MACHINELIST");
        }

    }

    public void upgradeProd(double mult){
        for(ProductionData md : machines){
            md.setProductionPerMinuteByMachine(md.getProductionPerMinuteByMachine() * mult);
            Resource resource = DataHolder.getInstance().getMapUserInventory().get(md.getResourceProducted());
            double old = resource.getProdPerMinute();
            resource.setProdPerMinuteOnUpgrade(md.getTotalProductionPerMinute());//don't forget to change the prod per minute !!
            if(old > 0.0)
            Log.i("PRODUP", md.resourceProducted+" (old=) "+old+ " (new=) " +md.getProductionPerMinuteByMachine());


            //and change the cons per minute of each element in the recipe !!
            List<RecipeElement> recipe = md.getRecipe();
            for(RecipeElement re : recipe){
                Resource reResource = DataHolder.getInstance().getMapUserInventory().get(re.getIngredient());
                double new_cons = reResource.getConsPerMinute() * mult;//
                reResource.setConsPerMinuteOnUpgrade(new_cons);
            }


        }
    }

    public boolean buyMachine() {
        DataHolder dataHolder = DataHolder.getInstance();
        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        int nextMachineNumber = getNbMachines() + 1;
        List<RecipeElement> recipe = getNewMachineRecipe(nextMachineNumber);

        for(RecipeElement re : recipe){
            if(! (mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()) ) {
                Context context = MyMainActivity.getMyMain();
                CharSequence text = "Not enough ingredients";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
        }

        if(nbMachines < max_machines){
            this.nbMachines++;
            this.nbMachinesNotUsed++;
            for(RecipeElement re : recipe){
                mapUserInventory.get(re.getIngredient()).consume(re.getNumber());
            }
            return true;
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text = "Maximum number of machines reached.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }

    public List<RecipeElement> getBasicMachineRecipe() {
        List<RecipeElement> recipe = new ArrayList<>();

        switch (type){
            case SMELTER:
                recipe.add(new RecipeElement(ResourceType.IRON_ROD, 5));
                recipe.add(new RecipeElement(ResourceType.CABLE, 8));
                break;
            case CONSTRUCTOR:
                recipe.add(new RecipeElement(ResourceType.REINFORCED_IRON_PLATE, 3));
                recipe.add(new RecipeElement(ResourceType.CABLE, 2));
                break;
            case ASSEMBLER:
                recipe.add(new RecipeElement(ResourceType.MODULAR_FRAME, 3));
                recipe.add(new RecipeElement(ResourceType.ROTOR, 4));
                recipe.add(new RecipeElement(ResourceType.CABLE, 10));
                break;
            case MANUFACTURER:
                recipe.add(new RecipeElement(ResourceType.HEAVY_MODULAR_FRAME, 2));
                recipe.add(new RecipeElement(ResourceType.MOTOR, 2));
                recipe.add(new RecipeElement(ResourceType.CABLE, 25));
                recipe.add(new RecipeElement(ResourceType.COMPUTER, 3));
                break;
            default: Log.i("BUG", "UNKNOWN MACHINETYPE IN MACHINELIST");
        }

        return recipe;
    }

    public boolean canAddMachine(ProductionData prodD){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        double ppbm = prodD.getProductionPerMinuteByMachine();
        List<RecipeElement> recipe = prodD.getRecipe();
        double rpn = mapUserInventory.get(prodD.getResourceProducted()).getRecipeProductionNumber();
        for(RecipeElement re : prodD.recipe){
            Resource r = mapUserInventory.get(re.getIngredient());
            double nbPerRecipe = re.getNumber();
            double new_cons = r.getConsPerMinute() + (ppbm / rpn * nbPerRecipe);
            if(! r.canConsumePerMinute(new_cons)){
                Log.i("INFO", "CAN ADDMACHINE FALSE "+ re.getIngredient() + " not producted enough : "+new_cons+'/' +r.getProdPerMinute());
                return false;
            }
        }

        Log.i("INFO", "CAN ADDMACHINE TRUE");
        return true;
    }

    public boolean canDelMachine(ProductionData prodD){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        double PPM = prodD.productionPerMinuteByMachine;
        Resource r = mapUserInventory.get(prodD.resourceProducted);
        if(! r.canReduceProdPerMinute(PPM * 1.0))
            return false;
        return true;
    }

    public boolean addMachine(ProductionData md) {
        if (this.nbMachinesNotUsed > 0) {
            md.setNbMachines(md.getNbMachines() + 1);
            this.nbMachinesNotUsed--;

            Log.i("INFO", "ADDMACHINE TRUE");
            return true;
        }else{
            return false;
        }
    }

    public boolean delMachine(ProductionData md) {
        if (md.getNbMachines() > 0) {
            md.setNbMachines(md.getNbMachines() - 1);
            this.nbMachinesNotUsed++;
            return true;
        }else{
            return false;
        }
    }

    public List<RecipeElement> getNewMachineRecipe(int n){
        List<RecipeElement> recipe;
        recipe = getBasicMachineRecipe();
        for(RecipeElement re : recipe){
            re.setNumber(n * re.getNumber());
        }
        return recipe;
    }

    public boolean canBuyOneMachines(ResourceType type, Map<ResourceType, Resource> mapUserInventory){
        ProductionData prodD = mapMachines.get(type);
        return prodD.canBuyOneMachines(mapUserInventory);
    }


    public int getNbMachines() {
        return nbMachines;
    }

    public int getNbMachinesNotUsed() {
        return nbMachinesNotUsed;
    }

    public int getNbMachinesUsed() {
        return nbMachines - nbMachinesNotUsed;
    }

    public List<ProductionData> getMachines() {
        return machines;
    }

    public Map<ResourceType, ProductionData> getMapMachines() {
        return mapMachines;
    }

    public ProductionData getProductionData(ResourceType type){
        return this.mapMachines.get(type);
    }

    public int getMax_machines() {
        return max_machines;
    }

    private void initSmelter(){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ResourceType rt = ResourceType.IRON_INGOT;
        ProductionData prD = new ProductionData(rt,
                30.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.COPPER_INGOT;
        prD = new ProductionData(rt,
                30.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.CATERIUM_INGOT;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.STEEL_INGOT;
        prD = new ProductionData(rt,
                30.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.ALUMINIUM_INGOT;
        prD = new ProductionData(rt,
                30.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);
    }
    private void initConstructor(){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ResourceType rt = ResourceType.CABLE;
        ProductionData prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.CONCRETE;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.IRON_PLATE;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.IRON_ROD;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.SCREW;
        prD = new ProductionData(rt,
                90.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.WIRE;
        prD = new ProductionData(rt,
                45.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.QUICKWIRE;
        prD = new ProductionData(rt,
                60.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.QUARTZ_CRYSTAL;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.SILICA;
        prD = new ProductionData(rt,
                45.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.STEEL_BEAM;
        prD = new ProductionData(rt,
                10.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.STEEL_PIPE;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.PLASTIC;
        prD = new ProductionData(rt,
                22.5, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.RUBBER;
        prD = new ProductionData(rt,
                30.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);
    }
    private void initAssembler(){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ResourceType rt = ResourceType.REINFORCED_IRON_PLATE;
        ProductionData prD = new ProductionData(rt,
                5.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.MODULAR_FRAME;
        prD = new ProductionData(rt,
                4.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.ROTOR;
        prD = new ProductionData(rt,
                6.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.ENCASED_INDUSTRIAL_BEAM;
        prD = new ProductionData(rt,
                4.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.MOTOR;
        prD = new ProductionData(rt,
                5.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.STATOR;
        prD = new ProductionData(rt,
                6.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.AI_LIMITER;
        prD = new ProductionData(rt,
                5.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.CIRCUIT_BOARD;
        prD = new ProductionData(rt,
                5.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.ALUMINIUM_SHEET;
        prD = new ProductionData(rt,
                15.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.HEAT_SINK;
        prD = new ProductionData(rt,
                5.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);
    }
    private void initManufacturer(){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ResourceType rt = ResourceType.CRYSTAL_OSCILLATOR;
        ProductionData prD = new ProductionData(rt,
                1.875, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.HIGH_SPEED_CONNECTOR;
        prD = new ProductionData(rt,
                2.5, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.HEAVY_MODULAR_FRAME;
        prD = new ProductionData(rt,
                2.0, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.COMPUTER;
        prD = new ProductionData(rt,
                1.875, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.SUPERCOMPUTER;
        prD = new ProductionData(rt,
                1.875, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.RADIO_CONTROL_UNIT;
        prD = new ProductionData(rt,
                2.5, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);

        rt = ResourceType.TURBO_MOTOR;
        prD = new ProductionData(rt,
                1.875, mapUserInventory.get(rt).getIngredients());
        this.machines.add(prD);
        this.mapMachines.put(rt, prD);
    }
}