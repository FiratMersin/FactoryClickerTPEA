package com.fr.factoryclicker.automation.machines;

import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.List;
import java.util.Map;

public class ProductionData {

    protected ResourceType resourceProducted;
    protected List<RecipeElement> recipe;
    protected int nbMachines;
    protected double productionPerMinuteByMachine;
    protected double totalProductionPerMinute;

    public ProductionData(ResourceType resourceProducted, double productionPerMinuteByMachine, List<RecipeElement> recipe){
        this.resourceProducted = resourceProducted;
        this.productionPerMinuteByMachine = productionPerMinuteByMachine;
        this.nbMachines = 0;
        this.totalProductionPerMinute = 0.0;
        this.recipe = recipe;
    }

    public ResourceType getResourceProducted() {
        return resourceProducted;
    }

    public int getNbMachines() {
        return nbMachines;
    }

    public double getProductionPerMinuteByMachine() {
        return productionPerMinuteByMachine;
    }

    public double getTotalProductionPerMinute() {
        return totalProductionPerMinute;
    }

    public void setNbMachines(int nbMachines) {
        this.nbMachines = nbMachines;
        this.totalProductionPerMinute = this.productionPerMinuteByMachine * this.nbMachines;
    }

    public void setProductionPerMinuteByMachine(double productionPerMinuteByMachine) {
        this.productionPerMinuteByMachine = productionPerMinuteByMachine;
        this.totalProductionPerMinute = this.productionPerMinuteByMachine * this.nbMachines;
    }

    public List<RecipeElement> getRecipe() {
        return recipe;
    }

    public boolean canBuyOneMachines(Map<ResourceType, Resource> mapUserInventory){
        for(RecipeElement re : recipe){
            if(mapUserInventory.get(re.getIngredient()).canConsumePerMinute(re.getNumber() * productionPerMinuteByMachine));
        }
        return true;
    }
}
