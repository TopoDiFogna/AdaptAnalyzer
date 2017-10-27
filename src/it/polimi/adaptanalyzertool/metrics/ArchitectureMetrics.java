package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.logic.Architecture;
import it.polimi.adaptanalyzertool.logic.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class contains all the metrics for the architectures.
 * <p>
 * It has to be used as a static class since no object must be instatianted before calling the methods that are
 * all public and static.
 * </p>
 *
 * @see it.polimi.adaptanalyzertool.logic.Architecture Architecture
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public final class ArchitectureMetrics {

    /**
     * Defines the availability of the components that are in the architecture as probability that are all active in
     * a given instant.
     * <p>
     *     A better availability of a component in the architecture implies a better Fitness Ratio w.r.t. Availability
     *     that is reflected in a better global availability.
     * </p>
     * <p>
     *     Higher numbers mean better availability.
     * </p>
     * <p>
     * Calculates the Global Availability of System as specified in the thesis by Zanotti of 2016-12 at page 31.
     * </p>
     *
     * @see ComponentMetrics#FitnessRatioAvailability(float, float) FitnessRatioAvailability(float, float)
     *
     * @param components the components present in an architecture.
     * @param systemTargetAvailability the system target availability required.
     * @return the Global Availability of the System.
     */
    public static float GlobalAvailabilitySystem(HashMap<String, Component> components, float systemTargetAvailability){
        float gas = 1;
        for (Component component : components.values()) {
            gas *= ComponentMetrics.FitnessRatioAvailability(component.getAvailability(), systemTargetAvailability);
        }
        return gas;
    }

    /**
     * Defines the total cost of the components in an architecture w.r.t. the cost of each individual component. //TODO better define this metric
     * <p>
     * Calculates the Global Cost of System as specified in the thesis by Zanotti of 2016-12 at page 32.
     * </p>
     *
     * @see ComponentMetrics#FitnessRatioCost(float, float) FitnessRatioCost(float, float)
     *
     * @param components the components present in the architecture.
     * @param systemTargetCost the system target cost.
     * @return the global Cost of the system.
     */
    public static float GlobalCostSystem(HashMap<String, Component> components, float systemTargetCost){
        float gcs = 0;
        for (Component component : components.values()){
            gcs += ComponentMetrics.FitnessRatioCost(component.getCost(), systemTargetCost);
        }
        return gcs;
    }

    /**
     * Defines which architectures are suitable for the specified availability.
     *
     * @param architectures the architectures that need to be tested.
     * @param systemTargetAvailability the target availability.
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     *         with the target availability.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemAvailability(ArrayList<Architecture> architectures,
                                                                             float systemTargetAvailability){
        ArrayList <Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures){
            if (GlobalAvailabilitySystem(architecture.getComponents(), systemTargetAvailability) >= 1) {
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }

    /**
     * Defines which architectures are suitable for the specified cost.
     *
     * @param architectures the architectures that need to be tested.
     * @param systemTargetCost the target cost.
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     *         with the target cost.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemCost(ArrayList<Architecture> architectures,
                                                                     float systemTargetCost){
        ArrayList <Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures){
            if (GlobalCostSystem(architecture.getComponents(), systemTargetCost) >= 1){
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }
}
