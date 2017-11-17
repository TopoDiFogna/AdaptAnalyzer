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
     * @param name the name of the service.
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

    /**
     * Changes the execution time this service requires to complete.
     *
     * @param executionTime the new execution time to be used.
     */

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }
}
