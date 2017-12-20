package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;

import java.util.ArrayList;

/**
 * This class contains all the metrics for the architectures.
 * <p>
 * It has to be used as a static class since no object must be instantiated before calling the methods that are
 * all public and static.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see it.polimi.adaptanalyzertool.model.Architecture Architecture
 */
public final class ArchitectureMetrics {

    /**
     * Defines the availability of the components that are in the architecture as probability that are all active in
     * a given instant.
     * <p>
     * A better availability of a component in the architecture implies a better Fitness Ratio w.r.t. Availability
     * that is reflected in a better global availability.
     * </p>
     * <p>
     * Higher numbers mean better availability.
     * </p>
     * <p>
     * Calculates the Global Availability of System as specified in the thesis by Zanotti of 2016-12 at page 31.
     * </p>
     *
     * @param architecture             the architecture to be analyzed.
     * @param systemTargetAvailability the system target availability required.
     *
     * @return the Global Availability of the System.
     * @see ComponentMetrics#FitnessRatioAvailability(double, double) FitnessRatioAvailability(double, double)
     */
    public static double GlobalAvailabilitySystem(Architecture architecture, double systemTargetAvailability) {
        double gas = 1;
        for (Component component : architecture.getComponents().values()) {
            gas *= ComponentMetrics.FitnessRatioAvailability(component.getAvailability(), systemTargetAvailability);
        }
        return gas;
    }

    /**
     * Defines the total cost of the components in an architecture w.r.t. the cost of each individual component.
     * <p>
     * Calculates the Global Cost of System as specified in the thesis by Zanotti of 2016-12 at page 32.
     * </p>
     *
     * @param architecture     the architecture to be analyzed.
     * @param systemTargetCost the system target cost.
     *
     * @return the global Cost of the system.
     * @see ComponentMetrics#FitnessRatioCost(double, double) FitnessRatioCost(double, double)
     */
    public static double GlobalCostSystem(Architecture architecture, double systemTargetCost) {
        double gcs = 0;
        for (Component component : architecture.getComponents().values()) {
            gcs += ComponentMetrics.FitnessRatioCost(component.getCost(), systemTargetCost);
        }
        return gcs;
    }

    /**
     * Defines which architectures are suitable for the specified availability.
     *
     * @param architectures            the architectures that need to be tested.
     * @param systemTargetAvailability the target availability.
     *
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     * with the target availability.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemAvailability(ArrayList<Architecture> architectures,
                                                                             double systemTargetAvailability) {
        ArrayList<Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures) {
            if (GlobalAvailabilitySystem(architecture, systemTargetAvailability) >= 1) {
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }

    /**
     * Defines which architectures are suitable for the specified cost.
     *
     * @param architectures    the architectures that need to be tested.
     * @param systemTargetCost the target cost.
     *
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     * with the target cost.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemCost(ArrayList<Architecture> architectures,
                                                                     double systemTargetCost) {
        ArrayList<Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures) {
            if (GlobalCostSystem(architecture, systemTargetCost) >= 1) {
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }
}
