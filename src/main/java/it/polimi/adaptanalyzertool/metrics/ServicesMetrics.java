package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.*;

import java.util.HashSet;

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

    private ServicesMetrics() {
    }

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
        for (Component component : architecture.getComponents()) {
            if (component.getRequiredServicesNames().contains(serviceName)) {
                found = true;
                RequiredService requiredService = component.getSingleRequiredService(serviceName);
                double execProbability = requiredService.getUsedProbability();
                double executions = requiredService.getNumberOfExecutionsPerCall();
                double noOfExecs = 1;
                for (ProvidedService ps : component.getProvidedServices()) {
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
        for (AbstractService abstractService : collectServicesFromArchitecture(architecture)) {
            totalExecutionTimes += NumberOfExecutions(architecture, abstractService);
        }
        return NumberOfExecutions(architecture, service) * 100 / totalExecutionTimes;
    }

    /**
     * The absolute adaptability of a service (AAS) measures the number of used components for providing a given service.
     *
     * @param architecture the architecture where the service is.
     * @param service      the service that is offered by the components.
     *
     * @return the number of used components that provide the required service.
     */
    public static int AbsoluteAdaptability(Architecture architecture, AbstractService service) {
        int usedProvidedTimes = 0;
        for (Component component : architecture.getComponents()) {
            if (component.getProvidedServicesNames().contains(service.getName()) && component.isUsed()) {
                usedProvidedTimes += 1;
            }
        }
        return usedProvidedTimes;
    }

    /**
     * The relative adaptability of a service (RAS) measures the number of used components that provide a given service with
     * respect to the number of components actually offering such service.
     *
     * @param architecture the architecture where the service is.
     * @param service      the service that is offered by the components.
     *
     * @return the percentage of used component for the required service.
     */
    public static double RelativeAdaptability(Architecture architecture, AbstractService service) {
        int usedProvidedTimes = AbsoluteAdaptability(architecture, service);
        double providedTimes = 0;
        for (Component component : architecture.getComponents()) {
            if (component.getProvidedServicesNames().contains(service.getName())) {
                providedTimes += 1;
            }
        }
        return usedProvidedTimes / providedTimes;
    }

    /**
     * Collects all the services in a given architecture and returns them.
     *
     * @param architecture the architecture where to collect services.
     *
     * @return HashMap containing all the services found in the given architecture.
     */
    private static HashSet<AbstractService> collectServicesFromArchitecture(Architecture architecture) {
        HashSet<AbstractService> servicesHashSet = new HashSet<>();
        for (Component component : architecture.getComponents()) {
            servicesHashSet.addAll(component.getRequiredServices());
            servicesHashSet.addAll(component.getProvidedServices());
        }
        return servicesHashSet;
    }

    static HashSet<ProvidedService> collectProvidedServices(Architecture architecture) {
        HashSet<ProvidedService> servicesHashSet = new HashSet<>();
        for (Component component : architecture.getComponents()) {
            servicesHashSet.addAll(component.getProvidedServices());
        }
        return servicesHashSet;
    }
}
