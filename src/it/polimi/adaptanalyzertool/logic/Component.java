package it.polimi.adaptanalyzertool.logic;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains all the information about a component for an architecture.
 * <p>
 * <p>
 * The components are the fundamental parts of an architecture since they define the architecture.
 * A component cannot be more than once in an architecture but can be part of multiple architectures at once.
 * </p>
 * <p>
 * Components should at least require or provide a service to be useful for an architecture.
 * </p>
 *
 * @author Paolo Paterna
 * @version %I%, %G%
 */
public class Component {

    private float cost;
    private float availability;
    private float executrionTime;
    private Color color;
    private Set<Service> servicesProvided;
    private Set<Service> servicesRequired;

    /**
     * Basic constructor for the component. Only the mandatory attributes are required; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Color: WHITE</li>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param cost          the cost of the component.
     * @param availability  availability expressed in 0-1 range.
     * @param executionTime execution time expressed in seconds.
     *
     * @see Service
     */
    public Component(float cost, float availability, float executionTime) {
        this(cost, availability, executionTime, Color.WHITE, new HashSet<>(), new HashSet<>());
    }

    /**
     * Only the mandatory attributes are required but color; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param cost          the cost of the component.
     * @param availability  availability expressed in 0-1 range.
     * @param executionTime execution time expressed in seconds.
     * @param color         color for the component in the UI.
     *
     * @see Service
     */
    public Component(float cost, float availability, float executionTime, Color color) {
        this(cost, availability, executionTime, color, new HashSet<>(), new HashSet<>());
    }

    /**
     * All the attributes are required but Services Provided; Services Required are
     * set to default values:
     * <ul>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param executionTime    execution time expressed in seconds.
     * @param color            color for the component in the UI.
     * @param servicesProvided the services provided by this component.
     *
     * @see Service
     */
    public Component(float cost, float availability, float executionTime, Color color, HashSet<Service> servicesProvided) {
        this(cost, availability, executionTime, color, servicesProvided, new HashSet<>());
    }

    /**
     * Constructor for the component with all the attributes required.
     *
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param executionTime    execution time expressed in seconds.
     * @param color            color for the component in the UI.
     * @param servicesProvided the services provided by this component.
     * @param servicesRequired the services required by this component.
     *
     * @see Service
     */
    public Component(float cost, float availability, float executionTime, Color color, HashSet<Service> servicesProvided, HashSet<Service> servicesRequired) throws IllegalArgumentException {
        this.cost = cost;
        this.availability = availability;
        this.executrionTime = executionTime;
        this.color = color;
        this.servicesProvided = servicesProvided;
        this.servicesRequired = servicesRequired;
    }

    /**
     * Gets the cost for the current Component.
     *
     * @return the cost as a float for the current Component.
     */
    public float getCost() {
        return cost;
    }

    /**
     * Changes the cost for the current Component.
     *
     * @param cost the new cost for the Component.
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Gets the availability for the current Component.
     *
     * @return the availability for the current component in the range 0-1.
     */
    public float getAvailability() {
        return availability;
    }

    /**
     * Changes the cost for the current Component.
     *
     * @param availability availability expressed in a range 0-1
     */
    public void setAvailability(float availability) {
        this.availability = availability;
    }

    /**
     * Gets the execution time for the current Component.
     *
     * @return the execution time as a float for the current Component.
     */
    public float getExecutrionTime() {
        return executrionTime;
    }

    /**
     * Changes the execution time for the current Component.
     *
     * @param executrionTime the new execution time for the Component.
     */
    public void setExecutrionTime(float executrionTime) {
        this.executrionTime = executrionTime;
    }

    /**
     * Gets the color for the current Component.
     *
     * @return the color for the current Component.
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Changes the color for the current Component.
     *
     * @param color the new execution time for the Component.
     *
     * @see Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the services provided by the current Component.
     *
     * @return a <code>Set</code> containing the services provided by the current component.
     */
    public Set<Service> getServicesProvided() {
        return servicesProvided;
    }

    /**
     * Adds a provided service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceProvided the provided service that has to be added to the current component.
     *
     * @return <code>true</code> if the service is successfully added to the component;
     * <code>false</code> if the service cannot be added because it is already in.
     */
    public boolean addServiceProvided(Service serviceProvided) {
        return this.servicesProvided.add(serviceProvided);
    }

    /**
     * Removes a provided service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceProvided the provided service to be removed from the component.
     *
     * @return <code>true</code> if the service is removed, thus the set has changed; <code>false</code> otherwise.
     */
    public boolean removeServiceProvided(Service serviceProvided) {
        return servicesProvided.remove(serviceProvided);
    }

    /**
     * Removes all the provided services from the current component.
     *
     * @return <code>true</code> if the provided services' set is successfully emptied; <code>false</code> otherwise.
     */
    public boolean removeAllServicesProvided() {
        try {
            servicesProvided.clear();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    /**
     * Gets the services required by the current Component.
     *
     * @return a <code>Set</code> containing the services required by the current component.
     */
    public Set<Service> getServicesRequired() {
        return servicesRequired;
    }

    /**
     * Adds a required service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceRequired the required service that has to be added to the current component.
     *
     * @return <code>true</code> if the service is successfully added to the component;
     * <code>false</code> if the service cannot be added because it is already in.
     */
    public boolean addServiceRequired(Service serviceRequired) {
        return this.servicesRequired.add(serviceRequired);
    }

    /**
     * Removes a required service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceRequired the required service to be removed from the component.
     *
     * @return <code>true</code> if the service is removed, thus the set has changed; <code>false</code> otherwise.
     */
    public boolean removeServiceRequired(Service serviceRequired) {
        return servicesRequired.remove(serviceRequired);
    }

    /**
     * Removes all the required services from the current component.
     *
     * @return <code>true</code> if the provided services' set is successfully emptied; <code>false</code> otherwise.
     */
    public boolean removeAllServicesRequired() {
        try {
            servicesRequired.clear();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    /**
     * Removes all the services, required and provided, from the current component.
     *
     * @return <code>true</code> if the services' sets are successfully emptied; <code>false</code> otherwise.
     */
    public boolean removeAllServices() {
        return removeAllServicesProvided() && removeAllServicesRequired();
    }
}
