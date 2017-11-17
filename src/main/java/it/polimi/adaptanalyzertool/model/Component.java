package it.polimi.adaptanalyzertool.model;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * This class contains all the information about a component for an architecture.
 * <p>
 * The components are the fundamental parts of an architecture since they define the architecture.
 * A component cannot be more than once in an architecture but can be part of multiple architectures at once.
 * </p>
 * <p>
 * Components should at least require or provide a service to be useful for an architecture.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Component {

    private final String name;
    private double cost;
    private double availability;
    private Color color;
    private HashMap<String, ProvidedService> providedServices;
    private HashMap<String, RequiredService> requiredServices;

    /**
     * Basic constructor for the component. Only the mandatory attributes are required; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Color: WHITE</li>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability) {
        this(name, cost, availability, Color.WHITE, new HashMap<>(), new HashMap<>());
    }

    /**
     * Only the mandatory attributes are required but color; all non mandatory attributes are
     * set to default values:
     * <ul>
     * <li>Services Provided: EMPTY</li>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name         the name of the component.
     * @param cost         the cost of the component.
     * @param availability availability expressed in 0-1 range.
     * @param color        color for the component in the UI.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, Color color) {
        this(name, cost, availability, color, new HashMap<>(), new HashMap<>());
    }

    /**
     * All the attributes are required but Services Provided; Services Required are
     * set to default values:
     * <ul>
     * <li>Services Required: EMPTY</li>
     * </ul>
     *
     * @param name             the name of the component.
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param color            color for the component in the UI.
     * @param providedServices the services provided by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, Color color,
                     HashMap<String, ProvidedService> providedServices) {
        this(name, cost, availability, color, providedServices, new HashMap<>());
    }

    /**
     * Constructor for the component with all the attributes required.
     *
     * @param name             the name of the component.
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param color            color for the component in the UI.
     * @param providedServices the services provided by this component.
     * @param requiredServices the services required by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, Color color,
                     HashMap<String, ProvidedService> providedServices,
                     HashMap<String, RequiredService> requiredServices) {
        this.name = name;
        this.cost = cost;
        this.availability = availability;
        this.color = color;
        this.providedServices = providedServices;
        this.requiredServices = requiredServices;
    }

    /**
     * Gets the name of the current component.
     * <p>
     * The name identifies the component and acts as a key in the <code>HashMap</code> used in the implementation.
     * </p>
     *
     * @return the name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost for the current Component.
     *
     * @return the cost as a double for the current Component.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Changes the cost for the current Component.
     *
     * @param cost the new cost for the Component.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the availability for the current Component.
     *
     * @return the availability for the current component in the range 0-1.
     */
    public double getAvailability() {
        return availability;
    }

    /**
     * Changes the cost for the current Component.
     *
     * @param availability availability expressed in a range 0-1
     */
    public void setAvailability(double availability) {
        this.availability = availability;
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
     * @return a <code>HashMap</code> containing the services provided by the current component.
     */
    public HashMap<String, ProvidedService> getProvidedServices() {
        return providedServices;
    }

    /**
     * Adds a provided service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceProvided the provided service that has to be added to the current component.
     */
    public void addProvidedService(ProvidedService serviceProvided) {
        this.providedServices.put(serviceProvided.getName(), serviceProvided);
    }

    /**
     * Removes a provided service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceProvided the provided service to be removed from the component.
     *
     * @return <code>true</code> if the service is removed, thus the set has changed; <code>false</code> otherwise.
     */
    public boolean removeProvidedService(ProvidedService serviceProvided) {
        return providedServices.remove(serviceProvided.getName(), serviceProvided);
    }

    /**
     * Removes all the provided services from the current component.
     *
     * @return <code>true</code> if the provided services' set is successfully emptied; <code>false</code> otherwise.
     */
    public boolean removeAllProvidedServices() {
        try {
            providedServices.clear();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    /**
     * Gets the services required by the current Component.
     *
     * @return a <code>HashMap</code> containing the services required by the current component.
     */
    public HashMap<String, RequiredService> getRequiredServices() {
        return requiredServices;
    }

    /**
     * Adds a required service to the current component. If the service is already in the component it is not
     * duplicated.
     *
     * @param serviceRequired the required service that has to be added to the current component.
     */
    public void addRequiredService(RequiredService serviceRequired) {
        this.requiredServices.put(serviceRequired.getName(), serviceRequired);
    }

    /**
     * Removes a required service from the current component. Returns true if this set contained the element
     * (or equivalently, if this set changed as a result of the call).
     *
     * @param serviceRequired the required service to be removed from the component.
     *
     * @return <code>true</code> if the service is removed, thus the set has changed; <code>false</code> otherwise.
     */
    public boolean removeRequiredService(RequiredService serviceRequired) {
        return requiredServices.remove(serviceRequired.getName(), serviceRequired);
    }

    /**
     * Removes all the required services from the current component.
     *
     * @return <code>true</code> if the provided services' set is successfully emptied; <code>false</code> otherwise.
     */
    public boolean removeAllRequiredServices() {
        try {
            requiredServices.clear();
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
        return removeAllProvidedServices() && removeAllRequiredServices();
    }
}
