package it.polimi.adaptanalyzertool.metrics;

/**
 * This class contains all the metrics for the components.
 * <p>
 * It has to be used as a static class since no object must be instatianted before calling the methods that are
 * all public and static.
 * </p>
 *
 * @see it.polimi.adaptanalyzertool.logic.Component Component
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public final class ComponentMetrics {

    /**
     * Defines the ratio between a component availability and the target system availability.
     * <p>
     *     This result is from the hypothesis that a component with a high availability can provide, at first glance,
     *     more guarantees of functioning.
     * </p>
     * <p>
     * Calculates the Fitness Ratio w.r.t. Availability as specified in the thesis by Zanotti of 2016-12 at page 26.
     * </p>
     *
     * @param targetSystemAvailability the desired availability for the system
     * @param componentAvailability the component availability
     *
     * @return the Fitness Ratio w.r.t. Availability of a component.
     */
    public static float FitnessRatioAvailability(float targetSystemAvailability, float componentAvailability){
        return (1-targetSystemAvailability)/(1-componentAvailability);
    }

    /**
     * Defines if a component is eligible to be used in a system using only the system target availability.
     * <p>
     *     Calculates the Boolean Suitability w.r.t. Availability as specified in the thesis by Zanotti of 2016-12
     *     at page 27.
     * </p>
     *
     * @see #FitnessRatioAvailability(float, float)
     *
     * @param fra the Fitness Ratio w.r.t. Availability.
     * @return the Boolean suitability w.r.t. Availability.
     */
    public static boolean BooleanSuitabilityAvailability(float fra){
        return fra >= 1;
    }

    /**
     * Defines the ratio between a component cost and the system target cost.
     * <p>
     *     With the same component cost, if the system target cost grows higher the Fitness Ratio w.r.t. Cost becomes
     *     bigger. This implies that the bigger the component cost and the system target cost gap is, the more can be
     *     saved from buying this component w.r.t. the maximum budget invested in buying the components.
     * </p>
     * <p>
     *     Calculates the Fitness Ratio w.r.t. Cost as specified in the thesis by Zanotti of 2016-12
     *     at page 28.
     * </p>
     *
     * @param systemTargetCost the system target cost.
     * @param componentCost the component cost.
     * @return the Fitness ratio w.r.t. Cost of a component
     */
    public static float FitnessRatioCost(float systemTargetCost, float componentCost){
        return systemTargetCost/componentCost;
    }

    /**
     * Defines if a component is eligible to be used in a system considering only the system target cost.
     * <p>
     *     Calculates the Boolean Suitability w.r.t. Cost as specified in the thesis by Zanotti of 2016-12
     *     at page 29.
     * </p>
     *
     * @see #FitnessRatioCost(float, float)
     *
     * @param frc the Fitness Ratio w.r.t. Cost.
     * @return the Boolean suitability w.r.t. Cost.
     */
    public static boolean BooleanSuitabilityCost(float frc){
        return frc >= 1;
    }
}
