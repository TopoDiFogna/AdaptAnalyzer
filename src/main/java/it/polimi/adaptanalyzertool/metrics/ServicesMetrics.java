package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.*;

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
 * @see it.polimi.adaptanalyzertool.model.AbstractService AbstractService
 * @see it.polimi.adaptanalyzertool.model.ProvidedService ProvidedService
 * @see it.polimi.adaptanalyzertool.model.RequiredService RequiredService
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
        String serviceName = service.getName();
        double result = 0;
        boolean found = false;
        for (Component component : architecture.getComponents().values()) {
            if (component.getRequiredServices().containsKey(serviceName)) {
                found = true;
                RequiredService requiredService = component.getRequiredServices().get(serviceName);
                double execProbability = requiredService.getUsedProbability();
                double executions = requiredService.getNumberOfExecutions();
                double noOfExecs = 1;
                for (ProvidedService ps : component.getProvidedServices().values()) {
                    noOfExecs = NumberOfExecutions(architecture, ps);
                }
                result += execProbability * executions * noOfExecs;
            }
        }
        if (found) {
            return result;
        } else {
            return 1;
        }
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
        for (AbstractService abstractService : collectServicesFromArchitecture(architecture).values()) {
            totalExecutionTimes += NumberOfExecutions(architecture, abstractService);
        }
        return NumberOfExecutions(architecture, service) / totalExecutionTimes;
    }

    /**
     * Calculates which fraction of time a component is running w.r.t total running time of the architecture.
     * <p>
     *     //TODO finish javadoc
     * </p>
     *
     * @param architecture
     * @param component
     *
     * @return
     */
    public static double WeightResidenceTime(Architecture architecture, Component component) {
        double totalTime = 0;
        double componentTime = 0;
        for (Component architectureComponent: architecture.getComponents().values()){
            for (ProvidedService providedService : architectureComponent.getProvidedServices().values()){
                totalTime += NumberOfExecutions(architecture, providedService) * providedService.getExecutionTime();
            }
        }
        for (ProvidedService providedService : component.getProvidedServices().values()){
            componentTime += NumberOfExecutions(architecture, providedService) * providedService.getExecutionTime();
        }
        return componentTime/totalTime;
    }

    private static HashMap<String, AbstractService> collectServicesFromArchitecture(Architecture architecture) {
        HashMap<String, AbstractService> servicesHashMap = new HashMap<>();
        for (Component component : architecture.getComponents().values()) {
            servicesHashMap.putAll(component.getRequiredServices());
            servicesHashMap.putAll(component.getProvidedServices());
        }
        return servicesHashMap;
    }
}
