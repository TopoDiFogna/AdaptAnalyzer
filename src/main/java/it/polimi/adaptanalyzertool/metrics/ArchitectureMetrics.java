package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.Workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

    private ArchitectureMetrics() {
    }

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
     * @return the Global Availability of the System.
     * @see ComponentMetrics#FitnessRatioAvailability(double, double) FitnessRatioAvailability(double, double)
     */
    public static double GlobalAvailabilitySystem(Architecture architecture, double systemTargetAvailability) {
        double gas = 1;
        for (Component component : architecture.getComponents().values()) {
            gas *= ComponentMetrics.FitnessRatioAvailability(systemTargetAvailability, component.getAvailability());
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
     * @return the global Cost of the system.
     * @see ComponentMetrics#FitnessRatioCost(double, double) FitnessRatioCost(double, double)
     */
    public static double GlobalCostSystem(Architecture architecture, double systemTargetCost) {
        double gcs = 0;
        for (Component component : architecture.getComponents().values()) {
            gcs += ComponentMetrics.FitnessRatioCost(systemTargetCost, component.getCost());
        }
        return gcs;
    }

    public static double TotalStaticAvailability(HashMap<String, HashSet<Component>> architectureComponentGroups) {
        HashMap<String, Double> availabilityHashMap = new HashMap<>();
        for (String groupName : architectureComponentGroups.keySet()) {
            availabilityHashMap.put(groupName, null);
        }
        while (!completelyCalculated(availabilityHashMap)) {
            for (Map.Entry<String, HashSet<Component>> setEntry : architectureComponentGroups.entrySet()) {
                if (availabilityHashMap.get(setEntry.getKey()) == null) {
                    availabilityHashMap.put(setEntry.getKey(), tryCalculateGroupAvailability(setEntry.getKey(), architectureComponentGroups, availabilityHashMap));
                }
            }
        }
        return 0;
        //TODO
    }

    private static Double tryCalculateGroupAvailability(String groupName, HashMap<String, HashSet<Component>> architectureComponentGroups, HashMap<String, Double> availabilityHashMap) {
        if (architectureComponentGroups.get(groupName).iterator().next().getRequiredServices().isEmpty()) {
            return calculateTerminalAvailability(architectureComponentGroups.get(groupName));
        } else {

            return null;
        }
    }

    private static double calculateTerminalAvailability(HashSet<Component> componentGroup) {
        if (componentGroup.size() == 1) {
            return componentGroup.iterator().next().getAvailability();
        } else {
            double availabilityMul = 1;
            for (Component component : componentGroup) {
                availabilityMul *= 1 - component.getAvailability();
            }
            return 1 - availabilityMul;
        }
    }

    public static double TotalDynamicAvailability(HashMap<String, HashSet<Component>> architectureComponentGroups, Workflow workflow) {
        //TODO
        return 0;
    }

    private static boolean completelyCalculated(HashMap<String, Double> availabilityHashmaMap) {
        for (Double value : availabilityHashmaMap.values()) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }

    public static double TotalCost(Architecture architecture) {
        double totalCost = 0;
        for (Component component : architecture.getComponents().values()) {
            totalCost += component.getCost();
        }
        return totalCost;
    }

    /**
     * Checks if an architecture is suitable for the target cost.
     *
     * @param architecture     the architecture to be analyzed.
     * @param systemTargetCost the target cost.
     * @return <code>true</code> if the architecture is suitable, <code>false</code> otherwise.
     */
    public static boolean suitableForCost(Architecture architecture, double systemTargetCost) {
        return GlobalCostSystem(architecture, systemTargetCost) >= 1;
    }

    /**
     * Checks if an architecture is suitable for the target availability.
     *
     * @param architecture             the architecture to be analyzed.
     * @param systemTargetAvailability the target cost.
     * @return <code>true</code> if the architecture is suitable, <code>false</code> otherwise.
     */
    public static boolean suitableForAvailability(Architecture architecture, double systemTargetAvailability) {
        return GlobalAvailabilitySystem(architecture, systemTargetAvailability) >= 1;
    }

    /**
     * Defines which architectures are suitable for the specified availability.
     *
     * @param architectures            the architectures that need to be tested.
     * @param systemTargetAvailability the target availability.
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     * with the target availability.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemAvailability(ArrayList<Architecture> architectures,
                                                                             double systemTargetAvailability) {
        ArrayList<Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures) {
            if (suitableForAvailability(architecture, systemTargetAvailability)) {
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
     * @return an <code>ArrayList</code> containing a possible subset, even empty, of architectures that are suitable
     * with the target cost.
     */
    public static ArrayList<Architecture> SpacePossibilitySystemCost(ArrayList<Architecture> architectures,
                                                                     double systemTargetCost) {
        ArrayList<Architecture> eligibleArchitectures = new ArrayList<>();
        for (Architecture architecture : architectures) {
            if (suitableForCost(architecture, systemTargetCost)) {
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }
}
