package com.fr.factoryclicker.upgrade;

import com.fr.factoryclicker.resources.ResourceType;

public class LoadObj {

    private ResourceType type;
    private double needed;
    private double loaded;

    public LoadObj(ResourceType type, double needed){
        this.type = type;
        this.needed = needed;
        this.loaded = 0.0;
    }

    public double load(double load){
        double consumed = load;
        loaded += load;
        if(loaded > needed){
            double left = loaded - needed;
            loaded = needed;
            consumed -= left;
        }
        return consumed;
    }

    public boolean isCompleted(){
        return loaded >= needed;
    }

    public ResourceType getType() {
        return type;
    }

    public double getNeeded() {
        return needed;
    }

    public double getLoaded() {
        return loaded;
    }
}
