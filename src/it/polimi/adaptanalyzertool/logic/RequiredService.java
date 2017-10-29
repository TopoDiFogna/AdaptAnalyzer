package it.polimi.adaptanalyzertool.logic;

/**
 * A service provided by a component, identified by its name.
 * <p>
 *      A Required Service is more complicated than a Provided Service since it has also a used probability and a
 *      number of executions based on the {@link Architecture}.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class RequiredService extends AbstractService {

    private double usedProbability;
    private int numberOfExecutions;

    /**
     * Constructor for the Required Service.
     *
     * @param name the name for the service.
     * @param usedProbability probability for this service to be used.
     * @param numberOfExecutions how many times this service will run.
     */
    public RequiredService(String name, double usedProbability, int numberOfExecutions){
        super(name);
        this.usedProbability = usedProbability;
        this.numberOfExecutions = numberOfExecutions;
    }

    /**
     * Gets the probability for this service to be used.
     *
     * @return the probability to be used.
     */
    public double getUsedProbability() {
        return usedProbability;
    }

    /**
     * Changes the probability that this service will be used.
     *
     * @param usedProbability the new probability to be used.
     */
    public void setUsedProbability(double usedProbability) {
        this.usedProbability = usedProbability;
    }

    /**
     * Gets how many times this service will be run.
     *
     * @return how many times this service will be run.
     */
    public int getNumberOfExecutions() {
        return numberOfExecutions;
    }

    /**
     * Changes how many times this service will be run.
     *
     * @param numberOfExecutions the new number of times this service will be run.
     */
    public void setNumberOfExecutions(int numberOfExecutions) {
        this.numberOfExecutions = numberOfExecutions;
    }
}
