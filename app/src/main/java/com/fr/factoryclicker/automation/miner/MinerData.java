package com.fr.factoryclicker.automation.miner;

import com.fr.factoryclicker.resources.ResourceType;

public class MinerData {

    protected ResourceType resourceMined;
    protected int nbMiners;
    protected double productionPerMinuteByMiner;
    protected double totalProductionPerMinute;

    public MinerData(ResourceType resourceMined, int productionPerMinuteByMiner){
        this.resourceMined = resourceMined;
        this.productionPerMinuteByMiner = productionPerMinuteByMiner;
        this.nbMiners = 0;
        this.totalProductionPerMinute = 0;
    }

    public ResourceType getResourceMined() {
        return resourceMined;
    }

    public int getNbMiners() {
        return nbMiners;
    }

    public double getProductionPerMinuteByMiner() {
        return productionPerMinuteByMiner;
    }

    public double getTotalProductionPerMinute() {
        return totalProductionPerMinute;
    }

    public void setNbMiners(int nbMiners) {
        this.nbMiners = nbMiners;
        this.totalProductionPerMinute = this.productionPerMinuteByMiner * this.nbMiners;
    }

    public void setProductionPerMinuteByMiner(double productionPerMinuteByMiner) {
        this.productionPerMinuteByMiner = productionPerMinuteByMiner;
        this.totalProductionPerMinute = this.productionPerMinuteByMiner * this.nbMiners;
    }
}
