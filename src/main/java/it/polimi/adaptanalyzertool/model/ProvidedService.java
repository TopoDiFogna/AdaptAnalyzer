package it.polimi.adaptanalyzertool.model;

/**
 * A service provided by a component, identified by its name.
 * <p>
 * It also contains the time to execute this service.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see Component
 */
public class ProvidedService extends AbstractService {

    private double executionTime;

    /**
     * Constructor for a Provided Service; only requires the service name.
     *
     * @param name          the name of the service.
     * @param executionTime the time this service needs to complete its execution.
     */
    public ProvidedService(String name, double executionTime) {
        super(name);
        this.executionTime = executionTime;
    }

    /**
     * Gets the time in seconds that this service requires to complete.
     *
     * @return time in seconds that this service requires to complete.
     */
    public double getExecutionTime() {
        return executionTime;
    }
}
