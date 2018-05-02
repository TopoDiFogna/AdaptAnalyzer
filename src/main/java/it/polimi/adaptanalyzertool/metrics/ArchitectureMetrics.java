package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.*;

import java.util.*;

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
     *
     * @return the Global Availability of the System.
     * @see ComponentMetrics#FitnessRatioAvailability(double, double) FitnessRatioAvailability(double, double)
     */
    public static double GlobalAvailabilitySystem(Architecture architecture, double systemTargetAvailability) {
        double gas = 1;
        for (Component component : architecture.getComponents()) {
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
     *
     * @return the global Cost of the system.
     * @see ComponentMetrics#FitnessRatioCost(double, double) FitnessRatioCost(double, double)
     */
    public static double GlobalCostSystem(Architecture architecture, double systemTargetCost) {
        double gcs = 0;
        for (Component component : architecture.getComponents()) {
            gcs += ComponentMetrics.FitnessRatioCost(systemTargetCost, component.getCost());
        }
        return gcs;
    }

    public static double TotalStaticAvailability(HashMap<String, ComponentGroup> architectureComponentGroups) {
        double totalAvailability = -1f;
        HashMap<String, Double> availabilityHashMap = new HashMap<>();
        for (String groupName : architectureComponentGroups.keySet()) {
            availabilityHashMap.put(groupName, null);
        }
        while (!completelyCalculated(availabilityHashMap)) {
            for (Map.Entry<String, ComponentGroup> setEntry : architectureComponentGroups.entrySet()) {
                String key = setEntry.getKey();
                ComponentGroup value = setEntry.getValue();
                if (availabilityHashMap.get(key) == null) {
                    if (value.getRequiredServices().isEmpty()) {
                        //Terminal Group
                        double availability = calculateGroupAvailability(value);
                        availabilityHashMap.put(key, availability);
                        totalAvailability = availability;
                    } else {
                        //Non terminal group, so we calculate availability recursively
                        if (canBeCalculated(value, availabilityHashMap, architectureComponentGroups)) {
                            double groupAvailability = calculateGroupAvailability(value);
                            for (ComponentGroup cg : value.getRequiredGroups()) {
                                groupAvailability *= availabilityHashMap.get(cg.getName());
                            }
                            availabilityHashMap.put(value.getName(), groupAvailability);
                            totalAvailability = groupAvailability;
                        }
                    }
                }
            }
        }
        return totalAvailability;
    }

    private static boolean canBeCalculated(ComponentGroup componentGroup, HashMap<String, Double> availabilityHashMap, HashMap<String, ComponentGroup> architectureComponentGroups) {
        for (ComponentGroup cg : componentGroup.getRequiredGroups()) {
            if (availabilityHashMap.get(cg.getName()) == null) {
                return false;
            }
        }
        return true;
    }

    private static boolean requiredServicesMatchProvidedSerivces(Set<RequiredService> set1, Set<ProvidedService> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }
        HashSet<String> requiredServicesNames = new HashSet<>();
        HashSet<String> providedServicesNames = new HashSet<>();
        for (RequiredService rs : set1) {
            requiredServicesNames.add(rs.getName());
        }
        for (ProvidedService ps : set2) {
            providedServicesNames.add(ps.getName());
        }
        return requiredServicesNames.containsAll(providedServicesNames);
    }

    private static double calculateGroupAvailability(ComponentGroup componentGroup) {
        Set<Component> components = componentGroup.getComponents();
        if (components.size() == 1) {
            return components.iterator().next().getAvailability();
        } else {
            double availabilityMul = 1;
            for (Component component : components) {
                availabilityMul *= 1 - component.getAvailability();
            }
            return 1 - availabilityMul;
        }
    }

    private static boolean completelyCalculated(HashMap<String, Double> availabilityHashMap) {
        for (Double value : availabilityHashMap.values()) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }

    public static double TotalDynamicAvailability(HashMap<String, HashSet<Component>> architectureComponentGroups, Workflow workflow) {
        //TODO
        return 0;
    }

    /**
     * <p>
     * Calculates the total cost for the specified architecture.
     * </p>
     * <p>
     * The architecture cost is the sum of the cost of every component that are in the architecture.
     * </p>
     *
     * @param architecture the architecture to be analyzed.
     *
     * @return the total cost for the specified architecture.
     */
    public static double TotalCost(Architecture architecture) {
        double totalCost = 0;
        for (Component component : architecture.getComponents()) {
            totalCost += component.getCost();
        }
        return totalCost;
    }

    /**
     * Checks if an architecture is suitable for the target cost.
     *
     * @param architecture     the architecture to be analyzed.
     * @param systemTargetCost the target cost.
     *
     * @return <code>true</code> if the architecture is suitable, <code>false</code> otherwise.
     */
    public static boolean SuitableForCost(Architecture architecture, double systemTargetCost) {
        return GlobalCostSystem(architecture, systemTargetCost) >= 1;
    }

    /**
     * Checks if an architecture is suitable for the target availability.
     *
     * @param architecture             the architecture to be analyzed.
     * @param systemTargetAvailability the target cost.
     *
     * @return <code>true</code> if the architecture is suitable, <code>false</code> otherwise.
     */
    public static boolean SuitableForAvailability(Architecture architecture, double systemTargetAvailability) {
        return GlobalAvailabilitySystem(architecture, systemTargetAvailability) >= 1;
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
            if (SuitableForAvailability(architecture, systemTargetAvailability)) {
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
            if (SuitableForCost(architecture, systemTargetCost)) {
                eligibleArchitectures.add(architecture);
            }
        }
        return eligibleArchitectures;
    }
}
