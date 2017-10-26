package it.polimi.adaptanalyzertool.logic;

/**
 * Base class for the service which defines the details about a component's service. It provide base functionalities
 * shared among all the service subclasses.
 * <p>
 *     Services can be required or provided by a {@link Component}. Every service is identified by its name.
 * </p>
 *
 * @author Paolo Paterna
 * @version %I%, %G%
 */
public abstract class AbstractService {
    private String name;

    /**
     * Base constructor, the only required parameter is the service name.
     *
     * @param name the service name.
     */
    AbstractService(String name){
        this.name = name;
    }

    /**
     * Gets the service name.
     *
     * @return the service name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the service name. Since every service is identified by its name changing the name of a service affects
     * the {@link #equals(Object) AbstractService.equals(Object)}.
     *
     * @param name the new name for the service.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Since every service is identified by its name equals has to be overridden to honour this.
     *
     * @param obj the other object to compare.
     * @return <code>true</code> if the other object is not null <u>and</u> it has the same class and name;
     *         <code>false</code> otherwise.
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
