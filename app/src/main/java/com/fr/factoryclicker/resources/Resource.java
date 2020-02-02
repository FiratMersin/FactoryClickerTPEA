package com.fr.factoryclicker.resources;

import android.util.Log;

import com.fr.factoryclicker.R;

import java.util.ArrayList;
import java.util.List;

import com.fr.factoryclicker.recipe.RecipeElement;

public class Resource {

    private ResourceType type;
    private double numberStored;
    private double max;
    private double initmax;

    private final int numberOfClickToProd;

    private final int numberOfIngredients;

    private List<RecipeElement> ingredients;
    private double recipeProductionNumber;

    private double prodPerMinute;
    private double consPerMinute;
    private double prodPerSec;
    private double consPerSec;

    private double productionTmp;

    public Resource(ResourceType type, int numberStored, int max,int numberOfClickToProd,
                    int recipeProductionNumber, int numberOfIngredients, RecipeElement... ingredients){
        this.type = type;
        this.numberStored = numberStored;
        this.max = max;
        this.initmax = max;
        this.numberOfClickToProd = numberOfClickToProd;
        this.numberOfIngredients = numberOfIngredients;
        this.ingredients = new ArrayList<>();
        for(RecipeElement rt : ingredients){
            this.ingredients.add(rt);
        }
        this.recipeProductionNumber = recipeProductionNumber;
        this.prodPerMinute = 0.0;
        this.prodPerSec = 0.0;
        this.consPerMinute = 0.0;
        this.consPerSec = 0.0;
        this.productionTmp = 0.0;
    }

    public Resource(ResourceType type, int max, int numberOfClickToProd,
                    int recipeProductionNumber, int numberOfIngredients, RecipeElement... ingredients){
        this.type = type;
        this.numberStored = 0;
        this.max = max;
        this.initmax = max;
        this.numberOfClickToProd = numberOfClickToProd;
        this.numberOfIngredients = numberOfIngredients;
        this.ingredients = new ArrayList<>();
        for(RecipeElement rt : ingredients){
            this.ingredients.add(rt);
        }
        this.recipeProductionNumber = recipeProductionNumber;
        this.prodPerMinute = 0.0;
        this.prodPerSec = 0.0;
        this.consPerMinute = 0.0;
        this.consPerSec = 0.0;
    }

    public void setNumberStored(int numberStored){

        if(numberStored < 0){
            Log.d( "BUG" ,"set number of resources stored with a negative number");
        }else {

            if (this.max > numberStored) {
                this.numberStored = numberStored;
            }
        }
    }

    public void autoIncrementNumberStored(double n){
        if (this.max >= (numberStored + n)) {
            this.numberStored += n;
        }else{
            this.numberStored = this.max;
        }
    }

    public boolean incrementNumberStored(double n){
        if (this.max >= (numberStored + n)) {
            this.numberStored += n;
            return true;
        }else{
            this.numberStored = this.max;
            return true;
        }
    }

    public boolean consume(double number){
        if(numberStored < number){
            return false;
        }else {
            numberStored -= number;
            return true;
        }
    }

    public void automationIteration(){
        this.productionTmp += this.prodPerSec - this.consPerSec;

        if(productionTmp >= 1.0) {
            double toAdd = Math.floor(productionTmp);

            this.productionTmp -= toAdd;

            this.autoIncrementNumberStored(toAdd);
        }
    }

    public void setProdPerMinuteOnUpgrade(double prodPerMinute) {
        this.prodPerMinute = prodPerMinute;
        this.prodPerSec = this.prodPerMinute / 60.0;
    }

    public boolean setProdPerMinute(double prodPerMinute) {
        if(prodPerMinute < this.consPerMinute){
            return false;
        }else {
            Log.i("SETPROD", this.type+ " "+prodPerMinute + " consPerMinute = "+this.consPerMinute);
            this.prodPerMinute = prodPerMinute;
            this.prodPerSec = this.prodPerMinute / 60.0;
            return true;
        }
    }

    public boolean setConsPerMinute(double consPerMinute) {
        if(consPerMinute > this.prodPerMinute){
            Log.i("SETCONS FALSE", this.type+ " "+consPerMinute+ " > " + this.prodPerMinute);
            return false;
        }else {
            Log.i("SETCONS", this.type+ " "+consPerMinute);
            this.consPerMinute = consPerMinute;
            this.consPerSec = this.consPerMinute / 60.0;
            return true;
        }
    }

    public void setConsPerMinuteOnUpgrade(double consPerMinute) {
        this.consPerMinute = consPerMinute;
        this.consPerSec = this.consPerMinute / 60.0;
    }

    public boolean canConsumePerMinute(double newCons){
        return this.prodPerMinute - newCons >= 0.0;
    }

    public boolean canReduceProdPerMinute(double prodToReduce){
        return (this.prodPerMinute - prodToReduce) - this.consPerMinute >= 0.0;
    }

