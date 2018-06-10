package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;

public class QualityHolder {

    private double minCost;
    private double maxCost;

    private Architecture minCostArchitecture;
    private Architecture maxCostArchitecture;


    public QualityHolder() {
        this.minCost = Double.MAX_VALUE;
        this.maxCost = 0;
    }

    public void modifyCostIfNecessary(Architecture architecture, double cost) {
        if (cost < this.minCost) {
            this.minCost = cost;
            this.minCostArchitecture = architecture;
        }
        if (cost > this.maxCost) {
            this.maxCost = cost;
            this.maxCostArchitecture = architecture;
        }
    }

    public double getMinCost() {
        return minCost;
    }

    public double getMaxCost() {
        return maxCost;
    }

    public Architecture getMinCostArchitecture() {
        return minCostArchitecture;
    }

    public Architecture getMaxCostArchitecture() {
        return maxCostArchitecture;
    }
}
