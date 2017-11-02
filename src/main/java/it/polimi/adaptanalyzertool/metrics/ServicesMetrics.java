package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.logic.*;

import java.util.HashMap;

/**
 * This class contains all the metrics for the Services.
 * <p>
 * It has to be used as a static class since no object must be instantiated before calling the methods that are
 * all public and static.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see it.polimi.adaptanalyzertool.logic.AbstractService AbstractService
 * @see it.polimi.adaptanalyzertool.logic.ProvidedService ProvidedService
 * @see it.polimi.adaptanalyzertool.logic.RequiredService RequiredService
 */
public final class ServicesMetrics {

    /**
     * Defines the number of times a service is executed in a given architecture.
     *
     * @param architecture the architecture where the service is.
     * @param service      the service that has to be used to calculate its executions.
     *
     * @return the number of execution of the given service.
     */
    public static double NumberOfExecutions(Architecture architecture, AbstractService service) {
        double probability = 1;
        double execs = 1;
        double numberOfComponentExecutions = 0;
        double result = 0;
        String serviceName = service.getName();
        for (Component component : architecture.getComponents().values()) {
            if (component.getRequiredServices().containsKey(serviceName)) {
                RequiredService requiredService = component.getRequiredServices().get(serviceName);
                probability = requiredService.getUsedProbability();
                execs = requiredService.getNumberOfExecutions();
                numberOfComponentExecutions = componentExecutionTimes(architecture, component);
            }
            result += probability * execs * numberOfComponentExecutions;
            numberOfComponentExecutions = 0;
        }
        if (result == 0) {
            return 1;
        }
        return result;
    }

    /**
     * Defines the probability that a given service is running in a given moment in an architecture.
     *
     * @param architecture the architecture where the service is.
     * @param service      the service that has to be used to calculate its probability to be running, can be a
     *                     ProvidedService or a RequiredService.
     *
     * @return the probability of a service to be running in a given moment.
     */
    public static double ProbabilityToBeRunning(Architecture architecture, AbstractService service) {
        double totalExecutionTimes = 0;
        for (AbstractService abstractService : collectServices(architecture).values()) {
            totalExecutionTimes += NumberOfExecutions(architecture, abstractService);
        }
        return NumberOfExecutions(architecture, service) / totalExecutionTimes;
    }

    /**
     * //TODO not clear on thesis
     *
     * @param architecture
     * @param component
     *
     * @return
     */
    public static double WeightResidenceTime(Architecture architecture, Component component) {
        double totalExecutionTime = 0;
        for (Component architectureComponent : architecture.getComponents().values()) {
            totalExecutionTime += componentExecutionTimes(architecture, architectureComponent);
        }
        return component.getExecutionTime() / totalExecutionTime;
    }

    private static double componentExecutionTimes(Architecture architecture, Component component) {
        double usedProbability = 0;
        int timesFoundAsRequired = 0;
        for (ProvidedService providedService : component.getProvidedServices().values()) {
            for (Component providingComponent : architecture.getComponents().values()) {
                if (providingComponent.getRequiredServices().containsKey(providedService.getName())) {
                    timesFoundAsRequired += 1;
                    usedProbability += providingComponent.getRequiredServices().get(providedService.getName()).getUsedProbability();
                }
            }
        }
        if (timesFoundAsRequired == 0) {
            return 1;
        }
        return usedProbability;
    }

    private static HashMap<String, AbstractService> collectServices(Architecture architecture) {
        HashMap<String, AbstractService> servicesHashMap = new HashMap<>();
        for (Component component : architecture.getComponents().values()) {
            servicesHashMap.putAll(component.getRequiredServices());
            servicesHashMap.putAll(component.getProvidedServices());
        }
        return servicesHashMap;
    }
}
