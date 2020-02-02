package com.fr.factoryclicker.automation.miner;

import android.content.Context;
import android.widget.Toast;

import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.ui.main.MyMainActivity;

public class MinersList {

    private static int MAX_MINERS = 10000;

    protected int nbMiners = 0;
    protected int nbMinersNotUsed = 0;

    protected List<MinerData> miners;
    protected Map<ResourceType, MinerData> mapMiners;

    public MinersList(){
        this.miners = new ArrayList<>();
        this.mapMiners = new HashMap<>();

        MinerData md = new MinerData(ResourceType.IRON_ORE, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.COPPER_ORE, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.BAUXITE, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.COAL, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.CATERIUM_ORE, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.CRUDE_OIL, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.LIMESTONE, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);

        md = new MinerData(ResourceType.RAW_QUARTZ, 30);
        this.miners.add(md);
        this.mapMiners.put(md.getResourceMined(), md);
    }

    public boolean buyMiner(){
        DataHolder dataHolder = DataHolder.getInstance();
        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        int nextMinerNumber = getNbMiners() + 1;
        List<RecipeElement> recipe = getNewMinerRecipe(nextMinerNumber);

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

        if(nbMiners < MAX_MINERS){
            this.nbMiners++;
            this.nbMinersNotUsed++;
            for(RecipeElement re : recipe){
                mapUserInventory.get(re.getIngredient()).consume(re.getNumber());
            }
            return true;
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text = "Maximum number of miners reached.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }

    public boolean addMiner(MinerData md) {
        if (this.nbMinersNotUsed > 0) {
            md.setNbMiners(md.getNbMiners() + 1);
            this.nbMinersNotUsed--;
            return true;
        }else{
            return false;
        }
    }

    public boolean delMiner(MinerData md) {
        if (md.getNbMiners() > 0) {
            md.setNbMiners(md.getNbMiners() - 1);
            this.nbMinersNotUsed++;
            return true;
        }else{
            return false;
        }
    }

    public List<RecipeElement> getNewMinerRecipe(int n){
        List<RecipeElement> recipe;
        recipe = getBasicMinerRecipe();
        for(RecipeElement re : recipe){
            re.setNumber(n * re.getNumber());
        }
        return recipe;
    }

    public List<RecipeElement> getBasicMinerRecipe(){
        List<RecipeElement> recipe = new ArrayList<>();
        recipe.add(new RecipeElement(ResourceType.IRON_PLATE, 4));
        recipe.add(new RecipeElement(ResourceType.WIRE, 8));
        recipe.add(new RecipeElement(ResourceType.CABLE, 4));
        recipe.add(new RecipeElement(ResourceType.IRON_ROD, 5));
        recipe.add(new RecipeElement(ResourceType.CONCRETE, 5));

        return recipe;
    }

    public int getNbMiners() {
        return nbMiners;
    }

    public int getNbMinersUsed() {
        return nbMiners - nbMinersNotUsed;
    }

    public List<MinerData> getMiners() {
        return miners;
    }

    public MinerData getMinerData(ResourceType type){
        return this.mapMiners.get(type);
    }
}
