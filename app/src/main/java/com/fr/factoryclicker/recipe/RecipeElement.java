package com.fr.factoryclicker.recipe;

import com.fr.factoryclicker.resources.ResourceType;

public class RecipeElement{

    private ResourceType ingredient;
    private double number;

    public RecipeElement(ResourceType ingredient, double number){
        this.ingredient = ingredient;
        this.number = number;
    }

    public ResourceType getIngredient() {
        return ingredient;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
