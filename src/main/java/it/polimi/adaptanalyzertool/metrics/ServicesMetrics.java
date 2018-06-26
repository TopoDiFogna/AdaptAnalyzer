package it.polimi.adaptanalyzertool.metrics;

import it.polimi.adaptanalyzertool.model.*;

import java.util.HashMap;
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


    /**
     * This metrics calculates the probability to find a given component active considering the dynamic analysis
     * of the architecture.
     * <p>
     * It considers all the possible paths available in the architecture workflow. Note that the workflow are
     * specified per architecture and must be inputted by the user.
     * </p>
     *
     * @param architectureGroups the groups that the components form when they are replicated.
     * @param workflow           the workflow associated with this architecture.
     * @param service            the service to be analyzed.
     *
     * @return the probability to find a component active given a workflow for the architecture.
     * @see Workflow
     */
    public static double InAction(HashMap<String, ComponentGroup> architectureGroups, Workflow workflow, ProvidedService service) {
        double inActionService = 0;
        double serviceExecutionTime = 0;
        double totalExecutionTime = 0;
        for (Path path : workflow.getPaths()) {
            double pathProbability = path.getExecutionProbability();
            for (Message message : path.getMessagesList()) {
                ComponentGroup startingGroup = architectureGroups.get(message.getStartingGroupName());//TODO imported archis may throw errors
                if (startingGroup.getProvidedServices().contains(service)) {
                    serviceExecutionTime += service.getExecutionTime();
                }
                for (ProvidedService ps : startingGroup.getProvidedServices()) {
                    totalExecutionTime += ps.getExecutionTime();
                }
            }

            inActionService += serviceExecutionTime * pathProbability;

        }
        return inActionService / totalExecutionTime;
    }
}
