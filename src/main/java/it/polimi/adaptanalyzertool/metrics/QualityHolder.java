package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;

/**
 * <p>
 * This class represents an objects that holds the results from the analysis.
 * </p>
 * <p>
 * In general this should not be instantiated more than once but is not a singleton so it can be used for different
 * analysis.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class QualityHolder {

    private double minCost;
    private double maxCost;

    private Architecture minCostArchitecture;
    private Architecture maxCostArchitecture;


    /**
     * <p>
     * Creates a new holder where the min is setted to {@link Double#MAX_VALUE} and max setted to 0.
     * </p>
     */
    public QualityHolder() {
        this.minCost = Double.MAX_VALUE;
        this.maxCost = 0;
    }

    /**
     * Checks of the new cost is greater than the min or lesser than the max already encountered and modifies the
     * parameters accordingly.
     *
     * @param architecture the architecture that has to be checked.
     * @param cost         the new cost.
     */
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

    /**
     * @return the minimun cost currently saved.
     */
    public double getMinCost() {
        return minCost;
    }

    /**
     * @return the maximum cost currently saved.
     */
    public double getMaxCost() {
        return maxCost;
    }

    /**
     * @return the architecture with the minimum cost.
     */
    public Architecture getMinCostArchitecture() {
        return minCostArchitecture;
    }

    /**
     * @return the architecture with the maximum cost.
     */
    public Architecture getMaxCostArchitecture() {
        return maxCostArchitecture;
    }
}
