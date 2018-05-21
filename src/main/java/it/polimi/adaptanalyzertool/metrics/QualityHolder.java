package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;

public class QualityHolder {

    private double minAvailability;
    private double maxAvailability;
    private double minCost;
    private double maxCost;

    private Architecture minAvailabilityArchitecture;
    private Architecture maxAvailabilityArchitecture;
    private Architecture minCostArchitecture;
    private Architecture maxCostArchitecture;


    public QualityHolder() {
        this.maxAvailability = 0;
        this.minAvailability = 1;
        this.minCost = Double.MAX_VALUE;
        this.maxCost = 0;
    }

    public void modifyCostIfNecessary(Architecture architecture, double cost) {
        if (cost < this.minCost) {
            this.minCost = cost;
            this.minCostArchitecture = architecture;
        } else if (cost > this.maxCost) {
            this.maxCost = cost;
            this.maxCostArchitecture = architecture;
        }
    }

    public void modifyAvailabilityIfNecessary(Architecture architecture, double availability) {
        if (availability < this.minAvailability) {
            this.minAvailability = availability;
            this.minAvailabilityArchitecture = architecture;
        } else if (availability > this.maxAvailability) {
            this.maxAvailability = availability;
            this.maxAvailabilityArchitecture = architecture;
        }
    }

    public double getMinAvailability() {
        return minAvailability;
    }

    public double getMaxAvailability() {
        return maxAvailability;
    }

    public double getMinCost() {
        return minCost;
    }

    public double getMaxCost() {
        return maxCost;
    }

    public Architecture getMinAvailabilityArchitecture() {
        return minAvailabilityArchitecture;
    }

    public Architecture getMaxAvailabilityArchitecture() {
        return maxAvailabilityArchitecture;
    }

    public Architecture getMinCostArchitecture() {
        return minCostArchitecture;
    }

    public Architecture getMaxCostArchitecture() {
        return maxCostArchitecture;
    }
}
