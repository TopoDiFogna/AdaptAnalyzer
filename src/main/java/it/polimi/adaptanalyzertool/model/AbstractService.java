package it.polimi.adaptanalyzertool.model;

/**
 * Base class for the service which defines the details about a component's service. It provide base functionalities
 * shared among all the service subclasses.
 * <p>
 * Services can be required or provided by a {@link Component}. Every service is identified by its name.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public abstract class AbstractService {
    private final String name;

    /**
     * Base constructor, the only required parameter is the service name.
     *
     * @param name the service name.
     */
    AbstractService(String name) {
        this.name = name;
    }

    /**
     * Gets the service name.
     * <p>
     * The name identifies the component and acts as a key in the {@code HashMap} used in the implementation.
     * </p>
     *
     * @return the service name.
     */
    public String getName() {
        return name;
    }


    /**
     * Since every service is identified by its name equals has to be overridden to honour this. //TODO remove this?
     *
     * @param obj the other object to compare.
     * @return {@code true} if the other object is not null <u>and</u> it has the same class and name;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && (getClass() == obj.getClass()) && name.equals(((AbstractService) obj).getName());
    }

    /**
     * Creates the HashCode for this service using its name.
     *
     * @return the unique HashCode for this service based on its name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