    public double getConsPerMinute() {
        return consPerMinute;
    }

    public double getProdPerMinute() {
        return prodPerMinute;
    }

    public void restore(double number){
        numberStored += number;
    }

    public int getImage(){
        return Resource.getImage(this.type);
    }

    public static int getImage(ResourceType type){
        switch(type){
            case IRON_ORE: return R.mipmap.ic_image_iron_ore;
            case COPPER_ORE: return R.mipmap.ic_image_copper_ore;
            case LIMESTONE: return R.mipmap.ic_image_limestone;
            case COAL: return R.mipmap.ic_image_coal;
            case CRUDE_OIL: return R.mipmap.ic_image_crude_oil;
            case CATERIUM_ORE: return R.mipmap.ic_image_caterium_ore;
            case RAW_QUARTZ: return R.mipmap.ic_image_raw_quartz;
            case BAUXITE: return R.mipmap.ic_image_bauxite;


            case CABLE: return R.mipmap.ic_image_cable;
            case CONCRETE: return R.mipmap.ic_image_concrete;
            case COPPER_INGOT: return R.mipmap.ic_image_copper_ingot;
            case IRON_INGOT: return R.mipmap.ic_image_iron_ingot;
            case IRON_PLATE: return R.mipmap.ic_image_iron_plate;
            case IRON_ROD: return R.mipmap.ic_image_iron_rod;
            case SCREW: return R.mipmap.ic_image_screw;
            case WIRE: return R.mipmap.ic_image_wire;
            case CATERIUM_INGOT: return R.mipmap.ic_image_caterium_ingot;
            case QUICKWIRE: return R.mipmap.ic_image_quickwire;
            case QUARTZ_CRYSTAL: return R.mipmap.ic_image_quartz_crystal;
            case SILICA: return R.mipmap.ic_image_silica;
            case STEEL_BEAM: return R.mipmap.ic_image_steel_beam;
            case STEEL_PIPE: return R.mipmap.ic_image_steel_pipe;
            case PLASTIC: return R.mipmap.ic_image_plastic;
            case RUBBER: return R.mipmap.ic_image_rubber;


            case REINFORCED_IRON_PLATE: return R.mipmap.ic_image_reinforced_iron_plate;
            case MODULAR_FRAME: return R.mipmap.ic_image_modular_frame;
            case ROTOR: return R.mipmap.ic_image_rotor;
            case ENCASED_INDUSTRIAL_BEAM: return R.mipmap.ic_image_encased_industrial_beam;
            case MOTOR: return R.mipmap.ic_image_motor;
            case STATOR: return R.mipmap.ic_image_stator;
            case STEEL_INGOT: return R.mipmap.ic_image_steel_ingot;
            case AI_LIMITER: return R.mipmap.ic_image_ai_limiter;
            case CIRCUIT_BOARD: return R.mipmap.ic_image_circuit_board;
            case ALUMINIUM_INGOT: return R.mipmap.ic_image_aluminium_ingot;
            case ALUMINIUM_SHEET: return R.mipmap.ic_image_aluminium_sheet;
            case HEAT_SINK: return R.mipmap.ic_image_heat_sink;


            case CRYSTAL_OSCILLATOR: return R.mipmap.ic_image_crystal_oscillator;
            case HIGH_SPEED_CONNECTOR: return R.mipmap.ic_image_high_speed_connector;

            case HEAVY_MODULAR_FRAME: return R.mipmap.ic_image_heavy_modular_frame;
            case COMPUTER: return R.mipmap.ic_image_computer;
            case SUPERCOMPUTER: return R.mipmap.ic_image_supercomputer;
            case RADIO_CONTROL_UNIT: return R.mipmap.ic_image_radio_control_unit;
            case TURBO_MOTOR: return R.mipmap.ic_image_turbo_motor;
        }
        Log.i("IMAGE NOT FOUND", "Resource unknown image");
        return -1;
    }

    public static List<Resource> getAllResourceList(){
        List<Resource> list = new ArrayList<>();
        for(ResourceType rt : ResourceType.values()){
            list.add(ResourceValues.getResourceValues(rt));
        }
        return list;
    }

    public ResourceType getType() {
        return type;
    }

    public int getNumberOfClickToProd() {
        return numberOfClickToProd;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public double getNumberStored() {
        return numberStored;
    }

    public void setMax(double max) {
        this.max = Math.floor(max);
        if(this.max > 1000000000.0){
            this.max = 1000000000.0;
        }
    }

    public double getMax() {
        return Math.floor(max);
    }

    public double getRecipeProductionNumber(){
        return this.recipeProductionNumber;
    }

    public List<RecipeElement> getIngredients() {
        return ingredients;
    }

    public double getINITMAX() {
        return initmax;
    }
}
