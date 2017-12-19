package it.polimi.adaptanalyzertool.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    private double colorRed;
    private double colorBlue;
    private double colorGreen;
    private double colorOpacity;
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
        this(name, cost, availability, 1, 1, 1, 1, new HashMap<>(), new HashMap<>());
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
     * @param colorRed     red component color for the component in the UI.
     * @param colorBlue    blue component color for the component in the UI.
     * @param colorGreen   green component color for the component in the UI.
     * @param colorOpacity opacity component color for the component in the UI.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity) {
        this(name, cost, availability, colorRed, colorGreen, colorBlue, colorOpacity, new HashMap<>(), new HashMap<>());
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
     * @param colorRed         red component color for the component in the UI.
     * @param colorBlue        blue component color for the component in the UI.
     * @param colorGreen       green component color for the component in the UI.
     * @param colorOpacity     opacity component color for the component in the UI.
     * @param providedServices the services provided by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity,
                     HashMap<String, ProvidedService> providedServices) {
        this(name, cost, availability, colorRed, colorGreen, colorBlue, colorOpacity, providedServices, new HashMap<>());
    }

    /**
     * Constructor for the component with all the attributes required.
     *
     * @param name             the name of the component.
     * @param cost             the cost of the component.
     * @param availability     availability expressed in 0-1 range.
     * @param colorRed         red component color for the component in the UI.
     * @param colorBlue        blue component color for the component in the UI.
     * @param colorGreen       green component color for the component in the UI.
     * @param colorOpacity     opacity component color for the component in the UI.
     * @param providedServices the services provided by this component.
     * @param requiredServices the services required by this component.
     *
     * @see RequiredService
     * @see ProvidedService
     */
    public Component(String name, double cost, double availability, double colorRed, double colorGreen, double colorBlue,
                     double colorOpacity,
                     HashMap<String, ProvidedService> providedServices,
                     HashMap<String, RequiredService> requiredServices) {
        this.name = name;
        this.cost = cost;
        this.availability = availability;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.colorOpacity = colorOpacity;
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
     * Gets the red component color for the current Component.
     *
     * @return the red component color for the current Component.
     */
    public double getColorRed() {
        return colorRed;
    }

    /**
     * Changes the red component color for the current Component.
     *
     * @param colorRed the new red component color for the Component.
     *
     * @see Color
     */
    public void setColorRed(double colorRed) {
        this.colorRed = colorRed;
    }

    /**
     * Gets the blue component color for the current Component.
     *
     * @return the blue component color for the current Component.
     */
    public double getColorBlue() {
        return colorBlue;
    }

    /**
     * Changes the blue component color for the current Component.
     *
     * @param colorBlue the new blue component color for the Component.
     *
     * @see Color
     */
    public void setColorBlue(double colorBlue) {
        this.colorBlue = colorBlue;
    }

    /**
     * Gets the green component color for the current Component.
     *
     * @return the green component color for the current Component.
     */
    public double getColorGreen() {
        return colorGreen;
    }

    /**
     * Changes the green component color for the current Component.
     *
     * @param colorGreen the new green component color for the Component.
     *
     * @see Color
     */
    public void setColorGreen(double colorGreen) {
        this.colorGreen = colorGreen;
    }

    /**
     * Gets the opacity component color for the current Component.
     *
     * @return the opacity component color for the current Component.
     */
    public double getColorOpacity() {
        return colorOpacity;
    }

    /**
     * Changes the opacity component color for the current Component.
     *
     * @param colorOpacity the new opacity component color for the Component.
     *
     * @see Color
     */
    public void setColorOpacity(double colorOpacity) {
        this.colorOpacity = colorOpacity;
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
     */
    public void removeProvidedService(ProvidedService serviceProvided) {
        providedServices.remove(serviceProvided.getName(), serviceProvided);
    }

    /**
     * Removes all the provided services from the current component.
     */
    public void removeAllProvidedServices() {
        providedServices.clear();
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
     */
    public void removeRequiredService(RequiredService serviceRequired) {
        requiredServices.remove(serviceRequired.getName(), serviceRequired);
    }

    /**
     * Removes a generic service from a component.
     *
     * @param service the service to be removed, the type of service must extend {@link AbstractService}
     *
     * @throws IllegalArgumentException if the service does not extend {@link AbstractService}
     */
    public void removeService(AbstractService service) {
        if (service instanceof ProvidedService) {
            removeProvidedService((ProvidedService) service);
        } else if (service instanceof RequiredService) {
            removeRequiredService((RequiredService) service);
        } else throw new IllegalArgumentException("Service " + service.getName() + " not recognized");
    }

    /**
     * Removes all the required services from the current component.
     */
    public void removeAllRequiredServices() {
        requiredServices.clear();
    }

    /**
     * Removes all the services, required and provided, from the current component.
     */
    public void removeAllServices() {
        removeAllProvidedServices();
        removeAllRequiredServices();
    }

    public Paint getColor() {
        return new Color(colorRed, colorGreen, colorBlue, colorOpacity);
    }
}
