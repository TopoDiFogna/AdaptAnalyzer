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

    private static long recursiveCallCounts;

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

    /**
     * <p>
     * This methods calculate the Availability of an architecture without considering actual workflows of the
     * architecture.
     * </p>
     * <p>
     * It considers all components as used and considers a call to the main service to use always all the components.
     * </p>
     *
     * @param architectureComponentGroups the groups that the components form when they are replicated.
     *
     * @return the total availability of the architecture without considering any workflow.
     */
    @Deprecated
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
                        if (canBeCalculated(value, availabilityHashMap)) {
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

    private static boolean canBeCalculated(ComponentGroup componentGroup, HashMap<String, Double> availabilityHashMap) {
        for (ComponentGroup cg : componentGroup.getRequiredGroups()) {
            if (availabilityHashMap.get(cg.getName()) == null) {
                return false;
            }
        }
        return true;
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

//    public static double TotalDynamicAvailability(HashMap<String, ComponentGroup> architectureComponentGroups,
//                                                  Workflow workflow) {
//        Set<Path> paths = workflow.getPaths();
//
//        //TODO
//        return 0;
//    }

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
     * @return {@code true} if the architecture is suitable, {@code false} otherwise.
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
     * @return {@code true} if the architecture is suitable, {@code false} otherwise.
     */
    public static boolean SuitableForAvailability(Architecture architecture, double systemTargetAvailability) {
        return GlobalAvailabilitySystem(architecture, systemTargetAvailability) >= 1;
    }

    /**
     * Creates all possible Architecture with the specified components.
     *
     * @param architecture the base architecture containing all components.
     *
     * @return a {@link QualityHolder} object containing all the min/max value for every quality and the respective
     * architecture.
     */
    public static HashMap<Double, QualityHolder> CheckAllArchitectures(Architecture architecture) {
        HashMap<String, ComponentGroup> architectureComponentGroups = getComponentGroups(architecture);
        ComponentGroup mainFunctionalityGroup = findMainFunctionality(architectureComponentGroups);
        List<Component> currentList = new ArrayList<>();
        Set<Set<String>> componentsTreatedSet = new HashSet<>();
        List<Component> candidatesToInclude = new ArrayList<>(mainFunctionalityGroup.getComponents());

        recursiveCallCounts = 0;
        HashMap<Double, QualityHolder> adaptabilityQualityHashMap = new HashMap<>();
        Architecture newArch = architecture.clone(architecture.getName());
        for (Component component : newArch.getComponents()) {
            component.setUsed(false);
        }

        recursiveCalculator(adaptabilityQualityHashMap, newArch, currentList, componentsTreatedSet, candidatesToInclude);

        return adaptabilityQualityHashMap;
    }

    private static void recursiveCalculator(HashMap<Double, QualityHolder> qualityHolderHashMap,
                                            Architecture fullArchitecture,
                                            List<Component> currentList,
                                            Set<Set<String>> componentsTreatedSet,
                                            List<Component> candidatesToInclude) {
        recursiveCallCounts++;

        if (candidatesToInclude.isEmpty()) {
            return;
        }

        List<Component> candidatesToIncludeClone = new ArrayList<>(candidatesToInclude);
        for (Component addedComponent : candidatesToInclude) {

            List<Component> testList = new ArrayList<>(candidatesToIncludeClone);
            List<Component> currentListClone = new ArrayList<>(currentList);

            currentListClone.add(addedComponent);

            candidatesToIncludeClone.remove(addedComponent);
            testList.remove(addedComponent);
            for (AbstractService requiredService : addedComponent.getRequiredServices()) {
                for (Component c : fullArchitecture.getComponents()) {
                    for (AbstractService s : c.getProvidedServices()) {
                        if (s.getName().equals(requiredService.getName())) {
                            testList.add(0, c);
                        }
                    }
                }
            }

            //Set-up the new architecture to be saved if necessary
            Architecture ar = fullArchitecture.clone(String.valueOf(recursiveCallCounts));
            Set<String> usedComponentsNames = new HashSet<>();
            for (Component component : ar.getComponents()) {
                for (Component currentComponent : currentListClone) {
                    if (component.getName().equals(currentComponent.getName())) {
                        component.setUsed(true);
                        usedComponentsNames.add(currentComponent.getName());
                        break;
                    }
                }
            }

            updateQualityHolder(qualityHolderHashMap, ar);
            boolean found = false;
            for (Set<String> s : componentsTreatedSet){
                if (s.equals(usedComponentsNames)){
                    found = true;
                }
            }
            if(!found) {
                componentsTreatedSet.add(usedComponentsNames);
                recursiveCalculator(qualityHolderHashMap, fullArchitecture, currentListClone, componentsTreatedSet, testList);
            }
        }
    }

    private static void updateQualityHolder(HashMap<Double, QualityHolder> qualityHolderHashMap, Architecture architecture){
        //Perform the calculation on the new architecture
        double adaptability = AdaptabilityMetrics.LevelSystemAdaptability(architecture);
        double cost = 0;
        for (Component component : architecture.getComponents()) {
            if (component.isUsed()) {
                cost += component.getCost();
            }
        }

        //Add the new results to the main hashmap
        if (qualityHolderHashMap.get(adaptability) != null) {
            qualityHolderHashMap.get(adaptability).modifyCostIfNecessary(architecture, cost);
        } else {
            QualityHolder qh = new QualityHolder();
            qh.modifyCostIfNecessary(architecture, cost);
            qualityHolderHashMap.put(adaptability, qh);
        }
    }

    private static ComponentGroup findMainFunctionality(HashMap<String, ComponentGroup> architectureComponentGroups) {
        ComponentGroup testedGroup = null;
        for (ComponentGroup group1 : architectureComponentGroups.values()) {
            testedGroup = group1;
            for (ComponentGroup group2 : architectureComponentGroups.values()) {
                if (group2.getRequiredGroups().contains(group1)) {
                    testedGroup = null;
                    break;
                }
            }
            if (testedGroup != null) {
                break;
            }
        }
        return testedGroup;
    }

    /**
     * <p>
     * Creates the groups that contain the components that all provide and require the same services.
     * </p>
     * <p>
     * Formally a group contains a component and all its redundant copies.
     * </p>
     *
     * @param architecture the architecture that needs to be analyzed.
     *
     * @return a {@code HashMap} containing all the groups whose keys are the name of the main component.
     */
    public static HashMap<String, ComponentGroup> getComponentGroups(Architecture architecture) {
        HashMap<String, ComponentGroup> componentsGroups = new HashMap<>();
        for (Component component : architecture.getComponents()) {
            boolean found = false;
            for (ComponentGroup componentGroup : componentsGroups.values()) {
                if (component.getRequiredServices().equals(componentGroup.getRequiredServices()) &&
                        component.getProvidedServices().equals(componentGroup.getProvidedServices()) &&
                        !found) {
                    componentGroup.addComponent(component);
                    found = true;
                }
            }
            if (!found) {
                ComponentGroup componentGroup = new ComponentGroup(component.getName());
                componentGroup.addComponent(component);
                componentsGroups.put(component.getName(), componentGroup);
            }
        }
        for (ComponentGroup cg1 : componentsGroups.values()) {
            Set<String> requiredServicesNames = cg1.getRequiredServicesNames();
            for (ComponentGroup cg2 : componentsGroups.values()) {
                for (String ps : cg2.getProvidedServicesNames()) {
                    if (requiredServicesNames.contains(ps)) {
                        cg1.addRequiredGroup(cg2);
                    }
                }
            }
        }
        return componentsGroups;
    }

    /**
     * Defines which architectures are suitable for the specified availability.
     *
     * @param architectures            the architectures that need to be tested.
     * @param systemTargetAvailability the target availability.
     *
     * @return an {@code ArrayList} containing a possible subset, even empty, of architectures that are suitable
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
     * @return an {@code ArrayList} containing a possible subset, even empty, of architectures that are suitable
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
