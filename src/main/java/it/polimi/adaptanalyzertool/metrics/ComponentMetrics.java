package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.*;

import static it.polimi.adaptanalyzertool.metrics.ServicesMetrics.NumberOfExecutions;

/**
 * This class contains all the metrics for the components.
 * <p>
 * It has to be used as a static class since no object must be instantiated before calling the methods that are
 * all public and static.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see it.polimi.adaptanalyzertool.model.Component Component
 */
public final class ComponentMetrics {

    private ComponentMetrics() {
    }

    /**
     * Defines the ratio between a component availability and the target system availability.
     * <p>
     * This result is from the hypothesis that a component with a high availability can provide, at first glance,
     * more guarantees of functioning.
     * </p>
     * <p>
     * Calculates the Fitness Ratio w.r.t. Availability as specified in the thesis by Zanotti of 2016-12 at page 26.
     * </p>
     *
     * @param targetSystemAvailability the desired availability for the system
     * @param componentAvailability    the component availability
     * @return the Fitness Ratio w.r.t. Availability of a component.
     */
    public static double FitnessRatioAvailability(double targetSystemAvailability, double componentAvailability) {
        return (1 - targetSystemAvailability) / (1 - componentAvailability);
    }

    /**
     * Defines if a component is eligible to be used in a system using only the system target availability.
     * <p>
     * Calculates the Boolean Suitability w.r.t. Availability as specified in the thesis by Zanotti of 2016-12
     * at page 27.
     * </p>
     *
     * @param fra the Fitness Ratio w.r.t. Availability.
     * @return the Boolean suitability w.r.t. Availability.
     * @see #FitnessRatioAvailability(double, double)
     */
    public static boolean BooleanSuitabilityAvailability(double fra) {
        return fra >= 1;
    }

    /**
     * Defines the ratio between a component cost and the system target cost.
     * <p>
     * With the same component cost, if the system target cost grows higher the Fitness Ratio w.r.t. Cost becomes
     * bigger. This implies that the bigger the component cost and the system target cost gap is, the more can be
     * saved from buying this component w.r.t. the maximum budget invested in buying the components.
     * </p>
     * <p>
     * Calculates the Fitness Ratio w.r.t. Cost as specified in the thesis by Zanotti of 2016-12
     * at page 28.
     * </p>
     *
     * @param systemTargetCost the system target cost.
     * @param componentCost    the component cost.
     * @return the Fitness ratio w.r.t. Cost of a component
     */
    public static double FitnessRatioCost(double systemTargetCost, double componentCost) {
        return systemTargetCost / componentCost;
    }

    /**
     * Defines if a component is eligible to be used in a system considering only the system target cost.
     * <p>
     * Calculates the Boolean Suitability w.r.t. Cost as specified in the thesis by Zanotti of 2016-12
     * at page 29.
     * </p>
     *
     * @param frc the Fitness Ratio w.r.t. Cost.
     * @return the Boolean suitability w.r.t. Cost.
     * @see #FitnessRatioCost(double, double)
     */
    public static boolean BooleanSuitabilityCost(double frc) {
        return frc >= 1;
    }

    /**
     * Calculates which fraction of time a component is running w.r.t total running time of the architecture.
     * <p>
     * Higher results means that the component runs more than others.
     * </p>
     *
     * @param architecture the architecture where the service is.
     * @param component    the service that has to be used to calculate its weight residence time, can be a
     *                     ProvidedService or a RequiredService.
     * @return the weight residence time of a given service.
     */
    public static double WeightResidenceTime(Architecture architecture, Component component) {
        double totalTime = 0;
        double componentTime = 0;
        for (Component architectureComponent : architecture.getComponents()) {
            for (ProvidedService providedService : architectureComponent.getProvidedServices()) {
                totalTime += NumberOfExecutions(architecture, providedService) * providedService.getExecutionTime();
            }
        }
        for (ProvidedService providedService : component.getProvidedServices()) {
            componentTime += NumberOfExecutions(architecture, providedService) * providedService.getExecutionTime();
        }
        return componentTime / totalTime;
    }

    /**
     * This metrics calculates the probability to find a given component active considering the dynamic analysis
     * of the architecture.
     * <p>
     * It considers all the possible paths available in the architecture workflow. Note that the workflow are
     * specified per architecture and must be inputted by the user.
     * </p>
     *
     * @param architecture the architecture where the component resides.
     * @param workflow     the workflow associated with this architecture.
     * @param component    the selected component to calculate the probability on.
     * @return the probability to find a component active given a workflow for the architecture.
     * @see Workflow
     */
    public static double InAction(Architecture architecture, Workflow workflow, Component component) {
        double inAction = 0;
        for (Path path : workflow.getPaths()) {
            double pathProb = path.getExecutionProbability();
            double totExecTime = 0;
            int selectedComponentExes = 0;
            double selectedComponentExecTime = 0;
            for (Message message : path.getMessagesList()) {
                Component currComponent = architecture.getSingleComponent(message.getStartingComponentName());
                double maxExecTime = 0;
                for (ProvidedService service : currComponent.getProvidedServices()) {
                    if (service.getExecutionTime() > maxExecTime) {
                        maxExecTime = service.getExecutionTime();
                        if (currComponent == component) {
                            selectedComponentExecTime = maxExecTime;
                        }
                    }
                }
                totExecTime += maxExecTime;
                if (currComponent == component) {
                    selectedComponentExes++;
                }
            }
            inAction += pathProb * selectedComponentExes * selectedComponentExecTime / totExecTime;
        }
        return inAction;
    }
}
